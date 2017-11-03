package com.egouos.test;

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
import com.egouos.service.UserService;
import com.egouos.util.DateUtil;
import com.egouos.util.RandomUtil;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext*.xml"})
@Repository
public class VirtualUserBuyOne
{
  @Autowired
  private UserService userService;
  RandomUtil randomUtil = new RandomUtil();
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private RandomnumberService randomnumberService;
  @Autowired
  private ProductService productService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  private ProductCart productCart;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private Spellbuyrecord spellbuyrecord;
  private Randomnumber randomnumber;
  private Latestlottery latestlottery;
  private User user;
  Calendar calendar = Calendar.getInstance();
  
  @Test
  public void goBuy()
  {
    this.productCart = new ProductCart();
    List<Object[]> proList = this.spellbuyproductService.findByProductId(11582);
    this.product = ((Product)((Object[])proList.get(0))[0]);
    this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
    this.productCart.setHeadImage(this.product.getHeadImage());
    this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
    this.productCart.setProductName(this.product.getProductName());
    this.productCart.setProductPrice(this.product.getProductPrice());
    this.productCart.setProductTitle(this.product.getProductTitle());
    this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
    this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
    if (this.spellbuyproduct.getSpStatus().intValue() == 1)
    {
      this.spellbuyrecord = ((Spellbuyrecord)this.spellbuyrecordService.getEndBuyDateByProduct(this.productCart.getProductId()).get(0));
      
      Pagination page = this.spellbuyrecordService.getlotteryDetail(null, this.spellbuyrecord.getBuyDate(), 0, 100);
      List<Object[]> dataList = (List<Object[]>)page.getList();
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
      System.err.println(DateSUM + "************************");
      System.err.println(DateSUM.longValue() % this.productCart.getProductPrice().intValue() + "---------------");
      System.err.println(String.valueOf(DateSUM.longValue() % this.productCart.getProductPrice().intValue() + 10000001L) + "&&&&&&&&&&&&&&&&&&&&&&&");
      


      Integer winNmuber = Integer.valueOf(Integer.parseInt(String.valueOf(DateSUM.longValue() % this.productCart.getProductPrice().intValue() + 10000001L)));
      

      System.err.println("winNmuber+++" + winNmuber);
      
      List<Object[]> objList = this.spellbuyrecordService.randomByBuyHistoryByspellbuyProductId(this.productCart.getProductId(), String.valueOf(winNmuber));
      Randomnumber randomnumber = (Randomnumber)((Object[])objList.get(0))[0];
      Spellbuyrecord spellbuyrecord = (Spellbuyrecord)((Object[])objList.get(0))[1];
      User user2 = (User)((Object[])objList.get(0))[2];
      
      this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(this.productCart.getProductId().toString()));
      
      this.latestlottery = new Latestlottery();
      this.latestlottery.setProductId(this.spellbuyproduct.getFkProductId());
      this.latestlottery.setProductName(this.productCart.getProductName());
      this.latestlottery.setProductTitle(this.productCart.getProductTitle());
      this.latestlottery.setProductPrice(this.productCart.getProductPrice());
      this.latestlottery.setProductImg(this.productCart.getHeadImage());
      this.latestlottery.setProductPeriod(this.productCart.getProductPeriod());
      this.latestlottery.setAnnouncedTime(DateUtil.DateTimeToStr(new Date()));
      this.latestlottery.setBuyTime(spellbuyrecord.getBuyDate());
      this.latestlottery.setSpellbuyRecordId(spellbuyrecord.getSpellbuyRecordId());
      this.latestlottery.setSpellbuyProductId(spellbuyrecord.getFkSpellbuyProductId());
      this.latestlottery.setBuyNumberCount(spellbuyrecord.getBuyPrice());
      this.latestlottery.setRandomNumber(winNmuber);
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
      
      spellbuyrecord.setSpRandomNo(String.valueOf(winNmuber));
      spellbuyrecord.setSpWinningStatus("1");
      spellbuyrecord.setBuyStatus("1");
      this.spellbuyrecordService.add(spellbuyrecord);
      
      int productPeriod = this.productCart.getProductPeriod().intValue();
      this.spellbuyproduct = new Spellbuyproduct();
      this.spellbuyproduct.setFkProductId(this.spellbuyproduct.getFkProductId());
      this.spellbuyproduct.setProductPeriod(Integer.valueOf(productPeriod));
      this.spellbuyproduct.setSpellbuyCount(Integer.valueOf(0));
      this.spellbuyproduct.setSpellbuyEndDate(DateUtil.DateTimeToStr(new Date()));
      this.spellbuyproduct.setSpellbuyPrice(this.productCart.getProductPrice());
      this.spellbuyproduct.setSpellbuyStartDate(DateUtil.DateTimeToStr(new Date()));
      this.spellbuyproduct.setSpStatus(Integer.valueOf(0));
      

      this.product = ((Product)this.productService.findById(String.valueOf(this.spellbuyproduct.getFkProductId())));
      this.product.setAttribute71(String.valueOf(productPeriod + 1));
      this.product.setStatus(Integer.valueOf(1));
    }
  }
}
