package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayPassTplContentUpdateResponse;
import java.util.Map;

public class AlipayPassTplContentUpdateRequest
  implements AlipayRequest<AlipayPassTplContentUpdateResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String channelId;
  private String serialNumber;
  private String tplParams;
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
  
  public void setSerialNumber(String serialNumber)
  {
    this.serialNumber = serialNumber;
  }
  
  public String getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public void setTplParams(String tplParams)
  {
    this.tplParams = tplParams;
  }
  
  public String getTplParams()
  {
    return this.tplParams;
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
    return "alipay.pass.tpl.content.update";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("channel_id", this.channelId);
    txtParams.put("serial_number", this.serialNumber);
    txtParams.put("tpl_params", this.tplParams);
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
  
  public Class<AlipayPassTplContentUpdateResponse> getResponseClass()
  {
    return AlipayPassTplContentUpdateResponse.class;
  }
}
