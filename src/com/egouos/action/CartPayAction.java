package com.egouos.action;

import com.egouos.pojo.Commissionpoints;
import com.egouos.pojo.Commissionquery;
import com.egouos.pojo.Consumerdetail;
import com.egouos.pojo.Consumetable;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Lotteryproductutil;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Randomnumber;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.SysConfigure;
import com.egouos.pojo.User;
import com.egouos.service.CommissionpointsService;
import com.egouos.service.CommissionqueryService;
import com.egouos.service.ConsumerdetailService;
import com.egouos.service.ConsumetableService;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.LotteryproductutilService;
import com.egouos.service.ProductService;
import com.egouos.service.RandomnumberService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.service.UserService;
import com.egouos.tenpay.util.TenpayUtil;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.NewLotteryUtil;
import com.egouos.util.RandomUtil;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.egouos.yeepay.config.PaymentForOnlineService;
import com.opensymphony.xwork2.ActionSupport;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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
import net.sf.json.JSONObject;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CartPayAction
  extends ActionSupport
{
  private static final long serialVersionUID = 2516892117051908997L;
  @Autowired
  @Qualifier("spellbuyproductService")
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private RandomnumberService randomnumberService;
  @Autowired
  private UserService userService;
  @Autowired
  private ConsumetableService consumetableService;
  @Autowired
  private ConsumerdetailService consumerdetailService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ProductService productService;
  @Autowired
  private NewLotteryUtil newLotteryUtil;
  @Autowired
  CommissionqueryService commissionqueryService;
  @Autowired
  LotteryproductutilService lotteryproductutilService;
  @Autowired
  CommissionpointsService commissionpointsService;
  RandomUtil randomUtil = new RandomUtil();
  private String currTime = TenpayUtil.getCurrTime();
  private String strTime = this.currTime.substring(8, this.currTime.length());
  private String strRandom = TenpayUtil.buildRandom(4)+"";
  private String strReq = this.strTime + this.strRandom;
  private String out_trade_no = this.strReq;
  private String paymentStatus;
  private Consumetable consumetable;
  private Consumerdetail consumerdetail;
  private List<ProductCart> productCartList;
  private List<ProductJSON> successCartList;
  private ProductJSON productJSON;
  private ProductCart productCart;
  private Spellbuyproduct spellbuyproduct;
  private Spellbuyrecord spellbuyrecord;
  private Randomnumber randomnumber;
  private Latestlottery latestlottery;
  private Commissionquery commissionquery;
  private Lotteryproductutil lotteryproductutil;
  private Commissionpoints commissionpoints;
  private Product product;
  private String id;
  private User user;
  private String userId;
  private Integer moneyCount;
  private Integer userPayType;
  private String integral;
  private String hidUseBalance;
  private static String nodeAuthorizationURL = "https://www.yeepay.com/app-merchant-proxy/node";
  Random random = new Random();
  Calendar calendar = Calendar.getInstance();
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String index()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.productCartList = new ArrayList();
    Cookie[] cookies = this.request.getCookies();
    JSONArray array = null;
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("buyProduct"))
        {
          new StringUtil();String product = StringUtil.getUTF8URLDecoder(cookie.getValue());
          if ((product != null) && (!product.equals(""))) {
            array = JSONArray.fromObject(product);
          }
        }
      }
    }
    Integer productCount = Integer.valueOf(0);
    if ((array != null) && (!array.toString().equals("[{}]"))) {
      for (int i = 0; i < array.size(); i++) {
        try
        {
          JSONObject obj = (JSONObject)array.get(i);
          this.productCart = new ProductCart();
          this.product = ((Product)this.productService.findById(obj.getString("pId")));
          productCount = Integer.valueOf(productCount.intValue() + 1);
          this.productCart.setMoneyCount(Integer.valueOf(Integer.parseInt(obj.getString("num"))));
          this.productCart.setHeadImage(this.product.getHeadImage());
          this.productCart.setProductCount(productCount);
          this.productCart.setProductId(this.product.getProductId());
          this.productCart.setProductName(this.product.getProductName());
          this.productCart.setProductPrice(this.product.getProductPrice());
          this.productCart.setProductTitle(this.product.getProductTitle());
          this.productCartList.add(this.productCart);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    return "index";
  }
  
  public String payment()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    this.productCartList = new ArrayList();
    JSONArray array = null;
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId"))
        {
          this.userId = cookie.getValue();
          if ((this.userId != null) && (!this.userId.equals(""))) {
            this.user = ((User)this.userService.findById(this.userId));
          }
        }
        if (cookie.getName().equals("buyProduct"))
        {
          new StringUtil();String product = StringUtil.getUTF8URLDecoder(cookie.getValue());
          if ((product != null) && (!product.equals(""))) {
            array = JSONArray.fromObject(product);
          }
        }
      }
    }
    Integer moneyCount = Integer.valueOf(0);
    Integer productCount = Integer.valueOf(0);
    if ((array != null) && (!array.toString().equals("[{}]"))) {
      for (int i = 0; i < array.size(); i++) {
        try
        {
          JSONObject obj = (JSONObject)array.get(i);
          this.productCart = new ProductCart();
          this.product = ((Product)this.productService.findById(obj.getString("pId")));
          moneyCount = Integer.valueOf(moneyCount.intValue() + Integer.parseInt(obj.getString("num")));
          productCount = Integer.valueOf(productCount.intValue() + 1);
          this.productCart.setMoneyCount(moneyCount);
          this.productCart.setHeadImage(this.product.getHeadImage());
          this.productCart.setProductCount(productCount);
          this.productCart.setProductId(this.product.getProductId());
          this.productCart.setProductName(this.product.getProductName());
          this.productCart.setProductPrice(this.product.getProductPrice());
          this.productCart.setProductTitle(this.product.getProductTitle());
          this.productCartList.add(this.productCart);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    return "payment";
  }
  
  String formatString(String text)
  {
    if (text == null) {
      return "";
    }
    return text;
  }
  
  public String goPay()
    throws UnsupportedEncodingException
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
    String p8_Url = ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/yeepay/notifyUrl.action";
    String p9_SAF = "0";
    String pa_MP = this.integral;
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
    



    this.productCartList = new ArrayList();
    Cookie[] cookies = this.request.getCookies();
    JSONArray array = null;
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId")) {
          this.userId = cookie.getValue();
        }
        if (cookie.getName().equals("buyProduct"))
        {
          new StringUtil();String product = StringUtil.getUTF8URLDecoder(cookie.getValue());
          if ((product != null) && (!product.equals(""))) {
            array = JSONArray.fromObject(product);
          }
        }
      }
    }
    if (StringUtil.isNotBlank(this.userId))
    {
      Integer buyConut = Integer.valueOf(0);
      Integer productCount = Integer.valueOf(0);
      if ((array != null) && (!array.toString().equals("[{}]"))) {
        for (int i = 0; i < array.size(); i++) {
          try
          {
            JSONObject obj = (JSONObject)array.get(i);
            this.productCart = new ProductCart();
            this.product = ((Product)this.productService.findById(obj.getString("pId")));
            this.moneyCount = Integer.valueOf(this.moneyCount.intValue() + Integer.parseInt(obj.getString("num")));
            productCount = Integer.valueOf(productCount.intValue() + 1);
            this.productCart.setMoneyCount(this.moneyCount);
            this.productCart.setHeadImage(this.product.getHeadImage());
            this.productCart.setProductCount(productCount);
            this.productCart.setProductId(this.product.getProductId());
            this.productCart.setProductName(this.product.getProductName());
            this.productCart.setProductPrice(this.product.getProductPrice());
            this.productCart.setProductTitle(this.product.getProductTitle());
            this.productCartList.add(this.productCart);
            flag = true;
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }
      }
      if (flag)
      {
        try
        {
          this.consumetable = new Consumetable();
          double money = Double.parseDouble(String.valueOf(this.moneyCount));
          this.consumetable.setBuyCount(buyConut);
          this.consumetable.setDate(DateUtil.DateTimeToStr(new Date()));
          this.consumetable.setInterfaceType("yeePay");
          this.consumetable.setMoney(Double.valueOf(money));
          this.consumetable.setOutTradeNo(p2_Order);
          this.consumetable.setUserId(Integer.valueOf(Integer.parseInt(this.userId)));
          this.consumetableService.add(this.consumetable);
        }
        catch (Exception e)
        {
          e.printStackTrace();
          flag = false;
        }
      }
      else
      {
        flag = false;
        Struts2Utils.render("text/html", "<script>alert(\"购物车中有商品已经满员，请选择下一期！\");window.location.href=\"/mycart/index.html\";</script>", new String[] { "encoding:UTF-8" });
      }
    }
    String sHtmlText = buildRequest(sParaTemp, nodeAuthorizationURL, "POST", "确认");
    System.out.println(sHtmlText);
    if (flag) {
      Struts2Utils.render("text/html", sHtmlText, new String[] { "encoding:UTF-8" });
    } else {
      Struts2Utils.render("text/html", "<script>alert(\"购物车中有商品已经满员，请该商品的选择下一期！\");window.location.href=\"/mycart/index.html\";</script>", new String[] { "encoding:UTF-8" });
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
    
    boolean flag = false;
    
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
          String key = MD5Util.encode(r2_TrxId);
          if (AliyunOcsSampleHelp.getIMemcachedCache().get(key) != null) {
        	  return "success";
          }
          AliyunOcsSampleHelp.getIMemcachedCache().set(key, 43200, "y");
          this.productCartList = new ArrayList();
          this.successCartList = new ArrayList();
          try
          {
            this.consumetable = this.consumetableService.findByOutTradeNo(r6_Order);
            double money = Double.parseDouble(String.valueOf(r3_Amt));
            System.err.println(this.consumetable.getMoney());
            System.err.println(money);
            if ((this.consumetable.getMoney().equals(Double.valueOf(money))) && (this.consumetable.getTransactionId().equals(r2_TrxId)))
            {
              Cookie[] cookies = this.request.getCookies();
              JSONArray array = null;
              if (this.request.isRequestedSessionIdFromCookie()) {
                for (int i = 0; i < cookies.length; i++)
                {
                  Cookie cookie = cookies[i];
                  if (cookie.getName().equals("userId")) {
                    this.userId = cookie.getValue();
                  }
                  if (cookie.getName().equals("buyProduct"))
                  {
                    new StringUtil();String product = StringUtil.getUTF8URLDecoder(cookie.getValue());
                    if ((product != null) && (!product.equals(""))) {
                      array = JSONArray.fromObject(product);
                    }
                  }
                }
              }
              Integer moneyCount = Integer.valueOf(0);
              Integer productCount = Integer.valueOf(0);
              if ((array != null) && (!array.toString().equals("[{}]"))) {
                for (int i = 0; i < array.size(); i++) {
                  try
                  {
                    JSONObject obj = (JSONObject)array.get(i);
                    this.productCart = new ProductCart();
                    this.product = ((Product)this.productService.findById(obj.getString("pId")));
                    moneyCount = Integer.valueOf(moneyCount.intValue() + Integer.parseInt(obj.getString("num")));
                    productCount = Integer.valueOf(productCount.intValue() + 1);
                    this.productCart.setMoneyCount(moneyCount);
                    this.productCart.setHeadImage(this.product.getHeadImage());
                    this.productCart.setProductCount(productCount);
                    this.productCart.setProductId(this.product.getProductId());
                    this.productCart.setProductName(this.product.getProductName());
                    this.productCart.setProductPrice(this.product.getProductPrice());
                    this.productCart.setProductTitle(this.product.getProductTitle());
                    this.productCartList.add(this.productCart);
                  }
                  catch (Exception e)
                  {
                    e.printStackTrace();
                  }
                }
              }
              if (StringUtil.isNotBlank(this.userId)) {
                this.user = ((User)this.userService.findById(this.userId));
              }
              if (flag) {
                for (ProductCart productCart : this.productCartList) {
                  try
                  {
                    Integer count = productCart.getProductPrice();
                    if (count.intValue() > 0)
                    {
                      if ((StringUtil.isNotBlank(integral)) && (!integral.equals("0")))
                      {
                        if (this.user.getBalance().doubleValue() >= count.intValue() - Integer.parseInt(integral) / 100)
                        {
                          Double temp = Double.valueOf(this.user.getBalance().doubleValue() - (count.intValue() - Integer.parseInt(integral) / 100));
                          this.user.setBalance(temp);
                          

                          this.consumerdetail = new Consumerdetail();
                          this.consumerdetail.setBuyCount(count);
                          this.consumerdetail.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                          this.consumerdetail.setConsumetableId(r6_Order);
                          this.consumerdetail.setProductId(productCart.getProductId());
                          this.consumerdetail.setProductName(productCart.getProductName());
                          this.consumerdetail.setProductTitle(productCart.getProductTitle());
                          this.consumerdetailService.add(this.consumerdetail);
                          if (this.user.getInvite() != null)
                          {
                            User userCommission = (User)this.userService.findById(String.valueOf(this.user.getInvite()));
                            double tempCommissionCount = userCommission.getCommissionCount().doubleValue();
                            double commissionBalance = this.user.getCommissionBalance().doubleValue();
                            userCommission.setCommissionCount(Double.valueOf(tempCommissionCount += Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                            userCommission.setCommissionBalance(Double.valueOf(commissionBalance += Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                            this.userService.add(userCommission);
                            this.commissionquery = new Commissionquery();
                            this.commissionquery.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                            this.commissionquery.setCommission(Double.valueOf(Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                            this.commissionquery.setDate(DateUtil.DateTimeToStr(new Date()));
                            this.commissionquery.setDescription(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + productCart.getProductId() + ")获得佣金");
                            this.commissionquery.setInvitedId(this.user.getInvite());
                            this.commissionquery.setToUserId(this.user.getUserId());
                            this.commissionqueryService.add(this.commissionquery);
                          }
                        }
                        else
                        {
                          Struts2Utils.render("text/html", "<script>alert(\"您的余额不足，无法完成支付\");window.location.href=\"/mycart/index.html\";</script>", new String[] { "encoding:UTF-8" });
                        }
                      }
                      else if (this.user.getBalance().doubleValue() >= count.intValue())
                      {
                        Double temp = Double.valueOf(this.user.getBalance().doubleValue() - count.intValue());
                        this.user.setBalance(temp);
                        
                        this.consumerdetail = new Consumerdetail();
                        this.consumerdetail.setBuyCount(count);
                        this.consumerdetail.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                        this.consumerdetail.setConsumetableId(r6_Order);
                        this.consumerdetail.setProductId(productCart.getProductId());
                        this.consumerdetail.setProductName(productCart.getProductName());
                        this.consumerdetail.setProductTitle(productCart.getProductTitle());
                        this.consumerdetailService.add(this.consumerdetail);
                        if (this.user.getInvite() != null)
                        {
                          User userCommission = (User)this.userService.findById(String.valueOf(this.user.getInvite()));
                          double tempCommissionCount = userCommission.getCommissionCount().doubleValue();
                          double commissionBalance = this.user.getCommissionBalance().doubleValue();
                          userCommission.setCommissionCount(Double.valueOf(tempCommissionCount += Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                          userCommission.setCommissionBalance(Double.valueOf(commissionBalance += Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                          this.userService.add(userCommission);
                          this.commissionquery = new Commissionquery();
                          this.commissionquery.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                          this.commissionquery.setCommission(Double.valueOf(Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                          this.commissionquery.setDate(DateUtil.DateTimeToStr(new Date()));
                          this.commissionquery.setDescription(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + productCart.getProductId() + ")获得佣金");
                          this.commissionquery.setInvitedId(this.user.getInvite());
                          this.commissionquery.setToUserId(this.user.getUserId());
                          this.commissionqueryService.add(this.commissionquery);
                        }
                      }
                      else
                      {
                        Struts2Utils.render("text/html", "<script>alert(\"您的余额不足，无法完成支付\");window.location.href=\"/mycart/index.html\";</script>", new String[] { "encoding:UTF-8" });
                      }
                      if ((StringUtil.isNotBlank(integral)) && (!integral.equals("0")))
                      {
                        this.commissionpoints = new Commissionpoints();
                        this.commissionpoints.setDate(DateUtil.DateTimeToStr(new Date()));
                        this.commissionpoints.setDetailed(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + this.spellbuyproduct.getSpellbuyProductId() + ")积分抵扣");
                        this.commissionpoints.setPay("-" + integral);
                        this.commissionpoints.setToUserId(Integer.valueOf(Integer.parseInt(this.userId)));
                        this.commissionpointsService.add(this.commissionpoints);
                        Integer points = this.user.getCommissionPoints();
                        this.user.setCommissionPoints(Integer.valueOf(points.intValue() - Integer.parseInt(integral)));
                      }
                      this.commissionpoints = new Commissionpoints();
                      this.commissionpoints.setDate(DateUtil.DateTimeToStr(new Date()));
                      this.commissionpoints.setDetailed(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + this.spellbuyproduct.getSpellbuyProductId() + ")支付" + count + "元获得积分");
                      this.commissionpoints.setPay("+" + (count.intValue() * ApplicationListenerImpl.sysConfigureJson.getBuyProduct().intValue() - Integer.parseInt(integral) / 100));
                      this.commissionpoints.setToUserId(Integer.valueOf(Integer.parseInt(this.userId)));
                      this.commissionpointsService.add(this.commissionpoints);
                      
                      Integer points = this.user.getCommissionPoints();
                      this.user.setCommissionPoints(Integer.valueOf(points.intValue() + (count.intValue() * ApplicationListenerImpl.sysConfigureJson.getBuyProduct().intValue() - Integer.parseInt(integral) / 100)));
                      
                      Integer experience = this.user.getExperience();
                      experience = Integer.valueOf(experience.intValue() + count.intValue() * 10);
                      this.user.setExperience(experience);
                      this.userService.add(this.user);
                      
                      this.productJSON = new ProductJSON();
                      this.productJSON.setBuyDate(DateUtil.DateTimeToStr(new Date()));
                      this.productJSON.setProductId(productCart.getProductId());
                      this.productJSON.setProductName(productCart.getProductName());
                      this.productJSON.setProductTitle(productCart.getProductTitle());
                      this.productJSON.setBuyCount(count);
                      this.successCartList.add(this.productJSON);
                    }
                  }
                  catch (Exception e)
                  {
                    e.printStackTrace();
                    flag = false;
                  }
                }
              }
              if (flag) {
                if (this.request.isRequestedSessionIdFromCookie())
                {
                  Cookie cookie = new Cookie("buyProduct", null);
                  cookie.setMaxAge(0);
                  cookie.setPath("/");
                  cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
                  this.response.addCookie(cookie);
                }
              }
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
          System.out.println("即时到帐付款成功");
          this.paymentStatus = "success";
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    else
    {
      this.paymentStatus = "error";
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
            
            this.user = ((User)this.userService.findById(String.valueOf(this.consumetable.getUserId())));
            this.user.setBalance(Double.valueOf(money + this.user.getBalance().doubleValue()));
            this.userService.add(this.user);
            flag = true;
          }
        }
        catch (Exception e)
        {
          flag = false;
          e.printStackTrace();
        }
        String sHtmlText = buildRequest(sParaTemp, ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/yeepay/returnUrl.html", "POST", "确认");
        System.out.println(sHtmlText);
        Struts2Utils.render("text/html", sHtmlText, new String[] { "encoding:UTF-8" });
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
  
  public NewLotteryUtil getNewLotteryUtil()
  {
    return this.newLotteryUtil;
  }
  
  public void setNewLotteryUtil(NewLotteryUtil newLotteryUtil)
  {
    this.newLotteryUtil = newLotteryUtil;
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
  
  public Consumetable getConsumetable()
  {
    return this.consumetable;
  }
  
  public void setConsumetable(Consumetable consumetable)
  {
    this.consumetable = consumetable;
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
  
  public ProductCart getProductCart()
  {
    return this.productCart;
  }
  
  public void setProductCart(ProductCart productCart)
  {
    this.productCart = productCart;
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
  
  public Commissionquery getCommissionquery()
  {
    return this.commissionquery;
  }
  
  public void setCommissionquery(Commissionquery commissionquery)
  {
    this.commissionquery = commissionquery;
  }
  
  public Lotteryproductutil getLotteryproductutil()
  {
    return this.lotteryproductutil;
  }
  
  public void setLotteryproductutil(Lotteryproductutil lotteryproductutil)
  {
    this.lotteryproductutil = lotteryproductutil;
  }
  
  public Commissionpoints getCommissionpoints()
  {
    return this.commissionpoints;
  }
  
  public void setCommissionpoints(Commissionpoints commissionpoints)
  {
    this.commissionpoints = commissionpoints;
  }
  
  public Product getProduct()
  {
    return this.product;
  }
  
  public void setProduct(Product product)
  {
    this.product = product;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
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
  
  public Integer getMoneyCount()
  {
    return this.moneyCount;
  }
  
  public void setMoneyCount(Integer moneyCount)
  {
    this.moneyCount = moneyCount;
  }
  
  public Integer getUserPayType()
  {
    return this.userPayType;
  }
  
  public void setUserPayType(Integer userPayType)
  {
    this.userPayType = userPayType;
  }
  
  public String getIntegral()
  {
    return this.integral;
  }
  
  public void setIntegral(String integral)
  {
    this.integral = integral;
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
