package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayAcquirePrecreateResponse;
import java.util.Map;

public class AlipayAcquirePrecreateRequest
  implements AlipayRequest<AlipayAcquirePrecreateResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String body;
  private String channelParameters;
  private String currency;
  private String extendParams;
  private String goodsDetail;
  private String itBPay;
  private String operatorCode;
  private String operatorId;
  private String outTradeNo;
  private String price;
  private String quantity;
  private String royaltyParameters;
  private String royaltyType;
  private String sellerEmail;
  private String sellerId;
  private String showUrl;
  private String subject;
  private String totalFee;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setBody(String body)
  {
    this.body = body;
  }
  
  public String getBody()
  {
    return this.body;
  }
  
  public void setChannelParameters(String channelParameters)
  {
    this.channelParameters = channelParameters;
  }
  
  public String getChannelParameters()
  {
    return this.channelParameters;
  }
  
  public void setCurrency(String currency)
  {
    this.currency = currency;
  }
  
  public String getCurrency()
  {
    return this.currency;
  }
  
  public void setExtendParams(String extendParams)
  {
    this.extendParams = extendParams;
  }
  
  public String getExtendParams()
  {
    return this.extendParams;
  }
  
  public void setGoodsDetail(String goodsDetail)
  {
    this.goodsDetail = goodsDetail;
  }
  
  public String getGoodsDetail()
  {
    return this.goodsDetail;
  }
  
  public void setItBPay(String itBPay)
  {
    this.itBPay = itBPay;
  }
  
  public String getItBPay()
  {
    return this.itBPay;
  }
  
  public void setOperatorCode(String operatorCode)
  {
    this.operatorCode = operatorCode;
  }
  
  public String getOperatorCode()
  {
    return this.operatorCode;
  }
  
  public void setOperatorId(String operatorId)
  {
    this.operatorId = operatorId;
  }
  
  public String getOperatorId()
  {
    return this.operatorId;
  }
  
  public void setOutTradeNo(String outTradeNo)
  {
    this.outTradeNo = outTradeNo;
  }
  
  public String getOutTradeNo()
  {
    return this.outTradeNo;
  }
  
  public void setPrice(String price)
  {
    this.price = price;
  }
  
  public String getPrice()
  {
    return this.price;
  }
  
  public void setQuantity(String quantity)
  {
    this.quantity = quantity;
  }
  
  public String getQuantity()
  {
    return this.quantity;
  }
  
  public void setRoyaltyParameters(String royaltyParameters)
  {
    this.royaltyParameters = royaltyParameters;
  }
  
  public String getRoyaltyParameters()
  {
    return this.royaltyParameters;
  }
  
  public void setRoyaltyType(String royaltyType)
  {
    this.royaltyType = royaltyType;
  }
  
  public String getRoyaltyType()
  {
    return this.royaltyType;
  }
  
  public void setSellerEmail(String sellerEmail)
  {
    this.sellerEmail = sellerEmail;
  }
  
  public String getSellerEmail()
  {
    return this.sellerEmail;
  }
  
  public void setSellerId(String sellerId)
  {
    this.sellerId = sellerId;
  }
  
  public String getSellerId()
  {
    return this.sellerId;
  }
  
  public void setShowUrl(String showUrl)
  {
    this.showUrl = showUrl;
  }
  
  public String getShowUrl()
  {
    return this.showUrl;
  }
  
  public void setSubject(String subject)
  {
    this.subject = subject;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public void setTotalFee(String totalFee)
  {
    this.totalFee = totalFee;
  }
  
  public String getTotalFee()
  {
    return this.totalFee;
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
    return "alipay.acquire.precreate";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("body", this.body);
    txtParams.put("channel_parameters", this.channelParameters);
    txtParams.put("currency", this.currency);
    txtParams.put("extend_params", this.extendParams);
    txtParams.put("goods_detail", this.goodsDetail);
    txtParams.put("it_b_pay", this.itBPay);
    txtParams.put("operator_code", this.operatorCode);
    txtParams.put("operator_id", this.operatorId);
    txtParams.put("out_trade_no", this.outTradeNo);
    txtParams.put("price", this.price);
    txtParams.put("quantity", this.quantity);
    txtParams.put("royalty_parameters", this.royaltyParameters);
    txtParams.put("royalty_type", this.royaltyType);
    txtParams.put("seller_email", this.sellerEmail);
    txtParams.put("seller_id", this.sellerId);
    txtParams.put("show_url", this.showUrl);
    txtParams.put("subject", this.subject);
    txtParams.put("total_fee", this.totalFee);
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
  
  public Class<AlipayAcquirePrecreateResponse> getResponseClass()
  {
    return AlipayAcquirePrecreateResponse.class;
  }
}
