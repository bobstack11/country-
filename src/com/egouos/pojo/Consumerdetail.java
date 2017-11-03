package com.egouos.pojo;

import java.io.Serializable;

public class Consumerdetail
  implements Serializable
{
  private Integer id;
  private String consumetableId;
  private Integer productId;
  private Integer buyCount;
  private Double buyMoney;
  private String productTitle;
  private String productName;
  private Integer productPeriod;
  
  public Consumerdetail() {}
  
  public Consumerdetail(String consumetableId, Integer productId, Integer buyCount, Double buyMoney, Integer productPeriod)
  {
    this.consumetableId = consumetableId;
    this.productId = productId;
    this.buyCount = buyCount;
    this.buyMoney = buyMoney;
    this.productPeriod = productPeriod;
  }
  
  public Consumerdetail(String consumetableId, Integer productId, Integer buyCount, Double buyMoney, String productTitle, String productName, Integer productPeriod)
  {
    this.consumetableId = consumetableId;
    this.productId = productId;
    this.buyCount = buyCount;
    this.buyMoney = buyMoney;
    this.productTitle = productTitle;
    this.productName = productName;
    this.productPeriod = productPeriod;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getConsumetableId()
  {
    return this.consumetableId;
  }
  
  public void setConsumetableId(String consumetableId)
  {
    this.consumetableId = consumetableId;
  }
  
  public Integer getProductId()
  {
    return this.productId;
  }
  
  public void setProductId(Integer productId)
  {
    this.productId = productId;
  }
  
  public Integer getBuyCount()
  {
    return this.buyCount;
  }
  
  public void setBuyCount(Integer buyCount)
  {
    this.buyCount = buyCount;
  }
  
  public Double getBuyMoney()
  {
    return this.buyMoney;
  }
  
  public void setBuyMoney(Double buyMoney)
  {
    this.buyMoney = buyMoney;
  }
  
  public String getProductTitle()
  {
    return this.productTitle;
  }
  
  public void setProductTitle(String productTitle)
  {
    this.productTitle = productTitle;
  }
  
  public String getProductName()
  {
    return this.productName;
  }
  
  public void setProductName(String productName)
  {
    this.productName = productName;
  }
  
  public Integer getProductPeriod()
  {
    return this.productPeriod;
  }
  
  public void setProductPeriod(Integer productPeriod)
  {
    this.productPeriod = productPeriod;
  }
}
