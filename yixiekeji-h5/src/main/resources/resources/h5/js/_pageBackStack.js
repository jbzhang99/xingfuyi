	XBack.listen(function(){
		console.log("物理返回按钮被触发了。。。。");
		ejsPageBack(null);
	});

	$(function(){
		
		console.log("转义后的pageBackStack=" + decodeURIComponent(getPageBackCookie('pageBackStack')));
		
		var intoBackStackCurr = true;
		if("undefined" == typeof intoBackStack){ 
			intoBackStackCurr = true;
		} else {
			intoBackStackCurr = intoBackStack;
		}
		
		console.log("intoBackStackCurr=" + intoBackStackCurr);
		if (intoBackStackCurr != null && !intoBackStackCurr) {
			return false;
		}
		var currUrl = window.location.href;
		var referrer = document.referrer;
		
		var pageBackStack = getPageBackCookie('pageBackStack');
		console.log("pageBackStack=" + pageBackStack);
		var trueCurrUrl = currUrl;
		console.log("trueCurrUrl="+trueCurrUrl);
		if (currUrl != null) {
			currUrl = currUrl.replace('?pageBack=1','?');
			currUrl = currUrl.replace('&pageBack=1','');
			currUrl = currUrl.replace('pageBack=1','');
		}
		console.log("currUrl="+currUrl);
		console.log("referrer=" + referrer);
		
		if (pageBackStack == null || pageBackStack.length < 1) {
			// 如果cookie中存的数据是空，则新建一个存入
			var pageBackStack = new Array();
			pageBackStack[0] = encodeURIComponent(currUrl);
		} else {
			// 如果不是空
			pageBackStack = pageBackStack.split(',');
			// 判断refer是否与数组的最后一个相同，相同则不再增加
			var stactIndex = pageBackStack.length;
			var lastBackUrl = pageBackStack[stactIndex - 1];
			lastBackUrl = decodeURIComponent(lastBackUrl);
			var reg = new RegExp('pageBack\\=1');
			
			// 如果页面跳转时只是？后面的内容不一样则认为是同一个页面
			var currUrlProtocol = window.location.protocol;
			var currUrlHost = window.location.host;
			var currUrlPath = window.location.pathname;
			console.log("currUrlHost=" +currUrlProtocol+"//"+currUrlHost + currUrlPath);
			if (referrer.indexOf(currUrlProtocol + "//" + currUrlHost + currUrlPath) == 0) {
				// 如果是同一个页面则把上一个删了加本次的
				if (pageBackStack.length > 1) {
					pageBackStack.pop();
				}
				pageBackStack.push(encodeURIComponent(currUrl));
				console.log("如果是同一个页面则把上一个删了加本次的");
			}else if (lastBackUrl.indexOf(currUrlProtocol + "//" + currUrlHost + currUrlPath) == 0) {
				if (pageBackStack.length > 1) {
					pageBackStack.pop();
				}
				pageBackStack.push(encodeURIComponent(currUrl));
				console.log("如果是上一个页面和本地页面相同则把上一个删了加本次的");
			} else {
				console.log("如果最后数据中最后一个url不是当前页面的referrer则增加");
				// 如果最后数据中最后一个url不是当前页面的url则增加
				if (currUrl.indexOf(domain+'/0-') ==0 && referrer.indexOf(domain+'/0-') ==0) {
					console.log("列表页处理");
					if (pageBackStack.length > 1) {
						pageBackStack.pop();
					}
					pageBackStack.push(encodeURIComponent(currUrl));
				} else {
					console.log("增加一个返回");
					pageBackStack.push(encodeURIComponent(currUrl));
				}
			}
			// 如果数组长度超过20，则删除第一个url
			if(stactIndex>20){
				pageBackStack.splice(0,1);
			}
		}
		console.log("保存前的pageBackStack=" + decodeURIComponent(pageBackStack));
		// $.cookie("pageBackStack", pageBackStack.join(","), {path:'/', domain:"${(domainUrlUtil.EJS_COOKIE_DOMAIN)!}"});
		document.cookie="pageBackStack=" +pageBackStack.join(",")+ ";path=/;domain="+domainCookie; 
	});

	function ejsPageBack(defaultBackUrl) {
		var intoBackStackCurr = true;
		if("undefined" == typeof intoBackStack){
			intoBackStackCurr = true;
		} else {
			intoBackStackCurr = intoBackStack;
		}
		var pageBackStack = getPageBackCookie("pageBackStack");
		console.log("pageBackStack=" + pageBackStack);
		if (pageBackStack == null || pageBackStack.length < 1) {
			if (defaultBackUrl != null && defaultBackUrl.length > 0) {
				window.location.href = defaultBackUrl;
			} else {
				window.location.href = domain;
			}
			return false;
		} else {
			pageBackStack = pageBackStack.split(',');
			var stactIndex = pageBackStack.length;
			
			if (intoBackStackCurr && pageBackStack.length > 1) {
				pageBackStack.pop();
			}
			var backUrl = pageBackStack.pop();
			
			console.log("backUrl=" + backUrl);
			
			if (backUrl == null || backUrl=="null" || backUrl.length < 1) {
				if (defaultBackUrl != null && defaultBackUrl.length > 0) {
					window.location.href = defaultBackUrl;
				} else {
					window.location.href = domain;
				}
				return false;
			} else {
				backUrl = decodeURIComponent(backUrl);
				
				if (defaultBackUrl != null && defaultBackUrl.length > 0) {
					backUrl = defaultBackUrl;
				}
				
				var reg = new RegExp('\\?');
				if (!reg.test(backUrl)) {
					backUrl = backUrl + "?pageBack=1";
				} else {
					backUrl = backUrl + "&pageBack=1";
				}
				// $.cookie("pageBackStack", pageBackStack.join(","), {path:'/', domain:"${(domainUrlUtil.EJS_COOKIE_DOMAIN)!}"});
				// document.cookie="pageBackStack=" +pageBackStack.join(",")+ ";path=/;domain=${(domainUrlUtil.EJS_COOKIE_DOMAIN)!}";
				document.cookie="pageBackStack=" +pageBackStack.join(",")+ ";path=/;domain="+domainCookie; 
				window.location.href = backUrl;
				return false;
			}
		}
	}

	/*
	 *删除栈里面最后一个返回URL
	 */
	function delLastPageBack() {
		var pageBackStack = getPageBackCookie("pageBackStack");
		console.log("pageBackStack=" + pageBackStack);
		if (pageBackStack != null && pageBackStack.length > 0) {
			pageBackStack = pageBackStack.split(',');
			pageBackStack.pop();
		}
		// $.cookie("pageBackStack", pageBackStack.join(","), {path:'/', domain:"${(domainUrlUtil.EJS_COOKIE_DOMAIN)!}"});
		document.cookie="pageBackStack=" +pageBackStack.join(",")+ ";path=/;domain="+domainCookie; 
	}
	
	function getPageBackCookie(cookieName) {
	    var cookieValue = null; //返回cookie的value值 
	    if (document.cookie && document.cookie != '') {
	        var cookies = document.cookie.split(';'); //将获得的所有cookie切割成数组 
	        console.log("cookies=" + document.cookie);
	        console.log("cookies=" + cookies);
	        for (var i = 0; i < cookies.length; i++) {
	            var cookie = cookies[i]; //得到某下标的cookies数组 
	            if (cookie.substring(0, cookieName.length + 2).trim() == cookieName.trim() + "=") { //如果存在该cookie的话就将cookie的值拿出来 
	                cookieValue = cookie.substring(cookieName.length + 2, cookie.length);
	                break
	            }
	        }
	    }
	    return decodeURIComponent(cookieValue);
	}
