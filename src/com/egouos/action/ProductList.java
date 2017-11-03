package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Producttype;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.SysConfigure;
import com.egouos.service.ProductService;
import com.egouos.service.ProducttypeService;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.PaginationUtil;
import com.egouos.util.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ProductList")
public class ProductList
  extends ActionSupport
{
  private static final long serialVersionUID = -8133635872882545829L;
  @Autowired
  ProducttypeService producttypeService;
  @Autowired
  ProductService productService;
  private List<Product> productList;
  private ProductJSON productJSON;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private String id;
  private String typeId;
  private String typeName;
  private int pageNo;
  private String pages;
  private String pageString;
  private int pageSize = 20;
  private int pageCount;
  private int resultCount;
  
  public String index()
  {
    if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      

      return null;
    }
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (this.pages != null) {
      this.pageNo = Integer.parseInt(this.pages.split("_")[1]);
    }
    if ((this.typeId != null) && (!this.typeId.equals(""))) {
      this.typeName = ((Producttype)this.producttypeService.findById(this.typeId)).getTypeName();
    } else {
      this.typeName = ((Producttype)this.producttypeService.findById("1000")).getTypeName();
    }
    if (this.id.equals("hot20"))
    {
      Pagination page = this.productService.ProductListByTypeIdList(this.typeId, "hot", this.pageNo, this.pageSize);
      this.productList = (List<Product>)page.getList();
      
      this.resultCount = page.getResultCount();
      if ((this.typeId != null) && (!this.typeId.equals(""))) {
        this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/productList/" + this.id + "/" + this.typeId + "/p_");
      } else {
        this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/productList/" + this.id + "/p_");
      }
    }
    return "index";
  }
  
  public List<Product> getProductList()
  {
    return this.productList;
  }
  
  public void setProductList(List<Product> productList)
  {
    this.productList = productList;
  }
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
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
  
  public int getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;
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
}
