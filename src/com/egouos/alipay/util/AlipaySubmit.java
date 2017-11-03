package com.egouos.alipay.util;

import com.egouos.alipay.config.AlipayConfig;
import com.egouos.alipay.sign.MD5;
import com.egouos.alipay.util.httpClient.HttpProtocolHandler;
import com.egouos.alipay.util.httpClient.HttpRequest;
import com.egouos.alipay.util.httpClient.HttpResponse;
import com.egouos.alipay.util.httpClient.HttpResultType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class AlipaySubmit
{
  private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
  
  public static String buildRequestMysign(Map<String, String> sPara)
  {
    String prestr = AlipayCore.createLinkString(sPara);
    String mysign = "";
    if (AlipayConfig.sign_type.equals("MD5")) {
      mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
    }
    return mysign;
  }
  
  private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp)
  {
    Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
    
    String mysign = buildRequestMysign(sPara);
    

    sPara.put("sign", mysign);
    sPara.put("sign_type", AlipayConfig.sign_type);
    
    return sPara;
  }
  
  public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName)
  {
    Map<String, String> sPara = buildRequestPara(sParaTemp);
    List<String> keys = new ArrayList((Collection)sPara.keySet());
    
    StringBuffer sbHtml = new StringBuffer();
    
    sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=" + 
      AlipayConfig.input_charset + "\" method=\"" + strMethod + 
      "\">");
    for (int i = 0; i < keys.size(); i++)
    {
      String name = (String)keys.get(i);
      String value = (String)sPara.get(name);
      
      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }
    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
    
    return sbHtml.toString();
  }
  
  public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName, String strParaFileName)
  {
    Map<String, String> sPara = buildRequestPara(sParaTemp);
    List<String> keys = new ArrayList((Collection)sPara.keySet());
    
    StringBuffer sbHtml = new StringBuffer();
    
    sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\"  enctype=\"multipart/form-data\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=" + 
      AlipayConfig.input_charset + "\" method=\"" + strMethod + 
      "\">");
    for (int i = 0; i < keys.size(); i++)
    {
      String name = (String)keys.get(i);
      String value = (String)sPara.get(name);
      
      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }
    sbHtml.append("<input type=\"file\" name=\"" + strParaFileName + "\" />");
    

    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
    
    return sbHtml.toString();
  }
  
  public static String buildRequest(String strParaFileName, String strFilePath, Map<String, String> sParaTemp)
    throws Exception
  {
    Map<String, String> sPara = buildRequestPara(sParaTemp);
    
    HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
    
    HttpRequest request = new HttpRequest(HttpResultType.BYTES);
    
    request.setCharset(AlipayConfig.input_charset);
    
    request.setParameters(generatNameValuePair(sPara));
    request.setUrl("https://mapi.alipay.com/gateway.do?_input_charset=" + AlipayConfig.input_charset);
    
    HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
    if (response == null) {
      return null;
    }
    String strResult = response.getStringResult();
    
    return strResult;
  }
  
  private static NameValuePair[] generatNameValuePair(Map<String, String> properties)
  {
    NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
    int i = 0;
    for (Map.Entry<String, String> entry : properties.entrySet()) {
      nameValuePair[(i++)] = new NameValuePair((String)entry.getKey(), (String)entry.getValue());
    }
    return nameValuePair;
  }
  
  /**
   * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数
   * 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
   * @return 时间戳字符串
   * @throws IOException
   * @throws DocumentException
   * @throws MalformedURLException
   */
	public static String query_timestamp() throws MalformedURLException,
                                                      DocumentException, IOException {

      //构造访问query_timestamp接口的URL串
      String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.partner + "&_input_charset" +AlipayConfig.input_charset;
      StringBuffer result = new StringBuffer();

      SAXReader reader = new SAXReader();
      Document doc = reader.read(new URL(strUrl).openStream());

      List<Node> nodeList = doc.selectNodes("//alipay/*");

      for (Node node : nodeList) {
          // 截取部分不需要解析的信息
          if (node.getName().equals("is_success") && node.getText().equals("T")) {
              // 判断是否有成功标示
              List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
              for (Node node1 : nodeList1) {
                  result.append(node1.getText());
              }
          }
      }

      return result.toString();
  }
}
