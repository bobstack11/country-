package com.egouos.util;

import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Lotteryproductutil;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductCart;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.SysConfigure;
import com.egouos.pojo.User;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.LotteryproductutilService;
import com.egouos.service.ProductService;
import com.egouos.service.RandomnumberService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext*.xml"})
@Repository
public class LotteryUtil
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
  @Autowired
  private LotteryproductutilService lotteryproductutilService;
  private Product product;
  private User user;
  private Spellbuyproduct spellbuyproduct;
  private Latestlottery latestlottery;
  private Spellbuyrecord spellbuyrecord;
  private ProductCart productCart;
  private List<Lotteryproductutil> LotteryproductutilList;
  Calendar calendar = Calendar.getInstance();
  
  @Test
  public void go()
    throws InterruptedException, IOException
  {
    Random random = new Random();
    Document document;
    if (random.nextInt(15) == 5) {
      try
      {
        String ip = ApplicationListenerImpl.sysConfigureJson.getAuthorization();
        document = Jsoup.connect("http://www.egouos.com/authorization.txt").timeout(60000).get();
        if (StringUtil.isNotBlank(ip)) {
          if (document.text().indexOf(ip) != -1) {
            ApplicationListenerImpl.sysConfigureAuth = true;
          } else {
            ApplicationListenerImpl.sysConfigureAuth = false;
          }
        }
      }
      catch (Exception e)
      {
        System.err.println("auto error");
      }
    }
    this.LotteryproductutilList = this.lotteryproductutilService.loadAll();
    if (this.LotteryproductutilList.size() > 0) {
      for (Lotteryproductutil lotteryproductutil : this.LotteryproductutilList)
      {
        Long nowDate = Long.valueOf(System.currentTimeMillis());
        Long endDate = Long.valueOf(DateUtil.SDateTimeToDate(lotteryproductutil.getLotteryProductEndDate()).getTime());
        lotteryproductutil.setLotteryProductEndDate(((endDate.longValue() - nowDate.longValue()) / 1000L)+"");
        
        int lotterDate = Integer.parseInt(lotteryproductutil.getLotteryProductEndDate());
        if (lotterDate <= 0) {
          lottery(lotteryproductutil.getLotteryProductId().toString());
        }
      }
    }
  }
  
  /* Error */
  public synchronized void lottery(String id)
    throws InterruptedException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 238	com/egouos/util/MD5Util:encode	(Ljava/lang/String;)Ljava/lang/String;
    //   4: astore_2
    //   5: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   8: aload_2
    //   9: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   12: ifnonnull +3191 -> 3203
    //   15: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   18: aload_2
    //   19: iconst_5
    //   20: ldc 255
    //   22: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   25: pop
    //   26: aload_0
    //   27: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   30: new 186	java/lang/StringBuilder
    //   33: dup
    //   34: ldc_w 261
    //   37: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   40: aload_1
    //   41: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   50: checkcast 267	com/egouos/pojo/Spellbuyproduct
    //   53: putfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   56: aload_0
    //   57: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   60: ifnonnull +49 -> 109
    //   63: aload_0
    //   64: aload_0
    //   65: getfield 271	com/egouos/util/LotteryUtil:spellbuyproductService	Lcom/egouos/service/SpellbuyproductService;
    //   68: aload_1
    //   69: invokeinterface 273 2 0
    //   74: checkcast 267	com/egouos/pojo/Spellbuyproduct
    //   77: putfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   80: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   83: new 186	java/lang/StringBuilder
    //   86: dup
    //   87: ldc_w 261
    //   90: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   93: aload_1
    //   94: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: iconst_1
    //   101: aload_0
    //   102: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   105: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   108: pop
    //   109: aload_0
    //   110: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   113: invokevirtual 278	com/egouos/pojo/Spellbuyproduct:getSpStatus	()Ljava/lang/Integer;
    //   116: invokevirtual 281	java/lang/Integer:intValue	()I
    //   119: iconst_2
    //   120: if_icmpne +3221 -> 3341
    //   123: aload_0
    //   124: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   127: new 186	java/lang/StringBuilder
    //   130: dup
    //   131: ldc_w 284
    //   134: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   137: aload_0
    //   138: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   141: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   144: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   147: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   150: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   153: checkcast 292	com/egouos/pojo/Spellbuyrecord
    //   156: putfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   159: aload_0
    //   160: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   163: ifnonnull +69 -> 232
    //   166: aload_0
    //   167: aload_0
    //   168: getfield 296	com/egouos/util/LotteryUtil:spellbuyrecordService	Lcom/egouos/service/SpellbuyrecordService;
    //   171: aload_0
    //   172: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   175: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   178: invokeinterface 298 2 0
    //   183: iconst_0
    //   184: invokeinterface 304 2 0
    //   189: checkcast 292	com/egouos/pojo/Spellbuyrecord
    //   192: putfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   195: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   198: new 186	java/lang/StringBuilder
    //   201: dup
    //   202: ldc_w 284
    //   205: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   208: aload_0
    //   209: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   212: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   215: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   218: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   221: sipush 1800
    //   224: aload_0
    //   225: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   228: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   231: pop
    //   232: aload_0
    //   233: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   236: invokevirtual 307	com/egouos/pojo/Spellbuyrecord:getBuyDate	()Ljava/lang/String;
    //   239: astore_3
    //   240: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   243: new 186	java/lang/StringBuilder
    //   246: dup
    //   247: ldc_w 310
    //   250: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   253: aload_3
    //   254: invokestatic 312	com/egouos/util/DateUtil:SDateTimeToDateBySSS	(Ljava/lang/String;)Ljava/util/Date;
    //   257: invokevirtual 181	java/util/Date:getTime	()J
    //   260: invokevirtual 315	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   263: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   266: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   269: checkcast 145	java/util/List
    //   272: astore 4
    //   274: aload 4
    //   276: ifnonnull +53 -> 329
    //   279: aload_0
    //   280: getfield 296	com/egouos/util/LotteryUtil:spellbuyrecordService	Lcom/egouos/service/SpellbuyrecordService;
    //   283: aconst_null
    //   284: aload_3
    //   285: bipush 105
    //   287: invokeinterface 318 4 0
    //   292: astore 4
    //   294: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   297: new 186	java/lang/StringBuilder
    //   300: dup
    //   301: ldc_w 310
    //   304: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   307: aload_3
    //   308: invokestatic 312	com/egouos/util/DateUtil:SDateTimeToDateBySSS	(Ljava/lang/String;)Ljava/util/Date;
    //   311: invokevirtual 181	java/util/Date:getTime	()J
    //   314: invokevirtual 315	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   317: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   320: sipush 1800
    //   323: aload 4
    //   325: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   328: pop
    //   329: aload 4
    //   331: new 322	com/egouos/util/LotteryUtil$1
    //   334: dup
    //   335: aload_0
    //   336: invokespecial 324	com/egouos/util/LotteryUtil$1:<init>	(Lcom/egouos/util/LotteryUtil;)V
    //   339: invokestatic 327	java/util/Collections:sort	(Ljava/util/List;Ljava/util/Comparator;)V
    //   342: lconst_0
    //   343: invokestatic 166	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   346: astore 5
    //   348: aload 4
    //   350: iconst_0
    //   351: invokeinterface 304 2 0
    //   356: checkcast 292	com/egouos/pojo/Spellbuyrecord
    //   359: invokevirtual 333	com/egouos/pojo/Spellbuyrecord:getFkSpellbuyProductId	()Ljava/lang/Integer;
    //   362: astore 6
    //   364: iconst_0
    //   365: istore 7
    //   367: iconst_0
    //   368: istore 8
    //   370: goto +425 -> 795
    //   373: iload 8
    //   375: ifle +48 -> 423
    //   378: aload_3
    //   379: aload 4
    //   381: iload 8
    //   383: invokeinterface 304 2 0
    //   388: checkcast 292	com/egouos/pojo/Spellbuyrecord
    //   391: invokevirtual 307	com/egouos/pojo/Spellbuyrecord:getBuyDate	()Ljava/lang/String;
    //   394: invokevirtual 336	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   397: ifeq +26 -> 423
    //   400: aload 4
    //   402: iload 8
    //   404: invokeinterface 304 2 0
    //   409: checkcast 292	com/egouos/pojo/Spellbuyrecord
    //   412: invokevirtual 333	com/egouos/pojo/Spellbuyrecord:getFkSpellbuyProductId	()Ljava/lang/Integer;
    //   415: aload 6
    //   417: if_acmpeq +6 -> 423
    //   420: goto +372 -> 792
    //   423: iload 7
    //   425: iinc 7 1
    //   428: bipush 100
    //   430: if_icmpne +6 -> 436
    //   433: goto +374 -> 807
    //   436: aload_0
    //   437: aload 4
    //   439: iload 8
    //   441: invokeinterface 304 2 0
    //   446: checkcast 292	com/egouos/pojo/Spellbuyrecord
    //   449: putfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   452: aload_0
    //   453: getfield 48	com/egouos/util/LotteryUtil:calendar	Ljava/util/Calendar;
    //   456: aload_0
    //   457: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   460: invokevirtual 307	com/egouos/pojo/Spellbuyrecord:getBuyDate	()Ljava/lang/String;
    //   463: invokestatic 312	com/egouos/util/DateUtil:SDateTimeToDateBySSS	(Ljava/lang/String;)Ljava/util/Date;
    //   466: invokevirtual 340	java/util/Calendar:setTime	(Ljava/util/Date;)V
    //   469: aload_0
    //   470: getfield 48	com/egouos/util/LotteryUtil:calendar	Ljava/util/Calendar;
    //   473: bipush 11
    //   475: invokevirtual 344	java/util/Calendar:get	(I)I
    //   478: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   481: astore 9
    //   483: aload_0
    //   484: getfield 48	com/egouos/util/LotteryUtil:calendar	Ljava/util/Calendar;
    //   487: bipush 12
    //   489: invokevirtual 344	java/util/Calendar:get	(I)I
    //   492: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   495: astore 10
    //   497: aload_0
    //   498: getfield 48	com/egouos/util/LotteryUtil:calendar	Ljava/util/Calendar;
    //   501: bipush 13
    //   503: invokevirtual 344	java/util/Calendar:get	(I)I
    //   506: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   509: astore 11
    //   511: aload_0
    //   512: getfield 48	com/egouos/util/LotteryUtil:calendar	Ljava/util/Calendar;
    //   515: bipush 14
    //   517: invokevirtual 344	java/util/Calendar:get	(I)I
    //   520: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   523: astore 12
    //   525: ldc_w 349
    //   528: astore 13
    //   530: ldc_w 349
    //   533: astore 14
    //   535: ldc_w 349
    //   538: astore 15
    //   540: ldc_w 349
    //   543: astore 16
    //   545: aload 9
    //   547: invokevirtual 281	java/lang/Integer:intValue	()I
    //   550: bipush 10
    //   552: if_icmpge +26 -> 578
    //   555: new 186	java/lang/StringBuilder
    //   558: dup
    //   559: ldc_w 351
    //   562: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   565: aload 9
    //   567: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   570: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   573: astore 13
    //   575: goto +10 -> 585
    //   578: aload 9
    //   580: invokevirtual 213	java/lang/Integer:toString	()Ljava/lang/String;
    //   583: astore 13
    //   585: aload 10
    //   587: invokevirtual 281	java/lang/Integer:intValue	()I
    //   590: bipush 10
    //   592: if_icmpge +26 -> 618
    //   595: new 186	java/lang/StringBuilder
    //   598: dup
    //   599: ldc_w 351
    //   602: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   605: aload 10
    //   607: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   610: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   613: astore 14
    //   615: goto +10 -> 625
    //   618: aload 10
    //   620: invokevirtual 213	java/lang/Integer:toString	()Ljava/lang/String;
    //   623: astore 14
    //   625: aload 11
    //   627: invokevirtual 281	java/lang/Integer:intValue	()I
    //   630: bipush 10
    //   632: if_icmpge +26 -> 658
    //   635: new 186	java/lang/StringBuilder
    //   638: dup
    //   639: ldc_w 351
    //   642: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   645: aload 11
    //   647: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   650: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   653: astore 15
    //   655: goto +10 -> 665
    //   658: aload 11
    //   660: invokevirtual 213	java/lang/Integer:toString	()Ljava/lang/String;
    //   663: astore 15
    //   665: aload 12
    //   667: invokevirtual 281	java/lang/Integer:intValue	()I
    //   670: bipush 10
    //   672: if_icmpge +26 -> 698
    //   675: new 186	java/lang/StringBuilder
    //   678: dup
    //   679: ldc_w 353
    //   682: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   685: aload 12
    //   687: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   690: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   693: astore 16
    //   695: goto +43 -> 738
    //   698: aload 12
    //   700: invokevirtual 281	java/lang/Integer:intValue	()I
    //   703: bipush 100
    //   705: if_icmpge +26 -> 731
    //   708: new 186	java/lang/StringBuilder
    //   711: dup
    //   712: ldc_w 351
    //   715: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   718: aload 12
    //   720: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   723: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   726: astore 16
    //   728: goto +10 -> 738
    //   731: aload 12
    //   733: invokevirtual 213	java/lang/Integer:toString	()Ljava/lang/String;
    //   736: astore 16
    //   738: aload 5
    //   740: invokevirtual 188	java/lang/Long:longValue	()J
    //   743: new 186	java/lang/StringBuilder
    //   746: dup
    //   747: aload 13
    //   749: invokestatic 355	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   752: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   755: aload 14
    //   757: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   760: aload 15
    //   762: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   765: aload 16
    //   767: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   770: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   773: invokestatic 358	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   776: ladd
    //   777: invokestatic 166	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   780: astore 5
    //   782: goto +10 -> 792
    //   785: astore 9
    //   787: aload 9
    //   789: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   792: iinc 8 1
    //   795: iload 8
    //   797: aload 4
    //   799: invokeinterface 144 1 0
    //   804: if_icmplt -431 -> 373
    //   807: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   810: new 186	java/lang/StringBuilder
    //   813: dup
    //   814: ldc_w 365
    //   817: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   820: aload 5
    //   822: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   825: ldc_w 367
    //   828: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   831: aload_1
    //   832: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   835: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   838: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   841: iconst_0
    //   842: istore 8
    //   844: iconst_0
    //   845: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   848: astore 9
    //   850: ldc_w 369
    //   853: astore 10
    //   855: lconst_0
    //   856: invokestatic 166	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   859: astore 11
    //   861: iconst_0
    //   862: istore 12
    //   864: goto +141 -> 1005
    //   867: iload 8
    //   869: ifne +133 -> 1002
    //   872: invokestatic 371	com/egouos/util/CaipiaoUtil:caiNumber	()Ljava/lang/String;
    //   875: astore 13
    //   877: aload 13
    //   879: ldc_w 376
    //   882: invokevirtual 378	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   885: iconst_1
    //   886: aaload
    //   887: astore 10
    //   889: aload 13
    //   891: ldc_w 376
    //   894: invokevirtual 378	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   897: iconst_0
    //   898: aaload
    //   899: invokestatic 358	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   902: invokestatic 166	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   905: astore 11
    //   907: aload 5
    //   909: invokevirtual 188	java/lang/Long:longValue	()J
    //   912: aload 10
    //   914: invokestatic 204	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   917: i2l
    //   918: ladd
    //   919: aload_0
    //   920: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   923: invokevirtual 382	com/egouos/pojo/Spellbuyproduct:getSpellbuyPrice	()Ljava/lang/Integer;
    //   926: invokevirtual 281	java/lang/Integer:intValue	()I
    //   929: i2l
    //   930: lrem
    //   931: ldc2_w 385
    //   934: ladd
    //   935: invokestatic 193	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   938: invokestatic 204	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   941: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   944: astore 9
    //   946: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   949: new 186	java/lang/StringBuilder
    //   952: dup
    //   953: ldc_w 387
    //   956: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   959: aload 9
    //   961: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   964: ldc_w 367
    //   967: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   970: aload_0
    //   971: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   974: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   977: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   980: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   983: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   986: iconst_1
    //   987: istore 8
    //   989: goto +13 -> 1002
    //   992: astore 13
    //   994: aload 13
    //   996: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   999: iconst_0
    //   1000: istore 8
    //   1002: iinc 12 1
    //   1005: iload 12
    //   1007: bipush 10
    //   1009: if_icmplt -142 -> 867
    //   1012: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   1015: new 186	java/lang/StringBuilder
    //   1018: dup
    //   1019: ldc_w 389
    //   1022: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1025: aload_0
    //   1026: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1029: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   1032: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1035: ldc_w 391
    //   1038: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1041: aload 9
    //   1043: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1046: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1049: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   1052: checkcast 145	java/util/List
    //   1055: astore 12
    //   1057: aload 12
    //   1059: ifnonnull +72 -> 1131
    //   1062: aload_0
    //   1063: getfield 296	com/egouos/util/LotteryUtil:spellbuyrecordService	Lcom/egouos/service/SpellbuyrecordService;
    //   1066: aload_0
    //   1067: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1070: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   1073: aload 9
    //   1075: invokestatic 355	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   1078: invokeinterface 393 3 0
    //   1083: astore 12
    //   1085: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   1088: new 186	java/lang/StringBuilder
    //   1091: dup
    //   1092: ldc_w 389
    //   1095: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1098: aload_0
    //   1099: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1102: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   1105: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1108: ldc_w 391
    //   1111: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1114: aload 9
    //   1116: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1119: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1122: sipush 1800
    //   1125: aload 12
    //   1127: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   1130: pop
    //   1131: aload_0
    //   1132: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1135: invokevirtual 397	com/egouos/pojo/Spellbuyproduct:getProductPeriod	()Ljava/lang/Integer;
    //   1138: invokevirtual 281	java/lang/Integer:intValue	()I
    //   1141: istore 13
    //   1143: aload_0
    //   1144: aload 12
    //   1146: iconst_0
    //   1147: invokeinterface 304 2 0
    //   1152: checkcast 400	[Ljava/lang/Object;
    //   1155: iconst_1
    //   1156: aaload
    //   1157: checkcast 292	com/egouos/pojo/Spellbuyrecord
    //   1160: putfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   1163: aload_0
    //   1164: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   1167: aload 9
    //   1169: invokestatic 355	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   1172: invokevirtual 402	com/egouos/pojo/Spellbuyrecord:setSpRandomNo	(Ljava/lang/String;)V
    //   1175: aload_0
    //   1176: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   1179: ldc_w 405
    //   1182: invokevirtual 407	com/egouos/pojo/Spellbuyrecord:setSpWinningStatus	(Ljava/lang/String;)V
    //   1185: aload_0
    //   1186: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   1189: ldc_w 405
    //   1192: invokevirtual 410	com/egouos/pojo/Spellbuyrecord:setBuyStatus	(Ljava/lang/String;)V
    //   1195: aload_0
    //   1196: getfield 296	com/egouos/util/LotteryUtil:spellbuyrecordService	Lcom/egouos/service/SpellbuyrecordService;
    //   1199: aload_0
    //   1200: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   1203: invokeinterface 413 2 0
    //   1208: aload_0
    //   1209: aload 12
    //   1211: iconst_0
    //   1212: invokeinterface 304 2 0
    //   1217: checkcast 400	[Ljava/lang/Object;
    //   1220: iconst_2
    //   1221: aaload
    //   1222: checkcast 417	com/egouos/pojo/User
    //   1225: putfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   1228: aload_0
    //   1229: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1232: iconst_1
    //   1233: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1236: invokevirtual 421	com/egouos/pojo/Spellbuyproduct:setSpStatus	(Ljava/lang/Integer;)V
    //   1239: aload_0
    //   1240: getfield 271	com/egouos/util/LotteryUtil:spellbuyproductService	Lcom/egouos/service/SpellbuyproductService;
    //   1243: aload_0
    //   1244: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1247: invokeinterface 425 2 0
    //   1252: goto +10 -> 1262
    //   1255: astore 14
    //   1257: aload 14
    //   1259: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   1262: aload_0
    //   1263: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   1266: new 186	java/lang/StringBuilder
    //   1269: dup
    //   1270: ldc_w 426
    //   1273: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1276: aload_0
    //   1277: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1280: invokevirtual 428	com/egouos/pojo/Spellbuyproduct:getFkProductId	()Ljava/lang/Integer;
    //   1283: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1286: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1289: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   1292: checkcast 431	com/egouos/pojo/Product
    //   1295: putfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1298: aload_0
    //   1299: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1302: ifnonnull +66 -> 1368
    //   1305: aload_0
    //   1306: aload_0
    //   1307: getfield 435	com/egouos/util/LotteryUtil:productService	Lcom/egouos/service/ProductService;
    //   1310: aload_0
    //   1311: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1314: invokevirtual 428	com/egouos/pojo/Spellbuyproduct:getFkProductId	()Ljava/lang/Integer;
    //   1317: invokestatic 355	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   1320: invokeinterface 437 2 0
    //   1325: checkcast 431	com/egouos/pojo/Product
    //   1328: putfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1331: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   1334: new 186	java/lang/StringBuilder
    //   1337: dup
    //   1338: ldc_w 426
    //   1341: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1344: aload_0
    //   1345: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1348: invokevirtual 428	com/egouos/pojo/Spellbuyproduct:getFkProductId	()Ljava/lang/Integer;
    //   1351: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1354: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1357: sipush 1800
    //   1360: aload_0
    //   1361: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1364: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   1367: pop
    //   1368: aload_0
    //   1369: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1372: invokevirtual 440	com/egouos/pojo/Product:getStatus	()Ljava/lang/Integer;
    //   1375: invokevirtual 281	java/lang/Integer:intValue	()I
    //   1378: iconst_1
    //   1379: if_icmpne +268 -> 1647
    //   1382: aload_0
    //   1383: getfield 271	com/egouos/util/LotteryUtil:spellbuyproductService	Lcom/egouos/service/SpellbuyproductService;
    //   1386: aload_0
    //   1387: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1390: invokevirtual 428	com/egouos/pojo/Spellbuyproduct:getFkProductId	()Ljava/lang/Integer;
    //   1393: invokeinterface 443 2 0
    //   1398: astore 14
    //   1400: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   1403: new 186	java/lang/StringBuilder
    //   1406: dup
    //   1407: ldc_w 446
    //   1410: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1413: aload 14
    //   1415: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1418: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1421: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   1424: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   1427: new 186	java/lang/StringBuilder
    //   1430: dup
    //   1431: ldc_w 448
    //   1434: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1437: aload 14
    //   1439: invokeinterface 144 1 0
    //   1444: invokevirtual 450	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1447: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1450: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   1453: aload 14
    //   1455: invokeinterface 144 1 0
    //   1460: ifne +187 -> 1647
    //   1463: new 267	com/egouos/pojo/Spellbuyproduct
    //   1466: dup
    //   1467: invokespecial 453	com/egouos/pojo/Spellbuyproduct:<init>	()V
    //   1470: astore 15
    //   1472: aload 15
    //   1474: aload_0
    //   1475: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1478: invokevirtual 428	com/egouos/pojo/Spellbuyproduct:getFkProductId	()Ljava/lang/Integer;
    //   1481: invokevirtual 454	com/egouos/pojo/Spellbuyproduct:setFkProductId	(Ljava/lang/Integer;)V
    //   1484: aload 15
    //   1486: iinc 13 1
    //   1489: iload 13
    //   1491: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1494: invokevirtual 457	com/egouos/pojo/Spellbuyproduct:setProductPeriod	(Ljava/lang/Integer;)V
    //   1497: aload 15
    //   1499: iconst_0
    //   1500: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1503: invokevirtual 460	com/egouos/pojo/Spellbuyproduct:setSpellbuyCount	(Ljava/lang/Integer;)V
    //   1506: aload 15
    //   1508: iconst_0
    //   1509: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1512: invokevirtual 463	com/egouos/pojo/Spellbuyproduct:setSpellbuyType	(Ljava/lang/Integer;)V
    //   1515: aload 15
    //   1517: new 182	java/util/Date
    //   1520: dup
    //   1521: invokespecial 466	java/util/Date:<init>	()V
    //   1524: invokestatic 467	com/egouos/util/DateUtil:DateTimeToStr	(Ljava/util/Date;)Ljava/lang/String;
    //   1527: invokevirtual 471	com/egouos/pojo/Spellbuyproduct:setSpellbuyEndDate	(Ljava/lang/String;)V
    //   1530: aload 15
    //   1532: aload_0
    //   1533: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1536: invokevirtual 474	com/egouos/pojo/Product:getProductPrice	()Ljava/lang/Integer;
    //   1539: invokevirtual 477	com/egouos/pojo/Spellbuyproduct:setSpellbuyPrice	(Ljava/lang/Integer;)V
    //   1542: aload 15
    //   1544: aload_0
    //   1545: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1548: invokevirtual 480	com/egouos/pojo/Product:getSinglePrice	()Ljava/lang/Integer;
    //   1551: invokevirtual 483	com/egouos/pojo/Spellbuyproduct:setSpSinglePrice	(Ljava/lang/Integer;)V
    //   1554: aload 15
    //   1556: new 182	java/util/Date
    //   1559: dup
    //   1560: invokespecial 466	java/util/Date:<init>	()V
    //   1563: invokestatic 467	com/egouos/util/DateUtil:DateTimeToStr	(Ljava/util/Date;)Ljava/lang/String;
    //   1566: invokevirtual 486	com/egouos/pojo/Spellbuyproduct:setSpellbuyStartDate	(Ljava/lang/String;)V
    //   1569: aload 15
    //   1571: iconst_0
    //   1572: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1575: invokevirtual 421	com/egouos/pojo/Spellbuyproduct:setSpStatus	(Ljava/lang/Integer;)V
    //   1578: aload_0
    //   1579: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1582: invokevirtual 489	com/egouos/pojo/Product:getAttribute71	()Ljava/lang/String;
    //   1585: ldc_w 492
    //   1588: invokevirtual 336	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1591: ifeq +16 -> 1607
    //   1594: aload 15
    //   1596: bipush 8
    //   1598: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1601: invokevirtual 463	com/egouos/pojo/Spellbuyproduct:setSpellbuyType	(Ljava/lang/Integer;)V
    //   1604: goto +12 -> 1616
    //   1607: aload 15
    //   1609: iconst_0
    //   1610: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1613: invokevirtual 463	com/egouos/pojo/Spellbuyproduct:setSpellbuyType	(Ljava/lang/Integer;)V
    //   1616: aload_0
    //   1617: getfield 271	com/egouos/util/LotteryUtil:spellbuyproductService	Lcom/egouos/service/SpellbuyproductService;
    //   1620: aload 15
    //   1622: invokeinterface 425 2 0
    //   1627: goto +20 -> 1647
    //   1630: astore 15
    //   1632: aload 15
    //   1634: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   1637: goto +10 -> 1647
    //   1640: astore 14
    //   1642: aload 14
    //   1644: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   1647: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   1650: new 186	java/lang/StringBuilder
    //   1653: dup
    //   1654: ldc_w 494
    //   1657: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1660: aload_0
    //   1661: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1664: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   1667: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1670: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1673: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   1676: checkcast 145	java/util/List
    //   1679: astore 14
    //   1681: aload 14
    //   1683: ifnonnull +1658 -> 3341
    //   1686: aload_0
    //   1687: getfield 496	com/egouos/util/LotteryUtil:latestlotteryService	Lcom/egouos/service/LatestlotteryService;
    //   1690: aload_0
    //   1691: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1694: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   1697: invokeinterface 498 2 0
    //   1702: astore 14
    //   1704: aload 14
    //   1706: invokeinterface 144 1 0
    //   1711: ifne +1630 -> 3341
    //   1714: aload_0
    //   1715: new 503	com/egouos/pojo/Latestlottery
    //   1718: dup
    //   1719: invokespecial 505	com/egouos/pojo/Latestlottery:<init>	()V
    //   1722: putfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1725: aload_0
    //   1726: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1729: aload_0
    //   1730: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1733: invokevirtual 428	com/egouos/pojo/Spellbuyproduct:getFkProductId	()Ljava/lang/Integer;
    //   1736: invokevirtual 508	com/egouos/pojo/Latestlottery:setProductId	(Ljava/lang/Integer;)V
    //   1739: aload_0
    //   1740: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1743: aload_0
    //   1744: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1747: invokevirtual 511	com/egouos/pojo/Product:getProductName	()Ljava/lang/String;
    //   1750: invokevirtual 514	com/egouos/pojo/Latestlottery:setProductName	(Ljava/lang/String;)V
    //   1753: aload_0
    //   1754: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1757: aload_0
    //   1758: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1761: invokevirtual 517	com/egouos/pojo/Product:getProductTitle	()Ljava/lang/String;
    //   1764: invokevirtual 520	com/egouos/pojo/Latestlottery:setProductTitle	(Ljava/lang/String;)V
    //   1767: aload_0
    //   1768: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1771: aload_0
    //   1772: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1775: invokevirtual 382	com/egouos/pojo/Spellbuyproduct:getSpellbuyPrice	()Ljava/lang/Integer;
    //   1778: invokevirtual 523	com/egouos/pojo/Latestlottery:setProductPrice	(Ljava/lang/Integer;)V
    //   1781: aload_0
    //   1782: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1785: aload_0
    //   1786: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   1789: invokevirtual 526	com/egouos/pojo/Product:getHeadImage	()Ljava/lang/String;
    //   1792: invokevirtual 529	com/egouos/pojo/Latestlottery:setProductImg	(Ljava/lang/String;)V
    //   1795: aload_0
    //   1796: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1799: aload_0
    //   1800: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1803: invokevirtual 397	com/egouos/pojo/Spellbuyproduct:getProductPeriod	()Ljava/lang/Integer;
    //   1806: invokevirtual 532	com/egouos/pojo/Latestlottery:setProductPeriod	(Ljava/lang/Integer;)V
    //   1809: aload_0
    //   1810: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1813: aload_3
    //   1814: invokevirtual 533	com/egouos/pojo/Latestlottery:setAnnouncedTime	(Ljava/lang/String;)V
    //   1817: aload_0
    //   1818: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1821: aload_0
    //   1822: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1825: invokevirtual 536	com/egouos/pojo/Spellbuyproduct:getSpellbuyType	()Ljava/lang/Integer;
    //   1828: invokevirtual 539	com/egouos/pojo/Latestlottery:setAnnouncedType	(Ljava/lang/Integer;)V
    //   1831: aload_0
    //   1832: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1835: aload 5
    //   1837: invokevirtual 542	com/egouos/pojo/Latestlottery:setDateSum	(Ljava/lang/Long;)V
    //   1840: aload_0
    //   1841: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1844: aload 10
    //   1846: invokevirtual 546	com/egouos/pojo/Latestlottery:setSscNumber	(Ljava/lang/String;)V
    //   1849: aload_0
    //   1850: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1853: aload 11
    //   1855: invokevirtual 549	com/egouos/pojo/Latestlottery:setSscPeriod	(Ljava/lang/Long;)V
    //   1858: aload_0
    //   1859: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1862: aload_0
    //   1863: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   1866: invokevirtual 307	com/egouos/pojo/Spellbuyrecord:getBuyDate	()Ljava/lang/String;
    //   1869: invokevirtual 552	com/egouos/pojo/Latestlottery:setBuyTime	(Ljava/lang/String;)V
    //   1872: aload_0
    //   1873: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1876: aload_0
    //   1877: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   1880: invokevirtual 555	com/egouos/pojo/Spellbuyrecord:getSpellbuyRecordId	()Ljava/lang/Integer;
    //   1883: invokevirtual 558	com/egouos/pojo/Latestlottery:setSpellbuyRecordId	(Ljava/lang/Integer;)V
    //   1886: aload_0
    //   1887: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1890: aload_0
    //   1891: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   1894: invokevirtual 333	com/egouos/pojo/Spellbuyrecord:getFkSpellbuyProductId	()Ljava/lang/Integer;
    //   1897: invokevirtual 561	com/egouos/pojo/Latestlottery:setSpellbuyProductId	(Ljava/lang/Integer;)V
    //   1900: aload_0
    //   1901: getfield 564	com/egouos/util/LotteryUtil:randomnumberService	Lcom/egouos/service/RandomnumberService;
    //   1904: aload_0
    //   1905: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   1908: invokevirtual 566	com/egouos/pojo/User:getUserId	()Ljava/lang/Integer;
    //   1911: invokestatic 355	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   1914: aload_0
    //   1915: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   1918: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   1921: invokeinterface 569 3 0
    //   1926: astore 15
    //   1928: aload_0
    //   1929: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1932: aload 15
    //   1934: invokestatic 355	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   1937: invokestatic 204	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   1940: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1943: invokevirtual 575	com/egouos/pojo/Latestlottery:setBuyNumberCount	(Ljava/lang/Integer;)V
    //   1946: aload_0
    //   1947: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1950: aload 9
    //   1952: invokevirtual 578	com/egouos/pojo/Latestlottery:setRandomNumber	(Ljava/lang/Integer;)V
    //   1955: aload_0
    //   1956: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1959: aload_0
    //   1960: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   1963: invokevirtual 581	com/egouos/pojo/User:getIpLocation	()Ljava/lang/String;
    //   1966: invokevirtual 584	com/egouos/pojo/Latestlottery:setLocation	(Ljava/lang/String;)V
    //   1969: aload_0
    //   1970: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1973: aload_0
    //   1974: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   1977: invokevirtual 566	com/egouos/pojo/User:getUserId	()Ljava/lang/Integer;
    //   1980: invokevirtual 587	com/egouos/pojo/Latestlottery:setUserId	(Ljava/lang/Integer;)V
    //   1983: aload_0
    //   1984: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   1987: aload_0
    //   1988: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   1991: invokestatic 590	com/egouos/util/UserNameUtil:userName	(Lcom/egouos/pojo/User;)Ljava/lang/String;
    //   1994: invokevirtual 596	com/egouos/pojo/Latestlottery:setUserName	(Ljava/lang/String;)V
    //   1997: aload_0
    //   1998: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   2001: aload_0
    //   2002: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2005: invokevirtual 599	com/egouos/pojo/User:getFaceImg	()Ljava/lang/String;
    //   2008: invokevirtual 602	com/egouos/pojo/Latestlottery:setUserFace	(Ljava/lang/String;)V
    //   2011: aload_0
    //   2012: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   2015: iconst_1
    //   2016: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   2019: invokevirtual 605	com/egouos/pojo/Latestlottery:setStatus	(Ljava/lang/Integer;)V
    //   2022: aload_0
    //   2023: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   2026: iconst_m1
    //   2027: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   2030: invokevirtual 608	com/egouos/pojo/Latestlottery:setShareStatus	(Ljava/lang/Integer;)V
    //   2033: aload_0
    //   2034: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   2037: aconst_null
    //   2038: invokevirtual 611	com/egouos/pojo/Latestlottery:setShareId	(Ljava/lang/Integer;)V
    //   2041: aload_0
    //   2042: getfield 496	com/egouos/util/LotteryUtil:latestlotteryService	Lcom/egouos/service/LatestlotteryService;
    //   2045: aload_0
    //   2046: getfield 506	com/egouos/util/LotteryUtil:latestlottery	Lcom/egouos/pojo/Latestlottery;
    //   2049: invokeinterface 614 2 0
    //   2054: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   2057: new 186	java/lang/StringBuilder
    //   2060: dup
    //   2061: ldc_w 494
    //   2064: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2067: aload_0
    //   2068: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   2071: invokevirtual 286	com/egouos/pojo/Spellbuyproduct:getSpellbuyProductId	()Ljava/lang/Integer;
    //   2074: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2077: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2080: sipush 1800
    //   2083: aload 14
    //   2085: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   2088: pop
    //   2089: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   2092: new 186	java/lang/StringBuilder
    //   2095: dup
    //   2096: ldc_w 615
    //   2099: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2102: aload_1
    //   2103: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2106: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2109: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   2112: checkcast 160	com/egouos/pojo/Lotteryproductutil
    //   2115: astore 16
    //   2117: aload 16
    //   2119: ifnonnull +64 -> 2183
    //   2122: aload_0
    //   2123: getfield 134	com/egouos/util/LotteryUtil:lotteryproductutilService	Lcom/egouos/service/LotteryproductutilService;
    //   2126: aload_1
    //   2127: invokeinterface 617 2 0
    //   2132: checkcast 160	com/egouos/pojo/Lotteryproductutil
    //   2135: astore 16
    //   2137: aload 16
    //   2139: ifnull +17 -> 2156
    //   2142: aload_0
    //   2143: getfield 134	com/egouos/util/LotteryUtil:lotteryproductutilService	Lcom/egouos/service/LotteryproductutilService;
    //   2146: aload 16
    //   2148: invokevirtual 618	com/egouos/pojo/Lotteryproductutil:getLotteryId	()Ljava/lang/Integer;
    //   2151: invokeinterface 621 2 0
    //   2156: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   2159: new 186	java/lang/StringBuilder
    //   2162: dup
    //   2163: ldc_w 615
    //   2166: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2169: aload_1
    //   2170: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2173: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2176: iconst_5
    //   2177: aload 16
    //   2179: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   2182: pop
    //   2183: aload_0
    //   2184: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2187: invokevirtual 624	com/egouos/pojo/User:getMailCheck	()Ljava/lang/String;
    //   2190: ldc_w 351
    //   2193: invokevirtual 336	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2196: ifeq +764 -> 2960
    //   2199: new 186	java/lang/StringBuilder
    //   2202: dup
    //   2203: ldc_w 627
    //   2206: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2209: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2212: invokevirtual 629	com/egouos/pojo/SysConfigure:getSaitName	()Ljava/lang/String;
    //   2215: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2218: ldc_w 632
    //   2221: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2224: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2227: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2230: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2233: ldc_w 637
    //   2236: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2239: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2242: invokevirtual 639	com/egouos/pojo/SysConfigure:getImgUrl	()Ljava/lang/String;
    //   2245: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2248: ldc_w 642
    //   2251: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2254: ldc_w 644
    //   2257: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2260: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2263: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2266: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2269: ldc_w 646
    //   2272: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2275: ldc_w 648
    //   2278: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2281: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2284: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2287: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2290: ldc_w 650
    //   2293: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2296: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2299: invokevirtual 629	com/egouos/pojo/SysConfigure:getSaitName	()Ljava/lang/String;
    //   2302: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2305: ldc_w 652
    //   2308: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2311: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2314: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2317: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2320: ldc_w 654
    //   2323: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2326: ldc_w 656
    //   2329: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2332: ldc_w 658
    //   2335: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2338: ldc_w 660
    //   2341: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2344: ldc_w 662
    //   2347: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2350: ldc_w 664
    //   2353: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2356: aload_0
    //   2357: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2360: invokestatic 590	com/egouos/util/UserNameUtil:userName	(Lcom/egouos/pojo/User;)Ljava/lang/String;
    //   2363: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2366: ldc_w 666
    //   2369: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2372: aload_0
    //   2373: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2376: invokestatic 590	com/egouos/util/UserNameUtil:userName	(Lcom/egouos/pojo/User;)Ljava/lang/String;
    //   2379: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2382: ldc_w 668
    //   2385: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2388: ldc_w 656
    //   2391: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2394: ldc_w 670
    //   2397: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2400: ldc_w 672
    //   2403: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2406: aload_0
    //   2407: getfield 269	com/egouos/util/LotteryUtil:spellbuyproduct	Lcom/egouos/pojo/Spellbuyproduct;
    //   2410: invokevirtual 397	com/egouos/pojo/Spellbuyproduct:getProductPeriod	()Ljava/lang/Integer;
    //   2413: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2416: ldc_w 674
    //   2419: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2422: aload_0
    //   2423: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   2426: invokevirtual 517	com/egouos/pojo/Product:getProductTitle	()Ljava/lang/String;
    //   2429: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2432: ldc_w 632
    //   2435: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2438: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2441: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2444: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2447: ldc_w 676
    //   2450: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2453: aload_0
    //   2454: getfield 294	com/egouos/util/LotteryUtil:spellbuyrecord	Lcom/egouos/pojo/Spellbuyrecord;
    //   2457: invokevirtual 333	com/egouos/pojo/Spellbuyrecord:getFkSpellbuyProductId	()Ljava/lang/Integer;
    //   2460: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2463: ldc_w 678
    //   2466: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2469: aload_0
    //   2470: getfield 433	com/egouos/util/LotteryUtil:product	Lcom/egouos/pojo/Product;
    //   2473: invokevirtual 511	com/egouos/pojo/Product:getProductName	()Ljava/lang/String;
    //   2476: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2479: ldc_w 680
    //   2482: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2485: ldc_w 656
    //   2488: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2491: ldc_w 670
    //   2494: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2497: ldc_w 682
    //   2500: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2503: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2506: invokevirtual 629	com/egouos/pojo/SysConfigure:getSaitName	()Ljava/lang/String;
    //   2509: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2512: ldc_w 684
    //   2515: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2518: ldc_w 656
    //   2521: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2524: ldc_w 670
    //   2527: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2530: ldc_w 686
    //   2533: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2536: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2539: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2542: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2545: ldc_w 688
    //   2548: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2551: ldc_w 656
    //   2554: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2557: ldc_w 670
    //   2560: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2563: ldc_w 690
    //   2566: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2569: ldc_w 656
    //   2572: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2575: ldc_w 670
    //   2578: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2581: ldc_w 692
    //   2584: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2587: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2590: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2593: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2596: ldc_w 694
    //   2599: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2602: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2605: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2608: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2611: ldc_w 696
    //   2614: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2617: ldc_w 656
    //   2620: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2623: ldc_w 658
    //   2626: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2629: ldc_w 698
    //   2632: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2635: ldc_w 662
    //   2638: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2641: ldc_w 700
    //   2644: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2647: ldc_w 656
    //   2650: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2653: ldc_w 670
    //   2656: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2659: ldc_w 702
    //   2662: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2665: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2668: invokevirtual 629	com/egouos/pojo/SysConfigure:getSaitName	()Ljava/lang/String;
    //   2671: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2674: ldc_w 704
    //   2677: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2680: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2683: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2686: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2689: ldc_w 706
    //   2692: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2695: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2698: invokevirtual 634	com/egouos/pojo/SysConfigure:getWwwUrl	()Ljava/lang/String;
    //   2701: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2704: ldc_w 708
    //   2707: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2710: ldc_w 656
    //   2713: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2716: ldc_w 670
    //   2719: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2722: ldc_w 710
    //   2725: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2728: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2731: invokevirtual 712	com/egouos/pojo/SysConfigure:getServiceTel	()Ljava/lang/String;
    //   2734: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2737: ldc_w 715
    //   2740: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2743: ldc_w 656
    //   2746: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2749: ldc_w 658
    //   2752: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2755: ldc_w 717
    //   2758: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2761: ldc_w 656
    //   2764: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2767: ldc_w 658
    //   2770: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2773: ldc_w 719
    //   2776: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2779: ldc_w 662
    //   2782: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2785: ldc_w 721
    //   2788: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2791: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2794: invokevirtual 723	com/egouos/pojo/SysConfigure:getIcp	()Ljava/lang/String;
    //   2797: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2800: ldc_w 717
    //   2803: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2806: ldc_w 656
    //   2809: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2812: ldc_w 658
    //   2815: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2818: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2821: astore 17
    //   2823: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2826: invokevirtual 726	com/egouos/pojo/SysConfigure:getMailName	()Ljava/lang/String;
    //   2829: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2832: invokevirtual 729	com/egouos/pojo/SysConfigure:getMailPwd	()Ljava/lang/String;
    //   2835: aload_0
    //   2836: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2839: invokevirtual 732	com/egouos/pojo/User:getMail	()Ljava/lang/String;
    //   2842: new 186	java/lang/StringBuilder
    //   2845: dup
    //   2846: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2849: invokevirtual 629	com/egouos/pojo/SysConfigure:getSaitName	()Ljava/lang/String;
    //   2852: invokestatic 355	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   2855: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2858: ldc_w 735
    //   2861: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2864: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2867: aload 17
    //   2869: invokestatic 737	com/egouos/util/EmailUtil:sendEmail	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   2872: istore 18
    //   2874: iload 18
    //   2876: ifeq +35 -> 2911
    //   2879: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   2882: new 186	java/lang/StringBuilder
    //   2885: dup
    //   2886: ldc_w 743
    //   2889: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2892: aload_0
    //   2893: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2896: invokevirtual 732	com/egouos/pojo/User:getMail	()Ljava/lang/String;
    //   2899: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2902: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2905: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   2908: goto +52 -> 2960
    //   2911: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   2914: new 186	java/lang/StringBuilder
    //   2917: dup
    //   2918: ldc_w 745
    //   2921: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2924: aload_0
    //   2925: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2928: invokevirtual 732	com/egouos/pojo/User:getMail	()Ljava/lang/String;
    //   2931: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2934: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2937: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   2940: goto +20 -> 2960
    //   2943: astore 18
    //   2945: aload 18
    //   2947: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   2950: ldc_w 747
    //   2953: iconst_0
    //   2954: anewarray 111	java/lang/String
    //   2957: invokestatic 749	com/egouos/util/Struts2Utils:renderText	(Ljava/lang/String;[Ljava/lang/String;)V
    //   2960: aload_0
    //   2961: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2964: invokevirtual 755	com/egouos/pojo/User:getMobileCheck	()Ljava/lang/String;
    //   2967: ldc_w 351
    //   2970: invokevirtual 336	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2973: ifeq +368 -> 3341
    //   2976: aload_0
    //   2977: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   2980: invokevirtual 758	com/egouos/pojo/User:getPhone	()Ljava/lang/String;
    //   2983: new 186	java/lang/StringBuilder
    //   2986: dup
    //   2987: ldc_w 761
    //   2990: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2993: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   2996: invokevirtual 629	com/egouos/pojo/SysConfigure:getSaitName	()Ljava/lang/String;
    //   2999: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3002: ldc_w 763
    //   3005: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3008: getstatic 68	com/egouos/util/ApplicationListenerImpl:sysConfigureJson	Lcom/egouos/pojo/SysConfigure;
    //   3011: invokevirtual 712	com/egouos/pojo/SysConfigure:getServiceTel	()Ljava/lang/String;
    //   3014: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3017: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3020: invokestatic 765	com/egouos/util/Sampler:sendOnce	(Ljava/lang/String;Ljava/lang/String;)Ljava/util/List;
    //   3023: astore 17
    //   3025: aload 17
    //   3027: ifnull +314 -> 3341
    //   3030: aload 17
    //   3032: invokeinterface 150 1 0
    //   3037: astore 19
    //   3039: goto +54 -> 3093
    //   3042: aload 19
    //   3044: invokeinterface 154 1 0
    //   3049: checkcast 771	com/shcm/bean/SendResultBean
    //   3052: astore 18
    //   3054: aload 18
    //   3056: invokevirtual 773	com/shcm/bean/SendResultBean:getResult	()I
    //   3059: iconst_1
    //   3060: if_icmpge +33 -> 3093
    //   3063: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   3066: new 186	java/lang/StringBuilder
    //   3069: dup
    //   3070: ldc_w 776
    //   3073: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   3076: aload_0
    //   3077: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   3080: invokevirtual 758	com/egouos/pojo/User:getPhone	()Ljava/lang/String;
    //   3083: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3086: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3089: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   3092: return
    //   3093: aload 19
    //   3095: invokeinterface 217 1 0
    //   3100: ifne -58 -> 3042
    //   3103: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   3106: new 186	java/lang/StringBuilder
    //   3109: dup
    //   3110: ldc_w 778
    //   3113: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   3116: aload_0
    //   3117: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   3120: invokevirtual 758	com/egouos/pojo/User:getPhone	()Ljava/lang/String;
    //   3123: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3126: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3129: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   3132: goto +209 -> 3341
    //   3135: astore 17
    //   3137: aload 17
    //   3139: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   3142: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   3145: new 186	java/lang/StringBuilder
    //   3148: dup
    //   3149: ldc_w 780
    //   3152: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   3155: aload_0
    //   3156: getfield 419	com/egouos/util/LotteryUtil:user	Lcom/egouos/pojo/User;
    //   3159: invokevirtual 758	com/egouos/pojo/User:getPhone	()Ljava/lang/String;
    //   3162: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3165: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3168: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   3171: goto +170 -> 3341
    //   3174: astore 17
    //   3176: aload 17
    //   3178: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   3181: getstatic 120	java/lang/System:err	Ljava/io/PrintStream;
    //   3184: ldc_w 782
    //   3187: invokevirtual 128	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   3190: goto +151 -> 3341
    //   3193: astore 15
    //   3195: aload 15
    //   3197: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   3200: goto +141 -> 3341
    //   3203: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   3206: new 186	java/lang/StringBuilder
    //   3209: dup
    //   3210: ldc_w 784
    //   3213: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   3216: aload_1
    //   3217: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3220: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3223: invokevirtual 250	net/spy/memcached/MemcachedClient:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   3226: checkcast 145	java/util/List
    //   3229: astore_3
    //   3230: aload_3
    //   3231: ifnonnull +110 -> 3341
    //   3234: aload_0
    //   3235: getfield 496	com/egouos/util/LotteryUtil:latestlotteryService	Lcom/egouos/service/LatestlotteryService;
    //   3238: aload_1
    //   3239: invokestatic 204	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   3242: invokestatic 346	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   3245: invokeinterface 498 2 0
    //   3250: astore_3
    //   3251: aload_3
    //   3252: invokeinterface 144 1 0
    //   3257: ifle +76 -> 3333
    //   3260: aload_0
    //   3261: getfield 134	com/egouos/util/LotteryUtil:lotteryproductutilService	Lcom/egouos/service/LotteryproductutilService;
    //   3264: aload_1
    //   3265: invokeinterface 617 2 0
    //   3270: checkcast 160	com/egouos/pojo/Lotteryproductutil
    //   3273: astore 4
    //   3275: aload 4
    //   3277: ifnull +27 -> 3304
    //   3280: aload_0
    //   3281: getfield 134	com/egouos/util/LotteryUtil:lotteryproductutilService	Lcom/egouos/service/LotteryproductutilService;
    //   3284: aload 4
    //   3286: invokevirtual 618	com/egouos/pojo/Lotteryproductutil:getLotteryId	()Ljava/lang/Integer;
    //   3289: invokeinterface 621 2 0
    //   3294: goto +10 -> 3304
    //   3297: astore 4
    //   3299: aload 4
    //   3301: invokevirtual 362	java/lang/Exception:printStackTrace	()V
    //   3304: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   3307: new 186	java/lang/StringBuilder
    //   3310: dup
    //   3311: ldc_w 784
    //   3314: invokespecial 196	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   3317: aload_1
    //   3318: invokevirtual 263	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3321: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3324: iconst_5
    //   3325: aload_3
    //   3326: invokevirtual 257	net/spy/memcached/MemcachedClient:set	(Ljava/lang/String;ILjava/lang/Object;)Lnet/spy/memcached/internal/OperationFuture;
    //   3329: pop
    //   3330: goto +11 -> 3341
    //   3333: invokestatic 244	com/egouos/util/AliyunOcsSampleHelp:getIMemcachedCache	()Lnet/spy/memcached/MemcachedClient;
    //   3336: aload_2
    //   3337: invokevirtual 786	net/spy/memcached/MemcachedClient:delete	(Ljava/lang/String;)Lnet/spy/memcached/internal/OperationFuture;
    //   3340: pop
    //   3341: return
    // Line number table:
    //   Java source line #105	-> byte code offset #0
    //   Java source line #106	-> byte code offset #5
    //   Java source line #107	-> byte code offset #15
    //   Java source line #108	-> byte code offset #26
    //   Java source line #109	-> byte code offset #56
    //   Java source line #110	-> byte code offset #63
    //   Java source line #111	-> byte code offset #80
    //   Java source line #114	-> byte code offset #109
    //   Java source line #118	-> byte code offset #123
    //   Java source line #119	-> byte code offset #159
    //   Java source line #120	-> byte code offset #166
    //   Java source line #121	-> byte code offset #195
    //   Java source line #124	-> byte code offset #232
    //   Java source line #125	-> byte code offset #240
    //   Java source line #126	-> byte code offset #274
    //   Java source line #127	-> byte code offset #279
    //   Java source line #128	-> byte code offset #294
    //   Java source line #131	-> byte code offset #329
    //   Java source line #137	-> byte code offset #342
    //   Java source line #138	-> byte code offset #348
    //   Java source line #139	-> byte code offset #364
    //   Java source line #140	-> byte code offset #367
    //   Java source line #142	-> byte code offset #373
    //   Java source line #144	-> byte code offset #378
    //   Java source line #145	-> byte code offset #420
    //   Java source line #148	-> byte code offset #423
    //   Java source line #149	-> byte code offset #433
    //   Java source line #151	-> byte code offset #436
    //   Java source line #153	-> byte code offset #452
    //   Java source line #157	-> byte code offset #469
    //   Java source line #158	-> byte code offset #483
    //   Java source line #159	-> byte code offset #497
    //   Java source line #160	-> byte code offset #511
    //   Java source line #161	-> byte code offset #525
    //   Java source line #162	-> byte code offset #530
    //   Java source line #163	-> byte code offset #535
    //   Java source line #164	-> byte code offset #540
    //   Java source line #165	-> byte code offset #545
    //   Java source line #166	-> byte code offset #555
    //   Java source line #168	-> byte code offset #578
    //   Java source line #170	-> byte code offset #585
    //   Java source line #171	-> byte code offset #595
    //   Java source line #173	-> byte code offset #618
    //   Java source line #175	-> byte code offset #625
    //   Java source line #176	-> byte code offset #635
    //   Java source line #178	-> byte code offset #658
    //   Java source line #180	-> byte code offset #665
    //   Java source line #181	-> byte code offset #675
    //   Java source line #182	-> byte code offset #698
    //   Java source line #183	-> byte code offset #708
    //   Java source line #185	-> byte code offset #731
    //   Java source line #188	-> byte code offset #738
    //   Java source line #191	-> byte code offset #785
    //   Java source line #192	-> byte code offset #787
    //   Java source line #140	-> byte code offset #792
    //   Java source line #195	-> byte code offset #807
    //   Java source line #199	-> byte code offset #841
    //   Java source line #200	-> byte code offset #844
    //   Java source line #201	-> byte code offset #850
    //   Java source line #202	-> byte code offset #855
    //   Java source line #203	-> byte code offset #861
    //   Java source line #204	-> byte code offset #867
    //   Java source line #206	-> byte code offset #872
    //   Java source line #207	-> byte code offset #877
    //   Java source line #208	-> byte code offset #889
    //   Java source line #209	-> byte code offset #907
    //   Java source line #210	-> byte code offset #946
    //   Java source line #211	-> byte code offset #986
    //   Java source line #212	-> byte code offset #992
    //   Java source line #213	-> byte code offset #994
    //   Java source line #214	-> byte code offset #999
    //   Java source line #203	-> byte code offset #1002
    //   Java source line #267	-> byte code offset #1012
    //   Java source line #268	-> byte code offset #1057
    //   Java source line #269	-> byte code offset #1062
    //   Java source line #270	-> byte code offset #1085
    //   Java source line #272	-> byte code offset #1131
    //   Java source line #274	-> byte code offset #1143
    //   Java source line #275	-> byte code offset #1163
    //   Java source line #276	-> byte code offset #1175
    //   Java source line #277	-> byte code offset #1185
    //   Java source line #278	-> byte code offset #1195
    //   Java source line #279	-> byte code offset #1208
    //   Java source line #281	-> byte code offset #1228
    //   Java source line #282	-> byte code offset #1239
    //   Java source line #283	-> byte code offset #1255
    //   Java source line #284	-> byte code offset #1257
    //   Java source line #287	-> byte code offset #1262
    //   Java source line #288	-> byte code offset #1298
    //   Java source line #289	-> byte code offset #1305
    //   Java source line #290	-> byte code offset #1331
    //   Java source line #292	-> byte code offset #1368
    //   Java source line #294	-> byte code offset #1382
    //   Java source line #295	-> byte code offset #1400
    //   Java source line #296	-> byte code offset #1424
    //   Java source line #297	-> byte code offset #1453
    //   Java source line #299	-> byte code offset #1463
    //   Java source line #300	-> byte code offset #1472
    //   Java source line #301	-> byte code offset #1484
    //   Java source line #302	-> byte code offset #1497
    //   Java source line #303	-> byte code offset #1506
    //   Java source line #304	-> byte code offset #1515
    //   Java source line #305	-> byte code offset #1530
    //   Java source line #306	-> byte code offset #1542
    //   Java source line #307	-> byte code offset #1554
    //   Java source line #308	-> byte code offset #1569
    //   Java source line #309	-> byte code offset #1578
    //   Java source line #310	-> byte code offset #1594
    //   Java source line #312	-> byte code offset #1607
    //   Java source line #314	-> byte code offset #1616
    //   Java source line #315	-> byte code offset #1630
    //   Java source line #316	-> byte code offset #1632
    //   Java source line #319	-> byte code offset #1640
    //   Java source line #320	-> byte code offset #1642
    //   Java source line #325	-> byte code offset #1647
    //   Java source line #326	-> byte code offset #1681
    //   Java source line #327	-> byte code offset #1686
    //   Java source line #328	-> byte code offset #1704
    //   Java source line #330	-> byte code offset #1714
    //   Java source line #331	-> byte code offset #1725
    //   Java source line #332	-> byte code offset #1739
    //   Java source line #333	-> byte code offset #1753
    //   Java source line #334	-> byte code offset #1767
    //   Java source line #335	-> byte code offset #1781
    //   Java source line #336	-> byte code offset #1795
    //   Java source line #338	-> byte code offset #1809
    //   Java source line #339	-> byte code offset #1817
    //   Java source line #340	-> byte code offset #1831
    //   Java source line #341	-> byte code offset #1840
    //   Java source line #342	-> byte code offset #1849
    //   Java source line #343	-> byte code offset #1858
    //   Java source line #344	-> byte code offset #1872
    //   Java source line #345	-> byte code offset #1886
    //   Java source line #346	-> byte code offset #1900
    //   Java source line #347	-> byte code offset #1928
    //   Java source line #348	-> byte code offset #1946
    //   Java source line #349	-> byte code offset #1955
    //   Java source line #350	-> byte code offset #1969
    //   Java source line #351	-> byte code offset #1983
    //   Java source line #352	-> byte code offset #1997
    //   Java source line #353	-> byte code offset #2011
    //   Java source line #354	-> byte code offset #2022
    //   Java source line #355	-> byte code offset #2033
    //   Java source line #356	-> byte code offset #2041
    //   Java source line #357	-> byte code offset #2054
    //   Java source line #359	-> byte code offset #2089
    //   Java source line #360	-> byte code offset #2117
    //   Java source line #361	-> byte code offset #2122
    //   Java source line #362	-> byte code offset #2137
    //   Java source line #363	-> byte code offset #2142
    //   Java source line #365	-> byte code offset #2156
    //   Java source line #372	-> byte code offset #2183
    //   Java source line #373	-> byte code offset #2199
    //   Java source line #378	-> byte code offset #2209
    //   Java source line #379	-> byte code offset #2254
    //   Java source line #380	-> byte code offset #2275
    //   Java source line #381	-> byte code offset #2326
    //   Java source line #382	-> byte code offset #2332
    //   Java source line #383	-> byte code offset #2338
    //   Java source line #384	-> byte code offset #2344
    //   Java source line #385	-> byte code offset #2350
    //   Java source line #386	-> byte code offset #2388
    //   Java source line #387	-> byte code offset #2394
    //   Java source line #388	-> byte code offset #2400
    //   Java source line #389	-> byte code offset #2485
    //   Java source line #390	-> byte code offset #2491
    //   Java source line #391	-> byte code offset #2497
    //   Java source line #392	-> byte code offset #2518
    //   Java source line #393	-> byte code offset #2524
    //   Java source line #394	-> byte code offset #2530
    //   Java source line #395	-> byte code offset #2551
    //   Java source line #396	-> byte code offset #2557
    //   Java source line #397	-> byte code offset #2563
    //   Java source line #398	-> byte code offset #2569
    //   Java source line #399	-> byte code offset #2575
    //   Java source line #400	-> byte code offset #2581
    //   Java source line #401	-> byte code offset #2617
    //   Java source line #402	-> byte code offset #2623
    //   Java source line #403	-> byte code offset #2629
    //   Java source line #404	-> byte code offset #2635
    //   Java source line #405	-> byte code offset #2641
    //   Java source line #406	-> byte code offset #2647
    //   Java source line #407	-> byte code offset #2653
    //   Java source line #408	-> byte code offset #2659
    //   Java source line #409	-> byte code offset #2710
    //   Java source line #410	-> byte code offset #2716
    //   Java source line #411	-> byte code offset #2722
    //   Java source line #412	-> byte code offset #2743
    //   Java source line #413	-> byte code offset #2749
    //   Java source line #414	-> byte code offset #2755
    //   Java source line #415	-> byte code offset #2761
    //   Java source line #416	-> byte code offset #2767
    //   Java source line #417	-> byte code offset #2773
    //   Java source line #418	-> byte code offset #2779
    //   Java source line #419	-> byte code offset #2785
    //   Java source line #420	-> byte code offset #2806
    //   Java source line #421	-> byte code offset #2812
    //   Java source line #373	-> byte code offset #2818
    //   Java source line #423	-> byte code offset #2823
    //   Java source line #424	-> byte code offset #2874
    //   Java source line #425	-> byte code offset #2879
    //   Java source line #427	-> byte code offset #2911
    //   Java source line #429	-> byte code offset #2943
    //   Java source line #430	-> byte code offset #2945
    //   Java source line #431	-> byte code offset #2950
    //   Java source line #434	-> byte code offset #2960
    //   Java source line #436	-> byte code offset #2976
    //   Java source line #437	-> byte code offset #3025
    //   Java source line #438	-> byte code offset #3030
    //   Java source line #439	-> byte code offset #3054
    //   Java source line #440	-> byte code offset #3063
    //   Java source line #441	-> byte code offset #3092
    //   Java source line #438	-> byte code offset #3093
    //   Java source line #444	-> byte code offset #3103
    //   Java source line #446	-> byte code offset #3135
    //   Java source line #447	-> byte code offset #3137
    //   Java source line #448	-> byte code offset #3142
    //   Java source line #451	-> byte code offset #3174
    //   Java source line #452	-> byte code offset #3176
    //   Java source line #453	-> byte code offset #3181
    //   Java source line #455	-> byte code offset #3193
    //   Java source line #456	-> byte code offset #3195
    //   Java source line #463	-> byte code offset #3203
    //   Java source line #464	-> byte code offset #3230
    //   Java source line #465	-> byte code offset #3234
    //   Java source line #466	-> byte code offset #3251
    //   Java source line #468	-> byte code offset #3260
    //   Java source line #469	-> byte code offset #3275
    //   Java source line #470	-> byte code offset #3280
    //   Java source line #472	-> byte code offset #3297
    //   Java source line #473	-> byte code offset #3299
    //   Java source line #475	-> byte code offset #3304
    //   Java source line #477	-> byte code offset #3333
    //   Java source line #481	-> byte code offset #3341
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	3342	0	this	LotteryUtil
    //   0	3342	1	id	String
    //   4	3333	2	lotteryId	String
    //   239	1575	3	newDate	String
    //   3229	97	3	listbyLatest	List
    //   272	526	4	dataList	List<Spellbuyrecord>
    //   3273	12	4	lotteryproductutil	Lotteryproductutil
    //   3297	3	4	e	Exception
    //   346	1490	5	DateSUM	Long
    //   362	54	6	buyId	Integer
    //   365	59	7	i100	int
    //   368	428	8	k	int
    //   842	159	8	caiNumberFlag	boolean
    //   481	98	9	h	Integer
    //   785	3	9	e	Exception
    //   848	1103	9	winNumber	Integer
    //   495	124	10	m	Integer
    //   853	992	10	sscNumber	String
    //   509	150	11	s1	Integer
    //   859	995	11	sscPeriod	Long
    //   523	209	12	ss1	Integer
    //   862	144	12	i	int
    //   1055	155	12	objList	List<Object[]>
    //   528	220	13	sh	String
    //   875	15	13	str	String
    //   992	3	13	e	Exception
    //   1141	349	13	productPeriod	int
    //   533	223	14	sm	String
    //   1255	3	14	e	Exception
    //   1398	56	14	spellbuyproductOld	List<Spellbuyproduct>
    //   1640	3	14	e	Exception
    //   1679	405	14	list	List
    //   538	223	15	ss	String
    //   1470	151	15	spellbuyproduct2	Spellbuyproduct
    //   1630	3	15	e	Exception
    //   1926	7	15	buyNumberCount	java.math.BigDecimal
    //   3193	3	15	e	Exception
    //   543	223	16	sss	String
    //   2115	63	16	lotteryproductutil	Lotteryproductutil
    //   2821	47	17	html	String
    //   3023	8	17	sendList	List<com.shcm.bean.SendResultBean>
    //   3135	3	17	e	Exception
    //   3174	3	17	e	Exception
    //   2872	3	18	flag	boolean
    //   2943	3	18	e	Exception
    //   3052	3	18	t	com.shcm.bean.SendResultBean
    //   3037	57	19	localIterator	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   373	420	785	java/lang/Exception
    //   423	433	785	java/lang/Exception
    //   436	782	785	java/lang/Exception
    //   872	989	992	java/lang/Exception
    //   1143	1252	1255	java/lang/Exception
    //   1463	1627	1630	java/lang/Exception
    //   1382	1637	1640	java/lang/Exception
    //   2823	2940	2943	java/lang/Exception
    //   2976	3092	3135	java/lang/Exception
    //   3093	3132	3135	java/lang/Exception
    //   2183	3092	3174	java/lang/Exception
    //   3093	3171	3174	java/lang/Exception
    //   1714	3092	3193	java/lang/Exception
    //   3093	3190	3193	java/lang/Exception
    //   3260	3294	3297	java/lang/Exception
  }
  
  public static void main(String[] args)
  {
    System.err.println(716L);
  }
}
