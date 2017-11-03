package com.egouos.test;

import java.util.List;

public class EngineContainer
{
  private List<String> engineList;
  private static int count = 0;
  
  public EngineContainer(List<String> engineList)
  {
    this.engineList = engineList;
  }
  
  public synchronized String getEngineUrl()
  {
    if (this.engineList.size() == count) {
      count = 0;
    }
    return (String)this.engineList.get(count++);
  }
  
  public boolean isEmpty()
  {
    boolean isEmpty = false;
    if (this.engineList.size() == 0) {
      isEmpty = true;
    }
    return isEmpty;
  }
}
