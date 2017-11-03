package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayUserTradeSearchResponse;
import java.util.Map;

public class AlipayUserTradeSearchRequest
  implements AlipayRequest<AlipayUserTradeSearchResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String alipayOrderNo;
  private String endTime;
  private String merchantOrderNo;
  private String orderFrom;
  private String orderStatus;
  private String orderType;
  private String pageNo;
  private String pageSize;
  private String startTime;
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
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setMerchantOrderNo(String merchantOrderNo)
  {
    this.merchantOrderNo = merchantOrderNo;
  }
  
  public String getMerchantOrderNo()
  {
    return this.merchantOrderNo;
  }
  
  public void setOrderFrom(String orderFrom)
  {
    this.orderFrom = orderFrom;
  }
  
  public String getOrderFrom()
  {
    return this.orderFrom;
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
  
  public void setPageNo(String pageNo)
  {
    this.pageNo = pageNo;
  }
  
  public String getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageSize(String pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public String getPageSize()
  {
    return this.pageSize;
  }
  
  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }
  
  public String getStartTime()
  {
    return this.startTime;
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
    return "alipay.user.trade.search";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("alipay_order_no", this.alipayOrderNo);
    txtParams.put("end_time", this.endTime);
    txtParams.put("merchant_order_no", this.merchantOrderNo);
    txtParams.put("order_from", this.orderFrom);
    txtParams.put("order_status", this.orderStatus);
    txtParams.put("order_type", this.orderType);
    txtParams.put("page_no", this.pageNo);
    txtParams.put("page_size", this.pageSize);
    txtParams.put("start_time", this.startTime);
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
  
  public Class<AlipayUserTradeSearchResponse> getResponseClass()
  {
    return AlipayUserTradeSearchResponse.class;
  }
}
