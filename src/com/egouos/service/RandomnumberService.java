package com.egouos.service;

import com.egouos.pojo.Randomnumber;

import java.math.BigDecimal;
import java.util.List;

public abstract interface RandomnumberService
  extends TService<Randomnumber, Integer>
{
  public abstract List LotteryDetailByRandomnumber(Integer paramInteger1, Integer paramInteger2);
  
  public abstract BigDecimal RandomNumberByUserBuyCount(String paramString, Integer paramInteger);
  
  public abstract Randomnumber findRandomnumberByspellbuyrecordId(Integer paramInteger);
  
  public abstract Randomnumber getUserBuyCodeByBuyid(String paramString1, String paramString2);
  
  List<Randomnumber> query(String hql, String alias, boolean lock);;
  
}
