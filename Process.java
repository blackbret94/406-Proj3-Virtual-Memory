public class Process {
  
  private int pid, address, page, birthday, nextUse, lastUsed;
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
  
  public void setPage(int page){
	  this.page = page;
  }
  
  public int getPage(){
	  return page;
  }
  
  public int getBirthday(){
    return birthday;
  }
  
  public void setBirthday(int birthday){
    this.birthday = birthday;  
  }
  
  public int getLastUsed(){
    return lastUsed;
  }
  
  public void setLastUsed(int lastUsed){
    this.lastUsed = lastUsed;
  }
  
  public int getNextUse(){
    return nextUse;
  }
  
  public void setNextUse(int nextUse){
    this.nextUse = nextUse;
  }
}