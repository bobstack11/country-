package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Consumetable;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.Spellbuyproduct;

import java.util.List;
import java.util.Map;

public abstract interface SpellbuyproductService
  extends TService<Spellbuyproduct, Integer>
{
  public abstract List findByProductId(int paramInt);
  
  List findByProductId(int productId, boolean lock);
  
  public abstract Pagination upcomingAnnounced(int paramInt1, int paramInt2);
  
  public abstract Pagination upcomingAnnouncedByTop(int paramInt1, int paramInt2);
  
  public abstract List productPeriodList(Integer paramInteger);
  
  public abstract Spellbuyproduct findSpellbuyproductLastPeriod(Integer paramInteger);
  
  public abstract Spellbuyproduct findSpellbuyproductByStatus(Integer paramInteger);
  
  public abstract Pagination announcedProduct(int paramInt1, int paramInt2);
  
  public abstract List loadAllByType();
  
  public abstract List loadAll();
  
  public abstract List UpdateLatestlotteryGetList();
  
  public abstract List findSpellbuyproductByProductIdIsStatus(Integer paramInteger);
  
  public abstract List getIndexNewProduct();
  
  public abstract Spellbuyproduct getByProductId(int productId);
  
  public abstract Spellbuyproduct getByProductId(int productId, boolean lock);
  
  public abstract  List<Object[]> mayfullBuyProducts(int pageNo, int pageSize, boolean lock);

  public abstract void generateNextPeriods(int nr);
  
  Spellbuyproduct getByFkProductId(final int fkProductId, final boolean lock);
  
}
