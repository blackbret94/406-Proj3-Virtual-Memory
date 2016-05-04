public class PageTable{
	// Iterating over a hash map is a pita, gonna do it this way for less nightmare
	LinkedList<Process> table;
	int entries, pageSize;
	
	public PageTable(int entries, int pageSize, String alg){
		this.entries = entries;
		this.pageSize = pageSize;
		table = new LinkedList<Process> (entries);
		
	}
	
	public boolean isFull(){
		return true;
	}
	
	public boolean isInTable(){
		return true;
	}
	
	public Process getEntry(int pageNumber){
		return null;
	}
	
	//true if it does not replace, false if it does replace
	// keeps all the prints in the Main class
	public boolean add(Process){
		return true;
	}

}