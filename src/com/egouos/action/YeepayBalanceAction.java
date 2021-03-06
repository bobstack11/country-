package com.egouos.action;

import com.egouos.pojo.Consumetable;
import com.egouos.pojo.SysConfigure;
import com.egouos.pojo.User;
import com.egouos.service.ConsumerdetailService;
import com.egouos.service.ConsumetableService;
import com.egouos.service.UserService;
import com.egouos.tenpay.util.TenpayUtil;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.Struts2Utils;
import com.egouos.yeepay.config.PaymentForOnlineService;
import com.opensymphony.xwork2.ActionSupport;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;

public class YeepayBalanceAction
  extends ActionSupport
{
  private static final long serialVersionUID = 1198398565998377267L;
  @Autowired
  ConsumetableService consumetableService;
  @Autowired
  ConsumerdetailService consumerdetailService;
  @Autowired
  private UserService userService;
  private User user;
  private String userId;
  private Consumetable consumetable;
  private Integer moneyCount;
  private String hidUseBalance;
  private String paymentStatus;
  private String currTime = TenpayUtil.getCurrTime();
  private String strTime = this.currTime.substring(8, this.currTime.length());
  private String strRandom = TenpayUtil.buildRandom(4)+"";
  private String strReq = this.strTime + this.strRandom;
  private static String nodeAuthorizationURL = "https://www.yeepay.com/app-merchant-proxy/node";
  Random random = new Random();
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  String formatString(String text)
  {
    if (text == null) {
      return "";
    }
    return text;
  }
  
  public String index()
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
    String keyValue = ApplicationListenerImpl.sysConfigureJson.getYeepayKey();
    
    String p0_Cmd = formatString("Buy");
    String p1_MerId = ApplicationListenerImpl.sysConfigureJson.getYeepayPartner();
    String p2_Order = this.strReq;
    String p3_Amt = this.moneyCount.toString();
    String p4_Cur = formatString("CNY");
    String p5_Pid = ApplicationListenerImpl.sysConfigureJson.getWwwUrl();
    String p6_Pcat = ApplicationListenerImpl.sysConfigureJson.getWwwUrl();
    String p7_Pdesc = ApplicationListenerImpl.sysConfigureJson.getWwwUrl();
    String p8_Url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/yeepay/returnUrl.action";
    String p9_SAF = "0";
    String pa_MP = "";
    String pd_FrpId = "";
    
    pd_FrpId = pd_FrpId.toUpperCase();
    String pr_NeedResponse = formatString("1");
    String hmac = formatString("");
    

    hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd, 
      p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, 
      p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
    

    Map<String, String> sParaTemp = new HashMap();
    sParaTemp.put("p0_Cmd", p0_Cmd);
    sParaTemp.put("p1_MerId", p1_MerId);
    sParaTemp.put("p2_Order", p2_Order);
    sParaTemp.put("p3_Amt", p3_Amt);
    sParaTemp.put("p4_Cur", p4_Cur);
    sParaTemp.put("p5_Pid", p5_Pid);
    sParaTemp.put("p6_Pcat", p6_Pcat);
    sParaTemp.put("p7_Pdesc", p7_Pdesc);
    sParaTemp.put("p8_Url", p8_Url);
    sParaTemp.put("p9_SAF", p9_SAF);
    sParaTemp.put("pa_MP", pa_MP);
    sParaTemp.put("pd_FrpId", pd_FrpId);
    sParaTemp.put("pr_NeedResponse", pr_NeedResponse);
    sParaTemp.put("hmac", hmac);
    
    Cookie[] cookies = this.request.getCookies();
    JSONArray array = null;
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId")) {
          this.userId = cookie.getValue();
        }
      }
    }
    try
    {
      this.consumetable = new Consumetable();
      int buyConut = this.moneyCount.intValue();
      if ((this.hidUseBalance != null) && (!this.hidUseBalance.equals(""))) {
        buyConut += Integer.parseInt(this.hidUseBalance);
      }
      double money = Double.parseDouble(String.valueOf(this.moneyCount));
      this.consumetable.setBuyCount(Integer.valueOf(buyConut));
      this.consumetable.setDate(DateUtil.DateTimeToStr(new Date()));
      this.consumetable.setInterfaceType("yeePay");
      this.consumetable.setMoney(Double.valueOf(money));
      this.consumetable.setOutTradeNo(p2_Order);
      this.consumetable.setUserId(Integer.valueOf(Integer.parseInt(this.userId)));
      this.consumetableService.add(this.consumetable);
      








      flag = true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      flag = false;
    }
    String sHtmlText = buildRequest(sParaTemp, nodeAuthorizationURL, "POST", "确认");
    System.out.println(sHtmlText);
    if (flag) {
      Struts2Utils.render("text/html", sHtmlText, new String[] { "encoding:UTF-8" });
    } else {
      Struts2Utils.render("text/html", "<script>alert(\"充值失败，请联系管理员！\");window.location.href=\"/index.html\";</script>", new String[] { "encoding:UTF-8" });
    }
    return null;
  }
  
  public String returnUrl()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    String keyValue = ApplicationListenerImpl.sysConfigureJson.getYeepayKey();
    String r0_Cmd = formatString(this.request.getParameter("r0_Cmd"));
    String p1_MerId = ApplicationListenerImpl.sysConfigureJson.getYeepayPartner();
    String r1_Code = formatString(this.request.getParameter("r1_Code"));
    String r2_TrxId = formatString(this.request.getParameter("r2_TrxId"));
    String r3_Amt = formatString(this.request.getParameter("r3_Amt"));
    String r4_Cur = formatString(this.request.getParameter("r4_Cur"));
    String r5_Pid = new String(formatString(this.request.getParameter("r5_Pid")).getBytes("iso-8859-1"), "gbk");
    String r6_Order = formatString(this.request.getParameter("r6_Order"));
    String r7_Uid = formatString(this.request.getParameter("r7_Uid"));
    String integral = new String(formatString(this.request.getParameter("integral")).getBytes("iso-8859-1"), "gbk");
    String r9_BType = formatString(this.request.getParameter("r9_BType"));
    String hmac = formatString(this.request.getParameter("hmac"));
    
    boolean isOK = false;
    
    isOK = PaymentForOnlineService.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, 
      r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, integral, r9_BType, keyValue);
    if (isOK)
    {
      if (r1_Code.equals("1"))
      {
        if (r9_BType.equals("1")) {
          System.out.println("callback方式:产品通用接口支付成功返回-浏览器重定向");
        } else if (r9_BType.equals("2")) {
          System.out.println("SUCCESS");
        }
        System.out.println("<br>交易成功!<br>商家订单号:" + r6_Order + "<br>支付金额:" + r3_Amt + "<br>易宝支付交易流水号:" + r2_TrxId);
        try
        {
          try
          {
            this.consumetable = this.consumetableService.findByOutTradeNo(r6_Order);
            double money = Double.parseDouble(String.valueOf(r3_Amt));
            System.err.println(this.consumetable.getMoney());
            System.err.println(money);
            if ((this.consumetable.getMoney().equals(Double.valueOf(money))) && (this.consumetable.getTransactionId().equals(r2_TrxId))) {
              this.paymentStatus = "success";
            } else {
              this.paymentStatus = "error";
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
          System.out.println("即时到帐付款成功");
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    else {
      System.out.println("交易签名被篡改!");
    }
    return "success";
  }
  
  public String notifyUrl()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
    String keyValue = ApplicationListenerImpl.sysConfigureJson.getYeepayKey();
    String r0_Cmd = formatString(this.request.getParameter("r0_Cmd"));
    String p1_MerId = ApplicationListenerImpl.sysConfigureJson.getYeepayPartner();
    String r1_Code = formatString(this.request.getParameter("r1_Code"));
    String r2_TrxId = formatString(this.request.getParameter("r2_TrxId"));
    String r3_Amt = formatString(this.request.getParameter("r3_Amt"));
    String r4_Cur = formatString(this.request.getParameter("r4_Cur"));
    String r5_Pid = new String(formatString(this.request.getParameter("r5_Pid")).getBytes("iso-8859-1"), "gbk");
    String r6_Order = formatString(this.request.getParameter("r6_Order"));
    String r7_Uid = formatString(this.request.getParameter("r7_Uid"));
    String integral = new String(formatString(this.request.getParameter("r8_MP")).getBytes("iso-8859-1"), "gbk");
    String r9_BType = formatString(this.request.getParameter("r9_BType"));
    String hmac = formatString(this.request.getParameter("hmac"));
    

    Map<String, String> sParaTemp = new HashMap();
    sParaTemp.put("r0_Cmd", r0_Cmd);
    sParaTemp.put("p1_MerId", p1_MerId);
    sParaTemp.put("r1_Code", r1_Code);
    sParaTemp.put("r2_TrxId", r2_TrxId);
    sParaTemp.put("r3_Amt", r3_Amt);
    sParaTemp.put("r4_Cur", r4_Cur);
    sParaTemp.put("r5_Pid", r5_Pid);
    sParaTemp.put("r6_Order", r6_Order);
    sParaTemp.put("r7_Uid", r7_Uid);
    sParaTemp.put("integral", integral);
    sParaTemp.put("r9_BType", r9_BType);
    sParaTemp.put("hmac", hmac);
    

boolean isOK = false;
    
    isOK = PaymentForOnlineService.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, 
      r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, integral, r9_BType, keyValue);
    if (isOK)
    {
      if (r1_Code.equals("1"))
      {
        if (r9_BType.equals("1"))
        {
          System.out.println("callback方式:产品通用接口支付成功返回-浏览器重定向");
        }
        else if (r9_BType.equals("2"))
        {
          System.out.println("SUCCESS");
          Struts2Utils.render("text/html", "success", new String[] { "encoding:UTF-8" });
        }
        System.out.println("<br>交易成功!<br>商家订单号:" + r6_Order + "<br>支付金额:" + r3_Amt + "<br>易宝支付交易流水号:" + r2_TrxId);
        try
        {
          this.consumetable = this.consumetableService.findByOutTradeNo(r6_Order);
          double money = Double.parseDouble(String.valueOf(r3_Amt));
          System.err.println(this.consumetable.getMoney());
          System.err.println(money);
          if ((this.consumetable.getMoney().equals(Double.valueOf(money))) && (this.consumetable.getTransactionId() == null))
          {
            this.consumetable.setTransactionId(r2_TrxId);
            this.consumetableService.add(this.consumetable);
            this.userId = String.valueOf(this.consumetable.getUserId());
            if ((this.userId != null) && (!this.userId.equals(""))) {
              try
              {
                String key = MD5Util.encode(r2_TrxId);
                if (AliyunOcsSampleHelp.getIMemcachedCache().get(key) == null)
                {
                  this.user = ((User)this.userService.findById(this.userId));
                  Double recMoney = this.consumetable.getMoney();
                  if (recMoney.doubleValue() >= ApplicationListenerImpl.sysConfigureJson.getRecMoney().doubleValue()) {
                    recMoney = Double.valueOf(recMoney.doubleValue() + ApplicationListenerImpl.sysConfigureJson.getRecBalance().doubleValue());
                  }
                  Double temp = Double.valueOf(this.user.getBalance().doubleValue() + recMoney.doubleValue());
                  System.err.println("user.getBalance()" + this.user.getBalance());
                  System.err.println("consumetable.getMoney()" + this.consumetable.getMoney());
                  System.err.println("temp:" + temp);
                  this.user.setBalance(temp);
                  this.userService.add(this.user);
                  AliyunOcsSampleHelp.getIMemcachedCache().set(key, 43200, "y");
                }
              }
              catch (Exception e)
              {
                e.printStackTrace();
              }
            }
          }
          String sHtmlText;
          sHtmlText = buildRequest(sParaTemp, ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/yeepayBalance/returnUrl.html", "POST", "确认");
          Struts2Utils.render("text/html", sHtmlText, new String[] { "encoding:UTF-8" });
          return null;
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        System.out.println("success");
        Struts2Utils.render("text/html", "success", new String[] { "encoding:UTF-8" });
      }
    }
    else {
      System.out.println("交易签名被篡改!");
    }
    return null;
  }
  
  public static String buildRequest(Map<String, String> sParaTemp, String postUrl, String strMethod, String strButtonName)
  {
    Map<String, String> sPara = sParaTemp;
    List<String> keys = new ArrayList((Collection)sPara.keySet());
    
    StringBuffer sbHtml = new StringBuffer();
    
    sbHtml.append("<form id=\"yeepaysubmit\" name=\"yeepaysubmit\" action=\"" + postUrl + "\" method=\"" + strMethod + "\">");
    for (int i = 0; i < keys.size(); i++)
    {
      String name = (String)keys.get(i);
      String value = (String)sPara.get(name);
      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }
    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['yeepaysubmit'].submit();</script>");
    
    return sbHtml.toString();
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public Consumetable getConsumetable()
  {
    return this.consumetable;
  }
  
  public void setConsumetable(Consumetable consumetable)
  {
    this.consumetable = consumetable;
  }
  
  public Integer getMoneyCount()
  {
    return this.moneyCount;
  }
  
  public void setMoneyCount(Integer moneyCount)
  {
    this.moneyCount = moneyCount;
  }
  
  public String getHidUseBalance()
  {
    return this.hidUseBalance;
  }
  
  public void setHidUseBalance(String hidUseBalance)
  {
    this.hidUseBalance = hidUseBalance;
  }
  
  public String getPaymentStatus()
  {
    return this.paymentStatus;
  }
  
  public void setPaymentStatus(String paymentStatus)
  {
    this.paymentStatus = paymentStatus;
  }
  
  public String getCurrTime()
  {
    return this.currTime;
  }
  
  public void setCurrTime(String currTime)
  {
    this.currTime = currTime;
  }
  
  public String getStrTime()
  {
    return this.strTime;
  }
  
  public void setStrTime(String strTime)
  {
    this.strTime = strTime;
  }
  
  public String getStrRandom()
  {
    return this.strRandom;
  }
  
  public void setStrRandom(String strRandom)
  {
    this.strRandom = strRandom;
  }
  
  public String getStrReq()
  {
    return this.strReq;
  }
  
  public void setStrReq(String strReq)
  {
    this.strReq = strReq;
  }
}
