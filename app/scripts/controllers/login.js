(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("LoginController", ['$rootScope','$scope','$state', '$http', '$location','Captcha','UserFormFactory','vcRecaptchaService', function($rootScope, $scope,$state, $http, $location, Captcha,UserFormFactory){


            $scope.w = window.innerWidth;
            $scope.h = window.innerHeight;

           // $scope.uri = imageurl;
            $scope.images = [0, 1, 2, 3, 4];

			$scope.user = {};
			$scope.captchaResponse = '';
			$scope.user.rememberMe = false;

			$scope.loginUrl = backend_root + 'nms/login';

			var url = $location.absUrl();
			var error = url.split('?')[1];
            $scope.preUrl = localStorage.preUrl;
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

            $rootScope.$on('$locationChangeStart', function (event, current, previous) {
            if(!current.includes("login?error")){
            window.localStorage.setItem('preUrl', previous);
            $scope.preUrl = localStorage.preUrl;
            }
            });
            $scope.goToDownloads = function () {
                $state.go('Downloads', {pageNum: 1});
            }
			$scope.login = function(e){
			    if($scope.user.username == null || $scope.user.username == ""){
			        if(UserFormFactory.isInternetExplorer()){
                        alert("Please specify a username")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please specify a username")
                        return;
                    }

			    }
			    if($scope.user.password == null || $scope.user.password == ""){
			        if(UserFormFactory.isInternetExplorer()){
                        alert("Please specify a password")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please specify a password")
                        return;
                    }
                }

                if($scope.captchaResponse ==''){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please check the captcha")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please check the captcha")
                        return;
                    }

                }
                else{
                    var formElement = angular.element(e.target);
                    formElement.attr("action", $scope.loginUrl);
                    formElement.attr("method", "post");
                    formElement[0].submit();

                }

                /*
                if($scope.user.captchaCode == null || $scope.user.captchaCode == ""){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the captchaCode")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the captchaCode")
                        return;
                    }
                }

                var captcha = new Captcha();

               // }

                // captcha code input value for validating captcha at server-side
                var captchaCode = angular.uppercase($scope.user.captchaCode);

                var postData = {
                  captchaId: captchaId,
                  captchaCode: captchaCode
                };

                $http({
                    method  : 'POST',
                    url     : backend_root + 'nms/captcha',
                    data    : postData, //forms user object
                    headers : {'Content-Type': 'application/json'}
                }).then(function(response){

                      if(response.data.success){
                         var formElement = angular.element(e.target);
                         formElement.attr("action", $scope.loginUrl);
                         formElement.attr("method", "post");
                         formElement[0].submit();

                      }
                      else{
                        if(UserFormFactory.isInternetExplorer()){
                            alert("Incorrect Captcha")
                            return;
                        }
                        else{
                            UserFormFactory.showAlert("Incorrect Captcha")
                            return;
                        }

                      }
                })*/

            }

		}])
})()

