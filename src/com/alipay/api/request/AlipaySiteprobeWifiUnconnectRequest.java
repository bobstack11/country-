package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipaySiteprobeWifiUnconnectResponse;
import java.util.Map;

public class AlipaySiteprobeWifiUnconnectRequest
  implements AlipayRequest<AlipaySiteprobeWifiUnconnectResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String deviceId;
  private String deviceMac;
  private String merchantId;
  private String partnerId;
  private String userMac;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
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
    return "alipay.siteprobe.wifi.unconnect";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("device_id", this.deviceId);
    txtParams.put("device_mac", this.deviceMac);
    txtParams.put("merchant_id", this.merchantId);
    txtParams.put("partner_id", this.partnerId);
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
  
  public Class<AlipaySiteprobeWifiUnconnectResponse> getResponseClass()
  {
    return AlipaySiteprobeWifiUnconnectResponse.class;
  }
}
