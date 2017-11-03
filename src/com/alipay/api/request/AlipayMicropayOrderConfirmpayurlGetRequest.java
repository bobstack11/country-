package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayMicropayOrderConfirmpayurlGetResponse;
import java.util.Map;

public class AlipayMicropayOrderConfirmpayurlGetRequest
  implements AlipayRequest<AlipayMicropayOrderConfirmpayurlGetResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String alipayOrderNo;
  private String amount;
  private String memo;
  private String receiveUserId;
  private String transferOutOrderNo;
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
  
  public void setAmount(String amount)
  {
    this.amount = amount;
  }
  
  public String getAmount()
  {
    return this.amount;
  }
  
  public void setMemo(String memo)
  {
    this.memo = memo;
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public void setReceiveUserId(String receiveUserId)
  {
    this.receiveUserId = receiveUserId;
  }
  
  public String getReceiveUserId()
  {
    return this.receiveUserId;
  }
  
  public void setTransferOutOrderNo(String transferOutOrderNo)
  {
    this.transferOutOrderNo = transferOutOrderNo;
  }
  
  public String getTransferOutOrderNo()
  {
    return this.transferOutOrderNo;
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
    return "alipay.micropay.order.confirmpayurl.get";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("alipay_order_no", this.alipayOrderNo);
    txtParams.put("amount", this.amount);
    txtParams.put("memo", this.memo);
    txtParams.put("receive_user_id", this.receiveUserId);
    txtParams.put("transfer_out_order_no", this.transferOutOrderNo);
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
  
  public Class<AlipayMicropayOrderConfirmpayurlGetResponse> getResponseClass()
  {
    return AlipayMicropayOrderConfirmpayurlGetResponse.class;
  }
}
