package com.egouos.util;

import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.User;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.ProductService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import java.io.PrintStream;
import java.util.Calendar;
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
@Service("TempLotteryUtil")
public class TempLotteryUtil
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
    List<Latestlottery> list = this.latestlotteryService.query("from Latestlottery latestlottery where 1=1");
    System.err.println("size:" + list.size());
    for (Latestlottery latestlottery : list)
    {
      Long l = latestlottery.getDateSum();
      l = Long.valueOf(l.longValue() - 1L);
      latestlottery.setDateSum(l);
      System.err.println("default:" + latestlottery.getDateSum() + "   update:" + l);
      this.latestlotteryService.add(latestlottery);
    }
  }
}
