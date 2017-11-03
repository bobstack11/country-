package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Applymention;

public abstract interface ApplymentionService
  extends TService<Applymention, Integer>
{
  public abstract Pagination getApplymentionList(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Integer getApplymentionListByCount(String paramString1, String paramString2, String paramString3);
}
