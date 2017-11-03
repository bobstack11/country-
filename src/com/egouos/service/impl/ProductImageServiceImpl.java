package com.egouos.service.impl;

import com.egouos.dao.BaseDAO;
import com.egouos.pojo.Productimage;
import com.egouos.service.ProductImageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("productImageService")
public class ProductImageServiceImpl
  implements ProductImageService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public void add(Productimage t)
  {
    this.baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id)
  {
    this.baseDao.delById(Productimage.class, id);
  }
  
  public Productimage findById(String id)
  {
    return null;
  }
  
  public List<Productimage> query(String hql)
  {
    return null;
  }
  
  public void update(String hql) {}
  
  public List findByProductId(String productId, String type)
  {
    StringBuffer hql = new StringBuffer("select pe.* from productimage pe where pe.piProductId = '" + productId + "' and pe.Attribute_75 = '" + type + "' order by pe.image asc");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("pe", Productimage.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
}
