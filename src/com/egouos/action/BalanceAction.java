package com.egouos.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.egouos.pojo.Consumerdetail;
import com.egouos.pojo.Consumetable;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.User;
import com.egouos.service.ConsumerdetailService;
import com.egouos.service.ConsumetableService;
import com.egouos.service.OrderIdService;
import com.egouos.service.UserService;
import com.egouos.tenpay.RequestHandler;
import com.egouos.tenpay.ResponseHandler;
import com.egouos.tenpay.client.ClientResponseHandler;
import com.egouos.tenpay.client.TenpayHttpClient;
import com.egouos.tenpay.config.TenpayConfig;
import com.egouos.tenpay.util.TenpayUtil;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.ConfigUtil;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;

@Component("BalanceAction")
public class BalanceAction extends ActionSupport
{
  private static final long serialVersionUID = 8197993150697915816L;
  protected static final Logger LOG = LoggerFactory.getLogger(BalanceAction.class);
  
  private static String secret = ConfigUtil.getValue("secret", "1yyg1234567890");
  /** 支付平台 表。*/
	protected static final Map<String, String> PPLATS = new HashMap<String, String>();
	static{
		PPLATS.put("Alipay", "/alipay/goPay.action");
		PPLATS.put("Tenpay", "/tenpay/goPay.action");
		PPLATS.put("aliPayUser", "/mycart/aliPayUser.action");
		PPLATS.put("tenPayUser", "/mycart/tenPayUser.action");
		PPLATS.put("Chinabank", "/chinabank/goPay.action");
		PPLATS.put("Yeepay", "/yeepay/goPay.action");
		PPLATS.put("Swift", "/weixinpay/goPay.action");
		PPLATS.put("jdpay", "/jdpay/goPay.action");
		PPLATS.put("YunPay", "/yunpay/goPay.action");
		PPLATS.put("IAppPay", "/iapppay/goPay.action");
		PPLATS.put("JubaoPay", "/jubaopay/goPay.action");
	}
	
  @Autowired
  ConsumetableService consumetableService;
  @Autowired
  ConsumerdetailService consumerdetailService;
  @Autowired
  private UserService userService;
  @Autowired
  OrderIdService orderIdService;
  
