package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayEbppBillPayResponse;
import java.util.Map;

public class AlipayEbppBillPayRequest
  implements AlipayRequest<AlipayEbppBillPayResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String alipayOrderNo;
  private String dispatchClusterTarget;
  private String extend;
  private String merchantOrderNo;
  private String orderType;
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
  
  public void setDispatchClusterTarget(String dispatchClusterTarget)
  {
    this.dispatchClusterTarget = dispatchClusterTarget;
  }
  
  public String getDispatchClusterTarget()
  {
    return this.dispatchClusterTarget;
  }
  
  public void setExtend(String extend)
  {
    this.extend = extend;
  }
  
  public String getExtend()
  {
    return this.extend;
  }
  
  public void setMerchantOrderNo(String merchantOrderNo)
  {
    this.merchantOrderNo = merchantOrderNo;
  }
  
  public String getMerchantOrderNo()
  {
    return this.merchantOrderNo;
  }
  
  public void setOrderType(String orderType)
  {
    this.orderType = orderType;
  }
  
  public String getOrderType()
  {
    return this.orderType;
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
    return "alipay.ebpp.bill.pay";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("alipay_order_no", this.alipayOrderNo);
    txtParams.put("dispatch_cluster_target", this.dispatchClusterTarget);
    txtParams.put("extend", this.extend);
    txtParams.put("merchant_order_no", this.merchantOrderNo);
    txtParams.put("order_type", this.orderType);
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
  
  public Class<AlipayEbppBillPayResponse> getResponseClass()
  {
    return AlipayEbppBillPayResponse.class;
  }
}
