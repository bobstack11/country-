package com.egouos.pojo;

public class RandomNumberJSON
{
  private String buyDate;
  private String buyCount;
  private String randomNumbers;
  
  public RandomNumberJSON() {}
  
  public RandomNumberJSON(String buyDate, String buyCount, String randomNumbers)
  {
    this.buyDate = buyDate;
    this.buyCount = buyCount;
    this.randomNumbers = randomNumbers;
  }
  
  public String getBuyCount()
  {
    return this.buyCount;
  }
  
  public void setBuyCount(String buyCount)
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
  
  public String getRandomNumbers()
  {
    return this.randomNumbers;
  }
  
  public void setRandomNumbers(String randomNumbers)
  {
    this.randomNumbers = randomNumbers;
  }
}
