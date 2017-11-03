package com.egouos.service;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Orderdetailaddress;

public abstract interface OrderdetailaddressService
  extends TService<Orderdetailaddress, Integer>
{
  public abstract Pagination orderdetailaddressList(int paramInt1, int paramInt2);
}
