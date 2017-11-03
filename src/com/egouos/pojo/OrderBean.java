package com.egouos.pojo;

import java.io.Serializable;

public class OrderBean
  implements Serializable
{
  private static final long serialVersionUID = 8961198063107641727L;
  private String outTradeNo;
  private String date;
  private Integer buyCount;
  private double money;
  private String payType;
  private Integer userId;
  private String userName;
  private Integer productId;
  private String productName;
  private String productPeriod;
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public Integer getBuyCount()
  {
    return this.buyCount;
  }
  
  public void setBuyCount(Integer buyCount)
  {
    this.buyCount = buyCount;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  public String getPayType()
  {
    return this.payType;
  }
  
  public void setPayType(String payType)
  {
    this.payType = payType;
  }
  
  public Integer getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public Integer getProductId()
  {
    return this.productId;
  }
  
  public void setProductId(Integer productId)
  {
    this.productId = productId;
  }
  
  public String getProductName()
  {
    return this.productName;
  }
  
  public void setProductName(String productName)
  {
    this.productName = productName;
  }
  
  public String getProductPeriod()
  {
    return this.productPeriod;
  }
  
  public void setProductPeriod(String productPeriod)
  {
    this.productPeriod = productPeriod;
  }
  
  public static long getSerialVersionUID()
  {
    return 8961198063107641727L;
  }
  
  public String getOutTradeNo()
  {
    return this.outTradeNo;
  }
  
  public void setOutTradeNo(String outTradeNo)
  {
    this.outTradeNo = outTradeNo;
  }
}
