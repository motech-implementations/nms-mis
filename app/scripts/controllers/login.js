$("#btn-login").click(function(){
	var user = {};

	user.username = "yash";
	user.password = "beehyv123";
	user.rememberMe = false;

	var error_p = $("#div-login .error");



	console.log(user);

	var error_p = $("#div-login .error");
	// $.post( "http://localhost:8080/NMSReportingSuite/nms/login", user, function( data ) {
	// 	console.log( data );
	// }, "json");

	// $.ajax({
	// 	type: "POST",
	// 	url: "http://localhost:8080/NMSReportingSuite/nms/login",
	// 	data: user,
	// 	success: console.log(result),
	// 	dataType: 'json'
	// });

});