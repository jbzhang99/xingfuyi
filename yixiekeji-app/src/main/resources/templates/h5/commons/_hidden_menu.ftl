<nav class="nav" id="nav">
   <div class="flex flex-align-center navbar">
   	   <div class="flex-1" ><a href="javascript:;" onclick="index()" class="block"><span class="fa fa-home"></span>首页</a></div>
   	   <div class="flex-1"><a href="javascript:;" onclick="cate()" class="block"><span class="fa fa-glass"></span>分类</a></div>
   	   <div class="flex-1"><a href="javascript:;" onclick="cart()" class="block"><span class="fa fa-cart-plus"></span>购物车</a></div>
   	   <div class="flex-1"><a href="javascript:;" onclick="member()" class="block"><span class="fa fa-user"></span>我的</a></div>
   </div>
</nav>
<script>
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

	if(isAndroid){
		function index(){
			window.phoneFunc.jsCallWebView("tohome");
		}
		
		function cate(){
			window.phoneFunc.jsCallWebView("tocategary");
		}
		
		function cart(){
			window.phoneFunc.jsCallWebView("tobuycar");
		}
		
		function member(){
			window.phoneFunc.jsCallWebView("tome");
		}
	}else if(isiOS){
		function index(){
			window.webkit.messageHandlers.tohome.postMessage(null);
		}
		
		function cate(){
			window.webkit.messageHandlers.tocategary.postMessage(null);
		}
		
		function cart(){
			window.webkit.messageHandlers.tobuycar.postMessage(null);
		}
		
		function member(){
			window.webkit.messageHandlers.tome.postMessage(null);
		}
	}
</script>