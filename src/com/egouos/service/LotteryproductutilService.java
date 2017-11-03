package com.egouos.service;

import com.egouos.pojo.Lotteryproductutil;

import java.util.List;

public abstract interface LotteryproductutilService
  extends TService<Lotteryproductutil, Integer>
{
  public abstract List<Lotteryproductutil> loadAll();
  
  public List<Lotteryproductutil> loadAll(final Integer spellbuyProductStatus);
  
  public abstract List<Lotteryproductutil> findList(int pageNo, int pageSize);
  
}
