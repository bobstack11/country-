package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayPassCodeVerifyResponse;
import java.util.Map;

public class AlipayPassCodeVerifyRequest
  implements AlipayRequest<AlipayPassCodeVerifyResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String extInfo;
  private String operatorId;
  private String operatorType;
  private String verifyCode;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setExtInfo(String extInfo)
  {
    this.extInfo = extInfo;
  }
  
  public String getExtInfo()
  {
    return this.extInfo;
  }
  
  public void setOperatorId(String operatorId)
  {
    this.operatorId = operatorId;
  }
  
  public String getOperatorId()
  {
    return this.operatorId;
  }
  
  public void setOperatorType(String operatorType)
  {
    this.operatorType = operatorType;
  }
  
  public String getOperatorType()
  {
    return this.operatorType;
  }
  
  public void setVerifyCode(String verifyCode)
  {
    this.verifyCode = verifyCode;
  }
  
  public String getVerifyCode()
  {
    return this.verifyCode;
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
    return "alipay.pass.code.verify";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("ext_info", this.extInfo);
    txtParams.put("operator_id", this.operatorId);
    txtParams.put("operator_type", this.operatorType);
    txtParams.put("verify_code", this.verifyCode);
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
  
  public Class<AlipayPassCodeVerifyResponse> getResponseClass()
  {
    return AlipayPassCodeVerifyResponse.class;
  }
}
