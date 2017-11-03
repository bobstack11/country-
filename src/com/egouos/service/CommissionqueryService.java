package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Commissionquery;
import java.util.List;

public abstract interface CommissionqueryService
  extends TService<Commissionquery, Integer>
{
  public abstract Pagination getCommissionQueryList(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Integer getCommissionQueryListByCount(String paramString1, String paramString2, String paramString3);
  
  public abstract List getCommissionQueryListByData(String paramString);
}
