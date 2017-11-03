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
import com.egouos.tenpay.RequestHandler;
import com.egouos.tenpay.ResponseHandler;
import com.egouos.tenpay.client.ClientResponseHandler;
import com.egouos.tenpay.client.TenpayHttpClient;
import com.egouos.tenpay.config.TenpayConfig;
import com.egouos.tenpay.util.TenpayUtil;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.NewLotteryUtil;
import com.egouos.util.RandomUtil;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("TenpayAction")
public class TenpayAction
  extends ActionSupport
{
  private static final long serialVersionUID = 2460509323066698846L;
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
  @Autowired
  LotteryproductutilService lotteryproductutilService;
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
  private Lotteryproductutil lotteryproductutil;
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
  private String integral;
  private String requestUrl;
  Random random = new Random();
  Calendar calendar = Calendar.getInstance();
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String goPay()
    throws ServletException, IOException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
    
    RequestHandler reqHandler = new RequestHandler(this.request, this.response);
    reqHandler.init();
    

    reqHandler.setKey(TenpayConfig.key);
    
    reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");
    


    this.moneyCount = Integer.valueOf(this.moneyCount.intValue() * 100);
    reqHandler.setParameter("partner", TenpayConfig.partner);
    reqHandler.setParameter("out_trade_no", this.out_trade_no);
    reqHandler.setParameter("total_fee", this.moneyCount+"");
    reqHandler.setParameter("return_url", TenpayConfig.return_url);
    reqHandler.setParameter("notify_url", TenpayConfig.notify_url);
    reqHandler.setParameter("body", ApplicationListenerImpl.sysConfigureJson.getSaitName() + "(" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + ")");
    reqHandler.setParameter("bank_type", this.bank_type);
    
    reqHandler.setParameter("spbill_create_ip", this.request.getHeader("X-Real-IP"));
    reqHandler.setParameter("fee_type", "1");
    reqHandler.setParameter("subject", this.productName);
    

    reqHandler.setParameter("sign_type", "MD5");
    reqHandler.setParameter("service_version", "1.0");
    reqHandler.setParameter("input_charset", "UTF-8");
    reqHandler.setParameter("sign_key_index", "1");
    


    reqHandler.setParameter("attach", this.integral);
    
















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
        if (cookie.getName().equals("products"))
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
            List<Object[]> proList = this.spellbuyproductService.findByProductId(Integer.parseInt(obj.getString("pId")));
            this.product = ((Product)((Object[])proList.get(0))[0]);
            this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
            if (this.spellbuyproduct.getSpStatus().intValue() == 0)
            {
              Integer count = Integer.valueOf(0);
              
              Integer CurrentPrice = this.spellbuyproduct.getSpellbuyCount();
              if (this.spellbuyproduct.getSpellbuyCount().intValue() + obj.getInt("num") > this.spellbuyproduct.getSpellbuyPrice().intValue()) {
                count = Integer.valueOf(this.spellbuyproduct.getSpellbuyPrice().intValue() - this.spellbuyproduct.getSpellbuyCount().intValue());
              } else {
                count = Integer.valueOf(obj.getInt("num"));
              }
              buyConut = Integer.valueOf(buyConut.intValue() + count.intValue());
              productCount = Integer.valueOf(productCount.intValue() + 1);
              this.productCart.setCount(count);
              this.productCart.setHeadImage(this.product.getHeadImage());
              
              this.productCart.setProductCount(productCount);
              this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
              this.productCart.setProductName(this.product.getProductName());
              this.productCart.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
              this.productCart.setProductTitle(this.product.getProductTitle());
              this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
              this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
              this.productCartList.add(this.productCart);
              flag = true;
            }
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
          money *= 0.01D;
          this.consumetable.setBuyCount(buyConut);
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
          flag = false;
        }
      }
      else
      {
        flag = false;
        Struts2Utils.render("text/html", "<script>alert(\"购物车中有商品已经满员，请选择下一期！\");window.location.href=\"/mycart/index.html\";</script>", new String[] { "encoding:UTF-8" });
      }
    }
    this.requestUrl = reqHandler.getRequestURL();
    

    String debuginfo = reqHandler.getDebugInfo();
    System.out.println("requestUrl:  " + this.requestUrl);
    System.out.println("sign_String:  " + debuginfo);
    if (flag) {
      this.response.sendRedirect(this.requestUrl);
    } else {
      Struts2Utils.render("text/html", "<script>alert(\"购物车中有商品已经满员，请该商品的选择下一期！\");window.location.href=\"/mycart/index.html\";</script>", new String[] { "encoding:UTF-8" });
    }
    return null;
  }
  
  public String returnUrl()
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    
    ResponseHandler resHandler = new ResponseHandler(this.request, this.response);
    resHandler.setKey(TenpayConfig.key);
    System.out.println("前台回调返回参数:" + resHandler.getAllParameters());
    boolean flag = false;
    String buyproduct = "";
    if (resHandler.isTenpaySign())
    {
      String notify_id = resHandler.getParameter("notify_id");
      
      String out_trade_no = resHandler.getParameter("out_trade_no");
      
      String transaction_id = resHandler.getParameter("transaction_id");
      
      String total_fee = resHandler.getParameter("total_fee");
      
      String discount = resHandler.getParameter("discount");
      
      String trade_state = resHandler.getParameter("trade_state");
      
      String trade_mode = resHandler.getParameter("trade_mode");
      
      String integral = resHandler.getParameter("attach");
      
      System.err.println("returnUrl   integral:" + integral);
      if ("1".equals(trade_mode))
      {
        if ("0".equals(trade_state))
        {
          try
          {
            String key = MD5Util.encode(transaction_id);
            if (AliyunOcsSampleHelp.getIMemcachedCache().get(key) != null) {
            	return "success";
            }
            AliyunOcsSampleHelp.getIMemcachedCache().set(key, 43200, "y");
            this.productCartList = new ArrayList();
            this.successCartList = new ArrayList();
            try
            {
              this.consumetable = this.consumetableService.findByOutTradeNo(out_trade_no);
              double money = Double.parseDouble(String.valueOf(total_fee));
              money *= 0.01D;
              System.err.println(this.consumetable.getMoney());
              System.err.println(money);
              if (this.consumetable.getMoney().equals(Double.valueOf(money)))
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
                    if (cookie.getName().equals("products"))
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
                      List<Object[]> proList = this.spellbuyproductService.findByProductId(Integer.parseInt(obj.getString("pId")));
                      this.product = ((Product)((Object[])proList.get(0))[0]);
                      this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
                      if (this.spellbuyproduct.getSpStatus().intValue() == 0)
                      {
                        Integer count = Integer.valueOf(0);
                        
                        Integer CurrentPrice = this.spellbuyproduct.getSpellbuyCount();
                        if (this.spellbuyproduct.getSpellbuyCount().intValue() + obj.getInt("num") > this.spellbuyproduct.getSpellbuyPrice().intValue()) {
                          count = Integer.valueOf(this.spellbuyproduct.getSpellbuyPrice().intValue() - this.spellbuyproduct.getSpellbuyCount().intValue());
                        } else {
                          count = Integer.valueOf(obj.getInt("num"));
                        }
                        moneyCount = Integer.valueOf(moneyCount.intValue() + count.intValue());
                        productCount = Integer.valueOf(productCount.intValue() + 1);
                        this.productCart.setCount(count);
                        this.productCart.setHeadImage(this.product.getHeadImage());
                        this.productCart.setMoneyCount(moneyCount);
                        this.productCart.setProductCount(productCount);
                        this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
                        this.productCart.setProductName(this.product.getProductName());
                        this.productCart.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
                        this.productCart.setProductTitle(this.product.getProductTitle());
                        this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
                        this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
                        this.productCartList.add(this.productCart);
                        flag = true;
                      }
                      else
                      {
                        buyproduct = buyproduct + "您购买的商品中 <a href=\"" + ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/products/" + this.spellbuyproduct.getSpellbuyProductId() + ".html\" targer=\"_blank\">" + this.product.getProductName() + "</a>  已经满员.<br/>";
                      }
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
                      this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(productCart.getProductId().toString()));
                      
                      Integer count = Integer.valueOf(0);
                      
                      Integer CurrentPrice = this.spellbuyproduct.getSpellbuyCount();
                      if (this.spellbuyproduct.getSpellbuyCount().intValue() + productCart.getCount().intValue() > productCart.getProductPrice().intValue()) {
                        count = Integer.valueOf(productCart.getProductPrice().intValue() - this.spellbuyproduct.getSpellbuyCount().intValue());
                      } else {
                        count = productCart.getCount();
                      }
                      if (count.intValue() > 0)
                      {
                        if ((StringUtil.isNotBlank(integral)) || (!integral.equals("0")))
                        {
                          if (this.user.getBalance().doubleValue() >= count.intValue() - Integer.parseInt(integral) / 100)
                          {
                            Double temp = Double.valueOf(this.user.getBalance().doubleValue() - (count.intValue() - Integer.parseInt(integral) / 100));
                            this.user.setBalance(temp);
                            

                            this.consumerdetail = new Consumerdetail();
                            this.consumerdetail.setBuyCount(count);
                            this.consumerdetail.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                            this.consumerdetail.setConsumetableId(out_trade_no);
                            this.consumerdetail.setProductId(productCart.getProductId());
                            this.consumerdetail.setProductName(productCart.getProductName());
                            this.consumerdetail.setProductPeriod(productCart.getProductPeriod());
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
                          this.consumerdetail.setConsumetableId(out_trade_no);
                          this.consumerdetail.setProductId(productCart.getProductId());
                          this.consumerdetail.setProductName(productCart.getProductName());
                          this.consumerdetail.setProductPeriod(productCart.getProductPeriod());
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
                        this.spellbuyproduct.setSpellbuyCount(Integer.valueOf(this.spellbuyproduct.getSpellbuyCount().intValue() + count.intValue()));
                        if (this.spellbuyproduct.getSpellbuyCount().intValue() >= productCart.getProductPrice().intValue())
                        {
                          this.spellbuyproduct.setSpellbuyCount(productCart.getProductPrice());
                          
                          this.spellbuyproduct.setSpStatus(Integer.valueOf(2));
                          this.spellbuyproduct.setSpellbuyEndDate(DateUtil.DateTimeToStr(DateUtil.subMinute(new Date(), -11)));
                          try
                          {
                            this.lotteryproductutil = new Lotteryproductutil();
                            this.lotteryproductutil.setLotteryProductEndDate(DateUtil.DateTimeToStr(DateUtil.subMinute(new Date(), -11)));
                            this.lotteryproductutil.setLotteryProductId(this.spellbuyproduct.getSpellbuyProductId());
                            this.lotteryproductutil.setLotteryProductImg(productCart.getHeadImage());
                            this.lotteryproductutil.setLotteryProductName(productCart.getProductName());
                            this.lotteryproductutil.setLotteryProductPeriod(this.spellbuyproduct.getProductPeriod());
                            this.lotteryproductutil.setLotteryProductPrice(this.spellbuyproduct.getSpellbuyPrice());
                            this.lotteryproductutil.setLotteryProductTitle(productCart.getProductTitle());
                            this.lotteryproductutilService.add(this.lotteryproductutil);
                          }
                          catch (Exception localException1) {}
                        }
                        this.spellbuyproductService.add(this.spellbuyproduct);
                        
                        this.spellbuyrecord = new Spellbuyrecord();
                        this.spellbuyrecord.setFkSpellbuyProductId(this.spellbuyproduct.getSpellbuyProductId());
                        this.spellbuyrecord.setBuyer(this.user.getUserId());
                        this.spellbuyrecord.setBuyPrice(count);
                        this.spellbuyrecord.setBuyDate(DateUtil.DateTimeToStrBySSS(new Date()));
                        this.spellbuyrecord.setSpWinningStatus("0");
                        this.spellbuyrecord.setBuyStatus("0");
                        this.spellbuyrecord.setSpRandomNo("");
                        this.spellbuyrecordService.add(this.spellbuyrecord);
                        this.randomnumber = new Randomnumber();
                        this.randomnumber.setProductId(productCart.getProductId());
                        
                        List<Randomnumber> RandomnumberList = this.randomnumberService.query(" from Randomnumber where productId='" + this.spellbuyproduct.getSpellbuyProductId() + 
                          "'");
                        List<String> oldRandomList = new ArrayList();
                        for (Randomnumber randomnumber : RandomnumberList) {
                          if (randomnumber.getRandomNumber().contains(","))
                          {
                            String[] rs = randomnumber.getRandomNumber().split(",");
                            for (String string : rs) {
                              oldRandomList.add(string);
                            }
                          }
                          else
                          {
                            oldRandomList.add(randomnumber.getRandomNumber());
                          }
                        }
                        this.randomnumber.setRandomNumber(RandomUtil.newRandom(count.intValue(), this.spellbuyproduct.getSpellbuyPrice().intValue(), oldRandomList));
                        
                        this.randomnumber.setSpellbuyrecordId(this.spellbuyrecord.getSpellbuyRecordId());
                        this.randomnumber.setBuyDate(this.spellbuyrecord.getBuyDate());
                        this.randomnumber.setUserId(Integer.valueOf(Integer.parseInt(this.userId)));
                        this.randomnumberService.add(this.randomnumber);
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
                        this.productJSON.setBuyDate(this.spellbuyrecord.getBuyDate());
                        this.productJSON.setProductId(productCart.getProductId());
                        this.productJSON.setProductName(productCart.getProductName());
                        this.productJSON.setProductPeriod(productCart.getProductPeriod());
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
                    Cookie cookie = new Cookie("products", null);
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
            this.request.setAttribute("buyproduct", buyproduct);
            System.out.println("即时到帐付款成功");
            this.paymentStatus = "success";
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }
        else
        {
          System.out.println("即时到帐付款失败");
          this.paymentStatus = "error";
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
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
    String buyproduct = "";
    
    ResponseHandler resHandler = new ResponseHandler(this.request, this.response);
    resHandler.setKey(TenpayConfig.key);
    System.out.println("后台回调返回参数:" + resHandler.getAllParameters());
    if (resHandler.isTenpaySign())
    {
      String notify_id = resHandler.getParameter("notify_id");
      
      RequestHandler queryReq = new RequestHandler(null, null);
      
      TenpayHttpClient httpClient = new TenpayHttpClient();
      
      ClientResponseHandler queryRes = new ClientResponseHandler();
      
      String integral = resHandler.getParameter("attach");
      

      queryReq.init();
      queryReq.setKey(TenpayConfig.key);
      queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
      queryReq.setParameter("partner", TenpayConfig.partner);
      queryReq.setParameter("notify_id", notify_id);
      queryReq.setParameter("attach", integral);
      
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
  
  public String getRequestUrl()
  {
    return this.requestUrl;
  }
  
  public void setRequestUrl(String requestUrl)
  {
    this.requestUrl = requestUrl;
  }
  
  public String getHidUseBalance()
  {
    return this.hidUseBalance;
  }
  
  public void setHidUseBalance(String hidUseBalance)
  {
    this.hidUseBalance = hidUseBalance;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public Consumetable getConsumetable()
  {
    return this.consumetable;
  }
  
  public void setConsumetable(Consumetable consumetable)
  {
    this.consumetable = consumetable;
  }
  
  public List<ProductCart> getProductCartList()
  {
    return this.productCartList;
  }
  
  public void setProductCartList(List<ProductCart> productCartList)
  {
    this.productCartList = productCartList;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public Consumerdetail getConsumerdetail()
  {
    return this.consumerdetail;
  }
  
  public void setConsumerdetail(Consumerdetail consumerdetail)
  {
    this.consumerdetail = consumerdetail;
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
  
  public String getPaymentStatus()
  {
    return this.paymentStatus;
  }
  
  public void setPaymentStatus(String paymentStatus)
  {
    this.paymentStatus = paymentStatus;
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
  
  public Lotteryproductutil getLotteryproductutil()
  {
    return this.lotteryproductutil;
  }
  
  public void setLotteryproductutil(Lotteryproductutil lotteryproductutil)
  {
    this.lotteryproductutil = lotteryproductutil;
  }
}
