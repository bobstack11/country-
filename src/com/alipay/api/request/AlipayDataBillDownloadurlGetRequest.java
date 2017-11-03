package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayDataBillDownloadurlGetResponse;
import java.util.Map;

public class AlipayDataBillDownloadurlGetRequest
  implements AlipayRequest<AlipayDataBillDownloadurlGetResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String billDate;
  private String billType;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setBillDate(String billDate)
  {
    this.billDate = billDate;
  }
  
  public String getBillDate()
  {
    return this.billDate;
  }
  
  public void setBillType(String billType)
  {
    this.billType = billType;
  }
  
  public String getBillType()
  {
    return this.billType;
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
    return "alipay.data.bill.downloadurl.get";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("bill_date", this.billDate);
    txtParams.put("bill_type", this.billType);
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
  
  public Class<AlipayDataBillDownloadurlGetResponse> getResponseClass()
  {
    return AlipayDataBillDownloadurlGetResponse.class;
  }
}
