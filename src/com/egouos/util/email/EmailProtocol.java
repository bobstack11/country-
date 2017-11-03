package com.egouos.util.email;

import com.egouos.pojo.SysConfigure;
import com.egouos.util.ApplicationListenerImpl;

public class EmailProtocol
{
  private String str;
  
  public EmailProtocol(String str)
  {
    this.str = str;
  }
  
  public String getPopProtocol()
  {
    String[] s = this.str.split("@");
    return "pop3." + s[1];
  }
  
  public String getSmtpProtocol()
  {
    return ApplicationListenerImpl.sysConfigureJson.getMailsmtp();
  }
}
