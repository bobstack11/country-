package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Producttype;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.service.ProducttypeService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("ListAction")
public class ListAction extends BaseAction
{
  private static final long serialVersionUID = 8452833122481904678L;
  @Autowired
  @Qualifier("spellbuyrecordService")
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  @Qualifier("spellbuyproductService")
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  ProducttypeService producttypeService;
  private List<ProductJSON> ProductList;
  private ProductJSON productJSON;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private List<Producttype> producttyList;
  private List<Producttype> brandList;
  private String id;
  private String typeId;
  private String tId;
  private String typeName;
  private String brandName;
  private int pageNo;
  private String pages;
  private String pageString;
  private int pageSize = 10;
  private int pageCount;
  private int resultCount;
  private String brandId;
  
  public String index()
  {
    /*if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      return null;
    }*/
    return "index";
  }
  
  public void goodsList()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (this.pages != null) {
      this.pageNo = Integer.parseInt(this.pages.split("_")[1]);
    }
    if (StringUtil.isNotBlank(this.typeId))
    {
      if (this.typeId.indexOf("b") != -1)
      {
        this.brandId = this.typeId.split("b")[1];
        this.tId = this.typeId.split("b")[0];
        if (StringUtil.isNotBlank(this.tId)) {
          this.typeName = ((Producttype)this.producttypeService.findById(this.tId)).getTypeName();
        } else {
          this.typeName = ((Producttype)this.producttypeService.findById("1000")).getTypeName();
        }
      }
      else
      {
        this.tId = this.typeId;
        this.typeName = ((Producttype)this.producttypeService.findById(this.tId)).getTypeName();
      }
    }
    else {
      this.typeName = ((Producttype)this.producttypeService.findById("1000")).getTypeName();
    }
    if (this.id.equals("hot20"))
    {
      Pagination hotPage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("list_hot20_" + this.tId + "_" + this.brandId + "_" + "hot_" + this.pageNo + "_" + this.pageSize);
      if (hotPage == null)
      {
        hotPage = this.spellbuyrecordService.ProductByTypeIdList(this.tId, this.brandId, "hot", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("list_hot20_" + this.tId + "_" + this.brandId + "_" + "hot_" + this.pageNo + "_" + this.pageSize, 5, hotPage);
      }
      List<Object[]> HotList = (List<Object[]>)hotPage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < HotList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])HotList.get(i);
      	if(objs[0] instanceof Product){
      		product = (Product)objs[0];
      		spellbuyproduct = (Spellbuyproduct)objs[1];
      	}else{
      		product = (Product)objs[1];
      		spellbuyproduct = (Spellbuyproduct)objs[0];
      	}
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    else if (this.id.equals("date20"))
    {
      Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("list_date20_" + this.tId + "_" + this.brandId + "_" + "date_" + this.pageNo + "_" + this.pageSize);
      if (datePage == null)
      {
        datePage = this.spellbuyrecordService.ProductByTypeIdList(this.tId, this.brandId, "date", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("list_date20_" + this.tId + "_" + this.brandId + "_" + "date_" + this.pageNo + "_" + this.pageSize, 5, datePage);
      }
      List<Object[]> dateList = (List<Object[]>)datePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < dateList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])dateList.get(i);
      	if(objs[0] instanceof Product){
      		product = (Product)objs[0];
      		spellbuyproduct = (Spellbuyproduct)objs[1];
      	}else{
      		product = (Product)objs[1];
      		spellbuyproduct = (Spellbuyproduct)objs[0];
      	}
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    else if (this.id.equals("price20"))
    {
      Pagination pricePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("list_price20_" + this.tId + "_" + this.brandId + "_" + "price_" + this.pageNo + "_" + this.pageSize);
      if (pricePage == null)
      {
        pricePage = this.spellbuyrecordService.ProductByTypeIdList(this.tId, this.brandId, "price", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("list_price20_" + this.tId + "_" + this.brandId + "_" + "price_" + this.pageNo + "_" + this.pageSize, 5, pricePage);
      }
      List<Object[]> priceList = (List<Object[]>)pricePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])priceList.get(i);
      	if(objs[0] instanceof Product){
      		product = (Product)objs[0];
      		spellbuyproduct = (Spellbuyproduct)objs[1];
      	}else{
      		product = (Product)objs[1];
      		spellbuyproduct = (Spellbuyproduct)objs[0];
      	}
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    else if (this.id.equals("priceAsc20"))
    {
      Pagination pricePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("list_priceAsc20_" + this.tId + "_" + this.brandId + "_" + "priceAsc_" + this.pageNo + "_" + this.pageSize);
      if (pricePage == null)
      {
        pricePage = this.spellbuyrecordService.ProductByTypeIdList(this.tId, this.brandId, "priceAsc", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("list_priceAsc20_" + this.tId + "_" + this.brandId + "_" + "priceAsc_" + this.pageNo + "_" + this.pageSize, 5, pricePage);
      }
      List<Object[]> priceList = (List<Object[]>)pricePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])priceList.get(i);
      	if(objs[0] instanceof Product){
      		product = (Product)objs[0];
      		spellbuyproduct = (Spellbuyproduct)objs[1];
      	}else{
      		product = (Product)objs[1];
      		spellbuyproduct = (Spellbuyproduct)objs[0];
      	}
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    else if (this.id.equals("about20"))
    {
      Pagination pricePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("list_about20_" + this.tId + "_" + this.brandId + "_" + "about_" + this.pageNo + "_" + this.pageSize);
      if (pricePage == null)
      {
        pricePage = this.spellbuyrecordService.ProductByTypeIdList(this.tId, this.brandId, "about", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("list_about20_" + this.tId + "_" + this.brandId + "_" + "about_" + this.pageNo + "_" + this.pageSize, 5, pricePage);
      }
      List<Object[]> priceList = (List<Object[]>)pricePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])priceList.get(i);
      	if(objs[0] instanceof Product){
      		product = (Product)objs[0];
      		spellbuyproduct = (Spellbuyproduct)objs[1];
      	}else{
      		product = (Product)objs[1];
      		spellbuyproduct = (Spellbuyproduct)objs[0];
      	}
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    else if (this.id.equals("surplus20"))
    {
      Pagination pricePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("list_surplus20_" + this.tId + "_" + this.brandId + "_" + "surplus_" + this.pageNo + "_" + this.pageSize);
      if (pricePage == null)
      {
        pricePage = this.spellbuyrecordService.ProductByTypeIdList(this.tId, this.brandId, "surplus", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("list_surplus20_" + this.tId + "_" + this.brandId + "_" + "surplus_" + this.pageNo + "_" + this.pageSize, 5, pricePage);
      }
      List<Object[]> priceList = (List<Object[]>)pricePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        Object[] objs = (Object[])priceList.get(i);
      	if(objs[0] instanceof Product){
      		product = (Product)objs[0];
      		spellbuyproduct = (Spellbuyproduct)objs[1];
      	}else{
      		product = (Product)objs[1];
      		spellbuyproduct = (Spellbuyproduct)objs[0];
      	}
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
  }
  
  public void isStatus()
  {
    this.spellbuyproduct = ((Spellbuyproduct)AliyunOcsSampleHelp.getIMemcachedCache().get("index_spellbuyproduct_" + this.id));
    if (this.spellbuyproduct == null)
    {
      this.spellbuyproduct = ((Spellbuyproduct)this.spellbuyproductService.findById(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("index_spellbuyproduct", 2, this.spellbuyproduct);
    }
    if (this.spellbuyproduct.getSpStatus().intValue() == 1) {
      Struts2Utils.renderText("false", new String[0]);
    } else if (this.spellbuyproduct.getSpellbuyPrice().intValue() - this.spellbuyproduct.getSpellbuyCount().intValue() == 0) {
      Struts2Utils.renderText("false", new String[0]);
    } else {
      Struts2Utils.renderText(String.valueOf(this.spellbuyproduct.getSpellbuyPrice().intValue() - this.spellbuyproduct.getSpellbuyCount().intValue()), new String[0]);
    }
  }
  
  public Product getProduct()
  {
    return this.product;
  }
  
  public void setProduct(Product product)
  {
    this.product = product;
  }
  
  public Spellbuyproduct getSpellbuyproduct()
  {
    return this.spellbuyproduct;
  }
  
  public void setSpellbuyproduct(Spellbuyproduct spellbuyproduct)
  {
    this.spellbuyproduct = spellbuyproduct;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public int getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getPageCount()
  {
    return this.pageCount;
  }
  
  public void setPageCount(int pageCount)
  {
    this.pageCount = pageCount;
  }
  
  public int getResultCount()
  {
    return this.resultCount;
  }
  
  public void setResultCount(int resultCount)
  {
    this.resultCount = resultCount;
  }
  
  public List<ProductJSON> getProductList()
  {
    return this.ProductList;
  }
  
  public void setProductList(List<ProductJSON> productList)
  {
    this.ProductList = productList;
  }
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
  }
  
  public String getTypeId()
  {
    return this.typeId;
  }
  
  public void setTypeId(String typeId)
  {
    this.typeId = typeId;
  }
  
  public String getTypeName()
  {
    return this.typeName;
  }
  
  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
  }
  
  public String getPages()
  {
    return this.pages;
  }
  
  public void setPages(String pages)
  {
    this.pages = pages;
  }
  
  public String getPageString()
  {
    return this.pageString;
  }
  
  public void setPageString(String pageString)
  {
    this.pageString = pageString;
  }
  
  public List<Producttype> getProducttyList()
  {
    return this.producttyList;
  }
  
  public void setProducttyList(List<Producttype> producttyList)
  {
    this.producttyList = producttyList;
  }
  
  public List<Producttype> getBrandList()
  {
    return this.brandList;
  }
  
  public void setBrandList(List<Producttype> brandList)
  {
    this.brandList = brandList;
  }
  
  public String getBrandId()
  {
    return this.brandId;
  }
  
  public void setBrandId(String brandId)
  {
    this.brandId = brandId;
  }
  
  public String getTId()
  {
    return this.tId;
  }
  
  public void setTId(String id)
  {
    this.tId = id;
  }
  
  public String getBrandName()
  {
    return this.brandName;
  }
  
  public void setBrandName(String brandName)
  {
    this.brandName = brandName;
  }
}
