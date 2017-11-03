package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayTransferThirdpartyBillCreateResponse;
import java.util.Map;

public class AlipayTransferThirdpartyBillCreateRequest
  implements AlipayRequest<AlipayTransferThirdpartyBillCreateResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String amount;
  private String currency;
  private String extParam;
  private String memo;
  private String partnerId;
  private String payeeAccount;
  private String payeeType;
  private String payerAccount;
  private String payerType;
  private String paymentId;
  private String paymentSource;
  private String title;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setAmount(String amount)
  {
    this.amount = amount;
  }
  
  public String getAmount()
  {
    return this.amount;
  }
  
  public void setCurrency(String currency)
  {
    this.currency = currency;
  }
  
  public String getCurrency()
  {
    return this.currency;
  }
  
  public void setExtParam(String extParam)
  {
    this.extParam = extParam;
  }
  
  public String getExtParam()
  {
    return this.extParam;
  }
  
  public void setMemo(String memo)
  {
    this.memo = memo;
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public void setPartnerId(String partnerId)
  {
    this.partnerId = partnerId;
  }
  
  public String getPartnerId()
  {
    return this.partnerId;
  }
  
  public void setPayeeAccount(String payeeAccount)
  {
    this.payeeAccount = payeeAccount;
  }
  
  public String getPayeeAccount()
  {
    return this.payeeAccount;
  }
  
  public void setPayeeType(String payeeType)
  {
    this.payeeType = payeeType;
  }
  
  public String getPayeeType()
  {
    return this.payeeType;
  }
  
  public void setPayerAccount(String payerAccount)
  {
    this.payerAccount = payerAccount;
  }
  
  public String getPayerAccount()
  {
    return this.payerAccount;
  }
  
  public void setPayerType(String payerType)
  {
    this.payerType = payerType;
  }
  
  public String getPayerType()
  {
    return this.payerType;
  }
  
  public void setPaymentId(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  public String getPaymentId()
  {
    return this.paymentId;
  }
  
  public void setPaymentSource(String paymentSource)
  {
    this.paymentSource = paymentSource;
  }
  
  public String getPaymentSource()
  {
    return this.paymentSource;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getTitle()
  {
    return this.title;
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
    return "alipay.transfer.thirdparty.bill.create";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("amount", this.amount);
    txtParams.put("currency", this.currency);
    txtParams.put("ext_param", this.extParam);
    txtParams.put("memo", this.memo);
    txtParams.put("partner_id", this.partnerId);
    txtParams.put("payee_account", this.payeeAccount);
    txtParams.put("payee_type", this.payeeType);
    txtParams.put("payer_account", this.payerAccount);
    txtParams.put("payer_type", this.payerType);
    txtParams.put("payment_id", this.paymentId);
    txtParams.put("payment_source", this.paymentSource);
    txtParams.put("title", this.title);
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
  
  public Class<AlipayTransferThirdpartyBillCreateResponse> getResponseClass()
  {
    return AlipayTransferThirdpartyBillCreateResponse.class;
  }
}
