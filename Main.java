/** Programming project 3
	* @author Bret Black, Will Dixon 
	* This class is inspired by the Main class of Project 2**/

import java.util.*;
import java.io.*;

// TODO: Throw an error if not in the acceptable range
public class Main{
	private static int VIRT_ADDR_SPACE = 65536;
	private static int VIRT_ADDR_SPACE_EXP = 16;
	private static int P_MEM = 2048;
	private static int P_MEM_EXP = 11;
	private int PAGE_SIZE = 0;
	private int PAGE_SIZE_EXP = 0;
	
	public static void main (String[] args) {
		Main main = new Main();
		main.init(args);
	}

	public void init(String[] args) {
		
		String alg, inputFileName;
		// get stuff from arguements
		try{
			alg = args[0];
		}
		catch(Exception e){
			System.out.println("Algorithm not specified, please properly format your arguement. " + e);
			return;
		}
		try {
			PAGE_SIZE = Integer.parseInt(args[1]);
			
			// throw an error if it is not in the accepted range
			if(PAGE_SIZE < 32 || PAGE_SIZE > 512) throw new RuntimeException("Specified Page Size out of Bounds");
			
		}
		catch (Exception e){
			System.out.println("Page size not properly specified, please properly format your argument. " + e);
			return;
		}
		//calc exp from bits
		PAGE_SIZE_EXP = (int) (Math.log(PAGE_SIZE)/Math.log(2));
		
		try{
			inputFileName = args[2];
		}
		catch(Exception e){
			System.out.println("File name not specified, please properly format your arguement. " + e);
			return;
		}
		
		//read from file
		LinkedList<Process> tempQueue = new LinkedList<Process>();
		
		Scanner in;
		try{
			in = new Scanner(new FileReader(inputFileName));
		}
		catch(Exception e){
			System.out.println("File not readable: "+ e );
			return;
		}
		
		//takes each line and breaks it at ,[whitespace] combinations
		while(in.hasNextLine()){
			String[] brokenLine = in.nextLine().split(",\\s");
			
			//converting all of these strings to the right things
			int pid = Integer.parseInt(brokenLine[0]);

			int address = -1;
			try{
				address = Integer.parseInt(brokenLine[1]);
			}
			catch(Exception e){
				System.out.println("Address either not given or not an integer: "+e);
				return;
			}
			boolean writable = false;
			try{
				if(brokenLine[2].equals("R")){
					writable = false;
				} else if(brokenLine[2].equals("W")){
					writable = true;
				} else System.out.println("Read/write could not be interpretted as a boolean, please specify as \"R\" or \"W\"");
				writable = Boolean.parseBoolean(brokenLine[2]);
			}
			catch(Exception e){
				System.out.println("Read/write could not be interpretted as a boolean, please specify as \"R\" or \"W\": "+e);
				return;
			}
			
			//adding it to the queue structure
			tempQueue.add(new Process(pid, address, writable));
		}
		
		in.close();
		
		
		//Conversion between incoming process/instruction to Page goes here
		//preprocessing for OPT
		
		//figure out page number and when process is executed
		int counter = 0;
		for(Process entry: tempQueue){
			
			int pageNumber = (int) entry.getAddress()/ PAGE_SIZE;
			
			entry.setPage(pageNumber);
			entry.setBirthday(counter);
			counter++;
		}
		
		for(int i = 0; i<tempQueue.size(); i++){
			Process currentInstruction = tempQueue.get(i);
			System.out.println(currentInstruction.getBirthday());
			//determine previous
			currentInstruction.setLastUsed(currentInstruction.getBirthday());
			for(int j = 0; j<i; j++){
				Process previousInstruction = tempQueue.get(j);
				if(previousInstruction.getPid()==currentInstruction.getPid() && previousInstruction.getPage() == currentInstruction.getPage()){
					currentInstruction.setLastUsed(j);
				}
			}
			//determine next
			currentInstruction.setUsedAgain(false);
			for(int j = i+1; j<tempQueue.size(); j++){
				Process nextInstruction = tempQueue.get(j);
				if(nextInstruction.getPid()==currentInstruction.getPid() && nextInstruction.getPage() == currentInstruction.getPage()){
					currentInstruction.setNextUse(j);
					currentInstruction.setUsedAgain(true);
				}
			}
		}
		
		System.out.println(P_MEM);
		System.out.println(PAGE_SIZE);
		PageTable frameTable = new PageTable(P_MEM/PAGE_SIZE, PAGE_SIZE, alg);
		
		while(!tempQueue.isEmpty()){
			//pop
			Process nextInstruction = tempQueue.poll();
			
			//make nice readable vars
			frameTable.add(nextInstruction);
		}	
		frameTable.printResults();
	}
}