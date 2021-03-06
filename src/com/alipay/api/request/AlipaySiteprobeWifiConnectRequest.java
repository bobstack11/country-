package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipaySiteprobeWifiConnectResponse;
import java.util.Map;

public class AlipaySiteprobeWifiConnectRequest
  implements AlipayRequest<AlipaySiteprobeWifiConnectResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private Boolean auth;
  private String deviceId;
  private String deviceMac;
  private String merchantId;
  private String partnerId;
  private String token;
  private String userMac;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setAuth(Boolean auth)
  {
    this.auth = auth;
  }
  
  public Boolean getAuth()
  {
    return this.auth;
  }
  
  public void setDeviceId(String deviceId)
  {
    this.deviceId = deviceId;
  }
  
  public String getDeviceId()
  {
    return this.deviceId;
  }
  
  public void setDeviceMac(String deviceMac)
  {
    this.deviceMac = deviceMac;
  }
  
  public String getDeviceMac()
  {
    return this.deviceMac;
  }
  
  public void setMerchantId(String merchantId)
  {
    this.merchantId = merchantId;
  }
  
  public String getMerchantId()
  {
    return this.merchantId;
  }
  
  public void setPartnerId(String partnerId)
  {
    this.partnerId = partnerId;
  }
  
  public String getPartnerId()
  {
    return this.partnerId;
  }
  
  public void setToken(String token)
  {
    this.token = token;
  }
  
  public String getToken()
  {
    return this.token;
  }
  
  public void setUserMac(String userMac)
  {
    this.userMac = userMac;
  }
  
  public String getUserMac()
  {
    return this.userMac;
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
    return "alipay.siteprobe.wifi.connect";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("auth", this.auth);
    txtParams.put("device_id", this.deviceId);
    txtParams.put("device_mac", this.deviceMac);
    txtParams.put("merchant_id", this.merchantId);
    txtParams.put("partner_id", this.partnerId);
    txtParams.put("token", this.token);
    txtParams.put("user_mac", this.userMac);
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
  
  public Class<AlipaySiteprobeWifiConnectResponse> getResponseClass()
  {
    return AlipaySiteprobeWifiConnectResponse.class;
  }
}
