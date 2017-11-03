package com.egouos.action;

import com.egouos.alipay.config.AlipayConfig;
import com.egouos.alipay.util.AlipayNotify;
import com.egouos.alipay.util.AlipaySubmit;
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
import com.opensymphony.xwork2.ActionSupport;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("AlipayBalanceAction")
public class AlipayBalanceAction
  extends ActionSupport
{
  private static final long serialVersionUID = 2055647485775038604L;
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
  Random random = new Random();
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String index()
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    
    boolean flag = false;
    
    String payment_type = "1";
    


    String notify_url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/alipayBalance/notifyUrl.action";
    


    String return_url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/alipayBalance/returnUrl.action";
    

    String seller_email = AlipayConfig.mail;
    

    String out_trade_no = this.strReq;
    

    String subject = "商品购买";
    

    String total_fee = this.moneyCount.toString();
    

    String body = "商品购买";
    
    String show_url = "https://www.alipay.com/?src=alipay.com";
    

    String anti_phishing_key = this.currTime;
    


    String exter_invoke_ip = this.request.getHeader("X-Real-IP");
    


    Map<String, String> sParaTemp = new HashMap();
    sParaTemp.put("service", "create_direct_pay_by_user");
    sParaTemp.put("partner", AlipayConfig.partner);
    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
    sParaTemp.put("payment_type", payment_type);
    sParaTemp.put("notify_url", notify_url);
    sParaTemp.put("return_url", return_url);
    sParaTemp.put("seller_email", seller_email);
    sParaTemp.put("out_trade_no", out_trade_no);
    sParaTemp.put("subject", subject);
    sParaTemp.put("total_fee", total_fee);
    sParaTemp.put("body", body);
    sParaTemp.put("show_url", show_url);
    sParaTemp.put("anti_phishing_key", anti_phishing_key);
    sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
    

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
      this.consumetable.setInterfaceType("aliPay");
      this.consumetable.setMoney(Double.valueOf(money));
      this.consumetable.setOutTradeNo(out_trade_no);
      this.consumetable.setUserId(Integer.valueOf(Integer.parseInt(this.userId)));
      this.consumetableService.add(this.consumetable);
      








      flag = true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      flag = false;
    }
    String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
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
    String buyproduct = "";
    
    Map<String, String> params = new HashMap();
    Map requestParams = this.request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
    {
      String name = (String)iter.next();
      String[] values = (String[])requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = 
          valueStr + values[i] + ",";
      }
      params.put(name, valueStr);
    }
    String out_trade_no = new String(this.request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
    
    String trade_no = new String(this.request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
    
    String total_fee = new String(this.request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
    
    String trade_status = new String(this.request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
    


    boolean verify_result = AlipayNotify.verify(params);
    if (verify_result)
    {
      if ((trade_status.equals("TRADE_FINISHED")) || (trade_status.equals("TRADE_SUCCESS"))) {
        try
        {
          try
          {
            this.consumetable = this.consumetableService.findByOutTradeNo(out_trade_no);
            double money = Double.parseDouble(String.valueOf(total_fee));
            System.err.println(this.consumetable.getMoney());
            System.err.println(money);
            if ((this.consumetable.getMoney().equals(Double.valueOf(money))) && (this.consumetable.getTransactionId().equals(trade_no))) {
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
      System.out.println("验证成功<br />");
    }
    else
    {
      System.out.println("验证失败");
    }
    return "success";
  }
  
  public String notifyUrl()
    throws Exception
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    
    Map<String, String> params = new HashMap();
    Map requestParams = this.request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
    {
      String name = (String)iter.next();
      String[] values = (String[])requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = 
          valueStr + values[i] + ",";
      }
      params.put(name, valueStr);
    }
    String out_trade_no = new String(this.request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
    
    String trade_no = new String(this.request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
    String total_fee = new String(this.request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
    
    String trade_status = new String(this.request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
    if (AlipayNotify.verify(params))
    {
      if (!trade_status.equals("TRADE_FINISHED")) {
        if (trade_status.equals("TRADE_SUCCESS")) {
          try
          {
            this.consumetable = this.consumetableService.findByOutTradeNo(out_trade_no);
            double money = Double.parseDouble(String.valueOf(total_fee));
            System.err.println(this.consumetable.getMoney());
            System.err.println(money);
            if ((this.consumetable.getMoney().equals(Double.valueOf(money))) && (this.consumetable.getTransactionId() == null))
            {
              this.consumetable.setTransactionId(trade_no);
              this.consumetableService.add(this.consumetable);
              this.userId = String.valueOf(this.consumetable.getUserId());
              if ((this.userId != null) && (!this.userId.equals(""))) {
                try
                {
                  String key = MD5Util.encode(trade_no);
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
            System.out.println("success");
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }
      }
      Struts2Utils.render("text/html", "success", new String[] { "encoding:UTF-8" });
    }
    else
    {
      Struts2Utils.render("text/html", "fail", new String[] { "encoding:UTF-8" });
      System.out.println("fail");
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
}
