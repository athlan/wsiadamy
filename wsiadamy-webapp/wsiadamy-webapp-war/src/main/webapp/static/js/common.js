$(function() {
	
	$('.btnLocker').bind('click', function() {
		$(this).addClass('disabled').attr('disabled', true);
		$(this).parents('form').submit();
	});
	
});
