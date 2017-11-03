package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.SysConfigure;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.PaginationUtil;
import com.egouos.util.Struts2Utils;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("SearchAction")
public class SearchAction
  extends ActionSupport
{
  private static final long serialVersionUID = -6415908765367001524L;
  @Autowired
  @Qualifier("spellbuyrecordService")
  private SpellbuyrecordService spellbuyrecordService;
  private List<ProductJSON> ProductList;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private ProductJSON productJSON;
  private String id;
  private int pageNo;
  private String pages;
  private String pageString;
  private int pageSize = 20;
  private int pageCount;
  private int resultCount;
  private String keyword;
  
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
    if (this.id.equals("hot20"))
    {
      Pagination hotPage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("search_index_hotPage_hot20_" + this.keyword + "_hot_" + this.pageNo + "_" + this.pageSize);
      if (hotPage == null)
      {
        hotPage = this.spellbuyrecordService.searchProduct(this.keyword, "hot", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("search_index_hotPage_hot20_" + this.keyword + "_hot_" + this.pageNo + "_" + this.pageSize, 10, hotPage);
      }
      List<Object[]> HotList = (List<Object[]>)hotPage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < HotList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])HotList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])HotList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      this.resultCount = hotPage.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/search/" + this.id + "/" + this.keyword + "/p_");
    }
    else if (this.id.equals("date20"))
    {
      Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("search_index_datePage_date20_" + this.keyword + "_date_" + this.pageNo + "_" + this.pageSize);
      if (datePage == null)
      {
        datePage = this.spellbuyrecordService.searchProduct(this.keyword, "date", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("search_index_datePage_date20_" + this.keyword + "_date_" + this.pageNo + "_" + this.pageSize, 10, datePage);
      }
      List<Object[]> dateList = (List<Object[]>)datePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < dateList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])dateList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])dateList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      this.resultCount = datePage.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/search/" + this.id + "/" + this.keyword + "/p_");
    }
    else if (this.id.equals("price20"))
    {
      Pagination pricePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("search_index_pricePage_price20_" + this.keyword + "_price_" + this.pageNo + "_" + this.pageSize);
      if (pricePage == null)
      {
        pricePage = this.spellbuyrecordService.searchProduct(this.keyword, "price", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("search_index_pricePage_price20_" + this.keyword + "_price_" + this.pageNo + "_" + this.pageSize, 10, pricePage);
      }
      List<Object[]> priceList = (List<Object[]>)pricePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])priceList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])priceList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      this.resultCount = pricePage.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/search/" + this.id + "/" + this.keyword + "/p_");
    }
    else if (this.id.equals("priceAsc20"))
    {
      Pagination pricePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("search_index_pricePage_priceAsc20_" + this.keyword + "_priceAsc_" + this.pageNo + "_" + this.pageSize);
      if (pricePage == null)
      {
        pricePage = this.spellbuyrecordService.searchProduct(this.keyword, "priceAsc", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("search_index_pricePage_priceAsc20_" + this.keyword + "_priceAsc_" + this.pageNo + "_" + this.pageSize, 10, pricePage);
      }
      List<Object[]> priceList = (List<Object[]>)pricePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])priceList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])priceList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      this.resultCount = pricePage.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/search/" + this.id + "/" + this.keyword + "/p_");
    }
    else if (this.id.equals("about20"))
    {
      Pagination aboutPage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("search_index_aboutPage_about20_" + this.keyword + "_about_" + this.pageNo + "_" + this.pageSize);
      if (aboutPage == null)
      {
        aboutPage = this.spellbuyrecordService.searchProduct(this.keyword, "about", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("search_index_aboutPage_about20_" + this.keyword + "_about_" + this.pageNo + "_" + this.pageSize, 10, aboutPage);
      }
      List<Object[]> priceList = (List<Object[]>)aboutPage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])priceList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])priceList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      this.resultCount = aboutPage.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/search/" + this.id + "/" + this.keyword + "/p_");
    }
    else if (this.id.equals("surplus20"))
    {
      Pagination surplusPage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("search_index_surplusPage_surplus20_" + this.keyword + "_surplus_" + this.pageNo + "_" + this.pageSize);
      if (surplusPage == null)
      {
        surplusPage = this.spellbuyrecordService.searchProduct(this.keyword, "surplus", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("search_index_surplusPage_surplus20_" + this.keyword + "_surplus_" + this.pageNo + "_" + this.pageSize, 10, surplusPage);
      }
      List<Object[]> priceList = (List<Object[]>)surplusPage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])priceList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])priceList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      this.resultCount = surplusPage.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), ApplicationListenerImpl.sysConfigureJson.getWwwUrl() + "/search/" + this.id + "/" + this.keyword + "/p_");
    }
    return "index";
  }
  
  public String ajaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    } else {
      this.pageNo += 1;
    }
    if (this.id.equals("hot20"))
    {
      Pagination hotPage = this.spellbuyrecordService.searchProduct(this.keyword, "hot", this.pageNo, this.pageSize);
      List<Object[]> HotList = (List<Object[]>)hotPage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < HotList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])HotList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])HotList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    else if (this.id.equals("date20"))
    {
      Pagination datePage = this.spellbuyrecordService.searchProduct(this.keyword, "date", this.pageNo, this.pageSize);
      List<Object[]> dateList = (List<Object[]>)datePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < dateList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])dateList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])dateList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    else if (this.id.equals("price20"))
    {
      Pagination pricePage = this.spellbuyrecordService.searchProduct(this.keyword, "price", this.pageNo, this.pageSize);
      List<Object[]> priceList = (List<Object[]>)pricePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])priceList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])priceList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    else if (this.id.equals("priceAsc20"))
    {
      Pagination pricePage = this.spellbuyrecordService.searchProduct(this.keyword, "priceAsc", this.pageNo, this.pageSize);
      List<Object[]> priceList = (List<Object[]>)pricePage.getList();
      this.ProductList = new ArrayList();
      for (int i = 0; i < priceList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])priceList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])priceList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
        this.productJSON.setSinglePrice(this.spellbuyproduct.getSpSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductStyle(this.product.getStyle());
        this.ProductList.add(this.productJSON);
      }
      Struts2Utils.renderJson(this.ProductList, new String[0]);
    }
    return null;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public List<ProductJSON> getProductList()
  {
    return this.ProductList;
  }
  
  public void setProductList(List<ProductJSON> productList)
  {
    this.ProductList = productList;
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
  
  public String getKeyword()
  {
    return this.keyword;
  }
  
  public void setKeyword(String keyword)
  {
    this.keyword = keyword;
  }
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
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
}
