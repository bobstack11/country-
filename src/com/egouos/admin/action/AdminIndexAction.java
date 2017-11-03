package com.egouos.admin.action;

import com.egouos.action.RegisterAction;
import com.egouos.dao.Pagination;
import com.egouos.pojo.Applymention;
import com.egouos.pojo.Cardpassword;
import com.egouos.pojo.Commissionpoints;
import com.egouos.pojo.Consumerdetail;
import com.egouos.pojo.Consumetable;
import com.egouos.pojo.IndexImg;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.News;
import com.egouos.pojo.OrderBean;
import com.egouos.pojo.Orderdetail;
import com.egouos.pojo.Orderdetailaddress;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.Productimage;
import com.egouos.pojo.Producttype;
import com.egouos.pojo.Qqgroup;
import com.egouos.pojo.Recommend;
import com.egouos.pojo.SCity;
import com.egouos.pojo.SDistrict;
import com.egouos.pojo.SProvince;
import com.egouos.pojo.ShareJSON;
import com.egouos.pojo.Shareimage;
import com.egouos.pojo.Shareinfo;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Suggestion;
import com.egouos.pojo.SysConfigure;
import com.egouos.pojo.SysInfoBean;
import com.egouos.pojo.User;
import com.egouos.service.ApplymentionService;
import com.egouos.service.CardpasswordService;
import com.egouos.service.CommissionpointsService;
import com.egouos.service.ConsumerdetailService;
import com.egouos.service.ConsumetableService;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.NewsService;
import com.egouos.service.OrderdetailaddressService;
import com.egouos.service.ProductImageService;
import com.egouos.service.ProductService;
import com.egouos.service.ProducttypeService;
import com.egouos.service.RecommendService;
import com.egouos.service.RegionService;
import com.egouos.service.ShareService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.service.SysConfigureService;
import com.egouos.service.UserService;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.CutImages;
import com.egouos.util.DateUtil;
import com.egouos.util.EmailUtil;
import com.egouos.util.IPSeeker;
import com.egouos.util.MD5Util;
import com.egouos.util.PaginationUtil;
import com.egouos.util.Sampler;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UploadImagesUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.shcm.bean.SendResultBean;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.struts2.ServletActionContext;
import org.jdesktop.swingx.util.OS;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("AdminIndexAction")
public class AdminIndexAction
  extends ActionSupport
{
  private static final long serialVersionUID = 5983843029308947750L;
  @Autowired
  private UserService userService;
  @Autowired
  private ProductService productService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private ProducttypeService producttypeService;
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private ProductImageService productImageService;
  @Autowired
  private NewsService newsService;
  @Autowired
  private ShareService shareService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ConsumerdetailService consumerdetailService;
  @Autowired
  private ConsumetableService consumetableService;
  @Autowired
  private CardpasswordService cardpasswordService;
  @Autowired
  private RecommendService recommendService;
  @Autowired
  private ApplymentionService applymentionService;
  @Autowired
  private OrderdetailaddressService orderdetailaddressService;
  @Autowired
  private SysConfigureService sysConfigureService;
  @Autowired
  private RegionService regionService;
  @Autowired
  private CommissionpointsService commissionpointsService;
  private Product product;
  private Spellbuyproduct spellbuyproduct;
  private Productimage productimage;
  private Producttype producttype;
  private News news;
  private User user;
  private Shareinfo shareinfo;
  private Shareimage shareimage;
  private Qqgroup qqgroup;
  private List<Qqgroup> qqgroupList;
  private List<Producttype> productTypeList;
  private List<Producttype> productBrandList;
  private List<Product> productList;
  private List<Productimage> productimageList;
  private List<Shareimage> shareimageList;
  private List<News> newsList;
  private List<User> userList;
  private List<Latestlottery> latestlotteryList;
  private List<ShareJSON> ShareJSONList;
  private ShareJSON shareJSON;
  private Latestlottery latestlottery;
  private List<OrderBean> orderBeanList;
  private List<Cardpassword> cardpasswordList;
  private List<Applymention> applymentionList;
  private List<Orderdetailaddress> orderdetailaddressList;
  private SysConfigure sysConfigure;
  private List<IndexImg> indexImgList;
  private IndexImg indexImg;
  private List<Suggestion> suggestionList;
  private List<Orderdetail> orderdetailList;
  private Orderdetailaddress orderdetailaddress;
  private List<SProvince> sProvinceList;
  private List<SCity> sCityList;
  private List<SDistrict> sDistrictList;
  private Commissionpoints commissionpoints;
  private File myFile;
  private String myFileFileName;
  private String myFileContentType;
  private String imageFileName;
  private static final int BUFFER_SIZE = 102400;
  private int pageNo;
  private String pages;
  private String pageString;
  private int pageSize = 20;
  private int pageCount;
  private int resultCount;
  private String startDate;
  private String endDate;
  private String pId;
  private String cId;
  private String dId;
  private String id;
  private String keyword;
  private String userName;
  private String pwd;
  private String message;
  private String userId;
  private String announcedTime;
  private String typeId;
  private String tId;
  private String typeName;
  private List<ProductJSON> productJSONList;
  private ProductJSON productJSON;
  private List<File> Filedata;
  private List<String> FiledataFileName;
  private List<String> imageContentType;
  private String channelUrl;
  private String productType;
  private HttpGet httpGet;
  private HttpPost httpPost;
  private String backUrl;
  private SysInfoBean sysInfoBean;
  
  private static void copy(File src, File dst)
  {
    try
    {
      InputStream in = null;
      OutputStream out = null;
      try
      {
        in = new BufferedInputStream(new FileInputStream(src), 102400);
        out = new BufferedOutputStream(new FileOutputStream(dst), 102400);
        byte[] buffer = new byte[102400];
        while (in.read(buffer) > 0) {
          out.write(buffer);
        }
      }
      finally
      {
        if (in != null) {
          in.close();
        }
        if (out != null) {
          out.close();
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public String admin()
  {
    return "index";
  }
  
  public String index()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.pageNo == 0) {
        this.pageNo = 1;
      }
      if (this.pages != null) {
        this.pageNo = Integer.parseInt(this.pages.split("_")[1]);
      }
      if (this.id.equals("hot20"))
      {
    	Pagination hotPage = spellbuyrecordService.ProductByTypeIdList(typeId,"", "hot", pageNo, pageSize);
		List<Object[]> HotList = (List<Object[]>) hotPage.getList();
        this.productJSONList = new ArrayList();
        for (int i = 0; i < HotList.size(); i++)
        {
          this.productJSON = new ProductJSON();
          this.product = ((Product)((Object[])HotList.get(i))[0]);
          this.spellbuyproduct = ((Spellbuyproduct)((Object[])HotList.get(i))[1]);
          this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
          this.productJSON.setHeadImage(this.product.getHeadImage());
          this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
          this.productJSON.setProductName(this.product.getProductName());
          this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
          this.productJSON.setSinglePrice(this.product.getSinglePrice());
          this.productJSON.setProductTitle(this.product.getProductTitle());
          this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
          this.productJSONList.add(this.productJSON);
        }
        this.resultCount = hotPage.getResultCount();
        if ((this.typeId != null) && (!this.typeId.equals("")))
        {
          this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/index.action?id=" + this.id + "&type=" + this.typeId + "&pageNo=");
          this.pageString = this.pageString.replace(".html", "");
        }
        else
        {
          this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/index.action?id=" + this.id + "&pageNo=");
          this.pageString = this.pageString.replace(".html", "");
        }
        return "spellbuyProductList";
      }
      if (this.id.equals("date20"))
      {
        Pagination datePage = this.spellbuyrecordService.ProductByTypeIdList(this.typeId, "", "date", this.pageNo, this.pageSize);
        List<Object[]> dateList = (List<Object[]>)datePage.getList();
        this.productJSONList = new ArrayList();
        for (int i = 0; i < dateList.size(); i++)
        {
          this.productJSON = new ProductJSON();
          this.product = ((Product)((Object[])dateList.get(i))[0]);
          this.spellbuyproduct = ((Spellbuyproduct)((Object[])dateList.get(i))[1]);
          this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
          this.productJSON.setHeadImage(this.product.getHeadImage());
          this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
          this.productJSON.setProductName(this.product.getProductName());
          this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
          this.productJSON.setSinglePrice(this.product.getSinglePrice());
          this.productJSON.setProductTitle(this.product.getProductTitle());
          this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
          this.productJSONList.add(this.productJSON);
        }
        this.resultCount = datePage.getResultCount();
        if ((this.typeId != null) && (!this.typeId.equals("")))
        {
          this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/index.action?id=" + this.id + "&type=" + this.typeId + "&pageNo=");
          this.pageString = this.pageString.replace(".html", "");
        }
        else
        {
          this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/index.action?id=" + this.id + "&pageNo=");
          this.pageString = this.pageString.replace(".html", "");
        }
        return "spellbuyProductList";
      }
      if (this.id.equals("price20"))
      {
        Pagination pricePage = this.spellbuyrecordService.ProductByTypeIdList(this.typeId, "", "price", this.pageNo, this.pageSize);
        List<Object[]> priceList = (List<Object[]>)pricePage.getList();
        this.productJSONList = new ArrayList();
        for (int i = 0; i < priceList.size(); i++)
        {
          this.productJSON = new ProductJSON();
          this.product = ((Product)((Object[])priceList.get(i))[0]);
          this.spellbuyproduct = ((Spellbuyproduct)((Object[])priceList.get(i))[1]);
          this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
          this.productJSON.setHeadImage(this.product.getHeadImage());
          this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
          this.productJSON.setProductName(this.product.getProductName());
          this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
          this.productJSON.setSinglePrice(this.product.getSinglePrice());
          this.productJSON.setProductTitle(this.product.getProductTitle());
          this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
          this.productJSONList.add(this.productJSON);
        }
        this.resultCount = pricePage.getResultCount();
        if ((this.typeId != null) && (!this.typeId.equals("")))
        {
          this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/index.action?id=" + this.id + "&type=" + this.typeId + "&pageNo=");
          this.pageString = this.pageString.replace(".html", "");
        }
        else
        {
          this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/index.action?id=" + this.id + "&pageNo=");
          this.pageString = this.pageString.replace(".html", "");
        }
        return "spellbuyProductList";
      }
      if (this.id.equals("priceAsc20"))
      {
        Pagination pricePage = this.spellbuyrecordService.ProductByTypeIdList(this.typeId, "", "priceAsc", this.pageNo, this.pageSize);
        List<Object[]> priceList = (List<Object[]>)pricePage.getList();
        this.productJSONList = new ArrayList();
        for (int i = 0; i < priceList.size(); i++)
        {
          this.productJSON = new ProductJSON();
          this.product = ((Product)((Object[])priceList.get(i))[0]);
          this.spellbuyproduct = ((Spellbuyproduct)((Object[])priceList.get(i))[1]);
          this.productJSON.setCurrentBuyCount(this.spellbuyproduct.getSpellbuyCount());
          this.productJSON.setHeadImage(this.product.getHeadImage());
          this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
          this.productJSON.setProductName(this.product.getProductName());
          this.productJSON.setProductPrice(this.spellbuyproduct.getSpellbuyPrice());
          this.productJSON.setSinglePrice(this.product.getSinglePrice());
          this.productJSON.setProductTitle(this.product.getProductTitle());
          this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
          this.productJSONList.add(this.productJSON);
        }
        this.resultCount = pricePage.getResultCount();
        if ((this.typeId != null) && (!this.typeId.equals("")))
        {
          this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/index.action?id=" + this.id + "&type=" + this.typeId + "&pageNo=");
          this.pageString = this.pageString.replace(".html", "");
        }
        else
        {
          this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/index.action?id=" + this.id + "&pageNo=");
          this.pageString = this.pageString.replace(".html", "");
        }
        return "spellbuyProductList";
      }
    }
    return "index_index";
  }
  
  public String upSpellbuyproduct()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.product = ((Product)this.productService.findById(this.id));
        this.spellbuyproduct = this.spellbuyproductService.findSpellbuyproductByStatus(this.product.getProductId());
        if (this.spellbuyproduct == null)
        {
          int productPeriod = 0;
          Spellbuyproduct spellbuyproduct = this.spellbuyproductService.findSpellbuyproductLastPeriod(this.product.getProductId());
          if (spellbuyproduct != null)
          {
            productPeriod = spellbuyproduct.getProductPeriod().intValue();
            productPeriod++;
          }
          else
          {
            productPeriod = 1;
          }
          if (productPeriod > 0)
          {
            spellbuyproduct = new Spellbuyproduct();
            spellbuyproduct.setFkProductId(this.product.getProductId());
            spellbuyproduct.setProductPeriod(Integer.valueOf(productPeriod));
            spellbuyproduct.setSpellbuyCount(Integer.valueOf(0));
            spellbuyproduct.setSpellbuyEndDate(DateUtil.DateTimeToStr(new Date()));
            spellbuyproduct.setSpellbuyPrice(this.product.getProductPrice());
            spellbuyproduct.setSpSinglePrice(this.product.getSinglePrice());
            spellbuyproduct.setSpellbuyStartDate(DateUtil.DateTimeToStr(new Date()));
            spellbuyproduct.setSpStatus(Integer.valueOf(0));
            if (this.product.getAttribute71().equals("hot")) {
              spellbuyproduct.setSpellbuyType(Integer.valueOf(8));
            } else {
              spellbuyproduct.setSpellbuyType(Integer.valueOf(0));
            }
            this.spellbuyproductService.add(spellbuyproduct);
            this.product.setAttribute71(String.valueOf(productPeriod + 1));
          }
        }
        this.product.setStatus(Integer.valueOf(1));
        this.productService.add(this.product);
        Struts2Utils.renderText("success", new String[0]);
      }
      else
      {
        Struts2Utils.renderText("test", new String[0]);
      }
    }
    return null;
  }
  
  public void downSpellbuyproduct()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.product = ((Product)this.productService.findById(this.id));
        this.product.setStatus(Integer.valueOf(2));
        this.productService.add(this.product);
        Struts2Utils.renderText("success", new String[0]);
      }
      else
      {
        Struts2Utils.renderText("test", new String[0]);
      }
    }
  }
  
  public void hotProduct()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        try
        {
          this.product = ((Product)this.productService.findById(this.id));
          this.product.setAttribute71("hot");
          this.productService.add(this.product);
          List<Spellbuyproduct> list = this.spellbuyproductService.findSpellbuyproductByProductIdIsStatus(Integer.valueOf(Integer.parseInt(this.id)));
          if ((list != null) && (list.size() > 0))
          {
            this.spellbuyproduct = ((Spellbuyproduct)list.get(0));
            this.spellbuyproduct.setSpellbuyType(Integer.valueOf(8));
            this.spellbuyproductService.add(this.spellbuyproduct);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        Struts2Utils.renderText("success", new String[0]);
      }
      else
      {
        Struts2Utils.renderText("test", new String[0]);
      }
    }
  }
  
  public void downHotProduct()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        try
        {
          this.product = ((Product)this.productService.findById(this.id));
          this.product.setAttribute71("0");
          this.productService.add(this.product);
          List<Spellbuyproduct> list = this.spellbuyproductService.findSpellbuyproductByProductIdIsStatus(Integer.valueOf(Integer.parseInt(this.id)));
          if ((list != null) && (list.size() > 0))
          {
            this.spellbuyproduct = ((Spellbuyproduct)list.get(0));
            this.spellbuyproduct.setSpellbuyType(Integer.valueOf(0));
            this.spellbuyproductService.add(this.spellbuyproduct);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        Struts2Utils.renderText("success", new String[0]);
      }
      else
      {
        Struts2Utils.renderText("test", new String[0]);
      }
    }
  }
  
  public String announcedProduct()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      Pagination hotPage = this.spellbuyproductService.announcedProduct(this.pageNo, this.pageSize);
      List<Object[]> HotList = (List<Object[]>)hotPage.getList();
      this.productJSONList = new ArrayList();
      for (int i = 0; i < HotList.size(); i++)
      {
        this.productJSON = new ProductJSON();
        this.product = ((Product)((Object[])HotList.get(i))[0]);
        this.spellbuyproduct = ((Spellbuyproduct)((Object[])HotList.get(i))[1]);
        this.productJSON.setCurrentBuyCount(this.product.getProductId());
        this.productJSON.setHeadImage(this.product.getHeadImage());
        this.productJSON.setProductId(this.spellbuyproduct.getSpellbuyProductId());
        this.productJSON.setProductName(this.product.getProductName());
        this.productJSON.setProductPrice(this.product.getProductPrice());
        this.productJSON.setSinglePrice(this.product.getSinglePrice());
        this.productJSON.setProductTitle(this.product.getProductTitle());
        this.productJSON.setProductPeriod(this.spellbuyproduct.getProductPeriod());
        this.productJSONList.add(this.productJSON);
      }
      this.resultCount = hotPage.getResultCount();
      if ((this.typeId != null) && (!this.typeId.equals("")))
      {
        this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/announcedProduct.action?pageNo=");
        this.pageString = this.pageString.replace(".html", "");
      }
      else
      {
        this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/announcedProduct.action?pageNo=");
        this.pageString = this.pageString.replace(".html", "");
      }
      return "announcedProductList";
    }
    return "index_index";
  }
  
  public String login()
  {
    if (StringUtil.isBlank(this.userName)) {
      Struts2Utils.render("text/html", "<script>alert(\"用户名或密码错误！\");window.location.href=\"/admin_back/admin.html\";</script>", new String[] { "encoding:UTF-8" });
    }
    this.user = this.userService.userByName(this.userName);
    if ((this.user != null) && (this.user.getUserType() != null) && ("1".equals(this.user.getUserType())))
    {
      String password = this.user.getUserPwd();
      if ((StringUtil.isNotBlank(password)) && (password.equals(this.pwd)))
      {
        Struts2Utils.getSession().setAttribute("admin", this.user);
        String ip = Struts2Utils.getRequest().getHeader("X-Real-IP");
        if (ip == null) {
          ip = Struts2Utils.getRequest().getRemoteAddr();
        }
        String date = DateUtil.DateTimeToStr(new Date());
        this.user.setIpAddress(ip);
        String ipLocation = RegisterAction.seeker.getAddress(ip);
        this.user.setIpLocation(ipLocation);
        this.user.setOldIpAddress(this.user.getIpAddress() + "(" + this.user.getIpLocation() + ")");
        this.user.setOldDate(this.user.getNewDate());
        this.user.setNewDate(date);
        this.userService.add(this.user);
        Struts2Utils.render("text/html", "<script>window.location.href=\"/admin_back/main.action\";</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"用户名或密码错误！\");window.location.href=\"/admin_back/admin.html\";</script>", new String[] { "encoding:UTF-8" });
      }
    }
    else
    {
      Struts2Utils.render("text/html", "<script>alert(\"用户名或密码错误！\");window.location.href=\"/admin_back/admin.html\";</script>", new String[] { "encoding:UTF-8" });
    }
    return null;
  }
  
  public String logOut()
  {
    Struts2Utils.getSession().removeAttribute("admin");
    return "index";
  }
  
  public String main()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "main";
    }
    return "index_index";
  }
  
  public void numberCount()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      Pagination page = this.latestlotteryService.LatestAnnounced(this.pageNo, this.pageSize);
      Pagination hotPage = this.spellbuyrecordService.ProductByTypeIdList(this.typeId, "", "hot", this.pageNo, this.pageSize);
      Pagination page2 = this.shareService.loadPageShare("new20", this.pageNo, this.pageSize);
      int userCount = this.userService.getCountUser().intValue();
      Long buyCount = Long.valueOf(Long.parseLong(this.spellbuyrecordService.getAllByCount().toString()));
      int lotteryCount = page.getResultCount();
      int productCount = hotPage.getResultCount();
      int shareCount = page2.getResultCount();
      this.message = (userCount + "_" + buyCount + "_" + productCount + "_" + lotteryCount + "_" + shareCount);
      Struts2Utils.renderText(this.message, new String[0]);
    }
  }
  
  public String toAddProduct()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.productTypeList = this.producttypeService.queryAllProductType();
      this.productBrandList = this.producttypeService.listByBrand(null);
      return "addProduct";
    }
    return "index_index";
  }
  
  public void productBrandListByTypeId()
  {
    this.productBrandList = this.producttypeService.listByBrand(this.typeId);
    Struts2Utils.renderJson(this.productBrandList, new String[0]);
  }
  
  public void addProduct()
    throws Exception
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        String productImgPath = "/productImg/show";
        this.productService.add(this.product);
        try
        {
          if (this.myFile != null)
          {
            this.myFileFileName = this.myFileFileName.substring(this.myFileFileName.lastIndexOf("."), this.myFileFileName.length());
            this.imageFileName = (new Date().getTime() + this.myFileFileName);
            File imageFile = new File(UploadImagesUtil.getFolder(new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getRealPath(productImgPath))).append("/").toString(), String.valueOf(this.product.getProductId())) + "/" + this.imageFileName);
            copy(this.myFile, imageFile);
            CutImages.equimultipleConvert(200, 200, imageFile, imageFile);
            this.product.setHeadImage(productImgPath + "/" + String.valueOf(this.product.getProductId()) + "/" + this.imageFileName);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        this.product.setProductRealPrice(String.valueOf(this.product.getProductPrice()));
        this.product.setStatus(Integer.valueOf(0));
        this.product.setAttribute71(String.valueOf(1));
        this.productService.add(this.product);
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权添加！，请联系管理员！\");window.location.href=\"/admin_back/productList.action\";</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public String toUpdate()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.product = ((Product)this.productService.findById(this.id));
      this.productTypeList = this.producttypeService.queryAllProductType();
      this.productBrandList = this.producttypeService.listByBrand(null);
      String queryString = Struts2Utils.getRequest().getQueryString();
      this.backUrl = queryString.substring(queryString.indexOf("backUrl=") + 8, queryString.length());
      
      return "addProduct";
    }
    return "index_index";
  }
  
  public void update()
    throws Exception
  {
    HttpServletRequest request = Struts2Utils.getRequest();
    HttpServletResponse response = Struts2Utils.getResponse();
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        String productImgPath = "/productImg/show";
        if (this.myFile != null)
        {
          this.myFileFileName = this.myFileFileName.substring(this.myFileFileName.lastIndexOf("."), this.myFileFileName.length());
          this.imageFileName = (new Date().getTime() + this.myFileFileName);
          File imageFile = new File(UploadImagesUtil.getFolder(new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getRealPath(productImgPath))).append("/").toString(), String.valueOf(this.product.getProductId())) + "/" + this.imageFileName);
          copy(this.myFile, imageFile);
          CutImages.equimultipleConvert(200, 200, imageFile, imageFile);
          this.product.setHeadImage(productImgPath + "/" + String.valueOf(this.product.getProductId()) + "/" + this.imageFileName);
        }
        this.spellbuyproduct = this.spellbuyproductService.findSpellbuyproductByStatus(this.product.getProductId());
        if (this.spellbuyproduct != null)
        {
          this.spellbuyproduct.setSpSinglePrice(this.product.getSinglePrice());
          this.spellbuyproductService.add(this.spellbuyproduct);
        }
        this.productService.add(this.product);
        Struts2Utils.render("text/html", "<script>window.location.href=\"" + this.backUrl + "\";</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");window.location.href=\"/admin_back/productList.action\";</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public void deleteProduct()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        try
        {
          this.productService.delete(Integer.valueOf(Integer.parseInt(this.id)));
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        Struts2Utils.render("text/html", "<script>window.location.href=\"" + this.backUrl + "\";</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权删除！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public String toAddProductType()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.productTypeList = this.producttypeService.queryAllProductType();
      if (this.id != null) {
        this.producttype = ((Producttype)this.producttypeService.findById(this.id));
      }
      return "addProductType";
    }
    return "index_index";
  }
  
  public String addProductType()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.producttype.getFtypeId() == null) {
        this.producttype.setFtypeId("1000");
      }
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.producttype.setStypeId("0");
        this.producttype.setAttribute70("type");
        if (this.producttype.getTypeId() == null)
        {
          if (this.producttypeService.findTypeByName(this.producttype.getTypeName()) != null)
          {
            Struts2Utils.render("text/html", "<script>alert(\"该分类名称已经存在！\");history.go(-2);</script>", new String[] { "encoding:UTF-8" });
            return null;
          }
          this.producttypeService.add(this.producttype);
        }
        else
        {
          this.producttypeService.add(this.producttype);
        }
        return "addProductTypeSuccess";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String productTypeList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.productTypeList = this.producttypeService.queryAllProductType();
      return "productTypeList";
    }
    return "index_index";
  }
  
  public String productBrandList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.productTypeList = this.producttypeService.queryAllProductType();
      this.productBrandList = this.producttypeService.listByBrand(null);
      return "productBrandList";
    }
    return "index_index";
  }
  
  public String toAddProductBrand()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.productTypeList = this.producttypeService.queryAllProductType();
      if (this.id != null) {
        this.producttype = this.producttypeService.findBrandById(this.id);
      }
      return "addProductBrand";
    }
    return "index_index";
  }
  
  public String addProductBrand()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        if (this.producttype.getFtypeId() == null) {
          this.producttype.setFtypeId("1000");
        }
        this.producttype.setStypeId("0");
        this.producttype.setAttribute70("brand");
        if (this.producttype.getTypeId() == null)
        {
          if (this.producttypeService.findBrandByName(this.producttype.getTypeName()) != null)
          {
            Struts2Utils.render("text/html", "<script>alert(\"该品牌名称已经存在！\");history.go(-2);</script>", new String[] { "encoding:UTF-8" });
            return null;
          }
          this.producttypeService.add(this.producttype);
        }
        else
        {
          this.producttypeService.add(this.producttype);
        }
        return "addProductBrandSuccess";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String productList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.pageNo == 0) {
        this.pageNo = 1;
      }
      if (this.pages != null) {
        this.pageNo = Integer.parseInt(this.pages.split("_")[1]);
      }
      if (this.typeId == null) {
        this.typeId = "";
      }
      if (this.keyword == null) {
        this.keyword = "";
      }
      this.productTypeList = this.producttypeService.queryAllProductType();
      this.productBrandList = this.producttypeService.listByBrand(null);
      Pagination datePage = this.productService.searchProduct(this.typeId, this.keyword, this.pageNo, this.pageSize);
      this.productList = (List<Product>)datePage.getList();
      this.resultCount = datePage.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/productList.action?typeId=" + this.typeId + "&keyword=" + this.keyword + "&pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "productList";
    }
    return "index_index";
  }
  
  public String toAddProductImg()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.productimageList = this.productImageService.findByProductId(this.id, "show");
      return "addProductImg";
    }
    return "index_index";
  }
  
  public String addProductImg()
    throws Exception
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        String productImgPath = "/productImg/imagezoom";
        long fileDateName = 0L;
        String imagePath = null;
        if ((this.Filedata == null) || (this.Filedata.size() == 0)) {
          return null;
        }
        for (int i = 0; i < this.Filedata.size(); i++) {
          try
          {
            this.myFile = ((File)this.Filedata.get(i));
            if (this.myFile != null)
            {
              this.myFileFileName = ((String)this.FiledataFileName.get(i)).substring(((String)this.FiledataFileName.get(i)).lastIndexOf("."), ((String)this.FiledataFileName.get(i)).length());
              fileDateName = new Date().getTime();
              this.imageFileName = (fileDateName + this.myFileFileName);
              imagePath = UploadImagesUtil.getFolder(new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getRealPath(productImgPath))).append("/").toString(), this.id) + "/";
              File imageFile = new File(imagePath + this.imageFileName);
              copy(this.myFile, imageFile);
              CutImages.equimultipleConvert(310, 310, imageFile, new File(imagePath + fileDateName + "_mid" + this.myFileFileName));
              CutImages.equimultipleConvert(40, 40, imageFile, new File(imagePath + fileDateName + "_small" + this.myFileFileName));
              this.productimage = new Productimage();
              this.productimage.setPiProductId(Integer.valueOf(Integer.parseInt(this.id)));
              this.productimage.setImage(String.valueOf(fileDateName));
              this.productimage.setImageType(this.myFileFileName);
              this.productimage.setAttribute75("show");
              this.productImageService.add(this.productimage);
            }
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        }
        Struts2Utils.render("text/html", "<script>alert(\"上传成功！\");history.go(-2);</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
    return null;
  }
  
  public void doDeleteProductImg()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        try
        {
          this.productImageService.delete(Integer.valueOf(Integer.parseInt(this.id)));
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        Struts2Utils.render("text/html", "<script>alert(\"删除成功！\");history.go(-2);</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public String toAddSpellbuyProduct()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "addSpellbuyProduct";
    }
    return "index_index";
  }
  
  public String addSpellbuyProduct()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "spellbuyProductList";
    }
    return "index_index";
  }
  
  public String uploadImages()
    throws Exception
  {
    String productImgPath = "/productImg/detail";
    long fileDateName = 0L;
    String imagePath = null;
    if ((this.Filedata == null) || (this.Filedata.size() == 0)) {
      return null;
    }
    for (int i = 0; i < this.Filedata.size(); i++)
    {
      this.myFile = ((File)this.Filedata.get(i));
      if (this.myFile != null)
      {
        this.myFileFileName = ((String)this.FiledataFileName.get(i)).substring(((String)this.FiledataFileName.get(i)).lastIndexOf("."), ((String)this.FiledataFileName.get(i)).length()).toLowerCase();
        fileDateName = new Date().getTime();
        this.imageFileName = (fileDateName + this.myFileFileName);
        imagePath = UploadImagesUtil.getFolder(new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getRealPath(productImgPath))).append("/").toString(), DateUtil.dateToStr(new Date())) + "/";
        File imageFile = new File(imagePath + this.imageFileName);
        copy(this.myFile, imageFile);
      }
    }
    return null;
  }
  
  public String crawl()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.productTypeList = this.producttypeService.queryAllProductType();
      return "crawl";
    }
    return "index_index";
  }
  
  public String doCrawl()
    throws Exception
  {
    if ((this.pages != null) && (!this.pages.equals("")))
    {
      String url = "";
      int page = Integer.parseInt(this.pages);
      for (int i = 1; i < page; i++) {
        try
        {
          url = this.channelUrl.substring(0, this.channelUrl.lastIndexOf("0-1-1-") + 6);
          url = url + i + this.channelUrl.substring(this.channelUrl.lastIndexOf("0-1-1-") + 7, this.channelUrl.length());
          System.err.println(url);
          Document document = Jsoup.parse(new URL(url), 60000);
          Elements elements = null;
          elements = document.select("div.plist > ul > li");
          if (elements.size() == 0) {
            elements = document.select("div#plist > ul > li");
          }
          //label1158:
          for (Element element : elements) {
            try
            {
              String productImg = "";
              String productName = "";
              String productTitle = "";
              int productPrice = 0;
              int productRealPrice = 0;
              String productDetail = "";
              String headImage = "";
              String Attribute_71 = "1";
              String strImg = element.select("div.p-img > a > img").first().attr("src");
              if (strImg.indexOf("360buyimg.com") != -1)
              {
                productImg = strImg;
              }
              else
              {
                String strImg2 = element.select("div.p-img > a > img").first().attr("src2");
                productImg = strImg2;
              }
              productImg = productImg.replace(".com/n2/", ".com/n1/");
              productName = element.select("div.p-name > a").text();
              this.product = this.productService.findProductByName(productName);
              if (this.product == null)
              {
                this.product = new Product();
                String proUrl = element.select("div.p-name > a").attr("href");
                System.err.println(proUrl);
                File myFile = saveURLFile(productImg);
                
                String myFileFileName = myFile.getName();
                this.product.setAttribute71("1");
                this.product.setProductType(Integer.valueOf(Integer.parseInt(this.productType)));
                this.product.setProductDetail(proUrl);
                this.product.setProductName(productName);
                this.product.setProductPrice(Integer.valueOf(0));
                this.product.setProductRealPrice(String.valueOf(0));
                this.product.setProductTitle(productName);
                this.product.setStatus(Integer.valueOf(0));
                this.productService.add(this.product);
                String productImgPath = "/productImg/show";
                if (myFile != null)
                {
                  myFileFileName = myFileFileName.substring(myFileFileName.lastIndexOf("."), myFileFileName.length());
                  String imageFileName = new Date().getTime() + myFileFileName;
                  File imageFile = new File(UploadImagesUtil.getFolder(new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getRealPath(productImgPath))).append("/").toString(), String.valueOf(this.product.getProductId())) + "/" + imageFileName);
                  copy(myFile, imageFile);
                  CutImages.equimultipleConvert(200, 200, imageFile, imageFile);
                  this.product.setHeadImage(productImgPath + "/" + String.valueOf(this.product.getProductId()) + "/" + imageFileName);
                }
                this.productService.add(this.product);
                
                Document document2 = Jsoup.parse(new URL(proUrl), 60000);
                
                Elements element2 = document2.select("div.spec-items > ul > li");
                int count = 0;
                for (Element element3 : element2) {
					 try {
						 count ++;
							String img = element3.select("img").attr("src");
//							http://img14.360buyimg.com/n0/g9/M03/0D/12/rBEHaVCXVAsIAAAAAADw0WaBZaIAACptQFzb24AAPDp968.jpg
//							http://img14.360buyimg.com/n5/g9/M03/0D/12/rBEHaVCXVAsIAAAAAADw0WaBZaIAACptQFzb24AAPDp968.jpg

							img = img.replace(".com/n5/", ".com/n0/");
							System.err.println(img);
							productimage = new Productimage();
							String productImgPath2 = "/productImg/imagezoom";
							long fileDateName = 0;
							String imagePath = null;
							File myFile2 = saveURLFile(img);
							String myFileFileName2 = myFile2.getName();
							if(myFile2!=null){
								myFileFileName2 = myFileFileName2.substring(myFileFileName2.lastIndexOf("."), myFileFileName2.length());
								fileDateName = new Date().getTime();
								String imageFileName = fileDateName + myFileFileName;
								imagePath = UploadImagesUtil.getFolder(ServletActionContext.getServletContext().getRealPath(productImgPath2)+"/", String.valueOf(product.getProductId()))+"/";
							       File imageFile = new File(imagePath + imageFileName);
							       copy(myFile, imageFile);
							       CutImages.equimultipleConvert(310, 310, imageFile, new File(imagePath+fileDateName+"_mid"+myFileFileName));
							       CutImages.equimultipleConvert(40, 40, imageFile, new File(imagePath+fileDateName+"_small"+myFileFileName));
							       productimage.setPiProductId(product.getProductId());
							       productimage.setImage(String.valueOf(fileDateName));
							       productimage.setImageType(myFileFileName);
							       productimage.setAttribute75("show");
							       productImageService.add(productimage);
					       }
							if(count ==6){
								break;
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
				 }
              }
            }
            catch (Exception e)
            {
              Iterator localIterator2;
              e.printStackTrace();
            }
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    System.err.println("结束");
    Struts2Utils.renderText("结束", new String[0]);
    return null;
  }
  
  public String doCrawlShare()
    throws Exception
  {
    if ((this.pages != null) && (!this.pages.equals("")))
    {
      String url = "";
      int page = Integer.parseInt(this.pages);
      for (int i = 1; i < page; i++) {
        try
        {
          url = this.channelUrl.substring(0, this.channelUrl.lastIndexOf("0-1-1-") + 6);
          url = url + i + this.channelUrl.substring(this.channelUrl.lastIndexOf("0-1-1-") + 7, this.channelUrl.length());
          System.err.println(url);
          Document document = Jsoup.parse(new URL(url), 60000);
          Elements elements = null;
          elements = document.select("div.plist > ul > li");
          if (elements.size() == 0) {
            elements = document.select("div#plist > ul > li");
          }
          label1158:
          for (Element element : elements) {
            try
            {
              String productImg = "";
              String productName = "";
              String productTitle = "";
              int productPrice = 0;
              int productRealPrice = 0;
              String productDetail = "";
              String headImage = "";
              String Attribute_71 = "1";
              String strImg = element.select("div.p-img > a > img").first().attr("src");
              if (strImg.indexOf("360buyimg.com") != -1)
              {
                productImg = strImg;
              }
              else
              {
                String strImg2 = element.select("div.p-img > a > img").first().attr("src2");
                productImg = strImg2;
              }
              productImg = productImg.replace(".com/n2/", ".com/n1/");
              productName = element.select("div.p-name > a").text();
              this.product = this.productService.findProductByName(productName);
              if (this.product == null)
              {
                this.product = new Product();
                String proUrl = element.select("div.p-name > a").attr("href");
                System.err.println(proUrl);
                File myFile = saveURLFile(productImg);
                
                String myFileFileName = myFile.getName();
                this.product.setAttribute71("1");
                this.product.setProductType(Integer.valueOf(Integer.parseInt(this.productType)));
                this.product.setProductDetail(proUrl);
                this.product.setProductName(productName);
                this.product.setProductPrice(Integer.valueOf(0));
                this.product.setProductRealPrice(String.valueOf(0));
                this.product.setProductTitle(productName);
                this.product.setStatus(Integer.valueOf(0));
                this.productService.add(this.product);
                String productImgPath = "/productImg/show";
                if (myFile != null)
                {
                  myFileFileName = myFileFileName.substring(myFileFileName.lastIndexOf("."), myFileFileName.length());
                  String imageFileName = new Date().getTime() + myFileFileName;
                  File imageFile = new File(UploadImagesUtil.getFolder(new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getRealPath(productImgPath))).append("/").toString(), String.valueOf(this.product.getProductId())) + "/" + imageFileName);
                  copy(myFile, imageFile);
                  CutImages.equimultipleConvert(200, 200, imageFile, imageFile);
                  this.product.setHeadImage(productImgPath + "/" + String.valueOf(this.product.getProductId()) + "/" + imageFileName);
                }
                this.productService.add(this.product);
                
                Document document2 = Jsoup.parse(new URL(proUrl), 60000);
                
                Elements element2 = document2.select("div.spec-items > ul > li");
                int count = 0;
                for (Element element3 : element2) {
					 try {
						 count ++;
							String img = element3.select("img").attr("src");
//							http://img14.360buyimg.com/n0/g9/M03/0D/12/rBEHaVCXVAsIAAAAAADw0WaBZaIAACptQFzb24AAPDp968.jpg
//							http://img14.360buyimg.com/n5/g9/M03/0D/12/rBEHaVCXVAsIAAAAAADw0WaBZaIAACptQFzb24AAPDp968.jpg

							img = img.replace(".com/n5/", ".com/n0/");
							System.err.println(img);
							productimage = new Productimage();
							String productImgPath2 = "/productImg/imagezoom";
							long fileDateName = 0;
							String imagePath = null;
							File myFile2 = saveURLFile(img);
							String myFileFileName2 = myFile2.getName();
							if(myFile2!=null){
								myFileFileName2 = myFileFileName2.substring(myFileFileName2.lastIndexOf("."), myFileFileName2.length());
								fileDateName = new Date().getTime();
								String imageFileName = fileDateName + myFileFileName;
								imagePath = UploadImagesUtil.getFolder(ServletActionContext.getServletContext().getRealPath(productImgPath2)+"/", String.valueOf(product.getProductId()))+"/";
							       File imageFile = new File(imagePath + imageFileName);
							       copy(myFile, imageFile);
							       CutImages.equimultipleConvert(310, 310, imageFile, new File(imagePath+fileDateName+"_mid"+myFileFileName));
							       CutImages.equimultipleConvert(40, 40, imageFile, new File(imagePath+fileDateName+"_small"+myFileFileName));
							       productimage.setPiProductId(product.getProductId());
							       productimage.setImage(String.valueOf(fileDateName));
							       productimage.setImageType(myFileFileName);
							       productimage.setAttribute75("show");
							       productImageService.add(productimage);
					       }
							if(count ==6){
								break;
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
				 }
              }
            }
            catch (Exception e)
            {
              Iterator localIterator2;
              e.printStackTrace();
            }
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    System.err.println("结束");
    Struts2Utils.renderText("结束", new String[0]);
    return null;
  }
  
  public static File saveURLFile(String fileUrl)
    throws MalformedURLException
  {
    File file = null;
    try
    {
      URL url = new URL(fileUrl);
      String myFileFileName = fileUrl;
      URLConnection connect = url.openConnection();
      connect.connect();
      InputStream input = connect.getInputStream();
      myFileFileName = myFileFileName.substring(myFileFileName.lastIndexOf("."), myFileFileName.length());
      String image = ServletActionContext.getServletContext().getRealPath("/uploadImages") + "/" + "imageTemp" + myFileFileName;
      file = new File(image);
      
      OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
      
      int length = 1048576;
      byte[] a = new byte[length];
      while ((length = input.read(a)) > 0) {
        out.write(a, 0, length);
      }
      input.close();
      out.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return file;
  }
  
  public String toAddNews()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "addNews";
    }
    return "index_index";
  }
  
  public String addNews()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        if ((this.news.getPostDate() == null) || (this.news.getPostDate().equals(""))) {
          this.news.setPostDate(DateUtil.DateToStr(new Date()));
        }
        this.newsService.add(this.news);
        return "successNews";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String toUpdateNews()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.news = ((News)this.newsService.findById(this.id));
      return "addNews";
    }
    return "index_index";
  }
  
  public String updateNews()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.newsService.add(this.news);
        return "successNews";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String deleteNews()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.newsService.delete(Integer.valueOf(Integer.parseInt(this.id)));
        return "successNews";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
    }
    return "index_index";
  }
  
  public String newsList()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      Pagination datePage = this.newsService.newsList(this.pageNo, this.pageSize);
      this.newsList = (List<News>)datePage.getList();
      this.resultCount = datePage.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/newsList.action?pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "newsList";
    }
    return "index_index";
  }
  
  public String replaceNews()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        com.egouos.action.IndexAction.newsList = this.newsService.indexNews();
        Struts2Utils.render("text/html", "<script>alert(\"更新新闻成功！\");window.location.href=\"/admin_back/newsList.action\";</script>", new String[] { "encoding:UTF-8" });
        return null;
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");window.location.href=\"/admin_back/newsList.action\";</script>", new String[] { "encoding:UTF-8" });
    }
    return null;
  }
  
  public String indexImgAll()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.indexImgList = this.sysConfigureService.IndexImgAll();
      return "indexImgAll";
    }
    return "index_index";
  }
  
  public String toAddIndexImg()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (StringUtil.isNotBlank(this.id)) {
        this.indexImg = this.sysConfigureService.findByIndexImgId(this.id);
      }
      return "toIndexImg";
    }
    return "index_index";
  }
  
  public void doAddIndexImg()
    throws Exception
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        String productImgPath = "/productImg/show";
        try
        {
          if (this.myFile != null)
          {
            this.myFileFileName = this.myFileFileName.substring(this.myFileFileName.lastIndexOf("."), this.myFileFileName.length());
            this.imageFileName = (new Date().getTime() + this.myFileFileName);
            File imageFile = new File(UploadImagesUtil.getFolder(new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getRealPath(productImgPath))).append("/").toString(), "indexImg") + "/" + this.imageFileName);
            copy(this.myFile, imageFile);
            CutImages.equimultipleConvert(710, 300, imageFile, imageFile);
            this.indexImg.setProImg(productImgPath + "/" + "indexImg" + "/" + this.imageFileName);
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        this.sysConfigureService.addIndexImg(this.indexImg);
        ApplicationListenerImpl.indexImgAll = this.sysConfigureService.initializationIndexImgAll();
        Struts2Utils.render("text/html", "<script>alert(\"操作成功！\");window.location.href=\"/admin_back/indexImgAll.action\";</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public void delIndexImg()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.sysConfigureService.delIndexImg(Integer.valueOf(Integer.parseInt(this.id)));
        ApplicationListenerImpl.indexImgAll = this.sysConfigureService.initializationIndexImgAll();
        Struts2Utils.render("text/html", "<script>alert(\"操作成功！\");window.location.href=\"/admin_back/indexImgAll.action\";</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public String suggestion()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.pageNo == 0) {
        this.pageNo = 1;
      }
      Pagination page = this.sysConfigureService.suggestionList(this.pageNo, this.pageSize);
      this.suggestionList = (List<Suggestion>)page.getList();
      this.resultCount = page.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/suggestion.action?pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "suggestion";
    }
    return "index_index";
  }
  
  public String toQqGroup()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "toQqGroup";
    }
    return "index_index";
  }
  
  public void doQqGroup()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.regionService.addQQGroup(this.qqgroup);
        Struts2Utils.render("text/html", "<script>alert(\"操作成功！\");window.location.href=\"/admin_back/qqGroupByList.action\";</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public void delQqGroup()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.regionService.delQQGroup(Integer.valueOf(Integer.parseInt(this.id)));
        Struts2Utils.render("text/html", "<script>alert(\"操作成功！\");window.location.href=\"/admin_back/qqGroupByList.action\";</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public String qqGroupByList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.qqgroupList = this.regionService.qqGroupByList(this.pId, this.cId, this.dId);
      return "qqGroupList";
    }
    return "index_index";
  }
  
  public String shareList()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      Pagination page = this.shareService.adminShareList(this.typeId, this.id, this.pageNo, this.pageSize);
      List<Object[]> pageList = (List<Object[]>)page.getList();
      this.ShareJSONList = new ArrayList();
      for (int i = 0; i < pageList.size(); i++)
      {
        this.shareJSON = new ShareJSON();
        this.shareinfo = ((Shareinfo)((Object[])pageList.get(i))[0]);
        this.latestlottery = ((Latestlottery)((Object[])pageList.get(i))[1]);
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
        this.shareJSON.setShareDate(this.shareinfo.getShareDate());
        this.shareJSON.setShareTitle(this.shareinfo.getShareTitle());
        this.shareJSON.setUid(this.shareinfo.getUid());
        this.shareJSON.setUpCount(this.shareinfo.getUpCount());
        this.shareJSON.setUserName(userName);
        this.shareJSON.setStatus(this.shareinfo.getStatus());
        this.ShareJSONList.add(this.shareJSON);
      }
      this.resultCount = page.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/shareList.action?id=" + this.id + "&typeId=" + this.typeId + "&pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "shareList";
    }
    return "index_index";
  }
  
  public String shareByStatus()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      Pagination page = this.latestlotteryService.adminByLatestAnnounced(this.pageNo, this.pageSize, this.typeId, this.tId);
      this.resultCount = page.getResultCount();
      List<Latestlottery> objList = (List<Latestlottery>)page.getList();
      this.latestlotteryList = new ArrayList();
      for (int i = 0; i < objList.size(); i++)
      {
        this.latestlottery = new Latestlottery();
        this.latestlottery = ((Latestlottery)objList.get(i));
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
        this.latestlottery.setBuyUser(userName);
        this.latestlotteryList.add(this.latestlottery);
      }
      this.resultCount = page.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/shareByStatus.action?tId=" + this.tId + "&id=" + this.id + "&pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "shareByStatus";
    }
    return "index_index";
  }
  
  public String latestList()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      Pagination page = this.latestlotteryService.adminByLatestAnnounced(this.pageNo, this.pageSize, this.typeId, this.tId);
      this.resultCount = page.getResultCount();
      List<Latestlottery> objList = (List<Latestlottery>)page.getList();
      this.latestlotteryList = new ArrayList();
      for (int i = 0; i < objList.size(); i++)
      {
        this.latestlottery = new Latestlottery();
        this.latestlottery = ((Latestlottery)objList.get(i));
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
        this.latestlottery.setBuyUser(userName);
        this.latestlotteryList.add(this.latestlottery);
      }
      this.resultCount = page.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/latestList.action?typeId=" + this.typeId + "&id=" + this.id + "&pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "latestList";
    }
    return "index_index";
  }
  
  public String toPostDeliver()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.latestlottery = ((Latestlottery)this.latestlotteryService.findById(this.id));
      this.orderdetailaddress = this.latestlotteryService.orderdetailaddressFindByOrderdetailId(this.id);
      return "postDeliver";
    }
    return "index_index";
  }
  
  public String doPostDeliver()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.orderdetailaddressService.add(this.orderdetailaddress);
        this.latestlottery = ((Latestlottery)this.latestlotteryService.findById(this.id));
        this.latestlottery.setStatus(Integer.valueOf(3));
        this.latestlotteryService.add(this.latestlottery);
        return "postDeliverSuccess";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String toAddShare()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "addShare";
    }
    return "index_index";
  }
  
  public String addShare()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        if ((this.shareinfo.getShareDate() == null) || (this.shareinfo.getShareDate().equals(""))) {
          this.shareinfo.setShareDate(DateUtil.DateTimeToStr(new Date()));
        }
        this.shareinfo.setUpCount(Integer.valueOf(0));
        this.shareinfo.setReplyCount(Integer.valueOf(0));
        this.shareinfo.setReward(Integer.valueOf(0));
        this.shareService.add(this.shareinfo);
        this.latestlottery = ((Latestlottery)this.latestlotteryService.findById(String.valueOf(this.shareinfo.getSproductId())));
        this.latestlottery.setStatus(Integer.valueOf(10));
        this.latestlottery.setShareStatus(Integer.valueOf(0));
        this.latestlottery.setShareId(this.shareinfo.getUid());
        this.latestlotteryService.add(this.latestlottery);
        return "successShare";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String toAddShareImage()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.shareimageList = this.shareService.getShareimage(this.id);
      return "toAddShareImage";
    }
    return "index_index";
  }
  
  public String addShareImage()
    throws Exception
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        String productImgPath = "/UserPost";
        long fileDateName = 0L;
        String biGImagePath = null;
        String smallImagePath = null;
        String listImgPath = null;
        if ((this.Filedata == null) || (this.Filedata.size() == 0)) {
          return null;
        }
        for (int i = 0; i < this.Filedata.size(); i++)
        {
          this.myFile = ((File)this.Filedata.get(i));
          if (this.myFile != null)
          {
            this.myFileFileName = ((String)this.FiledataFileName.get(i)).substring(((String)this.FiledataFileName.get(i)).lastIndexOf("."), ((String)this.FiledataFileName.get(i)).length());
            fileDateName = new Date().getTime();
            this.imageFileName = (fileDateName + this.myFileFileName);
            biGImagePath = ServletActionContext.getServletContext().getRealPath(productImgPath) + "/Big/";
            smallImagePath = ServletActionContext.getServletContext().getRealPath(productImgPath) + "/Small/";
            listImgPath = ServletActionContext.getServletContext().getRealPath(productImgPath) + "/220/";
            File bigImageFile = new File(biGImagePath + this.imageFileName);
            File smallImageFile = new File(smallImagePath + this.imageFileName);
            File listImageFile = new File(smallImagePath + this.imageFileName);
            copy(this.myFile, bigImageFile);
            copy(this.myFile, smallImageFile);
            copy(this.myFile, listImageFile);
            CutImages.equimultipleConvert(560, 420, bigImageFile, new File(biGImagePath + fileDateName + this.myFileFileName));
            CutImages.equimultipleConvert(220, 220, listImageFile, new File(listImgPath + fileDateName + this.myFileFileName));
            CutImages.equimultipleConvert(100, 100, smallImageFile, new File(smallImagePath + fileDateName + this.myFileFileName));
            this.shareimage = new Shareimage();
            this.shareimage.setShareInfoId(Integer.valueOf(Integer.parseInt(this.id)));
            this.shareimage.setImages(fileDateName + this.myFileFileName);
            this.shareService.addShareImage(this.shareimage);
          }
        }
        Struts2Utils.render("text/html", "<script>alert(\"上传成功！\");history.go(-2);</script>", new String[] { "encoding:UTF-8" });
        return null;
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String toUpdateShare()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.shareimageList = this.shareService.getShareimage(this.id);
      this.shareinfo = ((Shareinfo)this.shareService.findById(this.id));
      return "addShare";
    }
    return "index_index";
  }
  
  public String updateShare()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.shareService.add(this.shareinfo);
        this.latestlottery = ((Latestlottery)this.latestlotteryService.findById(String.valueOf(this.shareinfo.getSproductId())));
        this.latestlottery.setStatus(Integer.valueOf(10));
        if (this.shareinfo.getStatus().intValue() == 1)
        {
          this.latestlottery.setShareStatus(Integer.valueOf(1));
        }
        else if (this.shareinfo.getStatus().intValue() == 2)
        {
          this.latestlottery.setShareStatus(Integer.valueOf(2));
          this.user = ((User)this.userService.findById(this.latestlottery.getUserId()+""));
          Integer m = this.user.getCommissionPoints();
          m = Integer.valueOf(m.intValue() + 1000);
          this.user.setCommissionPoints(m);
          this.userService.add(this.user);
          this.commissionpoints = new Commissionpoints();
          this.commissionpoints.setDate(DateUtil.DateTimeToStr(new Date()));
          this.commissionpoints.setDetailed("晒单成功通过审核，奖励 1000 积分");
          this.commissionpoints.setPay("+1000");
          this.commissionpoints.setToUserId(Integer.valueOf(Integer.parseInt(this.userId)));
          this.commissionpointsService.add(this.commissionpoints);
        }
        this.latestlotteryService.add(this.latestlottery);
        return "successShare";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String userListAll()
  {
    if (this.pageNo == 0) {
      this.pageNo = 1;
    }
    if (this.typeName == null) {
      this.typeName = "";
    }
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      Pagination page = this.userService.adminUserList(this.typeId, this.keyword, this.typeName, this.pageNo, this.pageSize);
      this.userList = (List<User>)page.getList();
      
      this.resultCount = page.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/userListAll.action?typeName=" + this.typeName + "&pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "userList";
    }
    return "index_index";
  }
  
  public String toAddUser()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "toAddUser";
    }
    return "index_index";
  }
  
  public String toUpdateUser()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.user = ((User)this.userService.findById(this.id));
      



      return "toAddUser";
    }
    return "index_index";
  }
  
  public void doUpdateUser()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        this.userService.add(this.user);
        Struts2Utils.render("text/html", "<script>window.location.href=\"" + this.backUrl + "\";</script>", new String[] { "encoding:UTF-8" });
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public String doAddUser()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        if (StringUtil.isNotBlank(this.user.getMail()))
        {
          User u = this.userService.userByName(this.user.getMail());
          if (u != null)
          {
            Struts2Utils.render("text/html", "<script>alert(\"该邮箱已被注册！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
            return null;
          }
        }
        if (StringUtil.isNotBlank(this.user.getPhone()))
        {
          User u2 = this.userService.userByName(this.user.getPhone());
          if (u2 != null)
          {
            Struts2Utils.render("text/html", "<script>alert(\"该手机已被注册！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
            return null;
          }
        }
        if (this.user.getCommissionBalance() == null) {
          this.user.setCommissionBalance(Double.valueOf(0.0D));
        }
        if (this.user.getCommissionCount() == null) {
          this.user.setCommissionCount(Double.valueOf(0.0D));
        }
        if (this.user.getCommissionMention() == null) {
          this.user.setCommissionMention(Double.valueOf(0.0D));
        }
        if (this.user.getCommissionPoints() == null) {
          this.user.setCommissionPoints(Integer.valueOf(0));
        }
        if (this.user.getExperience() == null) {
          this.user.setExperience(Integer.valueOf(0));
        }
        if (this.user.getBalance() == null) {
          this.user.setBalance(Double.valueOf(0.0D));
        }
        String date = DateUtil.DateTimeToStr(new Date());
        this.user.setOldDate(date);
        this.user.setFaceImg("/Images/defaultUserFace.png");
        this.userService.add(this.user);
        return "addUserSuccess";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String toUserConfigure()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      return "toUserConfigure";
    }
    return "index_index";
  }
  
  public String sysInfo()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      
      return "sysInfo";
    }
    return "index_index";
  }
  
  public String setSysInfo()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      Properties properties = new Properties();
      try
      {
        OutputStream outputStream = new FileOutputStream(ServletActionContext.getServletContext().getRealPath("/WEB-INF/classes") + "/" + "config.properties");
        properties.setProperty("alipayKey", this.sysInfoBean.getAlipayKey());
        properties.setProperty("alipayMail", this.sysInfoBean.getAlipayMail());
        properties.setProperty("alipayPartner", this.sysInfoBean.getAlipayPartner());
        properties.setProperty("domain", this.sysInfoBean.getDomain());
        properties.setProperty("icp", this.sysInfoBean.getIcp());
        properties.setProperty("img", this.sysInfoBean.getImg());
        properties.setProperty("keyword", this.sysInfoBean.getKeyword());
        properties.setProperty("description", this.sysInfoBean.getDescription());
        properties.setProperty("mailName", this.sysInfoBean.getMailName());
        properties.setProperty("mailPwd", this.sysInfoBean.getMailPwd());
        properties.setProperty("saitName", this.sysInfoBean.getSaitName());
        properties.setProperty("www", this.sysInfoBean.getSaitUrl());
        properties.setProperty("skin", this.sysInfoBean.getSkin());
        properties.setProperty("tenpayKey", this.sysInfoBean.getAlipayKey());
        properties.setProperty("tenpayPartner", this.sysInfoBean.getTenpayPartner());
        properties.setProperty("upfilefolder", "productImg/");
        properties.store(outputStream, "author: service@1ypg.com");
        outputStream.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      return "main";
    }
    return "index_index";
  }
  
  public void restartTomcat()
    throws IOException, InterruptedException
  {
    String windowsPath = "D:\\tomcat\\bin";
    String linuxPath = "/usr/bin/ye";
    List<String> cmd = new ArrayList();
    if (OS.isWindows()) {
      cmd.add(windowsPath);
    } else if (OS.isLinux()) {
      cmd.add(linuxPath);
    }
    ProcessBuilder pb = new ProcessBuilder(new String[0]);
    pb.command(cmd);
    pb.redirectErrorStream(true);
    Process process = pb.start();
    int w = process.waitFor();
    System.out.println("status:" + w);
  }
  
  public String orderList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.pageNo == 0) {
        this.pageNo = 1;
      }
      Pagination page = this.consumetableService.orderList(this.userName, this.pageNo, this.pageSize);
      List<Consumetable> list = (List<Consumetable>)page.getList();
      this.orderBeanList = new ArrayList();
      for (int i = 0; i < list.size(); i++)
      {
        OrderBean orderBean = new OrderBean();
        Consumetable consumetable = (Consumetable)list.get(i);
        orderBean.setOutTradeNo(consumetable.getOutTradeNo());
        orderBean.setBuyCount(consumetable.getBuyCount());
        orderBean.setDate(consumetable.getDate());
        orderBean.setMoney(consumetable.getMoney().doubleValue());
        if (consumetable.getInterfaceType().equals("tenPay")) {
          orderBean.setPayType("财付通");
        } else if (consumetable.getInterfaceType().equals("aliPay")) {
          orderBean.setPayType("支付宝");
        } else if (consumetable.getInterfaceType().equals("yeePay")) {
          orderBean.setPayType("易宝支付");
        } else if (consumetable.getInterfaceType().equals("积分抵扣")) {
          orderBean.setPayType("积分抵扣");
        } else if (consumetable.getInterfaceType().equals("积分+余额")) {
          orderBean.setPayType("积分+余额");
        } else {
          orderBean.setPayType("余额支付");
        }
        orderBean.setUserId(consumetable.getUserId());
        this.orderBeanList.add(orderBean);
      }
      this.resultCount = page.getResultCount();
      if (StringUtil.isNotBlank(this.userName)) {
        this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/orderList.action?userName=" + this.userName + "&pageNo=");
      } else {
        this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/orderList.action?pageNo=");
      }
      this.pageString = this.pageString.replace(".html", "");
      return "orderList";
    }
    return "index_index";
  }
  
  public String orderInfo()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.pageNo == 0) {
        this.pageNo = 1;
      }
      Pagination page = this.consumerdetailService.orderInfo(this.id, this.pageNo, this.pageSize);
      List<Consumerdetail> list = (List<Consumerdetail>)page.getList();
      this.orderBeanList = new ArrayList();
      for (Consumerdetail consumerdetail : list)
      {
        OrderBean orderBean = new OrderBean();
        orderBean.setBuyCount(consumerdetail.getBuyCount());
        orderBean.setMoney(consumerdetail.getBuyMoney().doubleValue());
        orderBean.setProductId(consumerdetail.getProductId());
        orderBean.setProductName(consumerdetail.getProductName());
        orderBean.setProductPeriod(consumerdetail.getProductPeriod()+"");
        this.orderBeanList.add(orderBean);
      }
      this.resultCount = page.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/orderInfo.action?pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "orderInfo";
    }
    return "index_index";
  }
  
  public String cardList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.pageNo == 0) {
        this.pageNo = 1;
      }
      Pagination page = this.cardpasswordService.cardRechargeList(this.pageNo, this.pageSize);
      this.cardpasswordList = (List<Cardpassword>)page.getList();
      this.resultCount = page.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/cardList.action?pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "cardList";
    }
    return "index_index";
  }
  
  public void indexTop()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin")) {
        try
        {
          Recommend recommend = new Recommend();
          recommend.setDate(DateUtil.DateToStr(new Date()));
          recommend.setRecommendId(Integer.valueOf(1));
          recommend.setSpellbuyProductId(Integer.valueOf(Integer.parseInt(this.id)));
          this.recommendService.add(recommend);
          Struts2Utils.renderText("success", new String[0]);
        }
        catch (Exception e)
        {
          e.printStackTrace();
          Struts2Utils.renderText("error", new String[0]);
        }
      } else {
        Struts2Utils.renderText("test", new String[0]);
      }
    }
  }
  
  public String toAddCard()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "addCard";
    }
    return "index_index";
  }
  
  public void doCard()
    throws InterruptedException
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        if ((StringUtil.isNotBlank(this.id)) && (StringUtil.isNotBlank(this.pwd)))
        {
          List<Cardpassword> cardpasswordList = new ArrayList();
          for (int i = 0; i < Integer.parseInt(this.id); i++) {
            try
            {
              Thread.sleep(100L);
              String s = UUID.randomUUID().toString().toUpperCase();
              s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
              String md5s = MD5Util.encode(s);
              String key = s + md5s;
              Cardpassword cardpassword = new Cardpassword();
              cardpassword.setCardPwd(md5s);
              cardpassword.setDate(DateUtil.DateTimeToStr(new Date()));
              cardpassword.setMoney(Double.valueOf(Double.parseDouble(this.pwd)));
              cardpassword.setRandomNo(s);
              this.cardpasswordService.add(cardpassword);
              cardpasswordList.add(cardpassword);
            }
            catch (Exception e)
            {
              Struts2Utils.renderText("error", new String[0]);
              e.printStackTrace();
            }
          }
          Struts2Utils.renderText("success", new String[0]);
        }
      }
      else {
        Struts2Utils.renderText("test", new String[0]);
      }
    }
  }
  
  public String applymentionList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.pageNo == 0) {
        this.pageNo = 1;
      }
      Pagination page = this.applymentionService.getApplymentionList(null, null, null, this.pageNo, this.pageSize);
      this.applymentionList = (List<Applymention>)page.getList();
      this.resultCount = this.applymentionService.getApplymentionListByCount(null, null, null).intValue();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/applymentionList.action?pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "applymentionList";
    }
    return "index_index";
  }
  
  public String orderdetailaddressList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (this.pageNo == 0) {
        this.pageNo = 1;
      }
      Pagination page = this.orderdetailaddressService.orderdetailaddressList(this.pageNo, this.pageSize);
      this.orderdetailaddressList = (List<Orderdetailaddress>)page.getList();
      this.resultCount = page.getResultCount();
      this.pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(this.resultCount), Integer.valueOf(this.pageSize), Integer.valueOf(this.pageNo), Integer.valueOf(2), Integer.valueOf(5), "/admin_back/orderdetailaddressList.action?pageNo=");
      this.pageString = this.pageString.replace(".html", "");
      return "orderdetailaddressList";
    }
    return "index_index";
  }
  
  public String lineUpdate()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "lineUpdate";
    }
    return "index_index";
  }
  
  public String qqSetInfo()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      return "qqSetInfo";
    }
    return "index_index";
  }
  
  public String qqGyjjNumber()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      return "gyjjNumber";
    }
    return "index_index";
  }
  
  public String toSeoSet()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      return "seoSet";
    }
    return "index_index";
  }
  
  public String toBasicSet()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      return "toBasicSet";
    }
    return "index_index";
  }
  
  public String toMailSet()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      return "toMailSet";
    }
    return "index_index";
  }
  
  public void doTestMail()
    throws InterruptedException
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        if ((StringUtil.isBlank(ApplicationListenerImpl.sysConfigureJson.getMailName())) || (StringUtil.isBlank(ApplicationListenerImpl.sysConfigureJson.getMailPwd()))) {
          Struts2Utils.render("text/html", "<script>alert(\"请先配置系统邮箱设置！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
        }
        String html = this.message;
        boolean flag = false;
        if (StringUtil.isNotBlank(this.userName))
        {
          String[] mailTo = this.userName.split(",");
          for (String string : mailTo)
          {
            try
            {
              flag = EmailUtil.sendEmail(ApplicationListenerImpl.sysConfigureJson.getMailName(), ApplicationListenerImpl.sysConfigureJson.getMailPwd(), string, this.tId, html);
            }
            catch (Exception e)
            {
              e.printStackTrace();
              Struts2Utils.render("text/html", "<script>alert(\"" + string + "发送失败！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
            }
            Thread.sleep(1000L);
          }
        }
        if (flag) {
          Struts2Utils.render("text/html", "<script>alert(\"发送成功！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
        }
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public String toMessageSet()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      this.message = Sampler.getMesBalance();
      return "toMessageSet";
    }
    return "index_index";
  }
  
  public void testMessage()
    throws Exception
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        List<SendResultBean> sendList = Sampler.sendOnce(this.id, this.message);
        if (sendList != null) {
          for (SendResultBean t : sendList)
          {
            if (t.getResult() < 1)
            {
              Struts2Utils.renderText("发送失败，请联系管理员！", new String[0]);
              return;
            }
            Struts2Utils.renderText("发送成功!总数：" + t.getTotal(), new String[0]);
          }
        }
      }
      else
      {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
  }
  
  public String toPaySet()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      return "toPaySet";
    }
    return "index_index";
  }
  
  public String payInfo()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.sysConfigure = ApplicationListenerImpl.sysConfigureJson;
      return "payInfo";
    }
    return "index_index";
  }
  
  public String toAddAdmin()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "toAddAdmin";
    }
    return "index_index";
  }
  
  public String doAddAdmin()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin"))
      {
        User u = new User();
        u.setUserName(this.user.getUserName());
        u.setPhone(this.user.getUserName());
        u.setMail(this.user.getUserName());
        u.setUserPwd(this.user.getUserPwd());
        u.setUserType(this.user.getUserType());
        this.userService.add(u);
        return "addAdminSuccess";
      }
      Struts2Utils.render("text/html", "<script>alert(\"测试用户无权修改！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      return null;
    }
    return "index_index";
  }
  
  public String toAdminList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.userList = this.userService.query("from User u where 1=1 and u.userType = 1");
      return "toAdminList";
    }
    return "index_index";
  }
  
  public String updateAdminPwd()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.userName = this.id;
      return "updateAdminPwd";
    }
    return "index_index";
  }
  
  public void doUpdateAdminPwd()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin")) {
        try
        {
          this.user = ((User)Struts2Utils.getSession().getAttribute("admin"));
          this.user.setUserPwd(this.pwd);
          this.userService.add(this.user);
          Struts2Utils.renderText("success", new String[0]);
        }
        catch (Exception e)
        {
          Struts2Utils.renderText("error", new String[0]);
          e.printStackTrace();
        }
      } else {
        Struts2Utils.renderText("test", new String[0]);
      }
    }
  }
  
  public String doSeoSet()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      if (((User)Struts2Utils.getSession().getAttribute("admin")).getUserName().equals("admin")) {
        try
        {
          SysConfigure sysConfigure2 = (SysConfigure)this.sysConfigureService.findById("1");
          String productImgPath = "/Images";
          if (this.myFile != null)
          {
            this.myFileFileName = this.myFileFileName.substring(this.myFileFileName.lastIndexOf("."), this.myFileFileName.length());
            this.imageFileName = ("new-logo" + this.myFileFileName);
            File imageFile = new File(UploadImagesUtil.getFolder(new StringBuilder(String.valueOf(ServletActionContext.getServletContext().getRealPath(productImgPath))).append("/").toString(), "") + "/" + this.imageFileName);
            copy(this.myFile, imageFile);
            this.imageFileName = this.imageFileName.toLowerCase();
            sysConfigure2.setSaitLogo(productImgPath + "/" + this.imageFileName);
          }
          if (this.sysConfigure.getAlipayKey() != null) {
            sysConfigure2.setAlipayKey(this.sysConfigure.getAlipayKey());
          }
          if (this.sysConfigure.getAlipayMail() != null) {
            sysConfigure2.setAlipayMail(this.sysConfigure.getAlipayMail());
          }
          if (this.sysConfigure.getAlipayPartner() != null) {
            sysConfigure2.setAlipayPartner(this.sysConfigure.getAlipayPartner());
          }
          if (this.sysConfigure.getAlipayStatus() != null) {
            sysConfigure2.setAlipayStatus(this.sysConfigure.getAlipayStatus());
          }
          if (this.sysConfigure.getAliPayUser() != null) {
            sysConfigure2.setAliPayUser(this.sysConfigure.getAliPayUser());
          }
          if (this.sysConfigure.getAliPayUserStatus() != null) {
            sysConfigure2.setAliPayUserStatus(this.sysConfigure.getAliPayUserStatus());
          }
          if (this.sysConfigure.getAuthorization() != null) {
            sysConfigure2.setAuthorization(this.sysConfigure.getAuthorization());
          }
          if (this.sysConfigure.getBillKey() != null) {
            sysConfigure2.setBillKey(this.sysConfigure.getBillKey());
          }
          if (this.sysConfigure.getBillPartner() != null) {
            sysConfigure2.setBillPartner(this.sysConfigure.getBillPartner());
          }
          if (this.sysConfigure.getBillStatus() != null) {
            sysConfigure2.setBillStatus(this.sysConfigure.getBillStatus());
          }
          if (this.sysConfigure.getBuyProduct() != null) {
            sysConfigure2.setBuyProduct(this.sysConfigure.getBuyProduct());
          }
          if (this.sysConfigure.getChinabankKey() != null) {
            sysConfigure2.setChinabankKey(this.sysConfigure.getChinabankKey());
          }
          if (this.sysConfigure.getChinabankPartner() != null) {
            sysConfigure2.setChinabankPartner(this.sysConfigure.getChinabankPartner());
          }
          if (this.sysConfigure.getChinabankStatus() != null) {
            sysConfigure2.setChinabankStatus(this.sysConfigure.getChinabankStatus());
          }
          if (this.sysConfigure.getCommission() != null) {
            sysConfigure2.setCommission(this.sysConfigure.getCommission());
          }
          if (this.sysConfigure.getDescription() != null) {
            sysConfigure2.setDescription(this.sysConfigure.getDescription());
          }
          if (this.sysConfigure.getDomain() != null) {
            sysConfigure2.setDomain(this.sysConfigure.getDomain());
          }
          if (this.sysConfigure.getGyjjNumber() != null) {
            sysConfigure2.setGyjjNumber(this.sysConfigure.getGyjjNumber());
          }
          if (this.sysConfigure.getIcp() != null) {
            sysConfigure2.setIcp(this.sysConfigure.getIcp());
          }
          if (this.sysConfigure.getId() != null) {
            sysConfigure2.setId(this.sysConfigure.getId());
          }
          if (this.sysConfigure.getImgUrl() != null) {
            sysConfigure2.setImgUrl(this.sysConfigure.getImgUrl());
          }
          if (this.sysConfigure.getInvite() != null) {
            sysConfigure2.setInvite(this.sysConfigure.getInvite());
          }
          if (this.sysConfigure.getKeyword() != null) {
            sysConfigure2.setKeyword(this.sysConfigure.getKeyword());
          }
          if (this.sysConfigure.getMailName() != null) {
            sysConfigure2.setMailName(this.sysConfigure.getMailName());
          }
          if (this.sysConfigure.getMailPwd() != null) {
            sysConfigure2.setMailPwd(this.sysConfigure.getMailPwd());
          }
          if (this.sysConfigure.getMailsmtp() != null) {
            sysConfigure2.setMailsmtp(this.sysConfigure.getMailsmtp());
          }
          if (this.sysConfigure.getMessageChannel() != null) {
            sysConfigure2.setMessageChannel(this.sysConfigure.getMessageChannel());
          }
          if (this.sysConfigure.getMessageKey() != null) {
            sysConfigure2.setMessageKey(this.sysConfigure.getMessageKey());
          }
          if (this.sysConfigure.getMessagePartner() != null) {
            sysConfigure2.setMessagePartner(this.sysConfigure.getMessagePartner());
          }
          if (this.sysConfigure.getMessageSign() != null) {
            sysConfigure2.setMessageSign(this.sysConfigure.getMessageSign());
          }
          if (this.sysConfigure.getQqAppId() != null) {
            sysConfigure2.setQqAppId(this.sysConfigure.getQqAppId());
          }
          if (this.sysConfigure.getQqAppKey() != null) {
            sysConfigure2.setQqAppKey(this.sysConfigure.getQqAppKey());
          }
          if (this.sysConfigure.getQqAppStatus() != null) {
            sysConfigure2.setQqAppStatus(this.sysConfigure.getQqAppStatus());
          }
          if (this.sysConfigure.getRecBalance() != null) {
            sysConfigure2.setRecBalance(this.sysConfigure.getRecBalance());
          }
          if (this.sysConfigure.getRecMoney() != null) {
            sysConfigure2.setRecMoney(this.sysConfigure.getRecMoney());
          }
          if (this.sysConfigure.getRegBalance() != null) {
            sysConfigure2.setRegBalance(this.sysConfigure.getRegBalance());
          }
          if (this.sysConfigure.getSaitLogo() != null) {
            sysConfigure2.setSaitLogo(this.sysConfigure.getSaitLogo());
          }
          if (this.sysConfigure.getSaitName() != null) {
            sysConfigure2.setSaitName(this.sysConfigure.getSaitName());
          }
          if (this.sysConfigure.getSaitTitle() != null) {
            sysConfigure2.setSaitTitle(this.sysConfigure.getSaitTitle());
          }
          if (this.sysConfigure.getServiceQq() != null) {
            sysConfigure2.setServiceQq(this.sysConfigure.getServiceQq());
          }
          if (this.sysConfigure.getServiceTel() != null) {
            sysConfigure2.setServiceTel(this.sysConfigure.getServiceTel());
          }
          if (this.sysConfigure.getShortName() != null) {
            sysConfigure2.setShortName(this.sysConfigure.getShortName());
          }
          if (this.sysConfigure.getSkinUrl() != null) {
            sysConfigure2.setSkinUrl(this.sysConfigure.getSkinUrl());
          }
          if (this.sysConfigure.getTenpayKey() != null) {
            sysConfigure2.setTenpayKey(this.sysConfigure.getTenpayKey());
          }
          if (this.sysConfigure.getTenpayPartner() != null) {
            sysConfigure2.setTenpayPartner(this.sysConfigure.getTenpayPartner());
          }
          if (this.sysConfigure.getTenpayStatus() != null) {
            sysConfigure2.setTenpayStatus(this.sysConfigure.getTenpayStatus());
          }
          if (this.sysConfigure.getTenPayUser() != null) {
            sysConfigure2.setTenPayUser(this.sysConfigure.getTenPayUser());
          }
          if (this.sysConfigure.getTenPayUserStatus() != null) {
            sysConfigure2.setTenPayUserStatus(this.sysConfigure.getTenPayUserStatus());
          }
          if (this.sysConfigure.getUnionpayKey() != null) {
            sysConfigure2.setUnionpayKey(this.sysConfigure.getUnionpayKey());
          }
          if (this.sysConfigure.getUnionpayPartner() != null) {
            sysConfigure2.setUnionpayPartner(this.sysConfigure.getUnionpayPartner());
          }
          if (this.sysConfigure.getUnionpayStatus() != null) {
            sysConfigure2.setUnionpayStatus(this.sysConfigure.getUnionpayStatus());
          }
          if (this.sysConfigure.getUserData() != null) {
            sysConfigure2.setUserData(this.sysConfigure.getUserData());
          }
          if (this.sysConfigure.getWeixinAppId() != null) {
            sysConfigure2.setWeixinAppId(this.sysConfigure.getWeixinAppId());
          }
          if (this.sysConfigure.getWeixinAppKey() != null) {
            sysConfigure2.setWeixinAppKey(this.sysConfigure.getWeixinAppKey());
          }
          if (this.sysConfigure.getWeixinAppSecret() != null) {
            sysConfigure2.setWeixinAppSecret(this.sysConfigure.getWeixinAppSecret());
          }
          if (this.sysConfigure.getWeixinPayKey() != null) {
            sysConfigure2.setWeixinPayKey(this.sysConfigure.getWeixinPayKey());
          }
          if (this.sysConfigure.getWeixinPayPartner() != null) {
            sysConfigure2.setWeixinPayPartner(this.sysConfigure.getWeixinPayPartner());
          }
          if (this.sysConfigure.getWeixinStatus() != null) {
            sysConfigure2.setWeixinStatus(this.sysConfigure.getWeixinStatus());
          }
          if (this.sysConfigure.getWwwUrl() != null) {
            sysConfigure2.setWwwUrl(this.sysConfigure.getWwwUrl());
          }
          if (this.sysConfigure.getYeepayKey() != null) {
            sysConfigure2.setYeepayKey(this.sysConfigure.getYeepayKey());
          }
          if (this.sysConfigure.getYeepayPartner() != null) {
            sysConfigure2.setYeepayPartner(this.sysConfigure.getYeepayPartner());
          }
          if (this.sysConfigure.getYeepayStatus() != null) {
            sysConfigure2.setYeepayStatus(this.sysConfigure.getYeepayStatus());
          }
          sysConfigure2.setAuthorization(Struts2Utils.getLocalIP());
          this.sysConfigureService.add(sysConfigure2);
          ApplicationListenerImpl.sysConfigureJson = (SysConfigure)this.sysConfigureService.findById("1");
          Struts2Utils.render("text/html", "<script>alert(\"操作成功！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
        }
        catch (Exception e)
        {
          Struts2Utils.render("text/html", "<script>alert(\"操作失败，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
          e.printStackTrace();
        }
      } else {
        Struts2Utils.render("text/html", "<script>alert(\"测试用户无权配置系统！，请联系管理员！\");history.go(-1);</script>", new String[] { "encoding:UTF-8" });
      }
    }
    return null;
  }
  
  public String rechargeAllList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.startDate = DateUtil.DateToStr(DateUtil.addMonth(new Date(), -1));
      this.endDate = DateUtil.DateToStr(new Date());
      this.startDate += " 00:00:00";
      this.endDate += " 23:59:59";
      this.resultCount = this.consumetableService.userByConsumetableByDeltaByCount(this.userId, this.startDate, this.endDate).intValue();
      return "rechargeAllList";
    }
    return "index_index";
  }
  
  public String consumeAllList()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null)
    {
      this.startDate = DateUtil.DateToStr(DateUtil.addMonth(new Date(), -1));
      this.endDate = DateUtil.DateToStr(new Date());
      this.startDate += " 00:00:00";
      this.endDate += " 23:59:59";
      this.resultCount = this.consumetableService.userByConsumetableByCount(this.userId, this.startDate, this.endDate).intValue();
      return "consumeAllList";
    }
    return "index_index";
  }
  
  public String toSaitMap()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "toSaitMap";
    }
    return "index_index";
  }
  
  public String toSaitPost()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "toSaitPost";
    }
    return "index_index";
  }
  
  public String toSaitCount()
  {
    if (Struts2Utils.getSession().getAttribute("admin") != null) {
      return "toSaitCount";
    }
    return "index_index";
  }
  
  public String timeOut()
  {
    return "timeout";
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getPwd()
  {
    return this.pwd;
  }
  
  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
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
  
  public Productimage getProductimage()
  {
    return this.productimage;
  }
  
  public void setProductimage(Productimage productimage)
  {
    this.productimage = productimage;
  }
  
  public Producttype getProducttype()
  {
    return this.producttype;
  }
  
  public void setProducttype(Producttype producttype)
  {
    this.producttype = producttype;
  }
  
  public List<Producttype> getProductTypeList()
  {
    return this.productTypeList;
  }
  
  public void setProductTypeList(List<Producttype> productTypeList)
  {
    this.productTypeList = productTypeList;
  }
  
  public File getMyFile()
  {
    return this.myFile;
  }
  
  public void setMyFile(File myFile)
  {
    this.myFile = myFile;
  }
  
  public String getMyFileFileName()
  {
    return this.myFileFileName;
  }
  
  public void setMyFileFileName(String myFileFileName)
  {
    this.myFileFileName = myFileFileName;
  }
  
  public String getMyFileContentType()
  {
    return this.myFileContentType;
  }
  
  public void setMyFileContentType(String myFileContentType)
  {
    this.myFileContentType = myFileContentType;
  }
  
  public String getImageFileName()
  {
    return this.imageFileName;
  }
  
  public void setImageFileName(String imageFileName)
  {
    this.imageFileName = imageFileName;
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
  
  public String getKeyword()
  {
    return this.keyword;
  }
  
  public void setKeyword(String keyword)
  {
    this.keyword = keyword;
  }
  
  public List<Product> getProductList()
  {
    return this.productList;
  }
  
  public void setProductList(List<Product> productList)
  {
    this.productList = productList;
  }
  
  public String getPageString()
  {
    return this.pageString;
  }
  
  public void setPageString(String pageString)
  {
    this.pageString = pageString;
  }
  
  public String getPages()
  {
    return this.pages;
  }
  
  public void setPages(String pages)
  {
    this.pages = pages;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public List<File> getFiledata()
  {
    return this.Filedata;
  }
  
  public void setFiledata(List<File> filedata)
  {
    this.Filedata = filedata;
  }
  
  public List<String> getFiledataFileName()
  {
    return this.FiledataFileName;
  }
  
  public void setFiledataFileName(List<String> filedataFileName)
  {
    this.FiledataFileName = filedataFileName;
  }
  
  public List<String> getImageContentType()
  {
    return this.imageContentType;
  }
  
  public void setImageContentType(List<String> imageContentType)
  {
    this.imageContentType = imageContentType;
  }
  
  public List<Productimage> getProductimageList()
  {
    return this.productimageList;
  }
  
  public void setProductimageList(List<Productimage> productimageList)
  {
    this.productimageList = productimageList;
  }
  
  public String getTypeId()
  {
    return this.typeId;
  }
  
  public void setTypeId(String typeId)
  {
    this.typeId = typeId;
  }
  
  public String getTypeName()
  {
    return this.typeName;
  }
  
  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
  }
  
  public List<ProductJSON> getProductJSONList()
  {
    return this.productJSONList;
  }
  
  public void setProductJSONList(List<ProductJSON> productJSONList)
  {
    this.productJSONList = productJSONList;
  }
  
  public ProductJSON getProductJSON()
  {
    return this.productJSON;
  }
  
  public void setProductJSON(ProductJSON productJSON)
  {
    this.productJSON = productJSON;
  }
  
  public String getChannelUrl()
  {
    return this.channelUrl;
  }
  
  public void setChannelUrl(String channelUrl)
  {
    this.channelUrl = channelUrl;
  }
  
  public String getProductType()
  {
    return this.productType;
  }
  
  public void setProductType(String productType)
  {
    this.productType = productType;
  }
  
  public String getBackUrl()
  {
    return this.backUrl;
  }
  
  public void setBackUrl(String backUrl)
  {
    this.backUrl = backUrl;
  }
  
  public News getNews()
  {
    return this.news;
  }
  
  public void setNews(News news)
  {
    this.news = news;
  }
  
  public List<News> getNewsList()
  {
    return this.newsList;
  }
  
  public void setNewsList(List<News> newsList)
  {
    this.newsList = newsList;
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
  
  public Latestlottery getLatestlottery()
  {
    return this.latestlottery;
  }
  
  public void setLatestlottery(Latestlottery latestlottery)
  {
    this.latestlottery = latestlottery;
  }
  
  public List<User> getUserList()
  {
    return this.userList;
  }
  
  public void setUserList(List<User> userList)
  {
    this.userList = userList;
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
  
  public List<Latestlottery> getLatestlotteryList()
  {
    return this.latestlotteryList;
  }
  
  public void setLatestlotteryList(List<Latestlottery> latestlotteryList)
  {
    this.latestlotteryList = latestlotteryList;
  }
  
  public List<Shareimage> getShareimageList()
  {
    return this.shareimageList;
  }
  
  public void setShareimageList(List<Shareimage> shareimageList)
  {
    this.shareimageList = shareimageList;
  }
  
  public String getAnnouncedTime()
  {
    return this.announcedTime;
  }
  
  public void setAnnouncedTime(String announcedTime)
  {
    this.announcedTime = announcedTime;
  }
  
  public SysInfoBean getSysInfoBean()
  {
    return this.sysInfoBean;
  }
  
  public void setSysInfoBean(SysInfoBean sysInfoBean)
  {
    this.sysInfoBean = sysInfoBean;
  }
  
  public List<OrderBean> getOrderBeanList()
  {
    return this.orderBeanList;
  }
  
  public void setOrderBeanList(List<OrderBean> orderBeanList)
  {
    this.orderBeanList = orderBeanList;
  }
  
  public List<Cardpassword> getCardpasswordList()
  {
    return this.cardpasswordList;
  }
  
  public void setCardpasswordList(List<Cardpassword> cardpasswordList)
  {
    this.cardpasswordList = cardpasswordList;
  }
  
  public List<Applymention> getApplymentionList()
  {
    return this.applymentionList;
  }
  
  public void setApplymentionList(List<Applymention> applymentionList)
  {
    this.applymentionList = applymentionList;
  }
  
  public List<Orderdetailaddress> getOrderdetailaddressList()
  {
    return this.orderdetailaddressList;
  }
  
  public void setOrderdetailaddressList(List<Orderdetailaddress> orderdetailaddressList)
  {
    this.orderdetailaddressList = orderdetailaddressList;
  }
  
  public SysConfigure getSysConfigure()
  {
    return this.sysConfigure;
  }
  
  public void setSysConfigure(SysConfigure sysConfigure)
  {
    this.sysConfigure = sysConfigure;
  }
  
  public String getStartDate()
  {
    return this.startDate;
  }
  
  public void setStartDate(String startDate)
  {
    this.startDate = startDate;
  }
  
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(String endDate)
  {
    this.endDate = endDate;
  }
  
  public List<IndexImg> getIndexImgList()
  {
    return this.indexImgList;
  }
  
  public void setIndexImgList(List<IndexImg> indexImgList)
  {
    this.indexImgList = indexImgList;
  }
  
  public IndexImg getIndexImg()
  {
    return this.indexImg;
  }
  
  public void setIndexImg(IndexImg indexImg)
  {
    this.indexImg = indexImg;
  }
  
  public List<Producttype> getProductBrandList()
  {
    return this.productBrandList;
  }
  
  public void setProductBrandList(List<Producttype> productBrandList)
  {
    this.productBrandList = productBrandList;
  }
  
  public String getTId()
  {
    return this.tId;
  }
  
  public void setTId(String id)
  {
    this.tId = id;
  }
  
  public List<Suggestion> getSuggestionList()
  {
    return this.suggestionList;
  }
  
  public void setSuggestionList(List<Suggestion> suggestionList)
  {
    this.suggestionList = suggestionList;
  }
  
  public List<Orderdetail> getOrderdetailList()
  {
    return this.orderdetailList;
  }
  
  public void setOrderdetailList(List<Orderdetail> orderdetailList)
  {
    this.orderdetailList = orderdetailList;
  }
  
  public Orderdetailaddress getOrderdetailaddress()
  {
    return this.orderdetailaddress;
  }
  
  public void setOrderdetailaddress(Orderdetailaddress orderdetailaddress)
  {
    this.orderdetailaddress = orderdetailaddress;
  }
  
  public List<SProvince> getSProvinceList()
  {
    return this.sProvinceList;
  }
  
  public void setSProvinceList(List<SProvince> provinceList)
  {
    this.sProvinceList = provinceList;
  }
  
  public List<SCity> getSCityList()
  {
    return this.sCityList;
  }
  
  public void setSCityList(List<SCity> cityList)
  {
    this.sCityList = cityList;
  }
  
  public List<SDistrict> getSDistrictList()
  {
    return this.sDistrictList;
  }
  
  public void setSDistrictList(List<SDistrict> districtList)
  {
    this.sDistrictList = districtList;
  }
  
  public Qqgroup getQqgroup()
  {
    return this.qqgroup;
  }
  
  public void setQqgroup(Qqgroup qqgroup)
  {
    this.qqgroup = qqgroup;
  }
  
  public List<Qqgroup> getQqgroupList()
  {
    return this.qqgroupList;
  }
  
  public void setQqgroupList(List<Qqgroup> qqgroupList)
  {
    this.qqgroupList = qqgroupList;
  }
  
  public String getPId()
  {
    return this.pId;
  }
  
  public void setPId(String id)
  {
    this.pId = id;
  }
  
  public String getCId()
  {
    return this.cId;
  }
  
  public void setCId(String id)
  {
    this.cId = id;
  }
  
  public String getDId()
  {
    return this.dId;
  }
  
  public void setDId(String id)
  {
    this.dId = id;
  }
  
  public Commissionpoints getCommissionpoints()
  {
    return this.commissionpoints;
  }
  
  public void setCommissionpoints(Commissionpoints commissionpoints)
  {
    this.commissionpoints = commissionpoints;
  }
}
