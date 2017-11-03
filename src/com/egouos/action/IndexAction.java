package com.egouos.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.egouos.dao.Pagination;
import com.egouos.pojo.BuyHistoryJSON;
import com.egouos.pojo.Commissionpoints;
import com.egouos.pojo.IndexImg;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.News;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.SysConfigure;
import com.egouos.pojo.User;
import com.egouos.pojo.UserJSON;
import com.egouos.proto.http.Proxy;
import com.egouos.service.CommissionpointsService;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.NewsService;
import com.egouos.service.RecommendService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.service.SysConfigureService;
import com.egouos.service.UserService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.CookieUtil;
import com.egouos.util.DateUtil;
import com.egouos.util.EscapeUtil;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;

@Component("IndexAction")
public class IndexAction extends BaseAction
{
  private static final long serialVersionUID = -7084752934617098983L;
  @Autowired
  @Qualifier("spellbuyrecordService")
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  @Qualifier("latestlotteryService")
  private LatestlotteryService latestlotteryService;
  @Autowired
  @Qualifier("spellbuyproductService")
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private NewsService newsService;
  @Autowired
  private RecommendService recommendService;
  @Autowired
  private UserService userService;
  @Autowired
  private SysConfigureService sysConfigureService;
  @Autowired
  private CommissionpointsService commissionpointsService;
  @Autowired
  Proxy httproxy;
  
  private List<ProductJSON> hotProductList;
  private List<ProductJSON> newProductList;
  private List<ProductJSON> indexPopProductList;
  private List<ProductJSON> productList;
  private List<BuyHistoryJSON> buyHistoryJSONList;
  public static List<News> newsList;
  private BuyHistoryJSON buyHistoryJSON;
  private ProductJSON productJSON;
  private ProductJSON recommendJSON;
  private UserJSON userJSON;
  private Product product;
  private Spellbuyrecord spellbuyrecord;
  private Spellbuyproduct spellbuyproduct;
  private Latestlottery latestlottery;
  private List<Latestlottery> latestlotteryList;
  private User user;
  private String id;
  private String share;
  private int pageNo;
  private int pageSize = 20;
  private int pageCount;
  private int resultCount;
  private String startDate;
  private String endDate;
  private int lotteryCount;
  private List<IndexImg> indexImgList;
  private Commissionpoints commissionpoints;
  private String uid;
  private static List<ProductJSON> nowBuyProductList;
  private static List<ProductJSON> newRecordList;
  private static Long allBuyCount;
  private static Long nowDateByAllCount = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateByAllCount;
  private static Long nowDateByNowBuyProduct = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateByNowBuyProduct;
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String index()
  {
    if(!StringUtil.isEmpty(share)){
    	System.out.println("index() share="+share);
        this.request = Struts2Utils.getRequest();
        this.response = Struts2Utils.getResponse();
        Cookie cookie = new Cookie("inviteId", this.share);
        cookie.setMaxAge(43200);
        cookie.setPath("/");
        cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
        this.response.addCookie(cookie);
        //跳转到微信入口
        String redirect_uri = ApplicationListenerImpl.sysConfigureJson.getWeixinUrl()+"/WxTransit.html";
    	try {
    		redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8").replace("+", "%20");
    	} catch (UnsupportedEncodingException e) {
    	}
    	StringBuilder sb = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize");
    	sb.append("?appid="+ApplicationListenerImpl.sysConfigureJson.getWeixinAppId());
    	sb.append("&redirect_uri="+redirect_uri);
    	sb.append("&response_type=code");
    	sb.append("&scope=snsapi_userinfo");
    	sb.append("&state=STATE#wechat_redirect");
    	System.out.println("share:"+sb.toString());
    	Struts2Utils.render("text/html", "<script>location.replace('"+sb.toString()+"');</script>", StringUtil.ENCA_UTF8);
    	//Struts2Utils.render("text/html", "<script>window.location.href=\"/?share=" + this.uid + "\";</script>", new String[] { "encoding:UTF-8" });
        return null;
    }
    List<Latestlottery> objList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("index_objList");
    if (objList == null)
    {
      objList = this.latestlotteryService.indexWinningScroll();
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_objList", 5, objList);
    }
    this.latestlotteryList = new ArrayList();
    if ((objList != null) && (objList.size() > 0)) {
      for (int i = 0; i < objList.size(); i++)
      {
        this.latestlottery = new Latestlottery();
        this.latestlottery = ((Latestlottery)objList.get(i));
        String userName = "";
        if ((this.latestlottery.getUserName() != null) && (!this.latestlottery.getUserName().equals("")))
        {
          userName = this.latestlottery.getUserName();
        }
        else if ((this.latestlottery.getBuyUser() != null) && (!this.latestlottery.getBuyUser().equals("")))
        {
          userName = this.latestlottery.getBuyUser();
          if (userName.indexOf("@") != -1)
          {
            String[] u = userName.split("@");
            String u1 = u[0].substring(0, 2) + "***";
            userName = u1 + "@" + u[1];
          }
          else
          {
            userName = userName.substring(0, 4) + "*** " + userName.substring(7);
          }
        }
        this.latestlottery.setBuyUser(userName);
        this.latestlotteryList.add(this.latestlottery);
      }
    }
    String lCount = (String)AliyunOcsSampleHelp.getIMemcachedCache().get("index_lotteryCount");
    if (lCount == null)
    {
      this.lotteryCount = this.latestlotteryService.getLotteryByCount().intValue();
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_lotteryCount", 5, String.valueOf(this.lotteryCount));
    }
    else
    {
      this.lotteryCount = Integer.parseInt(lCount);
    }
    Pagination hotPage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("index_hotPage");
    if (hotPage == null)
    {
      hotPage = this.spellbuyrecordService.findHotProductList(1, 12);
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_hotPage", 5, hotPage);
    }
    List<Object[]> HotList = (List<Object[]>)hotPage.getList();
    this.hotProductList = new ArrayList();
    for (int i = 0; i < HotList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      Object[] objs = (Object[])HotList.get(i);
  	if(objs[0] instanceof Product){
  		product = (Product)objs[0];
  		spellbuyproduct = (Spellbuyproduct)objs[1];
  	}else{
  		product = (Product)objs[1];
  		spellbuyproduct = (Spellbuyproduct)objs[0];
  	}
      this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
      this.productJSON.setHeadImage(this.product.getHeadImage());
      this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      this.productJSON.setProductName(this.product.getProductName());
      this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
      this.productJSON.setProductTitle(this.product.getProductTitle());
      this.productJSON.setProductStyle(this.product.getStyle());
      this.hotProductList.add(this.productJSON);
    }
    this.indexImgList = JSONArray.fromObject(ApplicationListenerImpl.indexImgAll);
    return "index";
  }
  
