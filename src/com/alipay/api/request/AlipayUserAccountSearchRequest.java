package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayUserAccountSearchResponse;
import java.util.Map;

public class AlipayUserAccountSearchRequest
  implements AlipayRequest<AlipayUserAccountSearchResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String endTime;
  private String fields;
  private String pageNo;
  private String pageSize;
  private String startTime;
  private String type;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setFields(String fields)
  {
    this.fields = fields;
  }
  
  public String getFields()
  {
    return this.fields;
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
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getType()
  {
    return this.type;
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
    return "alipay.user.account.search";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("end_time", this.endTime);
    txtParams.put("fields", this.fields);
    txtParams.put("page_no", this.pageNo);
    txtParams.put("page_size", this.pageSize);
    txtParams.put("start_time", this.startTime);
    txtParams.put("type", this.type);
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
  
  public Class<AlipayUserAccountSearchResponse> getResponseClass()
  {
    return AlipayUserAccountSearchResponse.class;
  }
}
