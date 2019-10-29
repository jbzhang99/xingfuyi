<#include "/seller/commons/_head.ftl"> <#assign
currentBaseUrl="${domainUrlUtil.urlResources}/seller/operate/sellerTransport"/>

<link rel="stylesheet"
	href="${(domainUrlUtil.staticResources)!}/resources/seller/css/seller.css" type="text/css"></link>
<script>
	var domain = "${domainUrlUtil.urlResources}";
	var currentBaseUrl = "${currentBaseUrl}";
	var id = "${(obj.id)!}";
	var transType = "${(obj.transType)!}";
</script>
<script
	src="${(domainUrlUtil.staticResources)!}/resources/seller/jslib/seller/transport_edit.js"></script>
<script>
	$(function(){
		<#if ((obj.transMail)!0)==1>
			$("#transtype_mail_info").show();
		</#if>
		<#if ((obj.transExpress)!0)==1>
			$("#transtype_express_info").show();
		</#if>
		<#if ((obj.transEms)!0)==1>
			$("#transtype_ems_info").show();
		</#if>
	});
</script>
<style>
body{
	overflow: auto !important;
}

.dl-group p img {
	max-width: 120px;
	float: left;
}

.formbox-a .col-lg-2 control-label {
	float: left;
	width: 120px;
	text-align: right;
	margin-right: 3px;
	display: inline;
	padding-top: 5px;
}

