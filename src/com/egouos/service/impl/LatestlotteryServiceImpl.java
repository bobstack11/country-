package com.egouos.service.impl;

import com.egouos.dao.BaseDAO;
import com.egouos.dao.Pagination;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Orderdetail;
import com.egouos.pojo.Orderdetailaddress;
import com.egouos.pojo.Randomnumber;
import com.egouos.pojo.User;
import com.egouos.service.LatestlotteryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("latestlotteryService")
public class LatestlotteryServiceImpl
  implements LatestlotteryService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public Pagination LatestAnnounced(int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery order by AnnouncedTime desc");
    StringBuffer sql = new StringBuffer("select count(*) from latestlottery");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
  
  public void add(Latestlottery t)
  {
    this.baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id) {}
  
  public Latestlottery findById(String id)
  {
    StringBuffer hql = new StringBuffer("from Latestlottery ly where 1=1");
    if (StringUtils.isNotBlank(id)) {
      hql.append(" and ly.spellbuyProductId='" + id + "'");
    }
    return (Latestlottery)this.baseDao.loadObject(hql.toString());
  }
  
  public List<Latestlottery> query(String hql)
  {
    return (List<Latestlottery>)this.baseDao.query(hql);
  }
  
  public void update(String hql) {}
  
  public List getBuyHistoryByDetail(Integer spellbuyProductId)
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery ly where ly.spellbuyProductId = '" + spellbuyProductId + "'");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public List getByProductHistoryWin(Integer productId)
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery ly where ly.productId = '" + productId + "'  order by ly.spellbuyProductId desc");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public Pagination getProductByUser(String userId, String startDate, String endDate, String status, String shareStatus, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery ly where ly.userId = '" + userId + "'");
    if (StringUtils.isNotBlank(startDate)) {
      hql.append(" and ly.announcedTime >='" + startDate + "'");
    }
    if (StringUtils.isNotBlank(endDate)) {
      hql.append(" and ly.announcedTime <='" + endDate + "'");
    }
    if (StringUtils.isNotBlank(status)) {
      hql.append(" and ly.status ='" + status + "'");
    }
    if (StringUtils.isNotBlank(shareStatus)) {
      hql.append(" and ly.shareStatus ='" + shareStatus + "'");
    }
    hql.append(" order by ly.announcedTime desc");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    
    page.setList(list);
    
    return page;
  }
  
  public Integer getProductByUserByCount(String userId, String startDate, String endDate)
  {
    StringBuffer sql = new StringBuffer("select count(*) from latestlottery ly where ly.userId = '" + userId + "'");
    if (StringUtils.isNotBlank(startDate)) {
      sql.append(" and ly.announcedTime >='" + startDate + "'");
    }
    if (StringUtils.isNotBlank(endDate)) {
      sql.append(" and ly.announcedTime <='" + endDate + "'");
    }
    return Integer.valueOf(this.baseDao.getAllNum(sql));
  }
  
  public List indexWinningScroll()
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery ly order by ly.announcedTime desc limit 4");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public List getLotteryDetail(Integer spellbuyProductId)
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery ly where ly.spellbuyProductId = '" + spellbuyProductId + "'");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public Integer getCountByLotteryDetail(String spellbuyProductId)
  {
    StringBuffer hql = new StringBuffer("select count(DISTINCT buyer) from spellbuyrecord where fkSpellbuyProductId = '" + spellbuyProductId + "'");
    return Integer.valueOf(this.baseDao.getAllNum(hql));
  }
  
  public Pagination getLotteryDetailBybuyerList(Integer SpellbuyProductId, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select * from user ur,randomnumber rr where rr.userId=ur.userId and rr.productId= '" + SpellbuyProductId + "' order by rr.buyDate desc ");
    
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("rr", Randomnumber.class);
    entityMap.put("ur", User.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    
    page.setList(list);
    
    return page;
  }
  
  public Integer getLotteryDetailBybuyerListByCount(Integer SpellbuyProductId)
  {
    StringBuffer sql = new StringBuffer("select count(*) from user ur,randomnumber rr where rr.userId=ur.userId and rr.productId = '" + SpellbuyProductId + "'");
    return Integer.valueOf(this.baseDao.getAllNum(sql));
  }
  
  public List getProductOtherWinUser(String productId, String shareId)
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery where productId = '" + productId + "' and shareId <> '" + shareId + "' limit 6");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public List getLatestlotteryBySpellbuyProductIdAndProductIdIsExist(Integer SpellbuyProductId)
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery ly where ly.spellbuyProductId = '" + SpellbuyProductId + "' limit 1");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public Latestlottery findLatestlotteryByspellbuyrecordId(Integer spellbuyrecordId)
  {
    StringBuffer hql = new StringBuffer("from Latestlottery latestlottery where latestlottery.spellbuyRecordId='" + spellbuyrecordId + "' ");
    return (Latestlottery)this.baseDao.loadObject(hql.toString());
  }
  
  public List orderdetailListById(String id)
  {
    StringBuffer hql = new StringBuffer("select * from orderdetail ol where ol.orderDetailId = '" + id + "'");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ol", Orderdetail.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public Orderdetailaddress orderdetailaddressFindByOrderdetailId(String id)
  {
    StringBuffer hql = new StringBuffer("from Orderdetailaddress os where os.orderDetailId = '" + id + "'");
    
    return (Orderdetailaddress)this.baseDao.loadObject(hql.toString());
  }
  
  public void addOrderdetailaddress(Orderdetailaddress orderdetailaddress)
  {
    this.baseDao.saveOrUpdate(orderdetailaddress);
  }
  
  public Integer getLotteryByCount()
  {
    StringBuffer sql = new StringBuffer("select count(*) from latestlottery");
    return Integer.valueOf(this.baseDao.getAllNum(sql));
  }
  
  public Pagination adminByLatestAnnounced(int pageNo, int pageSize, String status, String shareStatus)
  {
    StringBuffer hql = new StringBuffer("select * from latestlottery where 1=1");
    StringBuffer sql = new StringBuffer("select count(*) from latestlottery where 1=1");
    if (StringUtils.isNotBlank(status)) {
      if (status.equals("2"))
      {
        hql.append(" and status='" + status + "'");
        sql.append(" and status='" + status + "'");
      }
      else if (status.equals("3"))
      {
        hql.append(" and status='" + status + "' or status='" + 4 + "'  or status='" + 10 + "'");
        sql.append(" and status='" + status + "' or status='" + 4 + "'  or status='" + 10 + "'");
      }
    }
    if (StringUtils.isNotBlank(shareStatus)) {
      if (shareStatus.equals("0"))
      {
        hql.append(" and shareStatus='" + shareStatus + "' or shareStatus='" + 1 + "' or shareStatus='" + 2 + "'");
        sql.append(" and shareStatus='" + shareStatus + "' or shareStatus='" + 1 + "' or shareStatus='" + 2 + "'");
      }
      else if (shareStatus.equals("-1"))
      {
        hql.append(" and shareStatus='" + shareStatus + "'");
        sql.append(" and shareStatus='" + shareStatus + "'");
      }
    }
    hql.append(" order by AnnouncedTime desc");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
}
