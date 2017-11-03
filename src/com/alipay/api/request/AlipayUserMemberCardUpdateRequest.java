package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayUserMemberCardUpdateResponse;
import java.util.Map;

public class AlipayUserMemberCardUpdateRequest
  implements AlipayRequest<AlipayUserMemberCardUpdateResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String balance;
  private String bizCardNo;
  private String cardMerchantInfo;
  private String extInfo;
  private String externalCardNo;
  private String level;
  private String orrurTime;
  private Long point;
  private String requestFrom;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setBalance(String balance)
  {
    this.balance = balance;
  }
  
  public String getBalance()
  {
    return this.balance;
  }
  
  public void setBizCardNo(String bizCardNo)
  {
    this.bizCardNo = bizCardNo;
  }
  
  public String getBizCardNo()
  {
    return this.bizCardNo;
  }
  
  public void setCardMerchantInfo(String cardMerchantInfo)
  {
    this.cardMerchantInfo = cardMerchantInfo;
  }
  
  public String getCardMerchantInfo()
  {
    return this.cardMerchantInfo;
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
  
  public void setLevel(String level)
  {
    this.level = level;
  }
  
  public String getLevel()
  {
    return this.level;
  }
  
  public void setOrrurTime(String orrurTime)
  {
    this.orrurTime = orrurTime;
  }
  
  public String getOrrurTime()
  {
    return this.orrurTime;
  }
  
  public void setPoint(Long point)
  {
    this.point = point;
  }
  
  public Long getPoint()
  {
    return this.point;
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
    return "alipay.user.member.card.update";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("balance", this.balance);
    txtParams.put("biz_card_no", this.bizCardNo);
    txtParams.put("card_merchant_info", this.cardMerchantInfo);
    txtParams.put("ext_info", this.extInfo);
    txtParams.put("external_card_no", this.externalCardNo);
    txtParams.put("level", this.level);
    txtParams.put("orrur_time", this.orrurTime);
    txtParams.put("point", this.point);
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
  
  public Class<AlipayUserMemberCardUpdateResponse> getResponseClass()
  {
    return AlipayUserMemberCardUpdateResponse.class;
  }
}
