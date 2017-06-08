(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ProfileController", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){
			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

			$scope.clearForm = function(){
				$scope.password.newPassword = null;
				$scope.confirmPassword = null;
				$scope.password = {};
				$scope.changePasswordForm.$setPristine();
			}

			$http.get(backend_root + 'nms/user/profile')
			.then(function(result){
				$scope.user = result.data;
			})
			

		}])
})()