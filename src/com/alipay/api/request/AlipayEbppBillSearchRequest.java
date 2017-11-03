package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayEbppBillSearchResponse;
import java.util.Map;

public class AlipayEbppBillSearchRequest
  implements AlipayRequest<AlipayEbppBillSearchResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String billKey;
  private String chargeInst;
  private String chargeoffInst;
  private String companyId;
  private String extend;
  private String orderType;
  private String subOrderType;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setBillKey(String billKey)
  {
    this.billKey = billKey;
  }
  
  public String getBillKey()
  {
    return this.billKey;
  }
  
  public void setChargeInst(String chargeInst)
  {
    this.chargeInst = chargeInst;
  }
  
  public String getChargeInst()
  {
    return this.chargeInst;
  }
  
  public void setChargeoffInst(String chargeoffInst)
  {
    this.chargeoffInst = chargeoffInst;
  }
  
  public String getChargeoffInst()
  {
    return this.chargeoffInst;
  }
  
  public void setCompanyId(String companyId)
  {
    this.companyId = companyId;
  }
  
  public String getCompanyId()
  {
    return this.companyId;
  }
  
  public void setExtend(String extend)
  {
    this.extend = extend;
  }
  
  public String getExtend()
  {
    return this.extend;
  }
  
  public void setOrderType(String orderType)
  {
    this.orderType = orderType;
  }
  
  public String getOrderType()
  {
    return this.orderType;
  }
  
  public void setSubOrderType(String subOrderType)
  {
    this.subOrderType = subOrderType;
  }
  
  public String getSubOrderType()
  {
    return this.subOrderType;
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
    return "alipay.ebpp.bill.search";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("bill_key", this.billKey);
    txtParams.put("charge_inst", this.chargeInst);
    txtParams.put("chargeoff_inst", this.chargeoffInst);
    txtParams.put("company_id", this.companyId);
    txtParams.put("extend", this.extend);
    txtParams.put("order_type", this.orderType);
    txtParams.put("sub_order_type", this.subOrderType);
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
  
  public Class<AlipayEbppBillSearchResponse> getResponseClass()
  {
    return AlipayEbppBillSearchResponse.class;
  }
}
