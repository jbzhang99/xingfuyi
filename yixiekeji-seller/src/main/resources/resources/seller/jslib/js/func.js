$.fn.foucsText = function(c) {
	var a = this;
	var b = (c == null) ? $(a).val() : c;
	a.val(b);
	a.focus(function() {
		if (a.val() == b) {
			a.val("")
		}
	});
	a.blur(function() {
		if (a.val() == "") {
			a.val(b)
		}
	});
	return a
};

// 日志对象，用于调试
function Logger() {
};
Logger.prototype.setLevel = function(l) {
	this.l = l;
}
Logger.prototype.i = function(msg) {
	if (console && typeof console != undefined) {
		if (this.l) {
			try{
				var level_ = eval("this.l");
				return eval("console." + level_ + "('" + eval('msg') + "')");
			} catch (e) {
				//
			}
		}
	}
}

var log = new Logger();
log.setLevel("debug");

var browser = {
	versions : function() {
		var u = navigator.userAgent, app = navigator.appVersion;
		return {// 移动终端浏览器版本信息
			trident : u.indexOf('Trident') > -1, // IE内核
			presto : u.indexOf('Presto') > -1, // opera内核
			webKit : u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
			gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
			mobile : !!u.match(/AppleWebKit.*Mobile.*/)
					|| !!u.match(/AppleWebKit/), // 是否为移动终端
			ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
			android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端或者uc浏览器
			iPhone : u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, // 是否为iPhone或者QQHD浏览器
			iPad : u.indexOf('iPad') > -1, // 是否iPad
			webApp : u.indexOf('Safari') == -1
		// 是否web应该程序，没有头部与底部
		};
	}(),
	language : (navigator.browserLanguage || navigator.language).toLowerCase()
}

function ismobile(){
	//校验是否是移动端
	var sUserAgent = navigator.userAgent.toLowerCase();
    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
    var bIsAndroid = sUserAgent.match(/android/i) == "android";
    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
    
    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
    	return true;
    }else{
    	return false;
    }
}

/* 分页函数 */
function pagerFilter(data) {
	if (typeof data.length == 'number' && typeof data.splice == 'function') { // is
																				// array
		data = {
			total : data.length,
			rows : data
		}
	}
	var dg = $(this);
	var opts = dg.datagrid('options');
	var pager = dg.datagrid('getPager');
	pager.pagination({
		onSelectPage : function(pageNum, pageSize) {
			opts.pageNumber = pageNum;
			opts.pageSize = pageSize;
			pager.pagination('refresh', {
				pageNumber : pageNum,
				pageSize : pageSize
			});
			dg.datagrid('loadData', data);
		}
	});
	if (!data.originalRows) {
		data.originalRows = (data.rows);
	}
	var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
	var end = start + parseInt(opts.pageSize);
	data.rows = (data.originalRows.slice(start, end));
	return data;
}

/**
 * @author zhl
 * @param
 * @return 初始化页面菜单选中样式<br>
 *         如果指定选中菜单，则选中该菜单，如果没有指定，以当前url判断
 */
