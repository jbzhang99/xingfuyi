  		<!-- footer -->
		<!-- 页脚 -->
		<div class='footer '>
			<div class="slogen">
				<div class="container">
					<span class="item fore1">
						<img src="${(domainUrlUtil.staticResources)!}/img/service_items_1.png" width="100%" height="100%">
					</span>
					<span class="item fore1">
						<img src="${(domainUrlUtil.staticResources)!}/img/service_items_2.png" width="100%" height="100%">
					</span>
					<span class="item fore1">
						<img src="${(domainUrlUtil.staticResources)!}/img/service_items_3.png" width="100%" height="100%">
					</span>
					<span class="item fore1">
						<img src="${(domainUrlUtil.staticResources)!}/img/service_items_4.png" width="100%" height="100%">
					</span>
				</div>
			</div>
				
			<div class='wraper '>
				<ul id="newstypes">
				
				</ul>
			</div>
		</div>
		<div class='wraper' id='footer'>
			<p>
				<a target="_blank" href='${(domainUrlUtil.urlResources)!}/index.html'>首页</a>
						|
				<a target="_blank" href='#'>招聘英才</a>
						|
				<a target="_blank" href='#'>合作及洽谈</a>
						|
				<a target="_blank" href='#'>联系我们</a>
						|
				<a target="_blank" href='#'>关于幸福易</a>
			</p>
  				Copyright 2019 北京齐驱科技公司 Inc.,All rights reserved.
  			<br>
  				Powered by 
  				<span class='vol'>
  					<font class='b'>北京齐驱科技公司</font>
  				</span>
		</div>
		<!-- footer -->
		<script type="text/javascript">
			$(function(){
				$.ajax({
					url:'${(domainUrlUtil.urlResources)!}/news/footerNews.html',
					dataType: "json",
        			cache:false,
        			type:"get",
					success:function(data){
						if (data.success) {
							var html = "";
							$.each(data.data, function(i, newstype){
								html += "<li><dl>"+
										"<dt>"+newstype.name+"</dt>";
										$.each(newstype.news,function(nidx,nw){
											html += 
											"<dd>"+
												"<i></i>"+
												"<a href='${(domainUrlUtil.urlResources)!}/news/"+
														nw.id+".html' title='"+nw.title+"' target='_blank'>"+nw.title+"</a>"+
											"</dd>";
										});
								html += "</dl></li>";
							});
							$("#newstypes").html(html);
						}
					}
				});
				
				$.ajax({
					url:'${(domainUrlUtil.urlResources)!}/searchKeyword.html',
					dataType: "json",
        			cache:false,
        			type:"get",
					success:function(data){
						if (data.success) {
							var html = "";
							$.each(data.data, function(i, kd){
								html += "<a href='${(domainUrlUtil.urlResources)!}/search.html?keyword=" + kd + "' target='_blank'>" + kd + "</a>";
							});
							$("#keywordIDs").html(html);
						}
					}
				});
				
			});
		</script>
  </body> 
</html>


<script type="text/javascript">

// 点击关闭按钮
function closeLi(o){
	$(o).parent().css({"display":"none"});
}
$(function(){
	// 鼠标失去焦点
	// 在document绑定mousedown，点击非ul区域隐藏ul
	$(document).bind('mousedown',function(event){
		var $target = $(event.target);
		if((!($target.parents("").andSelf().is('ul'))) && (!$target.is(".text"))){
			var $hideBox = $(".hide-box")
			if($hideBox.css("display") != "none"){
					$hideBox.hide();
				}		
		}
	});
	
	// 键盘抬起触发
	$('.text').keyup(function(){
		if($('.text').val()==""){
			$('.hide-box').hide();
		}else{
			$('.hide-box').show();
			var keyword = $("#keyword").val();
			if(keyword != '') {
				var urljson = "${(domainUrlUtil.urlResources)!}/get_search_record.html?keyword=" + keyword;
				$.ajax({
		            type:"get",
		            url: urljson,
		            dataType: "json",
		            cache:false,
		            success:function(data){
		                if (data.success) {
		                
		                	var htmlInfo = "";
		                    $.each(data.data, function(i, searchrecord){
								htmlInfo += "<li><a href='${(domainUrlUtil.urlResources)!}/search.html?keyword=" + searchrecord.keyword + "'><div class='search-item'>"+ searchrecord.keyword +"</div>";
								htmlInfo += "<div class='search-count'>"+ searchrecord.keywordIndex +"</div></a></li>";
		                    })
		                    htmlInfo +="<li class='last-li' onclick='closeLi(this)'>关闭</li>";
					
							if(data.total > 0) {
								$(".i-search ul").html(htmlInfo);
								$(".i-search ul").css({"display":"block"});
							}
		                }
		            }
		        });
			}
		}
	});

	// 鼠标获得焦点
	$('.text').focus(function(){
		var keyword = $("#keyword").val();
		if(keyword == '') {
			var urljson = "${(domainUrlUtil.urlResources)!}/get_search_logs.html";
			$.ajax({
	            type:"get",
	            url: urljson,
	            dataType: "json",
	            cache:false,
	            success:function(data){
	                if (data.success) {
	                	var htmlInfo = "";
	                    $.each(data.data, function(i, searchlogs){
							htmlInfo += "<li><a target='_blank' href='${(domainUrlUtil.urlResources)!}/search.html?keyword=" + searchlogs.keyword + "'><div class='search-item'>"+ searchlogs.keyword +"</div>";
							htmlInfo += "<div class='search-count'>搜索历史</div></a></li>";
	                    })
	                    htmlInfo +="<li class='last-li' onclick='closeLi(this)'>关闭</li>";
						
						if(data.total > 0) {
							$(".i-search ul").html(htmlInfo);
							$(".i-search ul").css({"display":"block"});
						}
	                }
	            }
	        });
		}

	});

})

var memberId = 0;
<#if user??>
	memberId = ${(user.id)!0};
</#if>
function getBrowserInfo() {
	var agent = navigator.userAgent.toLowerCase() ;
	var regStr_ie = /msie [\d.]+;/gi;
	var regStr_ff = /firefox\/[\d.]+/gi;
	var regStr_chrome = /chrome\/[\d.]+/gi;
	var regStr_saf = /safari\/[\d.]+/gi;
	
	if(agent.indexOf("msie") > 0) {
		return agent.match(regStr_ie) ;
	}

	//firefox
	if(agent.indexOf("firefox") > 0) {
		return agent.match(regStr_ff) ;
	}

	//Chrome
	if(agent.indexOf("chrome") > 0) {
		return agent.match(regStr_chrome) ;
	}

	//Safari
	if(agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
		return agent.match(regStr_saf) ;
	}
}
var browser = getBrowserInfo() ;
var verinfo = (browser+"").replace(/[^0-9.]/ig,"");

var ref = document.referrer;
var hrf = window.location.href;
document.write('<img width="1" height="1" style="position:absolute;display: none;" src="${(domainUrlUtil.urlResources)!}/browse_Logs.html?ref='+ref
		+'&hrf='+ hrf + '&memberId='+ memberId + '&browser='+ browser + '&verinfo=' + verinfo + '" />');
</script>
