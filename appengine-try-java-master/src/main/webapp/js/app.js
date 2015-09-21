$(document).ready(function() {
	$('#example').DataTable( );
});

$(function() {
	$(".clickableRow").on("click", function() {
		location.href = $(this).data("href");
	});
});