(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ChangePassword", ['$scope', '$state','$http', 'UserFormFactory','$mdDialog', function($scope, $state, $http, UserFormFactory,$mdDialog){

			UserFormFactory.isAdminLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

            $http.get(backend_root + 'nms/user/profile')
            .then(function(result){
                $scope.user = result.data;
            })

			$scope.changePasswordSubmit = function(){
                if ($scope.changePasswordForm.$valid) {
                    delete $scope.password.$$hashKey;
                    $scope.password.userId = $scope.user.id;
                    $http({
                        method  : 'POST',
                        url     : backend_root + 'nms/user/resetPassword',
                        data    : $scope.password, //forms user object
                        headers : {'Content-Type': 'application/json'}
                    }).then(function(result){
                        $scope.showAlert(result.data['0']);
                        $state.go('login', {});
                        $scope.clearForm();
                    })
                }
                else{
                    angular.forEach($scope.changePasswordForm.$error, function (field) {
                        angular.forEach(field, function(errorField){
                            errorField.$setDirty();
                        })
                    });
                }
            }

			$scope.clearForm = function(){

                if($scope.currentUser.default){
                    $state.go('login', {});
                }else {
                    $scope.password = {};
                    $scope.confirmPassword = null;
                    $scope.changePasswordForm.$setPristine();
                }
            }

            UserFormFactory.downloadCurrentUser()
            .then(function(result){
                UserFormFactory.setCurrentUser(result.data);
                $scope.currentUser = UserFormFactory.getCurrentUser();
                console.log($scope.currentUser);
            })

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