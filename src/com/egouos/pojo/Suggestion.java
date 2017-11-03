package com.egouos.pojo;

import java.io.Serializable;

public class Suggestion
  implements Serializable
{
  private Integer id;
  private String subject;
  private String userName;
  private String mobilePhone;
  private String mail;
  private String postText;
  
  public Suggestion() {}
  
  public Suggestion(String subject, String userName, String mobilePhone, String mail, String postText)
  {
    this.subject = subject;
    this.userName = userName;
    this.mobilePhone = mobilePhone;
    this.mail = mail;
    this.postText = postText;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public void setSubject(String subject)
  {
    this.subject = subject;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getMobilePhone()
  {
    return this.mobilePhone;
  }
  
  public void setMobilePhone(String mobilePhone)
  {
    this.mobilePhone = mobilePhone;
  }
  
  public String getMail()
  {
    return this.mail;
  }
  
  public void setMail(String mail)
  {
    this.mail = mail;
  }
  
  public String getPostText()
  {
    return this.postText;
  }
  
  public void setPostText(String postText)
  {
    this.postText = postText;
  }
}
