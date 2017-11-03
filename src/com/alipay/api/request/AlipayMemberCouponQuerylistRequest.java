package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayMemberCouponQuerylistResponse;
import java.util.Map;

public class AlipayMemberCouponQuerylistRequest
  implements AlipayRequest<AlipayMemberCouponQuerylistResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String merchantInfo;
  private String pageNo;
  private String pageSize;
  private String status;
  private String userInfo;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setMerchantInfo(String merchantInfo)
  {
    this.merchantInfo = merchantInfo;
  }
  
  public String getMerchantInfo()
  {
    return this.merchantInfo;
  }
  
  public void setPageNo(String pageNo)
  {
    this.pageNo = pageNo;
  }
  
  public String getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageSize(String pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public String getPageSize()
  {
    return this.pageSize;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setUserInfo(String userInfo)
  {
    this.userInfo = userInfo;
  }
  
  public String getUserInfo()
  {
    return this.userInfo;
  }
  
  public String getApiVersion()
  {
    return this.apiVersion;
  }
  
  public void setApiVersion(String apiVersion)
  {
    this.apiVersion = apiVersion;
  }
  
  public void setTerminalType(String terminalType)
  {
    this.terminalType = terminalType;
  }
  
  public String getTerminalType()
  {
    return this.terminalType;
  }
  
  public void setTerminalInfo(String terminalInfo)
  {
    this.terminalInfo = terminalInfo;
  }
  
  public String getTerminalInfo()
  {
    return this.terminalInfo;
  }
  
  public void setProdCode(String prodCode)
  {
    this.prodCode = prodCode;
  }
  
  public String getProdCode()
  {
    return this.prodCode;
  }
  
  public String getApiMethodName()
  {
    return "alipay.member.coupon.querylist";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("merchant_info", this.merchantInfo);
    txtParams.put("page_no", this.pageNo);
    txtParams.put("page_size", this.pageSize);
    txtParams.put("status", this.status);
    txtParams.put("user_info", this.userInfo);
    if (this.udfParams != null) {
      txtParams.putAll(this.udfParams);
    }
    return txtParams;
  }
  
  public void putOtherTextParam(String key, String value)
  {
    if (this.udfParams == null) {
      this.udfParams = new AlipayHashMap();
    }
    this.udfParams.put(key, value);
  }
  
  public Class<AlipayMemberCouponQuerylistResponse> getResponseClass()
  {
    return AlipayMemberCouponQuerylistResponse.class;
  }
}
