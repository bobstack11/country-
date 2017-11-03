package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Lotteryproductutil;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Randomnumber;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.User;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.LotteryproductutilService;
import com.egouos.service.ProductService;
import com.egouos.service.RandomnumberService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.CaipiaoUtil;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("LotteryAction")
public class LotteryAction
  extends ActionSupport
{
  private static final long serialVersionUID = 2321693841189871589L;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private ProductService productService;
  @Autowired
  private RandomnumberService randomnumberService;
  @Autowired
  private LotteryproductutilService lotteryproductutilService;
  private Latestlottery latestlottery;
  private Latestlottery latestlotteryByAjax;
  private List<Latestlottery> latestlotteryList;
  private ProductJSON productJSON;
  private ProductCart productCart;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private Spellbuyrecord spellbuyrecord;
  private static List<Lotteryproductutil> LotteryproductutilList;
  private User user;
  private String id;
  private int pageNo;
  private String pages;
  private String pageString;
  private int pageSize = 24;
  private int pageCount;
  private int resultCount;
  private long time;
  Calendar calendar = Calendar.getInstance();
  private static List<ProductJSON> upcomingAnnouncedList;
  private static List<ProductJSON> upcomingAnnouncedByTopList;
  private static Long nowDateByUpcomingAnnounced = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateByUpcomingAnnounced;
  private static Long nowDateByUpcomingAnnouncedByTop = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateByUpcomingAnnouncedByTop;
  private static Long nowDateBylatteryUtil = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateBylatteryUtil;
  
  public String index()
  {
    /*if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      return null;
    }*/
    return "index";
  }
  
  public void getLotteryList()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (this.pages != null) {
      this.pageNo = Integer.parseInt(this.pages.split("_")[1]);
    }
    Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("lottery_index_page_" + this.pageNo + "_" + this.pageSize);
    if (page == null)
    {
      page = this.latestlotteryService.LatestAnnounced(this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("lottery_index_page_" + this.pageNo + "_" + this.pageSize, 5, page);
    }
    this.resultCount = page.getResultCount();
    List<Latestlottery> objList = (List<Latestlottery>)page.getList();
    this.latestlotteryList = new ArrayList();
    for (int i = 0; i < objList.size(); i++) {
      try
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
        this.latestlottery.setAnnouncedTime(DateUtil.getShortTime(this.latestlottery.getAnnouncedTime()));
        this.latestlottery.setBuyUser(userName);
        this.latestlotteryList.add(this.latestlottery);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    Struts2Utils.renderJson(this.latestlotteryList, new String[0]);
  }
  
  public void lotteryproductutilList()
  {
    nowDateBylatteryUtil = Long.valueOf(System.currentTimeMillis());
    if (beginDateBylatteryUtil == null)
    {
      LotteryproductutilList = this.lotteryproductutilService.loadAll(2);
      //LotteryproductutilList = this.lotteryproductutilService.findList(1,5);
      if (LotteryproductutilList.size() > 0)
      {
        for (Lotteryproductutil lotteryproductutil : LotteryproductutilList)
        {
          Long nowDate = Long.valueOf(System.currentTimeMillis());
          Long endDate = Long.valueOf(DateUtil.SDateTimeToDate(lotteryproductutil.getLotteryProductEndDate()).getTime());
          lotteryproductutil.setLotteryProductEndDate(((endDate.longValue() - nowDate.longValue()) / 1000L)+"");
        }
        Struts2Utils.renderJson(LotteryproductutilList, new String[0]);
      }
      beginDateBylatteryUtil = Long.valueOf(System.currentTimeMillis());
    }
    else if (nowDateBylatteryUtil.longValue() - beginDateBylatteryUtil.longValue() < 2000L)
    {
      Struts2Utils.renderJson(LotteryproductutilList, new String[0]);
    }
    else
    {
      beginDateBylatteryUtil = Long.valueOf(System.currentTimeMillis());
      LotteryproductutilList = this.lotteryproductutilService.loadAll(2);
      //LotteryproductutilList = this.lotteryproductutilService.findList(1,5);
      if (LotteryproductutilList.size() > 0)
      {
        for (Lotteryproductutil lotteryproductutil : LotteryproductutilList)
        {
          Long nowDate = Long.valueOf(System.currentTimeMillis());
          Long endDate = Long.valueOf(DateUtil.SDateTimeToDate(lotteryproductutil.getLotteryProductEndDate()).getTime());
          lotteryproductutil.setLotteryProductEndDate(((endDate.longValue() - nowDate.longValue()) / 1000L)+"");
        }
        Struts2Utils.renderJson(LotteryproductutilList);
      }
    }
  }
  
  public void LatestlotteryByProductId()
  {
    this.latestlotteryByAjax = ((Latestlottery)AliyunOcsSampleHelp.getIMemcachedCache().get("latestlotteryByAjax_" + this.id));
    if (this.latestlotteryByAjax == null)
    {
      this.latestlotteryByAjax = ((Latestlottery)this.latestlotteryService.findById(this.id));
      if (this.latestlotteryByAjax != null)
      {
        Struts2Utils.renderJson(this.latestlotteryByAjax, new String[0]);
        AliyunOcsSampleHelp.getIMemcachedCache().set("latestlotteryByAjax_" + this.id, 1800, this.latestlotteryByAjax);
      }
      else
      {
        Struts2Utils.renderText("false", new String[0]);
      }
    }
    else
    {
      Struts2Utils.renderJson(this.latestlotteryByAjax, new String[0]);
    }
  }
  
  public synchronized void lotteryUtil()
    throws NumberFormatException, IOException
  {
    List listbyLatest = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_listbyLatest_" + this.id);
    if (listbyLatest == null)
    {
      listbyLatest = this.latestlotteryService.getLatestlotteryBySpellbuyProductIdAndProductIdIsExist(Integer.valueOf(Integer.parseInt(this.id)));
      if (listbyLatest.size() > 0)
      {
        Struts2Utils.renderText("true", new String[0]);
        AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_listbyLatest_" + this.id, 1800, listbyLatest);
      }
      else
      {
        Struts2Utils.renderText("false", new String[0]);
      }
    }
    else
    {
      Struts2Utils.renderText("true", new String[0]);
    }
  }
  
  public synchronized boolean lotteryUtilBy(String id)
    throws NumberFormatException, IOException
  {
    boolean flag = false;
    String lotteryId = MD5Util.encode(id);
    if (AliyunOcsSampleHelp.getIMemcachedCache().get(lotteryId) == null)
    {
      AliyunOcsSampleHelp.getIMemcachedCache().set(lotteryId, 1800, "y");
      this.spellbuyproduct = ((Spellbuyproduct)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_spellbuyproduct_" + id));
      if (this.spellbuyproduct == null)
      {
        this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(id));
        AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_spellbuyproduct_" + id, 1, this.spellbuyproduct);
      }
      if (this.spellbuyproduct.getSpStatus().intValue() == 2)
      {
        this.spellbuyrecord = ((Spellbuyrecord)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_spellbuyrecord_" + this.spellbuyproduct.getSpellbuyProductId()));
        if (this.spellbuyrecord == null)
        {
          this.spellbuyrecord = ((Spellbuyrecord)this.spellbuyrecordService.getEndBuyDateByProduct(this.spellbuyproduct.getSpellbuyProductId()).get(0));
          AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_spellbuyrecord_" + this.spellbuyproduct.getSpellbuyProductId(), 1800, this.spellbuyrecord);
        }
        String newDate = this.spellbuyrecord.getBuyDate();
        List<Spellbuyrecord> dataList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_dataList_" + DateUtil.SDateTimeToDateBySSS(newDate).getTime());
        if (dataList == null)
        {
          dataList = this.spellbuyrecordService.getSpellbuyRecordByLast100(null, newDate, 60);
          AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_dataList_" + DateUtil.SDateTimeToDateBySSS(newDate).getTime(), 1800, dataList);
        }
        Long DateSUM = Long.valueOf(0L);
        Integer buyId = ((Spellbuyrecord)dataList.get(0)).getFkSpellbuyProductId();
        int i100 = 0;
        for (int k = 0; k < dataList.size(); k++) {
          if ((k <= 0) || 
          
            (!newDate.equals(((Spellbuyrecord)dataList.get(k)).getBuyDate())) || (((Spellbuyrecord)dataList.get(k)).getFkSpellbuyProductId() == buyId))
          {
            if (i100++ == 100) {
              break;
            }
            this.spellbuyrecord = ((Spellbuyrecord)dataList.get(k));
            
            this.calendar.setTime(DateUtil.SDateTimeToDate(this.spellbuyrecord.getBuyDate()));
            


            Integer h = Integer.valueOf(this.calendar.get(11));
            Integer m = Integer.valueOf(this.calendar.get(12));
            Integer s1 = Integer.valueOf(this.calendar.get(13));
            Integer ss1 = Integer.valueOf(this.calendar.get(14));
            String sh = "";
            String sm = "";
            String ss = "";
            String sss = "";
            if (h.intValue() < 10) {
              sh = "0" + h;
            } else {
              sh = h.toString();
            }
            if (m.intValue() < 10) {
              sm = "0" + m;
            } else {
              sm = m.toString();
            }
            if (s1.intValue() < 10) {
              ss = "0" + s1;
            } else {
              ss = s1.toString();
            }
            if (ss1.intValue() < 10) {
              sss = "00" + ss1;
            } else if (ss1.intValue() < 100) {
              sss = "0" + ss1;
            } else {
              sss = ss1.toString();
            }
            DateSUM = Long.valueOf(DateSUM.longValue() + Long.parseLong(sh + sm + ss + sss));
          }
        }
        System.err.println("NewLotteryUtil DateSUM:" + DateSUM + "    " + id);
        
        String str = CaipiaoUtil.caiNumber();
        String sscNumber = str.split("\\|")[1];
        Long sscPeriod = Long.valueOf(Long.parseLong(str.split("\\|")[0]));
        Integer winNumber = Integer.valueOf(Integer.parseInt(String.valueOf((DateSUM.longValue() + Integer.parseInt(sscNumber)) % this.spellbuyproduct.getSpellbuyPrice().intValue() + 10000001L)));
        
        System.err.println("NewLotteryUtil winNmuber:" + winNumber + "    " + this.spellbuyproduct.getSpellbuyProductId());
        
        List<Object[]> objList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_objList_" + this.spellbuyproduct.getSpellbuyProductId() + "_" + winNumber);
        if (objList == null)
        {
          objList = this.spellbuyrecordService.randomByBuyHistoryByspellbuyProductId(this.spellbuyproduct.getSpellbuyProductId(), String.valueOf(winNumber));
          AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_objList_" + this.spellbuyproduct.getSpellbuyProductId() + "_" + winNumber, 1800, objList);
        }
        Randomnumber randomnumber = (Randomnumber)((Object[])objList.get(0))[0];
        Spellbuyrecord spellbuyrecord = (Spellbuyrecord)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_spellbuyrecord_add_" + id);
        if (spellbuyrecord == null)
        {
          spellbuyrecord = (Spellbuyrecord)((Object[])objList.get(0))[1];
          spellbuyrecord.setSpRandomNo(String.valueOf(winNumber));
          spellbuyrecord.setSpWinningStatus("1");
          spellbuyrecord.setBuyStatus("1");
          this.spellbuyrecordService.add(spellbuyrecord);
          AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_spellbuyrecord_add_" + id, 1800, spellbuyrecord);
        }
        this.user = ((User)((Object[])objList.get(0))[2]);
        
        int productPeriod = this.spellbuyproduct.getProductPeriod().intValue();
        
        this.spellbuyproduct = ((Spellbuyproduct)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_spellbuyproduct_add_" + id));
        if (this.spellbuyproduct == null)
        {
          this.spellbuyproduct.setSpStatus(Integer.valueOf(1));
          this.spellbuyproductService.add(this.spellbuyproduct);
          AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_spellbuyproduct_add_" + id, 1800, this.spellbuyproduct);
        }
        this.product = ((Product)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_product_" + this.spellbuyproduct.getFkProductId()));
        if (this.product == null)
        {
          this.product = ((Product)this.productService.findById(String.valueOf(this.spellbuyproduct.getFkProductId())));
          AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_product_" + this.spellbuyproduct.getFkProductId(), 1800, this.product);
        }
        if (this.product.getStatus().intValue() == 1)
        {
          List<Spellbuyproduct> spellbuyproductOld = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_spellbuyproductOld_" + this.spellbuyproduct.getFkProductId());
          if (spellbuyproductOld == null)
          {
            spellbuyproductOld = this.spellbuyproductService.findSpellbuyproductByProductIdIsStatus(this.spellbuyproduct.getFkProductId());
            System.err.println("spellbuyproductOld:" + spellbuyproductOld);
            System.err.println("spellbuyproductOld-size:" + spellbuyproductOld.size());
            if (spellbuyproductOld.size() == 0)
            {
              Spellbuyproduct spellbuyproduct2 = new Spellbuyproduct();
              spellbuyproduct2.setFkProductId(this.spellbuyproduct.getFkProductId());
              spellbuyproduct2.setProductPeriod(Integer.valueOf(++productPeriod));
              spellbuyproduct2.setSpellbuyCount(Integer.valueOf(0));
              spellbuyproduct2.setSpellbuyType(Integer.valueOf(0));
              spellbuyproduct2.setSpellbuyEndDate(DateUtil.DateTimeToStr(new Date()));
              spellbuyproduct2.setSpellbuyPrice(this.product.getProductPrice());
              spellbuyproduct2.setSpSinglePrice(this.product.getSinglePrice());
              spellbuyproduct2.setSpellbuyStartDate(DateUtil.DateTimeToStr(new Date()));
              spellbuyproduct2.setSpStatus(Integer.valueOf(0));
              if (this.product.getAttribute71().equals("hot")) {
                spellbuyproduct2.setSpellbuyType(Integer.valueOf(8));
              } else {
                spellbuyproduct2.setSpellbuyType(Integer.valueOf(0));
              }
              this.spellbuyproductService.add(spellbuyproduct2);
            }
            AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_spellbuyproductOld_" + this.spellbuyproduct.getFkProductId(), 1800, spellbuyproductOld);
          }
        }
        List list = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_list_" + this.spellbuyproduct.getSpellbuyProductId());
        if (list == null)
        {
          list = this.latestlotteryService.getLatestlotteryBySpellbuyProductIdAndProductIdIsExist(this.spellbuyproduct.getSpellbuyProductId());
          if (list.size() == 0)
          {
            this.latestlottery = new Latestlottery();
            this.latestlottery.setProductId(this.spellbuyproduct.getFkProductId());
            this.latestlottery.setProductName(this.product.getProductName());
            this.latestlottery.setProductTitle(this.product.getProductTitle());
            this.latestlottery.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
            this.latestlottery.setProductImg(this.product.getHeadImage());
            this.latestlottery.setProductPeriod(this.spellbuyproduct.getProductPeriod());
            this.latestlottery.setAnnouncedTime(DateUtil.DateTimeToStrBySSS(new Date()));
            this.latestlottery.setAnnouncedType(this.spellbuyproduct.getSpellbuyType());
            this.latestlottery.setDateSum(DateSUM);
            this.latestlottery.setSscNumber(sscNumber);
            this.latestlottery.setSscPeriod(sscPeriod);
            this.latestlottery.setBuyTime(spellbuyrecord.getBuyDate());
            this.latestlottery.setSpellbuyRecordId(spellbuyrecord.getSpellbuyRecordId());
            this.latestlottery.setSpellbuyProductId(spellbuyrecord.getFkSpellbuyProductId());
            BigDecimal buyNumberCount = this.randomnumberService.RandomNumberByUserBuyCount(String.valueOf(this.user.getUserId()), this.spellbuyproduct.getSpellbuyProductId());
            this.latestlottery.setBuyNumberCount(Integer.valueOf(Integer.parseInt(String.valueOf(buyNumberCount))));
            this.latestlottery.setRandomNumber(winNumber);
            //this.latestlottery.setLocation(this.user.getIpLocation());
            latestlottery.setLocation(spellbuyrecord.getBuyLocal());
            this.latestlottery.setUserId(this.user.getUserId());
            this.latestlottery.setUserName(UserNameUtil.userName(this.user));
            this.latestlottery.setUserFace(this.user.getFaceImg());
            this.latestlottery.setStatus(Integer.valueOf(1));
            this.latestlottery.setShareStatus(Integer.valueOf(-1));
            this.latestlottery.setShareId(null);
            this.latestlotteryService.add(this.latestlottery);
          }
          AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_list_" + this.spellbuyproduct.getSpellbuyProductId(), 1800, list);
        }
        Struts2Utils.renderText("true", new String[0]);
        Lotteryproductutil lotteryproductutil = (Lotteryproductutil)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_lotteryproductutil_" + id);
        if (lotteryproductutil == null)
        {
          lotteryproductutil = (Lotteryproductutil)this.lotteryproductutilService.findById(id);
          if (lotteryproductutil != null) {
            this.lotteryproductutilService.delete(lotteryproductutil.getLotteryId());
          }
          AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_lotteryproductutil_" + id, 1800, lotteryproductutil);
        }
        flag = true;
      }
    }
    return flag;
  }
  
  public void lotteryUtilAjax()
  {
    String lotteryId = MD5Util.encode(this.id);
    if (AliyunOcsSampleHelp.getIMemcachedCache().get(lotteryId) == null)
    {
      AliyunOcsSampleHelp.getIMemcachedCache().set(lotteryId, 43200, "y");
      this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(this.id));
      if (this.spellbuyproduct.getSpStatus().intValue() == 2)
      {
        this.spellbuyrecord = ((Spellbuyrecord)this.spellbuyrecordService.getEndBuyDateByProduct(this.spellbuyproduct.getSpellbuyProductId()).get(0));
        String newDate = this.spellbuyrecord.getBuyDate();
        List<Spellbuyrecord> dataList = this.spellbuyrecordService.getSpellbuyRecordByLast100(null, newDate, 60);
        Long DateSUM = Long.valueOf(0L);
        Integer buyId = ((Spellbuyrecord)dataList.get(0)).getFkSpellbuyProductId();
        int i100 = 0;
        for (int k = 0; k < dataList.size(); k++) {
          if ((k <= 0) || 
          
            (!newDate.equals(((Spellbuyrecord)dataList.get(k)).getBuyDate())) || (((Spellbuyrecord)dataList.get(k)).getFkSpellbuyProductId() == buyId))
          {
            if (i100++ == 100) {
              break;
            }
            this.spellbuyrecord = ((Spellbuyrecord)dataList.get(k));
            
            this.calendar.setTime(DateUtil.SDateTimeToDate(this.spellbuyrecord.getBuyDate()));
            
            Integer h = Integer.valueOf(this.calendar.get(11));
            Integer m = Integer.valueOf(this.calendar.get(12));
            Integer s1 = Integer.valueOf(this.calendar.get(13));
            Integer ss1 = Integer.valueOf(this.calendar.get(14));
            String sh = "";
            String sm = "";
            String ss = "";
            String sss = "";
            if (h.intValue() < 10) {
              sh = "0" + h;
            } else {
              sh = h.toString();
            }
            if (m.intValue() < 10) {
              sm = "0" + m;
            } else {
              sm = m.toString();
            }
            if (s1.intValue() < 10) {
              ss = "0" + s1;
            } else {
              ss = s1.toString();
            }
            if (ss1.intValue() < 10) {
              sss = "00" + ss1;
            } else if (ss1.intValue() < 100) {
              sss = "0" + ss1;
            } else {
              sss = ss1.toString();
            }
            DateSUM = Long.valueOf(DateSUM.longValue() + Long.parseLong(sh + sm + ss + sss));
          }
        }
        System.err.println("NewLotteryUtil DateSUM:" + DateSUM + "    " + this.id);
        
        Integer winNumber = Integer.valueOf(Integer.parseInt(String.valueOf(DateSUM.longValue() % this.spellbuyproduct.getSpellbuyPrice().intValue() + 10000001L)));
        
        System.err.println("NewLotteryUtil winNmuber:" + winNumber + "    " + this.spellbuyproduct.getSpellbuyProductId());
        

        List<Object[]> objList = this.spellbuyrecordService.randomByBuyHistoryByspellbuyProductId(this.spellbuyproduct.getSpellbuyProductId(), String.valueOf(winNumber));
        Randomnumber randomnumber = (Randomnumber)((Object[])objList.get(0))[0];
        Spellbuyrecord spellbuyrecord = (Spellbuyrecord)((Object[])objList.get(0))[1];
        this.user = ((User)((Object[])objList.get(0))[2]);
        

        spellbuyrecord.setSpRandomNo(String.valueOf(winNumber));
        spellbuyrecord.setSpWinningStatus("1");
        spellbuyrecord.setBuyStatus("1");
        this.spellbuyrecordService.add(spellbuyrecord);
        
        int productPeriod = this.spellbuyproduct.getProductPeriod().intValue();
        
        this.spellbuyproduct.setSpStatus(Integer.valueOf(1));
        this.spellbuyproductService.add(this.spellbuyproduct);
        
        this.product = ((Product)this.productService.findById(String.valueOf(this.spellbuyproduct.getFkProductId())));
        if (this.product.getStatus().intValue() == 1)
        {
          List<Spellbuyproduct> spellbuyproductOld = this.spellbuyproductService.findSpellbuyproductByProductIdIsStatus(this.spellbuyproduct.getFkProductId());
          System.err.println("spellbuyproductOld:" + spellbuyproductOld);
          System.err.println("spellbuyproductOld-size:" + spellbuyproductOld.size());
          if (spellbuyproductOld.size() == 0)
          {
            Spellbuyproduct spellbuyproduct2 = new Spellbuyproduct();
            spellbuyproduct2.setFkProductId(this.spellbuyproduct.getFkProductId());
            spellbuyproduct2.setProductPeriod(Integer.valueOf(++productPeriod));
            spellbuyproduct2.setSpellbuyCount(Integer.valueOf(0));
            spellbuyproduct2.setSpellbuyType(Integer.valueOf(0));
            spellbuyproduct2.setSpellbuyEndDate(DateUtil.DateTimeToStr(new Date()));
            spellbuyproduct2.setSpellbuyPrice(this.product.getProductPrice());
            spellbuyproduct2.setSpSinglePrice(this.product.getSinglePrice());
            spellbuyproduct2.setSpellbuyStartDate(DateUtil.DateTimeToStr(new Date()));
            spellbuyproduct2.setSpStatus(Integer.valueOf(0));
            if (this.product.getAttribute71().equals("hot")) {
              spellbuyproduct2.setSpellbuyType(Integer.valueOf(8));
            } else {
              spellbuyproduct2.setSpellbuyType(Integer.valueOf(0));
            }
            this.spellbuyproductService.add(spellbuyproduct2);
          }
        }
        List list = this.latestlotteryService.getLatestlotteryBySpellbuyProductIdAndProductIdIsExist(this.spellbuyproduct.getSpellbuyProductId());
        if (list.size() == 0)
        {
          this.latestlottery = new Latestlottery();
          this.latestlottery.setProductId(this.spellbuyproduct.getFkProductId());
          this.latestlottery.setProductName(this.product.getProductName());
          this.latestlottery.setProductTitle(this.product.getProductTitle());
          this.latestlottery.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
          this.latestlottery.setProductImg(this.product.getHeadImage());
          this.latestlottery.setProductPeriod(this.spellbuyproduct.getProductPeriod());
          this.latestlottery.setAnnouncedTime(newDate);
          this.latestlottery.setAnnouncedType(this.spellbuyproduct.getSpellbuyType());
          this.latestlottery.setDateSum(DateSUM);
          this.latestlottery.setBuyTime(spellbuyrecord.getBuyDate());
          this.latestlottery.setSpellbuyRecordId(spellbuyrecord.getSpellbuyRecordId());
          this.latestlottery.setSpellbuyProductId(spellbuyrecord.getFkSpellbuyProductId());
          BigDecimal buyNumberCount = this.randomnumberService.RandomNumberByUserBuyCount(String.valueOf(this.user.getUserId()), this.spellbuyproduct.getSpellbuyProductId());
          this.latestlottery.setBuyNumberCount(Integer.valueOf(Integer.parseInt(String.valueOf(buyNumberCount))));
          this.latestlottery.setRandomNumber(winNumber);
          //this.latestlottery.setLocation(this.user.getIpLocation());
          latestlottery.setLocation(spellbuyrecord.getBuyLocal());
          this.latestlottery.setUserId(this.user.getUserId());
          this.latestlottery.setUserName(UserNameUtil.userName(this.user));
          this.latestlottery.setUserFace(this.user.getFaceImg());
          this.latestlottery.setStatus(Integer.valueOf(1));
          this.latestlottery.setShareStatus(Integer.valueOf(-1));
          this.latestlottery.setShareId(null);
          this.latestlotteryService.add(this.latestlottery);
        }
        Struts2Utils.renderText("true", new String[0]);
      }
    }
    else
    {
      List list = this.latestlotteryService.getLatestlotteryBySpellbuyProductIdAndProductIdIsExist(Integer.valueOf(Integer.parseInt(this.id)));
      if (list.size() > 0) {
        Struts2Utils.renderText("true", new String[0]);
      } else {
        Struts2Utils.renderText("false", new String[0]);
      }
    }
  }
  
  public String upcomingAnnounced()
  {
    nowDateByUpcomingAnnounced = Long.valueOf(System.currentTimeMillis());
    if (beginDateByUpcomingAnnounced == null)
    {
      Pagination datePage = this.spellbuyproductService.upcomingAnnounced(this.pageNo, this.pageSize);
      List<Object[]> dateList = (List<Object[]>)datePage.getList();
      upcomingAnnouncedList = new ArrayList();
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
        upcomingAnnouncedList.add(this.productJSON);
      }
      beginDateByUpcomingAnnounced = Long.valueOf(System.currentTimeMillis());
      Struts2Utils.renderJson(upcomingAnnouncedList, new String[0]);
    }
    else if (nowDateByUpcomingAnnounced.longValue() - beginDateByUpcomingAnnounced.longValue() < 5000L)
    {
      Struts2Utils.renderJson(upcomingAnnouncedList, new String[0]);
    }
    else
    {
      beginDateByUpcomingAnnounced = Long.valueOf(System.currentTimeMillis());
      Pagination datePage = this.spellbuyproductService.upcomingAnnounced(this.pageNo, this.pageSize);
      List<Object[]> dateList = (List<Object[]>)datePage.getList();
      upcomingAnnouncedList = new ArrayList();
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
        upcomingAnnouncedList.add(this.productJSON);
      }
      Struts2Utils.renderJson(upcomingAnnouncedList, new String[0]);
    }
    return null;
  }
  
  public String upcomingAnnouncedByTop()
  {
    nowDateByUpcomingAnnouncedByTop = Long.valueOf(System.currentTimeMillis());
    if (beginDateByUpcomingAnnouncedByTop == null)
    {
      Pagination datePage = this.spellbuyproductService.upcomingAnnouncedByTop(this.pageNo, this.pageSize);
      List<Object[]> dateList = (List<Object[]>)datePage.getList();
      upcomingAnnouncedByTopList = new ArrayList();
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
        upcomingAnnouncedByTopList.add(this.productJSON);
      }
      beginDateByUpcomingAnnouncedByTop = Long.valueOf(System.currentTimeMillis());
      Struts2Utils.renderJson(upcomingAnnouncedByTopList, new String[0]);
    }
    else if (nowDateByUpcomingAnnouncedByTop.longValue() - beginDateByUpcomingAnnouncedByTop.longValue() < 5000L)
    {
      Struts2Utils.renderJson(upcomingAnnouncedByTopList, new String[0]);
    }
    else
    {
      beginDateByUpcomingAnnouncedByTop = Long.valueOf(System.currentTimeMillis());
      Pagination datePage = this.spellbuyproductService.upcomingAnnouncedByTop(this.pageNo, this.pageSize);
      List<Object[]> dateList = (List<Object[]>)datePage.getList();
      upcomingAnnouncedByTopList = new ArrayList();
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
        upcomingAnnouncedByTopList.add(this.productJSON);
      }
      Struts2Utils.renderJson(upcomingAnnouncedByTopList, new String[0]);
    }
    return null;
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
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
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
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public String getPages()
  {
    return this.pages;
  }
  
  public void setPages(String pages)
  {
    this.pages = pages;
  }
  
  public String getPageString()
  {
    return this.pageString;
  }
  
  public void setPageString(String pageString)
  {
    this.pageString = pageString;
  }
  
  public ProductCart getProductCart()
  {
    return this.productCart;
  }
  
  public void setProductCart(ProductCart productCart)
  {
    this.productCart = productCart;
  }
  
  public long getTime()
  {
    return this.time;
  }
  
  public void setTime(long time)
  {
    this.time = time;
  }
}
