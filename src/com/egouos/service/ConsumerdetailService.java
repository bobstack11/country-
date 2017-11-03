package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Consumerdetail;

public abstract interface ConsumerdetailService
  extends TService<Consumerdetail, Integer>
{
  public abstract Pagination userByConsumetableDetail(String paramString, int paramInt1, int paramInt2);
  
  public abstract Integer userByConsumetableDetailByCount(String paramString);
  
  public abstract Pagination orderInfo(String paramString, int paramInt1, int paramInt2);
}
