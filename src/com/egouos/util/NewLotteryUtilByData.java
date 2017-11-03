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
import com.egouos.service.RandomnumberService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
@Service("NewLotteryUtilByData")
public class NewLotteryUtilByData
{
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ProductService productService;
  @Autowired
  private RandomnumberService randomnumberService;
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
    List<Spellbuyproduct> list = this.spellbuyproductService.query("from Spellbuyproduct spellbuyproduct where spellbuyproduct.spStatus = 1");
    for (Spellbuyproduct spellbuyproduct : list)
    {
      this.productCart = new ProductCart();
      List<Object[]> proList = this.spellbuyproductService.findByProductId(spellbuyproduct.getSpellbuyProductId().intValue());
      this.product = ((Product)((Object[])proList.get(0))[0]);
      spellbuyproduct = (Spellbuyproduct)((Object[])proList.get(0))[1];
      this.productCart.setHeadImage(this.product.getHeadImage());
      this.productCart.setProductId(spellbuyproduct.getSpellbuyProductId());
      this.productCart.setProductName(this.product.getProductName());
      this.productCart.setProductPrice(this.product.getProductPrice());
      this.productCart.setProductTitle(this.product.getProductTitle());
      this.productCart.setCurrentBuyCount(spellbuyproduct.getSpellbuyCount());
      this.productCart.setProductPeriod(spellbuyproduct.getProductPeriod());
      
      lottery(this.productCart);
    }
  }
  
  public void lottery(ProductCart productCart)
    throws InterruptedException
  {
    this.spellbuyrecord = ((Spellbuyrecord)this.spellbuyrecordService.getEndBuyDateByProduct(productCart.getProductId()).get(0));
    String startDate = this.spellbuyrecord.getBuyDate();
    
    System.err.println("NewLotteryUtil startDate:" + startDate + "   " + productCart.getProductId());
    




    Pagination page = this.spellbuyrecordService.getlotteryDetail(null, startDate, 0, 120);
    List<?> dataList = page.getList();
    Long DateSUM = Long.valueOf(0L);
    String newDate = "";
    int buyId = ((Spellbuyrecord)((Object[])dataList.get(0))[1]).getFkSpellbuyProductId().intValue();
    newDate = ((Spellbuyrecord)((Object[])dataList.get(0))[1]).getBuyDate();
    int i100 = 0;
    for (int k = 0; k < dataList.size(); k++) {
      if ((k <= 0) || 
      
        (!newDate.equals(((Spellbuyrecord)((Object[])dataList.get(k))[1]).getBuyDate())) || (((Spellbuyrecord)((Object[])dataList.get(k))[1]).getFkSpellbuyProductId().intValue() == buyId))
      {
        if (i100++ == 100) {
          break;
        }
        this.spellbuyrecord = ((Spellbuyrecord)((Object[])dataList.get(k))[1]);
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
        DateSUM = Long.valueOf(DateSUM.longValue() + Long.parseLong(sh + sm + ss + sss));
      }
    }
    System.err.println("NewLotteryUtil DateSUM:" + DateSUM + "    " + productCart.getProductId());
    


    Integer winNumber = Integer.valueOf(Integer.parseInt(String.valueOf(DateSUM.longValue() % productCart.getProductPrice().intValue() + 10000001L)));
    
    System.err.println("NewLotteryUtil winNmuber:" + winNumber + "    " + productCart.getProductId());
    boolean flag = false;
    Integer productPrice = productCart.getProductPrice();
    
    Randomnumber randomnumberUP = null;
    long winCount = 0L;
    long count = productPrice.intValue();
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
        randomnumberUP = (Randomnumber)((Object[])objList.get(0))[0];
      }
      count -= 1L;
      if (count == 0L)
      {
        winNumber = Integer.valueOf(Integer.parseInt(String.valueOf(DateSUM.longValue() % productCart.getProductPrice().intValue() + 10000001L)));
        break;
      }
    }
    long price = productPrice.intValue();
    long sucess = winNumber.intValue();
    long real = DateSUM.longValue() % price;
    long update = DateSUM.longValue();
    
    update += sucess - real;
    winCount = sucess - real;
    if (randomnumberUP != null)
    {
      String updateDate = randomnumberUP.getBuyDate();
      Long dateTime = Long.valueOf(DateUtil.SDateTimeToDateBySSS(updateDate).getTime());
      dateTime = Long.valueOf(dateTime.longValue() + winCount);
      Date date = new Date(dateTime.longValue());
      Spellbuyrecord spellbuyrecord2 = (Spellbuyrecord)this.spellbuyrecordService.findById(String.valueOf(randomnumberUP.getSpellbuyrecordId()));
      spellbuyrecord2.setBuyDate(DateUtil.DateTimeToStrBySSS(date));
      randomnumberUP.setBuyDate(DateUtil.DateTimeToStrBySSS(date));
      this.spellbuyrecordService.add(spellbuyrecord2);
      this.randomnumberService.add(randomnumberUP);
    }
    List<Object[]> objList = this.spellbuyrecordService.randomByBuyHistoryByspellbuyProductId(productCart.getProductId(), String.valueOf(winNumber));
    Randomnumber randomnumber = (Randomnumber)((Object[])objList.get(0))[0];
    Spellbuyrecord spellbuyrecord = (Spellbuyrecord)((Object[])objList.get(0))[1];
    User user2 = (User)((Object[])objList.get(0))[2];
    
    this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(productCart.getProductId().toString()));
    
    List list = this.latestlotteryService.getLatestlotteryBySpellbuyProductIdAndProductIdIsExist(productCart.getProductId());
    if (list.size() == 0)
    {
      this.latestlottery = new Latestlottery();
      this.latestlottery.setProductId(this.spellbuyproduct.getFkProductId());
      this.latestlottery.setProductName(productCart.getProductName());
      this.latestlottery.setProductTitle(productCart.getProductTitle());
      this.latestlottery.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
      this.latestlottery.setProductImg(productCart.getHeadImage());
      this.latestlottery.setProductPeriod(this.spellbuyproduct.getProductPeriod());
      this.latestlottery.setAnnouncedTime(startDate);
      this.latestlottery.setAnnouncedType(this.spellbuyproduct.getSpellbuyType());
      this.latestlottery.setDateSum(Long.valueOf(update));
      this.latestlottery.setBuyTime(spellbuyrecord.getBuyDate());
      this.latestlottery.setSpellbuyRecordId(spellbuyrecord.getSpellbuyRecordId());
      this.latestlottery.setSpellbuyProductId(spellbuyrecord.getFkSpellbuyProductId());
      BigDecimal buyNumberCount = this.randomnumberService.RandomNumberByUserBuyCount(String.valueOf(user2.getUserId()), this.spellbuyproduct.getSpellbuyProductId());
      this.latestlottery.setBuyNumberCount(Integer.valueOf(Integer.parseInt(String.valueOf(buyNumberCount))));
      this.latestlottery.setRandomNumber(winNumber);
      this.latestlottery.setLocation(spellbuyrecord.getBuyLocal());
      //this.latestlottery.setLocation(user2.getIpLocation());
      this.latestlottery.setUserId(user2.getUserId());
      this.latestlottery.setUserName(UserNameUtil.userName(user2));
      this.latestlottery.setUserFace(user2.getFaceImg());
      this.latestlottery.setStatus(Integer.valueOf(1));
      this.latestlottery.setShareStatus(Integer.valueOf(-1));
      this.latestlottery.setShareId(null);
      this.latestlotteryService.add(this.latestlottery);
      
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
          spellbuyproduct2.setSpellbuyPrice(productCart.getProductPrice());
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
    }
  }
  
  public static void main(String[] args)
    throws InterruptedException
  {
    Calendar calendar = Calendar.getInstance();
    
    Long DateSUM = Long.valueOf(0L);
    
    calendar.setTime(DateUtil.SDateTimeToDateBySSS("2013-12-11 15:38:19.095"));
    


    Integer h = Integer.valueOf(calendar.get(11));
    Integer m = Integer.valueOf(calendar.get(12));
    Integer s1 = Integer.valueOf(calendar.get(13));
    Integer ss1 = Integer.valueOf(calendar.get(14));
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
    System.err.println(sh);
    System.err.println(sm);
    System.err.println(ss);
    System.err.println(sss);
    System.err.println(sh + sm + ss + sss);
    DateSUM = Long.valueOf(DateSUM.longValue() + Long.parseLong(sh + sm + ss + sss));
    
    System.err.println(DateSUM);
    

    System.err.println(DateUtil.SDateTimeToDateBySSS("2013-12-11 15:38:19.095").getTime());
    System.err.println(DateUtil.SDateTimeToDateBySSS("2013-12-11 15:38:19.095").getTime() + 500L);
  }
}
