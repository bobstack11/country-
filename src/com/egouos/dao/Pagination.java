package com.egouos.dao;

import java.io.Serializable;
import java.util.List;

public class Pagination
  implements Serializable
{
  public int pageSize = 10;
  private int pageNo = 0;
  private int pageCount = 0;
  private int resultCount = 0;
  private boolean isHaveNext = false;
  private boolean isHavePrevious = false;
  private int currentCount;
  private List<?> list = null;
  
  public Pagination() {}
  
  public Pagination(int pageNo, int pageSize, int resultCount, List<?> list)
  {
    this.pageSize = pageSize;
    this.pageNo = pageNo;
    setResultCount(resultCount);
    this.list = list;
    setCurrentCount();
  }
  
  public void setResultCount(int resultCount)
  {
    if (resultCount <= 0) {
      return;
    }
    this.resultCount = resultCount;
    if (resultCount % this.pageSize == 0) {
      this.pageCount = (resultCount / this.pageSize);
    } else {
      this.pageCount = (resultCount / this.pageSize + 1);
    }
    if ((this.pageNo < this.pageCount) && (this.pageNo > 0)) {
      this.isHaveNext = true;
    }
    if ((this.pageNo > 1) && (this.pageNo <= this.pageCount)) {
      this.isHavePrevious = true;
    }
  }
  
  public int getFirstResult()
  {
    int firstNo = 1;
    firstNo = (this.pageNo - 1) * this.pageSize;
    return firstNo;
  }
  
  public int getEndResult()
  {
    int endNo = 1;
    if (this.pageNo == this.pageCount) {
      endNo = this.resultCount;
    } else if ((this.pageNo < this.pageCount) && (this.pageNo > 0)) {
      endNo = this.pageNo * this.pageSize;
    }
    return endNo;
  }
  
  public void setCurrentCount()
  {
    if (this.list != null) {
      this.currentCount = this.list.size();
    }
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;
  }
  
  public int getPageCount()
  {
    return this.pageCount;
  }
  
  public void setPageCount(int pageCount)
  {
    this.pageCount = pageCount;
  }
  
  public boolean isHaveNext()
  {
    return this.isHaveNext;
  }
  
  public void setHaveNext(boolean isHaveNext)
  {
    this.isHaveNext = isHaveNext;
  }
  
  public boolean isHavePrevious()
  {
    return this.isHavePrevious;
  }
  
  public void setHavePrevious(boolean isHavePrevious)
  {
    this.isHavePrevious = isHavePrevious;
  }
  
  public List<?> getList()
  {
    return this.list;
  }
  
  public void setList(List<?> list)
  {
    this.list = list;
  }
  
  public int getResultCount()
  {
    return this.resultCount;
  }
  
  public int getCurrentCount()
  {
    return this.currentCount;
  }
}
