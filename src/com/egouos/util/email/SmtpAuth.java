package com.egouos.util.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuth
  extends Authenticator
{
  private String user;
  private String password;
  
  public void setUserinfo(String getuser, String getpassword)
  {
    this.user = getuser;
    this.password = getpassword;
  }
  
  protected PasswordAuthentication getPasswordAuthentication()
  {
    return new PasswordAuthentication(this.user, this.password);
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public void setUser(String user)
  {
    this.user = user;
  }
}
