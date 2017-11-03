package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Spellbuyrecord;

import java.math.BigDecimal;
import java.util.List;

public abstract interface SpellbuyrecordService
  extends TService<Spellbuyrecord, Integer>
{
  public abstract Pagination findHotProductList(int paramInt1, int paramInt2);
  
  public abstract Pagination indexNewProductList(int paramInt1, int paramInt2);
  
  public abstract Pagination indexHotProductList(int paramInt1, int paramInt2);
  
  public abstract Pagination getNowBuyList(int paramInt1, int paramInt2);
  
  public abstract Pagination getNowBuyAjaxList(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Pagination getAllBuyRecord(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Pagination getlotteryDetail(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract List getSpellbuyRecordByLast100(String paramString1, String paramString2, int paramInt);
  
  public abstract List getEndBuyDateByProduct(Integer paramInteger);
  
  public abstract Pagination findProductByTypeId(String paramString, int paramInt1, int paramInt2);
  
  public abstract Pagination ProductByTypeIdList(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Pagination nowUpProducts(int paramInt1, int paramInt2);
  
  public abstract Pagination searchProduct(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Pagination LatestParticipate(String paramString, int paramInt1, int paramInt2);
  
  public abstract Integer LatestParticipateByCount(String paramString);
  
  public abstract List getRandomNumberList(String paramString1, String paramString2);
  
  public abstract Pagination getRandomNumberListPage(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Integer getRandomNumberListPageByCount(String paramString1, String paramString2);
  
  public abstract Pagination buyHistoryByUser(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Pagination buyHistoryByUser(String userId, String state, String startDate, String endDate, int pageNo, int pageSize);
  
  public abstract Integer buyHistoryByUserByCount(String paramString1, String paramString2, String paramString3);
  
  public abstract Integer buyHistoryByUserByCount(String userId, String state, String startDate, String endDate);
  
  public abstract List getBuyHistoryByDetail(String paramString1, String paramString2);
  
  public abstract List getUserByHistory(String paramString1, String paramString2);
  
  public abstract List randomByBuyHistoryByspellbuyProductId(Integer paramInteger, String paramString);
  
  public abstract List WinRandomNumber(Integer paramInteger1, Integer paramInteger2);
  
  public abstract BigDecimal getAllByCount();
  
  public abstract BigDecimal findSumByBuyPriceByUserId(String paramString);
}
