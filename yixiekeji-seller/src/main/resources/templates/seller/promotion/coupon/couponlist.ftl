<#include "/seller/commons/_head.ftl">

<script language="javascript">

	var codeBox;
	$(function(){
	//为客户端装配本页面需要的字典数据,多个用逗号分隔
		<#noescape>
			codeBox = eval('(${initJSCodeContainer("ACT_STATUS","CHANNEL","COUPON_TYPE")})');
		</#noescape>

		$("#a-gridAdd").click(function(){
	 		window.location.href="${(domainUrlUtil.urlResources)!}/seller/promotion/coupon/add";
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
            if(selected.status != 1 && selected.status != 4) {
            	$.messager.alert('提示','非新建或审核失败的优惠券不能修改。');
                return;
            }
	 		window.location.href="${(domainUrlUtil.urlResources)!}/seller/promotion/coupon/edit?couponId="+selected.id;
		});
		
		$("#a-gridDel").click(function(){
            var selectedCode = $('#dataGrid').datagrid('getSelected');
            if(!selectedCode){
                $.messager.alert('提示','请选择操作行。');
                return;
            }
            if(selectedCode.status != 1 && selectedCode.status != 4) {
            	$.messager.alert('提示','非新建或审核失败的优惠券不能删除。');
                return;
            }

            $.messager.confirm('提示', '确定删除吗？', function(r){
                if (r){
                    $.messager.progress({text:"提交中..."});
                    $.ajax({
                        type:"POST",
                        url: "${domainUrlUtil.urlResources}/seller/promotion/coupon/delete",
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
		
		$("#a-gridAudit").click(function(){
	        var selected = $('#dataGrid').datagrid('getSelected');
	        if(!selected){
	            $.messager.alert('提示','请选择操作行。');
	            return;
	        }
	        if(selected.status != 1 && selected.status != 4) {
	        	$.messager.alert('提示','非新建或审核失败的优惠券不能提交审核。');
	            return;
	        }

	        $.messager.confirm('提示', '提价审核后优惠券不能再修改，请确认优惠券信息填写正确，确定提交审核该优惠券吗？', function(r){
	            if (r){
	                $.messager.progress({text:"提交中..."});
	                $.ajax({
	                    type:"POST",
	                    url: "${domainUrlUtil.urlResources}/seller/promotion/coupon/audit",
	                    dataType: "json",
	                    data: "id="+selected.id,
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
		
		$("#a-gridUp").click(function(){
	        var selected = $('#dataGrid').datagrid('getSelected');
	        if(!selected){
	            $.messager.alert('提示','请选择操作行。');
	            return;
	        }
	        if(selected.status != 3) {
	        	$.messager.alert('提示','只能对审核通过的优惠券进行上架操作。');
	            return;
	        }

	        $.messager.confirm('提示', '确定上架该优惠券吗？', function(r){
	            if (r){
	                $.messager.progress({text:"提交中..."});
	                $.ajax({
	                    type:"POST",
	                    url: "${domainUrlUtil.urlResources}/seller/promotion/coupon/up",
	                    dataType: "json",
	                    data: "id="+selected.id,
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
	    
	    $("#a-gridDown").click(function(){
	        var selected = $('#dataGrid').datagrid('getSelected');
	        if(!selected){
	            $.messager.alert('提示','请选择操作行。');
	            return;
	        }
	        if(selected.status != 5) {
	        	$.messager.alert('提示','只能对上架状态的优惠券进行下架操作。');
	            return;
	        }

	        $.messager.confirm('提示', '优惠券下架后将不能再次执行上架操作，确定下架该优惠券？', function(r){
	            if (r){
	                $.messager.progress({text:"提交中..."});
	                $.ajax({
	                    type:"POST",
	                    url: "${domainUrlUtil.urlResources}/seller/promotion/coupon/down",
	                    dataType: "json",
	                    data: "id="+selected.id,
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

		$("#a-gridExport").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
			if(!selected) {
				$.messager.alert('提示','请选择操作行。');
				return;
			}
            if(selected.status != 5) {
            	$.messager.alert('提示','请选择已上架的优惠券。');
                return;
            }
            if(selected.type != 2) {
            	$.messager.alert('提示','请选择类型为线下发放类型的优惠券。');
                return;
            }
	 		window.location.href="${(domainUrlUtil.urlResources)!}/seller/promotion/coupon/export?couponId="+selected.id;
		});
        

		$("#a-gridUserDetail").click(function(){
			var selected = $('#dataGrid').datagrid('getSelected');
			if(!selected) {
				$.messager.alert('提示','请选择操作行。');
				return;
			}
            if(selected.status != 5 && selected.status != 6) {
            	$.messager.alert('提示','没有上架过的优惠券没有发放记录。');
                return;
            }
	 		window.location.href="${(domainUrlUtil.urlResources)!}/seller/promotion/couponuser?couponId="+selected.id;
		});
		
		<#if message??>$.messager.progress('close');$.messager.alert('提示','${message}');</#if>
	})

	function statusFormat(value,row,index){
		return codeBox["ACT_STATUS"][value];
	}

	function channelFormat(value,row,index){
		return codeBox["CHANNEL"][value];
	}

	function typeFormat(value,row,index){
		return codeBox["COUPON_TYPE"][value];
	}


</script>

<div class="main-container container-fluid">
    <!-- Page Container -->
    <div class="page-container">
        <!-- 左侧菜单开始 -->
        <#include "/seller/commons/_left.ftl">
        <!-- 左侧菜单结束 -->
        <!-- Page Content -->
        <div class="page-content">
            <!-- 主体头部开始 -->
            <div class="page-breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
                    </li>
                    <li class="active">优惠券管理</li>
                </ul>
                
                <!-- 头部按钮开始 -->
                <#include "/seller/commons/_headerbuttons.ftl">
                <!-- 头部按钮结束 -->
            </div>
            <!-- 主体头部结束 -->
            <!-- Page Body -->
            <div class="page-body">
              <div id="bodyejavashop" class="easyui-layout ejava-easyui-layout" data-options="fit:true,split:false" style="height:300px; " >
                <div class="whtitdiv" data-options="region:'north'" style="padding-top: 10px;overflow-x:hidden;overflow-y:auto;">
                    <!-- <table id="part1">12341234</table> -->
                    <form class="from-ff">
                      <div class="row">
                          <div class="col-lg-12 col-sm-12 col-xs-12">
                              <div class="row row-mgbot">
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
	                                  <label class="lab-item">优惠券名称：</label>
		                              <input type="text" id="q_couponName" name="q_couponName" value="${q_couponName!''}"/>
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
	                                  <label class="lab-item">状态：</label>
		                              <@cont.select id="q_status" value="${(q_status)!''}" codeDiv="ACT_STATUS"/>
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
	                                  <label class="lab-item">渠道：</label>
		                              <@cont.select id="q_channel" value="${(q_channel)!''}" codeDiv="CHANNEL"/>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="col-lg-12 col-sm-12 col-xs-12">
                              <div class="row row-mgbot">
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
	                                 <label class="lab-item">发放日期：</label>
		                             <input type="text" id="q_sendTime" name="q_sendTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:dd'})"/>
                                  </div>
                                  <div class="col-lg-4 col-sm-6 col-xs-12">
	                                  <label class="lab-item">使用日期：</label>
		                              <input type="text" id="q_useTime" name="q_useTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:dd'})"/>
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                    </form>
                </div>
               	<div data-options="region:'center'" border="false">
						<table id="dataGrid" class="easyui-datagrid"
								data-options="rownumbers:true
											,singleSelect:true
											,autoRowHeight:false
											,fitColumns:false
											,collapsible:true
											,toolbar:'#gridTools'
											,striped:true
											,pagination:true
											,pageSize:'${pageSize}'
											,fit:true
					    					,url:'${(domainUrlUtil.urlResources)!}/seller/promotion/coupon/list'
					    					,queryParams:queryParamsHandler()
					    					,onLoadSuccess:dataGridLoadSuccess
					    					,method:'get'">
							<thead>
								<tr>
									<th field="couponName" width="150" align="left" halign="center">优惠券名称</th>
									<th field="prefix" width="80" align="center" halign="center">优惠券前缀</th>
									<th field="couponValue" width="70" align="right" halign="center">优惠券面值</th>
									<th field="minAmount" width="100" align="right" halign="center">适用最低订单金额</th>
									<th field="sendStartTime" width="150" align="center" halign="center">发放开始时间</th>
									<th field="sendEndTime" width="150" align="center" halign="center">发放结束时间</th>
									<th field="useStartTime" width="150" align="center" halign="center">使用起始时间</th>
									<th field="useEndTime" width="150" align="center" halign="center">使用截止时间</th>
									<th field="personLimitNum" width="100" align="right" halign="center">会员限制数量</th>
									<th field="totalLimitNum" width="80" align="right" halign="center">总数量</th>
									<th field="receivedNum" width="100" align="right" halign="center">已发放数量</th>
									<th field="type" width="80" align="center" halign="center" formatter="typeFormat">优惠券类型</th>
									<th field="channel" width="80" align="center" halign="center" formatter="channelFormat">应用渠道</th>
									<th field="status" width="60" align="center" halign="center" formatter="statusFormat">状态</th>
									<th field="remark" width="150" align="left" halign="center">优惠券描述</th>
									<th field="auditOpinion" width="100" align="left" halign="center">审核意见</th>
									<th field="updateUserName" width="100" align="center" halign="center">最后修改人</th>
									<th field="updateTime" width="150" align="center" halign="center">最后修改时间</th>
								</tr>
							</thead>
						</table>
				
						<div id="gridTools">
							<a id="a-gridSearch" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
							
							<@shiro.hasPermission name="/seller/promotion/coupon/add">
							<a id="a-gridAdd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/seller/promotion/coupon/edit">
							<a id="a-gridEdit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/seller/promotion/coupon/delete">
							<a id="a-gridDel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-delete" plain="true">删除</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/seller/promotion/coupon/audit">
							<a id="a-gridAudit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-accept" plain="true">提交审核</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/seller/promotion/coupon/up">
							<a id="a-gridUp" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">上架</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/seller/promotion/coupon/down">
							<a id="a-gridDown" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-fail" plain="true">下架</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/seller/promotion/coupon/export">
							<a id="a-gridExport" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true">导出</a>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/seller/promotion/couponuser">
							<a id="a-gridUserDetail" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain="true">发放详情</a>
							</@shiro.hasPermission>
						</div>
				</div>
              </div>
            </div>
            <!-- /Page Body -->
        </div>
        <!-- /Page Content -->
    </div>
    <!-- /Page Container -->
</div>

<#include "/seller/commons/_listautoheight.ftl">
<#include "/seller/commons/_end.ftl">