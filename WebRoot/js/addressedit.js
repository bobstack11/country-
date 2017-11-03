$(function() {
	var orderId = $("#hideOrderID").val();
	var k = $("#hideContactID").val();
	var e = $("#hidIsActOrder").val();
	var uid = $("#userId").val();
	var j = 0;
	var shipName = $("#txtUserName");
	var shipMobile = $("#txtMobile");
	var shipAreaA = $("#selAreaA");
	var shipAreaB = $("#selAreaB");
	var shipAreaC = $("#selAreaC");
	//var shipAreaD = $("#selAreaD");
	var shipAddr = $("#txtAddr");
	var shipZip = $("#txtZip");
	var s = $("#spDefult");
	var n = "输入姓名";
	var d = "联系电话";
	var h = "请选择所在省份";
	var g = "请选择所在城市";
	var c = "请选择所在地区";
	var b = "请选择所在街道或乡镇";
	var u = "详细地址必须为3-100字之间,不包含特殊字符";
	var f = "邮政编码";
	var initData = function(data){
		var pid = null, cid=null;
		var N = function(P, R, Q) {
			P.focus(function() {
				if (P.val() == Q) {
					P.val("").css("color", "#666666")
				}
			}).blur(function() {
				if (P.val() == "") {
					P.val(Q).css("color", "#bbbbbb")
				}
			});
			if (data == null) {
				P.css("color", "#bbbbbb").val(Q)
			} else {
				P.css("color", "#666666").val(R)
			}
		};
		shipAreaA.css("color", "#666666");
		shipAreaB.css("color", "#666666");
		shipAreaC.css("color", "#666666");
		shipZip.css("color", "#666666");
		N(shipName, data.consignee, n);
		N(shipMobile, data.phone, d);
		N(shipAddr, data.address, u);
		N(shipZip, data.zipCode, f);
		var L = data.status == 1;
		if (L) {
			s.children("span").attr("class", "z-pay-mentsel")
		} else {
			s.click(function() {
				s.children("span").attr("class",
						L ? "z-pay-ment" : "z-pay-mentsel");
				L = !L
			})
		}
		var initProv = function(){
			$.ajax({
				url : "/user/getProvinceList.html",
				success : function(k) {
					var j = '<option value="0">\u3000\u3000\u3000\u3000\u3000</option>';
					var selStr = '';
					for (var h = 0; h < k.length; h++) {
						if(data.province==k[h][1]){
							pid = k[h][0];
							selStr = ' selected';
						}else{
							selStr = '';
						}
						j += '<option value="'
								+ k[h][0]+'"'
								+ selStr
								+ '>'
								+ k[h][1]
								+ "</option>"
					}
					shipAreaA.html(j);
					if(pid!=null){
						initCity();
					}
				},
				error : function() {
					alert("获取地区失败，请稍后再试！");
				}
			});
		};
		var initCity = function(){
			$.ajax({
				url : "/user/getCityList.html",
				data : "id="+ pid,
				success : function(k) {
					var j = '<option value="0">\u3000\u3000\u3000\u3000\u3000</option>';
					var selStr = '';
					for (var h = 0; h < k.length; h++) {
						if(data.city==k[h][1]){
							cid = k[h][0];
							selStr = ' selected';
						}else{
							selStr = '';
						}
						j += '<option value="'
								+ k[h][0]+'"'
								+ selStr
								+ '>'
								+ k[h][1]
								+ "</option>"
					}
					shipAreaB.html(j);
					//shipAreaC.html('<option value="0">\u3000\u3000\u3000\u3000\u3000</option>')
					if(cid!=null){
						initDistrict();
					}
				},
				error : function() {
					alert("获取地区失败，请稍后再试！")
				}
			});
		};
		var initDistrict = function(){
			$.ajax({
				url : "/user/getDistrictList.html",
				data : "id=" + cid,
				success : function(
						m) {
					var l = '<option value="0">\u3000\u3000\u3000\u3000\u3000</option>';
					if (m == "") {
						l += '<option value="'
								+ k
								+ '">'
								+ h
								+ "</option>"
					} else {
						var selStr = '';
						for (var k = 0; k < m.length; k++) {
							if(data.district==m[k][1]){
								selStr = ' selected';
							}else{
								selStr = '';
							}
							l += '<option value="'
									+ m[k][0]+'"'
									+ selStr
									+ '>'
									+ m[k][1]
									+ "</option>"
						}
					}
					shipAreaC.html(l)
				},
				error : function() {
					alert("获取地区失败，请稍后再试！")
				}
			});
		};
		initProv();
		//shipAreaB.html('<option value="0">\u3000\u3000\u3000\u3000\u3000</option>');
		//shipAreaC.html('<option value="0">\u3000\u3000\u3000\u3000\u3000</option>');
		shipAreaA.change(function() {
			$.ajax({
				url : "/user/getCityList.html",
				data : "id="+ shipAreaA.val(),
				success : function(k) {
					var j = '<option value="0">\u3000\u3000\u3000\u3000\u3000</option>';
					for (var h = 0; h < k.length; h++) {
						j += '<option value="'
								+ k[h][0]
								+ '">'
								+ k[h][1]
								+ "</option>"
					}
					shipAreaB.html(j);
					shipAreaC.html('<option value="0">\u3000\u3000\u3000\u3000\u3000</option>')
				},
				error : function() {
					alert("获取地区失败，请稍后再试！")
				}
			});
		});
		shipAreaB.change(function() {
			var j = shipAreaB.val();
			var h = $("#selAreaB option:selected").text();
			$.ajax({
				url : "/user/getDistrictList.html",
				data : "id="
						+ j,
				success : function(
						m) {
					var l = '<option value="0">\u3000\u3000\u3000\u3000\u3000</option>';
					if (m == "") {
						l += '<option value="'
								+ k
								+ '">'
								+ h
								+ "</option>"
					} else {
						for (var k = 0; k < m.length; k++) {
							l += '<option value="'
									+ m[k][0]
									+ '">'
									+ m[k][1]
									+ "</option>"
						}
					}
					shipAreaC.html(l)
				},
				error : function() {
					alert("获取地区失败，请稍后再试！")
				}
			});
		});
		
		$("#btnCancel").click(function() {
			history.go(-1);
		});
		$("#btnSure")
		.unbind("click")
		.bind(
			"click",
			function() {
				if (shipAreaA.val() == 0
						|| shipAreaB.val() == 0
						|| shipAreaC.val() == 0) {
					$.PageDialog.fail('请正确选择省市区');
					return false
				}
				if ($("#shipAddress").val() == "") {
					$.PageDialog.fail('请输入街道地址，不要重复输入省市区名称');
					return false
				}
				if ($("#shipName").val() == "") {
					$.PageDialog.fail('请输入收货人姓名');
					return false
				}
				if ($("#shipMobile").val() == "") {
					$.PageDialog.fail('请输入手机号码');
					return false
				}
				var h = {
					userId : uid,
					province : $(
							"#selAreaA > option:checked")
							.text(),
					city : $(
							"#selAreaB > option:checked")
							.text(),
					district : $(
							"#selAreaC > option:checked")
							.text(),
					address : shipAddr.val(),
					zipCode : shipZip.val() == "" ? 0
							: shipZip.val(),
					consignee : shipName.val(),
					phone : shipMobile.val(),
					status : L ? '1' : '0',
					addrId : data == null ? 0 : k,
					hidOrderNO : orderId
				};
				h = e(h);
				$
						.ajax({
							//url : "/user/addAddress.html",
							url : "/user/editUserContact.html",
							data : "userJSON="
									+ encodeURIComponent(h),
							success : function(i) {
								if (i == "success") {
									$.PageDialog.ok('操作成功！',function(){
										if(orderId)
											location.replace('orderdetail-'+orderId+'.html');
										else
											location.replace('AddressList.html');
									});
									//location.reload()
								} else {
									if (i == "sizeError") {
										$.PageDialog.fail('收货地址不能超过四个');
									}
								}
							},
							error : function() {
								alert("网络错误，请与管理员联系！");
								location.reload()
							}
						})
			});
		
		function e(k) {
			var j = [];
			for ( var h in k) {
				k[h] = typeof k[h] == "string" ? '"' + k[h] + '"'
						: (typeof k[h] == "object" ? e(k[h]) : k[h]);
				j.push(h + ":" + k[h])
			}
			return "{" + j.join(",") + "}"
		}
		
	};
	if (k > 0) {
		$.ajax({
			url : "/user/getAddrByID.html",
			data : "id=" + k,
			success : function(k) {
				//initData(k);
				$.getScript("/js/pagedialog.js?v=2015092201", function(){initData(k);});
			},
			error : function() {
				alert("获取地区失败，请稍后再试！");
			}
		});
		
	}
	//initData();
	
	//$.getScript("/js/pagedialog.js?v=2015092201", initData);
});
