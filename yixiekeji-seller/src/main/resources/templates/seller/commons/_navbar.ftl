<div class="navbar">
    <div class="navbar-inner">
        <div class="navbar-container">
            <div class="navbar-header pull-left">
                <a href="javascript:;" class="navbar-brand">
                    <small>
                        <img src="${(domainUrlUtil.staticResources)!}/resources/seller/img/yxkjlogo.png" alt="logo"/>
                    </small>
                </a>
            </div>
            
            <!-- Sidebar Collapse -->
            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="collapse-icon fa fa-bars"></i>
            </div>
            <!-- /Sidebar Collapse -->
            <!-- Account Area and Settings --->
            <div class="navbar-header pull-right hidden-xs">
                <div class="navbar-account">
                    <ul class="account-area">
                        
                        <li id="userinfo">
                            <a class="login-area" style="cursor:auto;padding-top:13px;">
                                <section>
                                    <h2><span class="profile"><span>欢迎您：${(SESSION_SELLER_USER.name)!}</span></span></h2>
                                </section>
                            </a>
                        </li>

                        <li style="color:#fff;">系统信息：
                            <a class="wave in dropdown-toggle" data-toggle="dropdown" title="Help" href="javascript:;">
                                <i class="icon fa fa-envelope"></i>
                                <span class="badge">0</span>
                            </a>
                        </li>
                        
                        <li style="color:#fff;padding-left:70px;">
                            <span style="position:absolute;top:14px;left:5px;">主题设置：</span>
                            <a class="dropdown-toggle" href="javascript:;" id="ejava-login-area" data-toggle="dropdown" >
                                <i class="icon glyphicon glyphicon-cog" ></i>
                            </a>
                            <ul class="pull-right dropdown-menu dropdown-arrow dropdown-login-area">
                                <li class="username"><a>David Stevenson</a></li>
                                <li class="edit">
                                    <a href="profile.html" class="pull-left" style="height:34px;">点击可更换主题</a>
                                </li>
                                <li class="theme-area">
                                    <ul class="colorpicker" id="skin-changer">
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#5DB2FF;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/blue.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#2dc3e8;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/azure.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#03B3B2;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/teal.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#53a93f;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/green.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#FF8F32;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/orange.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#cc324b;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/pink.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#AC193D;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/darkred.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#8C0095;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/purple.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#0072C6;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/darkblue.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#585858;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/gray.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#474544;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/black.min.css"></a></li>
                                        <li><a class="colorpick-btn" href="javascript:;" style="background-color:#001940;" rel="${(domainUrlUtil.staticResources)!}/resources/seller/css/skins/deepblue.min.css"></a></li>
                                    </ul>
                                </li>
                                <li class="dropdown-footer">
                                    <a href="${(domainUrlUtil.staticResources)!}/seller/exit">
                                        退出
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>