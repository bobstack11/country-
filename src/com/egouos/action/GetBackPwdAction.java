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
import com.egouos.util.MD5Util;
import com.egouos.util.RandomValidateCode;
import com.egouos.util.Sampler;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.mteams.commons.encrypt.digest.MessageDigestUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.shcm.bean.SendResultBean;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
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

@Component("GetBackPwdAction")
public class GetBackPwdAction
  extends ActionSupport
{
  private static final long serialVersionUID = 686157280940778943L;
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  private String rnd;
  private String mail;
  private String key;
  private User user;
  private String newPwd;
  private String name;
  private String sn;
  private String regsn;
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String index()
  {
    return "index";
  }
  
  public void getBackPwd()
  {
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("rndCode"))
        {
          String rndCode = cookie.getValue();
          System.err.println(rndCode);
          if (this.rnd.trim().equalsIgnoreCase(rndCode))
          {
            System.err.println(this.mail);
            this.user = this.userService.userByName(this.mail);
            if (this.user == null) {
              Struts2Utils.renderText("false", new String[0]);
            } else {
              Struts2Utils.renderText("1", new String[0]);
            }
          }
          else
          {
            Struts2Utils.renderText("3", new String[0]);
          }
        }
      }
    }
  }
  
  public void getRandomCode()
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    this.response.setContentType("image/jpeg");
    this.response.setHeader("Pragma", "No-cache");
    this.response.setHeader("Cache-Control", "no-cache");
    this.response.setDateHeader("Expire", 0L);
    RandomValidateCode randomValidateCode = new RandomValidateCode();
    try
    {
      randomValidateCode.getRandcode(this.request, this.response);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public String findemailcheck()
  {
    return "findemailcheck";
  }
  
	public String findmobilecheck() throws UnsupportedEncodingException {
		if (StringUtil.isNotBlank(key)) {
			sn = key.substring(64, key.length());
			mail = Base64.getDecode(sn);
/*			if (StringUtil.isNotBlank(mail)) {
				user = userService.userByName(mail);
				if (user != null) {
					return "findreset";
				}
			}
*/		}
		return "findmobilecheck";
	}
  
	/*
	 * mail 手机号
	 * sn 验证码
	 * regsn 加密串
	 * 返回 state: 0正确; 2操作超时， 请重新取回密码; -4 验证码错误或过期; 其他
	 */
	public void verifysmscode() {
		String mobileStr = (String)AliyunOcsSampleHelp.getIMemcachedCache().get(this.mail);
	    this.key = (MD5Util.encode(this.mail) + MD5Util.encode(DateUtil.dateTimeToStr(new Date())) + Base64.getEncode(this.mail));
	    if (mobileStr == null)
	    {
	    	Struts2Utils.renderJson("{\"state\":2}");
	    	return;
	    }
	    if(mobileStr.equals(sn)){
	    	Struts2Utils.renderJson("{\"state\":0,\"str\":\"" + key + "\"}");
	    	return;
	    }else{
	    	Struts2Utils.renderJson("{\"state\":-4}");
	    	return;
	    }
	}
  
  public static void main(String[] args)
    throws UnsupportedEncodingException
  {
    //String key = MD5Util.encode("127.0.0.11000");
    //AliyunOcsSampleHelp.getIMemcachedCache().delete(key);
	  String phone = "13758249979";
	  String k = MD5Util.encode(phone) + MD5Util.encode(DateUtil.dateTimeToStr(new Date())) + Base64.getEncode(phone);
	  System.out.println(k);
  }
  
  public String findreset()
    throws UnsupportedEncodingException
  {
    if (StringUtil.isNotBlank(this.key))
    {
      this.key = this.key.substring(64, this.key.length());
      this.mail = Base64.getDecode(this.key);
      if (StringUtil.isNotBlank(this.mail))
      {
        this.user = this.userService.userByName(this.mail);
        if (this.user != null) {
          return "findreset";
        }
      }
    }
    return "index_index";
  }
  
  public void updatePwd()
  {
    this.user = this.userService.userByName(this.mail);
    if (this.user != null)
    {
    	if ((newPwd != null) && (!newPwd.equals(""))) {
  		  newPwd = MessageDigestUtils.sha256Hex(newPwd);
        }
      this.user.setUserPwd(this.newPwd);
      this.userService.add(this.user);
      Struts2Utils.renderJson("{\"state\":0}");
    }
    else
    {
    	Struts2Utils.renderJson("{\"state\":-1}");
    }
  }
  
	public void checknamesn() {
		user = userService.userByName(name);
		if (user != null) {
			AliyunOcsSampleHelp.getIMemcachedCache().set(Base64.getEncode(name), 120, name);
			Struts2Utils.renderJson("{\"state\":1,\"str\":\"" + Base64.getEncode(name) + "\"}");
		} else {
			Struts2Utils.renderJson("{\"state\":0}");
		}
	}
	
	public void emailgetbackpwd() {
		this.request = Struts2Utils.getRequest();
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
	      "<td style=\"font-size: 12px; color: #333333; line-height: 22px;\"><p style=\"text-indent: 2em; padding: 0; margin: 0;\">您好！请点击下面的按钮，重新设置您的密码：</p></td>" + 
	      "</tr>" + 
	      "<tr>" + 
	      "<td style=\"padding-top: 15px; padding-left: 28px;\"><a title=\"重设密码\" style=\"display: inline-block; padding: 0 25px; height: 28px; line-height: 28px; text-align: center; color: #ffffff; background: #ff7700; font-size: 12px; cursor: pointer; border-radius: 2px; text-decoration: none;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/getbackpwd/findreset.html?key=" + this.key + "\">重设密码</a></td>" + 
	      "</tr>" + 
	      "<tr>" + 
	      "<td width=\"525\" style=\"font-size: 12px; color: #333333; line-height: 22px; padding-top: 20px;\">如果上面按钮不能点击或点击后没有反应，您还可以将以下链接复制到浏览器地址栏中访问完成重设密码。</td>" + 
	      "</tr>" + 
	      "<tr>" + 
	      "<td width=\"525\" style=\"font-size: 12px; padding-top: 5px; word-break: break-all; word-wrap: break-word;\"><a style=\"font-family: Arial; color: #22aaff;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/getbackpwd/findreset.html?key=" + this.key + "\">" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/getbackpwd/findreset.html?key=" + this.key + "</a></td>" + 
	      "</tr>" + 
	      "<tr>" + 
	      "<td style=\"font-size:12px; color:#333333; line-height:22px; padding-top:30px;\"><p style=\"text-indent:2em; padding:0; margin:0;\"><b>如果您现在想起了您的密码：</b></p></td>" + 
	      "</tr>" + 
	      "<tr>" + 
	      "<td style=\"font-size:12px; color:#333333; line-height:22px;\"><p style=\"text-indent:2em; padding:0; margin:0;\">可不必重设密码，继续用原来的密码登录。</p></td>" + 
	      "</tr>" + 
	      "</tbody></table>" + 
	      "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"margin-top: 60px;\">" + 
	      "<tbody><tr>" + 
	      "<td style=\"font-size: 12px; color: #777777; line-height: 22px; border-bottom: #22aaff 2px solid; padding-bottom: 8px; padding-left: 20px;\">此邮件由系统自动发出，请勿回复！</td>" + 
	      "</tr>" + 
	      "<tr>" + 
	      "<td style=\"font-size: 12px; color: #333333; line-height: 22px; padding: 8px 20px 0;\">感谢您对" + ApplicationListenerImpl.sysConfigureJson.getSaitName() + "（<a style=\"color: #22aaff; font-family: Arial;\" target=\"_blank\" href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "\">" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "</a>）的支持，祝您好运！</td>" + 
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
	    
	    Cookie[] cookies = this.request.getCookies();
	    if (this.request.isRequestedSessionIdFromCookie()) {
	      for (int i = 0; i < cookies.length; i++)
	      {
	        Cookie cookie = cookies[i];
	        if (cookie.getName().equals("rndCode"))
	        {
	          String rndCode = cookie.getValue();
	          if (this.rnd.trim().equalsIgnoreCase(rndCode))
	          {
	            if (AliyunOcsSampleHelp.getIMemcachedCache().get(MD5Util.encode(this.mail)) == null)
	            {
	              boolean flag = EmailUtil.sendEmail(ApplicationListenerImpl.sysConfigureJson.getMailName(), ApplicationListenerImpl.sysConfigureJson.getMailPwd(), this.mail, ApplicationListenerImpl.sysConfigureJson.getSaitName() + "取回密码", html);
	              if (flag)
	              {
	                AliyunOcsSampleHelp.getIMemcachedCache().set(MD5Util.encode(this.mail), 600, this.mail);
		            Struts2Utils.renderJson("{\"state\":0,\"str\":\"" + key + "\"}");
	              }
	              else
	              {
	            	  Struts2Utils.renderJson("{\"state\":1}");
	              }
	            }
	            else
	            {
	            	Struts2Utils.renderJson("{\"state\":1}");
	            }
	          }
	          else {
	        	  Struts2Utils.renderJson("{\"state\":1}");
	          }
	        }
	      }
	    }
	}
	
	/**
	 * sn UPacJy2yWilaJYGGT*a5r*7RKpCcPhyg9cIJGbW7WWw%3d
	 *    1geLOdCPE7bj1KsfqlCzJrZ.EoIn1280EIj4UhltYlw%3d
	 * @throws UnsupportedEncodingException
	 */
	public void sendfindsms() {
		String mobileStr = (String) AliyunOcsSampleHelp.getIMemcachedCache().get(this.mail);
		this.key = (MD5Util.encode(this.mail)
				+ MD5Util.encode(DateUtil.dateTimeToStr(new Date())) + Base64
				.getEncode(this.mail));
		if (mobileStr == null) {
			Random random = new Random();
			String ran = "";
			for (int i = 0; i < 6; i++) {
				ran = ran + random.nextInt(9);
			}
			boolean success = SmsSenderFactory.create().send(this.mail, ran);
			if (success) {
				AliyunOcsSampleHelp.getIMemcachedCache().set(this.mail, 120, ran);
				Struts2Utils.renderJson("{\"state\":0,\"str\":\"" + key + "\"}");
			} else {
				Struts2Utils.renderJson("{\"state\":1}");
			}
		}
	}
  
  public String findok()
  {
    return "findok";
  }
  
  public String getRnd()
  {
    return this.rnd;
  }
  
  public void setRnd(String rnd)
  {
    this.rnd = rnd;
  }
  
  public String getMail()
  {
    return this.mail;
  }
  
  public void setMail(String mail)
  {
    this.mail = mail;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public String getNewPwd()
  {
    return this.newPwd;
  }
  
  public void setNewPwd(String newPwd)
  {
    this.newPwd = newPwd;
  }

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getRegsn() {
		return regsn;
	}

	public void setRegsn(String regsn) {
		this.regsn = regsn;
	}
  
}
