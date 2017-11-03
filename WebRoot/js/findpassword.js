$(document)
		.ready(
				function() {
					var c = $www;
					var d = function() {
						var D = $("#userAccount");
						var v = $("#btnGetCode");
						var s = "";
						var y = 0;
						var u = false;
						var a = function(f) {
							var e = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
							return e.test(f)
						};
						var w = function(e) {
							var f = /^1\d{10}$/;
							return f.test(e)
						};
						var B = function(e) {
							var f = /^([\x00-\xff])+$/;
							return f.test(e)
						};
						var E = {
							empty : "请输入手机号/邮箱",
							avible : "该邮箱地址或手机号码未注册",
							error : "邮箱地址或手机号码不正确",
							many : "该帐号请求过于频繁，请稍后再试",
							mailFail : "发送邮件失败，请稍后再试",
							mobileFail : "验证码发送失败，请稍后再试"
						};
						var z = {
							txtStr : "获取验证码",
							sending : "正在发送"
						};
						var A = function(e) {
							$.PageDialog.fail(e)
						};
						var x = function() {
							if (!isLoaded) {
								return
							}
							var e = function(f) {
								if (f.state == 0) {
									location
											.replace("findmobilecheck.html?key="
													+ f.str);
									return
								} else {
									v.html(z.txtStr).removeClass("grayBtn")
											.bind("click", C);
									A(E.many)
								}
							};
							$.ajax({
								url : "sendfindsms.action",
								type : "get",
								data : "mail=" + D.val().trim() + "&sn=" + s,
								success : e
							});
							/*GetJPData(c, "sendfindsms", "mobile="
									+ D.val().trim() + "&sn=" + s, e)*/
						};
						var F = function() {
							if (!isLoaded) {
								return
							}
							var e = function(f) {
								if (f.state == 0) {
									location.replace("findemailcheck.html?key="
											+ f.str);
									return
								} else {
									v.html(z.txtStr).removeClass("grayBtn")
											.bind("click", C);
									A(E.mailFail)
								}
							};
							$.ajax({
								url : "emailgetbackpwd.action",
								type : "get",
								data : "mail=" + D.val().trim() + "&sn=" + s,
								success : e
							});
							/*GetJPData(c, "emailgetbackpwd", "email="
									+ D.val().trim() + "&sn=" + s, e)*/
						};
						var C = function() {
							if (!isLoaded) {
								return
							}
							var f = D.val().trim();
							if (f == "" || f == E.empty) {
								A(E.empty);
								return
							} else {
								if (!a(f) && !w(f)) {
									A(E.error);
									return
								}
							}
							var e = function(g) {
								if (g.state == 1) {
									isLoaded = true;
									s = g.str;
									if (w(f)) {
										x()
									} else {
										F()
									}
								} else {
									v.html(z.txtStr).removeClass("grayBtn")
											.bind("click", C);
									A(E.avible)
								}
							};
							v.html(z.sending).addClass("grayBtn").unbind(
									"click");
							$.ajax({
								url : "checknamesn.action",
								type : "get",
								data : "name=" + f,
								success : e
							});
							/*GetJPData(c, "checknamesn", "name=" + f, e)*/
							/*alert("手机端测试中,请在电脑端找回密码")*/
						};
						var t = "";
						var b = function() {
							var e = D.val();
							if (t != e) {
								if (B(e) || e == "") {
									t = e
								} else {
									D.val(t).focus()
								}
							}
							if (u) {
								setTimeout(b, 200)
							}
						};
						D.bind("focus", function() {
							$(this).attr("style", "color:#666666");
							u = true;
							b()
						}).bind("blur", function() {
							u = false
						});
						v.bind("click", C);
						isLoaded = true
					};
					$.getScript("/js/pagedialog.js", d)
				});
