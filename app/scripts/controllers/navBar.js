(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("NavBarController", ['$scope', 'AuthFactory', function($scope, AuthFactory){
			
			$scope.logout = function(){
				AuthFactory.logout();
			}

		}]
	)}
)()