/**
 * 多文件上传插件
 * 1.依赖一下js文件
 *   jquery-1.8.2.min.js
 *   jquery.ui.widget.js
 *   jquery.iframe-transport.js
 *   jquery.fileupload.js
 * 2.默认的type=file的input控件的name为files
 * 3.options格式例子{"url":"http://www.test.com/upfiles","validate":{"fileSize":1024000,"fileType":"img"},"callback":"callbackfunctionname"}
 */

(function($) {
    $.fn.multiupload = function(options) {
        options = options || {};
        return this.each(function() {
            //get the options
            $target = $(this);
            var newoptions = {};
            var data = $.parseJSON($target.attr("multiparam"));
            if (data) {
                newoptions = $.extend(data, options);
            }

            if (!newoptions.url) {
                var $targetId = $target.attr("id");
                var $targetName = $target.attr("name");
                if (!$targetName) {
                    $targetName = $targetId;
                }
                window.console && console.log("url option is not provided in " + $targetName);
                return;
            }

            //append the required html
            $target.append($('<input>').attr('name', 'files').attr('type', 'file').attr('multiple', '').attr('data-url', newoptions.url).attr('class', 'upbtn'));

            //bind event
            var container = $target[0];
            $target.find("[name='files']").click(function() {
                $target.find("[name='notAllowedFiles']").find("span").remove();
            });

            if (newoptions.validate) {
                $("[name='files']", container).data("validate", newoptions.validate);
            }
            if (newoptions.callback) {
                $("[name='files']", container).data("callback", newoptions.callback);
            }

            $("[name='files']", container).fileupload({
                dataType: 'json',
                replaceFileInput: true,
                sequentialUploads: true,
                progressInterval: 50,
                bitrateInterval: 500,

                add: function(e, data) {
                    if (data.files && data.files.length == 1) {
                        var file = data.files[0];
                        var validate = $("[name='files']", container).data("validate");
                        if (validate) {
                            var suc = true;
                            var errMsg;
                            $.each(validate,
                            function(key, valueObj) {
                                var ret = $.fn.multiupload.validate[key].call(this, file, valueObj, container);
                                if (!ret) {
                                    suc = false;
                                    errMsg = valueObj.errMsg;
                                    return false; //break;
                                }
                            });
                            if (!suc) {
                                alert(errMsg);
                                return;
                            }
                        }
                        
                        var fileIndex = parseInt($("input[name='fileIndex']", container).val());
                        $("input[name='fileIndex']", container).val(fileIndex + 1);
//                        data.context = fileIndex + 1;
//                        var lival = $('#prewtemplage').clone().attr('index', data.context).removeAttr('id').show();
//                        $(lival).find('img').attr('id', 'prev_'+data.context).attr('name','prev_'+data.context);
//                        $("#preview-img").append(lival);
//                        $(container).parent().parent().find('.s-picnum').html((fileIndex + 1) + "/10");
                        data.submit();
                    }
                },
                done: function(e, data) {
                	if(data.result.success == false){
                		log.i("上传失败");
                		if($ && $.messager){
                			$.messager.show({
                				title : '提示',
                				msg : '上传失败，请稍后重试',
                				showType : 'show'
                			});
                		}
                	} else{
                		log.i("上传成功");
                		var filenum = parseInt($("#preview-img > li:visible").length);
                		//图片下标索引规则：当前图片自然数加1
//                		var lastindex = $("#preview-img > li:last").attr("index");
//                		if(!lastindex)
//                			lastindex = 1;
//                		fileIndex = Number(lastindex);
                        $("input[name='fileIndex']", container).val(filenum + 1);
                        data.context = filenum + 1;
                        
//                        var lival = $('#prewtemplage').clone().attr('index', data.context).removeAttr('id').show();
//                        $(lival).find('img').attr('id', 'prev_'+data.context).attr('name','prev_'+data.context);
//                        $("#preview-img").append(lival);
//                		 
//                		$('#prev_'+data.context).attr('src', data.result.data.url)
                        var pichtml_ =
                                 '<div class="aa" DR_drag="1" DR_replace="1">' +  
                                 '<a  style=" position: absolute;left: 1px;top: 1px; z-index:9999;width: 20px; height:20px;" class="del-img icon-cancel"  href="javascript:void(0);"></a>'+
                                                '<img  name="prev_'+(filenum + 1)+'"' +
                                                'src="'+data.result.data.url+'">'  +
                                                '<i class="drag"></i>'+  
                                             '</div>';
                        $("#preview-img").append(pichtml_);
                        
                		$('#previewImgBox').show();
                		
                		//调用回调函数
                		var callback_ = $("[name='files']", container)
                		.data("callback");
                		log.i("解析回调函数："+callback_);
                		if (callback_) {
                			eval("(" + callback_
                					+ "(data.result.data))");
                		}
                		
                	}
                },
                progress: function(e, data) {
                    //alert('progress...'+data.files.length);
                    //if (data.files && data.files.length==1) {
                    //	var fileIndex = data.context;
                    //	var fileSuffix = 'file' + fileIndex;
                    //	var progress = parseInt(data.loaded / data.total * 100, 10);
                    //	$('#progress-'+fileSuffix+' .bar',container).html(progress+'%');
                    //}
                }
            });
        });
    };
    $.fn.multiupload.validate = {
        fileSize: function(currFile, valueObject, container) {
            var limitedFileSize = valueObject.value;
            if (currFile > limitedFileSize) {
                //$("[name='notAllowedFiles']", container).append($('<span>').html('文件[' + currFile.name + ']过大'));;
                return false;
            }
            return true;
        },
        fileType: function(currFile, valueObject, container) {
            var limitedFileType = valueObject.value;
            if (!currFile.type) {
                $("[name='notAllowedFiles']", container).append($('<span>').html('文件[' + currFile.name + ']格式不正确'));
                return false;
            }
            var acceptFileTypes = $.fn.multiupload.validate.fileTypeReg[limitedFileType].call(this);
            if (acceptFileTypes) {
                if (currFile.type && !acceptFileTypes.test(currFile.type)) {
                    $("[name='notAllowedFiles']", container).append($('<span>').html('文件[' + currFile.name + ']格式不正确'));
                    return false;
                }
            }
            return true;
        },
        fileMaxNum: function(currFile, valueObject, container) {
            var maxNum = valueObject.value;
            var uploaded = parseInt($("input[name='fileIndex']", container).val());
            log.i("fileIndex:"+(uploaded+1));
//            var uploaded = $("#preview-img > li:visible");
            if ((uploaded+1) > parseInt(maxNum)) {
                return false;
            }
            return true;
        }
    };
    $.fn.multiupload.validate.fileTypeReg = {
        img: function() {
            return /^image\/(gif|jpe?g|png)$/i;
        }
    };
})(jQuery);