package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayMemberCouponQuerydetailResponse;
import java.util.Map;

public class AlipayMemberCouponQuerydetailRequest
  implements AlipayRequest<AlipayMemberCouponQuerydetailResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String couponNo;
  private String merchantInfo;
  private String userInfo;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setCouponNo(String couponNo)
  {
    this.couponNo = couponNo;
  }
  
  public String getCouponNo()
  {
    return this.couponNo;
  }
  
  public void setMerchantInfo(String merchantInfo)
  {
    this.merchantInfo = merchantInfo;
  }
  
  public String getMerchantInfo()
  {
    return this.merchantInfo;
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
    return "alipay.member.coupon.querydetail";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("coupon_no", this.couponNo);
    txtParams.put("merchant_info", this.merchantInfo);
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
  
  public Class<AlipayMemberCouponQuerydetailResponse> getResponseClass()
  {
    return AlipayMemberCouponQuerydetailResponse.class;
  }
}
