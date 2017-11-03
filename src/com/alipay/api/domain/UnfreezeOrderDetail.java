package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;
import java.util.Date;

public class UnfreezeOrderDetail
  extends AlipayObject
{
  private static final long serialVersionUID = 1414131867649824294L;
  @ApiField("alipay_order_no")
  private String alipayOrderNo;
  @ApiField("create_time")
  private Date createTime;
  @ApiField("memo")
  private String memo;
  @ApiField("merchant_order_no")
  private String merchantOrderNo;
  @ApiField("modified_time")
  private Date modifiedTime;
  @ApiField("order_amount")
  private String orderAmount;
  @ApiField("order_status")
  private String orderStatus;
  @ApiField("unfreeze_amount")
  private String unfreezeAmount;
  
  public String getAlipayOrderNo()
  {
    return this.alipayOrderNo;
  }
  
  public void setAlipayOrderNo(String alipayOrderNo)
  {
    this.alipayOrderNo = alipayOrderNo;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public void setMemo(String memo)
  {
    this.memo = memo;
  }
  
  public String getMerchantOrderNo()
  {
    return this.merchantOrderNo;
  }
  
  public void setMerchantOrderNo(String merchantOrderNo)
  {
    this.merchantOrderNo = merchantOrderNo;
  }
  
  public Date getModifiedTime()
  {
    return this.modifiedTime;
  }
  
  public void setModifiedTime(Date modifiedTime)
  {
    this.modifiedTime = modifiedTime;
  }
  
  public String getOrderAmount()
  {
    return this.orderAmount;
  }
  
  public void setOrderAmount(String orderAmount)
  {
    this.orderAmount = orderAmount;
  }
  
  public String getOrderStatus()
  {
    return this.orderStatus;
  }
  
  public void setOrderStatus(String orderStatus)
  {
    this.orderStatus = orderStatus;
  }
  
  public String getUnfreezeAmount()
  {
    return this.unfreezeAmount;
  }
  
  public void setUnfreezeAmount(String unfreezeAmount)
  {
    this.unfreezeAmount = unfreezeAmount;
  }
}
