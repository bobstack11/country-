package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Consumetable;

public abstract interface ConsumetableService
  extends TService<Consumetable, Integer>
{
  public abstract Consumetable findByOutTradeNo(String paramString);
  
  Consumetable findByOutTradeNo(String id, final boolean lock);
  
  public abstract Pagination userByConsumetableByDelta(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Integer userByConsumetableByDeltaByCount(String paramString1, String paramString2, String paramString3);
  
  public abstract Pagination userByConsumetable(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Double totalRecharge(String paramString1, String paramString2);
  
  public abstract Double totalBuy(String paramString1, String paramString2);
  
  public abstract Double totalPay(String paramString1, String paramString2);
  
  public abstract Integer userByConsumetableByCount(String paramString1, String paramString2, String paramString3);
  
  public abstract Pagination orderList(String paramString, int paramInt1, int paramInt2);

  public abstract int restoreTimeoutOrders(long timeout, int nr);
  
  public abstract int countNotPaid(final Integer productId);
  
  public abstract Consumetable paid(String txno, String xid);
}
