package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayUserContractGetResponse;
import java.util.Map;

public class AlipayUserContractGetRequest
  implements AlipayRequest<AlipayUserContractGetResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String subscriberUserId;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setSubscriberUserId(String subscriberUserId)
  {
    this.subscriberUserId = subscriberUserId;
  }
  
  public String getSubscriberUserId()
  {
    return this.subscriberUserId;
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
    return "alipay.user.contract.get";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("subscriber_user_id", this.subscriberUserId);
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
  
  public Class<AlipayUserContractGetResponse> getResponseClass()
  {
    return AlipayUserContractGetResponse.class;
  }
}
