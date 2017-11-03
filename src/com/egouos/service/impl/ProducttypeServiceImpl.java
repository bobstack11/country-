package com.egouos.service.impl;

import com.egouos.dao.BaseDAO;
import com.egouos.pojo.Producttype;
import com.egouos.service.ProducttypeService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProducttypeServiceImpl
  implements ProducttypeService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public void add(Producttype t)
  {
    this.baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id)
  {
    this.baseDao.delById(Producttype.class, id);
  }
  
  public Producttype findById(String id)
  {
    StringBuffer hql = new StringBuffer("from Producttype p where 1=1 and p.attribute70='type'");
    if (StringUtils.isNotBlank(id)) {
      hql.append(" and p.typeId='" + id + "'");
    }
    return (Producttype)this.baseDao.loadObject(hql.toString());
  }
  
  public List<Producttype> query(String hql)
  {
    return null;
  }
  
  public void update(String hql) {}
  
  public List<Producttype> queryAllProductType()
  {
    String hql = " from Producttype p where 1=1 and p.attribute70='type'";
    return (List<Producttype>)this.baseDao.query(hql);
  }
  
  public List<Producttype> listByProductList()
  {
    String hql = " from Producttype p where 1=1 and p.ftypeId = '1000' and p.attribute70='type'";
    return (List<Producttype>)this.baseDao.query(hql);
  }
  
  public List<Producttype> listByProductListBybrand(String id)
  {
    String hql = " from Producttype p where 1=1 and p.ftypeId = '" + id + "' and p.attribute70='type'";
    return (List<Producttype>)this.baseDao.query(hql);
  }
  
  public List<Producttype> listByBrand(String id)
  {
    StringBuffer hql = new StringBuffer("from Producttype p where 1=1 and p.attribute70='brand'");
    if (StringUtils.isNotBlank(id)) {
      hql.append(" and p.ftypeId='" + id + "' ");
    }
    return (List<Producttype>)this.baseDao.query(hql.toString());
  }
  
  public Producttype findBrandById(String id)
  {
    StringBuffer hql = new StringBuffer("from Producttype p where 1=1 and p.attribute70='brand'");
    if (StringUtils.isNotBlank(id)) {
      hql.append(" and p.typeId='" + id + "'");
    }
    return (Producttype)this.baseDao.loadObject(hql.toString());
  }
  
  public Producttype findBrandByName(String name)
  {
    StringBuffer hql = new StringBuffer("from Producttype p where 1=1 and p.typeName='" + name + "' and p.attribute70='brand'");
    return (Producttype)this.baseDao.loadObject(hql.toString());
  }
  
  public Producttype findTypeByName(String name)
  {
    StringBuffer hql = new StringBuffer("from Producttype p where 1=1 and p.typeName='" + name + "' and p.attribute70='type'");
    return (Producttype)this.baseDao.loadObject(hql.toString());
  }
}
