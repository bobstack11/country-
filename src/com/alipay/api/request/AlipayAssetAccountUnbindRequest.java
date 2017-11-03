package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayAssetAccountUnbindResponse;
import java.util.Map;

public class AlipayAssetAccountUnbindRequest
  implements AlipayRequest<AlipayAssetAccountUnbindResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String providerId;
  private String providerUserId;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setProviderId(String providerId)
  {
    this.providerId = providerId;
  }
  
  public String getProviderId()
  {
    return this.providerId;
  }
  
  public void setProviderUserId(String providerUserId)
  {
    this.providerUserId = providerUserId;
  }
  
  public String getProviderUserId()
  {
    return this.providerUserId;
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
    return "alipay.asset.account.unbind";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("provider_id", this.providerId);
    txtParams.put("provider_user_id", this.providerUserId);
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
  
  public Class<AlipayAssetAccountUnbindResponse> getResponseClass()
  {
    return AlipayAssetAccountUnbindResponse.class;
  }
}
