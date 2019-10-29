<#include "/h5/commons/_head.ftl" />
<body>
   <!-- 头部 -->
   <header id="header">
   	  <div class="flex flex-align-center head-bar">
   	  	 <div class="flex-1 text-left">
   	  	 	<a href="javascript:ejsPageBack();">
   	  	 		<span class="fa fa-angle-left"></span>
   	  	 	</a>
   	  	 </div>
   	  	 <div class="flex-2 text-center">${(productCate.name)!''}</div>
   	  	 <div class="flex-1 text-right" id="fa-bars"><span class="fa fa-bars"></span></div>
   	  </div>
   	  <#include "/h5/commons/_hidden_menu.ftl" />
   </header>
   <!-- 头部 end-->
   
	<div class="containe" id="container">
		<nav>
		  <div class="flex flex-align-center nav-2-bar" id="nav-2-bar">
			<div class="flex-1"><a href="javascript:void(0)" onclick="filterSort(0)" <#if sort?? && sort == 0>class="btn-sort"</#if>>默认</a></div>
			<div class="flex-1">
				<#if sort??>
					<#if sort == 0 || sort == 1 || sort == 2>
						<a href="javascript:void(0)" onclick="filterSort(3)">价格</a>
					<#else>
						<#if sort == 3>
							<a href="javascript:void(0)" onclick="filterSort(4)" class="btn-sort">价格<span class="fa fa-long-arrow-up"></span></a>
						</#if>
						<#if sort == 4>
							<a href="javascript:void(0)" onclick="filterSort(3)" class="btn-sort">价格<span class="fa fa-long-arrow-down"></span></a>
						</#if>
					</#if>
				</#if>
			</div>
			<div class="flex-1"><a href="javascript:void(0)" onclick="filterSort(1)" <#if sort?? && sort == 1>class="btn-sort"</#if>>销量</a></div>
			<div class="flex-1"><a href="javascript:void(0)" onclick="filterSort(2)" <#if sort?? && sort == 2>class="btn-sort"</#if>>评论</a></div>
			<div class="flex-1" id="list-filter">筛选&nbsp;<span class="fa fa-caret-right"></span></div>
		  </div>
		</nav>

		<div class="listbox">
		
			<#if producListVOs??>
				<#list producListVOs as producListVO>
				    <a href="${(domainUrlUtil.urlResources)!}/product/${(producListVO.id)!0}.html" class="block">
					<dl class="flex list-dl">
						<dt><img src="${(domainUrlUtil.staticResources)!''}/img/loading.gif" data-echo="${(domainUrlUtil.imageResources)!}${(producListVO.masterImg)!""}" alt="" width="100" height="100"></dt>
						<dd class="padl flex-2">
							<div class="product_name">${(producListVO.name1)!""}</div>
							<div class="product-desript">
							   <p class="clr53">￥<font>${(producListVO.malMobilePrice)?string("0.00")!""}</font></p>
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
		<div class="push_msk" id="push_msk"></div>
	</div>
	<!-- 主体结束 -->

	<!-- footer -->
    
	<!-- 筛选层 -->
	<div class="slidebar" id="slidebar">
		<div class="flex flex-align-center head-bar">
	   	  	 <div class="flex-1 text-left" id="sliderarrow"><span class="fa fa-angle-left"></span></div>
	   	  	 <div class="flex-2 text-center">筛选</div>
	   	  	 <div class="flex-1 text-right"><a href="${(domainUrlUtil.urlResources)!}/cate/${(productCate.id)!''}.html">清除</a></div>
   	   </div>
   	   <div class="s-container overtouch" style="height:100%;">
   	   	    <div class="l-chioce">
	   	    	<#if store?? && store==0>
				    <a class="btn btn-default" href="javascript:void(0)" onclick="filterStore(1)"><i class="fa fa fa-check"></i>仅显示有货</a>
				<#else>
					<a class="btn btn-default current" href="javascript:void(0)" onclick="filterStore(0)"><i class="fa fa fa-check"></i>仅显示有货</a>
				</#if>
			
				<#if doSelf?? && doSelf==0>
				    <a class="btn btn-default" href="javascript:void(0)" onclick="filterDoSelf(1)"><i class="fa fa fa-check"></i>平台自营</a>
				<#else>
					<a class="btn btn-default current" href="javascript:void(0)" onclick="filterDoSelf(0)"><i class="fa fa fa-check"></i>平台自营</a>
				</#if>
   	   	    </div>
   	   	   
   	   	   <div class="menubox" >
   	   	   	   <h2 class="flex flex-pack-justify">
   	   	   	   	   <div>商品品牌</div>
   	   	   	   	   <div><#if brandId!=0><font class="cl70 font12">${(productBrandName)!''}&nbsp;</font></#if><span class="fa fa-angle-down"></span></div>
   	   	   	   </h2>
   	   	   	   <ul class="menu-2-ul">
   	   	   	   		<#if brandId==0>
	   	   	   	   		<li class="flex flex-pack-justify clr53">
		   	   	   	   	  	  <div><i></i>全部</div>
		   	   	   	   	  	  <div><span class="fa fa-check" style="display:inline-block;"></span></div>
		   	   	   	   	</li>
	   	   	   	   	<#else>
	   	   	   	   		<li class="flex flex-pack-justify" onclick="filterBrand(0)">
		   	   	   	   	  	  <div><i></i>全部</div>
		   	   	   	   	  	  <div><span class="fa fa-check"></span></div>
		   	   	   	   	</li>
	   	   	   	   	</#if>
   	   	   	      <#if productBrands??>
				    <#list productBrands as productBrand>
		   	   	   	   	  <li class="flex flex-pack-justify <#if brandId==productBrand.id>clr53</#if>" onclick="filterBrand(${(productBrand.id)!})">
		   	   	   	   	  	  <div><i></i>${(productBrand.name)!""}</div>
		   	   	   	   	  	  <div><span class="fa fa-check"  <#if brandId==productBrand.id>style="display:inline-block;"</#if>></span></div>
		   	   	   	   	  </li>
   	   	   	   	  	</#list>
				  </#if>
   	   	   	   </ul>
   	   	   </div>
		   
		   <#if productTypeAttrVOsAll??&&(productTypeAttrVOsAll?size>0)>
		   	 <#list productTypeAttrVOsAll as productTypeAttrVO>
	   	   	   <div class="menubox">
	   	   	   	   <h2 class="flex flex-pack-justify">
	   	   	   	   	   <div>${(productTypeAttrVO.name)!""}</div>
	   	   	   	   	   <div><#if productTypeAttrVO.isChoice != 0><font class="cl70 font12">${(productTypeAttrVO.choiceName)!""}&nbsp;</font></#if><span class="fa fa-angle-down"></span></div>
	   	   	   	   </h2>
	   	   	   	   <ul class="menu-2-ul">
	   	   	   	   	  <#if productTypeAttrVO.isChoice == 0>
		   	   	   	   	  <li class="flex flex-pack-justify clr53">
		   	   	   	   	  	  <div>全部</div>
		   	   	   	   	  	  <div><span class="fa fa-check" style="display:inline-block;"></span></div>
		   	   	   	   	  </li>
		   	   	   	   	   <#if productTypeAttrVO.values??>
						  	 <#list productTypeAttrVO.values as value>
				   	   	   	   	  <li class="flex flex-pack-justify" onclick="filterWhereAllNew('-${(productTypeAttrVO.id)!}_${(value_index)!}')">
				   	   	   	   	  	  <div>${(value)!""}</div>
				   	   	   	   	  	  <div><span class="fa fa-check"></span></div>
				   	   	   	   	  </li>
		   	   	   	   	     </#list>
					     </#if>
	   	   	   	   	  <#else>
		   	   	   	   	  <li class="flex flex-pack-justify" onclick="filterWhereAll('-${(productTypeAttrVO.choiceAll)!}')">
		   	   	   	   	  	  <div>全部</div>
		   	   	   	   	  	  <div><span class="fa fa-check"></span></div>
		   	   	   	   	  </li>
		   	   	   	   	  
		   	   	   	   	   <#if productTypeAttrVO.values??>
						  	 <#list productTypeAttrVO.values as value>
				   	   	   	   	  <li class="flex flex-pack-justify <#if value_index==productTypeAttrVO.isChoiceIndex>clr53</#if>" onclick="filterWhereAllChange('-${(productTypeAttrVO.choiceAll)!}', '-${(productTypeAttrVO.id)!}_${(value_index)!}')">
				   	   	   	   	  	  <div>${(value)!""}</div>
				   	   	   	   	  	  <div><span class="fa fa-check" <#if value_index==productTypeAttrVO.isChoiceIndex>style="display:inline-block;"</#if>></span></div>
				   	   	   	   	  </li>
		   	   	   	   	     </#list>
					     </#if>
	   	   	   	   	  </#if>
	   	   	   	   	  
	   	   	   	   </ul>
	   	   	   </div>
			</#list>
		</#if>
   	   </div>
	</div>
	<!-- 筛选层  end -->
	
	<!-- footer -->
	<#include "/h5/commons/_footer.ftl" />
	<#include "/h5/commons/_statistic.ftl" />

