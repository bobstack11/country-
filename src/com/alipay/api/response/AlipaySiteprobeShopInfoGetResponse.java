package com.alipay.api.response;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;

public class AlipaySiteprobeShopInfoGetResponse
  extends AlipayResponse
{
  private static final long serialVersionUID = 6558618836979279959L;
  @ApiField("adv_type")
  private String advType;
  @ApiField("code")
  private Long code;
  @ApiField("logo")
  private String logo;
  @ApiField("msg")
  private String msg;
  @ApiField("public_name")
  private String publicName;
  @ApiField("shop_name")
  private String shopName;
  @ApiField("shop_notice")
  private String shopNotice;
  @ApiField("third_party_shop_name")
  private String thirdPartyShopName;
  @ApiField("url")
  private String url;
  
  public void setAdvType(String advType)
  {
    this.advType = advType;
  }
  
  public String getAdvType()
  {
    return this.advType;
  }
  
  public void setCode(Long code)
  {
    this.code = code;
  }
  
  public Long getCode()
  {
    return this.code;
  }
  
  public void setLogo(String logo)
  {
    this.logo = logo;
  }
  
  public String getLogo()
  {
    return this.logo;
  }
  
  public void setMsg(String msg)
  {
    this.msg = msg;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
  
  public void setPublicName(String publicName)
  {
    this.publicName = publicName;
  }
  
  public String getPublicName()
  {
    return this.publicName;
  }
  
  public void setShopName(String shopName)
  {
    this.shopName = shopName;
  }
  
  public String getShopName()
  {
    return this.shopName;
  }
  
  public void setShopNotice(String shopNotice)
  {
    this.shopNotice = shopNotice;
  }
  
  public String getShopNotice()
  {
    return this.shopNotice;
  }
  
  public void setThirdPartyShopName(String thirdPartyShopName)
  {
    this.thirdPartyShopName = thirdPartyShopName;
  }
  
  public String getThirdPartyShopName()
  {
    return this.thirdPartyShopName;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getUrl()
  {
    return this.url;
  }
}
