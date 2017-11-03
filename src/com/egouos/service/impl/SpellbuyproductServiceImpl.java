package com.egouos.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egouos.dao.BaseDAO;
import com.egouos.dao.Pagination;
import com.egouos.pojo.Product;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.service.ConsumetableService;
import com.egouos.service.OrderIdService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.util.DateUtil;

@SuppressWarnings({"unchecked", "rawtypes"})
@Service("spellbuyproductService")
public class SpellbuyproductServiceImpl implements SpellbuyproductService
{
  private static final Logger LOG = LoggerFactory.getLogger(SpellbuyproductServiceImpl.class);
	
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  @Autowired
  SpellbuyrecordService sbuyrecordService;
  @Autowired
  OrderIdService orderIdService;
  @Autowired
  ConsumetableService consumetableService;
  
  public void add(Spellbuyproduct t)
  {
    this.baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id)
  {
    this.baseDao.delById(Spellbuyproduct.class, id);
  }
  
  public Spellbuyproduct findById(String id)
  {
    StringBuffer hql = new StringBuffer("from Spellbuyproduct spellbuyproduct where 1=1");
    if (StringUtils.isNotBlank(id)) {
      hql.append(" and spellbuyproduct.spellbuyProductId='" + id + "'");
    }
    return (Spellbuyproduct)this.baseDao.loadObject(hql.toString());
  }
  
  public List<Spellbuyproduct> query(String hql)
  {
    return (List<Spellbuyproduct>)this.baseDao.query(hql);
  }
  
  public void update(String hql) {}
  
  public List findByProductId(int productId)
  {
    StringBuffer sql = new StringBuffer("select pt.*,st.* from spellbuyproduct st,product pt where pt.productId = st.fkProductId and st.spellbuyProductId =" + productId);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("pt", Product.class);
    entityMap.put("st", Spellbuyproduct.class);
    List list = this.baseDao.sqlQueryEntity(sql, entityMap);
    return list;
  }
  
  @Override
  public List findByProductId(int productId, boolean lock)
  {
    StringBuffer sql = new StringBuffer("select pt.*,st.* from spellbuyproduct st,product pt where pt.productId=st.fkProductId and st.spStatus=0 and st.spellbuyProductId=");
    sql.append(productId);
    if(lock){
    	sql.append(" for update");
    }
    Map<String, Class> entityMap = new HashMap<String, Class>();
    entityMap.put("pt", Product.class);
    entityMap.put("st", Spellbuyproduct.class);
    List list = baseDao.sqlQueryEntity(sql, entityMap);
    return list;
  }
  