  private User user;
  private String userId;
  private Consumetable consumetable;
  private ProductCart productCart;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private Consumerdetail consumerdetail;
  private List<ProductCart> productCartList;
  private String paymentStatus;
  private String currTime = TenpayUtil.getCurrTime();
  private String strTime = this.currTime.substring(8, this.currTime.length());
  private String strRandom = TenpayUtil.buildRandom(4)+"";
  private String strReq = this.strTime + this.strRandom;
  private String out_trade_no = this.strReq;
  private Integer moneyCount;
  private String productBody = "";
  private String productName;
  private String bank_type;
  private String hidUseBalance;
  private String payName;
  private String payBank;
  private String integral;
  private String requestUrl;
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String goBalance() throws IOException
  {
    request = Struts2Utils.getRequest();
    response = Struts2Utils.getResponse();
    boolean flag = false;
    Cookie[] cookies = request.getCookies();
    productCartList = new ArrayList<ProductCart>();
    if (request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId"))
        {
          userId = cookie.getValue();
          flag = true;
        }
      }
    }
    if (StringUtil.isNotBlank(userId))
    {
      if (flag)
      {
    	out_trade_no = orderIdService.addOxid();
        consumetable = new Consumetable();
        double money = Double.parseDouble(String.valueOf(moneyCount));
        consumetable.setBuyCount(moneyCount);
        consumetable.setDate(DateUtil.DateTimeToStr(new Date()));
        consumetable.setInterfaceType("网银支付");
        consumetable.setMoney(Double.valueOf(money));
        consumetable.setOutTradeNo(out_trade_no);
        consumetable.setUserId(Integer.valueOf(userId));
        consumetable.setWithold("");
        consumetable.setPayStatus(Consumetable.PAY_STAT_NPAID);
        consumetable.setBankMoney(moneyCount);
        consumetable.setBuyIp(Struts2Utils.getRemoteIp());
        consumetableService.add(consumetable);
        LOG.debug("recharge: tradeno = {}, consid = {}", out_trade_no, consumetable.getId());
      }
      
      TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("moneyCount", moneyCount.toString());
		map.put("out_trade_no", out_trade_no);
		String sign="";
		try {
			sign = getSign(map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
      if (flag)
      {
        LOG.debug("payName: {}, payBank: {}", payName, payBank);
        final String action = PPLATS.get(payName);
        if (payBank.equals("0"))
        {
          String body = "<form method=\"post\" action=\""+action+"\" name=\"toPayForm\" id=\"toPayForm\">";
          body = body + "<input type=\"hidden\" value=\"" + moneyCount + "\" id=\"moneyCount\" name=\"moneyCount\">";
          body = body + "<input type=\"hidden\" value=\"" + hidUseBalance + "\" name=\"hidUseBalance\" id=\"hidUseBalance\">";
          body = body + "<input type=\"hidden\" value=\"" + out_trade_no + "\" name=\"out_trade_no\" id=\"out_trade_no\">";
          body = body + "<input type=\"hidden\" value=\"" + integral + "\" name=\"integral\" id=\"hidIntegral\">";
          body = body + "<input type=\"hidden\" value=\"" + payName + "\" name=\"payName\" id=\"payName\">";
          body = body + "<input type=\"hidden\" value=\"" + payBank + "\" name=\"payBank\" id=\"payBank\">";
          body = body + "<input type=\"hidden\" value=\"" + userId + "\" name=\"userId\" id=\"userId\">";
          body = body + "<input type=\"hidden\" value=\"" + sign + "\" name=\"token\" id=\"token\">";
          body = body + "</form>";
          body = body + "<script>document.forms['toPayForm'].submit();</script>";
          
          StringBuilder sb = new StringBuilder();
          sb.append('[');
          sb.append('{');
          sb.append("\"userPayType\":\"").append(0).append("\",");
          sb.append("\"userId\":\"").append(userId).append("\",");
          sb.append("\"moneyCount\":\"").append(moneyCount).append("\",");
          sb.append("\"integral\":\"").append(integral).append("\",");
          //sb.append("\"bankMoney\":\"").append(bankMoney).append("\",");
          sb.append("\"hidUseBalance\":\"").append(hidUseBalance).append("\",");
          sb.append("\"out_trade_no\":\"").append(out_trade_no).append("\"");
          sb.append('}');
          sb.append(']');
          
          JSONArray payInfo = JSONArray.fromObject(sb.toString());
          AliyunOcsSampleHelp.getIMemcachedCache().set("doPayList" + out_trade_no, 900, productCartList);
          AliyunOcsSampleHelp.getIMemcachedCache().set("doPay" + out_trade_no, 900, payInfo);
          
          Struts2Utils.render("text/html", body, new String[] { "encoding:UTF-8" });
        }
      }
    }
    else
    {
      Struts2Utils.render("text/html", "<script>alert(\"您还未登录，请登录。\");</script>", new String[] { "encoding:UTF-8" });
    }
    return null;
  }
  
  /**
	* 对参数进行MD5
	*
	* @param params
	* 排好序的参数Map
	* @param secret
	* 应用的密钥
	* @return MD5签名字符串
	* @throws UnsupportedEncodingException
	*/
	protected static String getSign(final TreeMap<String, String> params) throws UnsupportedEncodingException {
		if (null == params || params.isEmpty()) {
			return (String) null;
		}

		/*if (StringUtils.isEmpty(secret)) {
			return (String) null;
		}*/
		StringBuilder sb = new StringBuilder();
		for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			sb/*.append(entry.getKey())*/.append(defaultString(entry.getValue()));
		}
		sb.append(secret);
		byte[] bytes = sb.toString().getBytes("UTF-8");
		return DigestUtils.md5Hex(bytes);
	}
	
	public static String defaultString(String str) {
		return str == null ? "" : str;
	}
  
