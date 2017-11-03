package com.egouos.service.impl;

import com.egouos.dao.BaseDAO;
import com.egouos.pojo.Product;
import com.egouos.pojo.Recommend;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.service.RecommendService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RecommendServiceImpl
  implements RecommendService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public void delete(Integer id) {}
  
  public Recommend findById(String id)
  {
    return null;
  }
  
  public List<Recommend> query(String hql)
  {
    return (List<Recommend>)this.baseDao.query(hql);
  }
  
  public void update(String hql) {}
  
  public List getRecommend()
  {
    StringBuffer sql = new StringBuffer("select * from recommend rd,spellbuyproduct st,product pt where rd.spellbuyProductId=st.spellbuyProductId and st.fkProductId=pt.productId order by rd.date desc limit 1");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("rd", Recommend.class);
    entityMap.put("st", Spellbuyproduct.class);
    entityMap.put("pt", Product.class);
    List list = this.baseDao.sqlQueryEntity(sql, entityMap);
    return list;
  }
  
  public void add(Recommend t)
  {
    this.baseDao.saveOrUpdate(t);
  }
}
