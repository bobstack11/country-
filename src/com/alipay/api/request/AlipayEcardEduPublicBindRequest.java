package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayEcardEduPublicBindResponse;
import java.util.Map;

public class AlipayEcardEduPublicBindRequest
  implements AlipayRequest<AlipayEcardEduPublicBindResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String agentCode;
  private String agreementId;
  private String alipayUserId;
  private String cardName;
  private String cardNo;
  private String publicId;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setAgentCode(String agentCode)
  {
    this.agentCode = agentCode;
  }
  
  public String getAgentCode()
  {
    return this.agentCode;
  }
  
  public void setAgreementId(String agreementId)
  {
    this.agreementId = agreementId;
  }
  
  public String getAgreementId()
  {
    return this.agreementId;
  }
  
  public void setAlipayUserId(String alipayUserId)
  {
    this.alipayUserId = alipayUserId;
  }
  
  public String getAlipayUserId()
  {
    return this.alipayUserId;
  }
  
  public void setCardName(String cardName)
  {
    this.cardName = cardName;
  }
  
  public String getCardName()
  {
    return this.cardName;
  }
  
  public void setCardNo(String cardNo)
  {
    this.cardNo = cardNo;
  }
  
  public String getCardNo()
  {
    return this.cardNo;
  }
  
  public void setPublicId(String publicId)
  {
    this.publicId = publicId;
  }
  
  public String getPublicId()
  {
    return this.publicId;
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
    return "alipay.ecard.edu.public.bind";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("agent_code", this.agentCode);
    txtParams.put("agreement_id", this.agreementId);
    txtParams.put("alipay_user_id", this.alipayUserId);
    txtParams.put("card_name", this.cardName);
    txtParams.put("card_no", this.cardNo);
    txtParams.put("public_id", this.publicId);
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
  
  public Class<AlipayEcardEduPublicBindResponse> getResponseClass()
  {
    return AlipayEcardEduPublicBindResponse.class;
  }
}
