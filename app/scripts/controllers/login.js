(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("LoginController", ['$rootScope','$scope','$state', '$http', '$location','UserFormFactory', function($rootScope, $scope,$state, $http, $location ,UserFormFactory){


            $scope.w = window.innerWidth;
            $scope.h = window.innerHeight;

           // $scope.uri = imageurl;
            $scope.images = [0, 1, 2, 3, 4];

			$scope.user = {};
			$scope.user.captchaCode = '';
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

			$scope.Captcha = function(){
                 var alpha = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z',
             	    	'1','2','3','4','5','6','7','8','9');
                 var i;
                 for (i=0;i<6;i++){
                     var a = alpha[Math.floor(Math.random() * alpha.length)];
                     var b = alpha[Math.floor(Math.random() * alpha.length)];
                     var c = alpha[Math.floor(Math.random() * alpha.length)];
                     var d = alpha[Math.floor(Math.random() * alpha.length)];
                     var e = alpha[Math.floor(Math.random() * alpha.length)];
                                  }
                     var code = a + ' ' + b + ' ' + c + ' ' + d + ' ' + e;
                     $scope.user.mainCaptchaCode = code;


                   }
            $scope.ValidCaptcha = function(){
                 var string1 = $scope.user.mainCaptchaCode.split(' ').join('');;
                 var string2 = $scope.user.captchaCode.split(' ').join('');;
                 if (string1 == string2.toUpperCase()){
                        return true;
                 }else{
                      return false;
                      }
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
                if(!(current.indexOf("login?error") > 0)){
                    if(previous != base_url + "/app/#!/changePassword"){
                         window.localStorage.setItem('preUrl', previous);
                    }
                    if(localStorage.preUrl == base_url + "/app/#!/reports/ma/cumulative-summary-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/ma/subscriber-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/ma/performance-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/repeat-listener-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/message-matrix-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/call-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/thematic-content-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/listening-matrix-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/message-listenership-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/aggregate-beneficiary-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/usage-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/cumulative-summary-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/subscriber-report" ||
                       localStorage.preUrl == base_url + "/app/#!/reports/kilkari/beneficiary-completion-report" 
                    ){
                       $scope.preUrl = localStorage.preUrl;
                       console.log($scope.preUrl);
                       console.log($scope.user);
                    }
//                    else if(localStorage.preUrl == base_url + "/app/#!/changePassword"){
//                        if($scope.currentUser !== undefined || $scope.currentUser != ''){
//
//                             if(!($scope.currentUser.default || $scope.currentUser.default == null)){
//                                 $scope.preUrl = "";
//                                 console.log($scope.preUrl);
//                                 console.log($scope.user);
//                             }
//                        }
//
//                    }
                    else
                       $scope.preUrl = "";
                       console.log($scope.preUrl);
                       console.log($scope.user);

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

                if($scope.user.captchaCode ==''){
                console.log($scope.user);
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please fill the captcha")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please fill the captcha")
                        return;
                    }

                }
                if($scope.ValidCaptcha()==false){
                           if(UserFormFactory.isInternetExplorer()){
                               alert("Incorrect Captcha")
                               return;
                           }
                           else{
                               UserFormFactory.showAlert("Incorrect Captcha")
                               return;
                           }
                }


					UserFormFactory.downloadCurrentUser()
					.then(function(result){
                if(!(typeof(result.data) == "string")){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("You are already logged in with "+result.data.fullName);
                        location.reload();
                        return;
                    }
                    else{
                      var a= UserFormFactory.showAlert("You are already logged in with "+result.data.fullName);
                      a.then(function () {
                          location.reload();
                          return;
                      });

                    }

                }
                else{
                    var formElement = angular.element(e.target);
                    formElement.attr("action", $scope.loginUrl);
                    formElement.attr("method", "post");
                    formElement[0].submit();

                }});

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

