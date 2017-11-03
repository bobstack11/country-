package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayEbppBillAddResponse;
import java.util.Map;

public class AlipayEbppBillAddRequest
  implements AlipayRequest<AlipayEbppBillAddResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String bankBillNo;
  private String billDate;
  private String billKey;
  private String chargeInst;
  private String extendField;
  private String merchantOrderNo;
  private String mobile;
  private String orderType;
  private String ownerName;
  private String payAmount;
  private String serviceAmount;
  private String subOrderType;
  private String trafficLocation;
  private String trafficRegulations;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setBankBillNo(String bankBillNo)
  {
    this.bankBillNo = bankBillNo;
  }
  
  public String getBankBillNo()
  {
    return this.bankBillNo;
  }
  
  public void setBillDate(String billDate)
  {
    this.billDate = billDate;
  }
  
  public String getBillDate()
  {
    return this.billDate;
  }
  
  public void setBillKey(String billKey)
  {
    this.billKey = billKey;
  }
  
  public String getBillKey()
  {
    return this.billKey;
  }
  
  public void setChargeInst(String chargeInst)
  {
    this.chargeInst = chargeInst;
  }
  
  public String getChargeInst()
  {
    return this.chargeInst;
  }
  
  public void setExtendField(String extendField)
  {
    this.extendField = extendField;
  }
  
  public String getExtendField()
  {
    return this.extendField;
  }
  
  public void setMerchantOrderNo(String merchantOrderNo)
  {
    this.merchantOrderNo = merchantOrderNo;
  }
  
  public String getMerchantOrderNo()
  {
    return this.merchantOrderNo;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setOrderType(String orderType)
  {
    this.orderType = orderType;
  }
  
  public String getOrderType()
  {
    return this.orderType;
  }
  
  public void setOwnerName(String ownerName)
  {
    this.ownerName = ownerName;
  }
  
  public String getOwnerName()
  {
    return this.ownerName;
  }
  
  public void setPayAmount(String payAmount)
  {
    this.payAmount = payAmount;
  }
  
  public String getPayAmount()
  {
    return this.payAmount;
  }
  
  public void setServiceAmount(String serviceAmount)
  {
    this.serviceAmount = serviceAmount;
  }
  
  public String getServiceAmount()
  {
    return this.serviceAmount;
  }
  
  public void setSubOrderType(String subOrderType)
  {
    this.subOrderType = subOrderType;
  }
  
  public String getSubOrderType()
  {
    return this.subOrderType;
  }
  
  public void setTrafficLocation(String trafficLocation)
  {
    this.trafficLocation = trafficLocation;
  }
  
  public String getTrafficLocation()
  {
    return this.trafficLocation;
  }
  
  public void setTrafficRegulations(String trafficRegulations)
  {
    this.trafficRegulations = trafficRegulations;
  }
  
  public String getTrafficRegulations()
  {
    return this.trafficRegulations;
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
    return "alipay.ebpp.bill.add";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("bank_bill_no", this.bankBillNo);
    txtParams.put("bill_date", this.billDate);
    txtParams.put("bill_key", this.billKey);
    txtParams.put("charge_inst", this.chargeInst);
    txtParams.put("extend_field", this.extendField);
    txtParams.put("merchant_order_no", this.merchantOrderNo);
    txtParams.put("mobile", this.mobile);
    txtParams.put("order_type", this.orderType);
    txtParams.put("owner_name", this.ownerName);
    txtParams.put("pay_amount", this.payAmount);
    txtParams.put("service_amount", this.serviceAmount);
    txtParams.put("sub_order_type", this.subOrderType);
    txtParams.put("traffic_location", this.trafficLocation);
    txtParams.put("traffic_regulations", this.trafficRegulations);
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
  
  public Class<AlipayEbppBillAddResponse> getResponseClass()
  {
    return AlipayEbppBillAddResponse.class;
  }
}
