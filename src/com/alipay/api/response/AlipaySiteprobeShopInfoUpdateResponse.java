package com.alipay.api.response;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;

public class AlipaySiteprobeShopInfoUpdateResponse
  extends AlipayResponse
{
  private static final long serialVersionUID = 5414714192222952946L;
  @ApiField("code")
  private Long code;
  @ApiField("msg")
  private String msg;
  
  public void setCode(Long code)
  {
    this.code = code;
  }
  
  public Long getCode()
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