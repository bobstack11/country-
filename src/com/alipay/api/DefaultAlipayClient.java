package com.alipay.api;

import com.alipay.api.internal.parser.json.ObjectJsonParser;
import com.alipay.api.internal.parser.xml.ObjectXmlParser;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.internal.util.AlipayLogger;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.AlipayUtils;
import com.alipay.api.internal.util.RequestParametersHolder;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.internal.util.WebUtils;
import java.io.IOException;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DefaultAlipayClient
  implements AlipayClient
{
  private String serverUrl;
  private String appId;
  private String privateKey;
  private String prodCode;
  private String format = "json";
  private String sign_type = "RSA";
  private String charset;
  private int connectTimeout = 3000;
  private int readTimeout = 15000;
  
  static
  {
    Security.setProperty("jdk.certpath.disabledAlgorithms", "");
  }
  
  public DefaultAlipayClient(String serverUrl, String appId, String privateKey)
  {
    this.serverUrl = serverUrl;
    this.appId = appId;
    this.privateKey = privateKey;
  }
  
  public DefaultAlipayClient(String serverUrl, String appId, String privateKey, String format)
  {
    this(serverUrl, appId, privateKey);
    this.format = format;
  }
  
  public DefaultAlipayClient(String serverUrl, String appId, String privateKey, String format, String charset)
  {
    this(serverUrl, appId, privateKey);
    this.format = format;
    this.charset = charset;
  }
  
  public <T extends AlipayResponse> T execute(AlipayRequest<T> request)
    throws AlipayApiException
  {
    return execute(request, null);
  }
  
  public <T extends AlipayResponse> T execute(AlipayRequest<T> request, String accessToken)
    throws AlipayApiException
  {
    AlipayParser<T> parser = null;
    if ("xml".equals(this.format)) {
      parser = new ObjectXmlParser(request.getResponseClass());
    } else {
      parser = new ObjectJsonParser(request.getResponseClass());
    }
    return _execute(request, parser, accessToken);
  }
  
  private <T extends AlipayResponse> T _execute(AlipayRequest<T> request, AlipayParser<T> parser, String authToken)
    throws AlipayApiException
  {
    Map<String, Object> rt = doPost(request, authToken);
    if (rt == null) {
      return null;
    }
    T tRsp = null;
    try
    {
      tRsp = parser.parse((String)rt.get("rsp"));
      tRsp.setBody((String)rt.get("rsp"));
    }
    catch (RuntimeException e)
    {
      AlipayLogger.logBizError((String)rt.get("rsp"));
      throw e;
    }
    catch (AlipayApiException e)
    {
      AlipayLogger.logBizError((String)rt.get("rsp"));
      throw new AlipayApiException(e);
    }
    tRsp.setParams((AlipayHashMap)rt.get("textParams"));
    if (!tRsp.isSuccess()) {
      AlipayLogger.logErrorScene(rt, tRsp, "");
    }
    return tRsp;
  }
  
  public <T extends AlipayResponse> Map<String, Object> doPost(AlipayRequest<T> request, String accessToken)
    throws AlipayApiException
  {
    Map<String, Object> result = new HashMap();
    RequestParametersHolder requestHolder = new RequestParametersHolder();
    AlipayHashMap appParams = new AlipayHashMap(request.getTextParams());
    requestHolder.setApplicationParams(appParams);
    
    AlipayHashMap protocalMustParams = new AlipayHashMap();
    protocalMustParams.put("method", request.getApiMethodName());
    protocalMustParams.put("version", request.getApiVersion());
    protocalMustParams.put("app_id", this.appId);
    protocalMustParams.put("sign_type", this.sign_type);
    protocalMustParams.put("terminal_type", request.getTerminalType());
    protocalMustParams.put("terminal_info", request.getTerminalInfo());
    
    Long timestamp = Long.valueOf(System.currentTimeMillis());
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    protocalMustParams.put("timestamp", df.format(new Date(timestamp.longValue())));
    requestHolder.setProtocalMustParams(protocalMustParams);
    
    AlipayHashMap protocalOptParams = new AlipayHashMap();
    protocalOptParams.put("format", this.format);
    protocalOptParams.put("auth_token", accessToken);
    protocalOptParams.put("alipay_sdk", "alipay-sdk-java-dynamicVersionNo");
    protocalOptParams.put("prod_code", request.getProdCode());
    requestHolder.setProtocalOptParams(protocalOptParams);
    if (StringUtils.isEmpty(this.charset)) {
      this.charset = "UTF-8";
    }
    if ("RSA".equals(this.sign_type))
    {
      String signContent = AlipaySignature.getSignatureContent(requestHolder);
      
      protocalMustParams.put("sign", 
        AlipaySignature.rsaSign(signContent, this.privateKey, this.charset));
    }
    else
    {
      protocalMustParams.put("sign", "");
    }
    StringBuffer urlSb = new StringBuffer(this.serverUrl);
    try
    {
      String sysMustQuery = WebUtils.buildQuery(requestHolder.getProtocalMustParams(), 
        this.charset);
      String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), this.charset);
      
      urlSb.append("?");
      urlSb.append(sysMustQuery);
      if (((sysOptQuery != null ? 1 : 0) & (sysOptQuery.length() > 0 ? 1 : 0)) != 0)
      {
        urlSb.append("&");
        urlSb.append(sysOptQuery);
      }
    }
    catch (IOException e)
    {
      throw new AlipayApiException(e);
    }
    String rsp = null;
    try
    {
      if ((request instanceof AlipayUploadRequest))
      {
        AlipayUploadRequest<T> uRequest = (AlipayUploadRequest)request;
        Map<String, FileItem> fileParams = AlipayUtils.cleanupMap(uRequest.getFileParams());
        rsp = WebUtils.doPost(urlSb.toString(), appParams, fileParams, this.charset, 
          this.connectTimeout, this.readTimeout);
      }
      else
      {
        rsp = WebUtils.doPost(urlSb.toString(), appParams, this.charset, this.connectTimeout, 
          this.readTimeout);
      }
    }
    catch (IOException e)
    {
      throw new AlipayApiException(e);
    }
    result.put("rsp", rsp);
    result.put("textParams", appParams);
    result.put("protocalMustParams", protocalMustParams);
    result.put("protocalOptParams", protocalOptParams);
    result.put("url", urlSb.toString());
    return result;
  }
}