  public void getIndexSoonGoodsList()
  {
    Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("index_datePage");
    if (datePage == null)
    {
      datePage = this.spellbuyproductService.upcomingAnnounced(this.pageNo, 24);
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_datePage", 5, datePage);
    }
    List<Object[]> dateList = (List<Object[]>)datePage.getList();
    this.productList = new ArrayList();
    for (int i = 0; i < dateList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      Object[] objs = (Object[])dateList.get(i);
    	if(objs[0] instanceof Product){
    		product = (Product)objs[0];
    		spellbuyproduct = (Spellbuyproduct)objs[1];
    	}else{
    		product = (Product)objs[1];
    		spellbuyproduct = (Spellbuyproduct)objs[0];
    	}
      this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
      this.productJSON.setHeadImage(this.product.getHeadImage());
      this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      this.productJSON.setProductName(this.product.getProductName());
      this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
      this.productJSON.setProductTitle(this.product.getProductTitle());
      this.productJSON.setProductStyle(this.product.getStyle());
      this.productList.add(this.productJSON);
    }
    Struts2Utils.renderJson(this.productList, new String[0]);
  }
  
  public void getIndexHotProductList()
  {
    Pagination hotPage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("index_hotPage");
    if (hotPage == null)
    {
      hotPage = this.spellbuyrecordService.findHotProductList(1, 8);
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_hotPage", 5, hotPage);
    }
    List<Object[]> HotList = (List<Object[]>)hotPage.getList();
    this.hotProductList = new ArrayList();
    for (int i = 0; i < HotList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      Object[] objs = (Object[])HotList.get(i);
  	if(objs[0] instanceof Product){
  		product = (Product)objs[0];
  		spellbuyproduct = (Spellbuyproduct)objs[1];
  	}else{
  		product = (Product)objs[1];
  		spellbuyproduct = (Spellbuyproduct)objs[0];
  	}
      this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
      this.productJSON.setHeadImage(this.product.getHeadImage());
      this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      this.productJSON.setProductName(this.product.getProductName());
      this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
      this.productJSON.setProductTitle(this.product.getProductTitle());
      this.productJSON.setProductStyle(this.product.getStyle());
      this.hotProductList.add(this.productJSON);
    }
    Struts2Utils.renderJson(this.hotProductList, new String[0]);
  }
  
  public void getIndexNewProductList()
  {
    Pagination phoneDigitalPage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("index_phoneDigitalPage");
    if (phoneDigitalPage == null)
    {
      phoneDigitalPage = this.spellbuyrecordService.indexNewProductList(this.pageNo, 8);
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_phoneDigitalPage", 5, phoneDigitalPage);
    }
    List<Object[]> phoneDigitalList = (List<Object[]>)phoneDigitalPage.getList();
    this.newProductList = new ArrayList();
    for (int i = 0; i < phoneDigitalList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      Object[] objs = (Object[])phoneDigitalList.get(i);
    	if(objs[0] instanceof Product){
    		product = (Product)objs[0];
    		spellbuyproduct = (Spellbuyproduct)objs[1];
    	}else{
    		product = (Product)objs[1];
    		spellbuyproduct = (Spellbuyproduct)objs[0];
    	}
      this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
      this.productJSON.setHeadImage(this.product.getHeadImage());
      this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      this.productJSON.setProductName(this.product.getProductName());
      this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
      this.productJSON.setProductTitle(this.product.getProductTitle());
      this.productJSON.setProductStyle(this.product.getStyle());
      this.newProductList.add(this.productJSON);
    }
    Struts2Utils.renderJson(this.newProductList, new String[0]);
  }
  
  public void getIndexPopProductList()
  {
    Pagination ComputerOfficePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("index_ComputerOfficePage");
    if (ComputerOfficePage == null)
    {
      ComputerOfficePage = this.spellbuyrecordService.indexHotProductList(this.pageNo, 8);
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_ComputerOfficePage", 5, ComputerOfficePage);
    }
    List<Object[]> ComputerOfficeList = (List<Object[]>)ComputerOfficePage.getList();
    this.indexPopProductList = new ArrayList();
    for (int i = 0; i < ComputerOfficeList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      Object[] objs = (Object[])ComputerOfficeList.get(i);
  	if(objs[0] instanceof Product){
  		product = (Product)objs[0];
  		spellbuyproduct = (Spellbuyproduct)objs[1];
  	}else{
  		product = (Product)objs[1];
  		spellbuyproduct = (Spellbuyproduct)objs[0];
  	}
      this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
      this.productJSON.setHeadImage(this.product.getHeadImage());
      this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      this.productJSON.setProductName(this.product.getProductName());
      this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
      this.productJSON.setProductTitle(this.product.getProductTitle());
      this.productJSON.setProductStyle(this.product.getStyle());
      this.indexPopProductList.add(this.productJSON);
    }
    Struts2Utils.renderJson(this.indexPopProductList, new String[0]);
  }
  
