package com.egouos.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.egouos.exception.RuleViolationException;
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
import com.egouos.util.NewLotteryUtil;
import com.egouos.util.RandomUtil;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.egouos.weixin.config.MD5Util;
import com.opensymphony.xwork2.ActionSupport;

@Component("MyCartAction")
public class MyCartAction extends ActionSupport
{
  private static final long serialVersionUID = 837685801735393306L;
  protected static final Logger LOG = LoggerFactory.getLogger(MyCartAction.class);
  private static String secret = ConfigUtil.getValue("secret", "1yyg1234567890");
  
  RandomUtil randomUtil = new RandomUtil();
  
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
  private String currTime = TenpayUtil.getCurrTime();
  private String strTime = this.currTime.substring(8, this.currTime.length());
  private String strRandom = TenpayUtil.buildRandom(4)+"";
  private String strReq = this.strTime + this.strRandom;
  private String out_trade_no;
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
  private String forward;
  private String hidUseBalance;
  private Integer bankMoney;
  private String payName;
  
  Random random = new Random();
  Calendar calendar = Calendar.getInstance();
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String index()
    throws UnsupportedEncodingException
  {
    /*if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      return null;
    }*/
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    this.productCartList = new ArrayList();
    Cookie[] cookies = this.request.getCookies();
    JSONArray array = null;
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("products"))
        {
          String product = StringUtil.getUTF8URLDecoder(cookie.getValue());
          if ((product != null) && (!product.equals(""))) {
            array = JSONArray.fromObject(product);
          }
        }
      }
    }
    Integer moneyCount = Integer.valueOf(0);
    Integer productCount = Integer.valueOf(0);
    JSONArray array2 = new JSONArray();
    if ((array != null) && (!array.toString().equals("[{}]"))) {
      for (int i = 0; i < array.size(); i++) {
        try
        {
          JSONObject obj = (JSONObject)array.get(i);
          this.productCart = new ProductCart();
          List<Object[]> proList = this.spellbuyproductService.findByProductId(Integer.parseInt(obj.getString("pId")));
          if(!(proList!=null && proList.size()>0 && proList.get(0)!=null && ((Object[])proList.get(0)).length>0)){
        	  continue;
          }
          Object[] objects = (Object[])proList.get(0);
          for(Object object:objects){
        		if(object instanceof Product){
        			product = (Product)object;
        		}
        		if(object instanceof Spellbuyproduct){
        			spellbuyproduct = (Spellbuyproduct)object;
        		}
        	  }
          //this.product = ((Product)((Object[])proList.get(0))[0]);
          //this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
          if (this.spellbuyproduct.getSpStatus().intValue() == 0)
          {
        	  array2.add(array.get(i));
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
            this.productCart.setMoneyCount(moneyCount);
            this.productCart.setHeadImage(this.product.getHeadImage());
            this.productCart.setProductCount(productCount);
            this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
            this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
            this.productCart.setProductName(this.product.getProductName());
            this.productCart.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
            this.productCart.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
            this.productCart.setProductTitle(this.product.getProductTitle());
            this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
            this.productCartList.add(this.productCart);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      //清除不存在的购物车记录，重新写回cookie
      Cookie cookie = new Cookie("products", StringUtil.getUTF8URLEncoder(array2.toString()));
      cookie.setMaxAge(24*3600);
      cookie.setPath("/");
      cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
      this.response.addCookie(cookie);
    }
    return "index";
  }
  
  public String getProductCartCount()
    throws UnsupportedEncodingException
  {
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    JSONArray array = null;
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("products"))
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
          List<Object[]> proList = this.spellbuyproductService.findByProductId(Integer.parseInt(obj.getString("pId")));
          Object[] objects = (Object[])proList.get(0);
          for(Object object:objects){
        		if(object instanceof Product){
        			product = (Product)object;
        		}
        		if(object instanceof Spellbuyproduct){
        			spellbuyproduct = (Spellbuyproduct)object;
        		}
        	  }
          //this.product = ((Product)((Object[])proList.get(0))[0]);
          //this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
          if (this.spellbuyproduct.getSpStatus().intValue() == 0) {
            productCount = Integer.valueOf(productCount.intValue() + 1);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    Struts2Utils.renderText(String.valueOf(productCount), new String[0]);
    return null;
  }
  
  public String getMyProductCart()
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
          Object[] objects = (Object[])proList.get(0);
          for(Object object:objects){
        		if(object instanceof Product){
        			product = (Product)object;
        		}
        		if(object instanceof Spellbuyproduct){
        			spellbuyproduct = (Spellbuyproduct)object;
        		}
        	  }
          //this.product = ((Product)((Object[])proList.get(0))[0]);
          //this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
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
            this.productCart.setMoneyCount(moneyCount);
            this.productCart.setHeadImage(this.product.getHeadImage());
            this.productCart.setProductCount(productCount);
            this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
            this.productCart.setProductName(this.product.getProductName());
            this.productCart.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
            this.productCart.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
            this.productCart.setProductTitle(this.product.getProductTitle());
            this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
            this.productCartList.add(this.productCart);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    Struts2Utils.renderJson(this.productCartList, new String[0]);
    return null;
  }
  
  public void buyProductClick()
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
        if (cookie.getName().equals("products"))
        {
          String product = StringUtil.getUTF8URLDecoder(cookie.getValue());
          if ((product != null) && (!product.equals(""))) {
            array = JSONArray.fromObject(product);
          }
        }
      }
    }
    Integer moneyCount = Integer.valueOf(0);
    if ((array != null) && (!array.toString().equals("[{}]")))
    {
      for (int i = 0; i < array.size(); i++)
      {
        JSONObject obj = (JSONObject)array.get(i);
        moneyCount = Integer.valueOf(moneyCount.intValue() + obj.getInt("num"));
      }
      StringBuilder sb = new StringBuilder();
      
      sb.append('{');
      sb.append("\"productCount\":\"").append(array.size()).append("\",");
      sb.append("\"moneyCount\":\"").append(moneyCount).append("\"");
      sb.append('}');
      sb.append(",");
      sb.deleteCharAt(sb.length() - 1);
      
      Struts2Utils.renderJson(sb.toString(), new String[0]);
    }
    else
    {
      StringBuilder sb = new StringBuilder();
      
      sb.append('{');
      sb.append("\"productCount\":\"").append(0).append("\",");
      sb.append("\"moneyCount\":\"").append(0).append("\"");
      sb.append('}');
      sb.append(",");
      sb.deleteCharAt(sb.length() - 1);
      
      Struts2Utils.renderJson(sb.toString(), new String[0]);
    }
  }
  
  public String payment()
    throws UnsupportedEncodingException
  {
    request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    productCartList = new ArrayList();
    JSONArray array = null;
    if (request.isRequestedSessionIdFromCookie())
    {
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
        if (cookie.getName().equals("products"))
        {
          String product = StringUtil.getUTF8URLDecoder(cookie.getValue());
          if ((product != null) && (!product.equals(""))) {
            array = JSONArray.fromObject(product);
          }
        }
      }
      if (StringUtil.isNotBlank(this.userId))
      {
        Integer moneyCount = Integer.valueOf(0);
        Integer productCount = Integer.valueOf(0);
        if ((array != null) && (!array.toString().equals("[{}]"))) {
          for (int j = 0; j < array.size(); j++) {
            try
            {
              JSONObject obj = (JSONObject)array.get(j);
              this.productCart = new ProductCart();
              List<Object[]> proList = this.spellbuyproductService.findByProductId(Integer.parseInt(obj.getString("pId")));
              Object[] objects = (Object[])proList.get(0);
              for(Object object:objects){
            		if(object instanceof Product){
            			product = (Product)object;
            		}
            		if(object instanceof Spellbuyproduct){
            			spellbuyproduct = (Spellbuyproduct)object;
            		}
            	}
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
                this.productCart.setMoneyCount(moneyCount);
                this.productCart.setHeadImage(this.product.getHeadImage());
                this.productCart.setProductCount(productCount);
                this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
                this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
                this.productCart.setProductName(this.product.getProductName());
                this.productCart.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
                this.productCart.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
                this.productCart.setProductTitle(this.product.getProductTitle());
                this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
                this.productCartList.add(this.productCart);
              }
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
          }
        }
        return "payment";
      }
    }
    return "login_index";
  }
  
  public String goPay()
    throws UnsupportedEncodingException, InterruptedException
  {
    request = Struts2Utils.getRequest();
    response = Struts2Utils.getResponse();
    boolean flag = false;
    String buyproduct = "";
    Cookie[] cookies = this.request.getCookies();
    productCartList = new ArrayList();
    successCartList = new ArrayList();
    JSONArray products = null;
    if (request.isRequestedSessionIdFromCookie()) {
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
        	  products = JSONArray.fromObject(product);
          }
        }
      }
    }
 // user: must have logined for paying
 		if (StringUtil.isNotBlank(userId)) {
 			if ((products != null) && (!products.toString().equals("[]"))) {
 				Map<String, Integer> protbl = new HashMap<String, Integer>();
 				// pay: product by product
 				for (int i = 0; i < products.size(); i++) {
 					// pro - pId
 					final JSONObject pro = (JSONObject) products.get(i);
 					// id - selected product id list
 					if (StringUtil.isNotBlank(id)) {
 						String[] ids = id.split(",");
 						for (String s : ids) {
 							if (s.equals(pro.getString("pId"))) {
 								protbl.put(s, pro.getInt("num"));
 								break;
 							}
 						}
 					} else {
 						protbl.put(pro.getString("pId"), pro.getInt("num"));
 					}
 				}// for-products
 				// add order!
 				successCartList = new ArrayList<ProductJSON>(protbl.size());
 				productCartList = new ArrayList<ProductCart>(protbl.size());
 				String addOrderError = null;
 				try{
 					final String buyIp = Struts2Utils.getRemoteIp();
 					consumetable = initOrder(hidUseBalance, integral, bankMoney, buyIp);
 					consumetable = userService.addOrder(userId, protbl, productCartList, 
 							consumetable, integral);
 					userPayType = consumetable.getUserPayType();
 					out_trade_no= consumetable.getOutTradeNo();
 					// moneyCount: used to pay by the 3rd part or bank!
 					moneyCount  = consumetable.getMoney().intValue();
 				}catch(final RuleViolationException e){
 					if(consumetable.isPayByT3rdPart()){
 						addOrderError = e.getMessage();
 						Struts2Utils.render("text/html", "<script>alert(\"发生错误！"+e.getMessage()+"\");window.location.href=\"/mycart/index.html\";</script>", StringUtil.ENCA_UTF8);
 						return null;
 					}else{
 						Struts2Utils.render("text/html", "<script>alert(\"发生错误！"+e.getMessage()+"\");window.location.href=\"/mycart/index.html\";</script>", StringUtil.ENCA_UTF8);
 						//Struts2Utils.render("application/json", "{\"error\":\""+e.getMessage()+"\"}");
 						return null;
 					}
 				}catch(final RuntimeException e){
 					LOG.error("{}", e);
 					if(consumetable.isPayByT3rdPart()){
 						addOrderError = "出现异常情况";
 						Struts2Utils.render("text/html", "<script>alert(\"出现异常情况\");history.go(-1);</script>", StringUtil.ENCA_UTF8);
 						return null;
 					}else{
 						Struts2Utils.render("text/html", "<script>alert(\"出现异常情况\");history.go(-1);</script>", StringUtil.ENCA_UTF8);
 						//Struts2Utils.render("application/json", "{\"error\":\"出现异常情况\"}");
 						return null;
 					}
 				}
 				// calc money and select pay-style
 				//LOG.info("payName: {}, payBank: {}", payName, payBank);
 				if (userPayType==Consumetable.UPAY_TYPE_CYBBANK && StringUtil.isNotBlank(payName)) {
 					// pay by the 3rd part
 					payBy3rdPart(productCartList, userPayType, userId, integral,
 							bankMoney, hidUseBalance, out_trade_no, moneyCount, addOrderError);
 				} else {
 					// pay by balance or integral
 					try{
 						userService.payOrPaid(userId, productCartList, successCartList, 
 							userPayType, integral, bankMoney, hidUseBalance, out_trade_no);
 						Struts2Utils.render("text/html", "<script>alert(\"支付成功！\");window.location.href=\"/user/UserBuyList.html\";</script>", StringUtil.ENCA_UTF8);
 					}catch(final RuleViolationException e){
 						Struts2Utils.render("text/html", 
 							"<script>alert(\""+e.getMessage()+"\");window.location.href=\"/mycart/index.html\";</script>", 
 							StringUtil.ENCA_UTF8);
 					}
 				}
 				//refreshCarts(successCartList, products, out_trade_no);
 				//清空购物车
 				clearCarts();
 			} else {
 				// no product selected!
 				Struts2Utils.render("text/html", "<script>alert(\"购物车没有商品！\");history.go(-1);</script>", StringUtil.ENCA_UTF8);
 			}
 		} else {
 			// not login!
 			Struts2Utils.render("text/html", "<script>alert(\"你还没有登录！\");</script>", StringUtil.ENCA_UTF8);
 		}
 		return null;
 		
 		
    /*Integer moneyCount = 0;
    Integer productCount = 0;
    Integer count=0;
    if ((products != null) && (!products.toString().equals("[{}]"))) {
      for (int i = 0; i < products.size(); i++) {
        try
        {
          JSONObject obj = (JSONObject)products.get(i);
          this.productCart = new ProductCart();
          List<Object[]> proList = this.spellbuyproductService.findByProductId(Integer.parseInt(obj.getString("pId")));
          this.product = ((Product)((Object[])proList.get(0))[0]);
          this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
          if (this.spellbuyproduct.getSpStatus().intValue() == 0)
          {
            count = Integer.valueOf(0);
            
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
            this.productCart.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
            this.productCart.setProductTitle(this.product.getProductTitle());
            this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
            this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
            this.productCartList.add(this.productCart);
            flag = true;
          }
          else
          {
            Struts2Utils.render("text/html", "<script>alert(\"购物车中有商品已经满员，请重新购买！\");window.location.href=\"/mycart/index.html\";</script>", StringUtil.ENCA_UTF8);
            flag = false;
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
    } else {
      flag = false;
    }
    if (flag)
    {
      this.consumetable = new Consumetable();
      double money = Double.parseDouble(String.valueOf(moneyCount));
      this.consumetable.setBuyCount(moneyCount);
      this.consumetable.setDate(DateUtil.DateTimeToStr(new Date()));
      if (this.userPayType.intValue() == 1) {
        this.consumetable.setInterfaceType("余额支付");
      } else if (this.userPayType.intValue() == 2) {
        this.consumetable.setInterfaceType("积分抵扣");
      } else {
        this.consumetable.setInterfaceType("积分+余额");
      }
      this.consumetable.setMoney(Double.valueOf(money));
      this.consumetable.setOutTradeNo(this.out_trade_no);
      this.consumetable.setUserId(Integer.valueOf(Integer.parseInt(this.userId)));
      this.consumetableService.add(this.consumetable);
      for (ProductCart productCart : this.productCartList)
      {
        try
        {
          consumerdetail = new Consumerdetail();
          spellbuyrecord = new Spellbuyrecord();
          productJSON = new ProductJSON();
          commissionquery = new Commissionquery();
          spellbuyproduct = ((Spellbuyproduct)spellbuyproductService.findById(productCart.getProductId().toString()));
          
          Integer CurrentPrice = this.spellbuyproduct.getSpellbuyCount();
          if (this.spellbuyproduct.getSpellbuyCount().intValue() + productCart.getCount().intValue() > productCart.getProductPrice().intValue()) {
            count = Integer.valueOf(productCart.getProductPrice().intValue() - this.spellbuyproduct.getSpellbuyCount().intValue());
          } else {
            count = productCart.getCount();
          }
          if (count.intValue() > 0)
          {
            try
            {
              if (this.userPayType.intValue() == 1)
              {
                if (this.user.getBalance().doubleValue() >= count.intValue())
                {
                  Double temp = Double.valueOf(this.user.getBalance().doubleValue() - count.intValue());
                  Integer points = this.user.getCommissionPoints();
                  this.user.setBalance(temp);
                  this.user.setCommissionPoints(Integer.valueOf(points.intValue() + count.intValue() * ApplicationListenerImpl.sysConfigureJson.getBuyProduct().intValue()));
                  this.consumerdetail.setBuyCount(count);
                  this.consumerdetail.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                  this.consumerdetail.setConsumetableId(this.out_trade_no);
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
                    this.commissionquery.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                    this.commissionquery.setCommission(Double.valueOf(Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                    this.commissionquery.setDate(DateUtil.DateTimeToStr(new Date()));
                    this.commissionquery.setDescription(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + productCart.getProductId() + ")获得佣金");
                    this.commissionquery.setInvitedId(this.user.getInvite());
                    this.commissionquery.setToUserId(this.user.getUserId());
                    this.commissionqueryService.add(this.commissionquery);
                  }
                  this.commissionpoints = new Commissionpoints();
                  this.commissionpoints.setDate(DateUtil.DateTimeToStr(new Date()));
                  this.commissionpoints.setDetailed(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + this.spellbuyproduct.getSpellbuyProductId() + ")支付" + count + "元获得积分");
                  this.commissionpoints.setPay("+" + count.intValue() * ApplicationListenerImpl.sysConfigureJson.getBuyProduct().intValue());
                  this.commissionpoints.setToUserId(Integer.valueOf(Integer.parseInt(this.userId)));
                  this.commissionpointsService.add(this.commissionpoints);
                }
                else
                {
                  Struts2Utils.render("text/html", "<script>alert(\"您的余额不足，无法完成支付\");window.location.href=\"/mycart/index.html\";</script>", StringUtil.ENCA_UTF8);
                  flag = false;
                  break;
                }
              }
              else if (this.userPayType.intValue() == 2)
              {
                if (this.user.getCommissionPoints().intValue() / 100 >= count.intValue())
                {
                  Integer points = this.user.getCommissionPoints();
                  this.user.setCommissionPoints(Integer.valueOf(points.intValue() - Integer.parseInt(this.integral)));
                  
                  this.consumerdetail.setBuyCount(count);
                  this.consumerdetail.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                  this.consumerdetail.setConsumetableId(this.out_trade_no);
                  this.consumerdetail.setProductId(productCart.getProductId());
                  this.consumerdetail.setProductName(productCart.getProductName());
                  this.consumerdetail.setProductPeriod(productCart.getProductPeriod());
                  this.consumerdetail.setProductTitle(productCart.getProductTitle());
                  this.consumerdetailService.add(this.consumerdetail);
                  
                  this.commissionpoints = new Commissionpoints();
                  this.commissionpoints.setDate(DateUtil.DateTimeToStr(new Date()));
                  this.commissionpoints.setDetailed(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + this.spellbuyproduct.getSpellbuyProductId() + ")积分抵扣");
                  this.commissionpoints.setPay("-" + this.integral);
                  this.commissionpoints.setToUserId(Integer.valueOf(Integer.parseInt(this.userId)));
                  this.commissionpointsService.add(this.commissionpoints);
                }
                else
                {
                  Struts2Utils.render("text/html", "<script>alert(\"您的余额不足，无法完成支付\");window.location.href=\"/mycart/index.html\";</script>", StringUtil.ENCA_UTF8);
                  flag = false;
                  break;
                }
              }
              else
              {
                if (this.user.getBalance().doubleValue() >= Integer.parseInt(this.hidUseBalance))
                {
                  Double temp = Double.valueOf(this.user.getBalance().doubleValue() - Integer.parseInt(this.hidUseBalance));
                  Integer points = this.user.getCommissionPoints();
                  this.user.setBalance(temp);
                  this.user.setCommissionPoints(Integer.valueOf(points.intValue() + Integer.parseInt(this.hidUseBalance)));
                  if (this.user.getInvite() != null)
                  {
                    User userCommission = (User)this.userService.findById(String.valueOf(this.user.getInvite()));
                    double tempCommissionCount = userCommission.getCommissionCount().doubleValue();
                    double commissionBalance = this.user.getCommissionBalance().doubleValue();
                    userCommission.setCommissionCount(Double.valueOf(tempCommissionCount += Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                    userCommission.setCommissionBalance(Double.valueOf(commissionBalance += Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                    this.userService.add(userCommission);
                    this.commissionquery.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(this.hidUseBalance))));
                    this.commissionquery.setCommission(Double.valueOf(Double.parseDouble(String.valueOf(this.hidUseBalance)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                    this.commissionquery.setDate(DateUtil.DateTimeToStr(new Date()));
                    this.commissionquery.setDescription(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + productCart.getProductId() + ")获得佣金");
                    this.commissionquery.setInvitedId(this.user.getInvite());
                    this.commissionquery.setToUserId(this.user.getUserId());
                    this.commissionqueryService.add(this.commissionquery);
                  }
                  this.commissionpoints = new Commissionpoints();
                  this.commissionpoints.setDate(DateUtil.DateTimeToStr(new Date()));
                  this.commissionpoints.setDetailed(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + this.spellbuyproduct.getSpellbuyProductId() + ")支付" + this.hidUseBalance + "元获得积分");
                  this.commissionpoints.setPay("+" + this.hidUseBalance);
                  this.commissionpoints.setToUserId(Integer.valueOf(Integer.parseInt(this.userId)));
                  this.commissionpointsService.add(this.commissionpoints);
                }
                else
                {
                  flag = false;
                  break;
                }
                if (this.user.getCommissionPoints().intValue() >= Integer.parseInt(this.integral))
                {
                  Integer points = this.user.getCommissionPoints();
                  this.user.setCommissionPoints(Integer.valueOf(points.intValue() - Integer.parseInt(this.integral)));
                  
                  this.commissionpoints = new Commissionpoints();
                  this.commissionpoints.setDate(DateUtil.DateTimeToStr(new Date()));
                  this.commissionpoints.setDetailed(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + this.spellbuyproduct.getSpellbuyProductId() + ")积分抵扣");
                  this.commissionpoints.setPay("-" + this.integral);
                  this.commissionpoints.setToUserId(Integer.valueOf(Integer.parseInt(this.userId)));
                  this.commissionpointsService.add(this.commissionpoints);
                }
                else
                {
                  flag = false;
                  break;
                }
                this.consumerdetail.setBuyCount(count);
                this.consumerdetail.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                this.consumerdetail.setConsumetableId(this.out_trade_no);
                this.consumerdetail.setProductId(productCart.getProductId());
                this.consumerdetail.setProductName(productCart.getProductName());
                this.consumerdetail.setProductPeriod(productCart.getProductPeriod());
                this.consumerdetail.setProductTitle(productCart.getProductTitle());
                this.consumerdetailService.add(this.consumerdetail);
              }
            }
            catch (Exception e)
            {
              flag = false;
              break;
            }
            if (flag)
            {
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
              
              List<Randomnumber> RandomnumberList = this.randomnumberService.query(" from Randomnumber where productId='" + this.spellbuyproduct.getSpellbuyProductId() + "'");
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
              
              Integer experience = this.user.getExperience();
              experience = Integer.valueOf(experience.intValue() + count.intValue() * 10);
              this.user.setExperience(experience);
              this.userService.add(this.user);
              

              this.productJSON.setBuyDate(this.spellbuyrecord.getBuyDate());
              this.productJSON.setProductId(productCart.getProductId());
              this.productJSON.setProductName(productCart.getProductName());
              this.productJSON.setProductPeriod(productCart.getProductPeriod());
              this.productJSON.setProductTitle(productCart.getProductTitle());
              this.productJSON.setBuyCount(count);
              this.successCartList.add(this.productJSON);
            }
          }
        }
        catch (Exception e)
        {
          flag = false;
          e.printStackTrace();
        }
      }
    }
    if ((flag) && 
      (this.request.isRequestedSessionIdFromCookie()))
    {
      Cookie cookie = new Cookie("products", null);
      cookie.setMaxAge(0);
      cookie.setPath("/");
      cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
      this.response.addCookie(cookie);
    }
    return "success";*/
  }
  
  protected Consumetable initOrder(final String useBalance, final String integral,
		  final Integer bankMoney, final String buyIp){
	final Consumetable consumetable = new Consumetable();
	final int bm = bankMoney;
	consumetable.setPayStatus(Consumetable.PAY_STAT_NPAID);
	if (("0".equals(integral)) && (bm == 0)) {
		consumetable.setInterfaceType("余额支付");
		consumetable.setUserPayType(Consumetable.UPAY_TYPE_BALANCE);
	} else if ((!"0".equals(integral)) && ("0".equals(useBalance)) && (bm == 0)) {
		consumetable.setInterfaceType("积分抵扣");
		consumetable.setUserPayType(Consumetable.UPAY_TYPE_WELFARE);
	} else if ((!"0".equals(integral)) && (!"0".equals(useBalance)) && (bm == 0)) {
		consumetable.setInterfaceType("积分+余额");
		consumetable.setUserPayType(Consumetable.UPAY_TYPE_BALWELF);
	} else {
		// 可能有部分余额和积分！
		consumetable.setInterfaceType("网银支付");
		consumetable.setUserPayType(Consumetable.UPAY_TYPE_CYBBANK);
	}
	consumetable.setBalance(Integer.valueOf(useBalance));
	consumetable.setIntegral(Integer.valueOf(integral));
	consumetable.setBankMoney(bankMoney);
	consumetable.setBuyIp(buyIp);
	return consumetable;
}
  
  protected boolean refreshCarts(List<ProductJSON> successCartList, final JSONArray products, 
			final String out_trade_no) throws UnsupportedEncodingException{
		if (successCartList.size() > 0) {
			for (int i = 0; i < successCartList.size(); i++) {
				final ProductJSON productJSON = successCartList.get(i);
				for (int j = 0; j < products.size(); ++j) {
					final JSONObject json = (JSONObject) products.get(j);
					final Integer pid = json.getInt("pId");
					if (productJSON.getProductId().equals(pid)) {
						products.remove(j);
						break;
					}
				}
			}
			final String product = StringUtil.getUTF8URLEncoder(products.toString());
			Cookie cookie = new Cookie("products", product);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
			response.addCookie(cookie);
			AliyunOcsSampleHelp.getIMemcachedCache().set(out_trade_no, 300, successCartList);
			//Struts2Utils.renderText(out_trade_no, StringUtil.EARR_STRING);
			Struts2Utils.render("text/html", "<script>alert(\"支付成功！\");window.location.href=\"/user/UserBuyList.html\";</script>", StringUtil.ENCA_UTF8);
			return true;
		}
		return false;
	}
  
  protected boolean clearCarts() throws UnsupportedEncodingException{
		JSONArray products = new JSONArray();
		final String product = StringUtil.getUTF8URLEncoder(products.toString());
		Cookie cookie = new Cookie("products", product);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
		response.addCookie(cookie);
		//AliyunOcsSampleHelp.getIMemcachedCache().set(out_trade_no, 300, successCartList);
		return true;
	}
	
	protected boolean payBy3rdPart(List<ProductCart> productCartList, Integer userPayType,
			String userId, String integral, Integer bankMoney, String hidUseBalance,
			String out_trade_no, Integer moneyCount, String addOrderError){
		final String action = PPLATS.get(payName);
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("moneyCount", moneyCount.toString());//20160127杨振朋
		//map.put("moneyCount", bankMoney.toString());
		map.put("out_trade_no", out_trade_no);
		String sign="";
		try {
			sign = getSign(map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(action != null){
			addOrderError = (addOrderError==null?"":addOrderError);
			StringBuilder sbuf = new StringBuilder();
			// body
			sbuf.append("<form method=\"post\" action=\"").append(action)
				.append("\" name=\"toPayForm\" id=\"toPayForm\">")
				.append("<input type=\"hidden\" value=\"").append(bankMoney)
					.append("\" id=\"moneyCount\" name=\"moneyCount\">")
				.append("<input type=\"hidden\" value=\"").append(hidUseBalance)
					.append("\" name=\"hidUseBalance\" id=\"hidUseBalance\">")
				.append("<input type=\"hidden\" value=\"").append(out_trade_no)
					.append("\" name=\"out_trade_no\" id=\"out_trade_no\">")
				.append("<input type=\"hidden\" value=\"").append(integral)
					.append("\" name=\"integral\" id=\"hidIntegral\">")
				.append("<input type=\"hidden\" value=\"").append(payName)
					.append("\" name=\"payName\" id=\"payName\">")
				//.append("<input type=\"hidden\" value=\"").append(payBank)
				//	.append("\" name=\"payBank\" id=\"payBank\">")
				.append("<input type=\"hidden\" value=\"").append(userId)
					.append("\" name=\"userId\" id=\"userId\">")
				.append("<input type=\"hidden\" value=\"").append(addOrderError)
					.append("\" name=\"orderError\" id=\"orderError\">")
				.append("<input type=\"hidden\" value=\"").append(sign)
					.append("\" name=\"token\" id=\"token\">")
			.append("</form>")
			.append("<script>document.forms['toPayForm'].submit();</script>");
			final String body = sbuf.toString();
			sbuf.setLength(0);
			if("".equals(addOrderError)){
				// pay-info
				sbuf.append('[');
				sbuf.append('{');
				sbuf.append("\"userPayType\":\"").append(userPayType).append("\",");
				sbuf.append("\"userId\":\"").append(userId).append("\",");
				sbuf.append("\"moneyCount\":\"").append(moneyCount).append("\",");
				sbuf.append("\"integral\":\"").append(integral).append("\",");
				sbuf.append("\"bankMoney\":\"").append(bankMoney).append("\",");
				sbuf.append("\"hidUseBalance\":\"").append(hidUseBalance).append("\",");
				sbuf.append("\"out_trade_no\":\"").append(out_trade_no).append("\"");
				sbuf.append('}');
				sbuf.append(']');
				final JSONArray payInfo = JSONArray.fromObject(sbuf.toString());
				sbuf = null;
				AliyunOcsSampleHelp.getIMemcachedCache()
					.set("doPayList" + out_trade_no, 900, productCartList);
				AliyunOcsSampleHelp.getIMemcachedCache()
					.set("doPay" + out_trade_no, 900, payInfo);
			}
			// render
			Struts2Utils.render("text/html", body, StringUtil.ENCA_UTF8);
			return true;
		}
		return false;
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
	
  public void getShopResult()
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    boolean flag = false;
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
    if (StringUtil.isNotBlank(this.userId))
    {
      this.user = ((User)this.userService.findById(this.userId));
      this.userPayType = Integer.valueOf(1);
      flag = true;
    }
    else
    {
      flag = false;
    }
    Integer count = Integer.valueOf(0);
    if (flag)
    {
      List<Object[]> proList = this.spellbuyproductService.findByProductId(Integer.parseInt(this.id));
      Object[] objects = (Object[])proList.get(0);
      for(Object object:objects){
    		if(object instanceof Product){
    			product = (Product)object;
    		}
    		if(object instanceof Spellbuyproduct){
    			spellbuyproduct = (Spellbuyproduct)object;
    		}
    	  }
      //this.product = ((Product)((Object[])proList.get(0))[0]);
      //this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
      this.productCart = new ProductCart();
      this.productCartList = new ArrayList();
      if (this.spellbuyproduct.getSpStatus().intValue() == 0)
      {
        Integer CurrentPrice = this.spellbuyproduct.getSpellbuyCount();
        if (this.spellbuyproduct.getSpellbuyCount().intValue() + this.moneyCount.intValue() > this.spellbuyproduct.getSpellbuyPrice().intValue()) {
          count = Integer.valueOf(this.spellbuyproduct.getSpellbuyPrice().intValue() - this.spellbuyproduct.getSpellbuyCount().intValue());
        } else {
          count = this.moneyCount;
        }
        flag = true;
        this.productCart.setCount(count);
        this.productCart.setHeadImage(this.product.getHeadImage());
        this.productCart.setMoneyCount(count);
        this.productCart.setProductCount(count);
        this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productCart.setProductName(this.product.getProductName());
        this.productCart.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productCart.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productCart.setProductTitle(this.product.getProductTitle());
        this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
      }
      else
      {
        flag = false;
        Struts2Utils.renderText("over", new String[0]);
      }
    }
    if (flag)
    {
      this.consumetable = new Consumetable();
      double money = Double.parseDouble(String.valueOf(count));
      this.consumetable.setBuyCount(count);
      this.consumetable.setDate(DateUtil.DateTimeToStr(new Date()));
      if (this.userPayType.intValue() == 1) {
        this.consumetable.setInterfaceType("余额支付");
      } else if (this.userPayType.intValue() == 2) {
        this.consumetable.setInterfaceType("积分抵扣");
      } else {
        this.consumetable.setInterfaceType("积分+余额");
      }
      this.consumetable.setMoney(Double.valueOf(money));
      this.consumetable.setOutTradeNo(this.out_trade_no);
      this.consumetable.setUserId(Integer.valueOf(Integer.parseInt(this.userId)));
      this.consumetableService.add(this.consumetable);
      try
      {
        this.consumerdetail = new Consumerdetail();
        this.spellbuyrecord = new Spellbuyrecord();
        this.commissionquery = new Commissionquery();
        if (count.intValue() > 0)
        {
          double tempCommissionCount;
          try
          {
            if (this.userPayType.intValue() == 1) {
              if (this.user.getBalance().doubleValue() >= count.intValue())
              {
                Double temp = Double.valueOf(this.user.getBalance().doubleValue() - count.intValue());
                Integer points = this.user.getCommissionPoints();
                this.user.setBalance(temp);
                this.user.setCommissionPoints(Integer.valueOf(points.intValue() + count.intValue() * ApplicationListenerImpl.sysConfigureJson.getBuyProduct().intValue()));
                this.consumerdetail.setBuyCount(count);
                this.consumerdetail.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                this.consumerdetail.setConsumetableId(this.out_trade_no);
                this.consumerdetail.setProductId(this.productCart.getProductId());
                this.consumerdetail.setProductName(this.productCart.getProductName());
                this.consumerdetail.setProductPeriod(this.productCart.getProductPeriod());
                this.consumerdetail.setProductTitle(this.productCart.getProductTitle());
                this.consumerdetailService.add(this.consumerdetail);
                if (this.user.getInvite() != null)
                {
                  User userCommission = (User)this.userService.findById(String.valueOf(this.user.getInvite()));
                  tempCommissionCount = userCommission.getCommissionCount().doubleValue();
                  double commissionBalance = this.user.getCommissionBalance().doubleValue();
                  userCommission.setCommissionCount(Double.valueOf(tempCommissionCount += Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                  userCommission.setCommissionBalance(Double.valueOf(commissionBalance += Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                  this.userService.add(userCommission);
                  this.commissionquery.setBuyMoney(Double.valueOf(Double.parseDouble(String.valueOf(count))));
                  this.commissionquery.setCommission(Double.valueOf(Double.parseDouble(String.valueOf(count)) * ApplicationListenerImpl.sysConfigureJson.getCommission().doubleValue()));
                  this.commissionquery.setDate(DateUtil.DateTimeToStr(new Date()));
                  this.commissionquery.setDescription(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + this.productCart.getProductId() + ")获得佣金");
                  this.commissionquery.setInvitedId(this.user.getInvite());
                  this.commissionquery.setToUserId(this.user.getUserId());
                  this.commissionqueryService.add(this.commissionquery);
                }
                this.commissionpoints = new Commissionpoints();
                this.commissionpoints.setDate(DateUtil.DateTimeToStr(new Date()));
                this.commissionpoints.setDetailed(ApplicationListenerImpl.sysConfigureJson.getShortName() + "商品编码(" + this.spellbuyproduct.getSpellbuyProductId() + ")支付" + count + "元获得积分");
                this.commissionpoints.setPay("+" + count.intValue() * ApplicationListenerImpl.sysConfigureJson.getBuyProduct().intValue());
                this.commissionpoints.setToUserId(Integer.valueOf(Integer.parseInt(this.userId)));
                this.commissionpointsService.add(this.commissionpoints);
              }
              else
              {
                flag = false;
                Struts2Utils.renderText("moneyError", new String[0]);
              }
            }
          }
          catch (Exception e)
          {
            flag = false;
          }
          if (flag)
          {
            this.spellbuyproduct.setSpellbuyCount(Integer.valueOf(this.spellbuyproduct.getSpellbuyCount().intValue() + count.intValue()));
            if (this.spellbuyproduct.getSpellbuyCount().intValue() >= this.productCart.getProductPrice().intValue())
            {
              this.spellbuyproduct.setSpellbuyCount(this.productCart.getProductPrice());
              
              this.spellbuyproduct.setSpStatus(Integer.valueOf(2));
              this.spellbuyproduct.setSpellbuyEndDate(DateUtil.DateTimeToStr(DateUtil.subMinute(new Date(), -11)));
              try
              {
                this.lotteryproductutil = new Lotteryproductutil();
                this.lotteryproductutil.setLotteryProductEndDate(DateUtil.DateTimeToStr(DateUtil.subMinute(new Date(), -11)));
                this.lotteryproductutil.setLotteryProductId(this.spellbuyproduct.getSpellbuyProductId());
                this.lotteryproductutil.setLotteryProductImg(this.productCart.getHeadImage());
                this.lotteryproductutil.setLotteryProductName(this.productCart.getProductName());
                this.lotteryproductutil.setLotteryProductPeriod(this.spellbuyproduct.getProductPeriod());
                this.lotteryproductutil.setLotteryProductPrice(this.spellbuyproduct.getSpellbuyPrice());
                this.lotteryproductutil.setLotteryProductTitle(this.productCart.getProductTitle());
                this.lotteryproductutilService.add(this.lotteryproductutil);
              }
              catch (Exception localException1) {}
            }
            this.spellbuyproductService.add(this.spellbuyproduct);
            
            this.spellbuyrecord.setFkSpellbuyProductId(this.spellbuyproduct.getSpellbuyProductId());
            this.spellbuyrecord.setBuyer(this.user.getUserId());
            this.spellbuyrecord.setBuyPrice(count);
            this.spellbuyrecord.setBuyDate(DateUtil.DateTimeToStrBySSS(new Date()));
            this.spellbuyrecord.setSpWinningStatus("0");
            this.spellbuyrecord.setBuyStatus("0");
            this.spellbuyrecord.setSpRandomNo("");
            this.spellbuyrecordService.add(this.spellbuyrecord);
            this.randomnumber = new Randomnumber();
            this.randomnumber.setProductId(this.productCart.getProductId());
            
            List<Randomnumber> RandomnumberList = this.randomnumberService.query(" from Randomnumber where productId='" + this.spellbuyproduct.getSpellbuyProductId() + "'");
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
            
            Integer experience = this.user.getExperience();
            experience = Integer.valueOf(experience.intValue() + count.intValue() * 10);
            this.user.setExperience(experience);
            this.userService.add(this.user);
          }
        }
      }
      catch (Exception e)
      {
        flag = false;
        e.printStackTrace();
      }
    }
    if (flag) {
      Struts2Utils.renderText("success", new String[0]);
    }
  }
  
  public String aliPayUser()
  {
    return "aliPayUser";
  }
  
  public String tenPayUser()
  {
    this.id = MD5Util.MD5Encode(ApplicationListenerImpl.sysConfigureJson.getTenPayUser() + "&" + this.moneyCount + "&" + this.out_trade_no, null).toLowerCase();
    return "tenPayUser";
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public Product getProduct()
  {
    return this.product;
  }
  
  public void setProduct(Product product)
  {
    this.product = product;
  }
  
  public List<ProductCart> getProductCartList()
  {
    return this.productCartList;
  }
  
  public void setProductCartList(List<ProductCart> productCartList)
  {
    this.productCartList = productCartList;
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
  
  public Integer getMoneyCount()
  {
    return this.moneyCount;
  }
  
  public void setMoneyCount(Integer moneyCount)
  {
    this.moneyCount = moneyCount;
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
  
	public Integer getBankMoney() {
		return bankMoney;
	}

	public void setBankMoney(Integer bankMoney) {
		this.bankMoney = bankMoney;
	}
	
	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}
	
  public String getForward()
  {
    return this.forward;
  }
  
  public void setForward(String forward)
  {
    this.forward = forward;
  }
}
