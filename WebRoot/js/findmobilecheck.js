$(document).ready(
		function() {
			var b = $www;
			var a = function() {
				var k = $("#btnSubmitVerify");
				var s = $("#hidRegMobile").val().trim();
				var j = $("#mobileCode");
				var o = $("#hidRegSN").val();
				var d = $("#checkSN").val().trim();
				var l = function(u) {
					var t = /^[0-9a-zA-Z]+$/;
					return t.test(u)
				};
				var q = function(u) {
					var t = /^[0-9a-zA-Z]{6,}$/;
					return t.test(u)
				};
				var g = {
					txtStr : "请输入手机验证码",
					error : "验证码错误",
					outtime : "验证码错误或过期",
					lose : "验证失败",
					many : "请不要频繁获取手机验证信息",
					retry : "操作超时，请重新取回密码",
					ok : "验证码已发送，请注意查收"
				};
				var c = {
					txtStr : "确认，下一步",
					checkCode : "正在验证"
				};
				var e = function(t) {
					$.PageDialog.fail(t)
				};
				var i = function(u, t) {
					$.PageDialog.ok(u, t)
				};
				var h = function() {
					var v = $("#retrySend");
					v.unbind("click").addClass("grayBtn");
					var u = 150;
					var t = function() {
						if (u < 2) {
							v.bind("click", m).html("重新发送").removeClass(
									"grayBtn");
							return
						} else {
							u--;
							v.html("重新发送(" + u + ")")
						}
						setTimeout(t, 1000)
					};
					t()
				};
				var m = function() {
					if (!isLoaded) {
						return
					}
					var t = function(u) {
						if (u.state == 0) {
							i(g.ok, h)
						} else {
							e(g.many)
						}
					};
					$.ajax({
						url : "sendfindsms.action",
						type : "get",
						data : "mail=" + s + "&sn=" + o,
						success : t
					});
					//GetJPData(b, "sendfindsms", "mobile=" + s + "&sn=" + o, t)
				};
				var f = function() {
					if (!isLoaded) {
						return
					}
					var t = function(u) {
						if (u.state == 0) {
							location.href = "findreset.html?key=" + u.str;
							return
						} else {
							if (u.state == -4) {
								e(g.outtime)
							} else {
								if (u.state == 2) {
									e(g.retry)
								} else {
									e(g.lose)
								}
							}
						}
						k.html(c.txtStr).removeClass("grayBtn")
								.bind("click", p)
					};
					k.html(c.checkCode).addClass("grayBtn").unbind("click");
					$.ajax({
						url : "verifysmscode.action",
						type : "get",
						data : "mail=" + s + "&sn=" + j.val().trim() + "&regsn=" + o,
						success : t
					});
					/*GetJPData(b, "verifysmscode", "mobile=" + s + "&sn="
							+ j.val().trim() + "&regsn=" + o, t)*/
				};
				var p = function() {
					if (!isLoaded) {
						return
					}
					var t = j.val().trim();
					if (t == "" || t == g.txtStr) {
						e(g.txtStr)
					} else {
						if (q(t)) {
							f()
						} else {
							e(g.error)
						}
					}
				};
				var n = "";
				var r = function() {
					var t = j.val();
					if (n != t) {
						if (l(t) || t == "") {
							n = t
						} else {
							j.val(n).focus()
						}
					}
					if (checkSwitch) {
						setTimeout(r, 200)
					}
				};
				j.bind("focus", function() {
					$(this).attr("style", "color:#666666");
					checkSwitch = true;
					r()
				}).bind("blur", function() {
					checkSwitch = false
				});
				h();
				k.bind("click", p);
				isLoaded = true
			};
			$.getScript("/js/pagedialog.js", a)
		});