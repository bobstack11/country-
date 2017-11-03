package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayAssetAccountBindResponse;
import java.util.Map;

public class AlipayAssetAccountBindRequest
  implements AlipayRequest<AlipayAssetAccountBindResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String bindScene;
  private String providerId;
  private String providerUserId;
  private String providerUserName;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setBindScene(String bindScene)
  {
    this.bindScene = bindScene;
  }
  
  public String getBindScene()
  {
    return this.bindScene;
  }
  
  public void setProviderId(String providerId)
  {
    this.providerId = providerId;
  }
  
  public String getProviderId()
  {
    return this.providerId;
  }
  
  public void setProviderUserId(String providerUserId)
  {
    this.providerUserId = providerUserId;
  }
  
  public String getProviderUserId()
  {
    return this.providerUserId;
  }
  
  public void setProviderUserName(String providerUserName)
  {
    this.providerUserName = providerUserName;
  }
  
  public String getProviderUserName()
  {
    return this.providerUserName;
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
    return "alipay.asset.account.bind";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("bind_scene", this.bindScene);
    txtParams.put("provider_id", this.providerId);
    txtParams.put("provider_user_id", this.providerUserId);
    txtParams.put("provider_user_name", this.providerUserName);
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
  
  public Class<AlipayAssetAccountBindResponse> getResponseClass()
  {
    return AlipayAssetAccountBindResponse.class;
  }
}
