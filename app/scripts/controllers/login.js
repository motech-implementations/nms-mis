(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("LoginController", ['$scope', '$http', function($scope, $http){
			
			$scope.user = {};
			$scope.user.rememberMe = false;

			$scope.loginUrl = backend_root + 'nms/login';

			$scope.login = function(){
				console.log($scope.user)
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

