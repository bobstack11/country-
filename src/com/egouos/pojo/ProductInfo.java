package com.egouos.pojo;

import java.io.Serializable;

import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.StringUtil;

public class ProductInfo
  implements Serializable
{
  private static final long serialVersionUID = 2984681206487401036L;
  private String productName;
  private String productTitle;
  private Integer productPrice;
  private Integer singlePrice;
  private String headImage;
  private String productDetail;
  private Integer productId;
  private Integer spellbuyProductId;
  private Integer spellbuyCount;
  private Integer productPeriod;
  private Integer status;
  
  public ProductInfo() {}
  
  public ProductInfo(String productName, String productTitle, Integer productPrice, Integer singlePrice, String headImage, String productDetail, Integer productId, Integer spellbuyProductId, Integer spellbuyCount, Integer productPeriod, Integer status)
  {
    this.productName = productName;
    this.productTitle = productTitle;
    this.productPrice = productPrice;
    this.singlePrice = singlePrice;
    this.headImage = headImage;
    this.productDetail = productDetail;
    this.productId = productId;
    this.spellbuyProductId = spellbuyProductId;
    this.spellbuyCount = spellbuyCount;
    this.productPeriod = productPeriod;
    this.status = status;
  }
  
  public String getProductDetail2()
  {
	  if(StringUtil.isEmpty(productDetail)){
		  return productDetail;
	  }else{
		  String detail = productDetail.replaceAll("/productImg", ApplicationListenerImpl.sysConfigureJson.getWwwUrl()+"/productImg");
		  detail = detail.replaceAll("src", "src2");
		  return detail;
	  }
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public String getProductName()
  {
    return this.productName;
  }
  
  public void setProductName(String productName)
  {
    this.productName = productName;
  }
  
  public String getProductTitle()
  {
    return this.productTitle;
  }
  
  public void setProductTitle(String productTitle)
  {
    this.productTitle = productTitle;
  }
  
  public Integer getProductPrice()
  {
    return this.productPrice;
  }
  
  public void setProductPrice(Integer productPrice)
  {
    this.productPrice = productPrice;
  }
  
  public String getHeadImage()
  {
    return this.headImage;
  }
  
  public void setHeadImage(String headImage)
  {
    this.headImage = headImage;
  }
  
  public String getProductDetail()
  {
    return this.productDetail;
  }
  
  public void setProductDetail(String productDetail)
  {
    this.productDetail = productDetail;
  }
  
  public Integer getSpellbuyProductId()
  {
    return this.spellbuyProductId;
  }
  
  public void setSpellbuyProductId(Integer spellbuyProductId)
  {
    this.spellbuyProductId = spellbuyProductId;
  }
  
  public Integer getSpellbuyCount()
  {
    return this.spellbuyCount;
  }
  
  public void setSpellbuyCount(Integer spellbuyCount)
  {
    this.spellbuyCount = spellbuyCount;
  }
  
  public Integer getProductPeriod()
  {
    return this.productPeriod;
  }
  
  public void setProductPeriod(Integer productPeriod)
  {
    this.productPeriod = productPeriod;
  }
  
  public static long getSerialVersionUID()
  {
    return 2984681206487401036L;
  }
  
  public Integer getSinglePrice()
  {
    return this.singlePrice;
  }
  
  public void setSinglePrice(Integer singlePrice)
  {
    this.singlePrice = singlePrice;
  }
  
  public Integer getProductId()
  {
    return this.productId;
  }
  
  public void setProductId(Integer productId)
  {
    this.productId = productId;
  }
}
