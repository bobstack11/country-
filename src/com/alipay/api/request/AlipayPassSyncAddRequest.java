package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayPassSyncAddResponse;
import java.util.Map;

public class AlipayPassSyncAddRequest
  implements AlipayRequest<AlipayPassSyncAddResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String fileContent;
  private String outTradeNo;
  private String partnerId;
  private String userId;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setFileContent(String fileContent)
  {
    this.fileContent = fileContent;
  }
  
  public String getFileContent()
  {
    return this.fileContent;
  }
  
  public void setOutTradeNo(String outTradeNo)
  {
    this.outTradeNo = outTradeNo;
  }
  
  public String getOutTradeNo()
  {
    return this.outTradeNo;
  }
  
  public void setPartnerId(String partnerId)
  {
    this.partnerId = partnerId;
  }
  
  public String getPartnerId()
  {
    return this.partnerId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getUserId()
  {
    return this.userId;
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
    return "alipay.pass.sync.add";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("file_content", this.fileContent);
    txtParams.put("out_trade_no", this.outTradeNo);
    txtParams.put("partner_id", this.partnerId);
    txtParams.put("user_id", this.userId);
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
  
  public Class<AlipayPassSyncAddResponse> getResponseClass()
  {
    return AlipayPassSyncAddResponse.class;
  }
}