  public void getIndexUserInfo()
  {
    this.user = ((User)this.userService.findById(this.id));
    if (this.user != null)
    {
      this.userJSON = new UserJSON();
      this.userJSON.setUserBalance(this.user.getBalance().doubleValue());
      this.userJSON.setUserExperience(this.user.getExperience().intValue());
      this.userJSON.setUserFace(this.user.getFaceImg());
      this.userJSON.setUserId(this.user.getUserId().toString());
      if (this.user.getExperience().intValue() < 10000)
      {
        this.userJSON.setUserLevel(1);
        this.userJSON.setUserLevelName(ApplicationListenerImpl.sysConfigureJson.getShortName() + "小将");
      }
      else if (this.user.getExperience().intValue() < 50000)
      {
        this.userJSON.setUserLevel(2);
        this.userJSON.setUserLevelName(ApplicationListenerImpl.sysConfigureJson.getShortName() + "少将");
      }
      else if (this.user.getExperience().intValue() < 100000)
      {
        this.userJSON.setUserLevel(3);
        this.userJSON.setUserLevelName(ApplicationListenerImpl.sysConfigureJson.getShortName() + "中将");
      }
      else if (this.user.getExperience().intValue() < 500000)
      {
        this.userJSON.setUserLevel(4);
        this.userJSON.setUserLevelName(ApplicationListenerImpl.sysConfigureJson.getShortName() + "上将");
      }
      else if (this.user.getExperience().intValue() < 1000000)
      {
        this.userJSON.setUserLevel(5);
        this.userJSON.setUserLevelName(ApplicationListenerImpl.sysConfigureJson.getShortName() + "少校");
      }
      else if (this.user.getExperience().intValue() < 2000000)
      {
        this.userJSON.setUserLevel(6);
        this.userJSON.setUserLevelName(ApplicationListenerImpl.sysConfigureJson.getShortName() + "中校");
      }
      else if (this.user.getExperience().intValue() < 5000000)
      {
        this.userJSON.setUserLevel(7);
        this.userJSON.setUserLevelName(ApplicationListenerImpl.sysConfigureJson.getShortName() + "上校");
      }
      else if (this.user.getExperience().intValue() < 10000000)
      {
        this.userJSON.setUserLevel(7);
        this.userJSON.setUserLevelName(ApplicationListenerImpl.sysConfigureJson.getShortName() + "元帅");
      }
      this.userJSON.setUserName(UserNameUtil.userName(this.user));
      Struts2Utils.renderJson(this.userJSON, new String[0]);
    }
  }
  
  public String getNowBuyProduct()
  {
    nowDateByNowBuyProduct = Long.valueOf(System.currentTimeMillis());
    if (beginDateByNowBuyProduct == null)
    {
      Pagination page = this.spellbuyrecordService.getNowBuyList(this.pageNo, this.pageSize);
      List<Object[]> newBuyList = (List<Object[]>)page.getList();
      nowBuyProductList = new ArrayList();
      for (int i = 0; i < newBuyList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])newBuyList.get(i);
        for(Object obj:objs){
      		if(obj instanceof Product){
      			product = (Product)obj;
      		}
      		if(obj instanceof Spellbuyrecord){
      			spellbuyrecord = (Spellbuyrecord)obj;
      		}
      		if(obj instanceof User){
      			user = (User)obj;
      		}
      		if(obj instanceof Spellbuyproduct){
      			spellbuyproduct = (Spellbuyproduct)obj;
      		}
      	  }
        String userName = UserNameUtil.userName(this.user);
        this.productJSON.setBuyer(userName);
        this.productJSON.setUserId(String.valueOf(this.user.getUserId()));
        this.productJSON.setHeadImage(this.user.getFaceImg());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        nowBuyProductList.add(this.productJSON);
      }
      beginDateByNowBuyProduct = Long.valueOf(System.currentTimeMillis());
      
