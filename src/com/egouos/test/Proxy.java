package com.egouos.test;

public class Proxy
{
  private String ip;
  private Integer port;
  
  public Proxy() {}
  
  public Proxy(String ip, Integer port)
  {
    this.ip = ip;
    this.port = port;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public Integer getPort()
  {
    return this.port;
  }
  
  public void setPort(Integer port)
  {
    this.port = port;
  }
}
