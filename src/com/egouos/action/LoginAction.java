package com.egouos.action;

import com.egouos.pojo.SysConfigure;
import com.egouos.pojo.User;
import com.egouos.service.UserService;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.HTMLFilter;
import com.egouos.util.IPSeeker;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.mteams.commons.encrypt.digest.MessageDigestUtils;
import com.opensymphony.xwork2.ActionSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("LoginAction")
public class LoginAction
  extends ActionSupport
{
  private static final long serialVersionUID = -6356307819518359036L;
  private String userName;
  private String pwd;
  private String forward;
  private String shareId;
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  HttpServletRequest request = null;
  static HTMLFilter htmlFilter = new HTMLFilter();
  
  public String index()
  {
    try
    {
      if (StringUtil.isNotBlank(this.forward)) {
        this.forward = htmlFilter.filter(this.forward);
      }
      this.request = Struts2Utils.getRequest();
      Cookie[] cookies = this.request.getCookies();
      if (this.request.isRequestedSessionIdFromCookie()) {
        for (int i = 0; i < cookies.length; i++)
        {
          Cookie cookie = cookies[i];
          if (cookie.getName().equals("userId"))
          {
            String userId = cookie.getValue();
            if ((userId != null) && (!userId.equals(""))) {
              return "index_index";
            }
          }
        }
      } else {
        Struts2Utils.render("text/html", "<script>alert(\"您的浏览器未开启Cookie功能,无法保存购物信息,请先开启Cookie功能后继续购物！\");window.location.href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/help/openCookie.html\";</script>", new String[] { "encoding:UTF-8" });
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "index";
  }
  
  public String login()
  {
    this.userName = htmlFilter.filter(this.userName);
    this.pwd = htmlFilter.filter(this.pwd);
    try {
		this.pwd = URLDecoder.decode(this.pwd, "utf-8");
		this.pwd = MessageDigestUtils.sha256Hex(this.pwd);
	} catch (UnsupportedEncodingException e1) {
		e1.printStackTrace();
	}
    User user = this.userService.userByName(this.userName);
    
    String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
    if (ip == null) {
      ip = "127.0.0.1";
    }
    String date = DateUtil.DateTimeToStr(new Date());
    if (user != null)
    {
      if (this.userName.indexOf("@") != -1)
      {
        User u1 = this.userService.mailLogin(this.userName, this.pwd);
        if (u1 != null) {
          try
          {
            if (u1.getMailCheck().equals("0"))
            {
              Struts2Utils.renderJson(u1, new String[0]);
              user.setIpAddress(ip);
              String ipLocation = RegisterAction.seeker.getAddress(ip);
              user.setIpLocation(ipLocation);
              
              user.setNewDate(date);
              this.userService.add(user);
            }
            else
            {
              Struts2Utils.renderText("check", new String[0]);
            }
          }
          catch (Exception e)
          {
            Struts2Utils.renderText("check", new String[0]);
            e.printStackTrace();
          }
        } else {
          Struts2Utils.renderText("pwdError", new String[0]);
        }
      }
      else
      {
        User u2 = this.userService.phoneLogin(this.userName, this.pwd);
        if (u2 != null) {
          try
          {
            if (u2.getMobileCheck().equals("0"))
            {
              Struts2Utils.renderJson(u2, new String[0]);
              user.setIpAddress(ip);
              String ipLocation = RegisterAction.seeker.getAddress(ip);
              user.setIpLocation(ipLocation);
              
              user.setNewDate(date);
              this.userService.add(user);
            }
            else
            {
              Struts2Utils.renderText("check", new String[0]);
            }
          }
          catch (Exception e)
          {
            Struts2Utils.renderText("check", new String[0]);
            e.printStackTrace();
          }
        } else {
          Struts2Utils.renderText("pwdError", new String[0]);
        }
      }
    }
    else {
      Struts2Utils.renderText("userError", new String[0]);
    }
    return null;
  }
  
  public String fastLogin()
  {
    return "fastLogin";
  }
  
  public String popLogin()
  {
    return "popLogin";
  }
  
  public String buyCartLogin()
  {
    return "buyCartLogin";
  }
  
  public String postCommentLogin()
  {
    return "postCommentLogin";
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getPwd()
  {
    return this.pwd;
  }
  
  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }
  
  public String getForward()
  {
    return this.forward;
  }
  
  public void setForward(String forward)
  {
    this.forward = forward;
  }
  
  public String getShareId()
  {
    return this.shareId;
  }
  
  public void setShareId(String shareId)
  {
    this.shareId = shareId;
  }
}
