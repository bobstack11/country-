package com.egouos.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.stereotype.Component;

@Component("LogoutAction")
public class LogoutAction
  extends ActionSupport
{
  private static final long serialVersionUID = 5411610776024806651L;
  
  public String index()
  {
    return "logout";
  }
}
