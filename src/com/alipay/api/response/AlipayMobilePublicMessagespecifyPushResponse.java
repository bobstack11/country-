package com.alipay.api.response;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;

public class AlipayMobilePublicMessagespecifyPushResponse
  extends AlipayResponse
{
  private static final long serialVersionUID = 2678856923363656392L;
  @ApiField("code")
  private String code;
  @ApiField("data")
  private String data;
  @ApiField("msg")
  private String msg;
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setData(String data)
  {
    this.data = data;
  }
  
  public String getData()
  {
    return this.data;
  }
  
  public void setMsg(String msg)
  {
    this.msg = msg;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
}
