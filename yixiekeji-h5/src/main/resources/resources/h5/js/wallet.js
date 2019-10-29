/**
 * Created by zhousg on 2016/2/17.
 */
//window.onload = function(){
//    initLiWidth(3);
//    leftSwipe();
//}
/*初始化滑块内每个li的宽度，并以此计算li父容器的总款
* param showliNum 要展示的li个数
*/
function initLiWidth(showliNum){
    var currentLiNum=$(".slider-container li").length;
    console.log(currentLiNum);
    if(currentLiNum < showliNum){
        $(".slider-container li").css("width","109px");
    }else{
        var enalbeViewWidth = $(".slider-container").width();//获取可视化区域的宽度
        console.log(enalbeViewWidth);
        var liwidth = enalbeViewWidth/showliNum;//计算li初始化宽度
        console.log(liwidth);
        var ulWidth = liwidth*currentLiNum;//计算出容器需要容纳的宽度
        console.log(ulWidth);
        $(".slider-container li").css("width",liwidth);//初始化li的宽度
        $(".slider-container").css("width",ulWidth);//初始化父容器的宽度
        
    }
}
/*左侧滑动*/
function leftSwipe(){
    /*
    * 1.滑动起来
    * 2.滑动超过一定距离时候 需要一个吸附的效果
    * 3.点击 滑动到当前点击的元素 顶端   同时改变当前的选中元素
    * 4.在点击下面的一些元素的 时候不需要定位
    * */

    /*父盒子*/
    var parentBox = document.getElementsByClassName('vouchers-box')[0];
    /*子盒子*/
    var childBox = parentBox.getElementsByTagName('ul')[0];

    /*有两个区间  滑动区间  缓冲区间*/

    /*父容器的宽度*/
    var parentW = parentBox.offsetWidth;
    /*子容器的宽度*/
    var childW = childBox.offsetWidth;

    /*定位区间 缓冲区间*/
    var maxPosition = 0;
    var minPosition = -(childW - parentW);  

    var distance = 0;

    /*滑动区间*/
    var maxSwipe = maxPosition + distance;
    var minSwipe = minPosition - distance;

    /*公用方法*/
    /*定位*/
    var setTranslateX = function(translateX){
        /*效率更高*/
        childBox.style.transform = 'translateX('+translateX+'px)';
        childBox.style.webkitTransform = 'translateX('+translateX+'px)';
    }
    /*加过渡*/
    var addTransition = function(){
        childBox.style.transition = 'all .2s ease';
        childBox.style.webkitTransition = 'all .2s ease';
    }
    /*清楚过渡*/
    var removeTransition = function(){
        childBox.style.transition = 'none';
        childBox.style.webkitTransition = 'none';
    }

    /*滑动*/
    var startX = 0;/*开始X坐标*/
    var moveX = 0;/*滑动时候的X坐标*/
    var distanceX = 0;/*滑动的距离*/
    /*记录当前的定位*/
    var currX = 0;

    childBox.addEventListener('touchstart',function(e){
        startX = e.touches[0].clientX;
    });
    childBox.addEventListener('touchmove',function(e){
        moveX = e.touches[0].clientX;
        distanceX = moveX - startX;
        removeTransition();
        setTranslateX(currX + distanceX);

    });
    window.addEventListener('touchend',function(){
        /*计算 当前滑动结束之后的位置*/
        currX = currX + distanceX;

        if(currX > maxPosition){
            currX = maxPosition;
            addTransition();
            setTranslateX(currX);
        }else if(currX < minPosition){
            currX = minPosition;
            addTransition();
            setTranslateX(currX);
        }


        /*重置记录的参数*/
        startX = 0;
        moveX = 0;
        distanceX = 0;
    });
}
