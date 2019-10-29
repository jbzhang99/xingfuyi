<#include "/h5/commons/_head.ftl" />

<body class="bgf2">
   <!-- 头部 -->
   <header id="header">
   	  <form action="${(domainUrlUtil.urlResources)!}/search.html" method="get">
	   	  <div class="flex flex-align-center head-bar">
	   	     <div class="text-left pad-r">
	   	  	 	<a href="javascript:ejsPageBack();">
	   	  	 		<span class="fa fa-angle-left"></span>
	   	  	 	</a>
	   	  	 </div>
	   	  	 <div class="flex-2 search-input">
	   	  	 	<input type="text" name="keyword" value="${(keyword)!''}" class="form-control" placeholder="请输入您要搜索的关键字">
	   	  	 </div>
	   	  	 <div ><button type="submit" class="search_btn"><i class="fa fa-search"></i></button></div>
	   	  </div>
   	  </form>
   </header>
   <!-- 头部 end-->
   
	<div class="s-container" >
	    <div class="flex pad10 hotkeybox" <#if keywordNumber=1>style="display:none;"</#if> >
	    	<div class="pad-r">热搜</div>
	    	<div class="flex-2 hotkeyword">
	    	<#if keywords??> 
				<#list keywords as keyword>
	    	  		<a href="${(domainUrlUtil.urlResources)!}/search.html?keyword=${(keyword)!''}">${(keyword)!''}</a>
	    	  	</#list> 
			</#if>
	    	</div>
	    </div>
        
        <!-- 搜索结果层 -->
	    <div class="listbox" <#if keywordNumber=0>style="display:none;"</#if>>
		   <#if producListVOs??>
				<#list producListVOs as producListVO>
				    <a href="${(domainUrlUtil.urlResources)!}/product/${(producListVO.id)!0}.html" class="block">
					<dl class="flex list-dl">
						<dt><img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!}${(producListVO.masterImg)!""}" alt="" width="100" height="100"></dt>
						<dd class="padl flex-2">
							<div class="product_name"><#noescape>${(producListVO.name1)!""}</#noescape></div>
							<div class="product-desript">
							   <p class="clr53">￥<font>${(producListVO.malMobilePrice)!""}</font></p>
							   <p class="ratings">
							   		<#if producListVO.productStock gt 0>
							   			<span>有货</span>
							   		<#else>
							   			<span>无货</span>
							   		</#if>
							   		&nbsp;&nbsp;
							   		<font>${(producListVO.commentsNumber)!"0"}</font>条评价</p>
							</div>
						</dd>
					</dl>
					</a>
				</#list>
			</#if>
			<div id="product_list_more"></div>
			
			<#if producListVOs?? && producListVOs?size==pagesize>
				<div id="product_list_more_json" class="text-center font14 pad-top2">查看更多<i class="fa fa-angle-double-down"></i></div>
				<div id="product_list_more_json_no" class="text-center font14 pad-top2" style="display:none;">已展示全部商品</div>
			<#else>
				<div id="product_list_more_json_no" class="text-center font14 pad-top2">已展示全部商品</div>
			</#if>
			<input type="hidden"  name="list_page" id="list_page" value="1" />
			
	</div>

    </div>
	<!-- 主体结束 -->

	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

 <script type="text/javascript">
$(function(){
	$("#product_list_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var urljson = "${(domainUrlUtil.urlResources)!}/searchJson.html?keyword=${keyword!''}&pageNum=" + list_page;
		
		var listJsonHtml = "";
		$.ajax({
            type:"get",
            url: urljson,
            dataType: "json",
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.rows, function(i, product){
                    	listJsonHtml += "<a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' class='block'>";
                    	listJsonHtml += "<dl class='flex list-dl'>";
                    	listJsonHtml += "<dt><img src='${(domainUrlUtil.imageResources)!}" + product.masterImg + "' width='100' height='100'></dt>";
                    	listJsonHtml += "<dd class='padl flex-2'>";
                    	listJsonHtml += "<div class='product_name'>" + product.name1 + "</div>";
                    	listJsonHtml += "<div class='product-desript'>";
                    	listJsonHtml += "<p class='clr53'>￥<font>" + product.malMobilePrice + "</font></p>";
                    	listJsonHtml += "<p class='ratings'>";
                    	if(product.productStock > 0) {
                    		listJsonHtml += "<span>有货</span>";
                    	}else {
                    		listJsonHtml += "<span>无货</span>";
                    	}
                    	listJsonHtml += "&nbsp;&nbsp;";
                    	listJsonHtml += "<font>" + product.commentsNumber + "</font>条评价</p>";
                    	listJsonHtml += "</div></dd></dl></a>";
                    })
                    $("#product_list_more").append(listJsonHtml);
                    if ((data.total) == ${(pagesize)!}) {
                        $("#product_list_more_json").show();
                        $("#product_list_more_json_no").hide();
                    } else {
                        $("#product_list_more_json").hide();
                        $("#product_list_more_json_no").show();
                    }
                }
            }
        });
        
	});
	
})
 </script>

 

</body>
</html>