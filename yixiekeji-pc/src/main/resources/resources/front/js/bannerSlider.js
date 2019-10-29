function loadbanner(ulBox){

    var box = document.getElementById(ulBox);
    var screen = box.children[0];
    var ul = screen.children[0];
    var ol = screen.children[1];
    var ulLis = ul.children;
    var imgWidth = screen.offsetWidth;
    //alert(imgWidth);

    //1.根据图片的数量来 动态生成按钮
    for (var i = 0; i < ulLis.length; i++) {
        var li = document.createElement("li");
        //添加序号
        li.innerHTML = i + 1;
        ol.appendChild(li);
    }
    //获取刚刚生成的olLis
    var olLis = ol.children;
    //临时给他加一个"current"
    olLis[0].className = "current";

    //2.鼠标经过 按钮排他 并 显示相应的图片
    for (var j = 0; j < olLis.length; j++) {
        olLis[j].index = j;
        //给每一个olLis绑定 鼠标经过事件
        olLis[j].onmouseover = function () {
            //干掉所有人
            for (var k = 0; k < olLis.length; k++) {
                olLis[k].className = "";
            }
            //留下我自己
            this.className = "current";
            //通过动画函数 将ul移动到指定位置

            //分析 ul的目标位置 和 olLis的索引号 图片宽度imgWidth 有关

            var target = -this.index * imgWidth;

            animate(ul, target);

            pic = this.index;
            square = this.index;


        }
    }

    //3.为无缝滚动做准备 将第一张图片追加到最后
    ul.appendChild(ulLis[0].cloneNode(true));

    //4.图片自动滚动
    //5.按钮自动滚动
    var pic = 0;//用来记录 当前应该显示的图片的索引
    var square = 0;
    var timer = null;
    timer = setInterval(autoplay, 3000);


    //6.鼠标经过box 清理定时器 鼠标离开继续滚动
    box.onmouseover = function () {
        clearInterval(timer);
    }
    box.onmouseout = function () {
        timer = setInterval(autoplay, 2000);
    }

    function autoplay() {
        if (pic < ulLis.length - 1) {
            pic++;
        } else {
            pic = 1;
            ul.style.left = 0;
        }

        //分析 target 和 pic imgWidth
        var target = -pic * imgWidth;
        animate(ul, target);


        //如果square 小于 最后的按钮的索引号
        if (square < olLis.length - 1) {
            square++;
        } else {
            square = 0;
        }


        //干掉所有人
        for (var i = 0; i < olLis.length; i++) {
            olLis[i].className = "";
        }
        //亮起当前的
        olLis[square].className = "current";

    }

    function animate(obj, target) {
            clearInterval(obj.timer);
            obj.timer = setInterval(function () {
                var step = 9;
                step = obj.offsetLeft < target ? step : -step;
                if (Math.abs(obj.offsetLeft - target) > Math.abs(step)) {
                    obj.style.left = obj.offsetLeft + step + "px";
                } else {
                    obj.style.left = target + "px";
                    clearInterval(obj.timer);
                }
            }, 3)
        }


}