function initMenu() {
	var selectedType = arguments[0];
	if (selectedType) {
		setStyle(selectedType);
	} else {
		// 初始默认菜单及导航
		var currenturi = location.href;
		try {
			var patrn = /[`~!@$%^&()+<>?#{},;'[\]]/igm;
			currenturi = currenturi.replace(patrn, '!@#');
			var curiidx_ = currenturi.indexOf("!@#");
			if (curiidx_ > 0)
				currenturi = currenturi.substring(0, currenturi.indexOf("!@#"));
			if (currenturi.endsWith("/"))
				currenturi = currenturi.substring(0, currenturi.length - 1);
		} catch (e) {
			log.i(e.message);
		}
		log.i("当前访问：" + currenturi);
		// 菜单样式
		setStyle(null, currenturi);
	}
}

function setStyle() {
	var type = arguments[0];
	var currenturi = arguments[1];
	$("#sidebar .nav > li").each(
			function() {
				var outli = $(this);
				var iscontinue = true;
				if (iscontinue) {
					var ulli = outli.find("ul li");
					// log.i(ulli.length+"|菜单："+outli.find("span").html().trim());
					if (ulli.length > 0) {
						ulli.each(function() {
							if (type) {
								if ($(this).attr("menu") != type) {
									return true;
								}
							} else {
								var current = $(this).find(
										"a[href='" + currenturi + "']");
								if (current.length < 1) {
									return true;
								}

							}
							$(this).addClass("active").siblings().removeClass(
									"active");

							// 定位到当前元素
							setpos(this);

							outli.addClass("open").siblings().removeClass(
									"open");
							iscontinue = false;
							return false;
						});
					} else {
						if ($(this).attr("menu") == type) {
							outli.addClass("active").siblings().removeClass(
									"active");
						}
					}
				} else {
					return false;
				}
			});
}

function setpos(obj) {
	var par_ = $(obj).parent().height();
	var _thisindex = $(obj).index();
	var sidebarmeu_height = $(window).height() - (125 + (_thisindex * 40));
	var off_ = sidebarmeu_height - par_;

	if (par_ >= off_) {
		$(obj).parent().animate({
			scrollTop : par_
		}, 800);
	}
}

$(function() {
	initMenu();

	// 关闭所有input的输入建议
	$("#addform input[type='text']").prop("autocomplete", "off");

	// 点击菜单加选中样式
	$("#sidebar .nav > li").find("ul li").click(function() {
		$(this).addClass("active").siblings().removeClass("active");
	});

	if ($('.a-extend').length) {
		var $s = $('#searchbar')
		var sh = $s.panel('options').height;
		$('.a-extend').toggle(function() {
			$(this).addClass('a-intend');
			$s.panel('resize', {
				height : 26
			});
			$(window).trigger('resize');
		}, function() {
			$(this).removeClass('a-intend');
			$s.panel('resize', {
				height : sh
			});
			$(window).trigger('resize');
		});
	}

	$('.txt-focus').each(function() {
		$(this).foucsText();
	});

	if ($('.dt-group').length) {
		$('.dt-group').click(function() {
			var $p = $(this).parent();
			if ($p.hasClass('dl-intend')) {
				$p.removeClass('dl-intend');
			} else {
				$p.addClass('dl-intend');
			}
		});
	}

	// 异步加载公告
	$.ajax({
		url : domain + '/seller/systemNotice/getUnreadNotice',
		success : function(html_) {
			$("#userinfo").next().remove();
			$("#userinfo").after(html_);
		}
	});
});

function dataGridLoadSuccess(data, obj) {
	var this_ = obj ? $(obj) : $(this);
	// 无数据提示
	if (data.rows.length == 0) {
		// 表开头
		var body1 = this_.data().datagrid.dc.body1;
		// 数据列
		var body2 = this_.data().datagrid.dc.body2;
		body1
				.find('table')
				.html(
						'<tr class="datagrid-row"><td class="datagrid-td-rownumber">'
								+ '<div class="datagrid-cell-rownumber"></div></td></tr>');
		var b2table = body2.find('table');

		var width = b2table.find('tbody tr').width();
		b2table.width(width);

		b2table.find('tbody').html(
				"<tr class='datagrid-row'>"
						+ "<td style='text-align: center;'>没有数据</td></tr>");
	}
	if ('afterDataGridLoaded' in window) {
		afterDataGridLoaded(data);
	}
}

function queryParamsHandler() {
	var strParams = '{';
	$("[name^='q_']").each(function() {
		strParams += '"' + $(this).attr('name') + '"';
		strParams += ':';
		strParams += '"' + $(this).val() + '"';
		strParams += ',';
	});
	strParams = strParams.substr(0, strParams.length - 1);
	strParams += '}';
	return eval('(' + strParams + ')');
}

// 根据cookie的name取得value
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1)
				c_end = document.cookie.length;
			return decodeURIComponent(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}

function getErrMsg(errMsg) {
	if (errMsg) {
		if (errMsg.indexOf('"') == 0
				&& errMsg.lastIndexOf('"') == errMsg.length - 1) {
			errMsg = errMsg.substring(1, errMsg.length - 1);
		}
	}
	return errMsg;
}

// ajax防重复提交
function getCSRFTokenParam() {
	var cSRFTokenParam = 'CSRFToken=';
	cSRFTokenParam += $("input[name='CSRFToken']").val();
	cSRFTokenParam += '&CSRFMemKey=';
	cSRFTokenParam += $("input[name='CSRFMemKey']").val();
	return cSRFTokenParam;
}

function refrushCSRFToken(csrfToken) {
	$("input[name='CSRFToken']").val(csrfToken);
}

$(document).keydown(function(e){
	var curKey = e.which;
	if (curKey == 13) {
		return false;
	}
});