  public String goBalance2()
    throws IOException
  {
    HttpServletRequest request = Struts2Utils.getRequest();
    HttpServletResponse response = Struts2Utils.getResponse();
    
    RequestHandler reqHandler = new RequestHandler(request, response);
    reqHandler.init();
    

    reqHandler.setKey(TenpayConfig.key);
    
    reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");
    


    this.moneyCount = Integer.valueOf(this.moneyCount.intValue() * 100);
    System.err.println("moneyCount:" + this.moneyCount);
    System.err.println("bank_type" + this.bank_type);
    reqHandler.setParameter("partner", TenpayConfig.partner);
    reqHandler.setParameter("out_trade_no", this.out_trade_no);
    reqHandler.setParameter("total_fee", moneyCount+"");
    reqHandler.setParameter("return_url", TenpayConfig.balance_return_url);
    reqHandler.setParameter("notify_url", TenpayConfig.balance_notify_url);
    reqHandler.setParameter("body", ApplicationListenerImpl.sysConfigureJson.getSaitName() + "(" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + ")");
    reqHandler.setParameter("bank_type", this.bank_type);
    
    reqHandler.setParameter("spbill_create_ip", request.getHeader("X-Real-IP"));
    reqHandler.setParameter("fee_type", "1");
    reqHandler.setParameter("subject", this.productName);
    

    reqHandler.setParameter("sign_type", "MD5");
    reqHandler.setParameter("service_version", "1.0");
    reqHandler.setParameter("input_charset", "UTF-8");
    reqHandler.setParameter("sign_key_index", "1");
    


    reqHandler.setParameter("attach", this.hidUseBalance);
    
    Cookie[] cookies = request.getCookies();
    JSONArray array = null;
    if (request.isRequestedSessionIdFromCookie()) {
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
      int buyConut = this.moneyCount.intValue() / 100;
      if ((this.hidUseBalance != null) && (!this.hidUseBalance.equals(""))) {
        buyConut += Integer.parseInt(this.hidUseBalance);
      }
      double money = Double.parseDouble(String.valueOf(this.moneyCount));
      money *= 0.01D;
      this.consumetable.setBuyCount(Integer.valueOf(buyConut));
      this.consumetable.setDate(DateUtil.DateTimeToStr(new Date()));
      this.consumetable.setInterfaceType("tenPay");
      this.consumetable.setMoney(Double.valueOf(money));
      this.consumetable.setOutTradeNo(this.out_trade_no);
      this.consumetable.setUserId(Integer.valueOf(Integer.parseInt(this.userId)));
      this.consumetableService.add(this.consumetable);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    this.requestUrl = reqHandler.getRequestURL();
    

    String debuginfo = reqHandler.getDebugInfo();
    System.out.println("requestUrl:  " + this.requestUrl);
    System.out.println("sign_String:  " + debuginfo);
    
    response.sendRedirect(this.requestUrl);
    return null;
  }
  
  public String returnUrl()
  {
    HttpServletRequest request = Struts2Utils.getRequest();
    HttpServletResponse response = Struts2Utils.getResponse();
    
    ResponseHandler resHandler = new ResponseHandler(request, response);
    resHandler.setKey(TenpayConfig.key);
    System.out.println("前台回调返回参数:" + resHandler.getAllParameters());
    if (resHandler.isTenpaySign())
    {
      String notify_id = resHandler.getParameter("notify_id");
      
      String out_trade_no = resHandler.getParameter("out_trade_no");
      
      String transaction_id = resHandler.getParameter("transaction_id");
      
      String total_fee = resHandler.getParameter("total_fee");
      
      String discount = resHandler.getParameter("discount");
      
      String trade_state = resHandler.getParameter("trade_state");
      
      String trade_mode = resHandler.getParameter("trade_mode");
      
      String hidUseBalance = resHandler.getParameter("attach");
      if ("1".equals(trade_mode))
      {
        if ("0".equals(trade_state))
        {
          try
          {
            this.consumetable = this.consumetableService.findByOutTradeNo(out_trade_no);
            double money = Double.parseDouble(String.valueOf(total_fee));
            money *= 0.01D;
            System.err.println(this.consumetable.getMoney());
            System.err.println(money);
            if ((this.consumetable.getMoney().equals(Double.valueOf(money))) && (this.consumetable.getTransactionId().equals(transaction_id))) {
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
        else
        {
          System.out.println("即时到帐付款失败");
        }
      }
      else if ("2".equals(trade_mode)) {
        if ("0".equals(trade_state)) {
          System.out.println("中介担保付款成功");
        } else {
          System.out.println("trade_state=" + trade_state);
        }
      }
    }
    else
    {
      System.out.println("认证签名失败");
    }
    String debuginfo = resHandler.getDebugInfo();
    System.out.println("debuginfo:" + debuginfo);
    return "success";
  }
  
  public String notifyUrl()
    throws Exception
  {
    HttpServletRequest request = Struts2Utils.getRequest();
    HttpServletResponse response = Struts2Utils.getResponse();
    

    ResponseHandler resHandler = new ResponseHandler(request, response);
    resHandler.setKey(TenpayConfig.key);
    System.out.println("后台回调返回参数:" + resHandler.getAllParameters());
    if (resHandler.isTenpaySign())
    {
      String notify_id = resHandler.getParameter("notify_id");
      
      RequestHandler queryReq = new RequestHandler(null, null);
      
      TenpayHttpClient httpClient = new TenpayHttpClient();
      
      ClientResponseHandler queryRes = new ClientResponseHandler();
      

      queryReq.init();
      queryReq.setKey(TenpayConfig.key);
      queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
      queryReq.setParameter("partner", TenpayConfig.partner);
      queryReq.setParameter("notify_id", notify_id);
      
      httpClient.setTimeOut(5);
      
      httpClient.setReqContent(queryReq.getRequestURL());
      System.out.println("验证ID请求字符串:" + queryReq.getRequestURL());
      if (httpClient.call())
      {
        queryRes.setContent(httpClient.getResContent());
        System.out.println("验证ID返回字符串:" + httpClient.getResContent());
        queryRes.setKey(TenpayConfig.key);
        
        String retcode = queryRes.getParameter("retcode");
        
        String out_trade_no = resHandler.getParameter("out_trade_no");
        
        String transaction_id = resHandler.getParameter("transaction_id");
        
        String total_fee = resHandler.getParameter("total_fee");
        
        String discount = resHandler.getParameter("discount");
        
        String trade_state = resHandler.getParameter("trade_state");
        
        String trade_mode = resHandler.getParameter("trade_mode");
        if ((queryRes.isTenpaySign()) && ("0".equals(retcode)))
        {
          System.out.println("id验证成功");
          if ("1".equals(trade_mode))
          {
            if ("0".equals(trade_state))
            {
              try
              {
                this.consumetable = this.consumetableService.findByOutTradeNo(out_trade_no);
                double money = Double.parseDouble(String.valueOf(total_fee));
                money *= 0.01D;
                System.err.println(this.consumetable.getMoney());
                System.err.println(money);
                if ((this.consumetable.getMoney().equals(Double.valueOf(money))) && (this.consumetable.getTransactionId() == null))
                {
                  this.consumetable.setTransactionId(transaction_id);
                  this.consumetableService.add(this.consumetable);
                  this.userId = String.valueOf(this.consumetable.getUserId());
                  if ((this.userId != null) && (!this.userId.equals(""))) {
                    try
                    {
                      String key = MD5Util.encode(transaction_id);
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
                  resHandler.sendToCFT("success");
                }
              }
              catch (Exception e)
              {
                e.printStackTrace();
              }
              resHandler.sendToCFT("success");
            }
            else
            {
              System.out.println("即时到账支付失败");
              resHandler.sendToCFT("fail");
            }
          }
          else if ("2".equals(trade_mode))
          {
            int iStatus = TenpayUtil.toInt(trade_state);
            switch (iStatus)
            {
            case 0: 
              break;
            case 1: 
              break;
            case 2: 
              break;
            case 4: 
              break;
            case 5: 
              break;
            case 6: 
              break;
            case 7: 
              break;
            case 8: 
              break;
            case 9: 
              break;
            }
            System.out.println("trade_state = " + trade_state);
            
            resHandler.sendToCFT("success");
          }
        }
        else
        {
          System.out.println("查询验证签名失败或id验证失败,retcode:" + queryRes.getParameter("retcode"));
        }
      }
      else
      {
        System.out.println("后台调用通信失败");
        System.out.println(httpClient.getResponseCode());
        System.out.println(httpClient.getErrInfo());
      }
    }
    else
    {
      System.out.println("通知签名验证失败");
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
  
  public ProductCart getProductCart()
  {
    return this.productCart;
  }
  
  public void setProductCart(ProductCart productCart)
  {
    this.productCart = productCart;
  }
  
  public Product getProduct()
  {
    return this.product;
  }
  
  public void setProduct(Product product)
  {
    this.product = product;
  }
  
  public Spellbuyproduct getSpellbuyproduct()
  {
    return this.spellbuyproduct;
  }
  
  public void setSpellbuyproduct(Spellbuyproduct spellbuyproduct)
  {
    this.spellbuyproduct = spellbuyproduct;
  }
  
  public Consumerdetail getConsumerdetail()
  {
    return this.consumerdetail;
  }
  
  public void setConsumerdetail(Consumerdetail consumerdetail)
  {
    this.consumerdetail = consumerdetail;
  }
  
  public List<ProductCart> getProductCartList()
  {
    return this.productCartList;
  }
  
  public void setProductCartList(List<ProductCart> productCartList)
  {
    this.productCartList = productCartList;
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
  
  public String getOut_trade_no()
  {
    return this.out_trade_no;
  }
  
  public void setOut_trade_no(String out_trade_no)
  {
    this.out_trade_no = out_trade_no;
  }
  
  public Integer getMoneyCount()
  {
    return this.moneyCount;
  }
  
  public void setMoneyCount(Integer moneyCount)
  {
    this.moneyCount = moneyCount;
  }
  
  public String getProductBody()
  {
    return this.productBody;
  }
  
  public void setProductBody(String productBody)
  {
    this.productBody = productBody;
  }
  
  public String getProductName()
  {
    return this.productName;
  }
  
  public void setProductName(String productName)
  {
    this.productName = productName;
  }
  
  public String getBank_type()
  {
    return this.bank_type;
  }
  
  public void setBank_type(String bank_type)
  {
    this.bank_type = bank_type;
  }
  
  public String getHidUseBalance()
  {
    return this.hidUseBalance;
  }
  
  public void setHidUseBalance(String hidUseBalance)
  {
    this.hidUseBalance = hidUseBalance;
  }
  
  public String getRequestUrl()
  {
    return this.requestUrl;
  }
  
  public void setRequestUrl(String requestUrl)
  {
    this.requestUrl = requestUrl;
  }

	public String getPayName() {
		return payName;
	}
	
	public void setPayName(String payName) {
		this.payName = payName;
	}
	
	public String getPayBank() {
		return payBank;
	}
	
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}
  
}
