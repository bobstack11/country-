package com.egouos.service;

import com.egouos.pojo.Producttype;
import java.util.List;

public abstract interface ProducttypeService
  extends TService<Producttype, Integer>
{
  public abstract List<Producttype> queryAllProductType();
  
  public abstract List<Producttype> listByProductList();
  
  public abstract List<Producttype> listByProductListBybrand(String paramString);
  
  public abstract List<Producttype> listByBrand(String paramString);
  
  public abstract Producttype findBrandById(String paramString);
  
  public abstract Producttype findTypeByName(String paramString);
  
  public abstract Producttype findBrandByName(String paramString);
}
