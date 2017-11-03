package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayUserTestResponse;
import java.util.List;
import java.util.Map;

public class AlipayUserTestRequest
  implements AlipayRequest<AlipayUserTestResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private List<String> userinfo;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setUserinfo(List<String> userinfo)
  {
    this.userinfo = userinfo;
  }
  
  public List<String> getUserinfo()
  {
    return this.userinfo;
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
    return "alipay.user.test";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("userinfo", this.userinfo);
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
  
  public Class<AlipayUserTestResponse> getResponseClass()
  {
    return AlipayUserTestResponse.class;
  }
}
