package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayZdatafrontDatatransferedSendResponse;
import java.util.Map;

public class AlipayZdatafrontDatatransferedSendRequest
  implements AlipayRequest<AlipayZdatafrontDatatransferedSendResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String data;
  private String identity;
  private String typeId;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setData(String data)
  {
    this.data = data;
  }
  
  public String getData()
  {
    return this.data;
  }
  
  public void setIdentity(String identity)
  {
    this.identity = identity;
  }
  
  public String getIdentity()
  {
    return this.identity;
  }
  
  public void setTypeId(String typeId)
  {
    this.typeId = typeId;
  }
  
  public String getTypeId()
  {
    return this.typeId;
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
    return "alipay.zdatafront.datatransfered.send";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("data", this.data);
    txtParams.put("identity", this.identity);
    txtParams.put("type_id", this.typeId);
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
  
  public Class<AlipayZdatafrontDatatransferedSendResponse> getResponseClass()
  {
    return AlipayZdatafrontDatatransferedSendResponse.class;
  }
}