  public Pagination upcomingAnnounced(int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select pt.*,st.* from Product pt,Spellbuyproduct st where 1=1 and st.fkProductId=pt.productId  and (st.spellbuyCount > (pt.productPrice/6)) and st.spStatus <> 1  GROUP by pt.productId order by st.spellbuyCount desc");
    

    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("pt", Product.class);
    entityMap.put("st", Spellbuyproduct.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    
    page.setList(list);
    
    return page;
  }
  
  public Pagination upcomingAnnouncedByTop(int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select pt.*,st.* from Product pt,Spellbuyproduct st where 1=1 and st.fkProductId=pt.productId  and st.spStatus <> 1  GROUP by pt.productId order by st.spellbuyCount desc");
    
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("pt", Product.class);
    entityMap.put("st", Spellbuyproduct.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    
    page.setList(list);
    
    return page;
  }
  
  public List productPeriodList(Integer productId)
  {
    StringBuffer hql = new StringBuffer("select st.*,pt.* from spellbuyproduct st,product pt where st.fkProductId = pt.productId and pt.productId = '" + productId + "' order by st.productPeriod desc");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("st", Spellbuyproduct.class);
    entityMap.put("pt", Product.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public Spellbuyproduct findSpellbuyproductLastPeriod(Integer productId)
  {
    StringBuffer hql = new StringBuffer("from Spellbuyproduct spellbuyproduct where spellbuyproduct.fkProductId ='" + productId + "' order by spellbuyproduct.productPeriod desc limit 1");
    return (Spellbuyproduct)this.baseDao.loadObject(hql.toString());
  }
  
  public Spellbuyproduct findSpellbuyproductByStatus(Integer productId)
  {
    StringBuffer hql = new StringBuffer("from Spellbuyproduct spellbuyproduct where spellbuyproduct.fkProductId ='" + productId + "' and spellbuyproduct.spStatus=0 ");
    return (Spellbuyproduct)this.baseDao.loadObject(hql.toString());
  }
  
  public Pagination announcedProduct(int pageNo, int pageSize)
  {
    StringBuffer hql = new StringBuffer("select pt.*,st.* from product pt,spellbuyproduct st where st.fkProductId=pt.productId and st.spStatus = 1");
    StringBuffer sql = new StringBuffer("select count(DISTINCT(st.spellbuyProductId)) from product pt,spellbuyproduct st where st.fkProductId=pt.productId and st.spStatus = 1");
    Pagination page = new Pagination();
    page.setPageNo(pageNo);
    page.setPageSize(pageSize);
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("pt", Product.class);
    entityMap.put("st", Spellbuyproduct.class);
    List list = this.baseDao.sqlQuery(hql, entityMap, page);
    int resultCount = this.baseDao.getAllNum(sql);
    page.setList(list);
    page.setResultCount(resultCount);
    return page;
  }
  
  public List loadAllByType()
  {
    StringBuffer sql = new StringBuffer("from Spellbuyproduct st where 1=1 and st.spStatus <> 1 and st.spellbuyType = 8");
    List list = this.baseDao.find(sql.toString());
    return list;
  }
  
  public List loadAll()
  {
    StringBuffer sql = new StringBuffer("from Spellbuyproduct st where 1=1 and st.spStatus <> 1");
    List list = this.baseDao.find(sql.toString());
    return list;
  }
  
  public List UpdateLatestlotteryGetList()
  {
    StringBuffer sql = new StringBuffer("from Spellbuyproduct st where 1=1 and st.spStatus = 1");
    List list = this.baseDao.find(sql.toString());
    return list;
  }
  
  public List findSpellbuyproductByProductIdIsStatus(Integer productId)
  {
    StringBuffer hql = new StringBuffer("select * from spellbuyproduct st where st.fkProductId = '" + productId + "' and spStatus = 0");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("st", Spellbuyproduct.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }
  
  public List getIndexNewProduct()
  {
    StringBuffer hql = new StringBuffer("select st.*,pt.* from spellbuyproduct st,product pt where st.fkProductId = pt.productId and pt.style= 'goods_xp' and pt.status=1 and st.spStatus = 0 group by st.fkProductId limit 2");
    Map<String, Class> entityMap = new HashMap();
    entityMap.put("st", Spellbuyproduct.class);
    entityMap.put("pt", Product.class);
    List list = this.baseDao.sqlQueryEntity(hql, entityMap);
    return list;
  }

  @Override
	public  List<Object[]> mayfullBuyProducts(final int pageNo, final int pageSize, 
			final boolean lock) {
		final StringBuffer hql = new StringBuffer("select pt.*,st.* from Product pt,Spellbuyproduct st "
				+ "where st.fkProductId=pt.productId "
				+ "and st.spStatus=0 and st.spellbuyCount=st.spellbuyPrice ");
	    final Map<String, Class> entityMap = new HashMap<String, Class>(3);
	    entityMap.put("pt", Product.class);
	    entityMap.put("st", Spellbuyproduct.class);
	    final Pagination page = new Pagination();
	    page.setPageNo(pageNo);
	    page.setPageSize(pageSize);
	    List<Object[]> list = baseDao.sqlQuery(hql, entityMap, page, true);
	    return list;
	}

	@Override
    public void generateNextPeriods(final int nr) {
  	  final List<Object[]> products = mayfullBuyProducts(1, nr, true);
  	  for(final Object[] psb: products){
  		  final Product product = (Product)psb[0];
  		  final Integer pid = product.getProductId();
  		  if(consumetableService.countNotPaid(pid) == 0){
  			  // gen-next-p
  			  final Spellbuyproduct buyProduct = (Spellbuyproduct)psb[1];
  			  buyProduct.setSpStatus(Spellbuyproduct.STATUS_LOTABLE);
  			  final int curp = buyProduct.getProductPeriod();
  			  if (product.isUp()) {
  				  final Spellbuyproduct nextp = new Spellbuyproduct();
  				  nextp.setFkProductId(pid);
  				  nextp.setProductPeriod(Integer.valueOf(curp+1));
  				  nextp.setSpellbuyCount(Integer.valueOf(0));
  				  nextp.setSpellbuyType(Integer.valueOf(0));
  				  final Date now = new Date();
  				  nextp.setSpellbuyEndDate(DateUtil.DateTimeToStr(now));
  				  nextp.setSpellbuyPrice(product.getProductPrice());
  				  nextp.setSpSinglePrice(product.getSinglePrice());
  				  nextp.setSpellbuyStartDate(DateUtil.DateTimeToStr(now));
  				  nextp.setSpStatus(Spellbuyproduct.STATUS_BUYABLE);
  				  if (product.getAttribute71().equals("hot")) {
  		         	nextp.setSpellbuyType(Spellbuyproduct.BUYTYPE_HOT);
  				  } else {
  		        	nextp.setSpellbuyType(Spellbuyproduct.BUYTYPE_COMM);
  				  }
  		          add(nextp);
  		          final Integer sbpid = buyProduct.getSpellbuyProductId();
  		          final Integer ntpid = nextp.getSpellbuyProductId();
  		          LOG.info("last-period {}, gen next-period: {}", sbpid, ntpid);
  		      }
  		  }
  	  }
    }
	
	@Override
	public Spellbuyproduct getByProductId(int productId) {
		return (getByProductId(productId, false));
	}

	@Override
	public Spellbuyproduct getByProductId(int productId, boolean lock) {
		final List<Object[]> proList = findByProductId(productId, true);
		final Product product  = (Product) (proList.get(0)[0]);
		final Spellbuyproduct spellbuyproduct = (Spellbuyproduct) (proList.get(0)[1]);
		spellbuyproduct.setProduct(product);
		return spellbuyproduct;
	}
	
	@Override
	public Spellbuyproduct getByFkProductId(final int fkProductId, final boolean lock){
		StringBuffer sql = new StringBuffer("select pt.*,st.* from spellbuyproduct st,product pt "
				+ "where pt.productId=st.fkProductId and st.spStatus=0 and st.fkProductId=");
	    sql.append(fkProductId);
	    if(lock){
	    	sql.append(" for update");
	    }
	    Map<String, Class> entityMap = new HashMap<String, Class>(3);
	    entityMap.put("pt", Product.class);
	    entityMap.put("st", Spellbuyproduct.class);
		final List<Object[]> proList = baseDao.sqlQueryEntity(sql, entityMap);
	    final Product product  = (Product) (proList.get(0)[0]);
		final Spellbuyproduct spellbuyproduct = (Spellbuyproduct) (proList.get(0)[1]);
		spellbuyproduct.setProduct(product);
		return spellbuyproduct;
	}
	
}
