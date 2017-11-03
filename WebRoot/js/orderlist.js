$(function() {
	var b = function() {
		var q = $("#divOrderLoading");
		var j = $("#btnLoadMore");
		var m = $("#userId").val();
		var l = 0;
		var a = 10;
		var p = {
			FIdx : 1,
			EIdx : a,
			isCount : 1
		};
		var k = null;
		var n = false;
		var o = function() {
			var d = function() {
				return "pageNo=" + p.FIdx + "&pageSize=" + p.EIdx + "&isCount="
						+ p.isCount + "&userId=" + m
			};
			var c = function() {
				j.hide();
				q.show();
				$
						.ajax({
							url : "/user/OrderListAjaxPage.action",
							data : d(),
							success : function(h) {
								if (h.length > 0) {
									if (p.isCount == 1) {
										p.isCount = 0;
										l = h.length
									}
									var i = h;
									var r = i.length;
									var e = "";
									for (var f = 0; f < r; f++) {
										e += "<li onclick=\"location.href='/user/orderdetail-"
												+ i[f].productId
												+ '.html\'"><a class="fl z-Limg" href="/lotteryDetail/'
												+ i[f].productId
												+ '.html"><img src="'
												+ $img
												+ i[f].productImg
												+ '" border=0 alt=""/></a><div class="u-gds-r gray9"><p class="z-gds-tt"><a href="/lotteryDetail/'
												+ i[f].productId
												+ '.html" class="gray6">(\u7b2c'
												+ i[f].productPeriod
												+ "\u4e91)"
												+ i[f].productName
												+ "</a></p><p>幸运"
												+ $shortName
												+ '码：<em class="orange">'
												+ i[f].winId
												+ "</em></p><p>揭晓时间："
												+ i[f].winDate + "</p>";
										var g = parseInt(i[f].buyStatus);
										if (g == 1) {
											e += '<a href="/user/orderdetail-'
													+ i[f].productId
													+ '.html"  class="z-gds-btn">完善收货地址</a>'
										} else {
											if (g == 2) {
												e += '<a href="javascript:;" class="z-gds-btn z-gds-btnDis">正在备货</a>'
											} else {
												if (g == 3) {
													e += '<a href="javascript:;" class="z-gds-btn z-gds-btnDis">已发货</a><a  href="/user/orderdetail-'
															+ i[f].productId
															+ '.html" class="z-gds-btn">确认收货</a>'
												} else {
													if (g == 4) {
														e += '<a href="javascript:;" class="z-gds-btn z-gds-btnDis">已确认收货</a>'
													} else {
														if (g == 10) {
															if (parseInt(i[f].shareStatus) != -1) {
																e += '<a href="javascript:;" class="z-gds-btn z-gds-btnDis">订单已完成</a><a href="javascript:void(0);" class="z-gds-btn z-gds-btnDis">已晒单</a>'
															} else {
																e += '<a href="javascript:;" class="z-gds-btn z-gds-btnDis">订单已完成</a><a href="/user/postSingle-'
																		+ i[f].productId
																		+ '..html" class="z-gds-btn">去晒单</a>'
															}
														} else {
															if (g == 11) {
																e += '<a href="javascript:;" class="z-gds-btn z-gds-btnDis">订单已取消</a>'
															}
														}
													}
												}
											}
										}
										e += '</div><b class="z-arrow"></b></li>'
									}
									if (p.FIdx > 1) {
										q.prev().removeClass("bornone")
									}
									q.before(e).prev().addClass("bornone");
									if (p.EIdx >= l) {
										n = false;
										j.show()
									}
									loadImgFun()
								} else {
									if (h.code == 10) {
										location.reload()
									} else {
										if (p.FIdx == 1) {
											q.before(Gobal.NoneHtml)
										}
									}
								}
								q.hide()
							}
						})
			};
			this.getInitPage = function() {
				c()
			};
			this.getNextPage = function() {
				p.FIdx += 1;
				p.EIdx = a;
				c()
			}
		};
		j.click(function() {
			if (!n) {
				n = true;
				k.getNextPage()
			}
		}).show();
		k = new o();
		k.getInitPage()
	};
	$.getScript("/js/comm.js?v=130826", b)
});
