package com.egouos.action;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.ShareCommentJSON;
import com.egouos.pojo.ShareInfoJSON;
import com.egouos.pojo.ShareInfoPro;
import com.egouos.pojo.Sharecomments;
import com.egouos.pojo.Shareimage;
import com.egouos.pojo.Shareinfo;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.User;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.ShareService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.UserService;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.DateUtil;
import com.egouos.util.HTMLFilter;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ShareShowAction")
public class ShareShowAction
  extends ActionSupport
{
  private static final long serialVersionUID = -5418862771252833639L;
  @Autowired
  private ShareService shareService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private UserService userService;
  private ShareInfoPro shareInfoPro;
  private Sharecomments sharecomments;
  private ShareCommentJSON shareCommentJSON;
  private List<ShareCommentJSON> shareCommentJSONList;
  private Shareinfo shareinfo;
  private User user;
  private List<Shareimage> shareimageList;
  private Latestlottery latestlottery;
  private List<Latestlottery> latestlotteryList;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private ProductJSON productJSON;
  private List<ShareInfoJSON> ShareJSONList;
  private ShareInfoJSON ShareInfoJSON;
  private Shareimage shareimage;
  private String id;
  private int pageNo;
  private int pageSize = 5;
  private int pageCount;
  private int resultCount;
  private String productId;
  private String shareId;
  private String userId;
  private String shareCommentId;
  private String commentText;
  private String reCommentId;
  HttpServletRequest request = null;
  static HTMLFilter htmlFilter = new HTMLFilter();
  
  public String index()
  {
    if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>", new String[0]);
      

      return null;
    }
    this.request = Struts2Utils.getRequest();
    Cookie[] cookies = this.request.getCookies();
    if (this.request.isRequestedSessionIdFromCookie()) {
      for (int i = 0; i < cookies.length; i++)
      {
        Cookie cookie = cookies[i];
        if (cookie.getName().equals("userId"))
        {
          this.userId = cookie.getValue();
          if ((this.userId != null) && (!this.userId.equals(""))) {
            this.user = ((User)this.userService.findById(this.userId));
          }
        }
      }
    }
    this.shareInfoPro = new ShareInfoPro();
    this.shareimageList = new ArrayList();
    List<Object[]> objectList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("shareShow_index_objectList_" + this.id);
    if (objectList == null)
    {
      objectList = this.shareService.shareShow(Integer.parseInt(this.id));
      AliyunOcsSampleHelp.getIMemcachedCache().set("shareShow_index_objectList_" + this.id, 5, objectList);
    }
    Object[] objs = (Object[])objectList.get(0);
	  for(Object obj:objs){
		if(obj instanceof Shareinfo){
			shareinfo = (Shareinfo)obj;
		}
		if(obj instanceof Latestlottery){
			latestlottery = (Latestlottery)obj;
		}
	  }
    //this.shareinfo = ((Shareinfo)((Object[])objectList.get(0))[0]);
    //this.latestlottery = ((Latestlottery)((Object[])objectList.get(0))[1]);
    this.shareInfoPro.setAnnouncedTime(this.latestlottery.getAnnouncedTime());
    this.shareInfoPro.setBuyNumberCount(String.valueOf(this.latestlottery.getBuyNumberCount()));
    this.shareInfoPro.setProductId(String.valueOf(this.latestlottery.getProductId()));
    this.shareInfoPro.setProductImg(this.latestlottery.getProductImg());
    this.shareInfoPro.setProductName(this.latestlottery.getProductName());
    this.shareInfoPro.setProductPeriod(String.valueOf(this.latestlottery.getProductPeriod()));
    this.shareInfoPro.setProductPrice(String.valueOf(this.latestlottery.getProductPrice()));
    this.shareInfoPro.setProductTitle(this.latestlottery.getProductTitle());
    this.shareInfoPro.setReplyCount(this.shareinfo.getReplyCount().intValue());
    this.shareInfoPro.setReward(this.shareinfo.getReward().intValue());
    this.shareInfoPro.setShareContent(this.shareinfo.getShareContent());
    this.shareInfoPro.setShareDate(this.shareinfo.getShareDate());
    this.shareInfoPro.setShareId(String.valueOf(this.shareinfo.getUid()));
    this.shareInfoPro.setUserId(String.valueOf(this.shareinfo.getUserId()));
    this.shareimageList = ((List)AliyunOcsSampleHelp.getIMemcachedCache().get("shareShow_index_shareimageList_" + this.shareinfo.getUid()));
    if (this.shareimageList == null)
    {
      this.shareimageList = this.shareService.getShareimage(String.valueOf(this.shareinfo.getUid()));
      AliyunOcsSampleHelp.getIMemcachedCache().set("shareShow_index_shareimageList_" + this.shareinfo.getUid(), 3600, this.shareimageList);
    }
    this.shareInfoPro.setShareTitle(this.shareinfo.getShareTitle());
    this.shareInfoPro.setSpellbuyProductId(String.valueOf(this.latestlottery.getSpellbuyProductId()));
    this.shareInfoPro.setUpCount(this.shareinfo.getUpCount().intValue());
    this.shareInfoPro.setWinRandomNumber(String.valueOf(this.latestlottery.getRandomNumber()));
    this.shareInfoPro.setWinUserFace(this.latestlottery.getUserFace());
    this.shareInfoPro.setWinUserName(this.latestlottery.getUserName());
    
    Pagination datePage = this.shareService.shareByComment(this.id, this.pageNo, this.pageSize);
    this.resultCount = datePage.getResultCount();
    





















































    return "index";
  }
  
  public void productInfoShareListByProductId()
  {
    Pagination page = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("productInfoShareListByProductId_" + this.id + "_" + this.pageNo + "_" + this.pageSize);
    if (page == null)
    {
      page = this.shareService.loadShareInfoByNew("new20", this.id, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("productInfoShareListByProductId_" + this.id + "_" + this.pageNo + "_" + this.pageSize, 3600, page);
    }
    List<Object[]> pageList = (List<Object[]>)page.getList();
    this.ShareJSONList = new ArrayList();
    for (int i = 0; i < pageList.size(); i++) {
      try
      {
        this.ShareInfoJSON = new ShareInfoJSON();
        Object[] objects = (Object[])pageList.get(i);
    	  for(Object obj:objects){
    		if(obj instanceof Shareinfo){
    			shareinfo = (Shareinfo)obj;
    		}
    		if(obj instanceof Latestlottery){
    			latestlottery = (Latestlottery)obj;
    		}
    	  }
        //this.shareinfo = ((Shareinfo)((Object[])pageList.get(i))[0]);
        //this.latestlottery = ((Latestlottery)((Object[])pageList.get(i))[1]);
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
        this.ShareInfoJSON.setAnnouncedTime(this.latestlottery.getAnnouncedTime().substring(0, 10));
        this.ShareInfoJSON.setReplyCount(this.shareinfo.getReplyCount());
        this.ShareInfoJSON.setReward(this.shareinfo.getReward());
        this.ShareInfoJSON.setShareContent(this.shareinfo.getShareContent());
        this.ShareInfoJSON.setShareDate(DateUtil.getTime(DateUtil.SDateTimeToDate(this.shareinfo.getShareDate())));
        List<Shareimage> shareimageList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("shareShow_index_shareimageList_page_" + this.shareinfo.getUid());
        if (shareimageList == null)
        {
          shareimageList = this.shareService.getShareimage(String.valueOf(this.shareinfo.getUid()));
          AliyunOcsSampleHelp.getIMemcachedCache().set("shareShow_index_shareimageList_page_" + this.shareinfo.getUid(), 3600, shareimageList);
        }
        this.ShareInfoJSON.setShareimageList(shareimageList);
        this.ShareInfoJSON.setShareTitle(this.shareinfo.getShareTitle());
        this.ShareInfoJSON.setUid(this.shareinfo.getUid());
        this.ShareInfoJSON.setUpCount(this.shareinfo.getUpCount());
        this.ShareInfoJSON.setUserName(userName);
        this.ShareInfoJSON.setUserFace(this.latestlottery.getUserFace());
        this.ShareInfoJSON.setUserId(this.latestlottery.getUserId()+"");
        this.ShareInfoJSON.setProductPeriod(this.latestlottery.getProductPeriod());
        this.ShareJSONList.add(this.ShareInfoJSON);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    Struts2Utils.renderJson(this.ShareJSONList, new String[0]);
  }
  
  public String productOtherWinUser()
  {
    this.latestlotteryList = this.latestlotteryService.getProductOtherWinUser(this.productId, this.shareId);
    Struts2Utils.renderJson(this.latestlotteryList, new String[0]);
    return null;
  }
  
  public String shareCommentListAjaxPage()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    Pagination datePage = (Pagination)AliyunOcsSampleHelp.getIMemcachedCache().get("shareShow_shareComment_" + this.shareId + "_" + this.pageNo + "_" + this.pageSize);
    if (datePage == null)
    {
      datePage = this.shareService.shareByComment(this.shareId, this.pageNo, this.pageSize);
      AliyunOcsSampleHelp.getIMemcachedCache().set("shareShow_shareComment_" + this.shareId + "_" + this.pageNo + "_" + this.pageSize, 5, datePage);
    }
    List<Object[]> dataList = (List<Object[]>)datePage.getList();
    this.shareCommentJSONList = new ArrayList();
    for (Object[] objects : dataList) {
      try
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
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    Struts2Utils.renderJson(this.shareCommentJSONList, new String[0]);
    return null;
  }
  
  public void getReCommentList()
  {
    List<Object[]> objectList = (List)AliyunOcsSampleHelp.getIMemcachedCache().get("shareShow_shareComment_objectList_" + this.shareCommentId);
    if (objectList == null)
    {
      objectList = this.shareService.getReCommentList(this.shareCommentId);
      AliyunOcsSampleHelp.getIMemcachedCache().set("shareShow_shareComment_objectList_" + this.shareCommentId, 5, objectList);
    }
    this.shareCommentJSONList = new ArrayList();
    for (Object[] objects : objectList) {
      try
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
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    Struts2Utils.renderJson(this.shareCommentJSONList, new String[0]);
  }
  
  public void postComment()
  {
    try
    {
      this.commentText = htmlFilter.filter(this.commentText);
      this.shareId = htmlFilter.filter(this.shareId);
      this.userId = htmlFilter.filter(this.userId);
      this.sharecomments = new Sharecomments();
      this.sharecomments.setContent(this.commentText);
      this.sharecomments.setCreateDate(DateUtil.DateTimeToStr(new Date()));
      if (this.reCommentId != null)
      {
        this.sharecomments.setReCommentId(Integer.valueOf(Integer.parseInt(this.reCommentId)));
        Sharecomments sharecomments = this.shareService.findBySharecommentsId(this.reCommentId);
        Integer reCount = sharecomments.getReCount();
        sharecomments.setReCount(Integer.valueOf(reCount.intValue() + 1));
        this.shareService.createComment(sharecomments);
      }
      this.sharecomments.setShareInfoId(Integer.valueOf(Integer.parseInt(this.shareId)));
      this.sharecomments.setUserId(Integer.valueOf(Integer.parseInt(this.userId)));
      this.sharecomments.setReCount(Integer.valueOf(0));
      this.shareService.createComment(this.sharecomments);
      this.shareinfo = ((Shareinfo)this.shareService.findById(this.shareId));
      Integer replyCount = this.shareinfo.getReplyCount();
      this.shareinfo.setReplyCount(Integer.valueOf(replyCount.intValue() + 1));
      this.shareService.add(this.shareinfo);
      Struts2Utils.renderText("true", new String[0]);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Struts2Utils.renderText("false", new String[0]);
    }
  }
  
  public void upShareInfo()
  {
    this.shareinfo = ((Shareinfo)this.shareService.findById(this.shareId));
    Integer upCount = this.shareinfo.getUpCount();
    this.shareinfo.setUpCount(Integer.valueOf(upCount.intValue() + 1));
    this.shareService.add(this.shareinfo);
    Struts2Utils.renderText("true", new String[0]);
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
  
  public ShareInfoPro getShareInfoPro()
  {
    return this.shareInfoPro;
  }
  
  public void setShareInfoPro(ShareInfoPro shareInfoPro)
  {
    this.shareInfoPro = shareInfoPro;
  }
  
  public Sharecomments getSharecomments()
  {
    return this.sharecomments;
  }
  
  public void setSharecomments(Sharecomments sharecomments)
  {
    this.sharecomments = sharecomments;
  }
  
  public List<ShareCommentJSON> getShareCommentJSONList()
  {
    return this.shareCommentJSONList;
  }
  
  public void setShareCommentJSONList(List<ShareCommentJSON> shareCommentJSONList)
  {
    this.shareCommentJSONList = shareCommentJSONList;
  }
  
  public Shareinfo getShareinfo()
  {
    return this.shareinfo;
  }
  
  public void setShareinfo(Shareinfo shareinfo)
  {
    this.shareinfo = shareinfo;
  }
  
  public List<Shareimage> getShareimageList()
  {
    return this.shareimageList;
  }
  
  public void setShareimageList(List<Shareimage> shareimageList)
  {
    this.shareimageList = shareimageList;
  }
  
  public Latestlottery getLatestlottery()
  {
    return this.latestlottery;
  }
  
  public void setLatestlottery(Latestlottery latestlottery)
  {
    this.latestlottery = latestlottery;
  }
  
  public List<Latestlottery> getLatestlotteryList()
  {
    return this.latestlotteryList;
  }
  
  public void setLatestlotteryList(List<Latestlottery> latestlotteryList)
  {
    this.latestlotteryList = latestlotteryList;
  }
  
  public String getProductId()
  {
    return this.productId;
  }
  
  public void setProductId(String productId)
  {
    this.productId = productId;
  }
  
  public String getShareId()
  {
    return this.shareId;
  }
  
  public void setShareId(String shareId)
  {
    this.shareId = shareId;
  }
  
  public ShareCommentJSON getShareCommentJSON()
  {
    return this.shareCommentJSON;
  }
  
  public void setShareCommentJSON(ShareCommentJSON shareCommentJSON)
  {
    this.shareCommentJSON = shareCommentJSON;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getShareCommentId()
  {
    return this.shareCommentId;
  }
  
  public void setShareCommentId(String shareCommentId)
  {
    this.shareCommentId = shareCommentId;
  }
  
  public String getCommentText()
  {
    return this.commentText;
  }
  
  public void setCommentText(String commentText)
  {
    this.commentText = commentText;
  }
  
  public String getReCommentId()
  {
    return this.reCommentId;
  }
  
  public void setReCommentId(String reCommentId)
  {
    this.reCommentId = reCommentId;
  }
  
  public Product getProduct()
  {
    return this.product;
  }
  
  public void setProduct(Product product)
  {
    this.product = product;
  }
  
  public Spellbuyproduct getSpellbuyproduct()
  {
    return this.spellbuyproduct;
  }
  
  public void setSpellbuyproduct(Spellbuyproduct spellbuyproduct)
  {
    this.spellbuyproduct = spellbuyproduct;
  }
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
  }
  
  public List<ShareInfoJSON> getShareJSONList()
  {
    return this.ShareJSONList;
  }
  
  public void setShareJSONList(List<ShareInfoJSON> shareJSONList)
  {
    this.ShareJSONList = shareJSONList;
  }
  
  public ShareInfoJSON getShareInfoJSON()
  {
    return this.ShareInfoJSON;
  }
  
  public void setShareInfoJSON(ShareInfoJSON shareInfoJSON)
  {
    this.ShareInfoJSON = shareInfoJSON;
  }
  
  public Shareimage getShareimage()
  {
    return this.shareimage;
  }
  
  public void setShareimage(Shareimage shareimage)
  {
    this.shareimage = shareimage;
  }
}
