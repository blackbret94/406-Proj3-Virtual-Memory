import java.util.*;

public class PageTable{
	// Iterating over a hash map is a pita, gonna do it this way for less nightmare
	ArrayList<Page> table;
	int entries, pageSize, time;
	PriorityQueue<Integer> kickNext;
	
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
			if(page.getNumber() == pageNumber) return true;
		}
		return false;
	}
	
	public Page getEntry(int pageNumber){
		for(Page page: table){
			if(page.getNumber() == pageNumber) return page;
		}
		
		throw new RuntimeException("Attempted to get Page not in Table");
	}
	//true if it does not replace, false if it does replace
	// keeps all the prints in the Main class
	public boolean add(Process p){
		if(kickNext.size() >= entries){
			// pop
			int insertSpot = kickNext.poll();
			
			// create page
			Page newPage = createPage(p,insertSpot);
			
			// add
			table.set(insertSpot,newPage);
			kickNext.add(insertSpot);
			
			// print
			System.out.println("loaded page #"+newPage.getNumber()+" of process #" + p.getPid() + " to frame #"+insertSpot+" with replacement.");
			
			// return
			return false;
		} else {
			// add
			int insertSpot = kickNext.size();
			
			// create page
			Page newPage = createPage(p,insertSpot);
			
			table.add(newPage);
			kickNext.add(insertSpot);
			
			// print
			System.out.println("loaded page #"+newPage.getNumber()+" of process #" + p.getPid() + " to frame #"+insertSpot+" with no replacement.");
			
			// return
			return true;
		}
	}
	
	public Page createPage(Process p, int number){
		// instanciate
		Page newPage = new Page(pageSize,number);
		
		// fill out
		
		// return
		return newPage;	
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