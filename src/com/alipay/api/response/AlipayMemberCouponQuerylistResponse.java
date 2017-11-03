package com.alipay.api.response;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.Coupon;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;
import java.util.List;

public class AlipayMemberCouponQuerylistResponse
  extends AlipayResponse
{
  private static final long serialVersionUID = 6231331599258561952L;
  @ApiListField("coupon_list")
  @ApiField("coupon")
  private List<Coupon> couponList;
  @ApiField("error_code")
  private String errorCode;
  @ApiField("error_msg")
  private String errorMsg;
  @ApiField("list_size")
  private String listSize;
  @ApiField("success_code")
  private String successCode;
  @ApiField("total_num")
  private String totalNum;
  
  public void setCouponList(List<Coupon> couponList)
  {
    this.couponList = couponList;
  }
  
  public List<Coupon> getCouponList()
  {
    return this.couponList;
  }
  
  public void setErrorCode(String errorCode)
  {
    this.errorCode = errorCode;
  }
  
  public String getErrorCode()
  {
    return this.errorCode;
  }
  
  public void setErrorMsg(String errorMsg)
  {
    this.errorMsg = errorMsg;
  }
  
  public String getErrorMsg()
  {
    return this.errorMsg;
  }
  
  public void setListSize(String listSize)
  {
    this.listSize = listSize;
  }
  
  public String getListSize()
  {
    return this.listSize;
  }
  
  public void setSuccessCode(String successCode)
  {
    this.successCode = successCode;
  }
  
  public String getSuccessCode()
  {
    return this.successCode;
  }
  
  public void setTotalNum(String totalNum)
  {
    this.totalNum = totalNum;
  }
  
  public String getTotalNum()
  {
    return this.totalNum;
  }
}
