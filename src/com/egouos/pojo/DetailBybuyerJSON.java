package com.egouos.pojo;

public class DetailBybuyerJSON
{
  private String userId;
  private String userName;
  private String buyTime;
  private String faceImg;
  private String randomNumber;
  private String buyCount;
  
  public DetailBybuyerJSON() {}
  
  public DetailBybuyerJSON(String userId, String userName, String buyTime, String faceImg, String randomNumber, String buyCount)
  {
    this.userId = userId;
    this.userName = userName;
    this.buyTime = buyTime;
    this.faceImg = faceImg;
    this.randomNumber = randomNumber;
    this.buyCount = buyCount;
  }
  
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
  
  public String getBuyTime()
  {
    return this.buyTime;
  }
  
  public void setBuyTime(String buyTime)
  {
    this.buyTime = buyTime;
  }
  
  public String getFaceImg()
  {
    return this.faceImg;
  }
  
  public void setFaceImg(String faceImg)
  {
    this.faceImg = faceImg;
  }
  
  public String getRandomNumber()
  {
    return this.randomNumber;
  }
  
  public void setRandomNumber(String randomNumber)
  {
    this.randomNumber = randomNumber;
  }
  
  public String getBuyCount()
  {
    return this.buyCount;
  }
  
  public void setBuyCount(String buyCount)
  {
    this.buyCount = buyCount;
  }
}
