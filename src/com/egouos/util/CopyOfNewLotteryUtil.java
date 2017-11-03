package com.egouos.util;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.Randomnumber;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.User;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.ProductService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext*.xml"})
@Repository
@Service("CopyOfNewLotteryUtil")
public class CopyOfNewLotteryUtil
{
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ProductService productService;
  private Product product;
  private User user;
  private Spellbuyproduct spellbuyproduct;
  private Latestlottery latestlottery;
  private Spellbuyrecord spellbuyrecord;
  private ProductCart productCart;
  Calendar calendar = Calendar.getInstance();
  
  @Test
  public void go()
    throws InterruptedException
  {
    this.productCart = new ProductCart();
    List<Object[]> proList = this.spellbuyproductService.findByProductId(149);
    this.product = ((Product)((Object[])proList.get(0))[0]);
    this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
    this.productCart.setHeadImage(this.product.getHeadImage());
    this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
    this.productCart.setProductName(this.product.getProductName());
    this.productCart.setProductPrice(this.product.getProductPrice());
    this.productCart.setProductTitle(this.product.getProductTitle());
    this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
    this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
    
    lottery(this.productCart);
  }
  
  public void lottery(ProductCart productCart)
    throws InterruptedException
  {
    Thread.sleep(10000L);
    


    this.spellbuyrecord = ((Spellbuyrecord)this.spellbuyrecordService.getEndBuyDateByProduct(productCart.getProductId()).get(0));
    String startDate = this.spellbuyrecord.getBuyDate();
    System.err.println("NewLotteryUtil startDate:" + startDate + "   " + productCart.getProductId());
    


    Pagination page = this.spellbuyrecordService.getlotteryDetail(null, startDate, 0, 100);
    List<?> dataList = page.getList();
    Long DateSUM = Long.valueOf(0L);
    for (int k = 0; k < dataList.size(); k++)
    {
      this.product = ((Product)((Object[])dataList.get(k))[0]);
      this.spellbuyrecord = ((Spellbuyrecord)((Object[])dataList.get(k))[1]);
      this.user = ((User)((Object[])dataList.get(k))[2]);
      this.spellbuyproduct = ((Spellbuyproduct)((Object[])dataList.get(k))[3]);
      
      this.calendar.setTime(DateUtil.SDateTimeToDate(this.spellbuyrecord.getBuyDate()));
      


      Integer h = Integer.valueOf(this.calendar.get(11));
      Integer m = Integer.valueOf(this.calendar.get(12));
      Integer s1 = Integer.valueOf(this.calendar.get(13));
      String shs = "";
      String sms = "";
      String sss = "";
      if (h.intValue() < 10) {
        shs = "0" + h;
      } else {
        shs = h.toString();
      }
      if (m.intValue() < 10) {
        sms = "0" + m;
      } else {
        sms = m.toString();
      }
      if (s1.intValue() < 10) {
        sss = "0" + s1;
      } else {
        sss = s1.toString();
      }
      DateSUM = Long.valueOf(DateSUM.longValue() + Long.parseLong(shs + sms + sss));
    }
    System.err.println("NewLotteryUtil DateSUM:" + DateSUM + "    " + productCart.getProductId());
    


    Integer winNumber = Integer.valueOf(Integer.parseInt(String.valueOf(DateSUM.longValue() % productCart.getProductPrice().intValue() + 10000001L)));
    System.err.println("NewLotteryUtil winNmuber:" + winNumber + "    " + productCart.getProductId());
    
    System.err.println("winNmuber:" + winNumber);
    boolean flag = false;
    Integer productPrice = productCart.getProductPrice();
    int count = productPrice.intValue();
    while (!flag)
    {
      List<Object[]> objList = this.spellbuyrecordService.WinRandomNumber(productCart.getProductId(), winNumber);
      if (objList.size() == 0)
      {
        winNumber = Integer.valueOf(winNumber.intValue() + 1);
        if (winNumber.intValue() > 10000000 + productPrice.intValue()) {
          winNumber = Integer.valueOf(10000001);
        }
      }
      else
      {
        flag = true;
      }
      System.err.println("++" + winNumber);
      count--;
      if (count == 0)
      {
        winNumber = Integer.valueOf(Integer.parseInt(String.valueOf(DateSUM.longValue() % productCart.getProductPrice().intValue() + 10000001L)));
        break;
      }
    }
    System.err.println("++" + winNumber);
    List<Object[]> objList = this.spellbuyrecordService.randomByBuyHistoryByspellbuyProductId(productCart.getProductId(), String.valueOf(winNumber));
    Randomnumber randomnumber = (Randomnumber)((Object[])objList.get(0))[0];
    Spellbuyrecord spellbuyrecord = (Spellbuyrecord)((Object[])objList.get(0))[1];
    User user2 = (User)((Object[])objList.get(0))[2];
    
    this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(productCart.getProductId().toString()));
    


