import java.util.*;

public class Page{
  private boolean dirty;
  private int birthday, lastUsed, nextUse, number, size, pid, virtNumber;
  
  public Page(){
    System.out.println("Blank page constructor should NOT be used");
  }
	
	public Page(int size, int number){
		this.size = size;
		this.number = number;
	}
	
	public boolean isDirty(){
		return dirty;
	}
	
	public void setDirty(boolean b){
		dirty = b;
	}
	
	public int getPid(){
		return pid;
	}
	
	public void setPid(int p){
		pid = p;
	}

	public int getSize(){
		return size;
	}
	
	public int getNumber(){
    return number;
  }
  
  public void setNumber(int number){
    this.number = number;  
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
  
  public int getVirtNumber(){
    return virtNumber;
  }
  
  public void setVirtNumber(int virtNumber){
    this.virtNumber = virtNumber;
  }
}