public class Process {
  
  private int pid, address, page;
  private boolean writable;
  
  public Process(){
    // blank constructor should not be used this is not mutable
    System.out.println("Warning! An empty process was created");
  }
  
  public Process(int pid, int address, boolean writable){
    this.pid = pid;
    this.address = address;
    this.writable = writable;
	page = -1;
  }
  
  public int getPid(){
    return pid;
  }
  
  public int getAddress(){
    return address;
  }
  
  public boolean canWrite(){
    return writable;
  }
  
  public boolean setPage(int page){
	  this.page = page;
  }
  
  public void getPage(){
	  return page;
  }
}