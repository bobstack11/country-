package com.alipay.api.request;

import com.alipay.api.AlipayUploadRequest;
import com.alipay.api.FileItem;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.response.AlipayTrustUserFileUploadResponse;
import java.util.HashMap;
import java.util.Map;

public class AlipayTrustUserFileUploadRequest
  implements AlipayUploadRequest<AlipayTrustUserFileUploadResponse>
{
  private AlipayHashMap udfParams;
  private String apiVersion = "1.0";
  private FileItem fileContent;
  private String fileType;
  private String terminalType;
  private String terminalInfo;
  private String prodCode;
  
  public void setFileContent(FileItem fileContent)
  {
    this.fileContent = fileContent;
  }
  
  public FileItem getFileContent()
  {
    return this.fileContent;
  }
  
  public void setFileType(String fileType)
  {
    this.fileType = fileType;
  }
  
  public String getFileType()
  {
    return this.fileType;
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
  
  public String getProdCode()
  {
    return this.prodCode;
  }
  
  public void setProdCode(String prodCode)
  {
    this.prodCode = prodCode;
  }
  
  public String getApiMethodName()
  {
    return "alipay.trust.user.file.upload";
  }
  
  public Map<String, String> getTextParams()
  {
    AlipayHashMap txtParams = new AlipayHashMap();
    txtParams.put("file_type", this.fileType);
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
  
  public Map<String, FileItem> getFileParams()
  {
    Map<String, FileItem> params = new HashMap();
    params.put("file_content", this.fileContent);
    return params;
  }
  
  public Class<AlipayTrustUserFileUploadResponse> getResponseClass()
  {
    return AlipayTrustUserFileUploadResponse.class;
  }
}
