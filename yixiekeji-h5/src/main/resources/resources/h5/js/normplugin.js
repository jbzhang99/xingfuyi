/**
 * @author zhaihl 商品规格选择插件<br>
 *         选中某规格后，如果此规格未启用，禁用其对应的规格
 */
var NormChecker = (function($, undefined) {

    /**
     * 多规格初始化
     */
    function initNorm() {
		if (!effectAttr || effectAttr.length < 1) {
		    return;
		}
		
		// 获得第一个选中的规格ID
		var normAttrId1 = $(".choosenorms #normsDiv0 .btn.active").attr("val");
		// 获得第二个选中的规格ID
		var normAttrId2 = $(".choosenorms #normsDiv1 .btn.active").attr("val");
		
		// 循环第一个规格，与第二个选中的规格组合，判断是否有效
		$(".choosenorms #normsDiv0 .btn").each(function() {
			var currAttrId_ = $(this).attr("val");
			if (isEffect(currAttrId_, normAttrId2)) {
				$(this).removeClass("disabled");
			} else {
				$(this).addClass("disabled");
			}
		});
		
		// 循环第二个规格，与第一个选中的规格组合，判断是否有效
		$(".choosenorms #normsDiv1 .btn").each(function() {
			var currAttrId_ = $(this).attr("val");
			if (isEffect(normAttrId1, currAttrId_)) {
				$(this).removeClass("disabled");
			} else {
				$(this).addClass("disabled");
			}
		});
    }

    /**
     * @author
     * @param 
     * 判断给定的值对应的属性是否启用<br>
     */
    function isEffect(normAttrId1_, normAttrId2_) {
    	var checkId = normAttrId1_ + "," + normAttrId2_;
    	if ($.inArray(checkId, effectAttr) > -1) {
    		return true;
	    } else {
			return false;
	    }
    }

    return {
		init : function() {
		    initNorm();
		}
    }
})($);
