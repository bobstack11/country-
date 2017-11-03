package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Cardpassword;

public abstract interface CardpasswordService
  extends TService<Cardpassword, Integer>
{
  public abstract Cardpassword doCardRecharge(String paramString1, String paramString2);
  
  public abstract Pagination cardRechargeList(int paramInt1, int paramInt2);
  
  public abstract void deleteByID(Integer paramInteger);
}
