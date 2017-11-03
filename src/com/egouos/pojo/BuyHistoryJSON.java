package com.egouos.pojo;

import java.io.Serializable;

public class BuyHistoryJSON
  implements Serializable
{
  private Integer productId;
  private String productName;
  private String productTitle;
  private String productImg;
  private Integer productPeriod;
  private Integer buyStatus;
  private String winUser;
  private String buyTime;
  private Long buyCount;
  private Integer historyId;
  private Integer winUserId;
  private Integer winId;
  private String winDate;
  private Integer spellbuyCount;
  private Integer productPrice;
  
  public String getBuyTime()
  {
    return this.buyTime;
  }
  
  public void setBuyTime(String buyTime)
  {
    this.buyTime = buyTime;
  }
  
  public Integer getSpellbuyCount()
  {
    return this.spellbuyCount;
  }
  
  public void setSpellbuyCount(Integer spellbuyCount)
  {
    this.spellbuyCount = spellbuyCount;
  }
  
  public Integer getProductPrice()
  {
    return this.productPrice;
  }
  
  public void setProductPrice(Integer productPrice)
  {
    this.productPrice = productPrice;
  }
  
  public Integer getWinUserId()
  {
    return this.winUserId;
  }
  
  public void setWinUserId(Integer winUserId)
  {
    this.winUserId = winUserId;
  }
  
  public Integer getWinId()
  {
    return this.winId;
  }
  
  public void setWinId(Integer winId)
  {
    this.winId = winId;
  }
  
  public String getWinDate()
  {
    return this.winDate;
  }
  
  public void setWinDate(String winDate)
  {
    this.winDate = winDate;
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
  
  public String getProductTitle()
  {
    return this.productTitle;
  }
  
  public void setProductTitle(String productTitle)
  {
    this.productTitle = productTitle;
  }
  
  public String getProductImg()
  {
    return this.productImg;
  }
  
  public void setProductImg(String productImg)
  {
    this.productImg = productImg;
  }
  
  public Integer getProductPeriod()
  {
    return this.productPeriod;
  }
  
  public void setProductPeriod(Integer productPeriod)
  {
    this.productPeriod = productPeriod;
  }
  
  public Integer getBuyStatus()
  {
    return this.buyStatus;
  }
  
  public void setBuyStatus(Integer buyStatus)
  {
    this.buyStatus = buyStatus;
  }
  
  public String getWinUser()
  {
    return this.winUser;
  }
  
  public void setWinUser(String winUser)
  {
    this.winUser = winUser;
  }
  
  public Long getBuyCount()
  {
    return this.buyCount;
  }
  
  public void setBuyCount(Object buyCount)
  {
    this.buyCount = Long.valueOf(Long.parseLong(buyCount.toString()));
  }
  
  public Integer getHistoryId()
  {
    return this.historyId;
  }
  
  public void setHistoryId(Integer historyId)
  {
    this.historyId = historyId;
  }
}
