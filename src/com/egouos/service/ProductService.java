package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Product;

public abstract interface ProductService
  extends TService<Product, Integer>
{
  public abstract Pagination searchSpellbuyproduct(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Pagination searchProduct(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Product findProductByName(String paramString);
  
  public abstract Pagination ProductListByTypeIdList(String paramString1, String paramString2, int paramInt1, int paramInt2);
}
