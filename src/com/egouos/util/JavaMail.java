package com.egouos.util;

import com.egouos.util.email.EmailProtocol;
import com.egouos.util.email.SendMail;
import java.util.List;

class JavaMail
  implements Runnable
{
  String mailFrom = null;
  String password = null;
  String mailto = null;
  String subject = null;
  String content = null;
  List<String> attachList = null;
  
  JavaMail(String mailFrom, String password, String mailto, String subject, String content, List<String> attachList)
  {
    this.mailFrom = mailFrom;
    this.password = password;
    this.mailto = mailto;
    this.subject = subject;
    this.content = content;
    this.attachList = attachList;
  }
  
  public void run()
  {
    sendMailByJavaMail(this.mailFrom, this.password, this.mailto, this.subject, this.content, this.attachList);
  }
  
  private synchronized boolean sendMailByJavaMail(String mailFrom, String password, String mailto, String subject, String content, List<String> attachList)
  {
    EmailProtocol eProtocol = new EmailProtocol(mailFrom);
    SendMail sendMail = new SendMail(mailFrom, password, eProtocol.getSmtpProtocol(), mailto, subject, content, attachList);
    return sendMail.sendMail();
  }
}
