package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.ShareCommentJSON;
import com.egouos.pojo.ShareJSON;
import com.egouos.pojo.Sharecomments;
import com.egouos.pojo.Shareimage;
import com.egouos.pojo.Shareinfo;
import com.egouos.pojo.User;
import com.egouos.service.ShareService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ShareAction")
public class ShareAction
  extends ActionSupport
{
  private static final long serialVersionUID = -8202280107475087499L;
  @Autowired
  ShareService shareService;
  private Shareinfo shareinfo;
  private Shareimage shareimage;
  private Latestlottery latestlottery;
  private List<ShareJSON> ShareJSONList;
  private List<ShareCommentJSON> shareCommentJSONList;
  private List<Shareimage> shareimageList;
  private User user;
  private Sharecomments sharecomments;
  private ShareCommentJSON shareCommentJSON;
  private ShareJSON shareJSON;
  private String id;
  private int pageNo;
  private String pages;
  private String pageString;
  private int pageSize = 20;
  private int pageCount;
  private int resultCount;
  private static List<ShareJSON> ShareJSONByIndexList;
  private static Long nowDateByShareJSONByIndex = Long.valueOf(System.currentTimeMillis());
  private static Long beginDateByShareJSONByIndex;
  
  public String index()
  {
    if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      

      return null;
    }
    return "index";
  }
  
  public String ajaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (this.id.equals("new20"))
    {
      Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("share_new20_page" + this.pageNo + "_" + this.pageSize);
      if (page == null)
      {
        page = this.shareService.loadPageShare("new20", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("share_new20_page" + this.pageNo + "_" + this.pageSize, 5, page);
      }
      List<Object[]> pageList = (List<Object[]>)page.getList();
      this.ShareJSONList = new ArrayList();
      for (int i = 0; i < pageList.size(); i++)
      {
        this.shareJSON = new ShareJSON();
        Object[] objs = (Object[])pageList.get(i);
  	  for(Object obj:objs){
  		if(obj instanceof Shareinfo){
  			shareinfo = (Shareinfo)obj;
  		}
  		if(obj instanceof Shareimage){
  			shareimage = (Shareimage)obj;
  		}
  		if(obj instanceof Latestlottery){
  			latestlottery = (Latestlottery)obj;
  		}
  	  }
        //this.shareinfo = ((Shareinfo)((Object[])pageList.get(i))[0]);
        //this.shareimage = ((Shareimage)((Object[])pageList.get(i))[1]);
        //this.latestlottery = ((Latestlottery)((Object[])pageList.get(i))[2]);
        String userName = "";
        if ((this.latestlottery.getUserName() != null) && (!this.latestlottery.getUserName().equals("")))
        {
          userName = this.latestlottery.getUserName();
        }
        else if ((this.latestlottery.getBuyUser() != null) && (!this.latestlottery.getBuyUser().equals("")))
        {
          userName = this.latestlottery.getBuyUser();
          if (userName.indexOf("@") != -1)
          {
            String[] u = userName.split("@");
            String u1 = u[0].substring(0, 2) + "***";
            userName = u1 + "@" + u[1];
          }
          else
          {
            userName = userName.substring(0, 4) + "*** " + userName.substring(7);
          }
        }
        this.shareJSON.setAnnouncedTime(this.latestlottery.getAnnouncedTime().substring(0, 10));
        this.shareJSON.setReplyCount(this.shareinfo.getReplyCount());
        this.shareJSON.setReward(this.shareinfo.getReward());
        this.shareJSON.setShareContent(this.shareinfo.getShareContent());
        this.shareJSON.setShareDate(DateUtil.getShortTime(this.shareinfo.getShareDate()));
        this.shareimageList = ((List)AliyunOcsSampleHelp.getIMemcachedCache().get("shareShow_index_shareimageList_" + this.shareinfo.getUid()));
        if (this.shareimageList == null)
        {
          this.shareimageList = this.shareService.getShareimage(String.valueOf(this.shareinfo.getUid()));
          AliyunOcsSampleHelp.getIMemcachedCache().set("shareShow_index_shareimageList_" + this.shareinfo.getUid(), 3600, this.shareimageList);
        }
        this.shareJSON.setShareimageList(this.shareimageList);
        this.shareJSON.setShareImages(this.shareimage.getImages());
        this.shareJSON.setShareTitle(this.shareinfo.getShareTitle());
        this.shareJSON.setUid(this.shareinfo.getUid());
        this.shareJSON.setUpCount(this.shareinfo.getUpCount());
        this.shareJSON.setUserName(userName);
        this.shareJSON.setUserFace(this.latestlottery.getUserFace());
        this.shareJSON.setUserId(this.latestlottery.getUserId()+"");
        this.ShareJSONList.add(this.shareJSON);
      }
      Struts2Utils.renderJson(this.ShareJSONList, new String[0]);
    }
    if (this.id.equals("hot20"))
    {
      Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("share_hot20_page_" + this.pageNo + "_" + this.pageSize);
      if (page == null)
      {
        page = this.shareService.loadPageShare("hot20", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("share_hot20_page_" + this.pageNo + "_" + this.pageSize, 5, page);
      }
      List<Object[]> pageList = (List<Object[]>)page.getList();
      this.ShareJSONList = new ArrayList();
      for (int i = 0; i < pageList.size(); i++)
      {
        this.shareJSON = new ShareJSON();
        Object[] objs = (Object[])pageList.get(i);
  	  for(Object obj:objs){
  		if(obj instanceof Shareinfo){
  			shareinfo = (Shareinfo)obj;
  		}
  		if(obj instanceof Shareimage){
  			shareimage = (Shareimage)obj;
  		}
  		if(obj instanceof Latestlottery){
  			latestlottery = (Latestlottery)obj;
  		}
  	  }
        //this.shareinfo = ((Shareinfo)((Object[])pageList.get(i))[0]);
        //this.shareimage = ((Shareimage)((Object[])pageList.get(i))[1]);
        //this.latestlottery = ((Latestlottery)((Object[])pageList.get(i))[2]);
        String userName = "";
        if ((this.latestlottery.getUserName() != null) && (!this.latestlottery.getUserName().equals("")))
        {
          userName = this.latestlottery.getUserName();
        }
        else if ((this.latestlottery.getBuyUser() != null) && (!this.latestlottery.getBuyUser().equals("")))
        {
          userName = this.latestlottery.getBuyUser();
          if (userName.indexOf("@") != -1)
          {
            String[] u = userName.split("@");
            String u1 = u[0].substring(0, 2) + "***";
            userName = u1 + "@" + u[1];
          }
          else
          {
            userName = userName.substring(0, 4) + "*** " + userName.substring(7);
          }
        }
        this.shareJSON.setAnnouncedTime(this.latestlottery.getAnnouncedTime().substring(0, 10));
        this.shareJSON.setReplyCount(this.shareinfo.getReplyCount());
        this.shareJSON.setReward(this.shareinfo.getReward());
        this.shareJSON.setShareContent(this.shareinfo.getShareContent());
        this.shareJSON.setShareDate(DateUtil.getShortTime(this.shareinfo.getShareDate()));
        this.shareimageList = ((List)AliyunOcsSampleHelp.getIMemcachedCache().get("shareShow_index_shareimageList_" + this.shareinfo.getUid()));
        if (this.shareimageList == null)
        {
          this.shareimageList = this.shareService.getShareimage(String.valueOf(this.shareinfo.getUid()));
          AliyunOcsSampleHelp.getIMemcachedCache().set("shareShow_index_shareimageList_" + this.shareinfo.getUid(), 3600, this.shareimageList);
        }
        this.shareJSON.setShareimageList(this.shareimageList);
        this.shareJSON.setShareImages(this.shareimage.getImages());
        this.shareJSON.setShareTitle(this.shareinfo.getShareTitle());
        this.shareJSON.setUid(this.shareinfo.getUid());
        this.shareJSON.setUpCount(this.shareinfo.getUpCount());
        this.shareJSON.setUserName(userName);
        this.shareJSON.setUserFace(this.latestlottery.getUserFace());
        this.shareJSON.setUserId(this.latestlottery.getUserId()+"");
        this.ShareJSONList.add(this.shareJSON);
      }
      Struts2Utils.renderJson(this.ShareJSONList, new String[0]);
    }
    if (this.id.equals("reply20"))
    {
      Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("share_reply20_page_" + this.pageNo + "_" + this.pageSize);
      if (page == null)
      {
        page = this.shareService.loadPageShare("reply20", this.pageNo, this.pageSize);
        AliyunOcsSampleHelp.getIMemcachedCache().set("share_reply20_page_" + this.pageNo + "_" + this.pageSize, 5, page);
      }
      List<Object[]> pageList = (List<Object[]>)page.getList();
      this.ShareJSONList = new ArrayList();
      for (int i = 0; i < pageList.size(); i++)
      {
        this.shareJSON = new ShareJSON();
        Object[] objs = (Object[])pageList.get(i);
  	  for(Object obj:objs){
  		if(obj instanceof Shareinfo){
  			shareinfo = (Shareinfo)obj;
  		}
  		if(obj instanceof Shareimage){
  			shareimage = (Shareimage)obj;
  		}
  		if(obj instanceof Latestlottery){
  			latestlottery = (Latestlottery)obj;
  		}
  	  }
        //this.shareinfo = ((Shareinfo)((Object[])pageList.get(i))[0]);
        //this.shareimage = ((Shareimage)((Object[])pageList.get(i))[1]);
        //this.latestlottery = ((Latestlottery)((Object[])pageList.get(i))[2]);
        String userName = "";
        if ((this.latestlottery.getUserName() != null) && (!this.latestlottery.getUserName().equals("")))
        {
          userName = this.latestlottery.getUserName();
        }
        else if ((this.latestlottery.getBuyUser() != null) && (!this.latestlottery.getBuyUser().equals("")))
        {
          userName = this.latestlottery.getBuyUser();
          if (userName.indexOf("@") != -1)
          {
            String[] u = userName.split("@");
            String u1 = u[0].substring(0, 2) + "***";
            userName = u1 + "@" + u[1];
          }
          else
          {
            userName = userName.substring(0, 4) + "*** " + userName.substring(7);
          }
        }
        this.shareJSON.setAnnouncedTime(this.latestlottery.getAnnouncedTime().substring(0, 10));
        this.shareJSON.setReplyCount(this.shareinfo.getReplyCount());
        this.shareJSON.setReward(this.shareinfo.getReward());
        this.shareJSON.setShareContent(this.shareinfo.getShareContent());
        this.shareJSON.setShareDate(DateUtil.getShortTime(this.shareinfo.getShareDate()));
        this.shareimageList = ((List)AliyunOcsSampleHelp.getIMemcachedCache().get("shareShow_index_shareimageList_" + this.shareinfo.getUid()));
        if (this.shareimageList == null)
        {
          this.shareimageList = this.shareService.getShareimage(String.valueOf(this.shareinfo.getUid()));
          AliyunOcsSampleHelp.getIMemcachedCache().set("shareShow_index_shareimageList_" + this.shareinfo.getUid(), 3600, this.shareimageList);
        }
        this.shareJSON.setShareimageList(this.shareimageList);
        this.shareJSON.setShareImages(this.shareimage.getImages());
        this.shareJSON.setShareTitle(this.shareinfo.getShareTitle());
        this.shareJSON.setUid(this.shareinfo.getUid());
        this.shareJSON.setUpCount(this.shareinfo.getUpCount());
        this.shareJSON.setUserName(userName);
        this.shareJSON.setUserFace(this.latestlottery.getUserFace());
        this.shareJSON.setUserId(this.latestlottery.getUserId()+"");
        this.ShareJSONList.add(this.shareJSON);
      }
      Struts2Utils.renderJson(this.ShareJSONList, new String[0]);
    }
    return null;
  }
  
  public String shareShow()
  {
    return null;
  }
  
  public void indexSharecommentsList()
  {
    List<Object[]> objectList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("share_objectList");
    if (objectList == null)
    {
      objectList = this.shareService.getIndexSharecommentsList();
      AliyunOcsSampleHelp.getIMemcachedCache().set("share_objectList", 5, objectList);
    }
    this.shareCommentJSONList = new ArrayList();
    for (Object[] objects : objectList)
    {
      this.user = ((User)objects[0]);
      this.sharecomments = ((Sharecomments)objects[1]);
      this.shareCommentJSON = new ShareCommentJSON();
      this.shareCommentJSON.setContent(this.sharecomments.getContent());
      this.shareCommentJSON.setCreateDate(this.sharecomments.getCreateDate());
      this.shareCommentJSON.setUid(this.sharecomments.getUid());
      this.shareCommentJSON.setUserFace(this.user.getFaceImg());
      this.shareCommentJSON.setShareInfoId(this.sharecomments.getShareInfoId());
      String userName = UserNameUtil.userName(this.user);
      this.shareCommentJSON.setReCount(this.sharecomments.getReCount());
      this.shareCommentJSON.setUserName(userName);
      this.shareCommentJSON.setUserId(this.user.getUserId());
      this.shareCommentJSONList.add(this.shareCommentJSON);
    }
    Struts2Utils.renderJson(this.shareCommentJSONList, new String[0]);
  }
  
  public Shareinfo getShareinfo()
  {
    return this.shareinfo;
  }
  
  public void setShareinfo(Shareinfo shareinfo)
  {
    this.shareinfo = shareinfo;
  }
  
  public Shareimage getShareimage()
  {
    return this.shareimage;
  }
  
  public void setShareimage(Shareimage shareimage)
  {
    this.shareimage = shareimage;
  }
  
  public Latestlottery getLatestlottery()
  {
    return this.latestlottery;
  }
  
  public void setLatestlottery(Latestlottery latestlottery)
  {
    this.latestlottery = latestlottery;
  }
  
  public List<ShareJSON> getShareJSONList()
  {
    return this.ShareJSONList;
  }
  
  public void setShareJSONList(List<ShareJSON> shareJSONList)
  {
    this.ShareJSONList = shareJSONList;
  }
  
  public ShareJSON getShareJSON()
  {
    return this.shareJSON;
  }
  
  public void setShareJSON(ShareJSON shareJSON)
  {
    this.shareJSON = shareJSON;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public int getPageNo()
  {
    return this.pageNo;
  }
  
  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;
  }
  
  public String getPages()
  {
    return this.pages;
  }
  
  public void setPages(String pages)
  {
    this.pages = pages;
  }
  
  public String getPageString()
  {
    return this.pageString;
  }
  
  public void setPageString(String pageString)
  {
    this.pageString = pageString;
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public int getPageCount()
  {
    return this.pageCount;
  }
  
  public void setPageCount(int pageCount)
  {
    this.pageCount = pageCount;
  }
  
  public int getResultCount()
  {
    return this.resultCount;
  }
  
  public void setResultCount(int resultCount)
  {
    this.resultCount = resultCount;
  }
  
  public List<ShareCommentJSON> getShareCommentJSONList()
  {
    return this.shareCommentJSONList;
  }
  
  public void setShareCommentJSONList(List<ShareCommentJSON> shareCommentJSONList)
  {
    this.shareCommentJSONList = shareCommentJSONList;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public Sharecomments getSharecomments()
  {
    return this.sharecomments;
  }
  
  public void setSharecomments(Sharecomments sharecomments)
  {
    this.sharecomments = sharecomments;
  }
  
  public ShareCommentJSON getShareCommentJSON()
  {
    return this.shareCommentJSON;
  }
  
  public void setShareCommentJSON(ShareCommentJSON shareCommentJSON)
  {
    this.shareCommentJSON = shareCommentJSON;
  }
  
  public List<Shareimage> getShareimageList()
  {
    return this.shareimageList;
  }
  
  public void setShareimageList(List<Shareimage> shareimageList)
  {
    this.shareimageList = shareimageList;
  }
}
