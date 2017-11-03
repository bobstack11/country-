package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Commissionpoints;

public abstract interface CommissionpointsService
  extends TService<Commissionpoints, Integer>
{
  public abstract Pagination CommissionPoints(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Integer getCommissionPointsListByCount(String paramString1, String paramString2, String paramString3);
}
