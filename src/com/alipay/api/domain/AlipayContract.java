package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;
import java.util.Date;

public class AlipayContract
  extends AlipayObject
{
  private static final long serialVersionUID = 5688616575265461122L;
  @ApiField("alipay_user_id")
  private String alipayUserId;
  @ApiField("contract_content")
  private String contractContent;
  @ApiField("end_time")
  private Date endTime;
  @ApiField("page_url")
  private String pageUrl;
  @ApiField("start_time")
  private Date startTime;
  @ApiField("subscribe")
  private Boolean subscribe;
  
  public String getAlipayUserId()
  {
    return this.alipayUserId;
  }
  
  public void setAlipayUserId(String alipayUserId)
  {
    this.alipayUserId = alipayUserId;
  }
  
  public String getContractContent()
  {
    return this.contractContent;
  }
  
  public void setContractContent(String contractContent)
  {
    this.contractContent = contractContent;
  }
  
  public Date getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(Date endTime)
  {
    this.endTime = endTime;
  }
  
  public String getPageUrl()
  {
    return this.pageUrl;
  }
  
  public void setPageUrl(String pageUrl)
  {
    this.pageUrl = pageUrl;
  }
  
  public Date getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(Date startTime)
  {
    this.startTime = startTime;
  }
  
  public Boolean getSubscribe()
  {
    return this.subscribe;
  }
  
  public void setSubscribe(Boolean subscribe)
  {
    this.subscribe = subscribe;
  }
}
