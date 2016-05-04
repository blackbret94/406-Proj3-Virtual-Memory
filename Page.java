public class Page{
  private boolean dirty;
  private int birthday, lastUsed, nextUse;
  
  public Page(){
    
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