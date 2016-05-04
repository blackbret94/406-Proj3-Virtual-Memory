import java.util.*;

public class PageTable{
	// Iterating over a hash map is a pita, gonna do it this way for less nightmare
	ArrayList<Page> table;
	int entries, pageSize, time;
	PriorityQueue<Page> kickNext;
	
	public PageTable(int entries, int pageSize, String alg){
		this.entries = entries;
		this.pageSize = pageSize;
		table = new ArrayList<Page> (1); // represents frames
		kickNext = new PriorityQueue<Integer>(1); // queue of what frames should be replaced next
		
		switch(alg){
			
			case "fifo":
			kickNext = new PriorityQueue<Integer> (entries, new CompareFIFO());
			break;
			
			case "lru":
			kickNext = new PriorityQueue<Integer> (entries, new CompareFIFO());
			break;
			
			default:
			throw new RuntimeException("Specified Scheduling method not recognized");
		}
		
	}
	
	public boolean isFull(){
		if (table.size() == entries) return true;
		return false;
	}
	
	public boolean hasFrame(int pageNumber){
		for(Page page: table){
			if(page.getPageNumber() == pageNumber) return true;
		}
		return false;
	}
	
	public Process getEntry(int pageNumber){
		for(Page page: table){
			if(page.getPageNumber() == pageNumber) return page;
		}
		
		throw new RuntimeException("Attempted to get Page not in Table");
		return null;
	}
	//true if it does not replace, false if it does replace
	// keeps all the prints in the Main class
	public boolean add(Process p){
		if(kickNext.size() >= entries){
			// pop
			int insertSpot = kickNext.poll();
			
			// add
			table.set(insertSpot,p);
			kickNext.add(insertSpot);
			
			// print
			System.out.println("loaded page #"+p.getPage().getNumber()+" of process #" + p.getPid() + " to frame #"+insertSpot+" with replacement.");
			
			// return
			return false;
		} else {
			// add
			insertSpot = kickNext.size();
			table.set(insertSpot,p);
			kickNext.add(insertSpot);
			
			// print
			System.out.println("loaded page #"+p.getPage().getNumber()+" of process #" + p.getPid() + " to frame #"+insertSpot+" with no replacement.");
			
			// return
			return true;
		}
	}
	
	public boolean remove(){
		// LIKELY NOT NEEDED
	}
	
	public void updateTick(int time){
		this.time = time;
	}
	
	class CompareFIFO implements Comparator<Integer>{
		public CompareFIFO(){

		}

		public int compare (Integer p1, Integer p2){
			return table.get(p1).getBirthday() - table.get(p2).getBirthday();
		}

	}

	class CompareLRU implements Comparator<Integer>{
		public CompareLRU(){

		}

		public int compare (Integer p1, Integer p2){
			return table.get(p1).getLastUsed() - table.get(p2).getLastUsed();
		}

	}
}