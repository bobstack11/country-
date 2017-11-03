package com.alipay.api.response;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;

public class AlipayEbppBillGetResponse
  extends AlipayResponse
{
  private static final long serialVersionUID = 2351763819139315165L;
  @ApiField("alipay_order_no")
  private String alipayOrderNo;
  @ApiField("bill_date")
  private String billDate;
  @ApiField("bill_key")
  private String billKey;
  @ApiField("charge_inst")
  private String chargeInst;
  @ApiField("charge_inst_name")
  private String chargeInstName;
  @ApiField("merchant_order_no")
  private String merchantOrderNo;
  @ApiField("order_status")
  private String orderStatus;
  @ApiField("order_type")
  private String orderType;
  @ApiField("owner_name")
  private String ownerName;
  @ApiField("pay_amount")
  private String payAmount;
  @ApiField("pay_time")
  private String payTime;
  @ApiField("service_amount")
  private String serviceAmount;
  @ApiField("sub_order_type")
  private String subOrderType;
  @ApiField("traffic_location")
  private String trafficLocation;
  @ApiField("traffic_regulations")
  private String trafficRegulations;
  
  public void setAlipayOrderNo(String alipayOrderNo)
  {
    this.alipayOrderNo = alipayOrderNo;
  }
  
  public String getAlipayOrderNo()
  {
    return this.alipayOrderNo;
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
  
  public void setChargeInstName(String chargeInstName)
  {
    this.chargeInstName = chargeInstName;
  }
  
  public String getChargeInstName()
  {
    return this.chargeInstName;
  }
  
  public void setMerchantOrderNo(String merchantOrderNo)
  {
    this.merchantOrderNo = merchantOrderNo;
  }
  
  public String getMerchantOrderNo()
  {
    return this.merchantOrderNo;
  }
  
  public void setOrderStatus(String orderStatus)
  {
    this.orderStatus = orderStatus;
  }
  
  public String getOrderStatus()
  {
    return this.orderStatus;
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
  
  public void setPayTime(String payTime)
  {
    this.payTime = payTime;
  }
  
  public String getPayTime()
  {
    return this.payTime;
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
}
