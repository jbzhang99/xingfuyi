var generatedCount = 0;
// 下拉刷新
function pullDownAction(theScroller) {
	window.location.reload();
}
// 上拉加载更多
function pullUpAction(theScroller) {
	if (!loadurl) {
		return null;
	}
	location.href = loadurl;
	setTimeout(function() {
		theScroller.refresh();
	}, 1000);
}

var IScrollPullUpDown = function(wrapperName, iScrollConfig,
		pullDownActionHandler, pullUpActionHandler) {
	var iScrollConfig, pullDownActionHandler, pullUpActionHandler, pullDownEl, pullDownOffset, pullUpEl, scrollStartPos;
	var pullThreshold = 5;
	var me = this;

	function showPullDownElNow(className) {
		// Shows pullDownEl with a given className
		pullDownEl.style.transitionDuration = '';
		pullDownEl.style.marginTop = '';
		pullDownEl.className = 'pullDown ' + className;
	}
	var hidePullDownEl = function(time, refresh) {
		// Hides pullDownEl
		pullDownEl.style.transitionDuration = (time > 0 ? time + 'ms' : '');
		pullDownEl.style.marginTop = '';
		pullDownEl.className = 'pullDown scrolledUp';

		if (refresh)
			setTimeout(function() {
				me.myScroll.refresh();
			}, time + 10);
	}

	function init() {
		var wrapperObj = document.querySelector('#' + wrapperName);
		var scrollerObj = wrapperObj.children[0];

		if (pullDownActionHandler) {
			pullDownEl = document.createElement('div');
			pullDownEl.className = 'pullDown scrolledUp';
			pullDownEl.innerHTML = '<span class="pullDownIcon"></span><span class="pullDownLabel"></span>';
			scrollerObj.insertBefore(pullDownEl, scrollerObj.firstChild);
			pullDownOffset = pullDownEl.offsetHeight;
		}
		if (pullUpActionHandler) {
			pullUpEl = document.createElement('div');
			pullUpEl.className = 'pullUp';
			pullUpEl.innerHTML = '<span class="pullUpIcon"></span><span class="pullUpLabel">加载更多...</span>';
			scrollerObj.appendChild(pullUpEl);
		}

		me.myScroll = new IScroll(wrapperObj, iScrollConfig);

		me.myScroll
				.on(
						'refresh',
						function() {
							if ((pullDownEl)
									&& (pullDownEl.className.match('loading'))) {
								pullDownEl.querySelector('.pullDownLabel').innerHTML = '';
								if (this.y >= 0) {
									hidePullDownEl(250, true);

								} else if (this.y > -pullDownOffset) {
									pullDownEl.style.marginTop = this.y + 'px';

									pullDownEl.offsetHeight;

									var animTime = (250 * (pullDownOffset + this.y) / pullDownOffset);

									this.scrollTo(0, 0, 0);

									setTimeout(function() { // Do this in a new
										hidePullDownEl(animTime, true);
									}, 0);

								} else {
									hidePullDownEl(0, true);
									this.scrollBy(0, pullDownOffset, 0);
								}
							}
							if ((pullUpEl)
									&& (pullUpEl.className.match('loading'))) {
								pullUpEl.className = 'pullUp';
								pullUpEl.querySelector('.pullUpLabel').innerHTML = '';
							}
						});

		me.myScroll.on('scrollStart', function() {
			scrollStartPos = this.y; // Store the scroll starting point to be
		});

		me.myScroll
				.on(
						'scroll',
						function() {
							if (pullDownEl || pullUpEl) {
								if ((scrollStartPos == 0) && (this.y == 0)) {
									this.hasVerticalScroll = true;

									scrollStartPos = -1000;
								} else if ((scrollStartPos == -1000)
										&& (((!pullUpEl)
												&& (!pullDownEl.className
														.match('flip')) && (this.y < 0)) || ((!pullDownEl)
												&& (!pullUpEl.className
														.match('flip')) && (this.y > 0)))) {
									this.hasVerticalScroll = false;
									scrollStartPos = 0;
									this.scrollBy(0, -this.y, 0); // Adjust
								}
							}

							if (pullDownEl) {
								if (this.y > pullDownOffset + pullThreshold
										&& !pullDownEl.className.match('flip')) {
									showPullDownElNow('flip');
									this.scrollBy(0, -pullDownOffset, 0); // Adjust
									pullDownEl.querySelector('.pullDownLabel').innerHTML = '释放刷新...';
								} else if (this.y < 0
										&& pullDownEl.className.match('flip')) { // User
									hidePullDownEl(0, false);
									this.scrollBy(0, pullDownOffset, 0); // Adjust
									pullDownEl.querySelector('.pullDownLabel').innerHTML = '';
								}
							}
							if (pullUpEl) {
								if (this.y < (this.maxScrollY - pullThreshold)
										&& !pullUpEl.className.match('flip')) {
									pullUpEl.className = 'pullUp flip';
									pullUpEl.querySelector('.pullUpLabel').innerHTML = '释放加载...';
								} else if (this.y > (this.maxScrollY + pullThreshold)
										&& pullUpEl.className.match('flip')) {
									pullUpEl.className = 'pullUp';
									pullUpEl.querySelector('.pullUpLabel').innerHTML = '';
								}
							}
						});

		me.myScroll
				.on(
						'scrollEnd',
						function() {
							if ((pullDownEl)
									&& (pullDownEl.className.match('flip'))) {
								showPullDownElNow('loading');
								pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
								pullDownActionHandler(this); // Execute
							}
							if ((pullUpEl)
									&& (pullUpEl.className.match('flip'))) {
								pullUpEl.className = 'pullUp loading';
								pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
								pullUpActionHandler(this); // Execute custom
							}
							if (scrollStartPos = -1000) {
								this.hasVerticalScroll = this.options.scrollY
										&& this.maxScrollY < 0;
							}
						});

	}
	window.addEventListener('load', function() {
		init()
	}, false);
};

var upaction = (typeof loadurl == 'undefined') ? null : pullUpAction;

var scroller1 = new IScrollPullUpDown('wrapper', {
	probeType : 2,
	bounceTime : 250,
	bounceEasing : 'quadratic',
	mouseWheel : false,
	scrollbars : true,
	fadeScrollbars : true,
	click : true,
	interactiveScrollbars : false
}, pullDownAction, upaction);

document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);