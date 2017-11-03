package com.egouos.util;

import com.egouos.pojo.SysConfigure;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext*.xml"})
@Repository
public class SysConfigureAuthUtil
{
  @Test
  public void go()
    throws InterruptedException, IOException
  {
    /*try
    {
      String ip = ApplicationListenerImpl.sysConfigureJson.getAuthorization();
      Document document = Jsoup.connect("http://www.egouos.com/authorization.txt").timeout(60000).get();
      if (StringUtil.isNotBlank(ip)) {
        if (document.text().indexOf(ip) != -1) {
          ApplicationListenerImpl.sysConfigureAuth = true;
        } else {
          ApplicationListenerImpl.sysConfigureAuth = false;
        }
      }
    }
    catch (Exception e)
    {
      System.err.println("auto error");
    }*/
  }
  
  private static final void printIp() { 
      try { 
          for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) { 
              NetworkInterface item = e.nextElement(); 

              System.out.println(item.toString()); 
              System.out.println(item.getMTU() + " " + item.isLoopback() + " " + item.isPointToPoint() + " " + item.isUp() + " " + item.isVirtual()); 

              for (InterfaceAddress address : item.getInterfaceAddresses()) { 
                  if (address.getAddress() instanceof Inet4Address) { 
                      Inet4Address inet4Address = (Inet4Address) address.getAddress(); 
                      System.out.println(inet4Address.getHostAddress()); 
                      System.out.println(inet4Address.isLinkLocalAddress() + " " + inet4Address.isLoopbackAddress() + " " + inet4Address.isMCGlobal() + " " + inet4Address.isMulticastAddress()); 
                  } 
              } 
          } 
      } catch (IOException ex) { 

      } 
  } 
  
  public static void main(String[] args)
    throws SocketException, UnknownHostException
  {}
}
