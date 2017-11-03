package com.egouos.pojo;

public class CommissionqueryJSON
{
  private String userId;
  private String userName;
  private String date;
  private String description;
  private Double buyMoney;
  private Double commission;
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
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
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public Double getBuyMoney()
  {
    return this.buyMoney;
  }
  
  public void setBuyMoney(Double buyMoney)
  {
    this.buyMoney = buyMoney;
  }
  
  public Double getCommission()
  {
    return this.commission;
  }
  
  public void setCommission(Double commission)
  {
    this.commission = commission;
  }
}
