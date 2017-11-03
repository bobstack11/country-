package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayMemberCardOpenResponse;
import java.util.Map;

public class AlipayMemberCardOpenRequest
  implements AlipayRequest<AlipayMemberCardOpenResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String bizSerialNo;
  private String cardMerchantInfo;
  private String cardUserInfo;
  private String extInfo;
  private String externalCardNo;
  private String requestFrom;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setBizSerialNo(String bizSerialNo)
  {
    this.bizSerialNo = bizSerialNo;
  }
  
  public String getBizSerialNo()
  {
    return this.bizSerialNo;
  }
  
  public void setCardMerchantInfo(String cardMerchantInfo)
  {
    this.cardMerchantInfo = cardMerchantInfo;
  }
  
  public String getCardMerchantInfo()
  {
    return this.cardMerchantInfo;
  }
  
  public void setCardUserInfo(String cardUserInfo)
  {
    this.cardUserInfo = cardUserInfo;
  }
  
  public String getCardUserInfo()
  {
    return this.cardUserInfo;
  }
  
  public void setExtInfo(String extInfo)
  {
    this.extInfo = extInfo;
  }
  
  public String getExtInfo()
  {
    return this.extInfo;
  }
  
  public void setExternalCardNo(String externalCardNo)
  {
    this.externalCardNo = externalCardNo;
  }
  
  public String getExternalCardNo()
  {
    return this.externalCardNo;
  }
  
  public void setRequestFrom(String requestFrom)
  {
    this.requestFrom = requestFrom;
  }
  
  public String getRequestFrom()
  {
    return this.requestFrom;
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
    return "alipay.member.card.open";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("biz_serial_no", this.bizSerialNo);
    txtParams.put("card_merchant_info", this.cardMerchantInfo);
    txtParams.put("card_user_info", this.cardUserInfo);
    txtParams.put("ext_info", this.extInfo);
    txtParams.put("external_card_no", this.externalCardNo);
    txtParams.put("request_from", this.requestFrom);
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
  
  public Class<AlipayMemberCardOpenResponse> getResponseClass()
  {
    return AlipayMemberCardOpenResponse.class;
  }
}