      Struts2Utils.renderJson(nowBuyProductList, new String[0]);
    }
    else if (nowDateByNowBuyProduct.longValue() - beginDateByNowBuyProduct.longValue() < 5000L)
    {
      Struts2Utils.renderJson(nowBuyProductList, new String[0]);
    }
    else
    {
      beginDateByNowBuyProduct = Long.valueOf(System.currentTimeMillis());
      Pagination page = this.spellbuyrecordService.getNowBuyList(this.pageNo, this.pageSize);
      List<Object[]> newBuyList = (List<Object[]>)page.getList();
      nowBuyProductList = new ArrayList();
      for (int i = 0; i < newBuyList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])newBuyList.get(i);
        for(Object obj:objs){
      		if(obj instanceof Product){
      			product = (Product)obj;
      		}
      		if(obj instanceof Spellbuyrecord){
      			spellbuyrecord = (Spellbuyrecord)obj;
      		}
      		if(obj instanceof User){
      			user = (User)obj;
      		}
      		if(obj instanceof Spellbuyproduct){
      			spellbuyproduct = (Spellbuyproduct)obj;
      		}
      	  }
        String userName = UserNameUtil.userName(this.user);
        this.productJSON.setBuyer(userName);
        this.productJSON.setUserId(String.valueOf(this.user.getUserId()));
        this.productJSON.setHeadImage(this.user.getFaceImg());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        nowBuyProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(nowBuyProductList, new String[0]);
    }
    return null;
  }
  
  public void getServerTime()
  {
    Long endDate = Long.valueOf(DateUtil.SDateTimeToDate(this.id).getTime());
    Long nowDate = Long.valueOf(System.currentTimeMillis());
    if (endDate.longValue() > nowDate.longValue()) {
        Struts2Utils.renderText(((endDate.longValue() - nowDate.longValue()) / 1000L)+"");
      } else {
        Struts2Utils.renderText(((nowDate.longValue() - endDate.longValue()) / 1000L)+"");
      }
  }
  
  public static void main(String[] args)
  {
    Date date = new Date();
    
    date = DateUtil.SDateTimeToDate("2014-10-23 15:42:47");
    System.err.println(date.getTime());
    System.err.println(DateUtil.SDateTimeToDate(DateUtil.DateTimeToStr(new Date())).getTime());
    System.err.println(DateUtil.SDateTimeToDate(DateUtil.DateTimeToStr(new Date())).getTime() - date.getTime());
    System.err.println((DateUtil.SDateTimeToDate(DateUtil.DateTimeToStr(new Date())).getTime() - date.getTime()) / 60L);
    System.err.println(System.currentTimeMillis());
  }
  
  public String referAuth()
  {
    if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      

      return null;
    }
    HttpServletRequest request = Struts2Utils.getRequest();
    Cookie[] cookies = request.getCookies();
    for (int i = 0; i < cookies.length; i++)
    {
      Cookie cookie = cookies[i];
      if (cookie.getName().equals("userId")) {
        this.uid = cookie.getValue();
      }
    }
    return "referAuthLogin";
  }
  
  public String share()
  {
	  System.out.println("share() uid="+uid);
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    CookieUtil.addCookie(request, response, "inviteId", this.uid, 43200, ApplicationListenerImpl.sysConfigureJson.getDomain());
    /*String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
    if (ip == null) {
      ip = "127.0.0.1";
    }
    if (AliyunOcsSampleHelp.getIMemcachedCache().get(this.uid) == null)
    {
      this.user = ((User)this.userService.findById(this.uid));
      if (this.user != null)
      {
        Integer m = this.user.getCommissionPoints();
        m = Integer.valueOf(m.intValue() + ApplicationListenerImpl.sysConfigureJson.getInvite().intValue());
        this.user.setCommissionPoints(m);
        this.userService.add(this.user);
        AliyunOcsSampleHelp.getIMemcachedCache().set(this.uid, 43200, Integer.valueOf(1));
      }
    }*/
    //跳转到微信入口
    String redirect_uri = ApplicationListenerImpl.sysConfigureJson.getWeixinUrl()+"/WxTransit.html";
	try {
		redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8").replace("+", "%20");
	} catch (UnsupportedEncodingException e) {
	}
	StringBuilder sb = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize");
	sb.append("?appid="+ApplicationListenerImpl.sysConfigureJson.getWeixinAppId());
	sb.append("&redirect_uri="+redirect_uri);
	sb.append("&response_type=code");
	sb.append("&scope=snsapi_userinfo");
	sb.append("&state=STATE#wechat_redirect");
	//System.out.println("share:"+sb.toString());
	Struts2Utils.render("text/html", "<script>location.replace('"+sb.toString()+"');</script>", StringUtil.ENCA_UTF8);
	//Struts2Utils.render("text/html", "<script>window.location.href=\"/?share=" + this.uid + "\";</script>", new String[] { "encoding:UTF-8" });
	return null;
  }
  
  public void getAllBuyCount()
  {
    nowDateByAllCount = Long.valueOf(System.currentTimeMillis());
    if (beginDateByAllCount == null)
    {
      allBuyCount = Long.valueOf(Long.parseLong(this.spellbuyrecordService.getAllByCount().toString()));
      beginDateByAllCount = Long.valueOf(System.currentTimeMillis());
      
      Struts2Utils.renderText(String.valueOf(allBuyCount), new String[0]);
    }
    else if (nowDateByAllCount.longValue() - beginDateByAllCount.longValue() < 5000L)
    {
      Struts2Utils.renderText(String.valueOf(allBuyCount), new String[0]);
    }
    else
    {
      beginDateByAllCount = Long.valueOf(System.currentTimeMillis());
      allBuyCount = Long.valueOf(Long.parseLong(this.spellbuyrecordService.getAllByCount().toString()));
      
      Struts2Utils.renderText(String.valueOf(allBuyCount), new String[0]);
    }
  }
  
  public String getNewRecord()
  {
    if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      return null;
    }
    newRecordList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("recordList");
    if (newRecordList != null) {
      return "newRecord";
    }
    Pagination page = this.spellbuyrecordService.getNowBuyList(this.pageNo, 100);
    List<Object[]> newBuyList = (List<Object[]>)page.getList();
    newRecordList = new ArrayList();
    for (int i = 0; i < newBuyList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      Object[] objs = (Object[])newBuyList.get(i);
    	if(objs[0] instanceof Product){
    		product = (Product)objs[0];
    		spellbuyproduct = (Spellbuyproduct)objs[1];
    	}else{
    		product = (Product)objs[1];
    		spellbuyproduct = (Spellbuyproduct)objs[0];
    	}
      this.user = ((User)((Object[])newBuyList.get(i))[2]);
      this.spellbuyproduct = ((Spellbuyproduct)((Object[])newBuyList.get(i))[3]);
      String userName = UserNameUtil.userName(this.user);
      this.productJSON.setBuyer(userName);
      this.productJSON.setUserId(String.valueOf(this.user.getUserId()));
      this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      if (this.product.getProductName().length() > 35) {
        this.productJSON.setProductName(this.product.getProductName().substring(0, 35) + "...");
      } else {
        this.productJSON.setProductName(this.product.getProductName());
      }
      this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
      this.productJSON.setBuyCount(this.spellbuyrecord.getBuyPrice());
      this.productJSON.setProductTitle(this.product.getProductTitle());
      this.productJSON.setBuyDate(this.spellbuyrecord.getBuyDate());
      this.productJSON.setProductStyle(String.valueOf(this.spellbuyrecord.getSpellbuyRecordId()));
      newRecordList.add(this.productJSON);
    }
    AliyunOcsSampleHelp.getIMemcachedCache().set("recordList", 5, newRecordList);
    
    return "newRecord";
  }
  
  public void getNewRecordAjax()
  {
    newRecordList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("recordList");
    if (newRecordList != null)
    {
      Struts2Utils.renderJson(newRecordList, new String[0]);
    }
    else
    {
      Pagination page = this.spellbuyrecordService.getNowBuyList(this.pageNo, 100);
      List<Object[]> newBuyList = (List<Object[]>)page.getList();
      newRecordList = new ArrayList();
      for (int i = 0; i < newBuyList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])newBuyList.get(i);
    	if(objs[0] instanceof Product){
    		product = (Product)objs[0];
    		spellbuyproduct = (Spellbuyproduct)objs[1];
    	}else{
    		product = (Product)objs[1];
    		spellbuyproduct = (Spellbuyproduct)objs[0];
    	}
        this.user = ((User)((Object[])newBuyList.get(i))[2]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])newBuyList.get(i))[3]);
        String userName = UserNameUtil.userName(this.user);
        this.productJSON.setBuyer(userName);
        this.productJSON.setUserId(String.valueOf(this.user.getUserId()));
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        if (this.product.getProductName().length() > 35) {
          this.productJSON.setProductName(this.product.getProductName().substring(0, 35) + "...");
        } else {
          this.productJSON.setProductName(this.product.getProductName());
        }
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.productJSON.setBuyCount(this.spellbuyrecord.getBuyPrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setBuyDate(this.spellbuyrecord.getBuyDate());
        this.productJSON.setProductStyle(String.valueOf(this.spellbuyrecord.getSpellbuyRecordId()));
        newRecordList.add(this.productJSON);
      }
      AliyunOcsSampleHelp.getIMemcachedCache().set("recordList", 5, newRecordList);
      Struts2Utils.renderJson(newRecordList, new String[0]);
    }
  }
  
  public String getAllBuyRecord()
  {
    Pagination datePage = this.spellbuyrecordService.getAllBuyRecord(this.startDate, this.endDate, this.pageNo, this.pageSize);
    List<Object[]> dataList = (List<Object[]>)datePage.getList();
    this.buyHistoryJSONList = new ArrayList();
    for (int j = 0; j < dataList.size(); j++)
    {
      this.buyHistoryJSON = new BuyHistoryJSON();
      Object[] objs = (Object[])dataList.get(j);
  	  for(Object obj:objs){
  		if(obj instanceof Product){
  			product = (Product)obj;
  		}
  		if(obj instanceof Spellbuyrecord){
  			spellbuyrecord = (Spellbuyrecord)obj;
  		}
  		if(obj instanceof User){
  			user = (User)obj;
  		}
  		if(obj instanceof Spellbuyproduct){
  			spellbuyproduct = (Spellbuyproduct)obj;
  		}
  	  }
      //this.product = ((Product)((Object[])dataList.get(j))[0]);
      //this.spellbuyrecord = ((Spellbuyrecord)((Object[])dataList.get(j))[1]);
      //this.user = ((User)((Object[])dataList.get(j))[2]);
      //this.spellbuyproduct = ((Spellbuyproduct)((Object[])dataList.get(j))[3]);
      this.buyHistoryJSON.setBuyCount(Long.valueOf(Long.parseLong(String.valueOf(this.spellbuyrecord.getBuyPrice()))));
      this.buyHistoryJSON.setBuyStatus(this.spellbuyproduct.getSpStatus());
      this.buyHistoryJSON.setHistoryId(this.spellbuyrecord.getSpellbuyRecordId());
      this.buyHistoryJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      this.buyHistoryJSON.setProductImg(this.product.getHeadImage());
      this.buyHistoryJSON.setProductName(this.product.getProductName());
      this.buyHistoryJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
      this.buyHistoryJSON.setProductTitle(this.product.getProductTitle());
      if (this.spellbuyproduct.getSpStatus().intValue() == 1)
      {
        this.latestlottery = ((Latestlottery)this.latestlotteryService.getBuyHistoryByDetail(this.spellbuyproduct.getSpellbuyProductId()).get(0));
        this.buyHistoryJSON.setWinDate(this.latestlottery.getAnnouncedTime());
        this.buyHistoryJSON.setWinId(this.latestlottery.getRandomNumber());
        String userer = null;
        if ((this.latestlottery.getUserName() != null) && (!this.latestlottery.getUserName().equals("")))
        {
          userer = this.latestlottery.getUserName();
        }
        else if ((this.latestlottery.getBuyUser() != null) && (!this.latestlottery.getBuyUser().equals("")))
        {
          userer = this.latestlottery.getBuyUser();
          if (userer.indexOf("@") != -1)
          {
            String[] u = userer.split("@");
            String u1 = u[0].substring(0, 2) + "***";
            userer = u1 + "@" + u[1];
          }
          else
          {
            userer = userer.substring(0, 4) + "*** " + userer.substring(7);
          }
        }
        this.buyHistoryJSON.setWinUser(userer);
      }
      this.buyHistoryJSONList.add(this.buyHistoryJSON);
    }
    return "allBuyRecord";
  }
  
  public String map()
  {
    String lCount = (String)AliyunOcsSampleHelp.getIMemcachedCache().get("index_lotteryCount");
    if (lCount == null)
    {
      this.lotteryCount = this.latestlotteryService.getLotteryByCount().intValue();
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_lotteryCount", 5, String.valueOf(this.lotteryCount));
    }
    else
    {
      this.lotteryCount = Integer.parseInt(lCount);
    }
    return "map";
  }
  
  public String sincerity()
  {
    return "sincerity";
  }
  
  public void reloadConfigure(){
	  ApplicationListenerImpl.sysConfigureJson = (SysConfigure)sysConfigureService.findById("1");
	  ApplicationListenerImpl.indexImgAll = sysConfigureService.initializationIndexImgAll();
	  System.out.println("weixin reloadConfigure:ok");
	  Struts2Utils.renderText("ok");
  }
  
  public String WxTransit() throws Exception{
	request = Struts2Utils.getRequest();
	response = Struts2Utils.getResponse();
	if("help".equals(pt)){
		//新手指南
		redirectUri = "/help/index.html";
		} else if ("goodslist".equals(pt)) {
			// 所有商品
			redirectUri = "/list/m1.html";
		} else if ("lottery".equals(pt)) {
			// 所有商品
			redirectUri = "/lottery/index.html";
		} else if ("post".equals(pt)) {
			// 晒单分享
			redirectUri = "/share/new20.html";
		} else if ("user".equals(pt)) {
			// 我的夺宝
			redirectUri = "/user/index.html";
		} else if ("userbuylist".equals(pt)) {
			// 夺宝记录
			redirectUri = "/user/UserBuyList.html";
		} else if ("orderlist".equals(pt)) {
			// 获得的商品
			redirectUri = "/user/OrderList.html";
		} else if ("sharelist".equals(pt)) {
			// 分享赚钱
			redirectUri = "/user/ShareList.html";
		} else if ("bind".equals(pt)) {
			// 验证手机
			redirectUri = "/user/MobileCheck.html";
		} else {
			redirectUri = "/";
		}
	
	if(!StringUtil.isEmpty(code)){
		SysConfigure conf = ApplicationListenerImpl.sysConfigureJson;
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token");
		sb.append("?appid="+conf.getWeixinAppId());
		sb.append("&secret="+conf.getWeixinAppSecret());
		sb.append("&code="+code);
		sb.append("&grant_type=authorization_code");
		
		// request
		final String result = httproxy.get(sb.toString(), null);
		System.out.println("result="+result);
		LOG.debug("openid return: {}", result);
		JSONObject obj = JSONObject.fromObject(result);
		//snsapi_base或者snsapi_userinfo
		String scope = null;
		String openId=null;
		String unionId=null;
		String accessToken=null;
		String nickname=null;
		String headimgurl=null;
		if(obj.containsKey("scope")){
			scope = obj.getString("scope");
		}
		if(obj.containsKey("openid")){
			openId = obj.getString("openid");
		}
		if(obj.containsKey("unionid")){
			unionId = obj.getString("unionid");
		}
		if(obj.containsKey("access_token")){
			accessToken = obj.getString("access_token");
		}
		if(accessToken!=null){
			sb.setLength(0);
			sb.append("https://api.weixin.qq.com/sns/userinfo?");
			sb.append("access_token=").append(accessToken);
			sb.append("&openid=").append(openId);
			sb.append("&lang=zh_CN");
			String ret = httproxy.get(sb.toString(), null);
			System.out.println("result2="+ret);
			LOG.debug("userinfo return: {}", ret);
			JSONObject userobj = JSONObject.fromObject(ret);
			nickname = userobj.getString("nickname");
			headimgurl = userobj.getString("headimgurl");
		}
		//System.out.println("openid="+openId);
		if(openId!=null){
			String ip = request.getHeader("X-Real-IP");
			String ipLocation = RegisterAction.seeker.getAddress(ip);
			String date = DateUtil.DateTimeToStr(new Date());
			String userName = null;
			User wxu = null;
			if(unionId!=null){
				wxu = userService.userByWeixinUnionId(unionId);
			}else{
				wxu = userService.userByWeixinOpenId(openId);
			}
			if(wxu!=null){
				if(!StringUtil.isEmpty(wxu.getUserName())){
					userName = wxu.getUserName();
				}else if(!StringUtil.isEmpty(wxu.getPhone())){
					userName = wxu.getPhone();
				}else if(!StringUtil.isEmpty(wxu.getMail())){
					userName = wxu.getMail();
				}
				//System.out.println("wxu!=null");
				wxu.setIpAddress(ip);
				wxu.setIpLocation(ipLocation);
				wxu.setNewDate(date);
				userService.add(wxu);
				if(!"0".equals(wxu.getMobileCheck())){
					redirectUri = "/user/MobileBind.html";
				}
			}else{
				//System.out.println("wxu==null");
				Cookie c = CookieUtil.getCookie(request, "userId");
				if(c!=null){
					String userId = c.getValue();
					if ((userId != null) && (!userId.equals(""))) {
						wxu = ((User)this.userService.findById(userId));
					}
				}
				//System.out.println("wxu2="+wxu);
				if(wxu!=null && StringUtil.isEmpty(wxu.getWeixinOpenId())){
					wxu.setWeixinOpenId(openId);
					wxu.setWeixinUnionId(unionId);
					userService.add(wxu);
				}else if(!StringUtil.isEmpty(nickname)){
					userName = nickname;
					//直接注册用户
					wxu = new User();
					wxu.setUserName(nickname);
					wxu.setWeixinOpenId(openId);
					wxu.setWeixinUnionId(unionId);
					wxu.setUserPwd(openId);
					wxu.setIpAddress(ip);
					wxu.setIpLocation(ipLocation);
					wxu.setOldDate(date);
					wxu.setNewDate(date);
					wxu.setBalance(ApplicationListenerImpl.sysConfigureJson.getRegBalance());
					wxu.setCommissionBalance(Double.valueOf(0.0D));
					wxu.setCommissionCount(Double.valueOf(0.0D));
					wxu.setCommissionMention(Double.valueOf(0.0D));
					wxu.setCommissionPoints(Integer.valueOf(0));
					wxu.setFaceImg(headimgurl);
					wxu.setUserType("0");
					wxu.setExperience(Integer.valueOf(0));
					//处理推荐人
					Cookie ic = CookieUtil.getCookie(request, "inviteId");
					if(ic!=null){
						String inviteId = ic.getValue();
						if ((inviteId != null) && (!inviteId.equals(""))) {
							wxu.setInvite(Integer.valueOf(Integer.parseInt(inviteId)));
							User user2 = (User) this.userService.findById(inviteId);
							int commissionPoints = user2.getCommissionPoints().intValue();
							commissionPoints += ApplicationListenerImpl.sysConfigureJson.getInvite().intValue();
							user2.setCommissionPoints(Integer.valueOf(commissionPoints));
							this.userService.add(user2);
						}
					}
					userService.add(wxu);
					redirectUri = "/user/MobileBind.html";
				}
			}
			
			if (wxu!=null){
				String domain = ApplicationListenerImpl.sysConfigureJson.getDomain();
				CookieUtil.addCookie(request, response, "userName", EscapeUtil.escape(userName), 31536000, domain);
				CookieUtil.addCookie(request, response, "loginUser", EscapeUtil.escape(userName), 31536000, domain);
				CookieUtil.addCookie(request, response, "userId", String.valueOf(wxu.getUserId()), 31536000, domain);
				CookieUtil.addCookie(request, response, "face", EscapeUtil.escape(wxu.getFaceImg()), 31536000, domain);
			}
		}
	  }
	  Struts2Utils.render("text/html", "<script>window.location.href=\""+redirectUri+"\";</script>", new String[] { "encoding:UTF-8" });
	  return null;
  }
  
	public String wxback() throws JSONException, HttpException, UnsupportedEncodingException
	{
		request = Struts2Utils.getRequest();
		response = Struts2Utils.getResponse();
		Cookie[] cookies = request.getCookies();
		if(StringUtil.isEmpty(code)){
			return null;
		}
		String appid = ApplicationListenerImpl.sysConfigureJson.getWxAppID();
		String secret = ApplicationListenerImpl.sysConfigureJson.getWxAppSecret();
		//String appid = ConfigUtil.getValue("wxAppID");
		//String secret = ConfigUtil.getValue("wxAppSecret");
		String grantType = "authorization_code";
		JSONObject jsonObject = JSONObject.fromObject(httproxy.get("https://api.weixin.qq.com/sns/oauth2/access_token?"+ 
				"appid="+ appid + "&secret=" + secret + "&code=" + code+
				"&grant_type=" + grantType, null));
		String token = jsonObject.getString("access_token");
		String openid = jsonObject.getString("openid");
		String unionid = null;
		if(jsonObject.has("unionid")){
			unionid = jsonObject.getString("unionid");
		}
		if (token == null || openid == null) {
			return null;
		}
		String ip = request.getHeader("X-Real-IP");
		if (ip == null) {
			ip = "127.0.0.1";
		}
		if(unionid!=null){
			user = userService.userByWeixinUnionId(unionid);
		}else{
			//user = userService.userByWebWxOpenId(openid);
			user = userService.userByWeixinOpenId(openid);
		}
		if(user!=null){
			//System.out.println("wxu!=null");
			if(!"0".equals(user.getMailCheck()) && !"0".equals(user.getMobileCheck())){
				String ipLocation = RegisterAction.seeker.getAddress(ip);
				String date = DateUtil.DateTimeToStr(new Date());
				user.setIpAddress(ip);
				user.setIpLocation(ipLocation);
				user.setNewDate(date);
				userService.add(user);
				
				//Struts2Utils.render("text/html", "<script>window.location.href=\"/register/wxUserInfoAuth.html\";</script>", new String[] { "encoding:UTF-8" });
			}
			Struts2Utils.render("text/html", "<script>window.location.href=\"/\";</script>", new String[] { "encoding:UTF-8" });
		}else{
			JSONObject result = JSONObject.fromObject(httproxy.get("https://api.weixin.qq.com/sns/userinfo?access_token=" + token+ 
					"&openid=" + openid, null));
			String nickName = result.get("nickname").toString();
			if(result.has("unionid")){
				unionid = result.getString("unionid");
			}
			user = new User();
			user.setWeixinOpenId(openid);
			user.setWeixinUnionId(unionid);
			String date = DateUtil.DateTimeToStr(new Date());
			if ((nickName != null) && (!nickName.equals(""))) {
				user.setUserName(nickName.trim());
			}
			user.setUserPwd(openid);
			user.setMobileCheck("3");
			user.setMailCheck("3");
			// user.setAttribute22(openId);
			user.setIpAddress(ip);
			String ipLocation = RegisterAction.seeker.getAddress(ip);
			user.setIpLocation(ipLocation);
			user.setOldDate(date);

			user.setBalance(ApplicationListenerImpl.sysConfigureJson
					.getRegBalance());
			user.setCommissionBalance(Double.valueOf(0.0D));
			user.setCommissionCount(Double.valueOf(0.0D));
			user.setCommissionMention(Double.valueOf(0.0D));
			user.setCommissionPoints(Integer.valueOf(0));
			user.setFaceImg("/Images/defaultUserFace.png");
			user.setUserType("0");
			user.setExperience(Integer.valueOf(0));
			Cookie c = CookieUtil.getCookie(request, "inviteId");
			if (c != null) {
				String inviteId = c.getValue();
				if ((inviteId != null) && (!inviteId.equals(""))) {
					user.setInvite(Integer.valueOf(Integer.parseInt(inviteId)));
					User user2 = (User) userService.findById(inviteId);
					int commissionPoints = user2.getCommissionPoints()
							.intValue();
					commissionPoints += ApplicationListenerImpl.sysConfigureJson
							.getInvite().intValue();
					user2.setCommissionPoints(Integer.valueOf(commissionPoints));
					userService.add(user2);
					commissionpoints = new Commissionpoints();
					commissionpoints
							.setDate(DateUtil.DateTimeToStr(new Date()));
					commissionpoints.setDetailed("邀请好友获得"
							+ ApplicationListenerImpl.sysConfigureJson
									.getInvite() + "积分");
					commissionpoints.setPay("+"
							+ ApplicationListenerImpl.sysConfigureJson
									.getBuyProduct());
					commissionpoints.setToUserId(Integer.valueOf(Integer
							.parseInt(inviteId)));
					commissionpointsService.add(commissionpoints);
				}
			}
			userService.add(user);
			Struts2Utils.render("text/html", "<script>window.location.href=\"/user/MobileBind.html\";</script>", new String[] { "encoding:UTF-8" });
		}
		if (user!=null){
			String domain = ApplicationListenerImpl.sysConfigureJson.getDomain();
			CookieUtil.addCookie(request, response, "userName", EscapeUtil.escape(user.getUserName()), 31536000, domain);
			CookieUtil.addCookie(request, response, "loginUser", EscapeUtil.escape(user.getUserName()), 31536000, domain);
			CookieUtil.addCookie(request, response, "userId", String.valueOf(user.getUserId()), 31536000, domain);
			CookieUtil.addCookie(request, response, "face", EscapeUtil.escape(user.getFaceImg()), 31536000, domain);
			
		}
		return null;
	}

	public String qcback() throws JSONException, HttpException, UnsupportedEncodingException
	{
		request = Struts2Utils.getRequest();
		response = Struts2Utils.getResponse();
		if(StringUtil.isEmpty(code)){
			return null;
		}
		String clientId = ApplicationListenerImpl.sysConfigureJson.getQqAppId();
		String appKey = ApplicationListenerImpl.sysConfigureJson.getQqAppKey();
		//String appid = ConfigUtil.getValue("wxAppID");
		//String secret = ConfigUtil.getValue("wxAppSecret");
		String grantType = "authorization_code";
		JSONObject jsonObject = JSONObject.fromObject(httproxy.get("https://graph.qq.com/oauth2.0/token?"+ 
				"client_id="+ clientId + "&client_secret=" + appKey + "&code=" + code+
				"&grant_type=" + grantType, null));
		String token = jsonObject.getString("access_token");
		
		String result = httproxy.get("https://graph.qq.com/oauth2.0/me?"+ "access_token=" +token, null);
		System.out.println("result="+result);
		JSONObject js = JSONObject.fromObject(result.substring("callback(".length(), result.length()-2).trim());
		String openid= js.getString("openid");

		if (token == null || openid == null) {
			return null;
		}
		String ip = request.getHeader("X-Real-IP");
		if (ip == null) {
			ip = "127.0.0.1";
		}
		user = userService.isNotOpenId(openid);
		if(user!=null){
			//System.out.println("wxu!=null");
			if(!"0".equals(user.getMailCheck()) && !"0".equals(user.getMobileCheck())){
				String ipLocation = RegisterAction.seeker.getAddress(ip);
				String date = DateUtil.DateTimeToStr(new Date());
				user.setIpAddress(ip);
				user.setIpLocation(ipLocation);
				user.setNewDate(date);
				userService.add(user);
				
				//Struts2Utils.render("text/html", "<script>window.location.href=\"/register/wxUserInfoAuth.html\";</script>", new String[] { "encoding:UTF-8" });
			}Struts2Utils.render("text/html", "<script>window.location.href=\"/\";</script>", new String[] { "encoding:UTF-8" });
		}else{
			JSONObject json = JSONObject.fromObject(httproxy.get("https://graph.qq.com/user/get_user_info?access_token=" + token
					+"&oauth_consumer_key="+clientId+"&openid=" + openid, null));
			System.out.println("json="+json.toString());
			String nickName = json.get("nickname").toString();
			/*if(result.has("unionid")){
				unionid = result.getString("unionid");
			}*/
			user = new User();
			user.setAttribute22(openid);
			String date = DateUtil.DateTimeToStr(new Date());
			if ((nickName != null) && (!nickName.equals(""))) {
				user.setUserName(nickName.trim());
			}
			user.setUserPwd(openid);
			user.setMobileCheck("3");
			user.setMailCheck("3");
			// user.setAttribute22(openId);
			user.setIpAddress(ip);
			String ipLocation = RegisterAction.seeker.getAddress(ip);
			user.setIpLocation(ipLocation);
			user.setOldDate(date);

			user.setBalance(ApplicationListenerImpl.sysConfigureJson
					.getRegBalance());
			user.setCommissionBalance(Double.valueOf(0.0D));
			user.setCommissionCount(Double.valueOf(0.0D));
			user.setCommissionMention(Double.valueOf(0.0D));
			user.setCommissionPoints(Integer.valueOf(0));
			user.setFaceImg("/Images/defaultUserFace.png");
			user.setUserType("0");
			user.setExperience(Integer.valueOf(0));
			Cookie c = CookieUtil.getCookie(request, "inviteId");
			if (c != null) {
				String inviteId = c.getValue();
				if ((inviteId != null) && (!inviteId.equals(""))) {
					user.setInvite(Integer.valueOf(Integer.parseInt(inviteId)));
					User user2 = (User) userService.findById(inviteId);
					int commissionPoints = user2.getCommissionPoints()
							.intValue();
					commissionPoints += ApplicationListenerImpl.sysConfigureJson
							.getInvite().intValue();
					user2.setCommissionPoints(Integer.valueOf(commissionPoints));
					userService.add(user2);
					commissionpoints = new Commissionpoints();
					commissionpoints
							.setDate(DateUtil.DateTimeToStr(new Date()));
					commissionpoints.setDetailed("邀请好友获得"
							+ ApplicationListenerImpl.sysConfigureJson
									.getInvite() + "积分");
					commissionpoints.setPay("+"
							+ ApplicationListenerImpl.sysConfigureJson
									.getBuyProduct());
					commissionpoints.setToUserId(Integer.valueOf(Integer
							.parseInt(inviteId)));
					commissionpointsService.add(commissionpoints);
				}
			}
			userService.add(user);
			Struts2Utils.render("text/html", "<script>window.location.href=\"/register/qqUserInfoAuth.html\";</script>", new String[] { "encoding:UTF-8" });
		}
		if (user!=null){
			String domain = ApplicationListenerImpl.sysConfigureJson.getDomain();
			CookieUtil.addCookie(request, response, "userName", EscapeUtil.escape(user.getUserName()), 31536000, domain);
			CookieUtil.addCookie(request, response, "loginUser", EscapeUtil.escape(user.getUserName()), 31536000, domain);
			CookieUtil.addCookie(request, response, "userId", String.valueOf(user.getUserId()), 31536000, domain);
			CookieUtil.addCookie(request, response, "face", EscapeUtil.escape(user.getFaceImg()), 31536000, domain);
		}
		return null;
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
  
  public Spellbuyrecord getSpellbuyrecord()
  {
    return this.spellbuyrecord;
  }
  
  public void setSpellbuyrecord(Spellbuyrecord spellbuyrecord)
  {
    this.spellbuyrecord = spellbuyrecord;
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
  
  public List<ProductJSON> getHotProductList()
  {
    return this.hotProductList;
  }
  
  public void setHotProductList(List<ProductJSON> hotProductList)
  {
    this.hotProductList = hotProductList;
  }
  
  public Latestlottery getLatestlottery()
  {
    return this.latestlottery;
  }
  
  public void setLatestlottery(Latestlottery latestlottery)
  {
    this.latestlottery = latestlottery;
  }
  
  public List<Latestlottery> getLatestlotteryList()
  {
    return this.latestlotteryList;
  }
  
  public void setLatestlotteryList(List<Latestlottery> latestlotteryList)
  {
    this.latestlotteryList = latestlotteryList;
  }
  
  public int getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getPageCount()
  {
    return this.pageCount;
  }
  
  public void setPageCount(int pageCount)
  {
    this.pageCount = pageCount;
  }
  
  public int getResultCount()
  {
    return this.resultCount;
  }
  
  public void setResultCount(int resultCount)
  {
    this.resultCount = resultCount;
  }
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
  }
  
  public List<News> getNewsList()
  {
    return newsList;
  }
  
  public void setNewsList(List<News> newsList)
  {
    newsList = newsList;
  }
  
  public ProductJSON getRecommendJSON()
  {
    return this.recommendJSON;
  }
  
  public void setRecommendJSON(ProductJSON recommendJSON)
  {
    this.recommendJSON = recommendJSON;
  }
  
  public String getUid()
  {
    return this.uid;
  }
  
  public void setUid(String uid)
  {
    this.uid = uid;
  }
  
	public String getShare() {
		return share;
	}
	
	public void setShare(String share) {
		this.share = share;
	}

  public List<ProductJSON> getProductList()
  {
    return this.productList;
  }
  
  public void setProductList(List<ProductJSON> productList)
  {
    this.productList = productList;
  }
  
  public String getStartDate()
  {
    return this.startDate;
  }
  
  public void setStartDate(String startDate)
  {
    this.startDate = startDate;
  }
  
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(String endDate)
  {
    this.endDate = endDate;
  }
  
  public List<BuyHistoryJSON> getBuyHistoryJSONList()
  {
    return this.buyHistoryJSONList;
  }
  
  public void setBuyHistoryJSONList(List<BuyHistoryJSON> buyHistoryJSONList)
  {
    this.buyHistoryJSONList = buyHistoryJSONList;
  }
  
  public BuyHistoryJSON getBuyHistoryJSON()
  {
    return this.buyHistoryJSON;
  }
  
  public void setBuyHistoryJSON(BuyHistoryJSON buyHistoryJSON)
  {
    this.buyHistoryJSON = buyHistoryJSON;
  }
  
  public List<IndexImg> getIndexImgList()
  {
    return this.indexImgList;
  }
  
  public void setIndexImgList(List<IndexImg> indexImgList)
  {
    this.indexImgList = indexImgList;
  }
  
  public List<ProductJSON> getNewProductList()
  {
    return this.newProductList;
  }
  
  public void setNewProductList(List<ProductJSON> newProductList)
  {
    this.newProductList = newProductList;
  }
  
  public void setIndexPopProductList(List<ProductJSON> indexPopProductList)
  {
    this.indexPopProductList = indexPopProductList;
  }
  
  public UserJSON getUserJSON()
  {
    return this.userJSON;
  }
  
  public void setUserJSON(UserJSON userJSON)
  {
    this.userJSON = userJSON;
  }
  
  public int getLotteryCount()
  {
    return this.lotteryCount;
  }
  
  public void setLotteryCount(int lotteryCount)
  {
    this.lotteryCount = lotteryCount;
  }
  
  public List<ProductJSON> getNewRecordList()
  {
    return newRecordList;
  }
  
  public void setNewRecordList(List<ProductJSON> newRecordList)
  {
    newRecordList = newRecordList;
  }

	public Commissionpoints getCommissionpoints() {
		return commissionpoints;
	}
	
	public void setCommissionpoints(Commissionpoints commissionpoints) {
		this.commissionpoints = commissionpoints;
	}
}
