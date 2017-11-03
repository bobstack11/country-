package com.alipay.api.request;

import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayStockPortfolioSingleAddResponse;
import java.util.Map;

public class AlipayStockPortfolioSingleAddRequest
  implements AlipayRequest<AlipayStockPortfolioSingleAddResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private String symbol;
  private String userId;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setSymbol(String symbol)
  {
    this.symbol = symbol;
  }
  
  public String getSymbol()
  {
    return this.symbol;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getUserId()
  {
    return this.userId;
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
    return "alipay.stock.portfolio.single.add";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("symbol", this.symbol);
    txtParams.put("user_id", this.userId);
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
  
  public Class<AlipayStockPortfolioSingleAddResponse> getResponseClass()
  {
    return AlipayStockPortfolioSingleAddResponse.class;
  }
}
