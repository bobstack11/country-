package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.BuyHistoryJSON;
import com.egouos.pojo.Friends;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Product;
import com.egouos.pojo.ShareJSON;
import com.egouos.pojo.Shareimage;
import com.egouos.pojo.Shareinfo;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.User;
import com.egouos.pojo.Visitors;
import com.egouos.service.FriendsService;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.ShareService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.service.UserService;
import com.egouos.service.VisitorsService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.IPSeeker;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInfoAction
  extends ActionSupport
{
  private static final long serialVersionUID = 8426857698329495895L;
  @Autowired
  private UserService userService;
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ShareService shareService;
  @Autowired
  private FriendsService friendsService;
  @Autowired
  private VisitorsService visitorsService;
  private User user;
  private String id;
  private String userId;
  private int pageNo;
  private int pageSize = 12;
  private int pageCount;
  private int resultCount;
  private String userName;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private Spellbuyrecord spellbuyrecord;
  private BuyHistoryJSON buyHistoryJSON;
  private Latestlottery latestlottery;
  private List<BuyHistoryJSON> buyHistoryJSONList;
  private List<Shareimage> shareimageList;
  private List<ShareJSON> ShareJSONList;
  private ShareJSON shareJSON;
  private Shareinfo shareinfo;
  private Shareimage shareimage;
  private List<User> userList;
  private Friends friends;
  private Visitors visitors;
  HttpServletRequest request = null;
  
  public String index()
  {
    if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      

      return null;
    }
    this.request = Struts2Utils.getRequest();
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    this.user = ((User)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_user_" + this.id));
    if (this.user == null)
    {
      this.user = ((User)this.userService.findById(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_user_" + this.id, 5, this.user);
    }
    this.userName = UserNameUtil.userName(this.user);
    
    Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_datePage_" + this.id + "_" + this.pageNo);
    if (datePage == null)
    {
      datePage = this.spellbuyrecordService.buyHistoryByUser(this.id, null, null, this.pageNo, 6);
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_datePage_" + this.id + "_" + this.pageNo, 5, datePage);
    }
    List<BuyHistoryJSON> dataList = (List<BuyHistoryJSON>)datePage.getList();
    if ((dataList != null) && (dataList.size() > 0))
    {
      this.buyHistoryJSONList = new ArrayList();
      for (int j = 0; j < dataList.size(); j++) {
        try
        {
          this.buyHistoryJSON = ((BuyHistoryJSON)dataList.get(j));
          if (this.buyHistoryJSON.getBuyStatus().intValue() == 1)
          {
            List<Latestlottery> list = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_list_" + this.buyHistoryJSON.getProductId());
            if (list == null)
            {
              list = this.latestlotteryService.getBuyHistoryByDetail(this.buyHistoryJSON.getProductId());
              AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_list_" + this.buyHistoryJSON.getProductId(), 3600, list);
            }
            if ((list != null) && (list.size() > 0))
            {
              this.latestlottery = ((Latestlottery)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_latestlottery_" + this.buyHistoryJSON.getProductId()));
              if (this.latestlottery == null)
              {
                this.latestlottery = ((Latestlottery)this.latestlotteryService.getBuyHistoryByDetail(this.buyHistoryJSON.getProductId()).get(0));
                AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_latestlottery_" + this.buyHistoryJSON.getProductId(), 3600, this.latestlottery);
              }
            }
            if (this.latestlottery != null)
            {
              this.buyHistoryJSON.setBuyTime(DateUtil.getTime(DateUtil.SDateTimeToDate(this.latestlottery.getAnnouncedTime())));
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
              this.buyHistoryJSON.setWinUserId(this.latestlottery.getUserId());
              this.buyHistoryJSON.setWinUser(userer);
            }
          }
          else
          {
            String userName = UserNameUtil.userName(this.user);
            String timeStr = DateUtil.getTime(DateUtil.SDateTimeToDate(this.buyHistoryJSON.getBuyTime()));
            this.buyHistoryJSON.setBuyTime(timeStr);
            this.buyHistoryJSON.setWinUser(userName);
          }
          this.buyHistoryJSONList.add(this.buyHistoryJSON);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    Cookie[] cookies = this.request.getCookies();
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId"))
        {
          this.userId = cookie.getValue();
          if ((this.userId != null) && (!this.userId.equals("")))
          {
            this.visitors = ((Visitors)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_visitors_" + this.id + "_" + this.userId));
            if (this.visitors == null)
            {
              this.visitors = this.visitorsService.findVisitorsByUserIdAndVisitorsId(this.id, this.userId);
              if (this.visitors != null) {
                AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_visitors_" + this.id + "_" + this.userId, 5, this.visitors);
              }
            }
            if (this.visitors != null)
            {
              List<Visitors> visitorsList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_visitorsList_" + this.id + "_" + this.userId);
              if (visitorsList == null)
              {
                visitorsList = this.visitorsService.findVisitors(this.id, this.userId, DateUtil.DateTimeToStr(DateUtils.addHours(new Date(), -1)), DateUtil.DateTimeToStr(new Date()));
                AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_visitorsList_" + this.id + "_" + this.userId, 3600, visitorsList);
              }
              if (visitorsList.size() < 0)
              {
                String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
                if (ip == null) {
                  ip = "127.0.0.1";
                }
                String ipLocation = RegisterAction.seeker.getAddress(ip);
                this.visitors.setAddress(ipLocation);
                this.visitors.setDate(DateUtil.DateTimeToStr(new Date()));
                this.visitors.setUid(Integer.valueOf(Integer.parseInt(this.id)));
                this.visitors.setVisitorsId(Integer.valueOf(Integer.parseInt(this.userId)));
                this.visitorsService.add(this.visitors);
              }
            }
            else
            {
              String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
              if (ip == null) {
                ip = "127.0.0.1";
              }
              String ipLocation = RegisterAction.seeker.getAddress(ip);
              this.visitors = new Visitors();
              this.visitors.setAddress(ipLocation);
              this.visitors.setDate(DateUtil.DateTimeToStr(new Date()));
              this.visitors.setUid(Integer.valueOf(Integer.parseInt(this.id)));
              this.visitors.setVisitorsId(Integer.valueOf(Integer.parseInt(this.userId)));
              this.visitorsService.add(this.visitors);
            }
          }
        }
      }
    }
    return "index";
  }
  
  public static void main(String[] args)
  {
    System.err.println(DateUtil.DateTimeToStr(DateUtils.addHours(new Date(), -1)));
  }
  
  public String userBuy()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    this.user = ((User)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_user_" + this.id));
    if (this.user == null)
    {
      this.user = ((User)this.userService.findById(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_user_" + this.id, 5, this.user);
    }
    this.userName = UserNameUtil.userName(this.user);
    String rCount = (String)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_userBuy_" + this.id);
    if (rCount == null)
    {
      this.resultCount = this.spellbuyrecordService.buyHistoryByUserByCount(this.id, null, null).intValue();
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_userBuy_" + this.id, 5, String.valueOf(this.resultCount));
    }
    else
    {
      this.resultCount = Integer.parseInt(rCount);
    }
    return "UserBuy";
  }
  
  public String userBuyAjaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    this.user = ((User)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_user_" + this.id));
    if (this.user == null)
    {
      this.user = ((User)this.userService.findById(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_user_" + this.id, 5, this.user);
    }
    Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_userBuyAjaxPage_datePage_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (datePage == null)
    {
      datePage = this.spellbuyrecordService.buyHistoryByUser(this.id, null, null, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_userBuyAjaxPage_datePage_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 5, datePage);
    }
    List<BuyHistoryJSON> dataList = (List<BuyHistoryJSON>)datePage.getList();
    this.buyHistoryJSONList = new ArrayList();
    if ((dataList != null) && (dataList.size() > 0)) {
      for (int j = 0; j < dataList.size(); j++)
      {
        this.buyHistoryJSON = ((BuyHistoryJSON)dataList.get(j));
        if (this.buyHistoryJSON.getBuyStatus().intValue() == 1)
        {
          List<Latestlottery> list = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_list_" + this.buyHistoryJSON.getProductId());
          if (list == null)
          {
            list = this.latestlotteryService.getBuyHistoryByDetail(this.buyHistoryJSON.getProductId());
            AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_list_" + this.buyHistoryJSON.getProductId(), 3600, list);
          }
          if ((list != null) && (list.size() > 0))
          {
            this.latestlottery = ((Latestlottery)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_latestlottery_" + this.buyHistoryJSON.getProductId()));
            if (this.latestlottery == null)
            {
              this.latestlottery = ((Latestlottery)this.latestlotteryService.getBuyHistoryByDetail(this.buyHistoryJSON.getProductId()).get(0));
              AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_latestlottery_" + this.buyHistoryJSON.getProductId(), 3600, this.latestlottery);
            }
          }
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
          this.buyHistoryJSON.setWinUserId(this.latestlottery.getUserId());
        }
        else if (this.buyHistoryJSON.getBuyStatus().intValue() == 2)
        {
          this.buyHistoryJSON = ((BuyHistoryJSON)dataList.get(j));
        }
        else
        {
          String userName = UserNameUtil.userName(this.user);
          this.buyHistoryJSON.setWinUser(userName);
        }
        this.buyHistoryJSONList.add(this.buyHistoryJSON);
      }
    }
    Struts2Utils.renderJson(this.buyHistoryJSONList, new String[0]);
    return null;
  }
  
  public String userRaffle()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    this.user = ((User)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_user_" + this.id));
    if (this.user == null)
    {
      this.user = ((User)this.userService.findById(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_user_" + this.id, 5, this.user);
    }
    this.userName = UserNameUtil.userName(this.user);
    String rCount = (String)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_userRaffle_" + this.id);
    if (rCount == null)
    {
      this.resultCount = this.latestlotteryService.getProductByUserByCount(this.id, null, null).intValue();
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_userRaffle_" + this.id, 5, String.valueOf(this.resultCount));
    }
    else
    {
      this.resultCount = Integer.parseInt(rCount);
    }
    return "UserRaffle";
  }
  
  public String userRaffleAjaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_userRaffleAjaxPage_datePage_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (datePage == null)
    {
      datePage = this.latestlotteryService.getProductByUser(this.id, null, null, null, null, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_userRaffleAjaxPage_datePage_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 5, datePage);
    }
    List<Latestlottery> dataList = (List<Latestlottery>)datePage.getList();
    this.buyHistoryJSONList = new ArrayList();
    if ((dataList != null) && (dataList.size() > 0)) {
      for (int j = 0; j < dataList.size(); j++)
      {
        this.buyHistoryJSON = new BuyHistoryJSON();
        this.latestlottery = ((Latestlottery)dataList.get(j));
        this.buyHistoryJSON.setProductId(this.latestlottery.getSpellbuyProductId());
        this.buyHistoryJSON.setProductImg(this.latestlottery.getProductImg());
        this.buyHistoryJSON.setProductName(this.latestlottery.getProductName());
        this.buyHistoryJSON.setProductPeriod(this.latestlottery.getProductPeriod());
        this.buyHistoryJSON.setProductPrice(this.latestlottery.getProductPrice());
        this.buyHistoryJSON.setProductTitle(this.latestlottery.getProductTitle());
        this.buyHistoryJSON.setWinDate(this.latestlottery.getAnnouncedTime());
        this.buyHistoryJSON.setWinId(this.latestlottery.getRandomNumber());
        this.buyHistoryJSONList.add(this.buyHistoryJSON);
      }
    }
    Struts2Utils.renderJson(this.buyHistoryJSONList, new String[0]);
    return null;
  }
  
  public String userPost()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    this.user = ((User)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_user_" + this.id));
    if (this.user == null)
    {
      this.user = ((User)this.userService.findById(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_user_" + this.id, 5, this.user);
    }
    this.userName = UserNameUtil.userName(this.user);
    Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_userPost_datePage_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (datePage == null)
    {
      datePage = this.shareService.UserInfoShareByUser(this.id, null, null, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_userPost_datePage_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 5, datePage);
    }
    this.resultCount = datePage.getResultCount();
    return "UserPost";
  }
  
  public String userPostAjaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_userPostAjaxPage_page_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (page == null)
    {
      page = this.shareService.UserInfoShareByUser(this.id, null, null, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_userPostAjaxPage_page_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 5, page);
    }
    List<Object[]> pageList = (List<Object[]>)page.getList();
    this.ShareJSONList = new ArrayList();
    if ((pageList != null) && (pageList.size() > 0)) {
      for (int i = 0; i < pageList.size(); i++)
      {
        this.shareJSON = new ShareJSON();
        Object[] objs = (Object[])pageList.get(i);
  	  for(Object obj:objs){
  		if(obj instanceof Shareinfo){
  			shareinfo = (Shareinfo)obj;
  		}
  		if(obj instanceof Shareimage){
  			shareimage = (Shareimage)obj;
  		}
  		if(obj instanceof Latestlottery){
  			latestlottery = (Latestlottery)obj;
  		}
  	  }
        //this.shareinfo = ((Shareinfo)((Object[])pageList.get(i))[0]);
        //this.shareimage = ((Shareimage)((Object[])pageList.get(i))[1]);
        //this.latestlottery = ((Latestlottery)((Object[])pageList.get(i))[2]);
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
        this.shareJSON.setAnnouncedTime(this.latestlottery.getAnnouncedTime().substring(0, 10));
        this.shareJSON.setReplyCount(this.shareinfo.getReplyCount());
        this.shareJSON.setReward(this.shareinfo.getReward());
        this.shareJSON.setShareContent(this.shareinfo.getShareContent());
        this.shareJSON.setShareDate(this.shareinfo.getShareDate());
        



        this.shareJSON.setShareImages(this.shareimage.getImages());
        this.shareJSON.setShareTitle(this.shareinfo.getShareTitle());
        this.shareJSON.setUid(this.shareinfo.getUid());
        this.shareJSON.setUpCount(this.shareinfo.getUpCount());
        this.shareJSON.setUserName(userName);
        this.shareJSON.setUserFace(this.latestlottery.getUserFace());
        this.shareJSON.setUserId(this.latestlottery.getUserId()+"");
        this.ShareJSONList.add(this.shareJSON);
      }
    }
    Struts2Utils.renderJson(this.ShareJSONList, new String[0]);
    return null;
  }
  
  public String userFriends()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    this.user = ((User)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_user_" + this.id));
    if (this.user == null)
    {
      this.user = ((User)this.userService.findById(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_user_" + this.id, 5, this.user);
    }
    this.userName = UserNameUtil.userName(this.user);
    Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_userFriends_datePage_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (datePage == null)
    {
      datePage = this.friendsService.getFriends(this.id, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_userFriends_datePage_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 5, datePage);
    }
    this.resultCount = datePage.getResultCount();
    return "UserFriends";
  }
  
  public String userFriendsAjaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_userFriendsAjaxPage_page_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (page == null)
    {
      page = this.friendsService.getFriends(this.id, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_userFriendsAjaxPage_page_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 5, page);
    }
    List<Object[]> pageList = (List<Object[]>)page.getList();
    this.userList = new ArrayList();
    if ((pageList != null) && (pageList.size() > 0)) {
      for (int i = 0; i < pageList.size(); i++)
      {
    	  Object[] objs = (Object[])pageList.get(i);
      	  for(Object obj:objs){
      		if(obj instanceof Friends){
      			friends = (Friends)obj;
      		}
      		if(obj instanceof User){
      			user = (User)obj;
      		}
      	  }
      	//this.friends = ((Friends)((Object[])pageList.get(i))[0]);
        //this.user = ((User)((Object[])pageList.get(i))[1]);
        this.userName = UserNameUtil.userName(this.user);
        this.user.setUserId(this.friends.getFriendsId());
        this.user.setUserName(this.userName);
        this.userList.add(this.user);
      }
    }
    Struts2Utils.renderJson(this.userList, new String[0]);
    return null;
  }
  
  public String visitors()
  {
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId"))
        {
          this.userId = cookie.getValue();
          if ((this.userId != null) && (!this.userId.equals("")))
          {
            this.user = ((User)this.userService.findById(this.userId));
            this.visitors = new Visitors();
            this.visitors.setAddress(this.user.getIpLocation());
            this.visitors.setDate(DateUtil.DateTimeToStr(new Date()));
            this.visitors.setUid(Integer.valueOf(Integer.parseInt(this.id)));
            this.visitors.setVisitorsId(Integer.valueOf(Integer.parseInt(this.userId)));
            this.visitorsService.add(this.visitors);
          }
        }
      }
    }
    return null;
  }
  
  public String visitorsList()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("userInfo_visitorsList_page_" + this.userId + "_" + this.pageNo);
    if (page == null)
    {
      page = this.visitorsService.getVisitors(this.userId, this.pageNo, 9);
      AliyunOcsSampleHelp.getIMemcachedCache().set("userInfo_visitorsList_page_" + this.userId + "_" + this.pageNo, 5, page);
    }
    List<Object[]> pageList = (List<Object[]>)page.getList();
    this.userList = new ArrayList();
    if ((pageList != null) && (pageList.size() > 0)) {
      for (int i = 0; i < pageList.size(); i++)
      {
    	  Object[] objs = (Object[])pageList.get(i);
      	  for(Object obj:objs){
      		if(obj instanceof User){
      			user = (User)obj;
      		}
      		if(obj instanceof Visitors){
      			visitors = (Visitors)obj;
      		}
      	  }
        //this.user = ((User)((Object[])pageList.get(i))[0]);
        //this.visitors = ((Visitors)((Object[])pageList.get(i))[1]);
        this.userName = UserNameUtil.userName(this.user);
        this.user.setNewDate(DateUtil.getTime(DateUtil.SDateTimeToDate(this.visitors.getDate())));
        this.user.setUserId(this.visitors.getVisitorsId());
        this.user.setUserName(this.userName);
        this.user.setIpLocation(this.visitors.getAddress());
        this.userList.add(this.user);
      }
    }
    Struts2Utils.renderJson(this.userList, new String[0]);
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
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
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
  
  public BuyHistoryJSON getBuyHistoryJSON()
  {
    return this.buyHistoryJSON;
  }
  
  public void setBuyHistoryJSON(BuyHistoryJSON buyHistoryJSON)
  {
    this.buyHistoryJSON = buyHistoryJSON;
  }
  
  public Latestlottery getLatestlottery()
  {
    return this.latestlottery;
  }
  
  public void setLatestlottery(Latestlottery latestlottery)
  {
    this.latestlottery = latestlottery;
  }
  
  public List<BuyHistoryJSON> getBuyHistoryJSONList()
  {
    return this.buyHistoryJSONList;
  }
  
  public void setBuyHistoryJSONList(List<BuyHistoryJSON> buyHistoryJSONList)
  {
    this.buyHistoryJSONList = buyHistoryJSONList;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public List<ShareJSON> getShareJSONList()
  {
    return this.ShareJSONList;
  }
  
  public void setShareJSONList(List<ShareJSON> shareJSONList)
  {
    this.ShareJSONList = shareJSONList;
  }
  
  public ShareJSON getShareJSON()
  {
    return this.shareJSON;
  }
  
  public void setShareJSON(ShareJSON shareJSON)
  {
    this.shareJSON = shareJSON;
  }
  
  public Shareinfo getShareinfo()
  {
    return this.shareinfo;
  }
  
  public void setShareinfo(Shareinfo shareinfo)
  {
    this.shareinfo = shareinfo;
  }
  
  public Shareimage getShareimage()
  {
    return this.shareimage;
  }
  
  public void setShareimage(Shareimage shareimage)
  {
    this.shareimage = shareimage;
  }
  
  public List<User> getUserList()
  {
    return this.userList;
  }
  
  public void setUserList(List<User> userList)
  {
    this.userList = userList;
  }
  
  public Friends getFriends()
  {
    return this.friends;
  }
  
  public void setFriends(Friends friends)
  {
    this.friends = friends;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public Visitors getVisitors()
  {
    return this.visitors;
  }
  
  public void setVisitors(Visitors visitors)
  {
    this.visitors = visitors;
  }
  
  public List<Shareimage> getShareimageList()
  {
    return this.shareimageList;
  }
  
  public void setShareimageList(List<Shareimage> shareimageList)
  {
    this.shareimageList = shareimageList;
  }
}
