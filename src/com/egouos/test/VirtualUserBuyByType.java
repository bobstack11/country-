package com.egouos.test;

import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Lotteryproductutil;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
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
import com.egouos.service.UserService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.DateUtil;
import com.egouos.util.MD5Util;
import com.egouos.util.NewLotteryUtil;
import com.egouos.util.RandomUtil;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext*.xml"})
@Repository
public class VirtualUserBuyByType
{
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
  @Autowired
  private NewLotteryUtil newLotteryUtil;
  @Autowired
  LotteryproductutilService lotteryproductutilService;
  private ProductCart productCart;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private Spellbuyrecord spellbuyrecord;
  private Randomnumber randomnumber;
  private Latestlottery latestlottery;
  private Lotteryproductutil lotteryproductutil;
  Calendar calendar = Calendar.getInstance();
  static List<User> userList = null;
  
  @Test
  public void goBuy()
  {
    Random random = new Random();
    if (random.nextInt(15) != 5) {
      return;
    }
    boolean flag = true;
    List<Spellbuyproduct> spellbuyproductList = this.spellbuyproductService.loadAllByType();
    if (spellbuyproductList.size() > 0)
    {
      for (int i = 0; i < 2; i++) {
        Collections.shuffle(spellbuyproductList);
      }
      if (userList == null) {
        userList = this.userService.loadAll();
      }
      for (int j = 0; j < 1; j++) {
        if (((Spellbuyproduct)spellbuyproductList.get(j)).getSpStatus().intValue() != 1)
        {
          Integer productId = ((Spellbuyproduct)spellbuyproductList.get(j)).getSpellbuyProductId();
          int BuyCount = 1;
          Collections.shuffle(userList);
          for (int i = 1; i <= BuyCount;) {
            try
            {
              User user = (User)userList.get(random.nextInt(userList.size()));
              int[] buyNumbers = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 5, 1, 2, 4, 3, 5, 5, 1, 3, 2, 4, 5, 1, 1, 2, 2, 2, 8, 8, 6, 7, 10, 1, 
                2, 10, 2, 1, 5, 10, 2, 1, 20, 5, 20, 1, 2, 1, 15, 2, 1, 10, 1, 2, 15, 5, 1, 2, 16, 1, 6, 8, 4, 1, 2, 1, 2, 1, 2, 30, 2, 1, 
                30, 1, 2, 50, 1, 2, 1, 50, 1, 2, 50, 1, 2, 5, 1, 2, 1, 5, 2, 1, 100, 2, 22, 1, 11, 2, 5, 1, 2, 2, 1, 2, 3, 1, 4, 40, 5, 1, 
                2, 2, 4, 5, 6, 1, 1, 2, 1, 2, 2, 4, 3, 5, 6, 1, 1, 2, 2, 25, 6, 2, 5, 3, 2, 1, 2, 4, 7, 8, 1, 2, 3, 1, 2, 55, 1, 2, 1, 2, 3, 2, 
                1, 2, 1, 23, 2, 3, 7, 8, 7, 3, 3, 4, 1, 2, 2, 3, 1, 2, 2, 2, 2, 3, 2, 1, 2, 5, 1, 2, 500, 2, 3, 3, 2, 1, 1, 2, 3, 3, 2, 1, 2, 5, 6, 7, 
                2, 20, 3, 1, 2, 3, 1, 2, 2, 100, 1, 2, 6, 23, 8, 25, 1, 25, 6, 1, 2, 3, 2, 1, 1, 23, 3, 4, 1, 2, 2, 24, 4, 2, 1, 21, 2, 12, 10, 
                1, 2, 2, 1, 2, 3, 5, 6, 10, 1, 1, 2, 2, 1, 1, 2, 2, 20, 10, 5, 3, 1, 5, 1, 12, 6, 4, 5, 6, 1, 2, 3, 6, 10, 8, 1, 23, 4, 7, 2, 2 };
              int buyCount = buyNumbers[random.nextInt(buyNumbers.length)];
              this.productCart = new ProductCart();
              List<Object[]> proList = this.spellbuyproductService.findByProductId(productId.intValue());
              this.product = ((Product)((Object[])proList.get(0))[0]);
              this.spellbuyproduct = ((Spellbuyproduct)((Object[])proList.get(0))[1]);
              if (productId.intValue() == this.spellbuyproduct.getSpellbuyProductId().intValue())
              {
                this.spellbuyrecord = new Spellbuyrecord();
                this.productCart.setCount(Integer.valueOf(buyCount));
                this.productCart.setHeadImage(this.product.getHeadImage());
                this.productCart.setProductId(this.spellbuyproduct.getSpellbuyProductId());
                this.productCart.setProductName(this.product.getProductName());
                this.productCart.setProductPrice(this.product.getProductPrice());
                this.productCart.setProductTitle(this.product.getProductTitle());
                this.productCart.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
                this.productCart.setProductPeriod(this.spellbuyproduct.getProductPeriod());
                try
                {
                  Integer count = Integer.valueOf(0);
                  
                  Integer CurrentPrice = this.spellbuyproduct.getSpellbuyCount();
                  if (this.spellbuyproduct.getSpellbuyCount().intValue() + buyCount > this.product.getProductPrice().intValue()) {
                    count = Integer.valueOf(this.productCart.getProductPrice().intValue() - this.spellbuyproduct.getSpellbuyCount().intValue());
                  } else {
                    count = Integer.valueOf(buyCount);
                  }
                  if (count.intValue() > 0)
                  {
                    this.spellbuyproduct.setSpellbuyCount(Integer.valueOf(this.spellbuyproduct.getSpellbuyCount().intValue() + count.intValue()));
                    if (this.spellbuyproduct.getSpellbuyCount().intValue() >= this.product.getProductPrice().intValue())
                    {
                      this.spellbuyproduct.setSpellbuyCount(this.product.getProductPrice());
                      this.spellbuyproduct.setSpStatus(Integer.valueOf(2));
                      this.spellbuyproduct.setSpellbuyEndDate(DateUtil.DateTimeToStr(DateUtil.subMinute(new Date(), -11)));
                    }
                    this.spellbuyproductService.add(this.spellbuyproduct);
                    

                    String dates = this.sdf.format(new Date());
                    int s = random.nextInt(60);
                    String ss = "";
                    if (s < 10) {
                      ss = "0" + String.valueOf(s);
                    } else {
                      ss = String.valueOf(s);
                    }
                    dates = dates + ":" + ss;
                    
                    String sss = "";
                    Integer rnd = Integer.valueOf(random.nextInt(1000));
                    if (rnd.intValue() < 10) {
                      sss = "00" + rnd;
                    } else if (rnd.intValue() < 100) {
                      sss = "0" + rnd;
                    } else {
                      sss = rnd.toString();
                    }
                    dates = dates + "." + sss;
                    

                    this.spellbuyrecord.setFkSpellbuyProductId(this.spellbuyproduct.getSpellbuyProductId());
                    this.spellbuyrecord.setBuyer(user.getUserId());
                    this.spellbuyrecord.setBuyPrice(count);
                    this.spellbuyrecord.setBuyDate(dates);
                    this.spellbuyrecord.setSpWinningStatus("0");
                    this.spellbuyrecord.setBuyStatus("0");
                    this.spellbuyrecord.setSpRandomNo("");
                    this.spellbuyrecordService.add(this.spellbuyrecord);
                    
                    this.randomnumber = new Randomnumber();
                    this.randomnumber.setProductId(this.productCart.getProductId());
                    
                    List<Randomnumber> RandomnumberList = this.randomnumberService.query(" from Randomnumber where productId='" + this.spellbuyproduct.getSpellbuyProductId() + 
                      "'");
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
                    this.randomnumber.setUserId(user.getUserId());
                    this.randomnumberService.add(this.randomnumber);
                    
                    Integer experience = user.getExperience();
                    experience = Integer.valueOf(experience.intValue() + count.intValue() * 10);
                    user.setExperience(experience);
                    this.userService.add(user);
                  }
                }
                catch (Exception e)
                {
                  e.printStackTrace();
                  flag = false;
                }
              }
              if ((flag) && 
                (this.spellbuyproduct.getSpStatus().intValue() == 2))
              {
                String lotteryId = MD5Util.encode(String.valueOf(this.spellbuyproduct.getSpellbuyProductId()));
                if (AliyunOcsSampleHelp.getIMemcachedCache().get(lotteryId) == null)
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
                  AliyunOcsSampleHelp.getIMemcachedCache().set(lotteryId, 1800, "y");
                }
              }
              if (!flag) {
                break;
              }
              i++;
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
  
  public static void main(String[] args)
  {
    Random random = new Random();
    











    int[] buyNumbers = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 5, 1, 2, 4, 3, 5, 5, 1, 3, 2, 4, 5, 1, 1, 2, 2, 2, 8, 8, 6, 7, 10, 1, 
      2, 10, 2, 1, 5, 10, 2, 1, 20, 5, 20, 1, 2, 1, 15, 2, 1, 10, 1, 2, 15, 5, 1, 2, 16, 1, 6, 8, 4, 1, 2, 1, 2, 1, 2, 30, 2, 1, 
      30, 1, 2, 50, 1, 2, 1, 50, 1, 2, 50, 1, 2, 5, 1, 2, 1, 5, 2, 1, 100, 2, 22, 1, 11, 2, 5, 1, 2, 2, 1, 2, 3, 1, 4, 40, 5, 1, 
      2, 2, 4, 5, 6, 1, 1, 2, 1, 2, 2, 4, 3, 5, 6, 1, 1, 2, 2, 25, 6, 2, 5, 3, 2, 1, 2, 4, 7, 8, 1, 2, 3, 1, 2, 55, 1, 2, 1, 2, 3, 2, 
      1, 2, 1, 23, 2, 3, 7, 8, 7, 3, 3, 4, 1, 2, 2, 3, 1, 2, 2, 2, 2, 3, 2, 1, 2, 5, 1, 2, 2, 3, 3, 2, 1, 1, 2, 3, 3, 2, 1, 2, 5, 6, 7, 
      2, 20, 3, 1, 2, 3, 1, 2, 2, 1, 2, 6, 23, 8, 25, 1, 25, 6, 1, 2, 3, 2, 1, 1, 23, 3, 4, 1, 2, 2, 24, 4, 2, 1, 21, 2, 12, 10, 
      1, 2, 2, 1, 2, 3, 5, 6, 10, 1, 1, 2, 2, 1, 1, 2, 2, 20, 10, 5, 3, 1, 5, 1, 12, 6, 4, 5, 6, 1, 2, 3, 6, 10, 8, 1, 23, 4, 7, 2, 2 };
    for (int i = 0; i < 100; i++) {
      System.err.println(buyNumbers[random.nextInt(buyNumbers.length)]);
    }
  }
}
