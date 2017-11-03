package com.egouos.util;

import com.egouos.pojo.IndexImg;
import com.egouos.pojo.SysConfigure;
import com.egouos.service.SysConfigureService;
import java.io.PrintStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationListenerImpl
  implements ApplicationListener
{
  @Autowired
  private SysConfigureService sysConfigureService;
  public static SysConfigure sysConfigureJson;
  public static boolean sysConfigureAuth = true;
  public static List<IndexImg> indexImgAll;
  
  public SysConfigureService getSysConfigureService()
  {
    return this.sysConfigureService;
  }
  
  public void setSysConfigureService(SysConfigureService sysConfigureService)
  {
    this.sysConfigureService = sysConfigureService;
  }
  
  public void onApplicationEvent(ApplicationEvent arg0)
  {
    if ((arg0 instanceof ApplicationEvent))
    {
      sysConfigureJson = (SysConfigure)this.sysConfigureService.findById("1");
      indexImgAll = this.sysConfigureService.initializationIndexImgAll();
      System.err.println(sysConfigureJson.getSaitName());
    }
  }
}
