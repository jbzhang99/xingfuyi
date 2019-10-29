<#include "/front/commons/_headbig.ftl" />
<link rel="stylesheet" type="text/css" href="${(domainUrlUtil.staticResources)!}/css/brand.css">
    
    <!-- S 主体 -->
    <div class="container">
      <!-- S 品牌推荐区块 -->
        <div class="brand-box">
          <h2 class="brand-tit"><span></span></h2>
          <hr/>
          <div class="brand-content">
            <ul>
            	<#if hotBrands?? && hotBrands?size &gt; 0 >
            		<#list hotBrands as brand >
            			<li>
			                <a href="${(domainUrlUtil.urlResources)!}/brand/${(brand.id)!'0'}.html" target="_blank" class="brand-content-img">
			                <#if brand.image?? && brand.image != '' >
			                <img src="${(domainUrlUtil.imageResources)!}${(brand.image)!''}" alt="${(brand.name)!''}">
			                <#else>
			                <span class="disblok">${(brand.name)!''}</span>
			                </#if>
			                <span id="nameDisplay">${(brand.name)!''}</span>
			                </a>
						</li>
            		</#list>
            	</#if>
            </ul>
          </div>
        </div>
      <!-- E 品牌推荐区块 -->
      <hr/>
      <!-- S 全部品牌 -->
        <div class="all-brand-box">
          <h2 class="brand-tit"><span></span></h2>
          <div class="all-brand">
            <div class="all-brand-div">
              <hr/>
              <div class="letters">
                <strong>按字母筛选：</strong>
                <a href="javascript:;">A</a>
                <a href="javascript:;">B</a>
                <a href="javascript:;">C</a>
                <a href="javascript:;">D</a>
                <a href="javascript:;">E</a>
                <a href="javascript:;">F</a>
                <a href="javascript:;">G</a>
                <a href="javascript:;">H</a>
                <a href="javascript:;">I</a>
                <a href="javascript:;">J</a>
                <a href="javascript:;">K</a>
                <a href="javascript:;">L</a>
                <a href="javascript:;">M</a>
                <a href="javascript:;">N</a>
                <a href="javascript:;">O</a>
                <a href="javascript:;">P</a>
                <a href="javascript:;">Q</a>
                <a href="javascript:;">R</a>
                <a href="javascript:;">S</a>
                <a href="javascript:;">T</a>
                <a href="javascript:;">U</a>
                <a href="javascript:;">V</a>
                <a href="javascript:;">W</a>
                <a href="javascript:;">X</a>
                <a href="javascript:;">Y</a>
                <a href="javascript:;">Z</a>
              </div>
              <hr/>
            </div>
            <div class="letters-cont">
            	
            	<#assign letters = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'] />
            	<#--
            	<#assign letters = ['A','B','C','D'] />
            	-->
            	<#list letters as letter >
            		<dl>
		                <dt>${(letter)!''}</dt>
		                <dd>
		                  <ul>
		                  	<#if groupBrands?? && groupBrands?size &gt; 0 && (groupBrands[letter])??>
		                  		<#assign brands = groupBrands[letter] />
		                  		<#if brands?? && brands?size &gt; 0 >
			            		<#list brands as brand >
			            			<li>
						                <a href="${(domainUrlUtil.urlResources)!}/brand/${(brand.id)!'0'}.html" target="_blank" class="brand-content-img">
					                  	<#if brand.image?? && brand.image != '' >
						                <img src="${(domainUrlUtil.imageResources)!}${(brand.image)!''}" alt="${(brand.name)!''}">
						                <#else>
						                <span class="disblok">${(brand.name)!''}</span>
						                </#if>
						                <span id="nameDisplay">${(brand.name)!''}</span>
						                </a>
									</li>
			            		</#list>
			            		</#if>
			            	</#if>
		                  </ul>
		                </dd>
		              </dl>
            	</#list>
            </div>
          </div>
        </div>
      <!-- E 全部品牌 -->
    </div>
    <!-- E 主体 -->


<#include "/front/commons/_endbig.ftl" />

    <!-- <script type="text/javascript" src='./js/list.js'></script> -->
<script>
	$(function(){
		
       // 鼠标移入移出logo效果
       $('.brand-content-img').on("mouseenter",function(){
         $(this).find('#nameDisplay').fadeIn();
       });

       $('.brand-content-img').on("mouseleave",function(){
         if($(this).find("img").attr('src')==''){
           $(this).find('#nameDisplay').fadeIn();
         }else{
           $(this).find('#nameDisplay').fadeOut();
         }
       });

		var wraH = parseInt($('.wrapper').css("height"));
		var topHeadH = parseInt($('.top-head').css("height"));
		var brandBoxH = parseInt($('.brand-box').css("height"));
		var zH = wraH+topHeadH+brandBoxH;

         // 定位全部品牌的头部
        $(window).scroll(function(){
         
          if($(window).scrollTop()>zH){
            $('.all-brand-div').addClass("all-brand-tit");
          }else {
            $('.all-brand-div').removeClass("all-brand-tit");
          }

        }); 

        // 点击字母显示对应的logo
        $(".letters > a").on("click",function(){
          var aIndex = $(this).index();
      
          $(this).addClass("active").siblings('a').removeClass("active");
          $('.all-brand-div').addClass("all-brand-tit");
		  var currentDlTop = $(".letters-cont").find("dl").eq(aIndex-1).offset().top;
          $(document).scrollTop(currentDlTop-60);
        });

	});
</script>
 