package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.exception.RuleViolationException;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.LotteryDetailJSON;
import com.egouos.pojo.ParticipateJSON;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductInfo;
import com.egouos.pojo.Productimage;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.User;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.ProductImageService;
import com.egouos.service.ShareService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("ProductsAction")
public class ProductsAction
  extends ActionSupport
{
  private static final long serialVersionUID = 1626790673064716640L;
  @Autowired
  @Qualifier("spellbuyrecordService")
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  @Qualifier("spellbuyproductService")
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private ShareService shareService;
  private ProductInfo productInfo;
  private Product product;
  private List<ParticipateJSON> ParticipateJSONList;
  private Spellbuyrecord spellbuyrecord;
  private List<Spellbuyrecord> spellbuyrecordList;
  private Spellbuyproduct spellbuyproduct;
  private Latestlottery latestlottery;
  private TreeMap<Integer, Integer> productPeriodList;
  private List<Productimage> productimageList;
  private LotteryDetailJSON lotteryDetailJSON;
  private List<LotteryDetailJSON> lotteryDetailJSONList;
  private User user;
  private String id;
  private String userId;
  private int pageNo;
  private int pageSize = 20;
  private int pageCount;
  private int resultCount;
  private String startDate;
  private Long DateSUM = Long.valueOf(0L);
  private String isLotteryJSON;
  private Long endDate;
  private Long nowDate;
  HttpServletRequest request = null;
  Calendar calendar = Calendar.getInstance();
  
  public String index()
    throws ServletException, IOException
  {
    /*if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      return null;
    }*/
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    for (int i = 0; i < cookies.length; i++)
    {
      Cookie cookie = cookies[i];
      if (cookie.getName().equals("userId")) {
        this.userId = cookie.getValue();
      }
    }
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    this.ParticipateJSONList = new ArrayList();
    List<Object[]> proList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("product_index_proList_" + this.id);
    if (proList == null)
    {
      proList = this.spellbuyproductService.findByProductId(Integer.parseInt(id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("product_index_proList_" + id, 2, proList);
    }
    this.productInfo = new ProductInfo();
    Object[] objs = (Object[])proList.get(0);
	if(objs[0] instanceof Product){
		product = (Product)objs[0];
		spellbuyproduct = (Spellbuyproduct)objs[1];
	}else{
		product = (Product)objs[1];
		spellbuyproduct = (Spellbuyproduct)objs[0];
	}
    if(product.getStatus()!=1){
    	throw new RuleViolationException("产品已经下架！");
    }
    this.productInfo.setProductPeriod(this.spellbuyproduct.getProductPeriod());
    this.productInfo.setStatus(this.spellbuyproduct.getSpStatus());
    this.productInfo.setHeadImage(this.product.getHeadImage());
    this.productInfo.setProductDetail(this.product.getProductDetail());
    this.productInfo.setProductName(this.product.getProductName());
    this.productInfo.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
    this.productInfo.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
    this.productInfo.setProductTitle(this.product.getProductTitle());
    this.productInfo.setSpellbuyCount(this.spellbuyproduct.getSpellbuyCount());
    this.productInfo.setProductId(this.product.getProductId());
    this.productInfo.setSpellbuyProductId(this.spellbuyproduct.getSpellbuyProductId());

    List<Object[]> objectList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("product_index_objectList_" + this.spellbuyproduct.getFkProductId());
    if (objectList == null)
    {
      objectList = this.spellbuyproductService.productPeriodList(this.spellbuyproduct.getFkProductId());
      AliyunOcsSampleHelp.getIMemcachedCache().set("product_index_objectList_" + this.spellbuyproduct.getFkProductId(), 2, objectList);
    }
    this.productPeriodList = new TreeMap(new Comparator()
    {
      public int compare(Object o1, Object o2)
      {
        return o2.hashCode() - o1.hashCode();
      }
    });
    for (Object[] objects : objectList)
    {
    	if(objects[0] instanceof Spellbuyproduct){
    		spellbuyproduct = (Spellbuyproduct)objects[0];
    	}else{
    		spellbuyproduct = (Spellbuyproduct)objects[1];
    	}
      //this.spellbuyproduct = ((Spellbuyproduct)objects[1]);
      this.productPeriodList.put(this.spellbuyproduct.getProductPeriod(), this.spellbuyproduct.getSpellbuyProductId());
    }
    if (this.productPeriodList.size() > 1) {
    	List list = this.latestlotteryService.getByProductHistoryWin(this.productInfo.getProductId());
    	if(list.size()>0){
    		latestlottery = ((Latestlottery)list.get(0));
    	}
      }
    this.productimageList = ((List)AliyunOcsSampleHelp.getIMemcachedCache().get("product_index_productimageList_" + this.product.getProductId() + "_show"));
    if (this.productimageList == null)
    {
      this.productimageList = this.productImageService.findByProductId(String.valueOf(this.product.getProductId()), "show");
      AliyunOcsSampleHelp.getIMemcachedCache().set("product_index_productimageList_" + this.product.getProductId() + "_show", 3600, this.productimageList);
    }
    Pagination pagination = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("product_index_pagination_" + this.id + "_" + this.pageNo);
    if (pagination == null)
    {
      pagination = this.spellbuyrecordService.LatestParticipate(this.id, this.pageNo, 6);
      AliyunOcsSampleHelp.getIMemcachedCache().set("product_index_pagination_" + this.id + "_" + this.pageNo, 2, pagination);
    }
    List<?> list = pagination.getList();
    for (int i = 0; i < ((List)list).size(); i++)
    {
      ParticipateJSON participateJSON = new ParticipateJSON();
      Object[] objects = (Object[])list.get(i);
    	if(objects[0] instanceof Spellbuyrecord){
    		spellbuyrecord = (Spellbuyrecord)objects[0];
    		user = (User)objects[1];
    	}else{
    		spellbuyrecord = (Spellbuyrecord)objects[1];
    		user = (User)objects[0];
    	}
      //this.spellbuyrecord = ((Spellbuyrecord)((Object[])list.get(i))[0]);
      //this.user = ((User)((Object[])list.get(i))[1]);
      String userName = UserNameUtil.userName(this.user);
      participateJSON.setBuyCount(this.spellbuyrecord.getBuyPrice().toString());
      participateJSON.setBuyDate(DateUtil.getTime(DateUtil.SDateTimeToDate(this.spellbuyrecord.getBuyDate())));
      participateJSON.setBuyId(this.spellbuyrecord.getSpellbuyRecordId().toString());
      /*participateJSON.setIp_address(this.user.getIpAddress());
      participateJSON.setIp_location(this.user.getIpLocation());*/
      participateJSON.setIp_address(spellbuyrecord.getBuyIp());
      participateJSON.setIp_location(spellbuyrecord.getBuyLocal());
      participateJSON.setUserName(userName);
      participateJSON.setUserId(String.valueOf(this.user.getUserId()));
      participateJSON.setUserFace(this.user.getFaceImg());
      this.ParticipateJSONList.add(participateJSON);
    }
    String pCount = (String)AliyunOcsSampleHelp.getIMemcachedCache().get("product_index_pageCount_" + this.productInfo.getProductId());
    if (pCount == null)
    {
      this.pageCount = this.shareService.loadShareInfoByNewByCount(this.productInfo.getProductId()+"").intValue();
      AliyunOcsSampleHelp.getIMemcachedCache().set("product_index_pageCount_" + this.productInfo.getProductId(), 2, String.valueOf(this.pageCount));
    }
    else
    {
      this.pageCount = Integer.parseInt(pCount);
    }
    if (this.productInfo.getStatus().intValue() == 2) {
      return "lottery";
    }
    if (this.productInfo.getStatus().intValue() == 1)
    {
      HttpServletResponse response = Struts2Utils.getResponse();
      response.sendRedirect("/lotteryDetail/" + this.productInfo.getSpellbuyProductId() + ".html");
      
      return null;
    }
    return "index";
  }
  
  public String buyrecords()
  {
    return "buyrecords";
  }
  
  public String goodsdesc()
  {
    List<Object[]> proList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("product_index_proList_" + this.id);
    if (proList == null)
    {
      proList = this.spellbuyproductService.findByProductId(Integer.parseInt(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("product_index_proList_" + this.id, 2, proList);
    }
    this.productInfo = new ProductInfo();
    Object[] objects = (Object[])proList.get(0);
  	if(objects[0] instanceof Product){
  		product = (Product)objects[0];
  		spellbuyproduct = (Spellbuyproduct)objects[1];
  	}else{
  		product = (Product)objects[1];
  		spellbuyproduct = (Spellbuyproduct)objects[0];
  	}
    //this.product = ((Product)((Object[])proList.get(0))[0]);
    //this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
    this.productInfo.setProductPeriod(this.spellbuyproduct.getProductPeriod());
    this.productInfo.setStatus(this.spellbuyproduct.getSpStatus());
    this.productInfo.setHeadImage(this.product.getHeadImage());
    this.productInfo.setProductDetail(this.product.getProductDetail());
    this.productInfo.setProductName(this.product.getProductName());
    this.productInfo.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
    this.productInfo.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
    this.productInfo.setProductTitle(this.product.getProductTitle());
    this.productInfo.setSpellbuyCount(this.spellbuyproduct.getSpellbuyCount());
    this.productInfo.setProductId(this.product.getProductId());
    this.productInfo.setSpellbuyProductId(this.spellbuyproduct.getSpellbuyProductId());
    return "goodsdesc";
  }
  
  public String goodspost()
  {
    System.err.println(this.id);
    return "goodspost";
  }
  
  public String getProductNewList()
  {
    this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(this.id));
    List<Object[]> objectList = this.spellbuyproductService.productPeriodList(this.spellbuyproduct.getFkProductId());
    this.productPeriodList = new TreeMap();
    for (Object[] objects : objectList)
    {
      this.spellbuyproduct = ((Spellbuyproduct)objects[1]);
      this.productPeriodList.put(this.spellbuyproduct.getProductPeriod(), this.spellbuyproduct.getSpellbuyProductId());
    }
    Struts2Utils.renderJson(this.productPeriodList, new String[0]);
    return null;
  }
  
  public String ajaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    this.ParticipateJSONList = new ArrayList();
    Pagination pagination = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("product_getProductNewList_pagination_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (pagination == null)
    {
      pagination = this.spellbuyrecordService.LatestParticipate(this.id, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("product_getProductNewList_pagination_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 5, pagination);
    }
    List<Object[]> list = (List<Object[]>)pagination.getList();
    for (int i = 0; i < list.size(); i++)
    {
      ParticipateJSON participateJSON = new ParticipateJSON();
      Object[] objects = (Object[])list.get(i);
  	if(objects[0] instanceof Spellbuyrecord){
  		spellbuyrecord = (Spellbuyrecord)objects[0];
  		user = (User)objects[1];
  	}else{
  		spellbuyrecord = (Spellbuyrecord)objects[1];
  		user = (User)objects[0];
  	}
      //this.spellbuyrecord = ((Spellbuyrecord)((Object[])list.get(i))[0]);
      //this.user = ((User)((Object[])list.get(i))[1]);
      String userName = UserNameUtil.userName(this.user);
      participateJSON.setBuyCount(this.spellbuyrecord.getBuyPrice().toString());
      participateJSON.setBuyDate(this.spellbuyrecord.getBuyDate());
      participateJSON.setBuyId(this.spellbuyrecord.getSpellbuyRecordId().toString());
      /*participateJSON.setIp_address(this.user.getIpAddress());
      participateJSON.setIp_location(this.user.getIpLocation());*/
      participateJSON.setIp_address(spellbuyrecord.getBuyIp());
      participateJSON.setIp_location(spellbuyrecord.getBuyLocal());
      participateJSON.setUserName(userName);
      participateJSON.setUserId(String.valueOf(this.user.getUserId()));
      participateJSON.setUserFace(this.user.getFaceImg());
      this.ParticipateJSONList.add(participateJSON);
    }
    Struts2Utils.renderJson(this.ParticipateJSONList, new String[0]);
    return null;
  }
  
  public void getUserByHistory()
  {
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    for (int i = 0; i < cookies.length; i++)
    {
      Cookie cookie = cookies[i];
      if (cookie.getName().equals("userId"))
      {
        this.userId = cookie.getValue();
        if ((this.userId != null) && (!this.userId.equals("")))
        {
          this.spellbuyrecordList = this.spellbuyrecordService.getUserByHistory(this.userId, this.id);
          Struts2Utils.renderJson(this.spellbuyrecordList, new String[0]);
        }
      }
    }
  }
  
  public void isLottery()
  {
    String key = MD5Util.encode(this.id + "status");
    if (AliyunOcsSampleHelp.getIMemcachedCache().get(key) == null)
    {
      this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(this.id));
      this.endDate = Long.valueOf(DateUtil.SDateTimeToDate(this.spellbuyproduct.getSpellbuyEndDate()).getTime());
      this.nowDate = Long.valueOf(System.currentTimeMillis());
      this.isLotteryJSON = ("{\"spStatus\":\"" + this.spellbuyproduct.getSpStatus() + "\",\"date\":\"" + (this.endDate.longValue() - this.nowDate.longValue()) / 1000L + "\"}");
      AliyunOcsSampleHelp.getIMemcachedCache().set(key, 60, String.valueOf(this.endDate));
      Struts2Utils.renderJson(this.isLotteryJSON, new String[0]);
    }
    else
    {
      this.endDate = Long.valueOf(Long.parseLong((String)AliyunOcsSampleHelp.getIMemcachedCache().get(key)));
      this.nowDate = Long.valueOf(System.currentTimeMillis());
      this.isLotteryJSON = ("{\"spStatus\":\"2\",\"date\":\"" + (this.endDate.longValue() - this.nowDate.longValue()) / 1000L + "\"}");
      Struts2Utils.renderJson(this.isLotteryJSON, new String[0]);
    }
  }
  
  public static void main(String[] args)
  {
    int lotteryId = 123155425;
    String a = (String)AliyunOcsSampleHelp.getIMemcachedCache().get("test_" + lotteryId);
    System.err.println(a);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public ProductInfo getProductInfo()
  {
    return this.productInfo;
  }
  
  public void setProductInfo(ProductInfo productInfo)
  {
    this.productInfo = productInfo;
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
  
  public List<ParticipateJSON> getParticipateJSONList()
  {
    return this.ParticipateJSONList;
  }
  
  public void setParticipateJSONList(List<ParticipateJSON> participateJSONList)
  {
    this.ParticipateJSONList = participateJSONList;
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
  
  public Product getProduct()
  {
    return this.product;
  }
  
  public void setProduct(Product product)
  {
    this.product = product;
  }
  
  public TreeMap<Integer, Integer> getProductPeriodList()
  {
    return this.productPeriodList;
  }
  
  public void setProductPeriodList(TreeMap<Integer, Integer> productPeriodList)
  {
    this.productPeriodList = productPeriodList;
  }
  
  public Latestlottery getLatestlottery()
  {
    return this.latestlottery;
  }
  
  public void setLatestlottery(Latestlottery latestlottery)
  {
    this.latestlottery = latestlottery;
  }
  
  public List<Productimage> getProductimageList()
  {
    return this.productimageList;
  }
  
  public void setProductimageList(List<Productimage> productimageList)
  {
    this.productimageList = productimageList;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public List<Spellbuyrecord> getSpellbuyrecordList()
  {
    return this.spellbuyrecordList;
  }
  
  public void setSpellbuyrecordList(List<Spellbuyrecord> spellbuyrecordList)
  {
    this.spellbuyrecordList = spellbuyrecordList;
  }
  
  public LotteryDetailJSON getLotteryDetailJSON()
  {
    return this.lotteryDetailJSON;
  }
  
  public void setLotteryDetailJSON(LotteryDetailJSON lotteryDetailJSON)
  {
    this.lotteryDetailJSON = lotteryDetailJSON;
  }
  
  public List<LotteryDetailJSON> getLotteryDetailJSONList()
  {
    return this.lotteryDetailJSONList;
  }
  
  public void setLotteryDetailJSONList(List<LotteryDetailJSON> lotteryDetailJSONList)
  {
    this.lotteryDetailJSONList = lotteryDetailJSONList;
  }
  
  public String getStartDate()
  {
    return this.startDate;
  }
  
  public void setStartDate(String startDate)
  {
    this.startDate = startDate;
  }
  
  public Long getDateSUM()
  {
    return this.DateSUM;
  }
  
  public void setDateSUM(Long dateSUM)
  {
    this.DateSUM = dateSUM;
  }
}
