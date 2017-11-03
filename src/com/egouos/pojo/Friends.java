package com.egouos.pojo;

import java.io.Serializable;

public class Friends
  implements Serializable
{
  private Integer id;
  private Integer friendsId;
  private Integer userId;
  
  public Friends() {}
  
  public Friends(Integer friendsId, Integer userId)
  {
    this.friendsId = friendsId;
    this.userId = userId;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getFriendsId()
  {
    return this.friendsId;
  }
  
  public void setFriendsId(Integer friendsId)
  {
    this.friendsId = friendsId;
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
