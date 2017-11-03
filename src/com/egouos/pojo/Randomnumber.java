package com.egouos.pojo;

import java.io.Serializable;

public class Randomnumber
  implements Serializable
{
  private Integer id;
  private Integer spellbuyrecordId;
  private Integer userId;
  private Integer productId;
  private String randomNumber;
  private String buyDate;
  
  public Randomnumber(Integer id, Integer spellbuyrecordId, Integer userId, Integer productId, String randomNumber, String buyDate)
  {
    this.id = id;
    this.spellbuyrecordId = spellbuyrecordId;
    this.userId = userId;
    this.productId = productId;
    this.randomNumber = randomNumber;
    this.buyDate = buyDate;
  }
  
  public Randomnumber() {}
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getSpellbuyrecordId()
  {
    return this.spellbuyrecordId;
  }
  
  public void setSpellbuyrecordId(Integer spellbuyrecordId)
  {
    this.spellbuyrecordId = spellbuyrecordId;
  }
  
  public String getRandomNumber()
  {
    return this.randomNumber;
  }
  
  public void setRandomNumber(String randomNumber)
  {
    this.randomNumber = randomNumber;
  }
  
  public Integer getProductId()
  {
    return this.productId;
  }
  
  public void setProductId(Integer productId)
  {
    this.productId = productId;
  }
  
  public String getBuyDate()
  {
    return this.buyDate;
  }
  
  public void setBuyDate(String buyDate)
  {
    this.buyDate = buyDate;
  }
  
  public Integer getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }
}
