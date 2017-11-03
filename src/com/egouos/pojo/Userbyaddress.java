package com.egouos.pojo;

import java.io.Serializable;

public class Userbyaddress
  implements Serializable
{
  private Integer id;
  private Integer userId;
  private String province;
  private String city;
  private String district;
  private String address;
  private Integer zipCode;
  private String consignee;
  private String phone;
  private Integer status = Integer.valueOf(1);
  
  public Userbyaddress() {}
  
  public Userbyaddress(Integer id, Integer userId, String province, String city, String district, String address, Integer zipCode, String consignee, String phone, Integer status)
  {
    this.id = id;
    this.userId = userId;
    this.province = province;
    this.city = city;
    this.district = district;
    this.address = address;
    this.zipCode = zipCode;
    this.consignee = consignee;
    this.phone = phone;
    this.status = status;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }
  
  public String getProvince()
  {
    return this.province;
  }
  
  public void setProvince(String province)
  {
    this.province = province;
  }
  
  public String getCity()
  {
    return this.city;
  }
  
  public void setCity(String city)
  {
    this.city = city;
  }
  
  public String getDistrict()
  {
    return this.district;
  }
  
  public void setDistrict(String district)
  {
    this.district = district;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public Integer getZipCode()
  {
    return this.zipCode;
  }
  
  public void setZipCode(Integer zipCode)
  {
    this.zipCode = zipCode;
  }
  
  public String getConsignee()
  {
    return this.consignee;
  }
  
  public void setConsignee(String consignee)
  {
    this.consignee = consignee;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
}
