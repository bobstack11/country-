$(function() {
	var a = function() {
		var d = $("#hidMobile").val();
		var g = $("#txtCode");
		var b = {
			Empty : "请输入6位验证码",
			LenErr : "验证码长度不对吧",
			Err : "验证码错误或过期"
		};
		var k = function(m) {
			var l = /^[0-9a-zA-Z]{6,}$/;
			return l.test(m)
		};
		var c = function(l) {
			$.PageDialog.fail(l)
		};
		var j = function(m, l) {
			$.PageDialog.ok(m, l)
		};
		var h = $("#btnSend");
		var f = function() {
			var l = 120;
			var m = function() {
				h.attr("class", "grayBtn").unbind("click");
				var n = function() {
					if (l > 0) {
						h.html("重新发送(" + l + ")");
						l--;
						setTimeout(n, 1000)
					} else {
						h.attr("class", "orgBtn").html("重新发送").unbind("click")
								.bind("click", f)
					}
				};
				n()
			};
			$.ajax({
				url : "sendMsg.html",
				data : "mobile=" + d,
				success : function(t) {
					if (t == "0") {
						j("手机验证短信已发送！");
						m();
						$("#btnSubmit").bind("click", e).attr("class", "orgBtn")
					} else {
						if (t == "2") {
							c("获取手机验证码太频繁！")
						} else {
							c("验证码发送失败！")
						}
					}
					isLoaded = true;
				}
			})
		};
		h.bind("click", f);
		var e = function() {
			if (!isLoaded) {
				return
			}
			var l = g.val();
			if (l == "") {
				c(b.Empty);
				return false
			} else {
				if (!k(l)) {
					c(b.LenErr);
					return false
				}
			}
			isLoaded = false;
			$.ajax({
				url : "verifyorgmobilesn.html",
				data : "mobile="+d+"&sn=" + l,
				success : function(m) {
					if (m.state == 0) {
						location.href = "MobileBind.html?sn=" + m.str;
					} else {
						c(b.Err);
					}
					isLoaded = true;
				}
			})
		};
		g.bind("keydown", function(m) {
			var l = (window.event) ? event.keyCode : m.keyCode;
			if (l == 32) {
				return false
			} else {
				if (l == 13) {
					return false
				}
			}
			return true
		});
		isLoaded = true
	};
	$.getScript("/js/pagedialog.js?v=130826", a)
});