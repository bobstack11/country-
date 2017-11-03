package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.DetailBybuyerJSON;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.LotteryDetailJSON;
import com.egouos.pojo.ParticipateJSON;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Productimage;
import com.egouos.pojo.RandomNumberJSON;
import com.egouos.pojo.Randomnumber;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.User;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.ProductImageService;
import com.egouos.service.RandomnumberService;
import com.egouos.service.ShareService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.DateUtil;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("LotteryDetailAction")
public class LotteryDetailAction
  extends ActionSupport
{
  private static final long serialVersionUID = -8369327417332420791L;
  @Autowired
  @Qualifier("latestlotteryService")
  private LatestlotteryService latestlotteryService;
  @Autowired
  private RandomnumberService randomnumberService;
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private ShareService shareService;
  private Latestlottery latestlottery;
  private List<Latestlottery> latestlotteryList;
  private List<RandomNumberJSON> randomNumberJSONList;
  private DetailBybuyerJSON detailBybuyerJSON;
  private List<DetailBybuyerJSON> detailBybuyerJSONList;
  private List<ParticipateJSON> ParticipateJSONList;
  private RandomNumberJSON randomNumberJSON;
  private ProductJSON productJSON;
  private Randomnumber randomnumber;
  private List<Randomnumber> randomnumberList;
  private Spellbuyrecord spellbuyrecord;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private User user;
  private TreeMap<Integer, Integer> productPeriodList;
  private LotteryDetailJSON lotteryDetailJSON;
  private List<LotteryDetailJSON> lotteryDetailJSONList;
  private List<Productimage> productimageList;
  private String id;
  private String spellbuyrecordId;
  private int pageNo;
  private int pageSize = 20;
  private int pageCount;
  private int resultCount;
  private Integer buyerCount;
  private int buyResultCount;
  private int newProductId;
  private int newProductPeriod;
  private String winNumber = "";
  private String startDate;
  private String endDate;
  private Long DateSUM = Long.valueOf(0L);
  Calendar calendar = Calendar.getInstance();
  
  public String index()
  {
    this.randomNumberJSONList = new ArrayList();
    this.latestlottery = ((Latestlottery)AliyunOcsSampleHelp.getIMemcachedCache().get("latestlottery_" + this.id));
    if (this.latestlottery == null)
    {
      this.latestlottery = ((Latestlottery)this.latestlotteryService.getLotteryDetail(Integer.valueOf(Integer.parseInt(this.id))).get(0));
      AliyunOcsSampleHelp.getIMemcachedCache().set("latestlottery_" + this.id, 0, this.latestlottery);
    }
    List<Object[]> objectList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("objectList_" + this.latestlottery.getProductId());
    if (objectList == null)
    {
      objectList = this.spellbuyproductService.productPeriodList(this.latestlottery.getProductId());
      AliyunOcsSampleHelp.getIMemcachedCache().set("objectList_" + this.latestlottery.getProductId(), 5, objectList);
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
    	for(Object obj:objects){
      		if(obj instanceof Spellbuyproduct){
      			spellbuyproduct = (Spellbuyproduct)obj;
      		}
      	  }
      //this.spellbuyproduct = ((Spellbuyproduct)objects[1]);
      productPeriodList.put(spellbuyproduct.getProductPeriod(), spellbuyproduct.getSpellbuyProductId());
    }
    this.productimageList = ((List)AliyunOcsSampleHelp.getIMemcachedCache().get("productimageList_" + this.latestlottery.getProductId()));
    if (this.productimageList == null)
    {
      this.productimageList = this.productImageService.findByProductId(String.valueOf(this.latestlottery.getProductId()), "show");
      AliyunOcsSampleHelp.getIMemcachedCache().set("productimageList_" + this.latestlottery.getProductId(), 43200, this.productimageList);
    }
    try
    {
    	Object[] objs = (Object[])objectList.get(0);
    	  for(Object obj:objs){
    		if(obj instanceof Product){
    			product = (Product)obj;
    		}
    		if(obj instanceof Spellbuyproduct){
    			spellbuyproduct = (Spellbuyproduct)obj;
    		}
    	  }
      //this.product = ((Product)((Object[])objectList.get(0))[0]);
      //this.spellbuyproduct = ((Spellbuyproduct)((Object[])objectList.get(0))[1]);
      if (this.product.getStatus().intValue() == 1)
      {
        this.productJSON = new ProductJSON();
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
      }
    }
    catch (Exception localException) {}
    String pCount = (String)AliyunOcsSampleHelp.getIMemcachedCache().get("product_index_pageCount_" + this.product.getProductId());
    if (pCount == null)
    {
      this.pageCount = this.shareService.loadShareInfoByNewByCount(this.product.getProductId()+"").intValue();
      AliyunOcsSampleHelp.getIMemcachedCache().set("product_index_pageCount_" + this.product.getProductId(), 2, String.valueOf(this.pageCount));
    }
    else
    {
      this.pageCount = Integer.parseInt(pCount);
    }
    return "index";
  }
  
  public String calResult()
  {
    this.latestlottery = ((Latestlottery)AliyunOcsSampleHelp.getIMemcachedCache().get("latestlottery_" + this.id));
    if (this.latestlottery == null)
    {
      this.latestlottery = ((Latestlottery)this.latestlotteryService.getLotteryDetail(Integer.valueOf(Integer.parseInt(this.id))).get(0));
      AliyunOcsSampleHelp.getIMemcachedCache().set("latestlottery_" + this.id, 0, this.latestlottery);
    }
    return "calResult";
  }
  
  public void getCalResult()
  {
    this.latestlottery = ((Latestlottery)AliyunOcsSampleHelp.getIMemcachedCache().get("latestlottery_" + this.id));
    if (this.latestlottery == null)
    {
      this.latestlottery = ((Latestlottery)this.latestlotteryService.getLotteryDetail(Integer.valueOf(Integer.parseInt(this.id))).get(0));
      AliyunOcsSampleHelp.getIMemcachedCache().set("latestlottery_" + this.id, 0, this.latestlottery);
    }
    this.spellbuyrecord = ((Spellbuyrecord)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryUtil_spellbuyrecord_" + this.id));
    if (this.spellbuyrecord == null)
    {
      this.spellbuyrecord = ((Spellbuyrecord)this.spellbuyrecordService.getEndBuyDateByProduct(this.latestlottery.getSpellbuyProductId()).get(0));
      AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryUtil_spellbuyrecord_" + this.id, 1800, this.spellbuyrecord);
    }
    this.startDate = this.spellbuyrecord.getBuyDate();

    Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("page_" + DateUtil.SDateTimeToDateBySSS(this.startDate).getTime());
    if (page == null)
    {
      page = this.spellbuyrecordService.getlotteryDetail(null, this.startDate, 0, 105);
      AliyunOcsSampleHelp.getIMemcachedCache().set("page_" + DateUtil.SDateTimeToDateBySSS(this.startDate).getTime(), 0, page);
    }
    List<Object[]> dataList = (List<Object[]>)page.getList();
    this.lotteryDetailJSONList = new ArrayList();
    Spellbuyrecord sbr=null;
    Object[] objs = (Object[])dataList.get(0);
	  for(Object obj:objs){
		if(obj instanceof Spellbuyrecord){
			sbr = (Spellbuyrecord)obj;
		}
	  }
    int buyId = sbr.getFkSpellbuyProductId().intValue();
    int i100 = 0;
    for (int j = 0; j < dataList.size(); j++) {
    	 objs = (Object[])dataList.get(j);
         for(Object obj:objs){
     		if(obj instanceof Spellbuyrecord){
     			sbr = (Spellbuyrecord)obj;
     		}
     	}
      if ((j <= 0) || (!this.startDate.equals(sbr.getBuyDate())) || (sbr.getFkSpellbuyProductId().intValue() == buyId))
      {
        if (i100++ == 100) {
          break;
        }
        this.lotteryDetailJSON = new LotteryDetailJSON();
        objs = (Object[])dataList.get(j);
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
        this.lotteryDetailJSON.setBuyCount(this.spellbuyrecord.getBuyPrice());
        this.lotteryDetailJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.lotteryDetailJSON.setProductName(this.product.getProductName());
        this.lotteryDetailJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.lotteryDetailJSON.setProductTitle(this.product.getProductTitle());
        this.lotteryDetailJSON.setBuyDate(this.spellbuyrecord.getBuyDate().split(" ")[0]);
        this.lotteryDetailJSON.setBuyTime(this.spellbuyrecord.getBuyDate().split(" ")[1]);
        
        this.calendar.setTime(DateUtil.SDateTimeToDateBySSS(this.spellbuyrecord.getBuyDate()));

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
        this.lotteryDetailJSON.setDateSum(sh + sm + ss + sss);
        this.DateSUM = this.latestlottery.getDateSum();
        
        String userName = UserNameUtil.userName(this.user);
        if (userName.length() > 6) {
          this.lotteryDetailJSON.setUserName(userName.substring(0, 6));
        } else {
          this.lotteryDetailJSON.setUserName(userName);
        }
        this.lotteryDetailJSON.setUserId(this.user.getUserId());
        this.lotteryDetailJSONList.add(this.lotteryDetailJSON);
      }
    }
    Collections.sort(lotteryDetailJSONList, new Comparator<LotteryDetailJSON>()
    {
      public int compare(LotteryDetailJSON arg0, LotteryDetailJSON arg1)
      {
        return arg1.getBuyDate().compareTo(arg0.getBuyDate());
      }
    });
    Struts2Utils.renderJson(this.lotteryDetailJSONList, new String[0]);
  }
  
  public static void main(String[] args)
    throws ParseException
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    String date = "2013-06-27 12:02:32.455";
    
    System.err.println(sdf.format(new Date()));
  }
  
  public void getNewProductResult()
  {
    this.productJSON = new ProductJSON();
    List<Object[]> objectList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryDetail_getNewProductResult_objectList_" + this.id);
    if (objectList == null)
    {
      objectList = this.spellbuyproductService.productPeriodList(Integer.valueOf(Integer.parseInt(this.id)));
      AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryDetail_getNewProductResult_objectList_" + this.id, 5, objectList);
    }
    Object[] objs = (Object[])objectList.get(0);
	if(objs[0] instanceof Product){
		product = (Product)objs[0];
		spellbuyproduct = (Spellbuyproduct)objs[1];
	}else{
		product = (Product)objs[1];
		spellbuyproduct = (Spellbuyproduct)objs[0];
	}
    this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
    this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
    this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
    this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
    Struts2Utils.renderJson(this.productJSON, new String[0]);
  }
  
  public String getLotteryDetailBybuyerListAjaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryDetail_getLotteryDetailBybuyerListAjaxPage_datePage_" + this.id + "_" + this.pageNo);
    if (datePage == null)
    {
      datePage = this.latestlotteryService.getLotteryDetailBybuyerList(Integer.valueOf(Integer.parseInt(this.id)), this.pageNo, 10);
      AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryDetail_getLotteryDetailBybuyerListAjaxPage_datePage_" + this.id + "_" + this.pageNo, 0, datePage);
    }
    List<Object[]> dataList = (List<Object[]>)datePage.getList();
    this.detailBybuyerJSONList = new ArrayList();
    for (int j = 0; j < dataList.size(); j++)
    {
    	Object[] objs = (Object[])dataList.get(j);
    	  for(Object obj:objs){
    		if(obj instanceof Randomnumber){
    			randomnumber = (Randomnumber)obj;
    		}
    		if(obj instanceof User){
    			user = (User)obj;
    		}
    	  }
      this.detailBybuyerJSON = new DetailBybuyerJSON();
      //this.randomnumber = ((Randomnumber)((Object[])dataList.get(j))[0]);
      //this.user = ((User)((Object[])dataList.get(j))[1]);
      String[] randoms = this.randomnumber.getRandomNumber().split(",");
      String numbers = "";
      for (String string : randoms) {
        numbers = numbers + "<b>" + string + "</b>";
      }
      this.detailBybuyerJSON.setBuyCount(randoms.length+"");
      this.detailBybuyerJSON.setBuyTime(this.randomnumber.getBuyDate());
      this.detailBybuyerJSON.setFaceImg(this.user.getFaceImg());
      this.detailBybuyerJSON.setRandomNumber(numbers);
      this.detailBybuyerJSON.setUserId(this.user.getUserId()+"");
      String userName = UserNameUtil.userName(this.user);
      this.detailBybuyerJSON.setUserName(userName);
      this.detailBybuyerJSONList.add(this.detailBybuyerJSON);
    }
    Struts2Utils.renderJson(this.detailBybuyerJSONList, new String[0]);
    return null;
  }
  
  public String buyListAjaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    this.ParticipateJSONList = new ArrayList();
    Pagination pagination = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryDetail_buyListAjaxPage_pagination_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (pagination == null)
    {
      pagination = this.spellbuyrecordService.LatestParticipate(this.id, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryDetail_buyListAjaxPage_pagination_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 0, pagination);
    }
    List<Object[]> list = (List<Object[]>)pagination.getList();
    for (int i = 0; i < list.size(); i++)
    {
    	Object[] objs = (Object[])list.get(i);
  	  for(Object obj:objs){
  		if(obj instanceof Spellbuyrecord){
  			spellbuyrecord = (Spellbuyrecord)obj;
  		}
  		if(obj instanceof User){
  			user = (User)obj;
  		}
  	  }
      ParticipateJSON participateJSON = new ParticipateJSON();
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
      participateJSON.setIp_address(this.user.getIpAddress());
      participateJSON.setIp_location(this.user.getIpLocation());
      participateJSON.setUserName(userName);
      participateJSON.setUserId(String.valueOf(this.user.getUserId()));
      participateJSON.setUserFace(this.user.getFaceImg());
      this.ParticipateJSONList.add(participateJSON);
    }
    Struts2Utils.renderJson(this.ParticipateJSONList, new String[0]);
    return null;
  }
  
  public void getUserBuyCodeByBuyid()
  {
    try
    {
      this.randomnumber = ((Randomnumber)AliyunOcsSampleHelp.getIMemcachedCache().get("lotteryDetail_getUserBuyCodeByBuyid_randomnumber_" + this.id + "_" + this.spellbuyrecordId));
      if (this.randomnumber == null)
      {
        this.randomnumber = this.randomnumberService.getUserBuyCodeByBuyid(this.id, this.spellbuyrecordId);
        AliyunOcsSampleHelp.getIMemcachedCache().set("lotteryDetail_getUserBuyCodeByBuyid_randomnumber_" + this.id + "_" + this.spellbuyrecordId, 0, this.randomnumber);
      }
      if (this.randomnumber != null)
      {
        List<String> numberList = new ArrayList();
        String[] randoms = this.randomnumber.getRandomNumber().split(",");
        for (String string : randoms) {
          numberList.add(string);
        }
        Struts2Utils.renderJson(numberList, new String[0]);
      }
    }
    catch (Exception e)
    {
      Struts2Utils.renderText("false", new String[0]);
      e.printStackTrace();
    }
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
  
  public List<RandomNumberJSON> getRandomNumberJSONList()
  {
    return this.randomNumberJSONList;
  }
  
  public void setRandomNumberJSONList(List<RandomNumberJSON> randomNumberJSONList)
  {
    this.randomNumberJSONList = randomNumberJSONList;
  }
  
  public Randomnumber getRandomnumber()
  {
    return this.randomnumber;
  }
  
  public void setRandomnumber(Randomnumber randomnumber)
  {
    this.randomnumber = randomnumber;
  }
  
  public Spellbuyrecord getSpellbuyrecord()
  {
    return this.spellbuyrecord;
  }
  
  public void setSpellbuyrecord(Spellbuyrecord spellbuyrecord)
  {
    this.spellbuyrecord = spellbuyrecord;
  }
  
  public RandomNumberJSON getRandomNumberJSON()
  {
    return this.randomNumberJSON;
  }
  
  public void setRandomNumberJSON(RandomNumberJSON randomNumberJSON)
  {
    this.randomNumberJSON = randomNumberJSON;
  }
  
  public List<Randomnumber> getRandomnumberList()
  {
    return this.randomnumberList;
  }
  
  public void setRandomnumberList(List<Randomnumber> randomnumberList)
  {
    this.randomnumberList = randomnumberList;
  }
  
  public Integer getBuyerCount()
  {
    return this.buyerCount;
  }
  
  public void setBuyerCount(Integer buyerCount)
  {
    this.buyerCount = buyerCount;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public DetailBybuyerJSON getDetailBybuyerJSON()
  {
    return this.detailBybuyerJSON;
  }
  
  public void setDetailBybuyerJSON(DetailBybuyerJSON detailBybuyerJSON)
  {
    this.detailBybuyerJSON = detailBybuyerJSON;
  }
  
  public List<DetailBybuyerJSON> getDetailBybuyerJSONList()
  {
    return this.detailBybuyerJSONList;
  }
  
  public void setDetailBybuyerJSONList(List<DetailBybuyerJSON> detailBybuyerJSONList)
  {
    this.detailBybuyerJSONList = detailBybuyerJSONList;
  }
  
  public int getBuyResultCount()
  {
    return this.buyResultCount;
  }
  
  public void setBuyResultCount(int buyResultCount)
  {
    this.buyResultCount = buyResultCount;
  }
  
  public List<ParticipateJSON> getParticipateJSONList()
  {
    return this.ParticipateJSONList;
  }
  
  public void setParticipateJSONList(List<ParticipateJSON> participateJSONList)
  {
    this.ParticipateJSONList = participateJSONList;
  }
  
  public int getNewProductId()
  {
    return this.newProductId;
  }
  
  public void setNewProductId(int newProductId)
  {
    this.newProductId = newProductId;
  }
  
  public int getNewProductPeriod()
  {
    return this.newProductPeriod;
  }
  
  public void setNewProductPeriod(int newProductPeriod)
  {
    this.newProductPeriod = newProductPeriod;
  }
  
  public Spellbuyproduct getSpellbuyproduct()
  {
    return this.spellbuyproduct;
  }
  
  public void setSpellbuyproduct(Spellbuyproduct spellbuyproduct)
  {
    this.spellbuyproduct = spellbuyproduct;
  }
  
  public Product getProduct()
  {
    return this.product;
  }
  
  public void setProduct(Product product)
  {
    this.product = product;
  }
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
  }
  
  public TreeMap<Integer, Integer> getProductPeriodList()
  {
    return this.productPeriodList;
  }
  
  public void setProductPeriodList(TreeMap<Integer, Integer> productPeriodList)
  {
    this.productPeriodList = productPeriodList;
  }
  
  public String getWinNumber()
  {
    return this.winNumber;
  }
  
  public void setWinNumber(String winNumber)
  {
    this.winNumber = winNumber;
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
  
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(String endDate)
  {
    this.endDate = endDate;
  }
  
  public Long getDateSUM()
  {
    return this.DateSUM;
  }
  
  public void setDateSUM(Long dateSUM)
  {
    this.DateSUM = dateSUM;
  }
  
  public List<Productimage> getProductimageList()
  {
    return this.productimageList;
  }
  
  public void setProductimageList(List<Productimage> productimageList)
  {
    this.productimageList = productimageList;
  }
  
  public String getSpellbuyrecordId()
  {
    return this.spellbuyrecordId;
  }
  
  public void setSpellbuyrecordId(String spellbuyrecordId)
  {
    this.spellbuyrecordId = spellbuyrecordId;
  }
}
