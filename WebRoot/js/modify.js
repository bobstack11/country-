$(function() {
	var a = function() {
		var an = function(d) {
			var b = /^1\d{10}$/;
			return b.test(d)
		};
		var aw = function(d) {
			var b = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			return b.test(d)
		};
		var R = function(d) {
			var b = /^(0[0-9]{2,3}\-)+([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
			return b.test(d)
		};
		var ad = function(b) {
			if (b.length > 20) {
				return false
			}
			var d = /^[\w\u4e00-\u9fa5\-]{2,20}$/;
			return d.test(b)
		};
		function W(b, d) {
			document.cookie = b + "=" + escape(d) + ";id=1yjx;path=/;domain="
					+ $domain
		}
		var ak = true;
		var ag = $("#txtName");
		var Q = $("#txtPhone");
		var ac = $("#txtQQ");
		var V = $("#txtSignature");
		var T = $("#userId").val();
		var ae = true;
		var S = {
			oldUserName : $("#hidOldName").val(),
			userName : "",
			userPhone : "",
			mobilePhone : "",
			userMail : "",
			userSex : 0,
			userBirthDay : "",
			userCons : 0,
			userLiveArea : 0,
			liveAreaName : "",
			userBirthArea : 0,
			birthAreaName : "",
			userQQ : "",
			userMonthIncome : "",
			userSign : "",
			rnd : 0
		};
		var Y = function(b, d) {
			ae = false;
			b.removeClass("txt").addClass("orangeBor");
			b.nextAll("span").eq(0).attr("class", "orange").html("<s></s>" + d)
		};
		var M = function(b, d) {
			b.removeClass("orangeBor").addClass("txt");
			b.nextAll("span").eq(0).attr("class", "").html(
					(d == undefined ? "" : d))
		};
		var av = function(b) {
			ae = false;
			if (b == 0) {
				$("#errmsgArea").attr("class", "orange").html(
						"<s></s>请选择所在市区")
						.show()
			} else {
				$("#errmsgArea2").attr("class", "orange").html(
						"<s></s>请选择所在市区")
						.show()
			}
		};
		var af = function(d) {
			this.DataTextField = null;
			this.DataValueField = null;
			this.DataSource = null;
			this.OnItemDataBinding = null;
			this.OnSelectedIndexChanged = null;
			var b = d;
			this.DataBind = function() {
				var f = $("#" + b);
				f.empty();
				if (this.DataSource == null) {
					return
				}
				var g = this.DataSource.length;
				if (this.OnSelectedIndexChanged != null) {
					f.bind("change", this.OnSelectedIndexChanged)
				}
				var e = null;
				for (var h = 0; h < g; h++) {
					e = this.DataSource[h];
					if (this.OnItemDataBinding != null) {
						this.OnItemDataBinding(e)
					}
					f.get(0).options.add(new Option(e[this.DataTextField],
							e[this.DataValueField]))
				}
			};
			this.appendFirstOption = function(f, e) {
				$("#" + b).get(0).options.add(new Option(f, e), 0)
			};
			this.show = function() {
				$("#" + b).show()
			};
			this.hide = function() {
				$("#" + b).hide()
			};
			this.getValue = function() {
				return $("#" + b).val()
			};
			this.setValue = function(e) {
				$("#" + b).val(e)
			};
			this.clear = function() {
				$("#" + b).empty()
			};
			this.getText = function() {
				return $("#" + b).get(0).options[$("#" + b).get(0).selectedIndex].text
			};
			this.getSelectedIndex = function() {
				return $("#" + b).get(0).selectedIndex
			}
		};
		var ao = function(f) {
			var d = null;
			var e = null;
			if (f == 0) {
				d = new af("sel_areaA");
				e = new af("sel_areaB")
			} else {
				d = new af("sel_areaA2");
				e = new af("sel_areaB2")
			}
			var g = new Object();
			d.DataTextField = e.DataTextField = "name";
			d.DataValueField = e.DataValueField = "id";
			var h = function(k, j) {
				var i = {
					name : "请选择　　",
					id : 0
				};
				if (j == 0) {
					k.DataSource = [ i ];
					k.DataBind();
					return
				}
				var l = g[j];
				if (l != null) {
					k.DataSource = l.regions;
					k.DataBind();
					return
				}
			};
			var b = function() {
				var i = d.getValue();
				h(e, i)
			};
			d.OnSelectedIndexChanged = b;
			e.OnSelectedIndexChanged = function() {
				var i = e.getValue();
				if (i == "0") {
					av(f)
				} else {
					if (f == 0) {
						$("#errmsgArea").hide()
					} else {
						$("#errmsgArea2").hide()
					}
				}
			};
			this.setValue = function(i, j, k) {
				h(d, 1);
				d.setValue(i);
				if (i != 0) {
					h(e, i)
				} else {
					h(e, 0)
				}
				if (j != 0) {
					e.setValue(j)
				}
				return
			};
			this.getValue = function() {
				var i = d.getValue();
				var j = e.getValue();
				if (i != "0" && j == "0") {
					av(f);
					return null
				}
				return {
					areaAID : i,
					areaBID : j
				}
			};
			this.getText = function() {
				var i = d.getValue();
				var j = e.getValue();
				if (i != "0" && j == "0") {
					av(f);
					return null
				} else {
					return d.getText() + e.getText()
				}
			}
		};
		var ap = new ao(0);
		ap.setValue($("#hidSelareaA").val(), $("#hidSelareaB").val());
		var au = new ao(1);
		au.setValue($("#hidSelareaA2").val(), $("#hidSelareaB2").val());
		var al = $("#sltYear");
		var aq = $("#sltMouth");
		var N = $("#sltDay");
		al.bind("change", function() {
			ab(al, aq, N)
		});
		aq.bind("change", function() {
			ab(al, aq, N)
		});
		var ar = null;
		var O = function() {
			ar = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
			var d = new Date().getFullYear();
			var b = new Date().getMonth() + 1;
			var f = new Date().getDate();
			var g = $("#hidBirthday").val().split("-");
			for (var h = d; h > d - 60; h--) {
				if (h == g[0]) {
					al.append("<option value='" + h + "' selected>" + h
							+ "</option>")
				} else {
					al.append("<option value='" + h + "'>" + h + "</option>")
				}
			}
			for (var h = 1; h <= 12; h++) {
				if (h == g[1]) {
					aq.append("<option value='" + h + "' selected>" + h
							+ "</option>")
				} else {
					aq.append("<option value='" + h + "'>" + h + "</option>")
				}
			}
			var e = ar[b - 1];
			if (b == 2 && ah(al.val())) {
				e++
			}
			for (var h = 1; h <= e; h++) {
				if (h == g[2]) {
					N.append("<option value='" + h + "' selected>" + h
							+ "</option>")
				} else {
					N.append("<option value='" + h + "'>" + h + "</option>")
				}
			}
		};
		var ab = function(g, f) {
			var d = g.val();
			var b = f.val();
			var e = ar[b - 1];
			if (b == 2 && ah(d)) {
				e++
			}
			aa(e)
		};
		var aa = function(d) {
			L();
			for (var b = 1; b <= d; b++) {
				N.append("<option value='" + b + "'>" + b + "</option>")
			}
		};
		var L = function() {
			N.find("option").remove()
		};
		var ah = function(b) {
			return (b % 4 == 0 || (b % 100 == 0 && b % 400 == 0))
		};
		if (!al.attr("disabled")) {
		} else {
			var X = $("#hidBirthday").val().split("-");
			$("#sltYear").append(
					"<option value='" + X[0] + "' selected>" + X[0]
							+ "</option>");
			$("#sltMouth").append(
					"<option value='" + X[1] + "' selected>" + X[1]
							+ "</option>");
			$("#sltDay").append(
					"<option value='" + X[2] + "' selected>" + X[2]
							+ "</option>");
			var aj = $("#hidUpdateTime").val().split("-");
			var am = (parseInt(aj[0]) + 1);
			var ai = am
					+ "\u5e74"
					+ aj[1]
					+ "\u6708"
					+ aj[2]
					+ "日后才能再次编辑您的生日";
			$("#BirthdayMsg").html(ai).show()
		}
		var c = function() {
			var d = [ "白羊座", "金牛座",
					"\u53cc\u5b50\u5ea7", "\u5de8\u87f9\u5ea7",
					"\u72ee\u5b50\u5ea7", "\u5904\u5973\u5ea7",
					"\u5929\u67b0\u5ea7", "\u5929\u874e\u5ea7",
					"\u5c04\u624b\u5ea7", "\u6469\u7faf\u5ea7",
					"\u6c34\u74f6\u5ea7", "\u53cc\u9c7c\u5ea7" ];
			var b = $("#selCons");
			b.empty();
			var f = $("#hideCons").val();
			for (var e = 1; e < 13; e++) {
				if (e == f) {
					b.append("<option value=" + e + " selected>" + d[e - 1]
							+ "</option>")
				} else {
					b.append("<option value=" + e + ">" + d[e - 1]
							+ "</option>")
				}
			}
		};
		c();
		var P = "昵称长度为2-20个字符，由汉字、字母、数字、“_-”字符组成";
		ag
				.blur(function() {
					var b = ag.val();
					if (b == "") {
						Y(ag, "请输入昵称")
					} else {
						if (!ad(b)) {
							Y(ag, P, 300)
						} else {
							if (b != S.oldUserName) {
								$
										.ajax({
											url : "/user/isUserNameExists.html",
											type : "post",
											data : "id="
													+ encodeURIComponent(b)
													+ "&userId=" + T,
											success : function(d) {
												if (d == "true") {
													M(ag, "")
												} else {
													Y(ag,
															"该昵称已被使用，换一个吧")
												}
											}
										})
							} else {
								M(ag, P)
							}
						}
					}
				});
		Q
				.blur(function() {
					var b = Q.val();
					if (b != "" && (!R(b) && !an(b))) {
						Y(Q,
								"请输入正确的电话号码")
					} else {
						M(Q)
					}
				});
		ac.blur(function() {
			var b = ac.val();
			var d = /^[0-9]{5,12}$/;
			if (b != "" && !d.test(b)) {
				Y(ac, "QQ为5-12位数字")
			} else {
				M(ac)
			}
		});
		V
				.focus(
						function() {
							if (V.val() == "让别人看到不一样的你") {
								V.val("").removeClass("gray03").addClass(
										"gray01")
							}
						})
				.blur(
						function() {
							if (V.val() == "") {
								V
										.val(
												"让别人看到不一样的你")
										.removeClass("gray01").addClass(
												"gray03")
							}
						}).bind("keyup", function(d) {
					var b = (window.event) ? event.keyCode : d.keyCode;
					var e = V.val();
					if (b == 13) {
						e = e.replace("\n", "");
						V.val(e)
					}
					if (e.length > 200) {
						V.val(e.substr(0, 200))
					}
				}).bind("keydown", function(d) {
					if (!window.event) {
						var b = d.keyCode;
						var b = String.fromCharCode(b).toLowerCase();
						if (d.ctrlKey && b == "v") {
							d.preventDefault();
							d.stopPropagation()
						}
					}
				});
		if (V.val() != "让别人看到不一样的你！") {
			V.removeClass("gray03").addClass("gray01")
		}
		function at() {
			ak = false;
			$("#butSaveSubmit").attr("disabled", true)
		}
		var U = function() {
			if (!ak) {
				return
			}
			var b = '{"userName":"' + S.userName + '","mobilePhone":"'
					+ S.mobilePhone + '","qq":"' + S.userQQ + '","userSign":"'
					+ S.userSign + '"}';
			$
					.ajax({
						url : "/user/updateUser.action",
						data : "id=" + T + "&userJSON=" + encodeURIComponent(b),
						beforeSend : at,
						success : function(d) {
							if (d != "false") {
								ak = true;
								$("#butSaveSubmit").attr("disabled", false);
								$.PageDialog.ok('操作成功！');
								if (d.userId != null && d.userId != "") {
									W("userId", d.userId);
									W("face", d.faceImg)
								}
								if (d.userName != null && d.userName != "") {
									W("userName", d.userName)
								} else {
									if (d.mail != null && d.mail != "") {
										W("userName", d.mail)
									} else {
										if (d.phone != null && d.phone != "") {
											W("userName", d.phone)
										}
									}
								}
								setTimeout(function() {
									location.replace(location.href)
								}, 1500)
							} else {
								$.PageDialog.fail('操作失败，请稍后再试！');
								setTimeout(function() {
									location.replace(location.href)
								}, 1500)
							}
						},
						error : function() {
							alert("网络错误，请稍后再试！");
							location.reload()
						}
					})
		};
		var Z = function() {
			if (!ak) {
				return
			}
			ae = true;
			var e = ag.val();
			if (e == "") {
				Y(ag, "请输入昵称")
			} else {
				if (!ad(e)) {
					Y(ag, P, 300)
				}
			}
			S.userName = e;
			var f = Q.val();
			if (f != "" && (!R(f) && !an(f))) {
				Y(Q,"请输入正确的电话号码")
			}
			S.mobilePhone = f;
			if ($("#rdoBoy").attr("checked") == true) {
				S.userSex = 2
			} else {
				if ($("#rdoGirl").attr("checked") == true) {
					S.userSex = 1
				} else {
					if ($("#rdoSecrit").attr("checked") == true) {
						S.userSex = 3
					}
				}
			}
			var b = ac.val();
			var d = /^[0-9]{5,12}$/;
			if (b != "" && !d.test(b)) {
				Y(ac, "QQ为5-12位数字")
			}
			S.userQQ = b;
			S.userMonthIncome = $("#sltMonthIncome").val();
			if (V.val() != "让别人看到不一样的你！") {
				S.userSign = V.val()
			}
			if (ae) {
				U()
			}
		};
		$("#butSaveSubmit").bind("click", function() {
			Z()
		});
		ak = true
	};
	a()
});
