(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ForgotPasswordController", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){


			$scope.forgotPasswordSubmit = function(){
				if ($scope.forgotPasswordForm.$valid) {
					$http({
						method  : 'POST',
						url     : backend_root + 'nms/user/forgotPassword',
						data    : $scope.forgotPassword, //forms user object
						headers : {'Content-Type': 'application/json'}
					}).then(function (result){
                        alert(result.data[0]);
                        $scope.forgotPassword = {};
                        $scope.forgotPasswordForm.$setPristine();
                        $scope.forgotPasswordForm.$setUntouched();

                     },function (result){
                        alert("error changing password");
                     });
				}
				else{
					angular.forEach($scope.forgotPasswordForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}

			}


		}])
})()