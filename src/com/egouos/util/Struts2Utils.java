package com.egouos.util;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.map.ObjectMapper;

public class Struts2Utils
{
  private static final String HEADER_ENCODING = "encoding";
  private static final String HEADER_NOCACHE = "no-cache";
  private static final String DEFAULT_ENCODING = "UTF-8";
  private static final boolean DEFAULT_NOCACHE = true;
  private static ObjectMapper mapper = new ObjectMapper();
  
  public static HttpSession getSession()
  {
    return ServletActionContext.getRequest().getSession();
  }
  
  public static HttpSession getSession(boolean isNew)
  {
    return ServletActionContext.getRequest().getSession(isNew);
  }
  
  public static Object getSessionAttribute(String name)
  {
    HttpSession session = getSession(false);
    return session != null ? session.getAttribute(name) : null;
  }
  
  public static HttpServletRequest getRequest()
  {
    return ServletActionContext.getRequest();
  }
  
  public static String getParameter(String name)
  {
    return getRequest().getParameter(name);
  }
  
  public static HttpServletResponse getResponse()
  {
    return ServletActionContext.getResponse();
  }
  
  public static void render(String contentType, String content, String... headers)
  {
    HttpServletResponse response = initResponseHeader(contentType, headers);
    try
    {
      response.getWriter().write(content);
      response.getWriter().flush();
    }
    catch (IOException e)
    {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
  
  public static void renderText(String text, String... headers)
  {
    render("text/plain", text, headers);
  }
  
  public static void renderHtml(String html, String... headers)
  {
    render("text/html", html, headers);
  }
  
  public static void renderXml(String xml, String... headers)
  {
    render("text/xml", xml, headers);
  }
  
  public static void renderJson(String jsonString, String... headers)
  {
    render("application/json", jsonString, headers);
  }
  
  public static void renderJson(Object data, String... headers)
  {
    HttpServletResponse response = initResponseHeader("application/json", headers);
    try
    {
      mapper.writeValue(response.getWriter(), data);
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException(e);
    }
  }
  
  public static void renderJsonp(String callbackName, Object object, String... headers)
  {
    String jsonString = null;
    try
    {
      jsonString = mapper.writeValueAsString(object);
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException(e);
    }
    String result = callbackName + "(" + jsonString + ");";
    System.out.println(result);
    
    render("text/javascript", result, headers);
  }
  
  private static HttpServletResponse initResponseHeader(String contentType, String... headers)
  {
    String encoding = "UTF-8";
    boolean noCache = true;
    for (String header : headers)
    {
      String headerName = StringUtils.substringBefore(header, ":");
      String headerValue = StringUtils.substringAfter(header, ":");
      if (StringUtils.equalsIgnoreCase(headerName, "encoding")) {
        encoding = headerValue;
      } else if (StringUtils.equalsIgnoreCase(headerName, "no-cache")) {
        noCache = Boolean.parseBoolean(headerValue);
      } else {
        throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
      }
    }
    HttpServletResponse response = ServletActionContext.getResponse();
    

    String fullContentType = contentType + ";charset=" + encoding;
    response.setContentType(fullContentType);
    if (noCache) {
      ServletUtils.setNoCacheHeader(response);
    }
    return response;
  }
  
  public static String getLocalIP()
  {
    InetAddress addr = null;
    try
    {
      addr = InetAddress.getLocalHost();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    byte[] ipAddr = addr.getAddress();
    String ipAddrStr = "";
    for (int i = 0; i < ipAddr.length; i++)
    {
      if (i > 0) {
        ipAddrStr = ipAddrStr + ".";
      }
      ipAddrStr = ipAddrStr + (ipAddr[i] & 0xFF);
    }
    return ipAddrStr;
  }
  
  public final static String getRemoteIp()
  {
	  final HttpServletRequest request = getRequest();
	  if(request == null){
		  return null;
	  }
	  // try-get
	  String ip = request.getHeader("X-Forwarded-For");
	  if(StringUtils.isBlank(ip)==false && ("unknown".equalsIgnoreCase(ip)==false)){
		  final int i = ip.indexOf(',');
		  return (i == -1)? ip : ip.substring(0, i);
	  }
	  if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
		  ip = request.getHeader("X-REAL-IP");
	  }
      if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
      {
          ip = request.getHeader("Proxy-Client-IP");
      }
      if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
      {
          ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
      {
          ip = request.getRemoteAddr();
      }
      return ip;
  }
}
