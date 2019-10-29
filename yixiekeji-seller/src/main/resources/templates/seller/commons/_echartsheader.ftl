<script
	src='${domainUrlUtil.urlResources}/resources/seller/jslib/My97DatePicker/WdatePicker.js'></script>

<script>
	
	$(function(){
		//注册主题
		var theme = new EchartsTheme();
		echarts.registerTheme("infographic", theme.getTheme("infographic"));
		echarts.registerTheme("macarons", theme.getTheme("macarons"));
		echarts.registerTheme("blue", theme.getTheme("blue"));
		echarts.registerTheme("macarons2", theme.getTheme("macarons2"));
		echarts.registerTheme("red", theme.getTheme("red"));
		echarts.registerTheme("sakura", theme.getTheme("sakura"));
		echarts.registerTheme("helianthus", theme.getTheme("helianthus"));
		echarts.registerTheme("mint", theme.getTheme("mint"));
		echarts.registerTheme("roma", theme.getTheme("roma"));
		
		try{
			//eharts初始化
			var dom_ = <#if chartDom??>$("#${chartDom}")[0]<#else>$("#chartdiv")[0]</#if>;
			if(!dom_)
				throw new Error("没有指定图表dom");
			var myChart = echarts.init($("#chartdiv")[0]<#if theme??>,'${theme}'</#if>);
			// 使用刚指定的配置项和数据显示图表。
			<#if option??>
			<#noescape>
		    myChart.setOption(eval(${option}));
			</#noescape>
			</#if>
		} catch(e){
			log.i(e.message);
		}
		
		//动态条件
		<#if model??&&model=='month'>
		$("#year").parent().hide();
		$("#month").parent().show();
		</#if>
		$(":radio").click(function(){
			var yeardiv_ = $(this).parent().siblings("div.year");
			if(yeardiv_.length > 0){
				if($(this).attr('id')=='r_year'){
					yeardiv_.show().siblings("div.month").hide();
				} else{
					yeardiv_.hide().siblings("div.month").show();
				}
			} else{
				if($(this).attr('id')=='r_year'){
					$(this).closest("div.con-inline").siblings("div.con-inline").find("div.year").show();
					$(this).closest("div.con-inline").siblings("div.con-inline").find("div.month").hide();
				} else{
					$(this).closest("div.con-inline").siblings("div.con-inline").find("div.year").hide();
					$(this).closest("div.con-inline").siblings("div.con-inline").find("div.month").show();
				}
			}
		});
		
		$("#byseller").change(function(){
			if($(this).is(":checked")){
				$("#sel_seller").show();
			} else{
				$("#sel_seller").hide();
			}
		});
	});
</script>