package com.egouos.pojo;

import java.io.Serializable;

public class ShareInfoPro
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String shareId;
  private String shareTitle;
  private String shareContent;
  private String shareDate;
  private String shareimageList;
  private int upCount;
  private int replyCount;
  private int reward;
  private String productId;
  private String spellbuyProductId;
  private String productName;
  private String productTitle;
  private String productPrice;
  private String productPeriod;
  private String winRandomNumber;
  private String productImg;
  private String winUserName;
  private String winUserFace;
  private String buyNumberCount;
  private String announcedTime;
  private String userId;
  private int resultSize;
  
  public ShareInfoPro() {}
  
  public ShareInfoPro(String shareId, String shareTitle, String shareContent, String shareDate, String shareimageList, int upCount, int replyCount, int reward, String productId, String spellbuyProductId, String productName, String productTitle, String productPrice, String productPeriod, String winRandomNumber, String productImg, String winUserName, String winUserFace, String buyNumberCount, String announcedTime, String userId, int resultSize)
  {
    this.shareId = shareId;
    this.shareTitle = shareTitle;
    this.shareContent = shareContent;
    this.shareDate = shareDate;
    this.shareimageList = shareimageList;
    this.upCount = upCount;
    this.replyCount = replyCount;
    this.reward = reward;
    this.productId = productId;
    this.spellbuyProductId = spellbuyProductId;
    this.productName = productName;
    this.productTitle = productTitle;
    this.productPrice = productPrice;
    this.productPeriod = productPeriod;
    this.winRandomNumber = winRandomNumber;
    this.productImg = productImg;
    this.winUserName = winUserName;
    this.winUserFace = winUserFace;
    this.buyNumberCount = buyNumberCount;
    this.announcedTime = announcedTime;
    this.userId = userId;
    this.resultSize = resultSize;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getShareId()
  {
    return this.shareId;
  }
  
  public void setShareId(String shareId)
  {
    this.shareId = shareId;
  }
  
  public String getShareTitle()
  {
    return this.shareTitle;
  }
  
  public void setShareTitle(String shareTitle)
  {
    this.shareTitle = shareTitle;
  }
  
  public String getShareContent()
  {
    return this.shareContent;
  }
  
  public void setShareContent(String shareContent)
  {
    this.shareContent = shareContent;
  }
  
  public String getShareDate()
  {
    return this.shareDate;
  }
  
  public void setShareDate(String shareDate)
  {
    this.shareDate = shareDate;
  }
  
  public String getShareimageList()
  {
    return this.shareimageList;
  }
  
  public void setShareimageList(String shareimageList)
  {
    this.shareimageList = shareimageList;
  }
  
  public int getUpCount()
  {
    return this.upCount;
  }
  
  public void setUpCount(int upCount)
  {
    this.upCount = upCount;
  }
  
  public int getReward()
  {
    return this.reward;
  }
  
  public void setReward(int reward)
  {
    this.reward = reward;
  }
  
  public String getProductId()
  {
    return this.productId;
  }
  
  public void setProductId(String productId)
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
  
  public String getProductPrice()
  {
    return this.productPrice;
  }
  
  public void setProductPrice(String productPrice)
  {
    this.productPrice = productPrice;
  }
  
  public String getWinRandomNumber()
  {
    return this.winRandomNumber;
  }
  
  public void setWinRandomNumber(String winRandomNumber)
  {
    this.winRandomNumber = winRandomNumber;
  }
  
  public String getProductImg()
  {
    return this.productImg;
  }
  
  public void setProductImg(String productImg)
  {
    this.productImg = productImg;
  }
  
  public String getWinUserName()
  {
    return this.winUserName;
  }
  
  public void setWinUserName(String winUserName)
  {
    this.winUserName = winUserName;
  }
  
  public String getBuyNumberCount()
  {
    return this.buyNumberCount;
  }
  
  public void setBuyNumberCount(String buyNumberCount)
  {
    this.buyNumberCount = buyNumberCount;
  }
  
  public String getAnnouncedTime()
  {
    return this.announcedTime;
  }
  
  public void setAnnouncedTime(String announcedTime)
  {
    this.announcedTime = announcedTime;
  }
  
  public int getResultSize()
  {
    return this.resultSize;
  }
  
  public void setResultSize(int resultSize)
  {
    this.resultSize = resultSize;
  }
  
  public String getSpellbuyProductId()
  {
    return this.spellbuyProductId;
  }
  
  public void setSpellbuyProductId(String spellbuyProductId)
  {
    this.spellbuyProductId = spellbuyProductId;
  }
  
  public String getProductPeriod()
  {
    return this.productPeriod;
  }
  
  public void setProductPeriod(String productPeriod)
  {
    this.productPeriod = productPeriod;
  }
  
  public String getWinUserFace()
  {
    return this.winUserFace;
  }
  
  public void setWinUserFace(String winUserFace)
  {
    this.winUserFace = winUserFace;
  }
  
  public int getReplyCount()
  {
    return this.replyCount;
  }
  
  public void setReplyCount(int replyCount)
  {
    this.replyCount = replyCount;
  }
}