<script type="text/javascript">
function filterWhereAll(delfilter) {
	var urlPath = "${(urlPath)!}";
	var url = urlPath.replace(delfilter, "");
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterWhereAllNew(filter) {
	var urlPath = "${(urlPath)!}";
	self.location="${(domainUrlUtil.urlResources)!}/" + urlPath + filter + ".html";
}

function filterWhereAllChange(delfilter, changefilter) {
	var urlPath = "${(urlPath)!}";
	var url = urlPath.replace(delfilter, "");
	url = url + changefilter;
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterSort(sort) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
	    if(i == 3) {
	    	url += sort;
	    } else {
	    	url += urlPaths[i];
	    }
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterStore(store) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
	    if(i == 5) {
	    	url += store;
	    } else {
	    	url += urlPaths[i];
	    }
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterDoSelf(doSelf) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
	    if(i == 4) {
	    	url += doSelf;
	    } else {
	    	url += urlPaths[i];
	    }
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

function filterBrand(brand) {
	var urlPath = "${(urlPath)!}";
	var urlPaths = urlPath.split("-");
	var url = "";
	for(var i=0; i<urlPaths.length; i++) {
	    if(i == 6) {
	    	url += brand;
	    } else {
	    	url += urlPaths[i];
	    }
		if((i+1) != urlPaths.length) {
			url += "-";
		}
	}
	self.location="${(domainUrlUtil.urlResources)!}/" + url + ".html";
}

$(function(){
	$("#product_list_more_json").click(function(){
		var list_page = $("#list_page").val();
		list_page++;
		$("#list_page").val(list_page);
		
		var urlPath = "${(urlPath)!}";
		var urlPaths = urlPath.split("-");
		var url = "listjson-";
		for(var i=1; i<urlPaths.length; i++) {
		    if(i == 2) {
		    	url += list_page;
		    } else {
		    	url += urlPaths[i];
		    }
			if((i+1) != urlPaths.length) {
				url += "-";
			}
		}
		var urljson = "${(domainUrlUtil.urlResources)!}/" + url + ".html";
		
		var listJsonHtml = "";
		$.ajax({
            type:"get",
            url: urljson,
            dataType: "json",
            cache:false,
            success:function(data){
                if (data.success) {
                    $.each(data.data, function(i, product){
                    	listJsonHtml += "<a href='${(domainUrlUtil.urlResources)!}/product/" + product.id+ ".html' class='block'>";
                    	listJsonHtml += "<dl class='flex list-dl'>";
                    	listJsonHtml += "<dt><img src='${(domainUrlUtil.imageResources)!}" + product.masterImg + "' width='100' height='100'></dt>";
                    	listJsonHtml += "<dd class='padl flex-2'>";
                    	listJsonHtml += "<div class='product_name'>" + product.name1 + "</div>";
                    	listJsonHtml += "<div class='product-desript'>";
                    	listJsonHtml += "<p class='clr53'>￥<font>" + parseFloat(product.malMobilePrice).toFixed(2) + "</font></p>";
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
