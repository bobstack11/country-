package com.alipay.api.response;

import com.alipay.api.AlipayResponse;
import com.alipay.api.internal.mapping.ApiField;
import java.util.Date;

public class AlipayPointOrderGetResponse
  extends AlipayResponse
{
  private static final long serialVersionUID = 8398443322198617396L;
  @ApiField("alipay_order_no")
  private String alipayOrderNo;
  @ApiField("create_time")
  private Date createTime;
  @ApiField("dispatch_user_id")
  private String dispatchUserId;
  @ApiField("memo")
  private String memo;
  @ApiField("merchant_order_no")
  private String merchantOrderNo;
  @ApiField("order_status")
  private String orderStatus;
  @ApiField("point_count")
  private Long pointCount;
  @ApiField("receive_user_id")
  private String receiveUserId;
  
  public void setAlipayOrderNo(String alipayOrderNo)
  {
    this.alipayOrderNo = alipayOrderNo;
  }
  
  public String getAlipayOrderNo()
  {
    return this.alipayOrderNo;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setDispatchUserId(String dispatchUserId)
  {
    this.dispatchUserId = dispatchUserId;
  }
  
  public String getDispatchUserId()
  {
    return this.dispatchUserId;
  }
  
  public void setMemo(String memo)
  {
    this.memo = memo;
  }
  
  public String getMemo()
  {
    return this.memo;
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
  
  public void setPointCount(Long pointCount)
  {
    this.pointCount = pointCount;
  }
  
  public Long getPointCount()
  {
    return this.pointCount;
  }
  
  public void setReceiveUserId(String receiveUserId)
  {
    this.receiveUserId = receiveUserId;
  }
  
  public String getReceiveUserId()
  {
    return this.receiveUserId;
  }
}
