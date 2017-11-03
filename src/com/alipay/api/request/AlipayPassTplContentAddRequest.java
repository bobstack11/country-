package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayPassTplContentAddResponse;
import java.util.Map;

public class AlipayPassTplContentAddRequest
  implements AlipayRequest<AlipayPassTplContentAddResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String recognitionInfo;
  private String recognitionType;
  private String tplId;
  private String tplParams;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setRecognitionInfo(String recognitionInfo)
  {
    this.recognitionInfo = recognitionInfo;
  }
  
  public String getRecognitionInfo()
  {
    return this.recognitionInfo;
  }
  
  public void setRecognitionType(String recognitionType)
  {
    this.recognitionType = recognitionType;
  }
  
  public String getRecognitionType()
  {
    return this.recognitionType;
  }
  
  public void setTplId(String tplId)
  {
    this.tplId = tplId;
  }
  
  public String getTplId()
  {
    return this.tplId;
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
    return "alipay.pass.tpl.content.add";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("recognition_info", this.recognitionInfo);
    txtParams.put("recognition_type", this.recognitionType);
    txtParams.put("tpl_id", this.tplId);
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
  
  public Class<AlipayPassTplContentAddResponse> getResponseClass()
  {
    return AlipayPassTplContentAddResponse.class;
  }
}
