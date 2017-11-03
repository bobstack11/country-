package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipaySiteprobeDeviceInfoUpdateResponse;
import java.util.Map;

public class AlipaySiteprobeDeviceInfoUpdateRequest
  implements AlipayRequest<AlipaySiteprobeDeviceInfoUpdateResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String bizContent;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setBizContent(String bizContent)
  {
    this.bizContent = bizContent;
  }
  
  public String getBizContent()
  {
    return this.bizContent;
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
    return "alipay.siteprobe.device.info.update";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("biz_content", this.bizContent);
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
  
  public Class<AlipaySiteprobeDeviceInfoUpdateResponse> getResponseClass()
  {
    return AlipaySiteprobeDeviceInfoUpdateResponse.class;
  }
}