    Thread.sleep(30000L);
    
    this.latestlottery = new Latestlottery();
    this.latestlottery.setProductId(this.spellbuyproduct.getFkProductId());
    this.latestlottery.setProductName(productCart.getProductName());
    this.latestlottery.setProductTitle(productCart.getProductTitle());
    this.latestlottery.setProductPrice(productCart.getProductPrice());
    this.latestlottery.setProductImg(productCart.getHeadImage());
    this.latestlottery.setProductPeriod(productCart.getProductPeriod());
    this.latestlottery.setAnnouncedTime(startDate);
    this.latestlottery.setBuyTime(spellbuyrecord.getBuyDate());
    this.latestlottery.setSpellbuyRecordId(spellbuyrecord.getSpellbuyRecordId());
    this.latestlottery.setSpellbuyProductId(spellbuyrecord.getFkSpellbuyProductId());
    this.latestlottery.setBuyNumberCount(spellbuyrecord.getBuyPrice());
    this.latestlottery.setRandomNumber(winNumber);
    this.latestlottery.setLocation(user2.getIpLocation());
    this.latestlottery.setUserId(user2.getUserId());
    if (StringUtils.isNotEmpty(user2.getUserName())) {
      this.latestlottery.setUserName(user2.getUserName());
    }
    if (StringUtils.isNotEmpty(user2.getPhone())) {
      this.latestlottery.setBuyUser(user2.getPhone());
    }
    if (StringUtils.isNotEmpty(user2.getMail())) {
      this.latestlottery.setBuyUser(user2.getMail());
    }
    this.latestlottery.setUserFace(user2.getFaceImg());
    this.latestlottery.setStatus(Integer.valueOf(1));
    this.latestlottery.setShareStatus(Integer.valueOf(-1));
    this.latestlottery.setShareId(null);
    this.latestlotteryService.add(this.latestlottery);
    
    spellbuyrecord.setSpRandomNo(String.valueOf(winNumber));
    spellbuyrecord.setSpWinningStatus("1");
    spellbuyrecord.setBuyStatus("1");
    this.spellbuyrecordService.add(spellbuyrecord);
    
    int productPeriod = productCart.getProductPeriod().intValue();
    Spellbuyproduct spellbuyproduct2 = new Spellbuyproduct();
    spellbuyproduct2.setFkProductId(this.spellbuyproduct.getFkProductId());
    spellbuyproduct2.setProductPeriod(Integer.valueOf(++productPeriod));
    spellbuyproduct2.setSpellbuyCount(Integer.valueOf(0));
    spellbuyproduct2.setSpellbuyEndDate(DateUtil.DateTimeToStr(new Date()));
    spellbuyproduct2.setSpellbuyPrice(productCart.getProductPrice());
    spellbuyproduct2.setSpellbuyStartDate(DateUtil.DateTimeToStr(new Date()));
    spellbuyproduct2.setSpStatus(Integer.valueOf(0));
    this.spellbuyproductService.add(spellbuyproduct2);
    
    this.product = ((Product)this.productService.findById(String.valueOf(this.spellbuyproduct.getFkProductId())));
    int Period = Integer.parseInt(this.product.getAttribute71());
    Period++;
    this.product.setAttribute71(String.valueOf(Period));
    this.product.setStatus(Integer.valueOf(1));
    this.productService.add(this.product);
    
    this.spellbuyproduct.setSpStatus(Integer.valueOf(1));
    this.spellbuyproductService.add(this.spellbuyproduct);
  }
}
