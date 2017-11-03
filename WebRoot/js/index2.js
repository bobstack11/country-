$(function() {
	var b = function() {
		var s = $("#divTimerItems");
		var p = s.find("div[name=timerItem]");
		if (p.length > 0) {
			var t = function() {
				p.each(function() {
					var c = $(this);
					var e = parseInt(c.attr("time"));
					if (e > 0) {
						var d = function() {
							window.location.reload()
						};
						c.countdowntime(e, d)
					}
				})
			};
			$.getScript("/js/countdown.js", t)
		}
		var m = s.find("div[name=waiterItem]");
		if (m.length > 0) {
			m.each(function() {
				var c = $(this);
				var e = parseInt(c.attr("time"));
				if (e > 0) {
					var d = function() {
						window.location.reload()
					};
					setTimeout(d, e * 1000)
				}
			})
		}
		var g = ",";
		var a = false;
		var n = 0;
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
				if (a) {
					d(e)
				} else {
					$.getScript("/js/indexlottery.js", function() {
						a = true;
						d(e)
					})
				}
			}
		};
		l();
		var q = function() {
			var e = $indexImg;
			var c = $("<ul/>");
			c.addClass("slides");
			var h = "";
			for (var i = 0; i < e.length; i++) {
				var d = '<li style="background-color: rgb(f, f, f); width: 1903px; float: left; display: block;" class="clone"><a href="'
						+ e[i].proUrl.replace($skin,$www)
						+ '"><img width="640" height="110"  src="'
						+ $img
						+ e[i].proImg + '"></a></li>';
				c.append(d)
			}
			var g = $("#sliderBox");
			g.append(c).flexslider()
		};
		$.getScript("/js/flexslider.js", q);
		var r = function(c) {
			if (c && c.stopPropagation) {
				c.stopPropagation()
			} else {
				window.event.cancelBubble = true
			}
		};
		$("li", s).each(function() {
			var c = $(this);
			var d = c.attr("codeid");
			c.click(function() {
				location.href = "/products/" + d + ".html"
			});
			var e = c.attr("uweb");
			if (e != undefined) {
				c.find("uImg").click(function(f) {
					r(f);
					location.href = "/u/" + e + ".html";
				});
				c.find("uName").click(function(f) {
					r(f);
					location.href = "/u/" + e + ".html";
				})
			}
		});
		$("#ulRecommend > li").each(function() {
			var c = $(this);
			c.click(function() {
				location.href = "/products/" + c.attr("id") + ".html"
			})
		})
	};
	b();
	$(".scrollLoading").scrollLoading()
});
