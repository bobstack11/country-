var Gobal = new Object();
function SetCookie(a, b) {
	document.cookie = a + "=" + escape(b) + ";id=1ypg;path=/;domain=" + $domain
}
function SetCookieByExpires(b, d, a) {
	var c = a;
	var e = new Date();
	e.setTime(e.getTime() + c * 24 * 60 * 60 * 1000);
	document.cookie = b + "=" + escape(d) + ";id=1ypg;path=/;expires="
			+ e.toGMTString() + ";domain=" + $domain
}
function getCookie(b) {
	var a = document.cookie.match(new RegExp("(^| )" + b + "=([^;]*)(;|$)"));
	if (a != null) {
		return unescape(a[2])
	}
	return null
}
function delCookie(a) {
	var c = new Date();
	c.setTime(c.getTime() - 1);
	var b = getCookie(a);
	if (b != null) {
		document.cookie = a + "=" + b + ";id=1ypg;path=/;expires="
				+ c.toGMTString() + ";domain=" + $domain
	}
}
function GetJPData(g, h, f, e) {
	$.getJSON(g + "/JPData?action=" + h + "&" + f + "&fun=?", e)
}
function loadImgFun(p) {
	var q = $("#loadingPicBlock");
	if (q.length > 0) {
		var j = "src2";
		Gobal.LoadImg = q.find("img[" + j + "]");
		var r = function() {
			return $(window).scrollTop()
		};
		var n = function() {
			return $(window).height() + r() + 50
		};
		var k = function() {
			Gobal.LoadImg.each(function(b) {
				if ($(this).offset().top <= n()) {
					var a = $(this).attr(j);
					if (a) {
						$(this).attr("src", a).removeAttr(j).show()
					}
				}
			})
		};
		var o = 0;
		var m = -100;
		var l = function() {
			o = r();
			if (o - m > 50) {
				m = o;
				k()
			}
		};
		if (p == 0) {
			$(window).bind("scroll", l)
		}
		l()
	}
}
var IsMasked = false;
var addNumToCartFun = null;
var _IsLoading = false;
function scrollForLoadData(a) {
	$(window).scroll(function() {
		var c = $(document).height();
		var b = $(window).height();
		var d = $(document).scrollTop() + b;
		if (c - d <= b * 4) {
			if (!_IsLoading && a) {
				_IsLoading = true;
				a()
			}
		}
	})
}
String.prototype.trim = function() {
	return this.replace(/(\s*$)|(^\s*)/g, "")
};
String.prototype.trims = function() {
	return this.replace(/\s/g, "")
};
var addNumToCartFun = null;
(function() {
	Gobal.Skin = $skin;
	Gobal.LoadImg = null;
	Gobal.LoadHtml = '<div class="loadImg">\u6b63\u5728\u52a0\u8f7d</div>';
	Gobal.LoadPic = Gobal.Skin + "/Images/loading.gif?v=130820";
	Gobal.NoneHtml = '<div class="haveNot z-minheight"><s></s><p>\u6682\u65e0\u8bb0\u5f55</p></div>';
	Gobal.unlink = "javascript:;";
	$("#tBtnReturn").click(function() {
		history.go(-1);
		return false
	});
	var p = $www;
	loadImgFun(0);
	var a = function() {
		var s = $("#fLoginInfo");
		var o = getCookie("userId");
		var k = '<span><a href="/">\u9996\u9875</a><b></b></span><span><a href="/help/index.html">\u65b0\u624b\u6307\u5357</a><b></b></span>';
		if (o != null) {
			var j = getCookie("face");
			var u = getCookie("userName");
			k = k
					+ '<span><a href="/user/index.html" class="Member">'
					+ u
					+ '</a><a href="/logout/index.html" class="Exit">\u9000\u51fa</a></span>'
		} else {
			k = k
					+ '<span><a href="/login/index.html?forward=rego">\u767b\u5f55</a><b></b></span><span><a href="/register/index.html?forward=rego">\u6ce8\u518c</a></span>'
		}
		s.html(k);
		var q = $("#btnCart");
		if (q.length > 0) {
			$.ajax({
				url : "/mycart/buyProductClick.html",
				type : "GET",
				success : function(a) {
					if (a != "" && a.productCount > 0) {
						q.find("i").html(m > 99 ? '<b class="tomore" num="' + a.productCount + '">...</b>'
										: "<b>" + a.productCount + "</b>")
					}
				}
			})
		}
		addNumToCartFun = function(a) {
			$.ajax({
				url : "/mycart/buyProductClick.html",
				type : "GET",
				success : function(b) {
					if (b != "" && b.productCount > 0) {
						q.find("i").html(m > 99 ? '<b class="tomore" num="' + b.productCount + '">...</b>'
										: "<b>" + b.productCount + "</b>")
					}
				}
			})
		};
		var m = function(a) {
			var b = new Date();
			a.attr("src", a.attr("data") + "?v=" + GetVerNum()).removeAttr("id")
					.removeAttr("data")
		};
		var l = $("#pageJS", "head");
		if (l.length > 0) {
			m(l)
		} else {
			l = $("#pageJS", "body");
			if (l.length > 0) {
				m(l)
			}
		}
		var r = $("#btnTop");
		if ($(window).scrollTop() == 0) {
			r.hide()
		}
		r.css("zIndex", "99").click(function() {
			$("body,html").animate({
				scrollTop : 0
			}, 0)
		});
		$(window).scroll(function() {
			if ($(this).scrollTop() > 0) {
				r.show()
			} else {
				r.hide()
			}
		});
		var n = function(c, b, a) {
			var d = new Date();
			d.setTime(d.getTime() + a * 60 * 1000);
			document.cookie = c + "=" + escape(b)
					+ (a == 0 ? "" : ";expires=" + d.toGMTString())
		};
		document.addEventListener("WeixinJSBridgeReady", function t() {
			n("_Terminal", "weixin", 0);
			WeixinJSBridge.call("hideToolbar")
		})
	};
	Base.getScript("/js/comm.js?v=151105", a)
})();
