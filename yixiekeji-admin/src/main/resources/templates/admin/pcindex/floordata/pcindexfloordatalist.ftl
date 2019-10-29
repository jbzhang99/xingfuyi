<#include "/admin/commons/_detailheader.ftl" />
<link rel="stylesheet" 
	href="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/jquery.boxer/css/jquery.fs.boxer.css" type="text/css">
<script type="text/javascript" 
	src="${(domainUrlUtil.staticResources)!}/resources/admin/jslib/jquery.boxer/js/jquery.fs.boxer.js"></script>

<style>
#newstypeTree img {
	max-width: 390px;
	max-height: 290px;
}
</style>
<script language="javascript">
	function loadSuccess(data){
		if(data.rows.length==0){//无数据提示
			var body1 = $(this).data().datagrid.dc.body1;
			var body2 = $(this).data().datagrid.dc.body2;
			body1.find('table').html('<tr class="datagrid-row"><td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber"></div></td></tr>');
			body2.find('table').width('100%').find('tbody').append('<tr><td style="height: 25px; text-align: center;">没有数据</td></tr>');
		}
		
		$(".colorbox").boxer();
	}
	var statusBox;
	$(function(){
	//为客户端装配本页面需要的字典数据,多个用逗号分隔
		<#noescape>
			statusBox = eval('(${initJSCodeContainer("DATA_TYPE")})');
		</#noescape>

		$("#a-gridAdd").click(function(){
	 		window.location.href="${(domainUrlUtil.urlResources)!}/admin/pcindex/floordata/add";
		});
		$("#a-gridSearch").click(function(){
	 		$('#dataGrid').datagrid('load',queryParamsHandler());
		});
		$("#a-gridEdit").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
			if(!selected) {
				$.messager.alert('提示','请选择操作行。');
				return;
			}
	 		window.location.href="${(domainUrlUtil.urlResources)!}/admin/pcindex/floordata/edit?pcIndexFloorDataId="+selected.id;
		});
		
		$("#a-gridDel").click(function(){
            var selectedCode = $('#dataGrid').datagrid('getSelected');
            if(!selectedCode){
                $.messager.alert('提示','请选择操作行。');
                return;
            }

            $.messager.confirm('提示', '确定删除吗？', function(r){
                if (r){
                    $.messager.progress({text:"提交中..."});
                    $.ajax({
                        type:"POST",
                        url: "${domainUrlUtil.urlResources}/admin/pcindex/floordata/delete",
                        dataType: "json",
                        data: "id="+selectedCode.id,
                        cache:false,
                        success:function(data, textStatus){
                            if (data.success) {
                                $('#dataGrid').datagrid('reload',queryParamsHandler());
                            }else{
                                $.messager.alert('提示', data.message);
                            }
                            $.messager.progress('close');
                        }
                    });
                }
            });
        });
        
		$("#newstypeWin").window({
			width : 666,
			height : 420,
			title : "链接图片",
			closed : true,
			shadow : false,
			modal : true,
			collapsible : false,
			minimizable : false,
			maximizable : false
		});
        
		<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
	})

	function statusFormat(value,row,index){
		return statusBox["DATA_TYPE"][value];
	}

	function imageFormat(value, row, index) {
		if(!value){
			return "-";
		} else{
			return "<a href='${domainUrlUtil.imageResources}" + 
	  			value + "' style='color:#276892' rel='gallery' class='colorbox'>点击查看</a>";
		}
	}

	function addFormat(value, row, index){
		if(!value){
			return "-";
		} else{
			return "<a href='" + value + "' style='color:#276892' target='_blank'>点击访问</a>";
		}
	}
	
	function nameFormat(value, row, index){
		return value?value:"-";
	}
</script>



<div id="searchbar" data-options="region:'north'" style="margin: 0 auto;" border="false">
	<div id="searchbox" class="head-seachbox">
		<h2 class="h2-title">PC端首页楼层分类数据列表 <span class="s-poar"><a class="a-extend" href="#">收起</a></span></h2>
		<div class="w-p99 marauto searchCont">
		<form class="form-search" onsubmit="return false;" action="" method="get" id="queryForm" name="queryForm">
			<div class="fluidbox"><!-- 不分隔 -->
				<p class="p4 p-item">
					<label class="lab-item">楼层：</label>
					<select name="q_floorClassId" id="q_floorClassId" value="${q_floorClassId!''}">
                    	<option value="">--全部--</option>
                        <#if floorClasss?? && floorClasss?size &gt; 0>
                        	<#list floorClasss as floorClass>
								<option value="${(floorClass.id)!}">${(floorClass.name)!}</option>
							</#list>
						</#if>
				    </select>
				</p>
				<p class="p4 p-item">数据类型：<@cont.select id="q_dataType" value="${(q_dataType)!''}" codeDiv="DATA_TYPE" style="width:80px" /></p>
			</div>
		</form>
		</div>
	</div>
</div>


<div data-options="region:'center'" border="false">
	<table id="dataGrid" class="easyui-datagrid"
			data-options="rownumbers:true
						,singleSelect:true
						,autoRowHeight:false
						,fitColumns:true
						,collapsible:true
						,toolbar:'#gridTools'
						,striped:true
						,pagination:true
						,pageSize:'${pageSize}'
						,fit:true
    					,url:'${(domainUrlUtil.urlResources)!}/admin/pcindex/floordata/list'
    					,queryParams:queryParamsHandler()
    					,onLoadSuccess:loadSuccess
    					,method:'get'">
		<thead>
			<tr>
				<th field="floorClassName" width="100" align="center" halign="center">楼层分类</th>
	            <th field="dataType" width="70" align="center" halign="center" formatter="statusFormat">数据类型</th>
				<th field="productName" width="200" align="center" halign="center" formatter="nameFormat">商品名称</th>
				<th field="title" width="150" align="left" halign="center">图片标题</th>
				<th field="linkUrl" width="60" align="center" halign="center" formatter="addFormat">链接地址</th>
	            <th field="image" width="60" align="center" halign="center" formatter="imageFormat">查看图片</th>
	            <th field="orderNo" width="50" align="center" halign="center">排序号</th>
	            <th field="remark" width="150" align="left" halign="center">备注</th>
			</tr>
		</thead>
	</table>
	<div id="gridTools">
		<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<@shiro.hasPermission name="/admin/pcindex/floordata/add">
		<a id="a-gridAdd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/pcindex/floordata/edit">
		<a id="a-gridEdit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		</@shiro.hasPermission>
		<@shiro.hasPermission name="/admin/pcindex/floordata/delete">
		<a id="a-gridDel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-delete" plain="true">删除</a>
		</@shiro.hasPermission>
	</div>
</div>

<div id="newstypeWin">
	<form id="newstypeForm" method="post">
		<ul id="newstypeTree"
			style="margin-top: 10px; margin-left: 10px; max-height: 370px; overflow: auto; border: 1px solid #86a3c4;"></ul>
	</form>
</div>

<#include "/admin/commons/_detailfooter.ftl" />