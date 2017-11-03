package com.egouos.action;

import com.egouos.alipay.config.AlipayConfig;
import com.egouos.alipay.util.AlipayNotify;
import com.egouos.alipay.util.AlipaySubmit;
import com.egouos.pojo.Commissionpoints;
import com.egouos.pojo.Commissionquery;
import com.egouos.pojo.Consumerdetail;
import com.egouos.pojo.Consumetable;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Randomnumber;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.User;
import com.egouos.service.CommissionpointsService;
import com.egouos.service.CommissionqueryService;
import com.egouos.service.ConsumerdetailService;
import com.egouos.service.ConsumetableService;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.ProductService;
import com.egouos.service.RandomnumberService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.service.UserService;
import com.egouos.tenpay.util.TenpayUtil;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.NewLotteryUtil;
import com.egouos.util.RandomUtil;
import com.egouos.util.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;

public class AlipaySoftAction
  extends ActionSupport
{
  private static final long serialVersionUID = -8970081284102469306L;
  RandomUtil randomUtil = new RandomUtil();
  @Autowired
  ConsumetableService consumetableService;
  @Autowired
  ConsumerdetailService consumerdetailService;
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private RandomnumberService randomnumberService;
  @Autowired
  private UserService userService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ProductService productService;
  @Autowired
  private NewLotteryUtil newLotteryUtil;
  @Autowired
  CommissionqueryService commissionqueryService;
  @Autowired
  CommissionpointsService commissionpointsService;
  private User user;
  private String userId;
  private Consumetable consumetable;
  private ProductCart productCart;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private Spellbuyrecord spellbuyrecord;
  private Randomnumber randomnumber;
  private Latestlottery latestlottery;
  private Consumerdetail consumerdetail;
  private List<ProductCart> productCartList;
  private List<ProductJSON> successCartList;
  private ProductJSON productJSON;
  private String paymentStatus;
  private Commissionquery commissionquery;
  private Commissionpoints commissionpoints;
  private Integer moneyCount;
  private String integral;
  private String currTime = TenpayUtil.getCurrTime();
  private String strTime = this.currTime.substring(8, this.currTime.length());
  private String strRandom = TenpayUtil.buildRandom(4)+"";
  private String strReq = this.strTime + this.strRandom;
  Random random = new Random();
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String goPay()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
    
    String payment_type = "1";
    


    String notify_url = notify_url = "http://test.hahsun.net/buySoft/notifyUrl.action";
    


    String return_url = "http://test.hahsun.net/buySoft/returnUrl.action";
    

    String seller_email = AlipayConfig.mail;
    

    String out_trade_no = this.strReq;
    

    String subject = "软件授权费";
    

    String total_fee = "5000";
    

    String body = "软件授权费";
    
    String show_url = "http://test.hahsun.net/buySoft/index.html";
    

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
    sParaTemp.put("extra_common_param", this.integral);
    


























































































    String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
    System.out.println(sHtmlText);
    
    Struts2Utils.render("text/html", sHtmlText, new String[] { "encoding:UTF-8" });
    


    return null;
  }
  
  public String returnUrl()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
    
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
    
    this.integral = new String(this.request.getParameter("extra_common_param").getBytes("ISO-8859-1"), "UTF-8");
    

    boolean verify_result = AlipayNotify.verify(params);
    if (verify_result)
    {
      if ((trade_status.equals("TRADE_FINISHED")) || (trade_status.equals("TRADE_SUCCESS"))) {
        try
        {
          String key = MD5Util.encode(trade_no);
          if (AliyunOcsSampleHelp.getIMemcachedCache().get(key) == null)
          {
            AliyunOcsSampleHelp.getIMemcachedCache().set(key, 43200, "y");
            







            System.out.println("即时到帐付款成功");
            this.paymentStatus = "success";
            String s = UUID.randomUUID().toString().toUpperCase();
            s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
            String md5s = MD5Util.encode(s);
            String softKey = s + md5s;
            this.userId = softKey;
          }
          else
          {
            this.paymentStatus = "ok";
          }
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
      this.paymentStatus = "error";
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
    
    String integral = new String(this.request.getParameter("extra_common_param").getBytes("ISO-8859-1"), "UTF-8");
    if (AlipayNotify.verify(params))
    {
      if (!trade_status.equals("TRADE_FINISHED")) {
        trade_status.equals("TRADE_SUCCESS");
      }
      System.out.println("success");
      Struts2Utils.render("text/html", "success", new String[] { "encoding:UTF-8" });
    }
    else
    {
      Struts2Utils.render("text/html", "fail", new String[] { "encoding:UTF-8" });
      System.out.println("fail");
    }
    return null;
  }
  
  public String index()
  {
    this.integral = DateUtil.DateTimeToStr(new Date());
    return "index";
  }
  
  public Integer getMoneyCount()
  {
    return this.moneyCount;
  }
  
  public void setMoneyCount(Integer moneyCount)
  {
    this.moneyCount = moneyCount;
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
  
  public Spellbuyrecord getSpellbuyrecord()
  {
    return this.spellbuyrecord;
  }
  
  public void setSpellbuyrecord(Spellbuyrecord spellbuyrecord)
  {
    this.spellbuyrecord = spellbuyrecord;
  }
  
  public Randomnumber getRandomnumber()
  {
    return this.randomnumber;
  }
  
  public void setRandomnumber(Randomnumber randomnumber)
  {
    this.randomnumber = randomnumber;
  }
  
  public Latestlottery getLatestlottery()
  {
    return this.latestlottery;
  }
  
  public void setLatestlottery(Latestlottery latestlottery)
  {
    this.latestlottery = latestlottery;
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
  
  public List<ProductJSON> getSuccessCartList()
  {
    return this.successCartList;
  }
  
  public void setSuccessCartList(List<ProductJSON> successCartList)
  {
    this.successCartList = successCartList;
  }
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
  }
  
  public String getPaymentStatus()
  {
    return this.paymentStatus;
  }
  
  public void setPaymentStatus(String paymentStatus)
  {
    this.paymentStatus = paymentStatus;
  }
  
  public Commissionquery getCommissionquery()
  {
    return this.commissionquery;
  }
  
  public void setCommissionquery(Commissionquery commissionquery)
  {
    this.commissionquery = commissionquery;
  }
  
  public Commissionpoints getCommissionpoints()
  {
    return this.commissionpoints;
  }
  
  public void setCommissionpoints(Commissionpoints commissionpoints)
  {
    this.commissionpoints = commissionpoints;
  }
  
  public String getIntegral()
  {
    return this.integral;
  }
  
  public void setIntegral(String integral)
  {
    this.integral = integral;
  }
}
