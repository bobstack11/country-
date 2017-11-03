package com.egouos.service.impl;

import com.egouos.dao.BaseDAO;
import com.egouos.dao.Pagination;
import com.egouos.pojo.Orderdetailaddress;
import com.egouos.service.OrderdetailaddressService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderdetailaddressServiceImpl
  implements OrderdetailaddressService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public void add(Orderdetailaddress t)
  {
    this.baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id) {}
  
  public Orderdetailaddress findById(String id)
  {
    return null;
  }
  
  public List<Orderdetailaddress> query(String hql)
  {
    return null;
  }
  
  public void update(String hql) {}
  
  public Pagination orderdetailaddressList(int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select * from orderdetailaddress os order by os.deliverTime desc");
    StringBuffer sql = new StringBuffer("select count(*) from orderdetailaddress");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("os", Orderdetailaddress.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
}
