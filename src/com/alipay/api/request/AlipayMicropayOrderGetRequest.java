package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayMicropayOrderGetResponse;
import java.util.Map;

public class AlipayMicropayOrderGetRequest
  implements AlipayRequest<AlipayMicropayOrderGetResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String alipayOrderNo;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setAlipayOrderNo(String alipayOrderNo)
  {
    this.alipayOrderNo = alipayOrderNo;
  }
  
  public String getAlipayOrderNo()
  {
    return this.alipayOrderNo;
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
    return "alipay.micropay.order.get";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("alipay_order_no", this.alipayOrderNo);
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
  
  public Class<AlipayMicropayOrderGetResponse> getResponseClass()
  {
    return AlipayMicropayOrderGetResponse.class;
  }
}
