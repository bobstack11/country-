package com.egouos.action;

import com.egouos.pojo.SysConfigure;
import com.egouos.pojo.User;
import com.egouos.service.UserService;
import com.egouos.sms.SmsSenderFactory;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.Base64;
import com.egouos.util.DateUtil;
import com.egouos.util.EmailUtil;
import com.egouos.util.HTMLFilter;
import com.egouos.util.IPSeeker;
import com.egouos.util.MD5Util;
import com.egouos.util.Sampler;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.mteams.commons.encrypt.digest.MessageDigestUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.shcm.bean.SendResultBean;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("RegsiterAction")
public class RegisterAction
  extends ActionSupport
{
  private static final long serialVersionUID = 5054777863371691520L;
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  private User user;
  private String phone;
  private String mail;
  private String userPwd;
  private String userName;
  private String forward;
  private String str;
  private String isVerify;
  private String key;
  private String date;
  private String openId;
  private String userFace;
  public static IPSeeker seeker = new IPSeeker();
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  static HTMLFilter htmlFilter = new HTMLFilter();
  
  public String index()
  {
    /*if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>");
      return null;
    }*/
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
    }
    return "index";
  }
  
  public String regsiter()
  {
    this.str = htmlFilter.filter(this.str);
    this.userPwd = htmlFilter.filter(this.userPwd);
    try {
		this.userPwd = URLDecoder.decode(this.userPwd, "utf-8");
		this.userPwd = MessageDigestUtils.sha256Hex(this.userPwd);
	} catch (UnsupportedEncodingException e1) {
		e1.printStackTrace();
	}
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    this.user = new User();
    
    String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
    if (ip == null) {
      ip = "127.0.0.1";
    }
    String date = DateUtil.DateTimeToStr(new Date());
    if (this.str.indexOf("@") != -1)
    {
      this.user.setMail(this.str);
      this.user.setMailCheck("3");
      this.user.setMobileCheck("3");
      if ((this.userPwd != null) && (!this.userPwd.equals(""))) {
        this.user.setUserPwd(this.userPwd);
      }
      this.user.setIpAddress(ip);
      String ipLocation = seeker.getAddress(ip);
      this.user.setIpLocation(ipLocation);
      this.user.setOldDate(date);
      this.user.setNewDate(date);
      this.user.setBalance(ApplicationListenerImpl.sysConfigureJson.getRegBalance());
      this.user.setCommissionBalance(Double.valueOf(0.0D));
      this.user.setCommissionCount(Double.valueOf(0.0D));
      this.user.setCommissionMention(Double.valueOf(0.0D));
      this.user.setCommissionPoints(Integer.valueOf(0));
      this.user.setFaceImg("/Images/defaultUserFace.png");
      this.user.setUserType("0");
      this.user.setExperience(Integer.valueOf(0));
      if (this.request.isRequestedSessionIdFromCookie()) {
        for (int i = 0; i < cookies.length; i++)
        {
          Cookie cookie = cookies[i];
          if (cookie.getName().equals("inviteId"))
          {
            String inviteId = cookie.getValue();
            if ((inviteId != null) && (!inviteId.equals("")))
            {
              this.user.setInvite(Integer.valueOf(Integer.parseInt(inviteId)));
              User user2 = (User)this.userService.findById(inviteId);
              int commissionPoints = user2.getCommissionPoints().intValue();
              commissionPoints += ApplicationListenerImpl.sysConfigureJson.getInvite().intValue();
              user2.setCommissionPoints(Integer.valueOf(commissionPoints));
              this.userService.add(user2);
            }
          }
        }
      }
      try
      {
        this.userService.add(this.user);
        Struts2Utils.renderText("true");
      }
      catch (Exception e)
      {
        e.printStackTrace();
        Struts2Utils.renderText("false");
      }
    }
    else if ((this.userPwd != null) && (!this.userPwd.equals("")))
    {
      AliyunOcsSampleHelp.getIMemcachedCache().set(this.str, 600, this.userPwd);
      Struts2Utils.renderText("true");
    }
    else
    {
      Struts2Utils.renderText("false");
    }
    return null;
  }
  
  public String mobilecheck()
  {
    if (AliyunOcsSampleHelp.getIMemcachedCache().get(this.phone) == null)
    {
      Struts2Utils.render("text/html", "<script>alert(\"该手机号没有注册,请注册！\");window.location.href=\"/register/index.html\";</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    this.isVerify = Base64.getEncode(this.phone);
    
    return "mobilecheck";
  }
  
  public void regSendMes()
    throws Exception
  {
    Random random = new Random();
    String ran = "";
    for (int i = 0; i < 6; i++) {
      ran = ran + random.nextInt(9);
    }
    if (this.isVerify.equals(Base64.getEncode(this.phone)))
    {
      if (AliyunOcsSampleHelp.getIMemcachedCache().get(Base64.getEncode(this.phone)) == null) {
        try
        {
        	String content = ApplicationListenerImpl.sysConfigureJson.getVerifyMsgTpl().replace("{000000}", ran);
        	boolean success = SmsSenderFactory.create().send(phone, content);
        	if(success){
        		AliyunOcsSampleHelp.getIMemcachedCache().set(Base64.getEncode(this.phone), 120, ran);
                Struts2Utils.renderText("0");
        	}else{
        		Struts2Utils.renderText("false");
                return;
        	}
        }
        catch (Exception e)
        {
          e.printStackTrace();
          Struts2Utils.renderText("error");
        }
      } else {
        Struts2Utils.renderText("2");
      }
    }
    else {
      Struts2Utils.renderText("error");
    }
  }
  
  public void checkMobileCode()
  {
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    this.user = new User();
    
    String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
    if (ip == null) {
      ip = "127.0.0.1";
    }
    String date = DateUtil.DateTimeToStr(new Date());
    try
    {
      if (AliyunOcsSampleHelp.getIMemcachedCache().get(this.isVerify) != null) {
        if (AliyunOcsSampleHelp.getIMemcachedCache().get(this.isVerify).equals(this.key)) {
          Struts2Utils.renderText("0");
        } else {
          Struts2Utils.renderText("1");
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Struts2Utils.renderText("false");
    }
  }
  
  public void mobileok()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    Cookie[] cookies = this.request.getCookies();
    
    String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
    if (ip == null) {
      ip = "127.0.0.1";
    }
    String date = DateUtil.DateTimeToStr(new Date());
    if (AliyunOcsSampleHelp.getIMemcachedCache().get(this.isVerify) != null) {
      try
      {
        phone = Base64.getDecode(this.isVerify);
        user = userService.userByName(phone);
        if(user==null){
        	this.user = new User();
            this.user.setPhone(phone);
            this.user.setMailCheck("3");
            this.user.setMobileCheck("0");
            if ((this.userPwd != null) && (!this.userPwd.equals(""))) {
            	this.userPwd = URLDecoder.decode(this.userPwd, "utf-8");
        		this.userPwd = MessageDigestUtils.sha256Hex(this.userPwd);
              this.user.setUserPwd(this.userPwd);
            }
            this.user.setIpAddress(ip);
            String ipLocation = seeker.getAddress(ip);
            this.user.setIpLocation(ipLocation);
            this.user.setOldDate(date);
            this.user.setNewDate(date);
            this.user.setBalance(ApplicationListenerImpl.sysConfigureJson.getRegBalance());
            this.user.setCommissionBalance(Double.valueOf(0.0D));
            this.user.setCommissionCount(Double.valueOf(0.0D));
            this.user.setCommissionMention(Double.valueOf(0.0D));
            this.user.setCommissionPoints(Integer.valueOf(0));
            this.user.setFaceImg("/Images/defaultUserFace.png");
            this.user.setUserType("0");
            this.user.setExperience(Integer.valueOf(0));
            if (this.request.isRequestedSessionIdFromCookie()) {
              for (int i = 0; i < cookies.length; i++)
              {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("inviteId"))
                {
                  String inviteId = cookie.getValue();
                  if ((inviteId != null) && (!inviteId.equals(""))) {
                    this.user.setInvite(Integer.valueOf(Integer.parseInt(inviteId)));
                  }
                }
              }
            }
            this.userService.add(this.user);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    if (this.user != null)
    {
      if (this.user.getMobileCheck().equals("0"))
      {
        this.isVerify = "0";
        if (this.request.isRequestedSessionIdFromCookie())
        {
          Cookie cookie = new Cookie("userName", this.user.getPhone());
          cookie.setMaxAge(31536000);
          cookie.setPath("/");
          cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
          this.response.addCookie(cookie);
          Cookie cookie2 = new Cookie("userId", String.valueOf(this.user.getUserId()));
          cookie2.setMaxAge(31536000);
          cookie2.setPath("/");
          cookie2.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
          this.response.addCookie(cookie2);
          Cookie cookie3 = new Cookie("face", URLEncoder.encode(this.user.getFaceImg(), "UTF-8"));
          cookie3.setMaxAge(31536000);
          cookie3.setPath("/");
          cookie3.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
          this.response.addCookie(cookie3);
          Cookie cookie4 = new Cookie("loginUser", this.user.getPhone());
          cookie4.setMaxAge(31536000);
          cookie4.setPath("/");
          cookie4.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
          this.response.addCookie(cookie4);
        }
      }
      else
      {
        this.isVerify = "1";
      }
    }
    else {
      this.isVerify = "1";
    }
    Struts2Utils.renderText(this.isVerify);
  }
  
	
  
  public String emailcheck()
  {
    if (AliyunOcsSampleHelp.getIMemcachedCache().get(this.phone) == null)
    {
      Struts2Utils.render("text/html", "<script>alert(\"该手机号没有注册,请注册！\");window.location.href=\"/register/index.html\";</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    this.isVerify = Base64.getEncode(this.phone);
    
    return "emailcheck";
  }
  
  public void SendRegisterMail()
  {
    this.key = (MD5Util.encode(this.mail) + MD5Util.encode(DateUtil.dateTimeToStr(new Date())) + Base64.getEncode(this.mail));
    String html = "<table width=\"600\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: #dddddd 1px solid; padding: 20px 0;\"><tbody><tr><td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"border-bottom: #ff6600 2px solid; padding-bottom: 12px;\"><tbody><tr><td style=\"line-height: 22px; padding-left: 20px;\"><a target=\"_blank\" title=\"" + 
    
      ApplicationListenerImpl.sysConfigureJson.getSaitName() + "\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "\"><img width=\"230px\" border=\"0\" height=\"52\" src=\"" + ApplicationListenerImpl.sysConfigureJson.getImgUrl() + "/Images/mail_logo.gif\"></a></td>" + 
      "<td align=\"right\" style=\"font-size: 12px; padding-right: 20px; padding-top: 30px;\"><a style=\"color: #22aaff; text-decoration: none;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "\">首页</a><b style=\"width: 1px; height: 10px; vertical-align: -1px; font-size: 1px; background: #CACACA; display: inline-block; margin: 0 5px;\"></b>" + 
      "<a style=\"color: #22aaff; text-decoration: none;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/user/index.html\">我的" + ApplicationListenerImpl.sysConfigureJson.getSaitName() + "</a><b style=\"width: 1px; height: 10px; vertical-align: -1px; font-size: 1px; background: #CACACA; display: inline-block; margin: 0 5px;\"></b><a style=\"color: #22aaff; text-decoration: none;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/help/index.html\">帮助</a></td>" + 
      "</tr>" + 
      "</tbody></table>" + 
      "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"padding: 0 20px;\">" + 
      "<tbody><tr>" + 
      "<td style=\"font-size: 14px; color: #333333; height: 40px; line-height: 40px; padding-top: 10px;\">亲爱的 <b style=\"color: #333333; font-family: Arial;\"><a href=\"mailto:" + this.mail + "\" target=\"_blank\">" + this.mail + "</a></b>：</td>" + 
      "</tr>" + 
      "<tr>" + 
      "<td style=\"font-size: 12px; color: #333333; line-height: 22px;\"><p style=\"text-indent: 2em; padding: 0; margin: 0;\">您好！感谢您注册" + ApplicationListenerImpl.sysConfigureJson.getSaitName() + "。</p></td>" + 
      "</tr>" + 
      "<tr>" + 
      "<td style=\"font-size: 12px; color: #333333; line-height: 22px;\"><p style=\"text-indent: 2em; padding: 0; margin: 0;\">请点击下面的按钮，完成邮箱验证。</p></td>" + 
      "</tr>" + 
      "<tr>" + 
      "<td style=\"padding-top: 15px; padding-left: 28px;\"><a title=\"邮箱验证\" style=\"display: inline-block; padding: 0 25px; height: 28px; line-height: 28px; text-align: center; color: #ffffff; background: #ff7700; font-size: 12px; cursor: pointer; border-radius: 2px; text-decoration: none;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/register/emailok.html?key=" + this.key + "\">邮箱验证</a></td>" + 
      "</tr>" + 
      "<tr>" + 
      "<td width=\"525\" style=\"font-size: 12px; color: #333333; line-height: 22px; padding-top: 20px;\">如果上面按钮不能点击或点击后没有反应，您还可以将以下链接复制到浏览器地址栏中访问完成验证。</td>" + 
      "</tr>" + 
      "<tr>" + 
      "<td width=\"525\" style=\"font-size: 12px; padding-top: 5px; word-break: break-all; word-wrap: break-word;\"><a style=\"font-family: Arial; color: #22aaff;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/register/emailok.html?key=" + this.key + "\">" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/register/emailok.html?key=" + this.key + "</a></td>" + 
      "</tr>" + 
      "</tbody></table>" + 
      "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"margin-top: 60px;\">" + 
      "<tbody><tr>" + 
      "<td style=\"font-size: 12px; color: #777777; line-height: 22px; border-bottom: #22aaff 2px solid; padding-bottom: 8px; padding-left: 20px;\">此邮件由系统自动发出，请勿回复！</td>" + 
      "</tr>" + 
      "<tr>" + 
      "<td style=\"font-size: 12px; color: #333333; line-height: 22px; padding: 8px 20px 0;\">感谢您对" + ApplicationListenerImpl.sysConfigureJson.getSaitName() + "（<a style=\"color: #22aaff; font-family: Arial;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "\">" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "</a>）的支持，祝您好运！</td>" + 
      "</tr>" + 
      "<tr>" + 
      "<td style=\"font-size: 12px; color: #333333; line-height: 22px; padding: 0 20px;\">客服热线：<b style=\"color: #ff6600; font-family: Arial;\">" + ApplicationListenerImpl.sysConfigureJson.getServiceTel() + "</b></td>" + 
      "</tr>" + 
      "</tbody></table>" + 
      "</td>" + 
      "</tr>" + 
      "</tbody></table>" + 
      "<table width=\"600\" cellspacing=\"0\" cellpadding=\"0\">" + 
      "<tbody><tr>" + 
      "<td align=\"center\" style=\"font-size:12px; color:#bbbbbb; padding-top:10px;\">" + ApplicationListenerImpl.sysConfigureJson.getIcp() + "</td>" + 
      "</tr>" + 
      "</tbody></table>";
    if (AliyunOcsSampleHelp.getIMemcachedCache().get(MD5Util.encode(this.mail)) == null)
    {
      this.user = this.userService.userByName(this.mail);
      if (this.user != null) {
        if (this.user.getMailCheck().equals("0")) {
          Struts2Utils.renderText("0");
        } else {
          try
          {
            boolean flag = EmailUtil.sendEmail(ApplicationListenerImpl.sysConfigureJson.getMailName(), ApplicationListenerImpl.sysConfigureJson.getMailPwd(), this.mail, ApplicationListenerImpl.sysConfigureJson.getSaitName() + "验证注册邮箱", html);
            if (flag)
            {
              this.user.setMailCheck("1");
              this.userService.add(this.user);
              AliyunOcsSampleHelp.getIMemcachedCache().set(MD5Util.encode(this.mail), 600, this.mail);
              Struts2Utils.renderText("2");
            }
            else
            {
              Struts2Utils.renderText("false");
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
            Struts2Utils.renderText("false");
          }
        }
      }
    }
    else
    {
      this.user = this.userService.userByName(this.mail);
      if (this.user != null) {
        if (this.user.getMailCheck().equals("1")) {
          Struts2Utils.renderText("3");
        } else if (this.user.getMailCheck().equals("0")) {
          Struts2Utils.renderText("0");
        }
      }
    }
  }
  
  public static void main(String[] args)
  {
    Random random = new Random();
    String ran = "";
    for (int i = 0; i < 6; i++) {
      ran = ran + random.nextInt(9);
    }
    System.err.println(ran);
  }
  
  public String emailok()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    if (StringUtil.isNotBlank(this.key))
    {
      this.key = this.key.substring(64, this.key.length());
      this.mail = Base64.getDecode(this.key);
      if (AliyunOcsSampleHelp.getIMemcachedCache().get(MD5Util.encode(this.mail)) != null)
      {
        if (StringUtil.isNotBlank(this.mail))
        {
          this.user = this.userService.userByName(this.mail);
          if (this.user != null)
          {
            if (!this.user.getMailCheck().equals("0"))
            {
              if (this.request.isRequestedSessionIdFromCookie())
              {
                Cookie cookie = new Cookie("userName", this.user.getMail());
                cookie.setMaxAge(-1);
                cookie.setPath("/");
                cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
                this.response.addCookie(cookie);
                Cookie cookie2 = new Cookie("userId", String.valueOf(this.user.getUserId()));
                cookie2.setMaxAge(-1);
                cookie2.setPath("/");
                cookie2.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
                this.response.addCookie(cookie2);
                Cookie cookie3 = new Cookie("face", URLEncoder.encode(this.user.getFaceImg(), "UTF-8"));
                cookie3.setMaxAge(-1);
                cookie3.setPath("/");
                cookie3.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
                this.response.addCookie(cookie3);
                this.user.setMailCheck("0");
                this.userService.add(this.user);
                this.isVerify = "0";
              }
            }
            else {
              this.isVerify = "0";
            }
          }
          else {
            this.isVerify = "1";
          }
        }
        else
        {
          this.isVerify = "1";
        }
      }
      else {
        this.isVerify = "1";
      }
    }
    return "emailok";
  }
  
  public void authorizeRegsiter()
  {
    this.user = new User();
    
    String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
    String date = DateUtil.DateTimeToStr(new Date());
    if ((this.userName != null) && (!this.userName.equals(""))) {
      this.user.setUserName(this.userName.trim());
    }
    this.user.setUserPwd(this.openId);
    this.user.setMobileCheck("3");
    this.user.setMailCheck("3");
    this.user.setAttribute22(this.openId);
    if (ip == null) {
      ip = "127.0.0.1";
    }
    this.user.setIpAddress(ip);
    String ipLocation = seeker.getAddress(ip);
    this.user.setIpLocation(ipLocation);
    this.user.setOldDate(date);
    
    this.user.setBalance(ApplicationListenerImpl.sysConfigureJson.getRegBalance());
    this.user.setCommissionBalance(Double.valueOf(0.0D));
    this.user.setCommissionCount(Double.valueOf(0.0D));
    this.user.setCommissionMention(Double.valueOf(0.0D));
    this.user.setCommissionPoints(Integer.valueOf(0));
    this.user.setFaceImg(this.userFace);
    this.user.setUserType("0");
    this.user.setExperience(Integer.valueOf(0));
    try
    {
      this.userService.add(this.user);
      Struts2Utils.renderJson(this.user);
    }
    catch (Exception e)
    {
      Struts2Utils.renderText("false");
      e.printStackTrace();
    }
  }
  
  public void authorizeIsExists()
  {
    this.user = this.userService.isNotOpenId(this.openId);
    if (this.user == null)
    {
      Struts2Utils.renderText("true");
    }
    else
    {
      if (this.user.getUserPwd().length() > 30)
      {
        this.user.setAttribute22(this.user.getUserPwd());
        this.userService.add(this.user);
      }
      Struts2Utils.renderJson(this.user);
    }
  }
  
  public String isExists()
  {
    this.user = this.userService.userByName(this.userName);
    if (this.user == null)
    {
      AliyunOcsSampleHelp.getIMemcachedCache().set(this.userName, 600, this.userName);
      Struts2Utils.renderText("true");
    }
    else
    {
      Struts2Utils.renderText("false");
    }
    return null;
  }
  
  public String qqUserInfoAuth()
  {
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId"))
        {
          String userId = cookie.getValue();
          if ((userId != null) && (!userId.equals("")))
          {
            this.user = ((User)this.userService.findById(userId));
            if ((this.user.getMailCheck().equals("0")) || (this.user.getMailCheck().equals("0"))) {
              return "index_index";
            }
            return "qqUserInfoAuth";
          }
        }
      }
    }
    return null;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public String getMail()
  {
    return this.mail;
  }
  
  public void setMail(String mail)
  {
    this.mail = mail;
  }
  
  public String getUserPwd()
  {
    return this.userPwd;
  }
  
  public void setUserPwd(String userPwd)
  {
    this.userPwd = userPwd;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getForward()
  {
    return this.forward;
  }
  
  public void setForward(String forward)
  {
    this.forward = forward;
  }
  
  public String getStr()
  {
    return this.str;
  }
  
  public void setStr(String str)
  {
    this.str = str;
  }
  
  public String getIsVerify()
  {
    return this.isVerify;
  }
  
  public void setIsVerify(String isVerify)
  {
    this.isVerify = isVerify;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String getOpenId()
  {
    return this.openId;
  }
  
  public void setOpenId(String openId)
  {
    this.openId = openId;
  }
  
  public String getUserFace()
  {
    return this.userFace;
  }
  
  public void setUserFace(String userFace)
  {
    this.userFace = userFace;
  }
}
