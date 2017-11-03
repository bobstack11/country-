package com.egouos.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.egouos.alipay.config.AlipayConfig;
import com.egouos.alipay.util.AlipayNotify;
import com.egouos.alipay.util.AlipaySubmit;
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
import com.egouos.util.ConfigUtil;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.NewLotteryUtil;
import com.egouos.util.RandomUtil;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;

public class AlipayAction extends ActionSupport
{
  private static final long serialVersionUID = -8970081284102469306L;
  
  private static final Logger LOG = LoggerFactory.getLogger(AlipayAction.class);
  private static String secret = ConfigUtil.getValue("secret", "1yyg1234567890");
  // consts
  private static final int VF_FAIL      = -1;
  private static final int PS_FAID_BUY  = 0x1;
  private static final int PS_FAID_REC  = 0x11;
  private static final int PS_TIMEO_BUY = 0x3;
  
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
  private String payName;
  private Integer moneyCount;
  private String integral;
  private String out_trade_no;
  private String token;
  private String currTime = TenpayUtil.getCurrTime();
  private String strTime = this.currTime.substring(8, this.currTime.length());
  //四位随机数
	private String strRandom = TenpayUtil.buildRandom(4) + "";
  private String strReq = this.strTime + this.strRandom;
  Random random = new Random();
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String goPay()
  {
    request = Struts2Utils.getRequest();
    response = Struts2Utils.getResponse();
    
    TreeMap<String, String> map = new TreeMap<String, String>();
	map.put("moneyCount", moneyCount.toString());
	map.put("out_trade_no", out_trade_no);
	String mysign="";
	try {
		mysign = getSign(map);
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
	if(!StringUtil.equals(token, mysign)){
		Struts2Utils.render("text/html",
				"<script>alert(\"验证失败，请联系客服！\");window.location.href=\"/index.html\";</script>",
				StringUtil.ENCA_UTF8);
		return null;
	}
	
    final SysConfigure conf = ApplicationListenerImpl.sysConfigureJson;
    String notify_url = conf.getWeixinUrl() + "/alipay/notifyUrl.action";
	String return_url = conf.getWeixinUrl() + "/alipay/returnUrl.action";
    String seller_email = AlipayConfig.mail;
    String subject = conf.getSaitName();
    String total_fee = moneyCount.toString();
    String body = conf.getSaitName();
    String anti_phishing_key = currTime;
    String exter_invoke_ip = Struts2Utils.getRemoteIp();
    Map<String, String> sParaTemp = new HashMap<String, String>();
    sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
    sParaTemp.put("partner", AlipayConfig.partner);
    sParaTemp.put("seller_id", AlipayConfig.partner);
    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
    sParaTemp.put("payment_type", "1");
    sParaTemp.put("notify_url", notify_url);
    sParaTemp.put("return_url", return_url);
    //sParaTemp.put("seller_email", seller_email);
    sParaTemp.put("out_trade_no", out_trade_no);
    sParaTemp.put("subject", subject);
    sParaTemp.put("total_fee", total_fee/*0.01*/+""); // 1分钱测试
    sParaTemp.put("show_url", conf.getWwwUrl());
    sParaTemp.put("body", body);
    //sParaTemp.put("it_b_pay", it_b_pay);	//选填:超时时间
	//sParaTemp.put("extern_token", extern_token);	//选填:钱包token
    String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
    LOG.debug(sHtmlText);
    Struts2Utils.render("text/html", sHtmlText, StringUtil.ENCA_UTF8);
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
  
  public String returnUrl() throws UnsupportedEncodingException
  {
	  switch(afterPaying()){
		case VF_FAIL:
			Struts2Utils.render("text/html", 
			  		  "<script>alert(\"验证失败，请联系客服！\");window.location.href=\"/index.html\";</script>", 
			  		  StringUtil.ENCA_UTF8);
			return null;
		case PS_FAID_BUY:
			Struts2Utils.render("text/html", 
      			  //"<script>window.location.href=\"/mycart/shopok.html?id=" + out_trade_no + "\";</script>",
					"<script>window.location.href=\"/index.html\";</script>",
      			  StringUtil.ENCA_UTF8);
			return null;
		case PS_TIMEO_BUY:
			Struts2Utils.render("text/html", 
			  		  "<script>alert(\"付款超时，请联系客服！\");window.location.href=\"/user/index.html\";</script>", 
			  		  StringUtil.ENCA_UTF8);
			return null;
		case PS_FAID_REC:
			Struts2Utils.render("text/html", 
	          		  "<script>window.location.href=\"/user/UserBalance.html\";</script>", 
	          		  StringUtil.ENCA_UTF8);
			return null;
		default:
			LOG.warn("Ooh");
			return null;
	  }
  }
  
  public String notifyUrl() throws Exception
  {
    switch(afterPaying()){
	case VF_FAIL:
		Struts2Utils.render("text/html", "fail", StringUtil.ENCA_UTF8);
		break;
	case PS_FAID_BUY:
	case PS_TIMEO_BUY:
	case PS_FAID_REC:
	default:
		Struts2Utils.render("text/html", "success", StringUtil.ENCA_UTF8);
    }
    return null;
  }

	private final int afterPaying() throws UnsupportedEncodingException{
		request = Struts2Utils.getRequest();
		response = Struts2Utils.getResponse();
		Map<String, String> params = new HashMap<String, String>();
	    Map<?,?> requestParams = request.getParameterMap();
	    for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();)
	    {
	      String name = (String)iter.next();
	      String[] values = (String[])requestParams.get(name);
	      params.put(name, values[0]);
	    }
	    out_trade_no = new String(request.getParameter("out_trade_no")
	    		.getBytes("ISO-8859-1"), "UTF-8");
	    final String trade_no = new String(request.getParameter("trade_no")
	    		.getBytes("ISO-8859-1"), "UTF-8");
	    final String trade_status = new String(request.getParameter("trade_status")
	    		.getBytes("ISO-8859-1"), "UTF-8");
	    
	    if (AlipayNotify.verify(params))
	    {
	      if (("TRADE_FINISHED".equals(trade_status)) || ("TRADE_SUCCESS".equals(trade_status)))
	      {
	        LOG.info("Alipay: 验证成功");
	    	final Consumetable cons = consumetableService.paid(out_trade_no, trade_no);
	    	if (StringUtil.isBlank(cons.getWithold()))
	        {
	    		return (cons.isPaid()?PS_FAID_BUY:PS_TIMEO_BUY);
	        }
	        else
	        {
	        	return (PS_FAID_REC);
	        }
		  }
	    }
	    return VF_FAIL; 
	}
  
  public String goPay2()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
    
    String payment_type = "1";
    


    String notify_url = AlipayConfig.notify_url;
    


    String return_url = AlipayConfig.return_url;
    

    String seller_email = AlipayConfig.mail;
    

    String out_trade_no = this.strReq;
    

    String subject = "商品购买";
    

    String total_fee = this.moneyCount.toString();
    

    String body = "商品购买";
    
    String show_url = "http://www.alipay.com";
    

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
          this.consumetable.setBuyCount(buyConut);
          this.consumetable.setDate(DateUtil.DateTimeToStr(new Date()));
          this.consumetable.setInterfaceType("aliPay");
          this.consumetable.setMoney(Double.valueOf(money));
          this.consumetable.setOutTradeNo(out_trade_no);
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
    String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
    System.out.println(sHtmlText);
    if (flag) {
      Struts2Utils.render("text/html", sHtmlText, new String[] { "encoding:UTF-8" });
    } else {
      Struts2Utils.render("text/html", "<script>alert(\"购物车中有商品已经满员，请该商品的选择下一期！\");window.location.href=\"/mycart/index.html\";</script>", new String[] { "encoding:UTF-8" });
    }
    return null;
  }
  
