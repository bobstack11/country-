package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Orderdetailaddress;
import java.util.List;

public abstract interface LatestlotteryService
  extends TService<Latestlottery, Integer>
{
  public abstract Pagination LatestAnnounced(int paramInt1, int paramInt2);
  
  public abstract Pagination adminByLatestAnnounced(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List indexWinningScroll();
  
  public abstract List getBuyHistoryByDetail(Integer paramInteger);
  
  public abstract List getByProductHistoryWin(Integer paramInteger);
  
  public abstract Pagination getProductByUser(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2);
  
  public abstract Integer getProductByUserByCount(String paramString1, String paramString2, String paramString3);
  
  public abstract List getLotteryDetail(Integer paramInteger);
  
  public abstract List getProductOtherWinUser(String paramString1, String paramString2);
  
  public abstract Integer getCountByLotteryDetail(String paramString);
  
  public abstract Pagination getLotteryDetailBybuyerList(Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract Integer getLotteryDetailBybuyerListByCount(Integer paramInteger);
  
  public abstract List getLatestlotteryBySpellbuyProductIdAndProductIdIsExist(Integer paramInteger);
  
  public abstract Latestlottery findLatestlotteryByspellbuyrecordId(Integer paramInteger);
  
  public abstract List orderdetailListById(String paramString);
  
  public abstract Orderdetailaddress orderdetailaddressFindByOrderdetailId(String paramString);
  
  public abstract void addOrderdetailaddress(Orderdetailaddress paramOrderdetailaddress);
  
  public abstract Integer getLotteryByCount();
}
