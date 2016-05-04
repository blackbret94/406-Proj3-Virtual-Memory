import java.util.*;

public class PageTable{
	// Iterating over a hash map is a pita, gonna do it this way for less nightmare
	ArrayList<Page> table;
	int entries, pageSize, time;
	PriorityQueue<Integer> kickNext;
	private int pageFaults;
	private int diskAccesses;
	
	public PageTable(int entries, int pageSize, String alg){
		this.entries = entries;
		this.pageSize = pageSize;
		table = new ArrayList<Page> (1); // represents frames
		kickNext = new PriorityQueue<Integer>(1); // queue of what frames should be replaced next
		
		pageFaults = 0;
		diskAccesses = 0;
		
		switch(alg){
			
			case "fifo":
			kickNext = new PriorityQueue<Integer> (entries, new CompareFIFO());
			break;
			
			case "lru":
			kickNext = new PriorityQueue<Integer> (entries, new CompareLRU());
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
	public void add(Process p){
		for(int i=0; i<table.size(); i++){
			// check for match
			if(table.get(i).getPid() == p.getPid() && table.get(i).getVirtNumber() == p.getPage()){
				// already in table
				
				// mark as dirty if writes
				if(p.canWrite()){
					table.get(i).setDirty(true);	
				}
				
				// print
				System.out.println("no page fault. accessed frame #"+i);
				System.out.println("\tVirtual Address: "+p.getAddress()+"  ->  Physical Address: "+(i*pageSize+(p.getAddress()-pageSize*p.getPage())));
				// return
				return;
			}
		}
		
		if(kickNext.size() >= entries){
			// pop
			int insertSpot = kickNext.poll();
			
			// create page
			Page newPage = createPage(p,insertSpot);
			if(p.canWrite()) newPage.setDirty(true);
			Page oldPage = table.get(insertSpot);
			// add
			table.set(insertSpot,newPage);
			kickNext.add(insertSpot);
			
			//virt addr/page size
			int virtAdd = p.getAddress()/pageSize;
			
			// print
			System.out.println("loaded page #"+virtAdd+" of processes #" + p.getPid() + " to frame #"+insertSpot+" with replacement");
			if(oldPage.isDirty()){
				System.out.println("\tNeeded to write frame #"+insertSpot+" to memory");
				diskAccesses++;
			}
			System.out.println("\tVirtual Address: "+p.getAddress()+"  ->  Physical Address: "+(insertSpot*pageSize+(p.getAddress()-pageSize*p.getPage())));
			
			//update info counts
			pageFaults++;
			diskAccesses++;
			
			// return
			return;
		} else {
			// add
			int insertSpot = kickNext.size();
			
			// create page
			Page newPage = createPage(p,insertSpot);
			if(p.canWrite()) newPage.setDirty(true);
			
			table.add(newPage);
			kickNext.add(insertSpot);
			
			//virt addr/page size
			int virtAdd = p.getAddress()/pageSize;
			
			// print
			System.out.println("loaded page #"+virtAdd+" of processes #" + p.getPid() + " to frame #"+insertSpot+" with no replacement.");
			System.out.println("\tVirtual Address: "+p.getAddress()+"  ->  Physical Address: "+(insertSpot*pageSize+(p.getAddress()-pageSize*p.getPage())));
			
			//update info counts
			pageFaults++;
			diskAccesses++;
			
			// return
			return;
		}
	}
	
	public Page createPage(Process p, int number){
		// instanciate
		Page newPage = new Page(pageSize,number);
		
		// fill out
		newPage.setBirthday(p.getBirthday());
		newPage.setLastUsed(p.getLastUsed());
		newPage.setNextUse(p.getNextUse());
		newPage.setPid(p.getPid());
		newPage.setVirtNumber(p.getPage());
		
		// return
		return newPage;	
	}
	
	public void printResults(){
		System.out.println("Number of page faults: "+pageFaults+". Number of memory accesses: "+diskAccesses);
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
			//System.out.println(table.get(p1).getLastUsed() +"-"+ table.get(p2).getLastUsed());
			return table.get(p1).getLastUsed() - table.get(p2).getLastUsed();
		}

	}
}