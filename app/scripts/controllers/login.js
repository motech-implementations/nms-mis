(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("LoginController", ['$scope', '$http', '$location','Captcha','$mdDialog', function($scope, $http, $location, Captcha,$mdDialog){
			
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

//			$scope.login = function(){
//				$http.post($scope.loginUrl,
//					angular.toJson($scope.user),
//					{
//						headers: {
//							'Content-Type': 'application/json'
//						}
//					})
//					.success(function (data, status, headers, config) {
//						console.log(data);
//					})
//					.error(function (data, status, header, config) {
//						console.log(data);
//					});
//				}
//			}

			$scope.login = function(){

			    if($scope.user.username == null){
			        $scope.showAlert("Please specify a username")
                    return;
			    }
			    if($scope.user.username == null){
                    $scope.showAlert("Please specify a password")
                    return;
                }
                if($scope.user.captchaCode == null){
                    $scope.showAlert("Please enter the captchaCode")
                    return;
                }
                if($scope.loginForm.captchaCode.$invalid){
                    $scope.showAlert("Incorrect Captcha")
                    return;
                }

                console.log($scope.user);
                $http({
                    method  : 'POST',
                    url     : backend_root + 'nms/login',
                    data    : $scope.user, //forms user object
                    headers : {'Content-Type': 'application/json'}
                }).then(function (result){
                  console.log(result);

                 },function (result){
                    $scope.showAlert("error logging in ");
                 });



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
            }


		}])
})()

