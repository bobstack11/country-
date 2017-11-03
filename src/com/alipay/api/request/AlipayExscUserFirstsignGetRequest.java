package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayExscUserFirstsignGetResponse;
import java.util.Map;

public class AlipayExscUserFirstsignGetRequest
  implements AlipayRequest<AlipayExscUserFirstsignGetResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String alipayId;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setAlipayId(String alipayId)
  {
    this.alipayId = alipayId;
  }
  
  public String getAlipayId()
  {
    return this.alipayId;
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
    return "alipay.exsc.user.firstsign.get";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("alipay_id", this.alipayId);
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
  
  public Class<AlipayExscUserFirstsignGetResponse> getResponseClass()
  {
    return AlipayExscUserFirstsignGetResponse.class;
  }
}
