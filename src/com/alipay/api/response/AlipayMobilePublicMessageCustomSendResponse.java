package com.alipay.api.response;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;

public class AlipayMobilePublicMessageCustomSendResponse
  extends AlipayResponse
{
  private static final long serialVersionUID = 3717996648771546434L;
  @ApiField("code")
  private String code;
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
  
  public void setMsg(String msg)
  {
    this.msg = msg;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
}
