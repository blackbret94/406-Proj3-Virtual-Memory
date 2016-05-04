/** Programming project 3
	* @author Bret Black, Will Dixon 
	* This class is inspired by the Main class of Project 2**/

import java.util.*;
import java.io.*;

// TODO: Throw an error if not in the acceptable range
public class Main{
	private int pageFaults;
	private int diskAccesses;
	private static int VIRT_ADDR_SPACE = 2^16;
	private static int VIRT_ADDR_SPACE_EXP = 16;
	private static int P_MEM = 2^11;
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
		
		PageTable frameTable = new PageTable(P_MEM/PAGE_SIZE, PAGE_SIZE, alg);
		
		while(!tempQueue.isEmpty()){
			//pop
			Page nextInstruction = tempQueue.poll();
			
			//make nice readable vars
			int virtPageNumber = nextInstruction.getPageNumber();
			int pid = nextInstruction.getpid();
			
			//is it in the table?
			if(frameTable.hasFrame(virtPageNumber, pid)){
				System.out.println("no page fault. accessed frame #"/* need phys addr here*/);
				if(nextInstruction.isWrite()){
						//flip dirty bit
				}
				System.out.println("no page fault. accessed frame #"/* need phys addr here*/);
				System.out.println("\tVirtual Address: "+/*virt address*/ " -> Physical Address"/*phys address*/);
			}
			else{
				//is table full?
				if(frameTable.isFull()){
					//replace
					
				}
				//add

			}
			
		}
		
	}

	/** FIRST COME FIRST SERVE */
	// LEFT FOR EXAMPLE
	/*public void fcfs(LinkedList<Process> inQueue){
		System.out.println("RUNNING FCFS");
		
		PriorityQueue<Process> sched = new PriorityQueue<Process>(5, new CompareFCFS());
		//get all the processes into the queue
		for(Process entry: inQueue){
			sched.add(entry);
		}
		//simulate
		int time = 0;
		
		while(!sched.isEmpty()) {
			//pop
			Process currentProcess = sched.poll();
			//print
			currentProcess.setIsCurrentProcess(true);
			boolean processHasArrived = currentProcess.getArrival() <= time;
			if(processHasArrived){
				System.out.println("Time: "+time+ ", process "+ currentProcess.getpid()+" running");
				
				//note when the process is first served to the CPU
				if(currentProcess.getResponse() == -1){
					currentProcess.setResponse(time - currentProcess.getArrival());
				}
			}
			
			else System.out.println("Time: "+time+ ", No process running");
			//increment all other processes waiting times
			for(Process entry: sched){
				//make sure a process only thinks that it is waiting if it has already arrived
				if(entry.getArrival()<=time){
					entry.incrementWaiting();
				}
			}

			
			//decrement remaining time and put back in queue so long as the process is not yet done running
			//puts a not arrived process back into the queue as well
			if(currentProcess.getRemaining()>1 || !processHasArrived){
				currentProcess.setRemaining(currentProcess.getRemaining()-1);
				sched.add(currentProcess);
			}
			time++;
		}
		//analyze
		analyze();
	}*/

	
	/** analysis function called at the end */
	public void analyze(){
		System.out.println("Page faults: " + pageFaults);
		System.out.println("Disk accesses: " + diskAccesses);
	}
}