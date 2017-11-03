package com.egouos.service.impl;

import com.egouos.dao.BaseDAO;
import com.egouos.dao.Pagination;
import com.egouos.pojo.Friends;
import com.egouos.pojo.User;
import com.egouos.service.FriendsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FriendsServiceImpl
  implements FriendsService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public void add(Friends t)
  {
    this.baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id) {}
  
  public Friends findById(String id)
  {
    return null;
  }
  
  public List<Friends> query(String hql)
  {
    return null;
  }
  
  public void update(String hql) {}
  
  public Pagination getFriends(String userId, int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select * from friends fs,user ur where fs.friendsId = ur.userId and fs.userId = '" + userId + "'");
    StringBuffer sql = new StringBuffer("select count(*) from friends fs,user ur where fs.friendsId = ur.userId and fs.userId = '" + userId + "'");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("fs", Friends.class);
    entityMap.put("ur", User.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
}
