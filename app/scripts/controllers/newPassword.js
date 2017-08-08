(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("NewPassword", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){
			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})


			$scope.forgotPasswordSubmit = function(){
				if ($scope.newPasswordForm.$valid) {
					delete $scope.password.$$hashKey;
					$scope.password.userId = $scope.user.id;
					$http({
						method  : 'POST',
						url     : backend_root + 'nms/user/newPassword',
						data    : $scope.password.conformPassword, //forms user object
						headers : {'Content-Type': 'application/json'}
					}).then(function(result){
						alert(result.data['0']);
					})
				}
				else{
					angular.forEach($scope.newPasswordForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}
			}


		}])
})()