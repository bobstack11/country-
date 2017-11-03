package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import java.util.Map;

public class AlipaySystemOauthTokenRequest
  implements AlipayRequest<AlipaySystemOauthTokenResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String code;
  private String grantType;
  private String refreshToken;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setGrantType(String grantType)
  {
    this.grantType = grantType;
  }
  
  public String getGrantType()
  {
    return this.grantType;
  }
  
  public void setRefreshToken(String refreshToken)
  {
    this.refreshToken = refreshToken;
  }
  
  public String getRefreshToken()
  {
    return this.refreshToken;
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
    return "alipay.system.oauth.token";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("code", this.code);
    txtParams.put("grant_type", this.grantType);
    txtParams.put("refresh_token", this.refreshToken);
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
  
  public Class<AlipaySystemOauthTokenResponse> getResponseClass()
  {
    return AlipaySystemOauthTokenResponse.class;
  }
}
