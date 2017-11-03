package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayPassSyncUpdateResponse;
import java.util.Map;

public class AlipayPassSyncUpdateRequest
  implements AlipayRequest<AlipayPassSyncUpdateResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String channelId;
  private String extInfo;
  private String pass;
  private String serialNumber;
  private String status;
  private String verifyCode;
  private String verifyType;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setChannelId(String channelId)
  {
    this.channelId = channelId;
  }
  
  public String getChannelId()
  {
    return this.channelId;
  }
  
  public void setExtInfo(String extInfo)
  {
    this.extInfo = extInfo;
  }
  
  public String getExtInfo()
  {
    return this.extInfo;
  }
  
  public void setPass(String pass)
  {
    this.pass = pass;
  }
  
  public String getPass()
  {
    return this.pass;
  }
  
  public void setSerialNumber(String serialNumber)
  {
    this.serialNumber = serialNumber;
  }
  
  public String getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setVerifyCode(String verifyCode)
  {
    this.verifyCode = verifyCode;
  }
  
  public String getVerifyCode()
  {
    return this.verifyCode;
  }
  
  public void setVerifyType(String verifyType)
  {
    this.verifyType = verifyType;
  }
  
  public String getVerifyType()
  {
    return this.verifyType;
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
    return "alipay.pass.sync.update";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("channel_id", this.channelId);
    txtParams.put("ext_info", this.extInfo);
    txtParams.put("pass", this.pass);
    txtParams.put("serial_number", this.serialNumber);
    txtParams.put("status", this.status);
    txtParams.put("verify_code", this.verifyCode);
    txtParams.put("verify_type", this.verifyType);
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
  
  public Class<AlipayPassSyncUpdateResponse> getResponseClass()
  {
    return AlipayPassSyncUpdateResponse.class;
  }
}
