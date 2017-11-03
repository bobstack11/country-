package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayOperatorMobileBindResponse;
import java.util.Map;

public class AlipayOperatorMobileBindRequest
  implements AlipayRequest<AlipayOperatorMobileBindResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String checkSigncard;
  private String fReturnUrl;
  private String hasSpi;
  private String operatorName;
  private String provinceName;
  private String sReturnUrl;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setCheckSigncard(String checkSigncard)
  {
    this.checkSigncard = checkSigncard;
  }
  
  public String getCheckSigncard()
  {
    return this.checkSigncard;
  }
  
  public void setfReturnUrl(String fReturnUrl)
  {
    this.fReturnUrl = fReturnUrl;
  }
  
  public String getfReturnUrl()
  {
    return this.fReturnUrl;
  }
  
  public void setHasSpi(String hasSpi)
  {
    this.hasSpi = hasSpi;
  }
  
  public String getHasSpi()
  {
    return this.hasSpi;
  }
  
  public void setOperatorName(String operatorName)
  {
    this.operatorName = operatorName;
  }
  
  public String getOperatorName()
  {
    return this.operatorName;
  }
  
  public void setProvinceName(String provinceName)
  {
    this.provinceName = provinceName;
  }
  
  public String getProvinceName()
  {
    return this.provinceName;
  }
  
  public void setsReturnUrl(String sReturnUrl)
  {
    this.sReturnUrl = sReturnUrl;
  }
  
  public String getsReturnUrl()
  {
    return this.sReturnUrl;
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
    return "alipay.operator.mobile.bind";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("check_signcard", this.checkSigncard);
    txtParams.put("f_return_url", this.fReturnUrl);
    txtParams.put("has_spi", this.hasSpi);
    txtParams.put("operator_name", this.operatorName);
    txtParams.put("province_name", this.provinceName);
    txtParams.put("s_return_url", this.sReturnUrl);
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
  
  public Class<AlipayOperatorMobileBindResponse> getResponseClass()
  {
    return AlipayOperatorMobileBindResponse.class;
  }
}
