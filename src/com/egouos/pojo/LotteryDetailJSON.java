package com.egouos.pojo;

import java.io.Serializable;

public class LotteryDetailJSON
  implements Serializable
{
  private Integer productId;
  private String productName;
  private String productTitle;
  private Integer productPeriod;
  private String userName;
  private Integer userId;
  private String buyTime;
  private Integer buyCount;
  private String buyDate;
  private String dateSum;
  
  public LotteryDetailJSON() {}
  
  public LotteryDetailJSON(Integer productId, String productName, String productTitle, Integer productPeriod, String userName, Integer userId, String buyTime, Integer buyCount, String buyDate, String dateSum)
  {
    this.productId = productId;
    this.productName = productName;
    this.productTitle = productTitle;
    this.productPeriod = productPeriod;
    this.userName = userName;
    this.userId = userId;
    this.buyTime = buyTime;
    this.buyCount = buyCount;
    this.buyDate = buyDate;
    this.dateSum = dateSum;
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
  
  public Integer getProductPeriod()
  {
    return this.productPeriod;
  }
  
  public void setProductPeriod(Integer productPeriod)
  {
    this.productPeriod = productPeriod;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public Integer getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }
  
  public String getBuyTime()
  {
    return this.buyTime;
  }
  
  public void setBuyTime(String buyTime)
  {
    this.buyTime = buyTime;
  }
  
  public Integer getBuyCount()
  {
    return this.buyCount;
  }
  
  public void setBuyCount(Integer buyCount)
  {
    this.buyCount = buyCount;
  }
  
  public String getBuyDate()
  {
    return this.buyDate;
  }
  
  public void setBuyDate(String buyDate)
  {
    this.buyDate = buyDate;
  }
  
  public String getDateSum()
  {
    return this.dateSum;
  }
  
  public void setDateSum(String dateSum)
  {
    this.dateSum = dateSum;
  }
}
