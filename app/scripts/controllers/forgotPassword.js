(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ForgotPasswordController", ['$scope', '$state', 'UserFormFactory', '$http','$mdDialog', function($scope, $state, UserFormFactory, $http, $mdDialog){


			$scope.forgotPasswordSubmit = function(){
				if ($scope.forgotPasswordForm.$valid) {
					$http({
						method  : 'POST',
						url     : backend_root + 'nms/user/forgotPassword',
						data    : $scope.forgotPassword, //forms user object
						headers : {'Content-Type': 'application/json'}
					}).then(function (result){
                        $scope.showAlert(result.data[0]);
                        $scope.forgotPassword = {};
                        $scope.forgotPasswordForm.$setPristine();
                        $scope.forgotPasswordForm.$setUntouched();
                        $state.go('login', {});

                     },function (result){
                        $scope.showAlert("error changing password");
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

            $scope.showAlert = function(message) {
                // Appending dialog to document.body to cover sidenav in docs app
                // Modal dialogs should fully cover application
                // to prevent interaction outside of dialog
                $mdDialog.show(
                  $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title('MIS Alert!!')
                    .textContent(message)
                    .ariaLabel('Alert Dialog Demo')
                    .ok('Got it!')
                );
            };
		}])
})()