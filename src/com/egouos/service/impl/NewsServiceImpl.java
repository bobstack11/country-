package com.egouos.service.impl;

import com.egouos.dao.BaseDAO;
import com.egouos.dao.Pagination;
import com.egouos.pojo.News;
import com.egouos.service.NewsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl
  implements NewsService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public void add(News t)
  {
    this.baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id)
  {
    this.baseDao.delById(News.class, id);
  }
  
  public News findById(String id)
  {
    StringBuffer hql = new StringBuffer("from News n where 1=1");
    if (StringUtils.isNotBlank(id)) {
      hql.append(" and n.newsId='" + id + "'");
    }
    return (News)this.baseDao.loadObject(hql.toString());
  }
  
  public List<News> query(String hql)
  {
    return null;
  }
  
  public void update(String hql) {}
  
  public Pagination newsList(int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select * from news n order by postDate desc");
    StringBuffer sql = new StringBuffer("select count(*) from news");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("n", News.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
  
  public List indexNews()
  {
    StringBuffer sql = new StringBuffer("select * from news n order by postDate desc limit 3");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("n", News.class);
    List list = this.baseDao.sqlQueryEntity(sql, entityMap);
    return list;
  }
}
