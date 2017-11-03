package com.egouos.util.email;

import com.egouos.pojo.SysConfigure;
import com.egouos.util.ApplicationListenerImpl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendMail
{
  String sender;
  String password;
  String smtpServer;
  String recipient;
  String subject;
  List<String> fileAttachment;
  String content;
  private final Log log = LogFactory.getLog(SendMail.class);
  
  public SendMail(String sender, String password, String smtpServer, String recipient, String subject, String content, List<String> fileAttachment)
  {
    this.sender = sender;
    this.password = password;
    this.smtpServer = smtpServer;
    this.recipient = recipient;
    this.subject = subject;
    this.fileAttachment = fileAttachment;
    this.content = content;
  }
  
  public boolean sendMail()
  {
    boolean flag = false;
    
    Properties proper = new Properties();
    proper.put("mail.smtp.host", this.smtpServer);
    proper.put("mail.smtp.auth", "true");
    proper.put("mail.smtp.port", "25");
    proper.put("mail.transport.protocol", "smtp");
    proper.put("mail.store.protocol", "pop3");
    proper.setProperty("mail.pop3.disabletop", "true");
    
    InternetAddress[] receiveAddress = new InternetAddress[1];
    try
    {
      receiveAddress[0] = new InternetAddress(this.recipient);
    }
    catch (AddressException e)
    {
      e.printStackTrace();
    }
    SmtpAuth sa = new SmtpAuth();
    sa.setUserinfo(this.sender, this.password);
    Session session = Session.getInstance(proper, sa);
    session.setPasswordAuthentication(new URLName(this.smtpServer), sa.getPasswordAuthentication());
    
    MimeMessage sendMess = new MimeMessage(session);
    MimeBodyPart mbp = new MimeBodyPart();
    MimeMultipart mmp = new MimeMultipart();
    try
    {
      mbp.setContent(this.content, "text/html; charset=UTF-8");
      mmp.addBodyPart(mbp);
      if ((this.fileAttachment != null) && (this.fileAttachment.size() > 0)) {
        for (String filePath : this.fileAttachment) {
          if ((filePath != null) && (!"".equals(filePath.trim())))
          {
            DataSource source = new FileDataSource(filePath);
            String name = source.getName();
            mbp = new MimeBodyPart();
            mbp.setDataHandler(new DataHandler(source));
            
            mbp.setFileName(MimeUtility.encodeText(name));
            mmp.addBodyPart(mbp);
          }
        }
      }
      sendMess.setSubject(this.subject);
      sendMess.setContent(mmp);
      
      String nick = "";
      try
      {
        nick = MimeUtility.encodeText(ApplicationListenerImpl.sysConfigureJson.getSaitName());
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
      sendMess.setFrom(new InternetAddress(this.sender, nick));
      
      sendMess.setHeader("X-Priority", "1");
      sendMess.setRecipients(Message.RecipientType.TO, receiveAddress);
      Transport.send(sendMess);
      flag = true;
      this.log.info("邮件发送成功! 标题:" + this.subject + "  收件人:" + this.recipient + "  发件人:" + this.sender);
    }
    catch (Exception ex)
    {
      this.log.error("邮件发送失败!!! 标题:" + this.subject + "  收件人:" + this.recipient + "  发件人:" + this.sender, ex);
    }
    return flag;
  }
}