  public String returnUrl2()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
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
    
    String integral = new String(this.request.getParameter("extra_common_param").getBytes("ISO-8859-1"), "UTF-8");
    

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
            this.productCartList = new ArrayList();
            this.successCartList = new ArrayList();
            try
            {
              this.consumetable = this.consumetableService.findByOutTradeNo(out_trade_no);
              double money = Double.parseDouble(String.valueOf(total_fee));
              System.err.println(this.consumetable.getMoney());
              System.err.println(money);
              if ((this.consumetable.getMoney().equals(Double.valueOf(money))) && (this.consumetable.getTransactionId().equals(trade_no)))
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
                        if ((StringUtil.isNotBlank(integral)) && (!integral.equals("0")))
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
  
  public String notifyUrl2()
    throws Exception
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
    
    String integral = new String(this.request.getParameter("extra_common_param").getBytes("ISO-8859-1"), "UTF-8");
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
        }
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
  
  public String getOut_trade_no()
  {
    return out_trade_no;
  }
  
  public void setOut_trade_no(String out_trade_no)
  {
    this.out_trade_no = out_trade_no;
  }
  
  public String getPayName()
  {
    return payName;
  }
  
  public void setPayName(String payName)
  {
    this.payName = payName;
  }
  
  public String getToken() {
	return token;
	}
	
	public void setToken(String token) {
		this.token = token;
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
