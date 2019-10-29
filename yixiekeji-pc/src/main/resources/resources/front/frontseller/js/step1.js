$(function(){
	$('#btn_apply_agreement_next').on('click', function() {
		if ($('#input_apply_agreement').prop('checked')) {
			window.location.href = domain+"/store/step2.html";
		} else {
			jAlert('请阅读并同意协议');
		}
	});
});