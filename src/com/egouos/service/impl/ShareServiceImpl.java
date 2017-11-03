package com.egouos.service.impl;

import com.egouos.dao.BaseDAO;
import com.egouos.dao.Pagination;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Sharecomments;
import com.egouos.pojo.Shareimage;
import com.egouos.pojo.Shareinfo;
import com.egouos.pojo.User;
import com.egouos.service.ShareService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl
  implements ShareService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public Pagination loadPageShare(String type, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select so.*,se.*,ly.* from shareinfo so,shareimage se,latestlottery ly where so.sproductId = ly.spellbuyProductId and so.userId = ly.userId and so.uid = se.shareInfoId and so.status = 2 group by so.uid");
    StringBuffer sql = new StringBuffer("select count(DISTINCT(so.uid)) from shareinfo so,shareimage se,latestlottery ly where so.sproductId = ly.spellbuyProductId and so.userId = ly.userId and so.uid = se.shareInfoId and so.status = 2");
    if (type.equals("new20")) {
      hql.append(" ORDER BY so.shareDate DESC");
    }
    if (type.equals("hot20")) {
      hql.append(" ORDER BY so.upCount DESC");
    }
    if (type.equals("reply20")) {
      hql.append(" ORDER BY so.replyCount DESC");
    }
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("so", Shareinfo.class);
    entityMap.put("se", Shareimage.class);
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
  
  public Pagination adminShareList(String type, String status, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select so.*,ly.* from shareinfo so,latestlottery ly where so.userId = ly.userId and so.sproductId = ly.spellbuyProductId");
    StringBuffer sql = new StringBuffer("select count(*) from shareinfo so,latestlottery ly where so.userId = ly.userId and so.sproductId = ly.spellbuyProductId");
    if ((StringUtils.isNotEmpty(status)) && 
      (!status.equals("null")))
    {
      hql.append(" and so.status ='" + status + "'");
      sql.append(" and so.status ='" + status + "'");
    }
    if (type.equals("new20")) {
      hql.append(" ORDER BY so.shareDate DESC");
    }
    if (type.equals("hot20")) {
      hql.append(" ORDER BY so.upCount DESC");
    }
    if (type.equals("reply20")) {
      hql.append(" ORDER BY so.replyCount DESC");
    }
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("so", Shareinfo.class);
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
  
  public List shareShow(int id)
  {
    StringBuffer sql = new StringBuffer("select so.*,ly.* from shareinfo so,latestlottery ly where so.userId = ly.userId and so.sproductId = ly.spellbuyProductId and so.uid ='" + id + "'");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("so", Shareinfo.class);
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQueryEntity(sql, entityMap);
    return list;
  }
  
  public Integer getShareByUserByCount(String userId, String status, String shareStatus, String startDate, String endDate)
  {
    StringBuffer sql = new StringBuffer("select count(*) from latestlottery ly where ly.userId = '" + userId + "'");
    if (StringUtils.isNotBlank(startDate)) {
      sql.append(" and ly.announcedTime >='" + startDate + "'");
    }
    if (StringUtils.isNotBlank(endDate)) {
      sql.append(" and ly.announcedTime <='" + endDate + "'");
    }
    if (StringUtils.isNotBlank(status)) {
      sql.append(" and ly.status ='" + status + "'");
    }
    if (StringUtils.isNotBlank(shareStatus)) {
      sql.append(" and ly.shareStatus ='" + shareStatus + "'");
    }
    return Integer.valueOf(this.baseDao.getAllNum(sql));
  }
  
  public void add(Shareinfo t)
  {
    this.baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id) {}
  
  public Shareinfo findById(String id)
  {
    StringBuffer hql = new StringBuffer("from Shareinfo shareinfo where 1=1");
    if (StringUtils.isNotBlank(id)) {
      hql.append(" and shareinfo.uid='" + id + "'");
    }
    return (Shareinfo)this.baseDao.loadObject(hql.toString());
  }
  
  public Shareinfo findByProductId(String id)
  {
    StringBuffer hql = new StringBuffer("from Shareinfo shareinfo where 1=1");
    if (StringUtils.isNotBlank(id)) {
      hql.append(" and shareinfo.sproductId='" + id + "'");
    }
    return (Shareinfo)this.baseDao.loadObject(hql.toString());
  }
  
  public List<Shareinfo> query(String hql)
  {
    return null;
  }
  
  public void update(String hql) {}
  
  public Pagination shareByUser(String userId, String startDate, String endDate, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select * from shareinfo so,shareimage se where so.uid = se.shareInfoId and so.userId = '" + userId + "'");
    StringBuffer sql = new StringBuffer("select count(DISTINCT(so.uid)) from shareinfo so,shareimage se where so.uid = se.shareInfoId and so.userId = '" + userId + "'");
    if (StringUtils.isNotBlank(startDate))
    {
      hql.append(" and so.shareDate >= '" + startDate + "'");
      sql.append(" and so.shareDate >= '" + startDate + "'");
    }
    if (StringUtils.isNotBlank(endDate))
    {
      hql.append(" and so.shareDate <='" + endDate + "'");
      sql.append(" and so.shareDate <='" + endDate + "'");
    }
    hql.append(" group by so.uid order by so.shareDate desc");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("so", Shareinfo.class);
    entityMap.put("se", Shareimage.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
  
  public Pagination UserInfoShareByUser(String userId, String startDate, String endDate, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select so.*,se.*,ly.* from shareinfo so,shareimage se,latestlottery ly where so.sproductId = ly.spellbuyProductId and so.userId = ly.userId and so.uid = se.shareInfoId and so.status = 2 and so.userId = '" + userId + "'");
    StringBuffer sql = new StringBuffer("select count(*) from shareinfo so,latestlottery ly where so.sproductId = ly.spellbuyProductId and so.userId = ly.userId and so.status = 2 and so.userId = '" + userId + "' ");
    if (StringUtils.isNotBlank(startDate))
    {
      hql.append(" and so.shareDate >= '" + startDate + "'");
      sql.append(" and so.shareDate >= '" + startDate + "'");
    }
    if (StringUtils.isNotBlank(endDate))
    {
      hql.append(" and so.shareDate <='" + endDate + "'");
      sql.append(" and so.shareDate <='" + endDate + "'");
    }
    hql.append(" group by so.uid order by so.shareDate desc");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("so", Shareinfo.class);
    entityMap.put("se", Shareimage.class);
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
  
  public List getShareimage(String shareId)
  {
    StringBuffer sql = new StringBuffer("select * from shareimage se where se.shareInfoId = '" + shareId + "'");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("se", Shareimage.class);
    List list = this.baseDao.sqlQueryEntity(sql, entityMap);
    return list;
  }
  
  public void addShareImage(Shareimage shareimage)
  {
    this.baseDao.saveOrUpdate(shareimage);
  }
  
  public Pagination shareByComment(String shareId, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select * from sharecomments ss,user ur where ss.userId = ur.userId and ss.shareInfoId = '" + shareId + "' and ss.reCommentId is null order by createDate desc");
    StringBuffer sql = new StringBuffer("select count(*) from sharecomments ss,user ur where ss.userId = ur.userId and ss.shareInfoId = '" + shareId + "' and ss.reCommentId is null");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ss", Sharecomments.class);
    entityMap.put("ur", User.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
  
  public List getReCommentList(String shareCommentId)
  {
    StringBuffer sql = new StringBuffer("select * from sharecomments ss,user ur where ss.userId = ur.userId and ss.reCommentId = '" + shareCommentId + "'");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ss", Sharecomments.class);
    entityMap.put("ur", User.class);
    List list = this.baseDao.sqlQueryEntity(sql, entityMap);
    return list;
  }
  
  public void createComment(Sharecomments sharecomments)
  {
    this.baseDao.saveOrUpdate(sharecomments);
  }
  
  public Sharecomments findBySharecommentsId(String sharecommentsId)
  {
    StringBuffer hql = new StringBuffer("from Sharecomments sharecomments where 1=1");
    if (StringUtils.isNotBlank(sharecommentsId)) {
      hql.append(" and sharecomments.uid='" + sharecommentsId + "'");
    }
    return (Sharecomments)this.baseDao.loadObject(hql.toString());
  }
  
  public List getIndexSharecommentsList()
  {
    StringBuffer sql = new StringBuffer("select * from sharecomments ss,user ur where ss.userId = ur.userId order by ss.createDate desc limit 18");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("ss", Sharecomments.class);
    entityMap.put("ur", User.class);
    List list = this.baseDao.sqlQueryEntity(sql, entityMap);
    return list;
  }
  
  public Pagination loadShareInfoByNew(String type, String productId, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select so.*,ly.* from shareinfo so,latestlottery ly where so.sproductId = ly.spellbuyProductId and so.userId = ly.userId and so.status = 2 ");
    if (StringUtils.isNotBlank(productId)) {
      hql.append(" and ly.productId=" + productId);
    }
    if (type.equals("new20")) {
      hql.append(" ORDER BY so.shareDate DESC");
    }
    if (type.equals("hot20")) {
      hql.append(" ORDER BY so.upCount DESC");
    }
    if (type.equals("reply20")) {
      hql.append(" ORDER BY so.replyCount DESC");
    }
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("so", Shareinfo.class);
    entityMap.put("ly", Latestlottery.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    
    page.setList(list);
    
    return page;
  }
  
  public Integer loadShareInfoByNewByCount(String spellbuyProductId)
  {
    StringBuffer sql = new StringBuffer("select count(DISTINCT(so.uid)) from shareinfo so,latestlottery ly where so.sproductId = ly.spellbuyProductId and so.userId = ly.userId and so.status = 2 and ly.productId =" + spellbuyProductId);
    return Integer.valueOf(this.baseDao.getAllNum(sql));
  }
}
