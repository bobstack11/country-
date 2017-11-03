package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayPassTplUpdateResponse;
import java.util.Map;

public class AlipayPassTplUpdateRequest
  implements AlipayRequest<AlipayPassTplUpdateResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String tplContent;
  private String tplId;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setTplContent(String tplContent)
  {
    this.tplContent = tplContent;
  }
  
  public String getTplContent()
  {
    return this.tplContent;
  }
  
  public void setTplId(String tplId)
  {
    this.tplId = tplId;
  }
  
  public String getTplId()
  {
    return this.tplId;
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
    return "alipay.pass.tpl.update";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("tpl_content", this.tplContent);
    txtParams.put("tpl_id", this.tplId);
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
  
  public Class<AlipayPassTplUpdateResponse> getResponseClass()
  {
    return AlipayPassTplUpdateResponse.class;
  }
}
