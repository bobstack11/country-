package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayAcquireCloseResponse;
import java.util.Map;

public class AlipayAcquireCloseRequest
  implements AlipayRequest<AlipayAcquireCloseResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String operatorId;
  private String outTradeNo;
  private String tradeNo;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setOperatorId(String operatorId)
  {
    this.operatorId = operatorId;
  }
  
  public String getOperatorId()
  {
    return this.operatorId;
  }
  
  public void setOutTradeNo(String outTradeNo)
  {
    this.outTradeNo = outTradeNo;
  }
  
  public String getOutTradeNo()
  {
    return this.outTradeNo;
  }
  
  public void setTradeNo(String tradeNo)
  {
    this.tradeNo = tradeNo;
  }
  
  public String getTradeNo()
  {
    return this.tradeNo;
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
    return "alipay.acquire.close";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("operator_id", this.operatorId);
    txtParams.put("out_trade_no", this.outTradeNo);
    txtParams.put("trade_no", this.tradeNo);
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
  
  public Class<AlipayAcquireCloseResponse> getResponseClass()
  {
    return AlipayAcquireCloseResponse.class;
  }
}
