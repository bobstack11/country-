$(function() {
	var a = $("#divGoodsNav");
	var c = $("#divGoodsLoading");
	var h = $("#btnLoadMore");
	var d = null;
	var e = 10;
	var g = 0;
	var b = $("#hidOrderFlag").val();
	var f = {
		sortID : "",
		brandID : "",
		orderFlag : b,
		FIdx : 1,
		EIdx : e,
		isCount : 1
	};
	var j = false;
	var i = function() {
		var m = function(r) {
			if (r && r.stopPropagation) {
				r.stopPropagation()
			} else {
				window.event.cancelBubble = true
			}
		};
		var k = false;
		var l = false;
		a
				.children("li")
				.each(
						function() {
							var s = $(this);
							var r = s.attr("order");
							if (r != "") {
								if (b == r) {
									s.addClass("current").siblings()
											.removeClass("current")
								}
								s
										.click(function() {
											s.addClass("current").siblings()
													.removeClass("current");
											r = s.attr("order");
											f.orderFlag = r;
											if (f.orderFlag == "priceAsc20") {
												s
														.attr("order",
																"price20")
														.html(
																'<a href="javascript:;">\u4ef7\u503c<em class="orange"></em><s></s><b></b></a>')
											} else {
												if (f.orderFlag == "price20") {
													s
															.attr("order",
																	"priceAsc20")
															.html(
																	'<a href="javascript:;">\u4ef7\u503c<em></em><s class="orange"></s><b></b></a>')
												} else {
													s
															.siblings(
																	'li[type="price"]')
															.attr("order",
																	"price20")
															.html(
																	'<a href="javascript:;">\u4ef7\u503c<em></em><s></s><b></b></a>')
												}
											}
											d.getInitPage()
										})
							} else {
								s
										.click(function(t) {
											m(t);
											if (k) {
												s.removeClass("gSort")
														.children("dl").css(
																"display",
																"none");
												k = false
											} else {
												k = true;
												s.addClass("gSort").children(
														"dl").show();
												if (!l) {
													s
															.children("dl")
															.children("dd")
															.each(
																	function() {
																		var u = $(this);
																		u
																				.click(function(
																						v) {
																					m(v);
																					f.sortID = u
																							.attr("type");
																					u
																							.addClass(
																									"sOrange")
																							.siblings()
																							.removeClass(
																									"sOrange");
																					u
																							.parent()
																							.hide()
																							.parent()
																							.removeClass(
																									"gSort");
																					k = false;
																					s
																							.children(
																									"a")
																							.html(
																									u
																											.text()
																											+ '<s class="arrowUp"></s>');
																					d
																							.getInitPage()
																				})
																	});
													l = true
												}
											}
										})
							}
						});
		$("#loadingPicBlock").click(
				function() {
					a.children("li").last().removeClass("gSort").children("dl")
							.css("display", "none");
					k = false
				});
		var n = function(r) {
			$.PageDialog.fail(r)
		};
		var q = function(r) {
			$.PageDialog.ok("<s></s>" + r)
		};
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
								q("\u6dfb\u52a0\u6210\u529f")
							}
						}
					})
		};
		$("div.goodsList").find("div.pRate").each(function() {
			var r = $(this);
			r.children("a.add").each(function() {
				$(this).click(function(s) {
					m(s);
					p($(this).attr("codeid"), 1)
				})
			})
		});
		var o = function() {
			var r = function() {
				return "id=" + f.orderFlag + "&tId=" + f.sortID + "&pageSize="
						+ e + "&pageNo=" + f.FIdx + "&FIdx=" + f.FIdx
						+ "&EIdx=" + f.EIdx + "&isCount=" + f.isCount
			};
			var s = function() {
				c.show();
				$
						.ajax({
							url : "/list/goodsList.action",
							data : r(),
							success : function(data) {
								if (data != "") {
									var u = data;
									if (f.isCount == 1) {
										g = u.length
									}
									var y = u.length;
									var z = 0;
									var B = 0;
									var t = 0;
									var C = 0;
									for (var w = 0; w < y; w++) {
										var x = '<ul id="'
												+ u[w].productId
												+ '"><li><span class="z-Limg"><img src="'
												+ $img + u[w].headImage + '">';
										if (u[w].productStyle == "goods_xs") {
											x += '<div class="pTitle pLimitedTime">\u9650\u65f6</div>'
										} else {
											switch (u[w].productStyle) {
											case "goods_xp":
												x += '<div class="pTitle pNewProducts">\u65b0\u54c1</div>';
												break;
											case "goods_tj":
												x += '<div class="pTitle pNewProducts">\u63a8\u8350</div>';
												break;
											case "goods_rq":
												x += '<div class="pTitle pPopularity">\u4eba\u6c14</div>';
												break
											}
										}
										x += '</span><div class="goodsListR"><h2>(\u7b2c'
												+ u[w].productPeriod
												+ "\u671f)"
												+ u[w].productName
												+ "</h2>";
										z = parseInt(u[w].currentBuyCount);
										B = parseInt(u[w].productPrice);
										t = parseInt(B - z);
										C = parseInt(z * 100) / B;
										C = z > 0 && C < 1 ? 1 : C;
										x += '<div class="pRate"><div class="Progress-bar"><p class="u-progress">'
												+ (z > 0 ? '<span style="width:'
														+ C
														+ '%;" class="pgbar"><span class="pging"></span></span>'
														: "")
												+ '</p><ul class="Pro-bar-li"><li class="P-bar01"><em>'
												+ z
												+ '</em>\u5df2\u53c2\u4e0e</li><li class="P-bar02"><em>'
												+ B
												+ '</em>\u603b\u9700\u4eba\u6b21</li><li class="P-bar03"><em>'
												+ t
												+ '</em>\u5269\u4f59</li></ul></div><a href="javascript:;" codeid="'
												+ u[w].productId
												+ '" class="add '
												+ (t == 0 ? "gray" : "")
												+ '"><s></s></a></div></div></li></ul>';
										var A = $(x);
										A
												.click(
														function() {
															location.href = "/products/"
																	+ $(this)
																			.attr(
																					"id")
																	+ ".html"
														})
												.find("div.pRate > a")
												.each(
														function() {
															var D = $(this);
															D
																	.click(function(
																			E) {
																		m(E);
																		if (!D
																				.hasClass("gray")) {
																			p(
																					D
																							.attr("codeid"),
																					1)
																		}
																	})
														});
										c.before(A)
									}
									if (g >= e) {
										j = false;
										h.show()
									}
									loadImgFun(0)
								} else {
									if (f.FIdx == 1) {
										c.before(Gobal.NoneHtml)
									}
								}
								c.hide()
							}
						})
			};
			this.getInitPage = function() {
				f.FIdx = 1;
				f.EIdx = e;
				f.isCount = 1;
				g = 0;
				c.prevAll().remove();
				h.hide();
				s()
			};
			this.getFirstPage = function() {
				s()
			};
			this.getNextPage = function() {
				f.FIdx += 1;
				f.EIdx += e;
				s()
			}
		};
		h.click(function() {
			if (!j) {
				j = true;
				h.hide();
				d.getNextPage()
			}
		});
		d = new o();
		d.getFirstPage();
		
		function loadData()
		{ 
		    totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度 
		    console.log("总高度: "+totalheight+",页面的文档高度 :"+$(document).height()); 
		    if ($(document).height() <= totalheight)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
		    { 
		        //加载数据
		    	if (!j) {
					j = true;
					h.hide();
					d.getNextPage();
				}
		    } 
		} 

		$(window).scroll( function() { 
		    //console.log("滚动条到顶部的垂直高度: "+$(document).scrollTop()); 
		    //console.log("页面的文档高度 ："+$(document).height());
		    //console.log('浏览器的高度：'+$(window).height());
		    loadData();
		}); 
	};
	$.getScript("/js/pagedialog.js", i)
});
