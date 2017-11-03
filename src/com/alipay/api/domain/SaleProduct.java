package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

public class SaleProduct
  extends AlipayObject
{
  private static final long serialVersionUID = 1132563264265361363L;
  @ApiField("channel_type")
  private String channelType;
  @ApiField("id")
  private String id;
  @ApiField("market_price")
  private String marketPrice;
  @ApiField("product_provider")
  private ProductProvider productProvider;
  @ApiField("sale_price")
  private String salePrice;
  @ApiField("status")
  private String status;
  
  public String getChannelType()
  {
    return this.channelType;
  }
  
  public void setChannelType(String channelType)
  {
    this.channelType = channelType;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getMarketPrice()
  {
    return this.marketPrice;
  }
  
  public void setMarketPrice(String marketPrice)
  {
    this.marketPrice = marketPrice;
  }
  
  public ProductProvider getProductProvider()
  {
    return this.productProvider;
  }
  
  public void setProductProvider(ProductProvider productProvider)
  {
    this.productProvider = productProvider;
  }
  
  public String getSalePrice()
  {
    return this.salePrice;
  }
  
  public void setSalePrice(String salePrice)
  {
    this.salePrice = salePrice;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
}
