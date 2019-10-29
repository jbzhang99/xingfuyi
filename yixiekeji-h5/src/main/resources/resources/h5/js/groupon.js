

// window.onload = function(){
//     initnav();
// }
/*��ʼ��������ÿ��li�Ŀ�ȣ����Դ˼���li���������ܿ�
* param showliNum Ҫչʾ��li����
*/

var currX = 0;
function initnav(){
    var currentLiNum=$(".tab_nav ul li").length;
    if(currentLiNum<6&&currentLiNum!=6){
        $(".tab_nav ul li").css("padding","0px");
        $(".tab_nav ul").css("width","100%");//初始化父容器的宽度
        $(".tab_nav ul li").css("width","20%");//初始化li的宽度
        var parentBox = $('.tab_nav')[0];
        var childBox = $('.tab_nav > ul')[0];
        var parentBoxW = $('.tab_nav').width();
        var childBoxW = $('.tab_nav > ul').width();
        leftSwipe(parentBox,childBox,parentBoxW,childBoxW);
    }else{
    	var liwidth = $(".tab_nav ul li").outerWidth();
        var ulWidth = liwidth*currentLiNum;
        $(".tab_nav ul li").css("width",liwidth);//��ʼ��li�Ŀ��
        $(".tab_nav ul").css("width",ulWidth);//��ʼ���������Ŀ��
        var parentBox = $('.tab_nav')[0];
        var childBox = $('.tab_nav > ul')[0];
        var parentBoxW = $('.tab_nav').width();
        var childBoxW = $('.tab_nav > ul').width();
        leftSwipe(parentBox,childBox,parentBoxW,childBoxW);
    }
    
}


/*��໬��*/
function leftSwipe(parentBox,childBox,parentW,childW){

    /*��λ��� �������*/
    var maxPosition = 0;
    var minPosition = -(childW - parentW);
    var distance = 0;

    /*�������*/
    var maxSwipe = maxPosition + distance;
    var minSwipe = minPosition - distance;

    /*���÷���*/
    /*��λ*/
    var setTranslateX = function(translateX){
        /*Ч�ʸ��*/
        childBox.style.transform = 'translateX('+translateX+'px)';
        childBox.style.webkitTransform = 'translateX('+translateX+'px)';
    }
    /*�ӹ��*/
    var addTransition = function(){
        childBox.style.transition = 'all .2s ease';
        childBox.style.webkitTransition = 'all .2s ease';
    }
    /*������*/
    var removeTransition = function(){
        childBox.style.transition = 'none';
        childBox.style.webkitTransition = 'none';
    }

    /*����*/
    var startX = 0;/*��ʼX���*/
    var moveX = 0;/*����ʱ���X���*/
    var distanceX = 0;/*�����ľ���*/
    /*��¼��ǰ�Ķ�λ*/
    //var currX = 0;

    childBox.addEventListener('touchstart',function(e){
        startX = e.touches[0].clientX;
        // console.log(startX);
    });
    childBox.addEventListener('touchmove',function(e){
        moveX = e.touches[0].clientX;
        distanceX = moveX - startX;
        removeTransition();
        setTranslateX(currX + distanceX);
        // console.log(distanceX);

    });
    window.addEventListener('touchend',function(){
        /*���� ��ǰ��������֮���λ��*/
        currX = currX + distanceX;
        // console.log(currX);
        if(currX > maxSwipe){
            currX = maxSwipe;
            addTransition();
            setTranslateX(currX);
        }else if(currX < minSwipe){
            currX = minSwipe;
            addTransition();
            setTranslateX(currX);
        }

    
        /*���ü�¼�Ĳ���*/
        startX = 0;
        moveX = 0;
        distanceX = 0;
    });
}
