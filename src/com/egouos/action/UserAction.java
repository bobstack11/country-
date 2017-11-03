package com.egouos.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.egouos.dao.Pagination;
import com.egouos.pojo.Applymention;
import com.egouos.pojo.BuyHistoryJSON;
import com.egouos.pojo.Cardpassword;
import com.egouos.pojo.Commissionpoints;
import com.egouos.pojo.Commissionquery;
import com.egouos.pojo.CommissionqueryJSON;
import com.egouos.pojo.Consumetable;
import com.egouos.pojo.Latestlottery;
import com.egouos.pojo.News;
import com.egouos.pojo.OrderListJSON;
import com.egouos.pojo.Orderdetail;
import com.egouos.pojo.Orderdetailaddress;
import com.egouos.pojo.Product;
import com.egouos.pojo.ProductJSON;
import com.egouos.pojo.RandomNumberJSON;
import com.egouos.pojo.Randomnumber;
import com.egouos.pojo.SCity;
import com.egouos.pojo.SDistrict;
import com.egouos.pojo.SProvince;
import com.egouos.pojo.ShareJSON;
import com.egouos.pojo.Shareimage;
import com.egouos.pojo.Shareinfo;
import com.egouos.pojo.Spellbuyproduct;
import com.egouos.pojo.Spellbuyrecord;
import com.egouos.pojo.SysConfigure;
import com.egouos.pojo.User;
import com.egouos.pojo.Userbyaddress;
import com.egouos.proto.http.Proxy;
import com.egouos.service.ApplymentionService;
import com.egouos.service.CardpasswordService;
import com.egouos.service.CommissionpointsService;
import com.egouos.service.CommissionqueryService;
import com.egouos.service.ConsumetableService;
import com.egouos.service.LatestlotteryService;
import com.egouos.service.NewsService;
import com.egouos.service.OrderdetailService;
import com.egouos.service.RegionService;
import com.egouos.service.ShareService;
import com.egouos.service.SpellbuyproductService;
import com.egouos.service.SpellbuyrecordService;
import com.egouos.service.UserService;
import com.egouos.sms.SmsSenderFactory;
import com.egouos.util.AliyunOcsSampleHelp;
import com.egouos.util.ApplicationListenerImpl;
import com.egouos.util.Base64;
import com.egouos.util.ConfigUtil;
import com.egouos.util.CookieUtil;
import com.egouos.util.CutImages;
import com.egouos.util.DateUtil;
import com.egouos.util.EmailUtil;
import com.egouos.util.HTMLInputFilter;
import com.egouos.util.MD5Util;
import com.egouos.util.StringUtil;
import com.egouos.util.Struts2Utils;
import com.egouos.util.UserNameUtil;
import com.egouos.weixin.config.Sha1Util;
import com.mteams.commons.encrypt.digest.MessageDigestUtils;
import com.opensymphony.xwork2.ActionSupport;

