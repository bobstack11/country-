package com.egouos.generics.dao;

import java.util.List;

public class PaginationSupport
{
  public static final int PAGESIZE = 10;
  private int pageSize = 10;
  private List items;
  private int totalCount;
  private int[] indexes = new int[0];
  private int startIndex = 0;
  
  public PaginationSupport(List items, int totalCount)
  {
    setPageSize(10);
    setTotalCount(totalCount);
    setItems(items);
    setStartIndex(0);
  }
  
  public PaginationSupport(List items, int totalCount, int startIndex)
  {
    setPageSize(10);
    setTotalCount(totalCount);
    setItems(items);
    setStartIndex(startIndex);
  }
  
  public PaginationSupport(List items, int totalCount, int pageSize, int startIndex)
  {
    setPageSize(pageSize);
    setTotalCount(totalCount);
    setItems(items);
    setStartIndex(startIndex);
  }
  
  public static int convertFromPageToStartIndex(int pageNo)
  {
    return (pageNo - 1) * 10;
  }
  
  public static int convertFromPageToStartIndex(int pageNo, int pageSize)
  {
    return (pageNo - 1) * pageSize;
  }
  
  public List getItems()
  {
    return this.items;
  }
  
  public void setItems(List items)
  {
    this.items = items;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getTotalCount()
  {
    return this.totalCount;
  }
  
  public void setTotalCount(int totalCount)
  {
    if (totalCount > 0)
    {
      this.totalCount = totalCount;
      int count = totalCount / this.pageSize;
      if (totalCount % this.pageSize > 0) {
        count++;
      }
      this.indexes = new int[count];
      for (int i = 0; i < count; i++) {
        this.indexes[i] = (this.pageSize * i);
      }
    }
    else
    {
      this.totalCount = 0;
    }
  }
  
  public int[] getIndexes()
  {
    return this.indexes;
  }
  
  public void setIndexes(int[] indexes)
  {
    this.indexes = indexes;
  }
  
  public int getStartIndex()
  {
    return this.startIndex;
  }
  
  public void setStartIndex(int startIndex)
  {
    if (this.totalCount <= 0) {
      this.startIndex = 0;
    } else if (startIndex >= this.totalCount) {
      this.startIndex = this.indexes[(this.indexes.length - 1)];
    } else if (startIndex < 0) {
      this.startIndex = 0;
    } else {
      this.startIndex = this.indexes[(startIndex / this.pageSize)];
    }
  }
  
  public int getNextIndex()
  {
    int nextIndex = getStartIndex() + this.pageSize;
    if (nextIndex >= this.totalCount) {
      return getStartIndex();
    }
    return nextIndex;
  }
  
  public int getPreviousIndex()
  {
    int previousIndex = getStartIndex() - this.pageSize;
    if (previousIndex < 0) {
      return 0;
    }
    return previousIndex;
  }
  
  public long getTotalPageCount()
  {
    if (this.totalCount % this.pageSize == 0) {
      return this.totalCount / this.pageSize;
    }
    return this.totalCount / this.pageSize + 1;
  }
  
  public long getCurrentPageNo()
  {
    return this.startIndex / this.pageSize + 1;
  }
  
  public boolean hasNextPage()
  {
    return getCurrentPageNo() < getTotalPageCount() - 1L;
  }
  
  public boolean hasPreviousPage()
  {
    return getCurrentPageNo() > 1L;
  }
}
