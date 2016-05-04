import java.util.*;

public class PageTable{
	// Iterating over a hash map is a pita, gonna do it this way for less nightmare
	LinkedList<Page> table;
	int entries, pageSize, time;
	PriorityQueue<Page> kickNext;
	
	public PageTable(int entries, int pageSize, String alg){
		this.entries = entries;
		this.pageSize = pageSize;
		table = new LinkedList<Page> (1);
		
		kickNext = new PriorityQueue<Page>(1);
		
		switch(alg){
			
			case "fifo":
			kickNext = new PriorityQueue<Page> (entries, new CompareFIFO);
			break;
			
			case "lru":
			kickNext = new PriorityQueue<Page> (entries, new CompareFIFO);
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
			kickNext.poll();
			
			// add
			kickNext.add(p);
			
			// return
			return false;
		} else {
			// add
			kickNext.add(p);
			
			// return
			return true;
		}
	}
	
	public boolean remove
	
	public void updateTick(int time){
		this.time = time;
	}
	
	class CompareFIFO implements Comparator<Page>{
		public CompareFIFO(){

		}

		public int compare (Page p1, Page p2){
			return p1.getBirthday() - p2.getBirthday();
		}

	}

	class CompareLRU implements Comparator<Page>{
		public CompareLRU(){

		}

		public int compare (Page p1, Page p2){
			return p1.getLastUsed() - p2.getLastUsed();
		}

	}
}