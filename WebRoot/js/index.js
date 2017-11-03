$(function() {
	var a = function() {
		/*var c = "_downApp";
		var m = $.cookie(c);
		if (m == null || m == "") {
			if ($("#hidAppType").val() == "0") {
				$("#downLink").attr("href",
						"http://app.1yyg.com/down1yygapk.do");
				$("#divDownApp").show().find("a.close").click(function() {
					$(this).parent().hide();
					$.cookie(c, "1", {
						expires : 7,
						path : "/"
					})
				})
			}
		}*/
		var CastMoney =function (a) {
			a = Math.round(a * 1000) / 1000;
			a = Math.round(a * 100) / 100;
			if (/^\d+$/.test(a)) {
				return a + ".00"
			}
			if (/^\d+\.\d$/.test(a)) {
				return a + "0"
			}
			return a
		}
		var j = new Object();
		var k = function() {
			var e = $indexImg;
			var c = $("<ul/>");
			c.addClass("slides");
			var h = "";
			for (var i = 0; i < e.length; i++) {
				var d = '<li style="background-color: rgb(f, f, f); width: 1903px; float: left; display: block;" class="clone"><a href="'
						+ e[i].proUrl
						+ '"><img width="640" height="110"  src="'
						+ $img
						+ e[i].proImg + '"></a></li>';
				c.append(d)
			}
			var g = $("#sliderBox");
			g.append(c).flexslider()
		};
		$.getScript("/js/flexslider.js", k);
		/*var k = function() {
			var w = $.cookie("_indexads");
			if (w != null && typeof (w) != "undefined") {
				var v = $(w);
				v.addClass("slides");
				$("#sliderBox").empty().append(v).flexslider()
			} else {
				GetJPData("http://poster.1yyg.com", "getbysortid", "ID=15",
						function(B) {
							if (B.state == 0) {
								var A = B.listItems;
								var z = "<ul>";
								for (var y = 0; y < A.length; y++) {
									z += '<li style="background-color:'
											+ A[y].alt
											+ ';"><a href="'
											+ A[y].url.replace("m.1yyg.com",
													"weixin.1yyg.com")
											+ '"><img src="' + A[y].src
											+ '" alt="" width="' + A[y].width
											+ '" height="' + A[y].height
											+ '" /></a></li>'
								}
								z += "</ul>";
								var x = $(z);
								x.addClass("slides");
								$("#sliderBox").empty().append(x).flexslider();
								$.cookie("_indexads", z, {
									domain : "1yyg.com",
									path : "/"
								})
							}
						})
			}
		};
		Base.getScript("/js/flexslider.js?v=151105", k);*/
		var f = function(v) {
			$.PageDialog('<div class="Prompt">' + v + "</div>", {
				W : 150,
				H : 45,
				close : false,
				autoClose : true,
				submit : function() {
					location.reload()
				}
			})
		};
		var u = function(v) {
			$.PageDialog.fail(v)
		};
		var g = function(v) {
			$.PageDialog.ok("<s></s>" + v)
		};
		var q = $("#goodsNav");
		var b = q.offset().top;
		var r = b + q.height();
		$("#ulOrder li").each(function() {
			$(this).click(function() {
				p();
				$(this).addClass("current").siblings().removeClass("current");
				i.orderFlag = $(this).attr("order");
				h.initPage();
				if (i.orderFlag == 'priceAsc20') {
					$(this).attr("order", "price20")
				} else {
					if (i.orderFlag == 'price20') {
						$(this).attr("order", "priceAsc20")
					}
				}
			})
		});
		var p = function() {
			$("body").attr("style", "");
			t.removeClass("current").next("div.select-total").hide();
			n = false
		};
		var n = false;
		var t = $("#divSort");
		t.click(function(v) {
			stopBubble(v);
			if (n) {
				p()
			} else {
				$("body").attr("style", "overflow:hidden;");
				t.addClass("current").next("div.select-total").show();
				n = true
			}
		});
		t
				.next("div.select-total")
				.find("li")
				.each(
						function() {
							$(this)
									.click(
											function() {
												i.sortID = $(this).attr("sortID");
												h.initPage();
												/*location.href = "/list/m1.html?id="
														+ i.orderFlag
														+ "&tId=" + i.sortID*/
											})
						});
		$("body").click(function() {
			p()
		});
		var h = null;
		var s = 60;
		var d = 0;
		var hidOrderFlag = $("#hidOrderFlag").val();
		var i = {
			sortID : "",
			orderFlag : hidOrderFlag,
			FIdx : 1,
			EIdx : s,
			isCount : 1
		};
		var l = $("#ulGoodsList");
		var o = $("div.loading");
		var e = function() {
			var v = function() {
				return "id=" + i.orderFlag + "&tId=" + i.sortID + "&pageSize="
				+ s + "&pageNo=" + i.FIdx + "&FIdx=" + i.FIdx
				+ "&EIdx=" + i.EIdx + "&isCount=" + i.isCount
				/*return "sortID=" + i.sortID + "&orderFlag=" + i.orderFlag
						+ "&FIdx=" + i.FIdx + "&EIdx=" + i.EIdx + "&isCount="
						+ i.isCount*/
			};
			/*var w = function(B, A, z, y) {
				B.addClass("add");
				GetJPData("http://weixin.1yyg.com", "addShopCart", "codeid="
						+ A + "&shopNum=" + z, function(C) {
					if (C.code == 0) {
						addNumToCartFun(C.num);
						if (typeof (y) == "function") {
							y()
						} else {
							g("添加成功")
						}
					} else {
						if (C.code == 1) {
							f("已满员")
						} else {
							if (C.code == 4) {
								u("您参与人次已达上限！")
							} else {
								u("添加失败，请重试")
							}
						}
					}
					B.removeClass("add")
				})
			};*/
			var x = function() {
				$.ajax({
					url : "/list/goodsList.action",
					data : v(),
					success : function(D) {
						if (D != "") {
							var B = D;
							if (i.isCount == 1) {
								d = B.length;
								i.isCount = 0
							}
							var E = B.length;
							var F = 0;
							var H = 0;
							var y = 0;
							var I = 0;
							var z = "";
							for (var C = 0; C < E; C++) {
								var A = B[C];
								F = parseInt(A.currentBuyCount);
								H = parseInt(A.productPrice);
								y = parseInt(H - F);
								I = parseInt(F * 100) / H;
								I = F > 0 && I < 1 ? 1 : I;
								z = '<li id="'
										+ A.productId
										+ '"><a href="javascript:;" class="g-pic"><img src="'
										+ $img + A.headImage+ '"'
										+ 'width="136" height="136" />'
										+ (A.goodsTag == 10 ? '<div class="pTitle pPurchase">限购</div>'
												: "")
										+ '</a><p class="g-name">(第'
										+ A.productPeriod
										+ "云)"
										+ A.productName
										+ '</p><ins class="gray9">价值:￥'
										+ CastMoney(A.productPrice)
										+ '</ins><div class="Progress-bar"><p class="u-progress"><span class="pgbar" style="width: '
										+ I
										+ '%;"><span class="pging"></span></span></p><div class="gRate'
										+ (y == 0 ? " unAdd" : "")
										+ '" codeid="'
										+ A.productId
										+ '"><a href="javascript:;"><s></s></a></div></div>'
									
										+ '</li>';
								var G = $(z);
								G.click(
										function() {
											location.href = "/products/"
													+ $(this).attr("id")
													+ ".html"
										}).find("div.gRate").click(
										function(K) {
											stopBubble(K);
											var J = $(this);
											if (!J.hasClass("unAdd")) {
												p(J.attr("codeid"), 1);
												/*w(J, J.attr("codeid"), 1)*/
											}
										});
								var p = function(s, r) {
									$
											.ajax({
												url : "/list/isStatus.action",
												data : "id=" + s,
												success : function(data) {
													if (data == "false") {
														n("\u672c\u671f\u5df2\u6ee1\u5458")
													} else {
														var count = parseInt(data);
														var codeid = s;
														var json = getCookie("products");
														if (json == null || json == "") {
															var val = "[{'pId':" + codeid + ",'num':"
																	+ r + "}]";
															SetCookieByExpires("products", val, 1)
														} else {
															var flag = false;
															json = eval("(" + json + ")");
															$
																	.each(
																			json,
																			function(n, value) {
																				if (codeid == value.pId) {
																					value.num = (parseInt(value.num) + parseInt(r));
																					if ((parseInt(value.num) + parseInt(r)) > count) {
																						value.num = count
																					}
																					flag = true
																				}
																			});
															if (!flag) {
																json.push({
																	pId : +codeid,
																	num : r
																})
															}
															json = JSON.stringify(json);
															SetCookieByExpires("products", json, 1)
														}
														addNumToCartFun(data);
														g("\u6dfb\u52a0\u6210\u529f")
													}
												}
											})
								};
								G
										.find("a.buy-btn")
										.click(
												function(K) {
													stopBubble(K);
													var J = $(this);
													if (!J.hasClass("unAdd")) {
														p(J.attr("codeid"), 1);
														/*w(
																J,
																J.attr("codeid"),
																1,
																function() {
																	location.href = "/mycart/index.html"
																})*/
													}
												});
								l.append(G)
							}
							if (i.EIdx < d) {
								_IsLoading = false
							} else {
								_IsLoading = true;
								o.hide()
							}
							loadImgFun(0)
						} else {
							o.hide();
							if (i.FIdx == 1) {
								_IsLoading = true;
								l.append(Gobal.NoneHtml)
							}
						}
					}
				})
			};
			this.getNextPage = function() {
				i.FIdx = i.FIdx + s;
				i.EIdx = i.EIdx + s;
				x()
			};
			this.initPage = function() {
				i.FIdx = 1;
				i.EIdx = s;
				i.isCount = 1;
				l.empty();
				x()
			}
		};
		h = new e();
		h.initPage();
		scrollForLoadData(h.getNextPage)
	};
	Base.getScript("/js/pagedialog.js?v=151104", a);
	
	var g = ",";
	var flag = false;
	var l = function() {
		$.ajax({
			url : "/lottery/lotteryproductutilList.html",
			data : "json=" + new Date().getTime(),
			success : function(d) {
				if (d != null) {
					c(d)
				}
				setTimeout(l, 5000)
			}
		});
		var c = function(e) {
			var d = function(t) {
				var s = $("#divLottery");
				var o = t.length > 4 ? 4 : t.length;
				for (var q = o - 1; q > -1; q--) {
					var r = t[q];
					if (g.indexOf("," + r.lotteryProductId + ",") < 0) {
						g += r.lotteryProductId + ",";
						var p = '<li id="' + r.lotteryProductId + '">';
						p += '<a href="javascript:;" class="u-lott-pic">';
						p += '<img alt="" src="'+$img+r.lotteryProductImg + '" border="0">';
						/*if (r.codeType == 3) {
							p += '<div class="pPurchase">限购</div>'
						}*/
						p += "</a>";
						p += "<p><em>揭晓倒计时</em><cite><b>00</b>:<b>00</b>:<b><i>0</i><i>0</i></b></cite></p>";
						p += "</li>";
						var u = $(p);
						u.click(function() {
							location.href = "/products/" + $(this).attr("id") + ".html";
						});
						s.children("li:last").remove();
						s.prepend(u);
						console.log(r.lotteryProductEndDate)
						console.log(parseInt(r.lotteryProductEndDate))
						u.StartTimeOut(r.lotteryProductId, parseInt(r.lotteryProductEndDate))
					}
				}
				/*var h = $("#divLottery");
				for (var j = g.length - 1; j > -1; j--) {
					var i = g[j];
					if (o.indexOf("," + i.lotteryProductId + ",") < 0) {
						o += i.lotteryProductId + ",";
						var f = $('<div class="m-lott-conduct" id="'
								+ i.lotteryProductId
								+ '"><p class="z-lott-tt">(\u7b2c'
								+ i.lotteryProductPeriod
								+ "\u671f)"
								+ i.lotteryProductName
								+ '<b class="z-arrow"></b><span class="z-lott-time">\u63ed\u6653\u5012\u8ba1\u65f6<span class="minute">00</span>:<span class="second">00</span>:<span class="millisecond">0</span><span class="last">0</span></span></p></div>');
						f.click(function() {
							location.href = "/products/"
									+ $(this).attr("id") + ".html"
						});
						h.prepend(f);
						f.StartTimeOut(i.lotteryProductId,
								parseInt(i.lotteryProductEndDate))
					}
				}*/
			};
			if (flag) {
				d(e)
			} else {
				$.getScript("/js/indexlottery.js", function() {
					flag = true;
					d(e)
				})
			}
		}
	};
	l();
});