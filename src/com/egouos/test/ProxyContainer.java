package com.egouos.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ProxyContainer
{
  private List<Proxy> proxyList;
  private CountDownLatch countDownLatch;
  
  public ProxyContainer(List<Proxy> proxyList, CountDownLatch countDownLatch)
  {
    this.proxyList = proxyList;
    this.countDownLatch = countDownLatch;
  }
  
  public synchronized Proxy getProxy()
  {
    if (this.proxyList.size() == 0) {
      return null;
    }
    return (Proxy)this.proxyList.remove(this.proxyList.size() - 1);
  }
  
  public boolean isEmpty()
  {
    boolean isEmpty = false;
    if (this.proxyList.size() == 0) {
      isEmpty = true;
    }
    return isEmpty;
  }
  
  public synchronized void reduceTask()
  {
    this.countDownLatch.countDown();
  }
  
  public synchronized void delProxy(Proxy proxy)
  {
    this.proxyList.remove(proxy);
  }
}
