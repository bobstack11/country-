$(function() {
	var b = function() {
		var E = $("#divPost");
		var y = $("#divUnPost");
		var A = E.children("ul");
		var t = y.children("ul");
		var z = $("#btnPost");
		var D = $("#btnUnPost");
		var H = $("#divPostLoad");
		var I = $("#btnLoadMore");
		var a = $("#userId").val();
		var v = false;
		var F = 10;
		var C = null;
		var w = null;
		var B = 1;
		var x = false;
		var J = false;
		var G = false;
		var K = function() {
			var d = 0;
			var f = {
				FIdx : 1,
				EIdx : F,
				isCount : 1
			};
			var e = function() {
				return "pageNo=" + f.FIdx + "&pageSize=" + f.EIdx + "&isCount="
						+ f.isCount + "&userId=" + a
			};
			var c = function() {
				H.show();
				$
						.ajax({
							url : "/user/PostSingleListAjaxPage.action",
							data : e(),
							success : function(i) {
								if (i.length > 0) {
									if (f.isCount == 1) {
										d = i.length;
										f.isCount = 0;
										z.html("\u5df2\u6652\u5355(" + d + ")")
									}
									var k = i;
									var m = k.length;
									var h = "";
									for (var l = 0; l < m; l++) {
										var j = k[l].shareStatus;
										var g = j == 2 ? "/user/PostSingleDetail-"
												+ k[l].productId + ".html"
												: "#";
										h += '<li class="'
												+ (l + 1 == m ? "bornone" : "")
												+ '" onclick="location.href=\''
												+ g
												+ '\'"><a class="fl z-Limg" href="'
												+ g
												+ '"><img src="'
												+ $img
												+ "/UserPost/Small/"
												+ k[l].productImg
												+ '" border=0 alt="" width=40></a><div class="u-sgl-r"><p class="z-sgl-tt"><a href="'
												+ g
												+ '" class="gray6">'
												+ k[l].productTitle
												+ '</a></p><p class="z-sgl-info gray9">'
												+ k[l].productName
												+ "</p><p>\u6652\u5355\u65f6\u95f4\uff1a"
												+ k[l].buyTime + "</p>";
										if (j == 0) {
											h += '<p>\u72b6\u6001\uff1a<span class="">\u6b63\u5728\u5ba1\u6838</span></p>'
										} else {
											if (j == 1) {
												h += '<p>\u72b6\u6001\uff1a<span class="red">\u5ba1\u6838\u672a\u901a\u8fc7</span> </p><p><a href="/user/PostSingleEdit-'
														+ k[l].productId
														+ '.html" class="z-sgl-btn">\u4fee\u6539</a></p>'
											} else {
												if (j == 2) {
													h += '<p>\u72b6\u6001\uff1a<span class="green">\u5ba1\u6838\u901a\u8fc7</span></p>'
												}
											}
										}
										h += '</div><b class="z-arrow"></b></li>'
									}
									if (f.FIdx > 1) {
										A.children("li").last().removeClass(
												"bornone")
									}
									A.append(h);
									if (f.EIdx >= d) {
										I.show()
									} else {
										J = true;
										I.hide()
									}
									loadImgFun()
								} else {
									if (f.FIdx == 1) {
										A.append(Gobal.NoneHtml);
										J = true
									}
								}
								H.hide();
								x = false
							}
						})
			};
			this.initPage = function() {
				c()
			};
			this.getNextPage = function() {
				f.FIdx += 1;
				f.EIdx = F;
				c()
			}
		};
		var u = function() {
			var d = 0;
			var f = {
				FIdx : 1,
				EIdx : F,
				isCount : 1
			};
			var e = function() {
				return "pageNo=" + f.FIdx + "&pageSize=" + f.EIdx + "&isCount="
						+ f.isCount + "&userId=" + a+ "&hidPicUrl=4&postAllPic=";;
			};
			var c = function() {
				H.show();
				$
						.ajax({
							url : "/user/OrderListAjaxPage.action",
							data : e(),
							success : function(l) {
								if (l.length > 0) {
									if (f.isCount == 1) {
										d = l.length
									}
									var h = l;
									var j = h.length;
									if (f.EIdx > 1) {
										t.children("li").last().removeClass(
												"bornone")
									}
									for (var g = 0; g < j; g++) {
										var i = '<li class="'
												+ (g + 1 == j ? "bornone" : "")
												+ '" id="'
												+ h[g].productId
												+ '"><a class="fl z-Limg" href="/lotteryDetail/'
												+ h[g].productId
												+ '.html"><img src="'
												+ $img
												+ h[g].productImg
												+ '"  border=0 alt=""></a><div class="u-sgl-r gray9"><p class="z-sgl-tt"><a href="/lotteryDetail/'
												+ h[g].productId
												+ '.html" class="gray6">(\u7b2c'
												+ h[g].productPeriod
												+ '\u4e91)'
												+ h[g].productName
												+ "</a></p><p>\u5e78\u8fd0\u4e91\u8d2d\u7801\uff1a"
												+ h[g].winId
												+ "</p><p>\u63ed\u6653\u65f6\u95f4\uff1a"
												+ h[g].winDate
												+ '</p>'
												+ '</div><b class="z-arrow"></b></li>';
										var k = $(i);
										k.click(function() {
													location.href = "/user/PostSingleAdd-"
															+ k.attr("id")
															+ ".html"
												});
										t.append(k)
									}
									if (f.EIdx >= d) {
										I.show()
									} else {
										G = true;
										I.hide()
									}
									loadImgFun(0)
								} else {
									if (f.FIdx == 1) {
										t.append(Gobal.NoneHtml);
										G = true;
										I.hide()
									}
								}
								H.hide();
								v = true;
								x = false
							}
						})
			};
			this.initPage = function() {
				c()
			};
			this.getNextPage = function() {
				f.FIdx += 1;
				f.EIdx = F;
				c()
			}
		};
		C = new K();
		w = new u();
		C.initPage();
		z.click(function() {
			B = 1;
			z.parent().addClass("z-sgl-crt");
			D.parent().removeClass("z-sgl-crt");
			E.show();
			y.hide();
			if (!J) {
				I.show()
			}
		});
		D.click(function() {
			B = 0;
			z.parent().removeClass("z-sgl-crt");
			D.parent().addClass("z-sgl-crt");
			I.hide();
			E.hide();
			y.show();
			if (!v) {
				w.initPage()
			}
		});
		I.click(function() {
			if (!x) {
				x = true;
				I.hide();
				if (B == 1 && !J) {
					C.getNextPage()
				} else {
					if (!G) {
						w.getNextPage()
					}
				}
			}
		})
	};
	$.getScript("/js/comm.js?v=130826", b)
});
