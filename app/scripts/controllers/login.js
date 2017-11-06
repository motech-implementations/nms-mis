(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("LoginController", ['$scope', '$http', '$location','Captcha', function($scope, $http, $location, Captcha){
			
			$scope.user = {};
			$scope.user.rememberMe = false;

			$scope.loginUrl = backend_root + 'nms/login';

			var url = $location.absUrl();
			var error = url.split('?')[1]
			
			console.log(url.split('?')[0])
			console.log(error)

			$scope.errorMessage = "";
			if(error == null){
				$scope.errorMessage = "";
			}
			else{
				$scope.errorMessage = "Invalid Username/Password";
			}

			$scope.login = function(){
				$http.post($scope.loginUrl,
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

