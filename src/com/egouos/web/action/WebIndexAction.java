package com.egouos.web.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.BuyHistoryJSON;
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
import com.egouos.service.LatestlotteryService;
import com.egouos.service.NewsService;
import com.egouos.service.RecommendService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.service.UserService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("WebIndexAction")
public class WebIndexAction
  extends ActionSupport
{
  private static final long serialVersionUID = -1247058159354574031L;
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
  private int pageNo;
  private int pageSize = 20;
  private int pageCount;
  private int resultCount;
  private String startDate;
  private String endDate;
  private List<IndexImg> indexImgList;
  private String uid;
  private static List<ProductJSON> nowBuyProductList;
  private static List<ProductJSON> newRecordList;
  private static Long allBuyCount;
  private static Long nowDateByAllCount = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateByAllCount;
  private static Long nowDateByNowBuyProduct = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateByNowBuyProduct;
  private static Long nowDateByNewRecord = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateByNewRecord;
  HttpServletRequest request = null;
  HttpServletResponse response = null;
  
  public String index()
  {
    if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      

      return null;
    }
    List<Latestlottery> objList = this.latestlotteryService.indexWinningScroll();
    this.latestlotteryList = new ArrayList();
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
    List<Object[]> objectList = this.recommendService.getRecommend();
    if ((objectList != null) && (objectList.size() > 0))
    {
      this.recommendJSON = new ProductJSON();
      this.product = ((Product)((Object[])objectList.get(0))[0]);
      this.spellbuyproduct = ((Spellbuyproduct)((Object[])objectList.get(0))[1]);
      this.recommendJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
      this.recommendJSON.setHeadImage(this.product.getHeadImage());
      this.recommendJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      this.recommendJSON.setProductName(this.product.getProductName());
      this.recommendJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
      this.recommendJSON.setProductTitle(this.product.getProductTitle());
    }
    if (newsList == null) {
      newsList = this.newsService.indexNews();
    }
    this.indexImgList = JSONArray.fromObject(ApplicationListenerImpl.indexImgAll);
    




    Pagination datePage = this.spellbuyproductService.upcomingAnnounced(this.pageNo, 5);
    List<Object[]> dateList = (List<Object[]>) datePage.getList();
    this.productList = new ArrayList();
    for (int i = 0; i < dateList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      this.product = ((Product)((Object[])dateList.get(i))[0]);
      this.spellbuyproduct = ((Spellbuyproduct)((Object[])dateList.get(i))[1]);
      this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
      this.productJSON.setHeadImage(this.product.getHeadImage());
      this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
      this.productJSON.setProductName(this.product.getProductName());
      this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
      this.productJSON.setProductTitle(this.product.getProductTitle());
      this.productJSON.setProductStyle(this.product.getStyle());
      this.productList.add(this.productJSON);
    }
    return "index";
  }
  
  public void getIndexHotProductList()
  {
    Pagination hotPage = this.spellbuyrecordService.findHotProductList(1, 8);
    List<Object[]> HotList = (List<Object[]>) hotPage.getList();
    this.hotProductList = new ArrayList();
    for (int i = 0; i < HotList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      this.product = ((Product)((Object[])HotList.get(i))[0]);
      this.spellbuyproduct = ((Spellbuyproduct)((Object[])HotList.get(i))[1]);
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
    Pagination phoneDigitalPage = this.spellbuyrecordService.indexNewProductList(this.pageNo, 10);
    List<Object[]> phoneDigitalList = (List<Object[]>) phoneDigitalPage.getList();
    this.newProductList = new ArrayList();
    for (int i = 0; i < phoneDigitalList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      this.product = ((Product)((Object[])phoneDigitalList.get(i))[0]);
      this.spellbuyproduct = ((Spellbuyproduct)((Object[])phoneDigitalList.get(i))[1]);
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
    Pagination ComputerOfficePage = this.spellbuyrecordService.indexHotProductList(this.pageNo, 8);
    List<Object[]> ComputerOfficeList = (List<Object[]>) ComputerOfficePage.getList();
    this.indexPopProductList = new ArrayList();
    for (int i = 0; i < ComputerOfficeList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      this.product = ((Product)((Object[])ComputerOfficeList.get(i))[0]);
      this.spellbuyproduct = ((Spellbuyproduct)((Object[])ComputerOfficeList.get(i))[1]);
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
      List<Object[]> newBuyList = (List<Object[]>) page.getList();
      nowBuyProductList = new ArrayList();
      for (int i = 0; i < newBuyList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])newBuyList.get(i))[0]);
        this.spellbuyrecord = ((Spellbuyrecord)((Object[])newBuyList.get(i))[1]);
        this.user = ((User)((Object[])newBuyList.get(i))[2]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])newBuyList.get(i))[3]);
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
      List<Object[]> newBuyList = (List<Object[]>) page.getList();
      nowBuyProductList = new ArrayList();
      for (int i = 0; i < newBuyList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])newBuyList.get(i))[0]);
        this.spellbuyrecord = ((Spellbuyrecord)((Object[])newBuyList.get(i))[1]);
        this.user = ((User)((Object[])newBuyList.get(i))[2]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])newBuyList.get(i))[3]);
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
    Date date = new Date();
    date = DateUtil.SDateTimeToDate("2014-09-23 16:14:29");
    
    Struts2Utils.renderText(System.currentTimeMillis()+"");
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
    HttpServletRequest request = Struts2Utils.getRequest();
    Cookie[] cookies = request.getCookies();
    if (request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId")) {
          this.uid = cookie.getValue();
        }
      }
    }
    return "referAuthLogin";
  }
  
  public String share()
  {
    this.request = Struts2Utils.getRequest();
    this.response = Struts2Utils.getResponse();
    if (this.request.isRequestedSessionIdFromCookie())
    {
      Cookie cookie = new Cookie("inviteId", this.uid);
      cookie.setMaxAge(43200);
      cookie.setPath("/");
      cookie.setDomain(ApplicationListenerImpl.sysConfigureJson.getDomain());
      this.response.addCookie(cookie);
    }
    String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
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
        AliyunOcsSampleHelp.getIMemcachedCache().set(this.uid, 43200000, Integer.valueOf(1));
      }
    }
    Struts2Utils.render("text/html", "<script>window.location.href=\"/?share=" + this.uid + "\";</script>", new String[] { "encoding:UTF-8" });
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
    Pagination page = this.spellbuyrecordService.getNowBuyList(this.pageNo, 100);
    List<Object[]> newBuyList = (List<Object[]>) page.getList();
    this.productList = new ArrayList();
    for (int i = 0; i < newBuyList.size(); i++)
    {
      this.productJSON = new ProductJSON();
      this.product = ((Product)((Object[])newBuyList.get(i))[0]);
      this.spellbuyrecord = ((Spellbuyrecord)((Object[])newBuyList.get(i))[1]);
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
      this.productList.add(this.productJSON);
    }
    return "newRecord";
  }
  
  public void getNewRecordAjax()
  {
    nowDateByNewRecord = Long.valueOf(System.currentTimeMillis());
    if (beginDateByNewRecord == null)
    {
      Pagination page = this.spellbuyrecordService.getNowBuyAjaxList(this.pageNo, 100, Integer.parseInt(this.id));
      List<Object[]> newBuyList = (List<Object[]>) page.getList();
      newRecordList = new ArrayList();
      for (int i = 0; i < newBuyList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])newBuyList.get(i))[0]);
        this.spellbuyrecord = ((Spellbuyrecord)((Object[])newBuyList.get(i))[1]);
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
      beginDateByNewRecord = Long.valueOf(System.currentTimeMillis());
      
      Struts2Utils.renderJson(newRecordList, new String[0]);
    }
    else if (nowDateByNewRecord.longValue() - beginDateByNewRecord.longValue() < 5000L)
    {
      Struts2Utils.renderJson(newRecordList, new String[0]);
    }
    else
    {
      beginDateByNewRecord = Long.valueOf(System.currentTimeMillis());
      Pagination page = this.spellbuyrecordService.getNowBuyAjaxList(this.pageNo, 100, Integer.parseInt(this.id));
      List<Object[]> newBuyList = (List<Object[]>) page.getList();
      newRecordList = new ArrayList();
      for (int i = 0; i < newBuyList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])newBuyList.get(i))[0]);
        this.spellbuyrecord = ((Spellbuyrecord)((Object[])newBuyList.get(i))[1]);
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
      Struts2Utils.renderJson(newRecordList, new String[0]);
    }
  }
  
  public String getAllBuyRecord()
  {
    Pagination datePage = this.spellbuyrecordService.getAllBuyRecord(this.startDate, this.endDate, this.pageNo, this.pageSize);
    List<Object[]> dataList = (List<Object[]>) datePage.getList();
    this.buyHistoryJSONList = new ArrayList();
    for (int j = 0; j < dataList.size(); j++)
    {
      this.buyHistoryJSON = new BuyHistoryJSON();
      this.product = ((Product)((Object[])dataList.get(j))[0]);
      this.spellbuyrecord = ((Spellbuyrecord)((Object[])dataList.get(j))[1]);
      this.spellbuyproduct = ((Spellbuyproduct)((Object[])dataList.get(j))[2]);
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
}