iframe .panel-fit, .panel-fit body {
    overflow: scroll;
}
</style>

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
					<li><i class="fa fa-home"></i> <a
						href="${domainUrlUtil.urlResources}/seller/index.html">首页</a>
					</li>
					<li>
					<a
						href="${domainUrlUtil.urlResources}/seller/operate/sellerTransport">运费设置</a>
					</li>
					<li class="active">添加运费设置</li>
				</ul>

				<!-- 头部按钮开始 -->
				<#include "/seller/commons/_headerbuttons.ftl">
				<!-- 头部按钮结束 -->

			</div>
			<!-- 主体头部结束 -->

			<!-- Page Body -->
			<div id="bodyejavashop" style="overflow-y: auto; overflow-x: hidden;">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div style="padding-top: 10px;">基本信息</div>
					<hr class="wide" style="margin-bottom: 10px; margin-top: 10px;" />

					<form method="post" id="addform" class="form-horizontal"
						action="${currentBaseUrl}/doAdd"
						enctype="multipart/form-data" data-bv-message="该项必填">
						
							<input type="hidden" value="${(obj.id)!''}" name="id"> 
				         	<input type="hidden" name="mail_city_count" id="mail_city_count" />
				            <input type="hidden" name="express_city_count" id="express_city_count" />
				            <input type="hidden" name="ems_city_count" id="ems_city_count" />
                                             <input type="hidden" name="no_trans_city_count" id="no_trans_city_count" />
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>模板名称: </label>
							<div class="col-lg-4">
								<input type="text" id="transName" name="transName" value="${(obj.transName)!}"
									class="form-control" 
									required
								 	data-bv-stringlength="true"
                                    data-bv-stringlength-min="2"
                                    data-bv-stringlength-max="20"
                                    data-bv-stringlength-message="模板名称2-20位长度"
									/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>发货时间: </label>
							<div class="col-lg-4">
	                        	 <@cont.select class="form-control" id="transTime"
									codeDiv="TRANSPORT_TIME"  value="${(obj.transTime)!}" name="transTime"/>
							</div>
						</div>
						
						<#if obj?? && obj.id?? && obj.id &gt; 0 >
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>计价方式: </label>
							<div class="col-lg-4">
							<label>
							 <#if ((obj.transType)!0)==2>
							 按重量
							 <#else>
							 按件数
							 </#if>
							</label>
							</div>
						</div>
						<#else>
						<div class="form-group">
							<label class="col-lg-2 control-label"><font class="red">*</font>计价方式: </label>
							<div class="col-lg-4">
								<label>
								<input type="radio" name="transType" id="transType" value="1" <#if ((obj.transType)!0)==1||((obj.transType)!0)==0>checked="checked"</#if> /> 按件数
								</label>
								<label>
								<input type="radio" name="transType" id="transType" value="2" <#if ((obj.transType)!0)==2>checked="checked"</#if>/> 按重量
								</label>
							</div>
							<label class="col-lg-6 ejava-errinforight">
								切换计价方式会清楚数据，请先选择计价方式
                           	</label>
						</div>
						</#if>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">换算系数: </label>
							<div class="col-lg-4">
	                            <input type="text" 
	                            	id="transRatio" 
									name="transRatio"
									value="${(obj.transRatio)!''}"
	                                class="form-control"
									data-bv-numeric="true"
									data-bv-numeric-message="请输入正确的数字"
									pattern="^\d{1,6}$"
                             	  	data-bv-regexp-message="输入非法字符，请检查" />
                            </div>
							<label class="col-lg-6 ejava-errinforight">
								正整数，用于按体积计算运费时，体积与重量之间的换算
                           	</label>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label">
								<font class="red">*</font>
								运送方式: 
							</label>
							<div class="col-lg-10">
								<label 
									class="trans-info">除指定地区外，其他地区的运费采用默认运费（配送区域为全国），
										如勾选多种运费类型，使用优先级将依照此列表顺序</label>
							</div>
						</div>   
						
						<div id="trans_detail">
						<div class="row form-group trans-port-item-info">
							<!--平邮 开始-->
							<div class="db_box_main col-xs-12">
								<div class="db_box_main_input row">
									<label class="col-lg-2"></label>
									<label> <input name="transMail" type="checkbox"
										id="transtype_mail" value="1" 
										<#if ((obj.transMail)!0)==1>checked</#if> /> 平邮
									</label>
								</div>
								<div class="row">
									<div class="col-xs-12 db_box_main_rdinary" id="transtype_mail_info" 
										style="display: none;">
										<div class="rdinary_top">
											默认运费(如为指定地区设置运费，则其它地区默认不发货)：
											<input name="mail_trans_weight" type="text"
												id="mail_trans_weight" size="5" class="easyui-numberbox"
												value="${(mail.trans_weight)!}"/> 件内， 
											<input
												name="mail_trans_fee" type="text" precision="2"
												id="mail_trans_fee" size="8" class="easyui-numberbox"
												value="${(mail.trans_fee)!}"/>元， 每增加
											<input name="mail_trans_add_weight" type="text"
												id="mail_trans_add_weight" size="5" 
												class="easyui-numberbox"
												value="${(mail.trans_add_weight)!}"/> 件，增加运费
											<input precision="2"
												name="mail_trans_add_fee" type="text" class="easyui-numberbox"
												id="mail_trans_add_fee"
												size="8" value="${(mail.trans_add_fee)!}"/> 元
										</div>
										
										<div class="rdinary_ul" id="mail_trans_city_info" 
											<#if mailList?size==0>style="display:none;"</#if>>
											<table width="100%" cellpadding="0" cellspacing="0">
												<tr bgcolor="#f5f5f5" class="trtitle">
													<td width="46%" align="center"><span class="width1">运送到</span></td>
													<td width="11%"><span class="width2">首件(件)</span></td>
													<td width="13%"><span class="width2">首费(元)</span></td>
													<td width="11%"><span class="width2">续件(件)</span></td>
													<td width="12%"><span class="width2">续费(元)</span></td>
													<td width="7%"><span class="width3">操作</span></td>
												</tr>
												<#if mailList??>
												<#list mailList as info>
													<tr index="${info_index}">
														<td><a href="javascript:void(0);"
															onclick="edit_trans_city(this);" trans_city_type="mail">编辑</a></span><span
															class="width1" id="mail${info_index}">${(info.city_name)!}</span>
															<input type="hidden" id="mail_city_ids${info_index}"
																   name="mail_city_ids${info_index}"
																   value="${(info.city_id)!}"/>
		                                                    <input type="hidden" id="mail_city_names${info_index}"
		                                                           name="mail_city_names${info_index}"
		                                                           value="${(info.city_name)!}"/>
														</td>
														<td><input type="text" class="in"
															id="mail_trans_weight${info_index}"
															name="mail_trans_weight${info_index}"
															value="${(info.trans_weight)!}" /></td>
														<td><input type="text" class="in money"
															id="mail_trans_fee${info_index}"
															name="mail_trans_fee${info_index}"
															value="${(info.trans_fee)!}" /></td>
														<td><input type="text" class="in"
															id="mail_trans_add_weight${info_index}"
															name="mail_trans_add_weight${info_index}"
															value="${(info.trans_add_weight)!}" /></td>
														<td><input type="text" class="in money"
															id="mail_trans_add_fee${info_index}"
															name="mail_trans_add_fee${info_index}"
															value="${(info.trans_add_fee)!}" /></td>
														<td><span class="width3"><a
																href="javascript:void(0);"
																onclick="deleteCity(this)">删除</a></span></td>
												</tr>
												</#list>
												</#if>
											</table>
										</div>
										<div class="rdinary_ul_bottom">
											<a href="javascript:void(0);" onclick="trans_city('mail')">为指定地区城市设置运费</a>
										</div>
									</div>
								</div>
							</div>
							<!--平邮 结束-->
							<!--快递 开始-->
							<div class="db_box_main col-xs-12">
								<div class="db_box_main_input row">
									<label class="col-lg-2"></label>
									<label> 
										<input name="transExpress" type="checkbox"
											id="transtype_express" value="1" 
											<#if ((obj.transExpress)!0)==1>checked</#if> /> 快递
									</label>
								</div>
								<div class="row">
								<div class="col-xs-12 db_box_main_rdinary" id="transtype_express_info" 
										style="display: none;">
									<div class="rdinary_top">
										默认运费：
										<input name="express_trans_weight" type="text" 
											id="express_trans_weight" size="5" class="easyui-numberbox"
											value="${(express.trans_weight)!}"/> 件内， 
										<input name="express_trans_fee" type="text" precision="2"
											id="express_trans_fee" size="8" class="easyui-numberbox"
											value="${(express.trans_fee)!}"/> 元， 每增加 
										<input name="express_trans_add_weight" type="text" 
											id="express_trans_add_weight" size="5" class="easyui-numberbox"
											value="${(express.trans_add_weight)!}"/> 件，增加运费
										<input name="express_trans_add_fee" type="text" precision="2"
											id="express_trans_add_fee" size="8" class="easyui-numberbox"
											value="${(express.trans_add_fee)!}"/> 元
									</div>
									<div class="rdinary_ul" id="express_trans_city_info" 
										<#if expressList?size==0>style="display:none;"</#if>>
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr bgcolor="#f5f5f5" class="trtitle">
												<td width="46%" align="center"><span class="width1">运送到</span></td>
												<td width="11%"><span class="width2">首件(件)</span></td>
												<td width="13%"><span class="width2">首费(元)</span></td>
												<td width="11%"><span class="width2">续件(件)</span></td>
												<td width="12%"><span class="width2">续费(元)</span></td>
												<td width="7%"><span class="width3">操作</span></td>
											</tr>
											<#if expressList??>
											<#list expressList as info>
												<tr index="${info_index}">
													<td><a href="javascript:void(0);" onclick="edit_trans_city(this);"
															trans_city_type="express">编辑</a><span class="width1"
														id="express${info_index}">${(info.city_name)!}</span>
	                                                    <input type="hidden" id="express_city_ids${info_index}"
	                                                           name="express_city_ids${info_index}"
	                                                           value="${(info.city_id)!}"/>
	                                                    <input type="hidden" id="express_city_names${info_index}"
	                                                           name="express_city_names${info_index}"
	                                                           value="${(info.city_name)!}"/>
													</td>
													<td><input type="text" class="in"
														id="express_trans_weight${info_index}"
														name="express_trans_weight${info_index}" 
														value="${(info.trans_weight)!}"/></td>
													<td><input type="text" class="in money" 
														id="express_trans_fee${info_index}"
														name="express_trans_fee${info_index}" 
														value="${(info.trans_fee)!}"/></td>
													<td><input type="text" class="in"
														id="express_trans_add_weight${info_index}"
														name="express_trans_add_weight${info_index}" 
														value="${(info.trans_add_weight)!}"/></td>
													<td><input type="text" class="in money"
														id="express_trans_add_fee${info_index}"
														name="express_trans_add_fee${info_index}" 
														value="${(info.trans_add_fee)!}"/></td>
													<td><span class="width3"><a
															href="javascript:void(0);"
															onclick="deleteCity(this)">删除</a></span></td>
												</tr>
											</#list>
											</#if>
										</table>
									</div>
									<div class="rdinary_ul_bottom">
										<a href="javascript:void(0);" onclick="trans_city('express')">为指定地区城市设置运费</a>
									</div>
								</div>
								</div>
							</div>
							<!--快递 结束-->
							<!--EMS 开始-->
							<div class="db_box_main col-xs-12">
								<div class="db_box_main_input row">
									<label class="col-lg-2"></label>
									<label> <input name="transEms" type="checkbox"
										id="transtype_ems" value="1" 
										<#if ((obj.transEms)!0)==1>checked</#if> /> EMS
									</label>
								</div>
								<div class="row">
								<div class="col-xs-12 db_box_main_rdinary" id="transtype_ems_info" 
										style="display: none;">
									<div class="rdinary_top">
										默认运费： 
										<input name="ems_trans_weight" type="text"
											id="ems_trans_weight" size="5" class="easyui-numberbox"
											value="${(ems.trans_weight)!}"/> 件内， 
										<input name="ems_trans_fee" type="text" precision="2"
											id="ems_trans_fee" size="8" class="easyui-numberbox"
											value="${(ems.trans_fee)!}"/> 元， 每增加
										<input name="ems_trans_add_weight" class="easyui-numberbox"
											type="text" id="ems_trans_add_weight" size="5" 
											value="${(ems.trans_add_weight)!}"/> 件，增加运费 
										<input name="ems_trans_add_fee" class="easyui-numberbox"
											type="text" id="ems_trans_add_fee" size="8" precision="2"
											value="${(ems.trans_add_fee)!}"/> 元
									</div>
									<div class="rdinary_ul" id="ems_trans_city_info" <#if emsList?size==0>style="display:none;"</#if>>
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr bgcolor="#f5f5f5" class="trtitle">
												<td width="46%" align="center"><span class="width1">运送到</span></td>
												<td width="11%"><span class="width2">首件(件)</span></td>
												<td width="13%"><span class="width2">首费(元)</span></td>
												<td width="11%"><span class="width2">续件(件)</span></td>
												<td width="12%"><span class="width2">续费(元)</span></td>
												<td width="7%"><span class="width3">操作</span></td>
											</tr>
											<#if emsList??>
											<#list emsList as info>
												<tr index="${info_index}">
													<input type="hidden" id="ems_city_ids${info_index}"
	                                                           name="ems_city_ids${info_index}"
	                                                           value="${(info.city_id)!}"/>
	                                                    <input type="hidden" id="ems_city_names${info_index}"
	                                                           name="ems_city_names${info_index}"
	                                                           value="${(info.city_name)!}"/>
													<td><a href="javascript:void(0);" onclick="edit_trans_city(this);"
															trans_city_type="ems">编辑</a><span class="width1"
														id="ems${info_index}">${(info.city_name)!}</span>
													</td>
													<td><input type="text" class="in"
														id="ems_trans_weight${info_index}"
														name="ems_trans_weight${info_index}" 
														value="${(info.trans_weight)!}"/></td>
													<td><input type="text"  class="in money" 
														id="ems_trans_fee${info_index}"
														name="ems_trans_fee${info_index}" 
														value="${(info.trans_fee)!}"/></td>
													<td><input type="text" class="in"
														id="ems_trans_add_weight${info_index}"
														name="ems_trans_add_weight${info_index}" 
														value="${(info.trans_add_weight)!}"/></td>
													<td><input type="text"  class="in money"
														id="ems_trans_add_fee${info_index}"
														name="ems_trans_add_fee${info_index}" 
														value="${(info.trans_add_fee)!}"/></td>
													<td><span class="width3"><a
															href="javascript:void(0);"
															onclick="deleteCity(this)">删除</a></span></td>
												</tr>
											</#list>
											</#if>
										</table>
									</div>
									<div class="rdinary_ul_bottom">
										<a href="javascript:void(0);" onclick="trans_city('ems')">为指定地区城市设置运费</a>
									</div>
								</div>
								</div>
							</div>
							<!--EMS 结束-->
                                                        
                                                        <!-- 设置不发货区域 -->
                                                        <div class="rdinary_ul" id="no_trans_city_info" <#if noTransList?size==0>style="display:none;"</#if>>
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr bgcolor="#f5f5f5" class="trtitle">
												<td width="46%" align="center"><span class="width1">不发货区域</span></td>
												<td width="7%"><span class="width3">操作</span></td>
											</tr>
											<#if noTransList??>
											<#list noTransList as info>
												<tr index="${info_index}">
													<input type="hidden" id="no_trans_city_ids${info_index}"
	                                                           name="no_trans_city_ids${info_index}"
	                                                           value="${(info.city_id)!}"/>
	                                                    <input type="hidden" id="no_trans_city_names${info_index}"
	                                                           name="no_trans_city_names${info_index}"
	                                                           value="${(info.city_name)!}"/>
													<td><i><input id="noTrans" name="noTrans" type="checkbox" checked="checked" value="1" /></i><a href="javascript:void(0);" onclick="edit_trans_city(this);"
															trans_city_type="no_trans${info_index}">编辑</a><span class="width1"
														id="no_trans${info_index}">${(info.city_name)!}</span>
													</td>
													<td><span class="width3"><a
															href="javascript:void(0);"
															onclick="deleteCity(this)">删除</a></span></td>
												</tr>
											</#list>
											</#if>
										</table>
									</div>
                                                        <div class="no_rdinary_ul_bottom">
											<a href="javascript:void(0);" onclick="no_trans_city('no_trans')">设置不发货区域</a>
							</div>
						</div>
						</div>
						
						<div class="form-group">
							<div class="col-lg-8 col-lg-offset-3">
								<button type="submit" class="btn btn-danger  btn-primary">提交</button>
								<button type="button" class="btn btn-danger back  btn-primary">返回</button>
							</div>
						</div>
					</form>

				</div>
			</div>
			<!-- /Page Body -->
		</div>
		<!-- /Page Content -->
	</div>
	<!-- /Page Container -->
</div>

<div id="editbyarea" style="position: fixed;
    width: 80%;
    top: 50%;
    left: 50%;
    height: 270px;
 /*    background: red; */
    margin-left: -40%;
    margin-top: -150px;
    z-index: 9999;
    display:none;
   "></div>

<#include "/seller/commons/_addcommonfooter.ftl"> <#include
"/seller/commons/_end.ftl">