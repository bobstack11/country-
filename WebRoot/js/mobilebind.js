$(function() {
	var a = function() {
		var n = function(s) {
			var r = /^1\d{10}$/;
			return r.test(s)
		};
		var o = function(s) {
			var r = /^[0-9a-zA-Z]{6,}$/;
			return r.test(s)
		};
		var d = function(r) {
			$.PageDialog.fail(r)
		};
		var l = function(s, r) {
			$.PageDialog.ok(s, r)
		};
		var m = $("#txtMobile");
		var q = $("#btnSend");
		var h = $("#btnAgain");
		var j = {
			Empty : "手机号码不能为空",
			Err : "您输入的手机号码有误",
			Registered : "该手机号码已被注册"
		};
		var b = {
			Empty : "请输入验证码",
			LenErr : "验证码长度不对吧",
			Err : "验证码错误"
		};
		var c = "";
		var g = $("#txtCode");
		var i = function() {
			c = m.val();
			if (c == "") {
				d(j.Empty)
			} else {
				if (!n(c)) {
					d(j.Err)
				} else {
					p(c)
				}
			}
		};
		var p = function(r) {
			if (!isLoaded) {
				return
			}
			isLoaded = false;
			$.ajax({
				url : "isCheckName.html",
				data : "userName=" + r,
				success : function(s) {
					if(s=='true'){
						f()
					}else {
						if (s=='false') {
							d(j.Registered)
						} else {
							d(j.Err)
						}
					}
					isLoaded = true
				}
			})
			/*GetJPData(k, "checkname", "name=" + r, function(s) {
				if (s.state == 0) {
					f()
				} else {
					if (s.state == 1) {
						d(j.Registered)
					} else {
						d(j.Err)
					}
				}
				isLoaded = true
			})*/
		};
		var f = function() {
			var r = 120;
			var s = function() {
				h.attr("disabled", true).unbind("click");
				var t = function() {
					if (r > 0) {
						h.html("(" + r + ")重新发送");
						r--;
						setTimeout(t, 1000)
					} else {
						h.attr("disabled", false).html("重新发送").unbind("click")
								.bind("click", f)
					}
				};
				t()
			};
			$.ajax({
				url : "sendMsg.html",
				data : "mobile=" + c,
				success : function(t) {
					isLoaded = true;
					if (t == "0") {
						$("#span_mobile").html(c);
						$("#div_registercon").hide();
						$("#div_securitycode").show();
						$("#btnSubmit").bind("click", e);
						l("验证短信发送成功,请注意查收！");
						s()
					} else {
						if (t == "2") {
							d("请检查手机号码是否正确！")
						} else {
							d("发送失败，请重试！")
						}
					}
				}
			})
			/*GetJPData(k, "send2Msg", "userMobile=" + c, function(t) {
				isLoaded = true;
				if (t.state == 0) {
					$("#span_mobile").html(c);
					$("#div_registercon").hide();
					$("#div_securitycode").show();
					$("#btnSubmit").bind("click", e);
					l("验证短信发送成功,请注意查收！");
					s()
				} else {
					if (t.state == 2) {
						d("请检查手机号码是否正确！")
					} else {
						d("发送失败，请重试！")
					}
				}
			})*/
		};
		var e = function() {
			if (!isLoaded) {
				return
			}
			var r = g.val();
			if (r == "") {
				d(b.Empty);
				return false
			} else {
				if (!o(r)) {
					d(b.LenErr);
					return false
				}
			}
			//var s = function() {
				if (!isLoaded) {
					return
				}
				var t = $("#hidAuth").val();
				$.ajax({
					url : "updateUserMobile.html",
					data : "mobile=" + c + "&sn=" + r + "&auth=" + t,
					success : function(t) {
						if (t == "0") {
							l("绑定成功！", function() {
								var v = $("#hidForward").val();
								if (v != "") {
									location.replace(v)
								} else {
									location.replace("SafeSettings.html")
								}
							})
						} else {
							if (t == "10") {
								location.reload()
							} else {
								d("绑定失败，请重试！")
							}
						}
						isLoaded = true
					}
				})
				/*GetJPData("", "updateMemberCenterUserMobile", "mobile=" + c
						+ "&sn=" + r + "&auth=" + t, function(u) {
					if (u.code == 0) {
						l("绑定成功！", function() {
							var v = $("#hidForward").val();
							if (v != "") {
								location.replace(v)
							} else {
								location.replace("SafeSettings.do")
							}
						})
					} else {
						if (u.code == 10) {
							location.reload()
						} else {
							d("绑定失败，请重试！")
						}
					}
					isLoaded = true
				})*/
			//};
			/*isLoaded = false;
			GetJPData(k, "verifymobilesn", "mobile=" + c + "&sn=" + r,
					function(t) {
						if (t.state == 0) {
							isLoaded = true;
							s()
						} else {
							d(b.Err)
						}
						isLoaded = true
					})*/
		};
		q.bind("click", function() {
			i()
		});
		g.bind("keydown", function(s) {
			var r = (window.event) ? event.keyCode : s.keyCode;
			if (r == 32) {
				return false
			} else {
				if (r == 13) {
					return false
				}
			}
			return true
		});
		isLoaded = true
	};
	$.getScript("/js/pagedialog.js?v=130826", a)
});