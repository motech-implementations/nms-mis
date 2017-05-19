(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("LoginController", ['$scope', '$http', function($scope, $http,){
			
			$scope.user = {};
			$scope.user.rememberMe = false;

			$scope.login = function(){
				console.log($scope.user)
				$http.post('http://localhost:8080/NMSReportingSuite/nms/login',
					angular.toJson($scope.user),
					{
						headers: {
							'Content-Type': 'application/json'
						}
					})
					.success(function (data, status, headers, config) {
						console.log(data);
					})
					.error(function (data, status, header, config) {
						console.log(data);
					});
				}
			}
		])
})()




// $("#btn-login").click(function(){
// 	var user = {};

// 	user.username = "yash";
// 	user.password = "beehyv123";
// 	user.rememberMe = false;

// 	var error_p = $("#div-login .error");



// 	console.log(user);

// 	var error_p = $("#div-login .error");
// 	// $.post( "http://localhost:8080/NMSReportingSuite/nms/login", user, function( data ) {
// 	// 	console.log( data );
// 	// }, "json");

// 	$.ajax({
// 		type: "POST",
// 		url: "http://localhost:8080/NMSReportingSuite/nms/login",
// 		data: user,
// 		success: console.log(result),
// 		dataType: 'json'
// 	});

// });