<script>
   $.parser.parse();  
</script>

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
											默认运费：
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
						</div>