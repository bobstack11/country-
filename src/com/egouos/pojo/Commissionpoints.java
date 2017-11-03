package com.egouos.pojo;

import java.io.Serializable;

public class Commissionpoints
  implements Serializable
{
  private static final long serialVersionUID = 5429099233990126477L;
  private Integer id;
  private Integer toUserId;
  private String date;
  private String pay;
  private String detailed;
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getToUserId()
  {
    return this.toUserId;
  }
  
  public void setToUserId(Integer toUserId)
  {
    this.toUserId = toUserId;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String getPay()
  {
    return this.pay;
  }
  
  public void setPay(String pay)
  {
    this.pay = pay;
  }
  
  public String getDetailed()
  {
    return this.detailed;
  }
  
  public void setDetailed(String detailed)
  {
    this.detailed = detailed;
  }
}
