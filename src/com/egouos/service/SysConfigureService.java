package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.IndexImg;
import com.egouos.pojo.Suggestion;
import com.egouos.pojo.SysConfigure;
import java.util.List;

public abstract interface SysConfigureService
  extends TService<SysConfigure, Integer>
{
  public abstract List initializationIndexImgAll();
  
  public abstract List IndexImgAll();
  
  public abstract void addIndexImg(IndexImg paramIndexImg);
  
  public abstract IndexImg findByIndexImgId(String paramString);
  
  public abstract void delIndexImg(Integer paramInteger);
  
  public abstract void doSuggestion(Suggestion paramSuggestion);
  
  public abstract Pagination suggestionList(int paramInt1, int paramInt2);
}