@Component("UserAction")
public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 6146740235643445087L;

	private SysConfigure conf = ApplicationListenerImpl.sysConfigureJson;

	@Autowired
	private UserService userService;
	@Autowired
	private SpellbuyproductService spellbuyproductService;
	@Autowired
	private SpellbuyrecordService spellbuyrecordService;
	@Autowired
	private LatestlotteryService latestlotteryService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private ConsumetableService consumetableService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private CommissionqueryService commissionqueryService;
	@Autowired
	private CommissionpointsService commissionpointsService;
	@Autowired
	private ApplymentionService applymentionService;
	@Autowired
	private CardpasswordService cardpasswordService;
	@Autowired
	private OrderdetailService orderdetailService;
	@Autowired
	Proxy httproxy;

	private String forward;
	private List<ProductJSON> productList;
	private List<ProductJSON> newDateList;
	private ProductJSON productJSON;
	private BuyHistoryJSON buyHistoryJSON;
	private OrderListJSON orderListJSON;
	private ShareJSON shareJSON;
	private List<BuyHistoryJSON> buyHistoryJSONList;
	private List<OrderListJSON> orderListJSONList;
	private List<RandomNumberJSON> randomNumberJSONList;
	private RandomNumberJSON randomNumberJSON;
	private List<ShareJSON> shareJSONList;
	private List<Userbyaddress> userbyaddressList;
	private List<News> newsList;
	private List<SProvince> sProvinceList;
	private List<SCity> sCityList;
	private List<SDistrict> sDistrictList;
	private List<User> userList;
	private List<Commissionquery> commissionqueryList;
	private List<Commissionpoints> commissionpointsList;
	private List<Applymention> applymentionList;
	private List<CommissionqueryJSON> commissionqueryJSONList;
	private List<Orderdetail> orderdetailList;
	private Orderdetail orderdetail;
	private Orderdetailaddress orderdetailaddress;
	private CommissionqueryJSON commissionqueryJSON;
	private Applymention applymention;
	private Commissionquery commissionquery;
	private Commissionpoints commissionpoints;
	private Userbyaddress userbyaddress;
	private Product product;
	private Spellbuyproduct spellbuyproduct;
	private Spellbuyrecord spellbuyrecord;
	private Randomnumber randomnumber;
	private Latestlottery latestlottery;
	private Shareinfo shareinfo;
	private User user;
	private String userJSON;
	private String userId;
	private String id;
	private String cid;
	private String state;
	private String mobile;
	private String sn;
	private String auth;
	private int pageNo;
	private int pageSize = 12;
	private int pageCount;
	private int resultCount;
	private String startDate;
	private String endDate;
	private String selectTime;
	private String postTitle;
	private String postContent;
	private String postAllPic;
	private File myFile;
	private String myFileFileName;
	private String myFileContentType;
	private String imageFileName;
	private static final int BUFFER_SIZE = 102400;
	private int x1;
	private int y1;
	private int w;
	private int h;
	private String hidPicUrl;
	private String key;
	private String userName;
	private String appId;
	private String timeSpan;
	private String nonceStr;
	private String lineLink;
	private String shareLink;
	private String signature;
	private List<Shareimage> shareimageList;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	static HTMLInputFilter htmlFilter = new HTMLInputFilter();

	private static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), 102400);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						102400);
				byte[] buffer = new byte[102400];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String index() {
		/*
		 * if (!ApplicationListenerImpl.sysConfigureAuth) {
		 * Struts2Utils.renderHtml(
		 * "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元夺宝开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.egouos.com\">www.egouos.com</a></div></body></html>"
		 * , new String[0]); return null; }
		 */
		if (StringUtil.isNotBlank(this.forward)) {
			this.forward = htmlFilter.filter(this.forward);
		}
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "index";
				}
			}
		}
		return "login_index";
	}

	public String UserBuyList() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					if (this.pageNo == 0) {
						this.pageNo = 1;
					}
					this.resultCount = this.spellbuyrecordService
							.buyHistoryByUserByCount(this.userId,
									this.startDate, this.endDate)
							.intValue();
					return "UserBuyList";
				}
			}
		}
		return "login_index";
	}

	public void getuserBuyListAjaxPageResultCount() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		this.resultCount = this.spellbuyrecordService.buyHistoryByUserByCount(
				this.userId, this.startDate, this.endDate).intValue();
		Struts2Utils.renderText(this.resultCount + "");
	}

	public String userBuyListAjaxPage() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		Pagination datePage = this.spellbuyrecordService.buyHistoryByUser(
				this.userId, state, null, null, this.pageNo, this.pageSize);
		List<BuyHistoryJSON> dataList = (List<BuyHistoryJSON>) datePage
				.getList();
		this.buyHistoryJSONList = new ArrayList();
		if ((dataList != null) && (dataList.size() > 0)) {
			for (int j = 0; j < dataList.size(); j++) {
				try {
					if (this.state.equals("-1")) {
						this.buyHistoryJSON = ((BuyHistoryJSON) dataList.get(j));
						if (this.buyHistoryJSON.getBuyStatus().intValue() == 1) {
							this.latestlottery = ((Latestlottery) this.latestlotteryService
									.getBuyHistoryByDetail(
											this.buyHistoryJSON.getProductId())
									.get(0));
							this.buyHistoryJSON.setWinDate(this.latestlottery
									.getAnnouncedTime());
							this.buyHistoryJSON.setWinId(this.latestlottery
									.getRandomNumber());
							String userer = null;
							if ((this.latestlottery.getUserName() != null)
									&& (!this.latestlottery.getUserName()
											.equals(""))) {
								userer = this.latestlottery.getUserName();
							} else if ((this.latestlottery.getBuyUser() != null)
									&& (!this.latestlottery.getBuyUser()
											.equals(""))) {
								userer = this.latestlottery.getBuyUser();
								if (userer.indexOf("@") != -1) {
									String[] u = userer.split("@");
									String u1 = u[0].substring(0, 2) + "***";
									userer = u1 + "@" + u[1];
								} else {
									userer = userer.substring(0, 4) + "*** "
											+ userer.substring(7);
								}
							}
							this.buyHistoryJSON.setWinUser(userer);
							this.buyHistoryJSON.setWinUserId(this.latestlottery
									.getUserId());
						}
						this.buyHistoryJSONList.add(this.buyHistoryJSON);
					} else if (this.state.equals("0")) {
						this.buyHistoryJSON = ((BuyHistoryJSON) dataList.get(j));
						if (this.buyHistoryJSON.getBuyStatus().intValue() == 0) {
							this.buyHistoryJSONList.add(this.buyHistoryJSON);
						}
					} else if (this.state.equals("1")) {
						this.buyHistoryJSON = ((BuyHistoryJSON) dataList.get(j));
						if (this.buyHistoryJSON.getBuyStatus().intValue() == 1) {
							this.latestlottery = ((Latestlottery) this.latestlotteryService
									.getBuyHistoryByDetail(
											this.buyHistoryJSON.getProductId())
									.get(0));
							this.buyHistoryJSON.setWinDate(this.latestlottery
									.getAnnouncedTime());
							this.buyHistoryJSON.setWinId(this.latestlottery
									.getRandomNumber());
							String userer = null;
							if ((this.latestlottery.getUserName() != null)
									&& (!this.latestlottery.getUserName()
											.equals(""))) {
								userer = this.latestlottery.getUserName();
							} else if ((this.latestlottery.getBuyUser() != null)
									&& (!this.latestlottery.getBuyUser()
											.equals(""))) {
								userer = this.latestlottery.getBuyUser();
								if (userer.indexOf("@") != -1) {
									String[] u = userer.split("@");
									String u1 = u[0].substring(0, 2) + "***";
									userer = u1 + "@" + u[1];
								} else {
									userer = userer.substring(0, 4) + "*** "
											+ userer.substring(7);
								}
							}
							this.buyHistoryJSON.setWinUser(userer);
							this.buyHistoryJSON.setWinUserId(this.latestlottery
									.getUserId());
							this.buyHistoryJSONList.add(this.buyHistoryJSON);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Struts2Utils.renderJson(this.buyHistoryJSONList, new String[0]);
		}
		return null;
	}

	public String UserBuyDetail() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			try {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("userId")) {
					this.userId = cookie.getValue();
					if ((this.userId != null) && (!this.userId.equals(""))) {
						this.user = ((User) this.userService
								.findById(this.userId));
						this.buyHistoryJSON = ((BuyHistoryJSON) this.spellbuyrecordService
								.getBuyHistoryByDetail(this.id, this.userId)
								.get(0));
						if (this.buyHistoryJSON.getBuyStatus().intValue() == 1) {
							this.latestlottery = ((Latestlottery) this.latestlotteryService
									.getBuyHistoryByDetail(
											this.buyHistoryJSON
													.getProductId()).get(0));
							this.buyHistoryJSON
									.setWinDate(this.latestlottery
											.getAnnouncedTime());
							this.buyHistoryJSON.setWinId(this.latestlottery
									.getRandomNumber());
							String userer = null;
							if ((this.latestlottery.getUserName() != null)
									&& (!this.latestlottery.getUserName()
											.equals(""))) {
								userer = this.latestlottery.getUserName();
							} else if ((this.latestlottery.getBuyUser() != null)
									&& (!this.latestlottery.getBuyUser()
											.equals(""))) {
								userer = this.latestlottery.getBuyUser();
								if (userer.indexOf("@") != -1) {
									String[] u = userer.split("@");
									String u1 = u[0].substring(0, 2)
											+ "***";
									userer = u1 + "@" + u[1];
								} else {
									userer = userer.substring(0, 4)
											+ "*** " + userer.substring(7);
								}
							}
							this.buyHistoryJSON.setWinUser(userer);
							this.buyHistoryJSON
									.setWinUserId(this.latestlottery
											.getUserId());
						}
						this.resultCount = 0;
						Pagination datePage = this.spellbuyrecordService
								.getRandomNumberListPage(this.id,
										this.userId, this.pageNo, 1000);
						List<Randomnumber> dataList = (List<Randomnumber>) datePage
								.getList();
						this.randomNumberJSONList = new ArrayList();
						for (Randomnumber randomnumber : dataList) {
							try {
								this.randomNumberJSON = new RandomNumberJSON();
								String[] randoms = randomnumber
										.getRandomNumber().split(",");
								String numbers = "";
								for (String string : randoms) {
									numbers = numbers + "<b>" + string
											+ "</b>";
									this.resultCount += 1;
								}
								this.randomNumberJSON
										.setRandomNumbers(numbers);
								this.randomNumberJSON.setBuyCount(String
										.valueOf(randoms.length));
								this.randomNumberJSON
										.setBuyDate(randomnumber
												.getBuyDate());
								this.randomNumberJSONList
										.add(this.randomNumberJSON);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						return "UserBuyDetail";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "login_index";
	}

	public void getRandomNumberList() {
		if (pageNo == 0) {
			pageNo = 1;
		}
		List<Randomnumber> dataList = this.spellbuyrecordService
				.getRandomNumberList(this.id, this.userId);
		this.randomNumberJSONList = new ArrayList();
		String numbers = "";
		for (Iterator<Randomnumber> localIterator = dataList.iterator(); localIterator
				.hasNext();) {
			final Randomnumber randomnumber = localIterator.next();
			final String[] randoms = randomnumber.getRandomNumber().split(",");
			for (int i = 0, j = randoms.length; i < j; ++i) {
				numbers = numbers + "<li>" + randoms[i] + "</li>";
			}
		}
		Struts2Utils.renderText(numbers, new String[0]);
	}

	public void getRandomNumberListPage() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Pagination datePage = this.spellbuyrecordService
				.getRandomNumberListPage(this.id, this.userId, this.pageNo, 50);
		List<Randomnumber> dataList = (List<Randomnumber>) datePage.getList();
		this.randomNumberJSONList = new ArrayList();
		for (Randomnumber randomnumber : dataList) {
			try {
				this.randomNumberJSON = new RandomNumberJSON();
				String[] randoms = randomnumber.getRandomNumber().split(",");
				String numbers = "";
				for (String string : randoms) {
					numbers = numbers + "<span>" + string + "</span>";
				}
				this.randomNumberJSON.setRandomNumbers(numbers);
				this.randomNumberJSON.setBuyCount(String
						.valueOf(randoms.length));
				this.randomNumberJSON.setBuyDate(randomnumber.getBuyDate());
				this.randomNumberJSONList.add(this.randomNumberJSON);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Struts2Utils.renderJson(this.randomNumberJSONList, new String[0]);
	}

	public String OrderList() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));

					return "OrderList";
				}
			}
		}
		return "login_index";
	}

	public String OrderListAjaxPage() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		if (this.postAllPic != null) {
			this.startDate = null;
			this.endDate = null;
		}
		Pagination datePage = this.latestlotteryService.getProductByUser(
				this.userId, null, null, this.hidPicUrl, this.postAllPic,
				this.pageNo, this.pageSize);
		List<Latestlottery> dataList = (List<Latestlottery>) datePage.getList();
		this.orderListJSONList = new ArrayList();
		for (int j = 0; j < dataList.size(); j++) {
			try {
				this.orderListJSON = new OrderListJSON();
				this.latestlottery = ((Latestlottery) dataList.get(j));
				this.orderListJSON.setProductName(this.latestlottery
						.getProductName());
				this.orderListJSON.setProductTitle(this.latestlottery
						.getProductTitle());
				this.orderListJSON.setProductImg(this.latestlottery
						.getProductImg());
				this.orderListJSON.setProductId(this.latestlottery
						.getSpellbuyProductId());
				this.orderListJSON.setProductPrice(this.latestlottery
						.getProductPrice());
				this.orderListJSON.setProductPeriod(this.latestlottery
						.getProductPeriod());
				this.orderListJSON.setWinId(this.latestlottery
						.getRandomNumber());
				this.orderListJSON.setWinDate(this.latestlottery
						.getAnnouncedTime());
				this.orderListJSON.setBuyStatus(this.latestlottery.getStatus());
				this.orderListJSON.setShareStatus(this.latestlottery
						.getShareStatus());
				this.orderListJSON.setShareId(this.latestlottery.getShareId());
				this.orderListJSONList.add(this.orderListJSON);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Struts2Utils.renderJson(this.orderListJSONList, new String[0]);
		return null;
	}

	public void OrderListAjaxPageResultCount() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		this.resultCount = this.latestlotteryService.getProductByUserByCount(
				this.userId, this.startDate, this.endDate).intValue();
		Struts2Utils.renderText(this.resultCount + "");
	}

	public String OrderDetail() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			try {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("userId")) {
					this.userId = cookie.getValue();
					if ((this.userId != null) && (!this.userId.equals(""))) {
						this.user = ((User) this.userService.findById(this.userId));
						this.latestlottery = ((Latestlottery) this.latestlotteryService
								.findById(this.id));
						if(user==null || latestlottery.getUserId().intValue()!=user.getUserId()){
							return "login_index";
						}
						if (this.latestlottery.getStatus().intValue() == 1) {
							this.userbyaddressList = this.userService
									.getUserbyaddress(this.userId);
							this.sProvinceList = this.regionService
									.getProvinceList();
						} else {
							this.orderdetailList = this.latestlotteryService
									.orderdetailListById(this.id);
							this.orderdetailaddress = this.latestlotteryService
									.orderdetailaddressFindByOrderdetailId(this.id);
						}
						return "OrderDetail";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "login_index";
	}
	
	public String AddressList() {
		this.request = Struts2Utils.getRequest();
		Cookie cookie = CookieUtil.getCookie(request, "userId");
		if(cookie!=null){
			this.userId = cookie.getValue();
			if ((this.userId != null) && (!this.userId.equals(""))) {
				this.user = ((User) this.userService.findById(this.userId));
				if(user==null){
					return "login_index";
				}
				this.userbyaddressList = userService.getUserbyaddress(this.userId);
				return "AddressList";
			}
		}
		return "login_index";
	}

	public String AddressAdd() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			try {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("userId")) {
					this.userId = cookie.getValue();
					if ((this.userId != null) && (!this.userId.equals(""))) {
						this.user = ((User) this.userService
								.findById(this.userId));
						/*
						 * this.latestlottery =
						 * ((Latestlottery)this.latestlotteryService
						 * .findById(this.id)); if
						 * (this.latestlottery.getStatus().intValue() == 1)
						 * { this.userbyaddressList =
						 * this.userService.getUserbyaddress(this.userId);
						 * this.sProvinceList =
						 * this.regionService.getProvinceList(); } else {
						 * this.orderdetailList =
						 * this.latestlotteryService.orderdetailListById
						 * (this.id); this.orderdetailaddress =
						 * this.latestlotteryService
						 * .orderdetailaddressFindByOrderdetailId(this.id);
						 * }
						 */
						return "AddressAdd";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "login_index";
	}
	
	public String AddressEdit() {
		this.request = Struts2Utils.getRequest();
		Cookie cookie = CookieUtil.getCookie(request, "userId");
		if (cookie.getName().equals("userId")) {
			this.userId = cookie.getValue();
			if ((this.userId != null) && (!this.userId.equals(""))) {
				this.user = ((User) this.userService.findById(this.userId));
				return "AddressEdit";
			}
		}
		return "login_index";
	}

	public void editUserContact() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			try {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("userId")) {
					this.userId = cookie.getValue();
					if ((this.userId != null) && (!this.userId.equals(""))) {
						JSONObject object = JSONObject
								.fromObject(this.userJSON);
						// String orderRemarks =
						// object.getString("orderRemarks");
						// String postDate = object.getString("postDate");
						String hidOrderNO = object.getString("hidOrderNO");
						this.userbyaddressList = this.userService
								.getUserbyaddress(userId);
						if (this.userbyaddressList.size() < 6) {
							int addrId=0;
							String province = object.getString("province");
							String city = object.getString("city");
							String district = object.getString("district");
							String address = object.getString("address");
							int zipCode = object.getInt("zipCode");
							String consignee = object
									.getString("consignee");
							String phone = object.getString("phone");
							String status = "0";
							if(object.containsKey("status")){
								status = object.getString("status");
							}
							if(object.containsKey("addrId")){
								addrId = object.getInt("addrId");
							}
							if(addrId>0){
								this.userbyaddress = userService.findAddressById(addrId);
							}else{
								this.userbyaddress = new Userbyaddress();
							}
							
							this.userbyaddress.setAddress(address);
							this.userbyaddress.setCity(city);
							this.userbyaddress.setConsignee(consignee);
							this.userbyaddress.setDistrict(district);
							this.userbyaddress.setPhone(phone);
							this.userbyaddress.setProvince(province);
							this.userbyaddress
									.setStatus(Integer.valueOf(1));
							this.userbyaddress.setUserId(Integer
									.valueOf(userId));
							this.userbyaddress.setZipCode(Integer
									.valueOf(zipCode));
							this.userbyaddress.setStatus(Integer.valueOf(status));
							this.userService.addAddress(this.userbyaddress);
							if (this.userbyaddress.getId() != null && "1".equals(status)) {
								this.userService.setDefaultAddress(
										String.valueOf(userId),
										this.userbyaddress.getId());
								this.userService.defaultAddress(String.valueOf(userId), this.userbyaddress.getId());
							}

							// this.id = userbyaddress.getId();
							// this.userbyaddress =
							// this.userService.findAddressById(Integer.valueOf(Integer.parseInt(this.id)));
							/*this.orderdetailaddress = new Orderdetailaddress();
							this.orderdetailaddress
									.setAddress(this.userbyaddress
											.getProvince()
											+ " "
											+ this.userbyaddress.getCity()
											+ " "
											+ this.userbyaddress
													.getDistrict()
											+ " "
											+ this.userbyaddress
													.getAddress());
							this.orderdetailaddress
									.setConsignee(this.userbyaddress
											.getConsignee());
							// this.orderdetailaddress.setOrderRemarks(orderRemarks);
							// this.orderdetailaddress.setPostDate(postDate);
							this.orderdetailaddress
									.setPhone(this.userbyaddress.getPhone());
							this.orderdetailaddress
									.setOrderDetailId(Integer
											.valueOf(Integer
													.parseInt(hidOrderNO)));
							this.latestlotteryService
									.addOrderdetailaddress(this.orderdetailaddress);
							this.orderdetail = new Orderdetail();
							this.orderdetail.setDate(DateUtil
									.DateTimeToStr(new Date()));
							this.orderdetail
									.setDetailText("会员已确认配送地址信息，等待商城发货");
							this.orderdetail.setOrderDetailId(Integer
									.valueOf(Integer.parseInt(hidOrderNO)));
							this.orderdetail.setUserName("会员本人");
							this.orderdetailService.add(this.orderdetail);
							this.latestlottery = ((Latestlottery) this.latestlotteryService
									.findById(hidOrderNO));
							this.latestlottery
									.setStatus(Integer.valueOf(2));
							this.latestlotteryService
									.add(this.latestlottery);*/
							Struts2Utils.renderText("success", new String[0]);
						} else {
							Struts2Utils.renderText("sizeError",
									new String[0]);
						}
					}
				}
			} catch (Exception e) {
				Struts2Utils.renderText("false", new String[0]);
				e.printStackTrace();
			}
		}
	}

	public void OrderDetailAddAddress() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			try {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("userId")) {
					this.userId = cookie.getValue();
					if ((this.userId != null) && (!this.userId.equals(""))) {
						JSONObject object = JSONObject
								.fromObject(this.userJSON);
						String orderRemarks = object
								.getString("orderRemarks");
						String postDate = object.getString("postDate");
						String hidOrderNO = object.getString("hidOrderNO");
						this.id = object.getString("id");
						this.userbyaddress = this.userService
								.findAddressById(Integer.valueOf(Integer
										.parseInt(this.id)));
						this.orderdetailaddress = new Orderdetailaddress();
						this.orderdetailaddress
								.setAddress(this.userbyaddress
										.getProvince()
										+ " "
										+ this.userbyaddress.getCity()
										+ " "
										+ this.userbyaddress.getDistrict()
										+ " "
										+ this.userbyaddress.getAddress());
						this.orderdetailaddress
								.setConsignee(this.userbyaddress
										.getConsignee());
						this.orderdetailaddress
								.setOrderRemarks(orderRemarks);
						this.orderdetailaddress.setPostDate(postDate);
						this.orderdetailaddress.setPhone(this.userbyaddress
								.getPhone());
						this.orderdetailaddress.setOrderDetailId(Integer
								.valueOf(Integer.parseInt(hidOrderNO)));
						this.latestlotteryService
								.addOrderdetailaddress(this.orderdetailaddress);
						this.orderdetail = new Orderdetail();
						this.orderdetail.setDate(DateUtil
								.DateTimeToStr(new Date()));
						this.orderdetail
								.setDetailText("会员已确认配送地址信息，等待商城发货");
						this.orderdetail.setOrderDetailId(Integer
								.valueOf(Integer.parseInt(hidOrderNO)));
						this.orderdetail.setUserName("会员本人");
						this.orderdetailService.add(this.orderdetail);
						this.latestlottery = ((Latestlottery) this.latestlotteryService
								.findById(hidOrderNO));
						this.latestlottery.setStatus(Integer.valueOf(2));
						this.latestlotteryService.add(this.latestlottery);
						Struts2Utils.renderText("success", new String[0]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void confirmOrderDetail() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			try {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("userId")) {
					this.userId = cookie.getValue();
					if ((this.userId != null) && (!this.userId.equals(""))) {
						this.latestlottery = ((Latestlottery) this.latestlotteryService
								.findById(this.id));
						this.latestlottery.setStatus(Integer.valueOf(4));
						this.latestlottery.setShareStatus(Integer
								.valueOf(-1));
						this.latestlotteryService.add(this.latestlottery);
						this.orderdetail = new Orderdetail();
						this.orderdetail.setDate(DateUtil
								.DateTimeToStr(new Date()));
						this.orderdetail.setDetailText("会员已确认收到商品。");
						this.orderdetail
								.setOrderDetailId(this.latestlottery
										.getSpellbuyProductId());
						this.orderdetail.setUserName("会员本人");
						this.orderdetailService.add(this.orderdetail);
						Struts2Utils.renderText("success", new String[0]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String PostSingleAdd() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			try {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("userId")) {
					this.userId = cookie.getValue();
					if ((this.userId != null) && (!this.userId.equals(""))) {
						this.user = ((User) this.userService
								.findById(this.userId));

						return "PostSingleAdd";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "login_index";
	}
	
	public String PostSingleEdit()
	  {
		//System.out.println("===编辑晒单----");
	    request = Struts2Utils.getRequest();
	    Cookie[] cookies = request.getCookies();
	    if (request.isRequestedSessionIdFromCookie()) {
	      for (int i = 0; i < cookies.length; i++) {
	        try
	        {
	          Cookie cookie = cookies[i];
	          if (cookie.getName().equals("userId"))
	          {
	            userId = cookie.getValue();
	            if ((userId != null) && (!userId.equals("")))
	            {
	              user = ((User)userService.findById(userId));
	              shareinfo = ((Shareinfo)shareService.findByProductId(id));
	              if (shareinfo.getUserId().intValue() == user.getUserId().intValue()) {
	            	  shareimageList = shareService.getShareimage(shareinfo.getUid().toString());
	                  StringBuffer picstr = new StringBuffer();
	                  for (Shareimage shareimage : shareimageList) {
	                    picstr.append(shareimage.getImages()).append(",");
	                  }
	                  picstr.deleteCharAt(picstr.length() - 1);
	                  hidPicUrl = picstr.toString();
	              } else {
	            	  shareinfo = null;
	              }
	              return "PostSingleEdit";
	            }
	          }
	        }
	        catch (Exception e)
	        {
	          e.printStackTrace();
	        }
	      }
	    }
	    return "login_index";
	  }
	
	public String PostSingleDetail()
	  {
		//System.out.println("===编辑晒单----");
	    request = Struts2Utils.getRequest();
	    Cookie[] cookies = request.getCookies();
	    if (request.isRequestedSessionIdFromCookie()) {
	      for (int i = 0; i < cookies.length; i++) {
	        try
	        {
	          Cookie cookie = cookies[i];
	          if (cookie.getName().equals("userId"))
	          {
	            userId = cookie.getValue();
	            if ((userId != null) && (!userId.equals("")))
	            {
	              user = ((User)userService.findById(userId));
	              shareinfo = ((Shareinfo)shareService.findByProductId(id));
	              if (shareinfo.getUserId().intValue() == user.getUserId().intValue()) {
	            	  shareimageList = shareService.getShareimage(shareinfo.getUid().toString());
	                  StringBuffer picstr = new StringBuffer();
	                  for (Shareimage shareimage : shareimageList) {
	                    picstr.append(shareimage.getImages()).append(",");
	                  }
	                  picstr.deleteCharAt(picstr.length() - 1);
	                  hidPicUrl = picstr.toString();
	              } else {
	            	  shareinfo = null;
	              }
	              return "PostSingleDetail";
	            }
	          }
	        }
	        catch (Exception e)
	        {
	          e.printStackTrace();
	        }
	      }
	    }
	    return "login_index";
	  }

	public void PostSingleAddUpLoadImg() {
		try {
			this.myFileFileName = this.myFileFileName.substring(
					this.myFileFileName.lastIndexOf("."),
					this.myFileFileName.length());
			this.imageFileName = (new Date().getTime() + this.myFileFileName
					.toLowerCase());
			String webRoot = ConfigUtil.getValue("webroot");
			String filePath = webRoot + "/uploadImages" + "/" + this.imageFileName;
			/*String filePath = ServletActionContext.getServletContext()
					.getRealPath("/uploadImages") + "/" + this.imageFileName;*/
			File imageFile = new File(filePath);
			if (this.myFile != null) {
				copy(this.myFile, imageFile);

				CutImages.scale2(filePath, filePath, 420, 560, true);
				Struts2Utils.renderText(this.imageFileName, new String[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void PostSingleAddShare() throws Exception {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					try {
						this.shareinfo = this.shareService
								.findByProductId(this.id);
						if (this.shareinfo == null) {
							this.shareinfo = new Shareinfo();
							this.shareinfo.setSproductId(Integer
									.valueOf(Integer.parseInt(this.id)));
							this.shareinfo
									.setReplyCount(Integer.valueOf(0));
							this.shareinfo.setReward(Integer.valueOf(0));
							this.shareinfo
									.setShareContent(this.postContent);
							this.shareinfo.setShareDate(DateUtil
									.DateTimeToStr(new Date()));
							this.shareinfo.setShareTitle(this.postTitle);
							this.shareinfo.setStatus(Integer.valueOf(0));
							this.shareinfo.setUpCount(Integer.valueOf(0));
							this.shareinfo
									.setUserId(Integer.valueOf(Integer
											.parseInt(this.userId)));
							this.shareService.add(this.shareinfo);

							this.latestlottery = ((Latestlottery) this.latestlotteryService
									.findById(this.id));
							this.latestlottery.setStatus(Integer
									.valueOf(10));
							this.latestlottery.setShareStatus(Integer
									.valueOf(0));
							this.latestlottery.setShareId(this.shareinfo
									.getUid());
							this.latestlotteryService
									.add(this.latestlottery);

							String[] sfile = this.postAllPic.split(",");
							String webRoot = ConfigUtil.getValue("webroot");
							String sfilePath = "/uploadImages";
							List<File> Filedata = new ArrayList();
							for (int j = 0; j < sfile.length; j++) {
								//String f = ServletActionContext.getServletContext().getRealPath(sfilePath) + "/" + sfile[j];
								String f = webRoot+sfilePath+"/" + sfile[j];
								System.err.println(f);
								Filedata.add(new File(f));
							}
							String productImgPath = "/UserPost";
							long fileDateName = 0L;
							String biGImagePath = null;
							String smallImagePath = null;
							String listImgPath = null;
							if ((Filedata == null)
									|| (Filedata.size() == 0)) {
								Struts2Utils.renderText("false",
										new String[0]);
							}
							for (int j = 0; j < Filedata.size(); j++) {
								this.myFile = ((File) Filedata.get(j));
								if (this.myFile != null) {
									this.myFileFileName = ((File) Filedata
											.get(j)).getName().substring(
											((File) Filedata.get(j))
													.getName().lastIndexOf(
															"."),
											((File) Filedata.get(j))
													.getName().length());
									fileDateName = new Date().getTime();
									this.imageFileName = (fileDateName + this.myFileFileName);
									biGImagePath = webRoot+productImgPath+"/Big/";
									smallImagePath = webRoot+productImgPath+"/Small/";
									listImgPath = webRoot+productImgPath+"/220/";
									/*biGImagePath = ServletActionContext
											.getServletContext()
											.getRealPath(productImgPath)
											+ "/Big/";
									smallImagePath = ServletActionContext
											.getServletContext()
											.getRealPath(productImgPath)
											+ "/Small/";
									listImgPath = ServletActionContext
											.getServletContext()
											.getRealPath(productImgPath)
											+ "/220/";*/
									File bigImageFile = new File(
											biGImagePath
													+ this.imageFileName);
									File smallImageFile = new File(
											smallImagePath
													+ this.imageFileName);
									File listImageFile = new File(
											smallImagePath
													+ this.imageFileName);
									copy(this.myFile, bigImageFile);
									copy(this.myFile, smallImageFile);
									copy(this.myFile, listImageFile);
									CutImages.equimultipleConvert(560, 420,
											bigImageFile,
											new File(biGImagePath
													+ fileDateName
													+ this.myFileFileName));
									CutImages.equimultipleConvert(220, 220,
											listImageFile,
											new File(listImgPath
													+ fileDateName
													+ this.myFileFileName));
									CutImages.equimultipleConvert(100, 100,
											smallImageFile,
											new File(smallImagePath
													+ fileDateName
													+ this.myFileFileName));
									Shareimage shareimage = new Shareimage();
									shareimage
											.setShareInfoId(this.shareinfo
													.getUid());
									shareimage.setImages(fileDateName
											+ this.myFileFileName);
									this.shareService
											.addShareImage(shareimage);
								}
							}
							Struts2Utils.renderText("true", new String[0]);
						}
					} catch (Exception e) {
						e.printStackTrace();
						Struts2Utils.renderText("false", new String[0]);
					}
				}
			}
		}
	}
	
	public void PostSingleEditShare()
		    throws Exception
		  {
		    request = Struts2Utils.getRequest();
		    Cookie[] cookies = request.getCookies();
		    if (request.isRequestedSessionIdFromCookie()) {
		      for (int i = 0; i < cookies.length; i++)
		      {
		        Cookie cookie = cookies[i];
		        if (cookie.getName().equals("userId"))
		        {
		          userId = cookie.getValue();
		          if ((userId != null) && (!userId.equals(""))) {
		            try
		            {
		              shareinfo = shareService.findByProductId(id);
		              if (shareinfo != null)
		              {
		                shareinfo.setSproductId(Integer.valueOf(Integer.parseInt(id)));
		                shareinfo.setReplyCount(Integer.valueOf(0));
		                shareinfo.setReward(Integer.valueOf(0));
		                shareinfo.setShareContent(postContent);
		                shareinfo.setShareDate(DateUtil.DateTimeToStr(new Date()));
		                shareinfo.setShareTitle(postTitle);
		                shareinfo.setStatus(Integer.valueOf(0));
		                shareinfo.setUpCount(Integer.valueOf(0));
		                shareinfo.setUserId(Integer.valueOf(Integer.parseInt(userId)));
		                shareService.add(shareinfo);
		                
		                latestlottery = ((Latestlottery)latestlotteryService.findById(id));
		                latestlottery.setStatus(Integer.valueOf(10));
		                latestlottery.setShareStatus(Integer.valueOf(0));
		                latestlottery.setShareId(shareinfo.getUid());
		                latestlotteryService.add(latestlottery);
		                
		                String[] sfile = postAllPic.split(",");
		                String sfilePath = "/uploadImages";
		                List<File> Filedata = new ArrayList();
		                for (int j = 0; j < sfile.length; j++)
		                {
		                  String f = ServletActionContext.getServletContext().getRealPath(sfilePath) + "/" + sfile[j];
		                  
		                  Filedata.add(new File(f));
		                }
		                String productImgPath = "/UserPost";
		                String biGImagePath = null;
		                String smallImagePath = null;
		                String listImgPath = null;
		                if ((Filedata == null) || (Filedata.size() == 0))
		                {
		                  Struts2Utils.renderText("false", new String[0]);
		                }
		                else
		                {
		                  try
		                  {
		                    shareimageList = shareService.getShareimage(String.valueOf(shareinfo.getUid()));
		                    for (Shareimage shareimage : shareimageList) {
		                      //shareService.delShareImageByShareId(shareimage.getUid());
		                    }
		                    biGImagePath = ServletActionContext.getServletContext().getRealPath(productImgPath) + "/Big/";
	                          File bigFile = new File(biGImagePath);
	                          if (!bigFile.exists()) {
	                        	  bigFile.mkdirs();
	                          }
	                          smallImagePath = ServletActionContext.getServletContext().getRealPath(productImgPath) + "/Small/";
	                          File smallFile = new File(smallImagePath);
	                          if (!smallFile.exists()) {
	                        	  smallFile.mkdirs();
	                          }
	                          listImgPath = ServletActionContext.getServletContext().getRealPath(productImgPath) + "/220/";
	                          File listFile = new File(listImgPath);
	                          if (!listFile.exists()) {
	                        	  listFile.mkdirs();
	                          }
		                    for (int j = 0; j < Filedata.size(); j++) {
		                      try
		                      {
		                        myFile = ((File)Filedata.get(j));
		                        if (myFile != null)
		                        {
		                          myFileFileName = ((File)Filedata.get(j)).getName();
		                          

		                          imageFileName = myFileFileName;
		                          
		                          File bigImageFile = new File(biGImagePath + imageFileName);
		                          File smallImageFile = new File(smallImagePath + imageFileName);
		                          File listImageFile = new File(smallImagePath + imageFileName);
		                          copy(myFile, bigImageFile);
		                          copy(myFile, smallImageFile);
		                          copy(myFile, listImageFile);
		                          CutImages.equimultipleConvert(700, 525, bigImageFile, new File(biGImagePath + myFileFileName));
		                          CutImages.equimultipleConvert(220, 220, listImageFile, new File(listImgPath + myFileFileName));
		                          CutImages.equimultipleConvert(100, 100, smallImageFile, new File(smallImagePath + myFileFileName));
		                          
		                          Shareimage shareimage = new Shareimage();
		                          shareimage.setShareInfoId(shareinfo.getUid());
		                          shareimage.setImages(imageFileName);
		                          shareService.addShareImage(shareimage);
		                        }
		                      }
		                      catch (Exception localException2) {}
		                    }
		                  }
		                  catch (Exception e)
		                  {
		                    Struts2Utils.renderText("false", new String[0]);
		                    e.printStackTrace();
		                  }
		                  Struts2Utils.renderText("true", new String[0]);
		                }
		              }
		            }
		            catch (Exception e)
		            {
		              e.printStackTrace();
		              Struts2Utils.renderText("false", new String[0]);
		            }
		          }
		        }
		      }
		    }
		  }

	public String PostSingleList() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));

					return "PostSingleList";
				}
			}
		}
		return "login_index";
	}

	public String PostSingleListAjaxPage() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		Pagination datePage = this.shareService.shareByUser(this.userId, null,
				null, this.pageNo, this.pageSize);
		List<Object[]> pageList = (List<Object[]>) datePage.getList();
		this.orderListJSONList = new ArrayList();
		for (int j = 0; j < pageList.size(); j++) {
			try {
				orderListJSON = new OrderListJSON();
				Shareimage shareimage = null;
				Object[] objects = (Object[]) pageList.get(j);
				if (objects[0] instanceof Shareinfo) {
					shareinfo = (Shareinfo) objects[0];
					shareimage = (Shareimage) objects[1];
				} else {
					shareinfo = (Shareinfo) objects[1];
					shareimage = (Shareimage) objects[0];
				}
				// shareinfo = ((Shareinfo) ((Object[]) pageList.get(j))[0]);
				// Shareimage shareimage = (Shareimage) ((Object[])
				// pageList.get(j))[1];
				String shareContent = this.shareinfo.getShareContent();
				if(shareContent!=null && shareContent.length()>50){
					shareContent = shareContent.substring(0, 50) + "...";
				}
				this.orderListJSON.setProductTitle(this.shareinfo
						.getShareTitle());
				this.orderListJSON.setProductImg(shareimage.getImages());
				this.orderListJSON.setProductId(this.shareinfo.getSproductId());
				this.orderListJSON.setShareStatus(this.shareinfo.getStatus());
				this.orderListJSON.setShareId(this.shareinfo.getUid());
				this.orderListJSON.setBuyTime(this.shareinfo.getShareDate());
				this.orderListJSON.setProductName(shareContent);
				this.orderListJSONList.add(this.orderListJSON);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Struts2Utils.renderJson(this.orderListJSONList, new String[0]);
		return null;
	}

	public void PostSingleListAjaxPageResultCount() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		this.resultCount = this.shareService.getShareByUserByCount(this.userId,
				this.hidPicUrl, this.postAllPic, null, null).intValue();
		Struts2Utils.renderText(this.resultCount + "");
	}

	public String InvitedList() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					this.resultCount = this.userService
							.getInvitedListByCount(this.userId).intValue();
					this.userList = this.userService
							.getInvitedListByData(this.userId);
					for (User user : this.userList) {
						if (user.getExperience().intValue() > 0) {
							this.w += 1;
							this.h += ApplicationListenerImpl.sysConfigureJson
									.getInvite().intValue();
						}
					}
					return "InvitedList";
				}
			}
		}
		return "login_index";
	}

	public String InvitedListAjaxPage() {
		if (pageNo == 0) {
			pageNo = 1;
		}
		Pagination datePage = this.userService.getInvitedList(this.userId,
				this.pageNo, this.pageSize);
		this.userList = (List<User>) datePage.getList();
		for (User user : this.userList) {
			user.setUserName(UserNameUtil.userName(user));
		}
		Struts2Utils.renderJson(this.userList, new String[0]);
		return null;
	}

	public String CommissionQuery() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));

					return "CommissionQuery";
				}
			}
		}
		return "login_index";
	}

	public String CommissionQueryAjaxPage() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		Pagination datePage = this.commissionqueryService
				.getCommissionQueryList(this.userId, null, null, this.pageNo,
						this.pageSize);
		List<Object[]> dateList = (List<Object[]>) datePage.getList();
		this.commissionqueryJSONList = new ArrayList();
		if (dateList.size() > 0) {
			for (int i = 0; i < dateList.size(); i++) {
				commissionqueryJSON = new CommissionqueryJSON();
				Object[] objects = (Object[]) dateList.get(i);
				if (objects[0] instanceof User) {
					user = (User) objects[0];
					commissionquery = (Commissionquery) objects[1];
				} else {
					user = (User) objects[1];
					commissionquery = (Commissionquery) objects[0];
				}
				// user = ((User) ((Object[]) dateList.get(i))[0]);
				// commissionquery = ((Commissionquery) ((Object[])
				// dateList.get(i))[1]);
				this.commissionqueryJSON.setBuyMoney(this.commissionquery
						.getBuyMoney());
				this.commissionqueryJSON.setCommission(this.commissionquery
						.getCommission());
				this.commissionqueryJSON
						.setDate(this.commissionquery.getDate());
				this.commissionqueryJSON.setDescription(this.commissionquery
						.getDescription());
				this.commissionqueryJSON.setUserId(String.valueOf(this.user
						.getUserId()));
				this.commissionqueryJSON.setUserName(UserNameUtil
						.userName(this.user));
				this.commissionqueryJSONList.add(this.commissionqueryJSON);
			}
		}
		Struts2Utils.renderJson(this.commissionqueryJSONList, new String[0]);
		return null;
	}

	public void getCommissionQueryAjaxPageResultCount() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		this.resultCount = this.commissionqueryService
				.getCommissionQueryListByCount(this.userId, this.startDate,
						this.endDate).intValue();
		Struts2Utils.renderText(this.resultCount + "");
	}

	public String MemberPoints() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));

					return "CommissionPoints";
				}
			}
		}
		return "login_index";
	}

	public String CommissionPointsAjaxPage() {
		if (pageNo == 0) {
			pageNo = 1;
		}
		Pagination datePage = this.commissionpointsService.CommissionPoints(
				this.userId, this.startDate, this.endDate, this.pageNo,
				this.pageSize);
		this.commissionpointsList = (List<Commissionpoints>) datePage.getList();
		Struts2Utils.renderJson(this.commissionpointsList);
		return null;
	}

	public String ShareList() throws Exception {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					// 邀请好友数
					this.resultCount = this.userService
							.getInvitedListByCount(this.userId).intValue();
					appId = conf.getWeixinAppId();
					timeSpan = String.valueOf(new Date().getTime() / 1000);
					nonceStr = Sha1Util.getNonceStr();
					shareLink = conf.getWeixinUrl() + "/share.html?uid="
							+ userId;
					lineLink = conf.getWeixinUrl() + "/user/ShareList.html";
					// lineLink =
					// "http://192.168.30.105/user/ShareList.html";

					String jsapi_ticket = (String) AliyunOcsSampleHelp
							.getIMemcachedCache().get("jsapi_ticket");
					if (jsapi_ticket == null) {
						StringBuilder sb = new StringBuilder(
								"https://api.weixin.qq.com/cgi-bin/token");
						sb.append("?grant_type=client_credential");
						sb.append("&appid=" + conf.getWeixinAppId());
						sb.append("&secret=" + conf.getWeixinAppSecret());
						// request
						String result = httproxy.get(sb.toString(), null);
						System.out.println("[ShareList]result=" + result);
						LOG.debug("access_token return: {}", result);
						JSONObject obj = JSONObject.fromObject(result);
						if (!obj.containsKey("access_token")) {
							return "ShareList";
						}
						String access_token = obj.getString("access_token");
						sb.setLength(0);
						sb.append("https://api.weixin.qq.com/cgi-bin/ticket/getticket");
						sb.append("?access_token=").append(access_token);
						sb.append("&type=jsapi");
						result = httproxy.get(sb.toString(), null);
						LOG.debug("jsapi_ticket return: {}", result);
						obj = JSONObject.fromObject(result);
						jsapi_ticket = obj.getString("ticket");
						int exp = obj.getInt("expires_in");
						AliyunOcsSampleHelp.getIMemcachedCache().set(
								"jsapi_ticket", exp - 5, jsapi_ticket);
						// System.out.println("jsapi_ticket=" +
						// jsapi_ticket);
					}

					StringBuilder sb = new StringBuilder();
					sb.append("jsapi_ticket=").append(jsapi_ticket);
					sb.append("&noncestr=").append(nonceStr);
					sb.append("&timestamp=").append(timeSpan);
					sb.append("&url=").append(lineLink);

					signature = Sha1Util.getSha1(sb.toString());

					return "ShareList";
				}
			}
		}
		return "login_index";

	}

	public String ApplyMention() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "ApplyMention";
				}
			}
		}
		return "login_index";
	}

	public void ApplyMentionAdd() {
		try {
			JSONObject object = JSONObject.fromObject(this.userJSON);
			this.userId = object.getString("userId");
			this.user = ((User) this.userService.findById(this.userId));
			double money = Double.parseDouble(object.getString("money"));
			double commissionBalance = this.user.getCommissionBalance()
					.doubleValue();
			double commissionMention = this.user.getCommissionMention()
					.doubleValue();
			if (commissionBalance < money) {
				Struts2Utils.renderText("moneyError", new String[0]);
			} else {
				this.user.setCommissionBalance(Double.valueOf(commissionBalance
						- money));
				this.user.setCommissionMention(Double.valueOf(commissionMention
						+ money));
				this.userService.add(this.user);
				this.applymention = new Applymention();
				this.applymention.setBankName(object.getString("bankName"));
				this.applymention.setBankNo(object.getString("bankNo"));
				this.applymention.setBankSubbranch(object
						.getString("bankSubbranch"));
				this.applymention.setBankUser(object.getString("bankUser"));
				this.applymention.setDate(DateUtil.DateTimeToStr(new Date()));
				this.applymention.setFee(Double.valueOf(0.0D));
				this.applymention.setMoney(Double.valueOf(money));
				this.applymention.setPhone(object.getString("phone"));
				this.applymention.setStatus("审核中");
				this.applymention.setUserId(Integer.valueOf(Integer
						.parseInt(this.userId)));
				this.applymentionService.add(this.applymention);
				Struts2Utils.renderText("success", new String[0]);
			}
		} catch (Exception e) {
			Struts2Utils.renderText("error", new String[0]);
			e.printStackTrace();
		}
	}

	public void ApplyMentionAddToUser() {
		try {
			JSONObject object = JSONObject.fromObject(this.userJSON);
			double recharge = Double.parseDouble(object.getString("recharge"));
			this.userId = object.getString("userId");
			this.user = ((User) this.userService.findById(this.userId));
			double tempCommissionBalance = this.user.getCommissionBalance()
					.doubleValue();
			double commissionMention = this.user.getCommissionMention()
					.doubleValue();
			if (tempCommissionBalance < recharge) {
				Struts2Utils.renderText("moneyError", new String[0]);
			} else {
				this.user.setCommissionMention(Double.valueOf(commissionMention
						+ recharge));
				this.user.setCommissionBalance(Double
						.valueOf(tempCommissionBalance - recharge));
				double tempBalance = this.user.getBalance().doubleValue();
				this.user.setBalance(Double.valueOf(tempBalance + recharge));
				this.userService.add(this.user);
				Struts2Utils.renderText("success", new String[0]);
			}
		} catch (Exception e) {
			Struts2Utils.renderText("error", new String[0]);
			e.printStackTrace();
		}
	}

	public String MentionList() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					this.resultCount = this.applymentionService
							.getApplymentionListByCount(this.userId,
									this.startDate, this.endDate)
							.intValue();
					return "MentionList";
				}
			}
		}
		return "login_index";
	}

	public String MentionListAjaxPage() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		Pagination datePage = this.applymentionService.getApplymentionList(
				this.userId, this.startDate, this.endDate, this.pageNo,
				this.pageSize);
		List<Applymention> dataList = (List<Applymention>) datePage.getList();
		Struts2Utils.renderJson(dataList, new String[0]);
		return null;
	}

	public void getMentionListAjaxPageResultCount() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		this.resultCount = this.applymentionService.getApplymentionListByCount(
				this.userId, this.startDate, this.endDate).intValue();
		Struts2Utils.renderText(this.resultCount + "");
	}

	public String UserRecharge() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "UserRecharge";
				}
			}
		}
		return "login_index";
	}

	public String userCardRecharge() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "userCardRecharge";
				}
			}
		}
		return "login_index";
	}

	public void doCardRecharge() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					try {
						if (StringUtil.isNotBlank(id)) {
							// String randomNo = this.id.substring(0, 32);
							// String cardPwd = this.id.substring(32,
							// this.id.length());
							String randomNo = id;
							String cardPwd = MD5Util.encode(id);
							Cardpassword cardpassword = this.cardpasswordService
									.doCardRecharge(randomNo, cardPwd);
							if (cardpassword != null) {
								Double temp = this.user.getBalance();
								this.cardpasswordService
										.deleteByID(cardpassword.getId());
								this.user.setBalance(Double.valueOf(temp
										.doubleValue()
										+ cardpassword.getMoney()
												.doubleValue()));
								this.userService.add(this.user);
								Consumetable consumetable = new Consumetable();
								consumetable
										.setBuyCount(Integer.valueOf(Integer
												.parseInt(new DecimalFormat(
														"0")
														.format(cardpassword
																.getMoney()))));
								consumetable.setDate(DateUtil
										.DateTimeToStr(new Date()));
								consumetable.setInterfaceType("卡密充值");
								consumetable.setMoney(cardpassword
										.getMoney());
								/*
								 * consumetable.setOutTradeNo(""); String s
								 * =
								 * UUID.randomUUID().toString().toUpperCase
								 * (); s = s.substring(0, 8) +
								 * s.substring(9, 13) + s.substring(14, 18)
								 * + s.substring(19, 23) + s.substring(24);
								 * consumetable.setTransactionId(s);
								 */
								consumetable.setOutTradeNo(randomNo);
								consumetable.setTransactionId(cardPwd);
								consumetable.setUserId(Integer
										.valueOf(Integer
												.parseInt(this.userId)));
								consumetable.setWithold("");
								consumetable
										.setPayStatus(Consumetable.PAY_STAT_PAID);
								this.consumetableService.add(consumetable);
								Struts2Utils.renderText("yes",
										new String[0]);
							} else {
								Struts2Utils
										.renderText("no", new String[0]);
							}
						} else {
							Struts2Utils.renderText("no", new String[0]);
						}
					} catch (Exception e) {
						e.printStackTrace();
						Struts2Utils.renderText("no", new String[0]);
					}
				}
			}
		}
	}

	public void getCardInfoByAccount() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		if (user != null) {
			String randomNo = id;
			String cardPwd = MD5Util.encode(id);
			Cardpassword cardpassword = this.cardpasswordService
					.doCardRecharge(randomNo, cardPwd);
			if (cardpassword != null) {
				sb.append('{');
				sb.append("\"cardState\":").append(1).append(",");
				sb.append("\"wasteMoney\":").append(cardpassword.getMoney());
				sb.append('}');
			} else {
				sb.append('{');
				sb.append("\"cardState\":").append(2);
				sb.append('}');
			}
			Struts2Utils.renderJson(sb.toString());
		}
	}

	public String UserBalance() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					this.resultCount = this.consumetableService
							.userByConsumetableByDeltaByCount(this.userId,
									this.startDate, this.endDate)
							.intValue();
					return "UserBalance";
				}
			}
		}
		return "login_index";
	}

	public String ConsumeList() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));

					return "ConsumeList";
				}
			}
		}
		return "login_index";
	}

	public String ConsumeListAjaxPage() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		Pagination datePage = this.consumetableService.userByConsumetable(
				this.userId, null, null, this.pageNo, this.pageSize);
		List<Consumetable> dataList = (List<Consumetable>) datePage.getList();
		Struts2Utils.renderJson(dataList, new String[0]);
		return null;
	}

	public void ConsumeListAjaxPageResultCount() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		this.resultCount = this.consumetableService.userByConsumetableByCount(
				this.userId, this.startDate, this.endDate).intValue();
		Struts2Utils.renderText(this.resultCount + "");
	}

	public String RechargeList() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));

					return "RechargeList";
				}
			}
		}
		return "login_index";
	}

	public String ConsumeListByDeltaAjaxPage() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		Pagination datePage = this.consumetableService
				.userByConsumetableByDelta(this.userId, null, null,
						this.pageNo, this.pageSize);
		List<Consumetable> dataList = (List<Consumetable>) datePage.getList();
		Struts2Utils.renderJson(dataList, new String[0]);
		return null;
	}

	public void ConsumeListByDeltaAjaxPageResultCount() {
		if (this.pageNo == 0) {
			this.pageNo = 1;
		}
		Date date = new Date();
		if (StringUtil.isNotBlank(this.selectTime)) {
			if (this.selectTime.equals("0")) {
				this.startDate = null;
				this.endDate = null;
			} else if (this.selectTime.equals("1")) {
				this.startDate = (DateUtil.DateToStr(date) + " 00:00:00");
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
			} else if (this.selectTime.equals("2")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil
						.DateToStr(DateUtil.addDate(date, -7)) + " 00:00:00");
			} else if (this.selectTime.equals("3")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -30)) + " 00:00:00");
			} else if (this.selectTime.equals("4")) {
				this.endDate = (DateUtil.DateToStr(date) + " 23:59:59");
				this.startDate = (DateUtil.DateToStr(DateUtil
						.addDate(date, -90)) + " 00:00:00");
			}
		} else {
			this.startDate += " 00:00:00";
			this.endDate += " 23:59:59";
		}
		this.resultCount = this.consumetableService
				.userByConsumetableByDeltaByCount(this.userId, this.startDate,
						this.endDate).intValue();
		Struts2Utils.renderText(this.resultCount + "");
	}

	public String Address() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "Address";
				}
			}
		}
		return "login_index";
	}
	
	public void getAddrByID() {
		this.userbyaddress = this.userService.findAddressById(Integer.valueOf(this.id));
		Struts2Utils.renderJson(this.userbyaddress, new String[0]);
	}

	public void getAddressList() {
		this.userbyaddressList = this.userService.getUserbyaddress(this.userId);
		Struts2Utils.renderJson(this.userbyaddressList, new String[0]);
	}

	public String addAddress() {
		try {
			JSONObject object = JSONObject.fromObject(this.userJSON);
			int userId = object.getInt("userId");
			this.userbyaddressList = this.userService.getUserbyaddress(userId
					+ "");
			if (this.userbyaddressList.size() < 4) {
				String province = object.getString("province");
				String city = object.getString("city");
				String district = object.getString("district");
				String address = object.getString("address");
				int zipCode = object.getInt("zipCode");
				String consignee = object.getString("consignee");
				String phone = object.getString("phone");
				this.userbyaddress = new Userbyaddress();
				this.userbyaddress.setAddress(address);
				this.userbyaddress.setCity(city);
				this.userbyaddress.setConsignee(consignee);
				this.userbyaddress.setDistrict(district);
				this.userbyaddress.setPhone(phone);
				this.userbyaddress.setProvince(province);
				this.userbyaddress.setStatus(Integer.valueOf(1));
				this.userbyaddress.setUserId(Integer.valueOf(userId));
				this.userbyaddress.setZipCode(Integer.valueOf(zipCode));
				this.userService.addAddress(this.userbyaddress);
				if (this.userbyaddress.getId() != null) {
					this.userService.setDefaultAddress(String.valueOf(userId),
							this.userbyaddress.getId());
				}
				Struts2Utils.renderText("success", new String[0]);
			} else {
				Struts2Utils.renderText("sizeError", new String[0]);
			}
		} catch (Exception e) {
			Struts2Utils.renderText("false", new String[0]);
			e.printStackTrace();
		}
		return null;
	}

	public void setDefaultAddress() {
		try {
			this.userService.defaultAddress(this.userId,
					Integer.valueOf(Integer.parseInt(this.id)));
			this.userService.setDefaultAddress(String.valueOf(this.userId),
					Integer.valueOf(Integer.parseInt(this.id)));
			Struts2Utils.renderText("success", new String[0]);
		} catch (Exception e) {
			Struts2Utils.renderText("false", new String[0]);
			e.printStackTrace();
		}
	}

	public void delAddress() {
		try {
			this.userService.delAddress(Integer.valueOf(Integer
					.parseInt(this.id)));
			Struts2Utils.renderText("success", new String[0]);
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderText("false", new String[0]);
		}
	}

	public void getProvinceList() {
		this.sProvinceList = this.regionService.getProvinceList();
		Struts2Utils.renderJson(this.sProvinceList, new String[0]);
	}

	public void getCityList() {
		this.sCityList = this.regionService.getCityListByProvinceId(this.id);
		Struts2Utils.renderJson(this.sCityList, new String[0]);
	}

	public void getDistrictList() {
		this.sDistrictList = this.regionService
				.getDistrictListByCityId(this.id);
		Struts2Utils.renderJson(this.sDistrictList, new String[0]);
	}
	
	public String SafeSettings() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "SafeSettings";
				}
			}
		}
		return "login_index";
	}

	public String MemberModify() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "MemberModify";
				}
			}
		}
		return "login_index";
	}

	public String qqUserInfoAuth() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "qqUserInfoAuth";
				}
			}
		}
		return "login_index";
	}

	public String MobileBind() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "MobileBind";
				}
			}
		}
		return "login_index";
	}

	public void sendMsg() {
		// TODO 先判断手机号码是否正确
		Random random = new Random();
		String ran = "";
		for (int i = 0; i < 6; i++) {
			ran = ran + random.nextInt(9);
		}
		try {
			String content = ApplicationListenerImpl.sysConfigureJson
					.getVerifyMsgTpl().replace("{000000}", ran);
			boolean success = SmsSenderFactory.create().send(this.mobile, content);
			if (success) {
				AliyunOcsSampleHelp.getIMemcachedCache().set(
						Base64.getEncode(this.mobile), 120, ran);
				Struts2Utils.renderText("0");
			} else {
				Struts2Utils.renderText("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderText("error");
		}
	}

	public void verifyorgmobilesn(){
		String mobileStr = (String) AliyunOcsSampleHelp.getIMemcachedCache()
				.get(Base64.getEncode(this.mobile));
		this.key = (MD5Util.encode(this.mobile)
				+ MD5Util.encode(DateUtil.dateTimeToStr(new Date())) + Base64
				.getEncode(this.mobile));
		if (mobileStr == null) {
			Struts2Utils.renderJson("{\"state\":2}");
			return;
		}
		if (mobileStr.equals(sn)) {
			Struts2Utils.renderJson("{\"state\":0,\"str\":\"" + key + "\"}");
			return;
		} else {
			Struts2Utils.renderJson("{\"state\":-4}");
			return;
		}
	}

	public void updateUserMobile() throws UnsupportedEncodingException {
		String oldMobile=null;
		if(!StringUtil.isEmpty(auth)){
			this.key = this.auth.substring(64, this.auth.length());
			oldMobile = Base64.getDecode(this.key);
		}
		String scode = (String) AliyunOcsSampleHelp.getIMemcachedCache().get(
				Base64.getEncode(this.mobile));
		try {
			if (StringUtil.equals(scode, sn)) {
				this.request = Struts2Utils.getRequest();
				Cookie[] cookies = this.request.getCookies();
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (cookie.getName().equals("userId")) {
						this.userId = cookie.getValue();
						if ((this.userId != null)
								&& (!this.userId.equals(""))) {
							this.user = ((User) this.userService
									.findById(this.userId));
							if(StringUtil.equals(this.user.getMobileCheck(), "0") && !StringUtil.equals(this.user.getPhone(), oldMobile)){
								Struts2Utils.renderText("1", new String[0]);
								return;
							}
							if (mobile != null) {
								this.user.setMobileCheck("0");
								this.user.setPhone(mobile);
								this.userService.add(this.user);
								Struts2Utils.renderText("0", new String[0]);
							} else {
								Struts2Utils.renderText("1", new String[0]);
							}
						}
					}
				}
			} else {
				Struts2Utils.renderText("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderText("false");
		}
	}

	public String MobileCheck() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "MobileCheck";
				}
			}
		}
		return "login_index";
	}

	public void MobileReturn() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					String mobileStr = (String) AliyunOcsSampleHelp
							.getIMemcachedCache().get(this.userJSON);
					if (mobileStr != null) {
						if (mobileStr.equals(this.id)) {
							this.user.setMobileCheck("0");
							this.user.setPhone(this.userJSON);
							this.userService.add(this.user);
							Struts2Utils.renderText("0", new String[0]);
						} else {
							Struts2Utils.renderText("2", new String[0]);
						}
					} else {
						Struts2Utils.renderText("1", new String[0]);
					}
				}
			}
		}
	}

	public String MobileReturnSuccess() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "MobileReturnSuccess";
				}
			}
		}
		return "login_index";
	}

	public String EmailChecking() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "EmailChecking";
				}
			}
		}
		return "login_index";
	}

	public void EmailSending() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					this.request = Struts2Utils.getRequest();
					String key = MD5Util.encode(this.userJSON)
							+ MD5Util.encode(DateUtil
									.dateTimeToStr(new Date()))
							+ Base64.getEncode(this.userJSON);
					String html = "<table width=\"600\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: #dddddd 1px solid; padding: 20px 0;\"><tbody><tr><td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"border-bottom: #ff6600 2px solid; padding-bottom: 12px;\"><tbody><tr><td style=\"line-height: 22px; padding-left: 20px;\"><a target=\"_blank\" title=\""
							+

							ApplicationListenerImpl.sysConfigureJson
									.getSaitName()
							+ "\" href=\""
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "\"><img width=\"230px\" border=\"0\" height=\"52\" src=\""
							+ ApplicationListenerImpl.sysConfigureJson
									.getImgUrl()
							+ "/Images/mail_logo.gif\"></a></td>"
							+ "<td align=\"right\" style=\"font-size: 12px; padding-right: 20px; padding-top: 30px;\"><a style=\"color: #22aaff; text-decoration: none;\" target=\"_blank\" href=\""
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "\">首页</a><b style=\"width: 1px; height: 10px; vertical-align: -1px; font-size: 1px; background: #CACACA; display: inline-block; margin: 0 5px;\"></b>"
							+ "<a style=\"color: #22aaff; text-decoration: none;\" target=\"_blank\" href=\""
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "/user/index.html\">我的"
							+ ApplicationListenerImpl.sysConfigureJson
									.getSaitName()
							+ "</a><b style=\"width: 1px; height: 10px; vertical-align: -1px; font-size: 1px; background: #CACACA; display: inline-block; margin: 0 5px;\"></b><a style=\"color: #22aaff; text-decoration: none;\" target=\"_blank\" href=\""
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "/help/index.html\">帮助</a></td>"
							+ "</tr>"
							+ "</tbody></table>"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"padding: 0 20px;\">"
							+ "<tbody><tr>"
							+ "<td style=\"font-size: 14px; color: #333333; height: 40px; line-height: 40px; padding-top: 10px;\">亲爱的 <b style=\"color: #333333; font-family: Arial;\"><a href=\"mailto:"
							+ this.userJSON
							+ "\" target=\"_blank\">"
							+ this.userJSON
							+ "</a></b>：</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td style=\"font-size: 12px; color: #333333; line-height: 22px;\"><p style=\"text-indent: 2em; padding: 0; margin: 0;\">您好！请点击下面的按钮，完成邮箱验证：</p></td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td style=\"padding-top: 15px; padding-left: 28px;\"><a title=\"邮箱验证\" style=\"display: inline-block; padding: 0 25px; height: 28px; line-height: 28px; text-align: center; color: #ffffff; background: #ff7700; font-size: 12px; cursor: pointer; border-radius: 2px; text-decoration: none;\" target=\"_blank\" href=\""
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "/user/EmailReturnSuccess.html?key="
							+ key
							+ "\">邮箱验证</a></td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td width=\"525\" style=\"font-size: 12px; color: #333333; line-height: 22px; padding-top: 20px;\">如果上面按钮不能点击或点击后没有反应，您还可以将以下链接复制到浏览器地址栏中访问完成邮箱验证。</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td width=\"525\" style=\"font-size: 12px; padding-top: 5px; word-break: break-all; word-wrap: break-word;\"><a style=\"font-family: Arial; color: #22aaff;\" target=\"_blank\" href=\""
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "/user/EmailReturnSuccess.html?key="
							+ key
							+ "\">"
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "/user/EmailReturnSuccess.html?key="
							+ key
							+ "</a></td>"
							+ "</tr>"
							+ "</tbody></table>"
							+ "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"margin-top: 60px;\">"
							+ "<tbody><tr>"
							+ "<td style=\"font-size: 12px; color: #777777; line-height: 22px; border-bottom: #22aaff 2px solid; padding-bottom: 8px; padding-left: 20px;\">此邮件由系统自动发出，请勿回复！</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td style=\"font-size: 12px; color: #333333; line-height: 22px; padding: 8px 20px 0;\">感谢您对"
							+ ApplicationListenerImpl.sysConfigureJson
									.getSaitName()
							+ "（<a style=\"color: #22aaff; font-family: Arial;\" target=\"_blank\" href=\""
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "\">"
							+ ApplicationListenerImpl.sysConfigureJson
									.getWwwUrl()
							+ "</a>）的支持，祝您好运！</td>"
							+ "</tr>"
							+

							"</tbody></table>"
							+ "</td>"
							+ "</tr>"
							+ "</tbody></table>"
							+ "<table width=\"600\" cellspacing=\"0\" cellpadding=\"0\">"
							+ "<tbody><tr>"
							+ "<td align=\"center\" style=\"font-size:12px; color:#bbbbbb; padding-top:10px;\">"
							+ ApplicationListenerImpl.sysConfigureJson
									.getIcp()
							+ "</td>"
							+ "</tr>"
							+ "</tbody></table>";
					if (AliyunOcsSampleHelp.getIMemcachedCache().get(
							MD5Util.encode(this.userJSON)) == null) {
						boolean flag = EmailUtil.sendEmail(
								ApplicationListenerImpl.sysConfigureJson
										.getMailName(),
								ApplicationListenerImpl.sysConfigureJson
										.getMailPwd(),
								this.userJSON,
								ApplicationListenerImpl.sysConfigureJson
										.getSaitName() + "验证新绑定邮箱", html);
						if (flag) {
							this.user.setMail(this.userJSON);
							this.userService.add(this.user);
							AliyunOcsSampleHelp.getIMemcachedCache().set(
									MD5Util.encode(this.userJSON), 120,
									this.userJSON);
							Struts2Utils.renderText("0", new String[0]);
						} else {
							Struts2Utils.renderText("false", new String[0]);
						}
					} else {
						Struts2Utils.renderText("3", new String[0]);
					}
				}
			}
		}
	}

	public String EmailSendSuccess() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "EmailSendSuccess";
				}
			}
		}
		return "login_index";
	}

	public String EmailReturnSuccess() throws UnsupportedEncodingException {
		if (StringUtil.isNotBlank(this.key)) {
			this.key = this.key.substring(64, this.key.length());
			this.userJSON = Base64.getDecode(this.key);
			if (StringUtil.isNotBlank(this.userJSON)) {
				this.user = this.userService.userByName(this.userJSON);
				if (this.user != null) {
					this.user.setMailCheck("0");
					this.userService.add(this.user);
					return "EmailReturnSuccess";
				}
			}
		}
		return null;
	}

	public String isCheckName() {
		this.user = this.userService.isCheckName(this.userName);
		if (this.user == null) {
			Struts2Utils.renderText("true", new String[0]);
		} else {
			Struts2Utils.renderText("false", new String[0]);
		}
		return null;
	}

	public void updateUser() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					if (this.user != null) {
						try {
							JSONObject object = JSONObject
									.fromObject(this.userJSON);
							if (this.user.getUserName() == null) {
								Integer m = this.user.getCommissionPoints();
								m = Integer
										.valueOf(m.intValue()
												+ ApplicationListenerImpl.sysConfigureJson
														.getUserData()
														.intValue());
								this.user.setCommissionPoints(m);
								this.commissionpoints = new Commissionpoints();
								this.commissionpoints.setDate(DateUtil
										.DateTimeToStr(new Date()));
								this.commissionpoints
										.setDetailed("完善会员资料获得"
												+ ApplicationListenerImpl.sysConfigureJson
														.getUserData()
												+ "积分");
								this.commissionpoints
										.setPay("+"
												+ ApplicationListenerImpl.sysConfigureJson
														.getUserData());
								this.commissionpoints.setToUserId(Integer
										.valueOf(Integer
												.parseInt(this.userId)));
								this.commissionpointsService
										.add(this.commissionpoints);
							}
							this.user.setUserName(htmlFilter.filter(object
									.getString("userName")));
							String mobilePhone = object
									.getString("mobilePhone");
							if (!mobilePhone.equals("undefined")) {
								this.user.setMobilePhone(mobilePhone);
							}
							String qq = object.getString("qq");
							if (!qq.equals("undefined")) {
								qq = htmlFilter.filter(qq);
								this.user.setQq(qq);
							}
							String userSign = object.getString("userSign");
							if ((!userSign.equals("undefined"))
									&& (!userSign.equals(""))) {
								userSign = htmlFilter.filter(userSign);
								this.user.setSignature(userSign);
							}
							this.userService.add(this.user);
							Struts2Utils.renderJson(this.user,
									new String[0]);
						} catch (Exception e) {
							e.printStackTrace();
							Struts2Utils.renderText("false", new String[0]);
						}
					} else {
						Struts2Utils.renderText("false", new String[0]);
					}
				}
			}
		}
	}

	public void isUserNameExists() {
		User user = this.userService.isUserName(this.id, this.userId);
		if (user == null) {
			Struts2Utils.renderText("true", new String[0]);
		} else {
			Struts2Utils.renderText("false", new String[0]);
		}
	}

	public String UserPhoto() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "UserPhoto";
				}
			}
		}
		return "login_index";
	}

	public String updateFaceFile() throws Exception {
		try {
			this.myFileFileName = this.myFileFileName.substring(
					this.myFileFileName.lastIndexOf("."),
					this.myFileFileName.length());
			this.imageFileName = (this.userId + "_" + new Date().getTime() + this.myFileFileName);
			String filePath = ServletActionContext.getServletContext()
					.getRealPath("/uploadImages") + "/" + this.imageFileName;
			File imageFile = new File(filePath);
			if (this.myFile != null) {
				copy(this.myFile, imageFile);

				CutImages.scale2(filePath, filePath, 300, 300, true);
				Struts2Utils.renderText("/uploadImages/" + this.imageFileName,
						new String[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateFace() throws IOException {
		this.request = Struts2Utils.getRequest();
		this.response = Struts2Utils.getResponse();
		this.hidPicUrl = this.hidPicUrl.replace("/", "\\");
		this.hidPicUrl = this.hidPicUrl.substring(
				this.hidPicUrl.lastIndexOf("\\") + 1, this.hidPicUrl.length());
		String fileName = ServletActionContext.getServletContext().getRealPath(
				"uploadImages")
				+ "/" + this.hidPicUrl;
		String faceName = ServletActionContext.getServletContext().getRealPath(
				"faceImages")
				+ "/" + this.hidPicUrl;
		try {
			CutImages cutImages = new CutImages(this.x1, this.y1, this.w,
					this.h, fileName, faceName);
			cutImages.cut();
			faceName = faceName.substring(faceName.lastIndexOf("ROOT") + 4,
					faceName.length()).replace("\\", "/");
			this.user = ((User) this.userService.findById(this.userId));
			this.user.setFaceImg(faceName);
			this.userService.add(this.user);
			Cookie cookie3 = new Cookie("face", URLEncoder.encode(
					this.user.getFaceImg(), "UTF-8"));
			cookie3.setMaxAge(-1);
			cookie3.setPath("/");
			cookie3.setDomain(ApplicationListenerImpl.sysConfigureJson
					.getDomain());
			this.response.addCookie(cookie3);
			Struts2Utils.renderText("success", new String[0]);
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderText("false", new String[0]);
		}
	}

	public String UpdatePassWord() {
		this.request = Struts2Utils.getRequest();
		Cookie[] cookies = this.request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("userId")) {
				this.userId = cookie.getValue();
				if ((this.userId != null) && (!this.userId.equals(""))) {
					this.user = ((User) this.userService
							.findById(this.userId));
					return "UpdatePassWord";
				}
			}
		}
		return "login_index";
	}

	public void updatePwd() {
		if (StringUtil.isNotBlank(this.userId)) {
			try {
				this.user = ((User) this.userService.findById(this.userId));
				if (this.user != null) {
					String password = MessageDigestUtils.sha256Hex(URLDecoder.decode(this.userJSON, "utf-8"));
					String pwd = MessageDigestUtils.sha256Hex(URLDecoder.decode(this.id, "utf-8"));
					if (!this.user.getUserPwd().equals(pwd)) {
						Struts2Utils.renderText("pwdError", new String[0]);
					} else {
						this.user.setUserPwd(password);
						this.userService.add(this.user);
						Struts2Utils.renderText("success", new String[0]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getForward() {
		return this.forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public List<ProductJSON> getProductList() {
		return this.productList;
	}

	public void setProductList(List<ProductJSON> productList) {
		this.productList = productList;
	}

	public List<ProductJSON> getNewDateList() {
		return this.newDateList;
	}

	public void setNewDateList(List<ProductJSON> newDateList) {
		this.newDateList = newDateList;
	}

	public ProductJSON getProductJSON() {
		return this.productJSON;
	}

	public void setProductJSON(ProductJSON productJSON) {
		this.productJSON = productJSON;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Spellbuyproduct getSpellbuyproduct() {
		return this.spellbuyproduct;
	}

	public void setSpellbuyproduct(Spellbuyproduct spellbuyproduct) {
		this.spellbuyproduct = spellbuyproduct;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getResultCount() {
		return this.resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public Spellbuyrecord getSpellbuyrecord() {
		return this.spellbuyrecord;
	}

	public void setSpellbuyrecord(Spellbuyrecord spellbuyrecord) {
		this.spellbuyrecord = spellbuyrecord;
	}

	public BuyHistoryJSON getBuyHistoryJSON() {
		return this.buyHistoryJSON;
	}

	public void setBuyHistoryJSON(BuyHistoryJSON buyHistoryJSON) {
		this.buyHistoryJSON = buyHistoryJSON;
	}

	public List<BuyHistoryJSON> getBuyHistoryJSONList() {
		return this.buyHistoryJSONList;
	}

	public void setBuyHistoryJSONList(List<BuyHistoryJSON> buyHistoryJSONList) {
		this.buyHistoryJSONList = buyHistoryJSONList;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSelectTime() {
		return this.selectTime;
	}

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}

	public Randomnumber getRandomnumber() {
		return this.randomnumber;
	}

	public void setRandomnumber(Randomnumber randomnumber) {
		this.randomnumber = randomnumber;
	}

	public Latestlottery getLatestlottery() {
		return this.latestlottery;
	}

	public void setLatestlottery(Latestlottery latestlottery) {
		this.latestlottery = latestlottery;
	}

	public Shareinfo getShareinfo() {
		return this.shareinfo;
	}

	public void setShareinfo(Shareinfo shareinfo) {
		this.shareinfo = shareinfo;
	}

	public ShareJSON getShareJSON() {
		return this.shareJSON;
	}

	public void setShareJSON(ShareJSON shareJSON) {
		this.shareJSON = shareJSON;
	}

	public List<ShareJSON> getShareJSONList() {
		return this.shareJSONList;
	}

	public void setShareJSONList(List<ShareJSON> shareJSONList) {
		this.shareJSONList = shareJSONList;
	}

	public List<Userbyaddress> getUserbyaddressList() {
		return this.userbyaddressList;
	}

	public void setUserbyaddressList(List<Userbyaddress> userbyaddressList) {
		this.userbyaddressList = userbyaddressList;
	}

	public Userbyaddress getUserbyaddress() {
		return this.userbyaddress;
	}

	public void setUserbyaddress(Userbyaddress userbyaddress) {
		this.userbyaddress = userbyaddress;
	}

	public String getUserJSON() {
		return this.userJSON;
	}

	public List<RandomNumberJSON> getRandomNumberJSONList() {
		return this.randomNumberJSONList;
	}

	public void setRandomNumberJSONList(
			List<RandomNumberJSON> randomNumberJSONList) {
		this.randomNumberJSONList = randomNumberJSONList;
	}

	public RandomNumberJSON getRandomNumberJSON() {
		return this.randomNumberJSON;
	}

	public void setRandomNumberJSON(RandomNumberJSON randomNumberJSON) {
		this.randomNumberJSON = randomNumberJSON;
	}

	public void setUserJSON(String userJSON) {
		this.userJSON = userJSON;
	}

	public File getMyFile() {
		return this.myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileFileName() {
		return this.myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public String getMyFileContentType() {
		return this.myFileContentType;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public int getX1() {
		return this.x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return this.y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public String getHidPicUrl() {
		return this.hidPicUrl;
	}

	public void setHidPicUrl(String hidPicUrl) {
		this.hidPicUrl = hidPicUrl;
	}

	public int getW() {
		return this.w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return this.h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public List<News> getNewsList() {
		return this.newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public List<SProvince> getSProvinceList() {
		return this.sProvinceList;
	}

	public void setSProvinceList(List<SProvince> provinceList) {
		this.sProvinceList = provinceList;
	}

	public List<SCity> getSCityList() {
		return this.sCityList;
	}

	public void setSCityList(List<SCity> cityList) {
		this.sCityList = cityList;
	}

	public List<SDistrict> getSDistrictList() {
		return this.sDistrictList;
	}

	public void setSDistrictList(List<SDistrict> districtList) {
		this.sDistrictList = districtList;
	}

	public List<User> getUserList() {
		return this.userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Commissionquery> getCommissionqueryList() {
		return this.commissionqueryList;
	}

	public void setCommissionqueryList(List<Commissionquery> commissionqueryList) {
		this.commissionqueryList = commissionqueryList;
	}

	public List<Applymention> getApplymentionList() {
		return this.applymentionList;
	}

	public void setApplymentionList(List<Applymention> applymentionList) {
		this.applymentionList = applymentionList;
	}

	public Applymention getApplymention() {
		return this.applymention;
	}

	public void setApplymention(Applymention applymention) {
		this.applymention = applymention;
	}

	public Commissionquery getCommissionquery() {
		return this.commissionquery;
	}

	public void setCommissionquery(Commissionquery commissionquery) {
		this.commissionquery = commissionquery;
	}

	public List<CommissionqueryJSON> getCommissionqueryJSONList() {
		return this.commissionqueryJSONList;
	}

	public void setCommissionqueryJSONList(
			List<CommissionqueryJSON> commissionqueryJSONList) {
		this.commissionqueryJSONList = commissionqueryJSONList;
	}

	public CommissionqueryJSON getCommissionqueryJSON() {
		return this.commissionqueryJSON;
	}

	public void setCommissionqueryJSON(CommissionqueryJSON commissionqueryJSON) {
		this.commissionqueryJSON = commissionqueryJSON;
	}

	public List<Commissionpoints> getCommissionpointsList() {
		return this.commissionpointsList;
	}

	public void setCommissionpointsList(
			List<Commissionpoints> commissionpointsList) {
		this.commissionpointsList = commissionpointsList;
	}

	public Commissionpoints getCommissionpoints() {
		return this.commissionpoints;
	}

	public void setCommissionpoints(Commissionpoints commissionpoints) {
		this.commissionpoints = commissionpoints;
	}

	public List<Orderdetail> getOrderdetailList() {
		return this.orderdetailList;
	}

	public void setOrderdetailList(List<Orderdetail> orderdetailList) {
		this.orderdetailList = orderdetailList;
	}

	public Orderdetailaddress getOrderdetailaddress() {
		return this.orderdetailaddress;
	}

	public void setOrderdetailaddress(Orderdetailaddress orderdetailaddress) {
		this.orderdetailaddress = orderdetailaddress;
	}

	public Orderdetail getOrderdetail() {
		return this.orderdetail;
	}

	public void setOrderdetail(Orderdetail orderdetail) {
		this.orderdetail = orderdetail;
	}

	public String getPostTitle() {
		return this.postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return this.postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostAllPic() {
		return this.postAllPic;
	}

	public void setPostAllPic(String postAllPic) {
		this.postAllPic = postAllPic;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(String timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getLineLink() {
		return lineLink;
	}

	public void setLineLink(String lineLink) {
		this.lineLink = lineLink;
	}

	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public List<Shareimage> getShareimageList() {
		return shareimageList;
	}

	public void setShareimageList(List<Shareimage> shareimageList) {
		this.shareimageList = shareimageList;
	}
	
}
