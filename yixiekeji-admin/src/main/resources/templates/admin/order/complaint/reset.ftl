<#include "/admin/commons/_detailheader.ftl" />
<#assign currentBaseUrl="${domainUrlUtil.urlResources}/admin/order/complaint"/>
<script language="javascript">
    $(function () {
        $("#back").click(function () {
        	window.location.href = "${domainUrlUtil.urlResources}/admin/order/complaint";
        });
      //提交
      $("#add").click(function (){
    	  //投诉信息id
    	  var id = $("#id").val();
    	  //1.退货，2.换货
    	  var source = $("#source").val();
    	  //退换货id
    	  var backExchangeId = $("#backExchangeId").val();
    	  //状态
    	  var state = $("#state").val();
    	  
    		/* $("#addForm").submit(); */
    		$.ajax({
    			url:"${domainUrlUtil.urlResources}/admin/order/complaint/doreset",
    			data:{id:id,source:source,backExchangeId:backExchangeId,state:state},
    			dataType:"json",
    			success:function (data){
    				if(data.success){
    					window.location.href = "${domainUrlUtil.urlResources}/admin/order/complaint";
    				}else{
    					$.messager.alert(data.message);
    					return ;
    				}
    			}
    		})
      });
    })
</script>

<div class="wrapper">
    <div class="formbox-a">
        <div class="form-contbox">
        <@form.form method="post" action="${domainUrlUtil.urlResources}/admin/order/complaint/doreset" class="validForm" id="addForm" name="addForm">
            <dl class="dl-group">
                <dt class="dt-group"><span class="s-icon"></span>重置申诉信息</dt>
                <input type="hidden" name="id" id="id" value="${(id)!'0'}">
                <input type="hidden" name="source" id="source" value="${(source)!0 }">
                <input type="hidden" name="backExchangeId" id="backExchangeId" value="${(obj.id)!0 }">
                <dd class="dd-group">
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">申诉类型: </label>
                            <#if source?? && source == 1>
                            <span style="line-height:24.8px;">退货<span>
                            <#else>
                            <span style="line-height:24.8px;">换货<span>
                            </#if>
                        </p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                        <#if source?? && source == 1>
                        <label class="lab-item">退货数量: </label>
                        <span style="line-height:24.8px;">${(obj.backNumber)!'0'}<span>
                        <#else>
                        <label class="lab-item">换货数量: </label>
                        <span style="line-height:24.8px;">${(obj.exchangeNumber)!'0'}<span>
                        </#if>
						</p>
                    </div>
                    <div class="fluidbox">
                        <p class="p12 p-item">
                            <label class="lab-item">状态: </label>
                            <#if source?? && source == 1>
                            <@cont.select id="state" codeDiv="MEM_PB_STATE_RETURN" value="${(obj.stateReturn)!'1'}" name="state" style="width:100px"/>
                            <#else>
                            <@cont.select id="state" codeDiv="MEM_PROD_EXCHG_STATE" value="${(obj.state)!'1'}" name="state" style="width:100px"/>
                            </#if>
                        </p>
                    </div>
                </dd>
            </dl>

            <#--2.batch button-------------->
            <p class="p-item p-btn">
            	<input type="button" id="add" class="btn" value="提交"/>
                <input type="button" id="back" class="btn" value="返回"/>
            </p>
        </@form.form>
        </div>
    </div>
</div>
<#include "/admin/commons/_detailfooter.ftl" />