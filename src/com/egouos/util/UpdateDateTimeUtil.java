package com.egouos.util;

import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Randomnumber;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.RandomnumberService;
import com.egouos.service.SpellbuyrecordService;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext*.xml"})
@Repository
public class UpdateDateTimeUtil
{
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private RandomnumberService randomnumberService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  Randomnumber randomnumber;
  Latestlottery latestlottery;
  
  @Test
  public void go()
  {
    Random random = new Random();
    System.err.println("start");
    try
    {
      List<Spellbuyrecord> spellbuyrecordList = this.spellbuyrecordService.query("from Spellbuyrecord spellbuyrecord where spellbuyrecord.buyDate not like '%.%'");
      System.err.println("spellbuyrecordList size:" + spellbuyrecordList.size());
      for (Spellbuyrecord spellbuyrecord : spellbuyrecordList) {
        try
        {
          String date = spellbuyrecord.getBuyDate();
          String buyDate = "";
          String sss = "";
          Integer rnd = Integer.valueOf(random.nextInt(1000));
          if (rnd.intValue() < 10) {
            sss = "00" + rnd;
          } else if (rnd.intValue() < 100) {
            sss = "0" + rnd;
          } else {
            sss = rnd.toString();
          }
          buyDate = date + "." + sss;
          
          spellbuyrecord.setBuyDate(buyDate);
          this.spellbuyrecordService.add(spellbuyrecord);
          this.randomnumber = this.randomnumberService.findRandomnumberByspellbuyrecordId(spellbuyrecord.getSpellbuyRecordId());
          this.randomnumber.setBuyDate(buyDate);
          this.randomnumberService.add(this.randomnumber);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      List<Spellbuyrecord> spellbuyrecordList2 = this.spellbuyrecordService.query("from Spellbuyrecord spellbuyrecord where 1=1");
      for (Spellbuyrecord spellbuyrecord2 : spellbuyrecordList2) {
        try
        {
          if (spellbuyrecord2.getBuyStatus().equals("1"))
          {
            System.err.println("spellbuyrecord2:" + spellbuyrecord2.getSpellbuyRecordId());
            Spellbuyrecord spellbuyrecord3 = (Spellbuyrecord)this.spellbuyrecordService.getEndBuyDateByProduct(spellbuyrecord2.getFkSpellbuyProductId()).get(0);
            this.latestlottery = this.latestlotteryService.findLatestlotteryByspellbuyrecordId(spellbuyrecord2.getSpellbuyRecordId());
            this.latestlottery.setBuyTime(spellbuyrecord2.getBuyDate());
            this.latestlottery.setAnnouncedTime(spellbuyrecord3.getBuyDate());
            this.latestlotteryService.add(this.latestlottery);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      System.err.println("end");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args)
  {
    Random random = new Random();
    for (int i = 0; i < 1000; i++)
    {
      String date = "2013-07-01 11:42:56";
      String sss = "";
      Integer rnd = Integer.valueOf(random.nextInt(1000));
      if (rnd.intValue() < 10) {
        sss = "00" + rnd;
      } else if (rnd.intValue() < 100) {
        sss = "0" + rnd;
      } else {
        sss = rnd.toString();
      }
      date = date + "." + sss;
      System.err.println(date);
    }
  }
}
