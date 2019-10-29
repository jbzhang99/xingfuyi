$(function() {
	$('#allinone_thumbnailsBanner_simple').allinone_thumbnailsBanner({
		skin : 'simple',
		numberOfThumbsPerScreen : 4,
		width : 960,
		height : 384,
		thumbsReflection : 0,
		defaultEffect : 'random'
	});

	$("#back").click(function() {
		location.href = '${currentBaseUrl}';
	});

	$("#add").click(function() {
		$("#addForm").form('submit', {
			url : currentBaseUrl + "/doAdd",
			success : function(e) {
				location.href = currentBaseUrl;
			}
		});
	});
});