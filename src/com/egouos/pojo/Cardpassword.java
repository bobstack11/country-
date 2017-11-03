package com.egouos.pojo;

import java.io.Serializable;

public class Cardpassword
  implements Serializable
{
  private static final long serialVersionUID = 2626873670299057819L;
  private Integer id;
  private String randomNo;
  private String cardPwd;
  private Double money;
  private String date;
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getRandomNo()
  {
    return this.randomNo;
  }
  
  public void setRandomNo(String randomNo)
  {
    this.randomNo = randomNo;
  }
  
  public String getCardPwd()
  {
    return this.cardPwd;
  }
  
  public void setCardPwd(String cardPwd)
  {
    this.cardPwd = cardPwd;
  }
  
  public Double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(Double money)
  {
    this.money = money;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
}
