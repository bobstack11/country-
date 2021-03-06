var isLoaded = null;
String.prototype.trim = function() {
	return this.replace(/(\s*$)|(^\s*)/g, "")
};
window.onerror = function() {
	return true
};
function GetData(d) {
	var f = d;
	var e = function() {
	};
	this.OK = function(a) {
		e = a
	};
	this.Get = function(a) {
		$.ajax({
			url : f,
			dataType : a,
			data : "",
			beforeSend : function(b) {
				if (a == "json") {
					b.setRequestHeader("Content-Type",
							"application/json;charset=utf-8")
				}
			},
			success : function(c) {
				try {
					e(c)
				} catch (b) {
				}
			}
		})
	}
}
function JQAjax(r, n) {
	var q = function() {
		isLoaded = false
	};
	var o = function() {
		isLoaded = true
	};
	var l = function() {
		isLoaded = true
	};
	var m = function() {
	};
	var p = null;
	var j = r;
	var k = 20000;
	if (n != null) {
		k = n
	}
	this.OnStart = function(a) {
		q = a
	};
	this.OnStop = function(a) {
		o = a
	};
	this.OnError = function(a) {
		l = a
	};
	this.OnSuccess = function(a) {
		m = a
	};
	this.OnTimeout = function(a) {
		p = window.setTimeout(a, k)
	};
	this.OnSend = function(c, b, a) {
		$.ajax({
			global : false,
			async : a,
			type : "POST",
			url : j + "?rnd=" + GetRandomNum(1, 10000),
			cache : true,
			contentType : b == "json" ? "application/json"
					: "application/x-www-form-urlencoded;charset=utf-8",
			data : c,
			dataType : b,
			dataFilter : function(e, d) {
				return e
			},
			beforeSend : function(d) {
				try {
					q()
				} catch (e) {
				}
			},
			complete : function(d, e) {
				if (p != null) {
					window.clearTimeout(p)
				}
				try {
					o()
				} catch (f) {
				}
			},
			error : function(d, e, f) {
				if (p != null) {
					window.clearTimeout(p)
				}
				try {
					l(e)
				} catch (g) {
				}
			},
			success : function(d) {
				if (p != null) {
					window.clearTimeout(p)
				}
				try {
					m(d)
				} catch (e) {
				}
			}
		})
	}
}
String.prototype.rExp = function(d, e) {
	var f = new RegExp(d, "g");
	return this.replace(f, e)
};
String.prototype.replaceWhile = function(e, f) {
	var g = this;
	var h = new RegExp(e, "g");
	while (g.indexOf(e) > -1) {
		g = g.replace(h, f)
	}
	return g
};
String.prototype.returnRegExp = function() {
	var b = this;
	b = b.rExp("\\+", "%2B");
	b = b.rExp("%F7", escape("&divide;"));
	b = b.rExp("%B1", escape("&plusmn;"));
	b = b.rExp("%D7", escape("&times;"));
	b = b.rExp("%A9", escape("&copy;"));
	b = b.rExp("%AE", escape("&reg;"));
	b = b.rExp("%B7", escape("&middot;"));
	b = b.rExp("%A3", escape("&pound;"));
	b = b.rExp("%u2122", escape("&#8482;"));
	return b
};
function escapeEx(b) {
	return escape(b).returnRegExp()
}
function escapeEx2(b) {
	return encodeURIComponent(b)
}
function descapeEx2(b) {
	return decodeURIComponent(b)
}
String.prototype.toHTML = function(c) {
	var d = String(this);
	if (c) {
	}
	d = d.rExp(">", "&gt;");
	d = d.rExp("<", "&lt;");
	d = d.rExp(" ", "&nbsp;");
	d = d.rExp("'", "'");
	d = d.rExp('"', '"');
	d = d.rExp("\r\n", "<br/>");
	d = d.rExp("\n", "<br/>");
	d = d.rExp("\r", "<br/>");
	return d
};
String.prototype.toText = function(c) {
	var d = String(this);
	if (c) {
	}
	d = d.rExp("<", "&#60;");
	d = d.rExp(">", "&#62;");
	d = d.rExp('"', "&#34;");
	d = d.rExp("'", "&#39;");
	return d
};
String.prototype.reAjaxStr = function() {
	var b = String(this);
	b = b.rExp("\u951b\ufffd", "'");
	return b
};
function ToHTML(c, d) {
	return c.toHTML(d)
}
function ToText(c, d) {
	return c.toText(d)
}
function GetAjaxPageNation(g, h, f, i, j) {
	return GetAjaxPageNationEx(g, h, f, i, j, true)
}
function GetAjaxPageNationEx(g, i, l, j, k, h) {
	return GetAjaxPageNationEx2(g, i, l, j, k, h, false)
}
function GetAjaxPageNationEx2(w, p, x, n, r, q, o) {
	if (w < 1) {
		return ""
	}
	var v = 1, s = 1, i = 1;
	v = ((w % p) == 0 ? Math.floor(w / p) : Math.floor(w / p) + 1);
	var t = '<ul class="pageULEx">';
	if (q) {
		t += '<li class="total_page">\u6924\u57ab\ue0bc<i>' + n + "/" + v
				+ "</i>&nbsp;&nbsp;\u934f\ufffd<i>" + w
				+ "</i>\u93c9\xa4\ue187\u8930\ufffd</li>"
	}
	while (s + x < v + 1 && s + x < n + 2) {
		s += x - 2
	}
	i = s + x - 1;
	if (i > v) {
		i = v
	}
	if (n == 1) {
		if (o) {
			t += '<li class="prev_page page_curgray"><a><i><</i>\u6d93\u5a41\u7af4\u6924\ufffd</a></li>'
		}
	} else {
		t += '<li class="prev_page"><a href="javascript:gotoClick();" onclick="javascript:return '
				+ r
				+ "("
				+ ((n - 2) * p + 1)
				+ ","
				+ (n - 1)
				* p
				+ ');">\u6d93\u5a41\u7af4\u6924\ufffd</a></li>'
	}
	if (s > 1) {
		t += '<li><a href="javascript:gotoClick();" onclick="javascript:return '
				+ r + "(1," + p + ');">1</a></li><li>\u9225\ufffd</li>'
	}
	for (var u = s; u <= i; u++) {
		if (u != n) {
			t += '<li><a href="javascript:gotoClick();" onclick="javascript:return '
					+ r
					+ "("
					+ ((u - 1) * p + 1)
					+ ","
					+ u
					* p
					+ ');">'
					+ u
					+ "</a></li>"
		} else {
			t += '<li class="curr_page">' + u + "</li>"
		}
	}
	if (i < v) {
		t += '<li>\u9225\ufffd</li><li><a href="javascript:gotoClick();" onclick="javascript:return '
				+ r
				+ "("
				+ ((v - 1) * p + 1)
				+ ","
				+ v
				* p
				+ ');">'
				+ v
				+ "</a></li>"
	}
	if (n < v) {
		t += '<li class="next_page"><a href="javascript:gotoClick();" onclick="javascript:return '
				+ r
				+ "("
				+ (n * p + 1)
				+ ","
				+ (n + 1)
				* p
				+ ');">\u6d93\u5b29\u7af4\u6924\ufffd</a></li>'
	} else {
		if (o) {
			t += '<li class="prev_page page_curgray"><a>\u6d93\u5b29\u7af4\u6924\ufffd<i>></i></a></li>'
		}
	}
	t += "</ul>";
	return t
}
function showLoadingStatus() {
}
function closeLoadingStatus() {
}
$.extend({
	toJSON : function(h) {
		var k = typeof h;
		if ("object" == k) {
			if (Array == h.constructor) {
				k = "array"
			} else {
				if (RegExp == h.constructor) {
					k = "regexp"
				} else {
					k = "object"
				}
			}
		}
		switch (k) {
		case "undefined":
		case "unknown":
			return;
			break;
		case "function":
		case "boolean":
		case "regexp":
			return h.toString();
			break;
		case "number":
			return isFinite(h) ? h.toString() : "null";
			break;
		case "string":
			return '"'
					+ h.replace(/(\\|\")/g, "\\$1").replace(
							/\n|\r|\t/g,
							function() {
								var a = arguments[0];
								return (a == "\n") ? "\\n"
										: (a == "\r") ? "\\r"
												: (a == "\t") ? "\\t" : ""
							}) + '"';
			break;
		case "object":
			if (h === null) {
				return "null"
			}
			var l = [];
			for ( var i in h) {
				var j = jQuery.toJSON(h[i]);
				if (j !== undefined) {
					l.push(jQuery.toJSON(i) + ":" + j)
				}
			}
			return "{" + l.join(",") + "}";
			break;
		case "array":
			var l = [];
			for (var g = 0; g < h.length; g++) {
				var j = jQuery.toJSON(h[g]);
				if (j !== undefined) {
					l.push(j)
				}
			}
			return "[" + l.join(",") + "]";
			break
		}
	}
});
