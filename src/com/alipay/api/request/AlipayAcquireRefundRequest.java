package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayAcquireRefundResponse;
import java.util.Map;

public class AlipayAcquireRefundRequest
  implements AlipayRequest<AlipayAcquireRefundResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String operatorId;
  private String operatorType;
  private String outRequestNo;
  private String outTradeNo;
  private String refIds;
  private String refundAmount;
  private String refundReason;
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
  
  public void setOperatorType(String operatorType)
  {
    this.operatorType = operatorType;
  }
  
  public String getOperatorType()
  {
    return this.operatorType;
  }
  
  public void setOutRequestNo(String outRequestNo)
  {
    this.outRequestNo = outRequestNo;
  }
  
  public String getOutRequestNo()
  {
    return this.outRequestNo;
  }
  
  public void setOutTradeNo(String outTradeNo)
  {
    this.outTradeNo = outTradeNo;
  }
  
  public String getOutTradeNo()
  {
    return this.outTradeNo;
  }
  
  public void setRefIds(String refIds)
  {
    this.refIds = refIds;
  }
  
  public String getRefIds()
  {
    return this.refIds;
  }
  
  public void setRefundAmount(String refundAmount)
  {
    this.refundAmount = refundAmount;
  }
  
  public String getRefundAmount()
  {
    return this.refundAmount;
  }
  
  public void setRefundReason(String refundReason)
  {
    this.refundReason = refundReason;
  }
  
  public String getRefundReason()
  {
    return this.refundReason;
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
    return "alipay.acquire.refund";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("operator_id", this.operatorId);
    txtParams.put("operator_type", this.operatorType);
    txtParams.put("out_request_no", this.outRequestNo);
    txtParams.put("out_trade_no", this.outTradeNo);
    txtParams.put("ref_ids", this.refIds);
    txtParams.put("refund_amount", this.refundAmount);
    txtParams.put("refund_reason", this.refundReason);
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
  
  public Class<AlipayAcquireRefundResponse> getResponseClass()
  {
    return AlipayAcquireRefundResponse.class;
  }
}
