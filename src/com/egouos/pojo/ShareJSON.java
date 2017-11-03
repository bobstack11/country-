package com.egouos.pojo;

import java.io.Serializable;
import java.util.List;

public class ShareJSON
  implements Serializable
{
  private static final long serialVersionUID = -8825464421098017421L;
  private Integer uid;
  private String shareTitle;
  private String shareContent;
  private List<Shareimage> shareimageList;
  private String shareImages;
  private Integer upCount;
  private Integer replyCount;
  private Integer reward;
  private String shareDate;
  private String announcedTime;
  private String userName;
  private String userFace;
  private String userId;
  private Integer status;
  
  public ShareJSON() {}
  
  public ShareJSON(Integer uid, String shareTitle, String shareContent, List<Shareimage> shareimageList, String shareImages, Integer upCount, Integer replyCount, Integer reward, String shareDate, String announcedTime, String userName, String userFace, String userId, Integer status)
  {
    this.uid = uid;
    this.shareTitle = shareTitle;
    this.shareContent = shareContent;
    this.shareimageList = shareimageList;
    this.shareImages = shareImages;
    this.upCount = upCount;
    this.replyCount = replyCount;
    this.reward = reward;
    this.shareDate = shareDate;
    this.announcedTime = announcedTime;
    this.userName = userName;
    this.userFace = userFace;
    this.userId = userId;
    this.status = status;
  }
  
  public Integer getUid()
  {
    return this.uid;
  }
  
  public void setUid(Integer uid)
  {
    this.uid = uid;
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
  
  public String getShareImages()
  {
    return this.shareImages;
  }
  
  public void setShareImages(String shareImages)
  {
    this.shareImages = shareImages;
  }
  
  public Integer getUpCount()
  {
    return this.upCount;
  }
  
  public void setUpCount(Integer upCount)
  {
    this.upCount = upCount;
  }
  
  public Integer getReplyCount()
  {
    return this.replyCount;
  }
  
  public void setReplyCount(Integer replyCount)
  {
    this.replyCount = replyCount;
  }
  
  public Integer getReward()
  {
    return this.reward;
  }
  
  public void setReward(Integer reward)
  {
    this.reward = reward;
  }
  
  public String getShareDate()
  {
    return this.shareDate;
  }
  
  public void setShareDate(String shareDate)
  {
    this.shareDate = shareDate;
  }
  
  public String getAnnouncedTime()
  {
    return this.announcedTime;
  }
  
  public void setAnnouncedTime(String announcedTime)
  {
    this.announcedTime = announcedTime;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getUserFace()
  {
    return this.userFace;
  }
  
  public void setUserFace(String userFace)
  {
    this.userFace = userFace;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public static long getSerialVersionUID()
  {
    return -8825464421098017421L;
  }
  
  public List<Shareimage> getShareimageList()
  {
    return this.shareimageList;
  }
  
  public void setShareimageList(List<Shareimage> shareimageList)
  {
    this.shareimageList = shareimageList;
  }
}
