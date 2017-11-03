package com.alipay.api.response;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;

public class AlipayMobilePublicGisGetResponse
  extends AlipayResponse
{
  private static final long serialVersionUID = 3325385974443832235L;
  @ApiField("accuracy")
  private String accuracy;
  @ApiField("city")
  private String city;
  @ApiField("code")
  private String code;
  @ApiField("latitude")
  private String latitude;
  @ApiField("longitude")
  private String longitude;
  @ApiField("msg")
  private String msg;
  @ApiField("province")
  private String province;
  
  public void setAccuracy(String accuracy)
  {
    this.accuracy = accuracy;
  }
  
  public String getAccuracy()
  {
    return this.accuracy;
  }
  
  public void setCity(String city)
  {
    this.city = city;
  }
  
  public String getCity()
  {
    return this.city;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setLatitude(String latitude)
  {
    this.latitude = latitude;
  }
  
  public String getLatitude()
  {
    return this.latitude;
  }
  
  public void setLongitude(String longitude)
  {
    this.longitude = longitude;
  }
  
  public String getLongitude()
  {
    return this.longitude;
  }
  
  public void setMsg(String msg)
  {
    this.msg = msg;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
  
  public void setProvince(String province)
  {
    this.province = province;
  }
  
  public String getProvince()
  {
    return this.province;
  }
}
