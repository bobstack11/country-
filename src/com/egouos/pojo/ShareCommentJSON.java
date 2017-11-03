package com.egouos.pojo;

public class ShareCommentJSON
{
  private Integer uid;
  private Integer shareInfoId;
  private Integer reCommentId;
  private Integer userId;
  private String userName;
  private String userFace;
  private String content;
  private String createDate;
  private Integer reCount;
  
  public ShareCommentJSON() {}
  
  public ShareCommentJSON(Integer uid, Integer shareInfoId, Integer reCommentId, Integer userId, String userName, String userFace, String content, String createDate, Integer reCount)
  {
    this.uid = uid;
    this.shareInfoId = shareInfoId;
    this.reCommentId = reCommentId;
    this.userId = userId;
    this.userName = userName;
    this.userFace = userFace;
    this.content = content;
    this.createDate = createDate;
    this.reCount = reCount;
  }
  
  public Integer getUid()
  {
    return this.uid;
  }
  
  public void setUid(Integer uid)
  {
    this.uid = uid;
  }
  
  public Integer getShareInfoId()
  {
    return this.shareInfoId;
  }
  
  public void setShareInfoId(Integer shareInfoId)
  {
    this.shareInfoId = shareInfoId;
  }
  
  public Integer getReCommentId()
  {
    return this.reCommentId;
  }
  
  public void setReCommentId(Integer reCommentId)
  {
    this.reCommentId = reCommentId;
  }
  
  public Integer getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public String getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(String createDate)
  {
    this.createDate = createDate;
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
  
  public Integer getReCount()
  {
    return this.reCount;
  }
  
  public void setReCount(Integer reCount)
  {
    this.reCount = reCount;
  }
}
