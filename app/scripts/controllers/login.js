(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("LoginController", ['$rootScope','$scope','$state', '$http', '$location','$window','UserFormFactory', '$crypto',
		 function($rootScope, $scope,$state, $http, $location, $window, UserFormFactory, $crypto){

            $scope.w = window.innerWidth;
            $scope.h = window.innerHeight;

           // $scope.uri = imageurl;
            $scope.images = [0];

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
			else if (error == 'blocked'){
				$scope.errorMessage = "3 unsuccessful attempts.Please try again in 24 hrs.";
			} else {
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


            $rootScope.$on('$locationChangeStart', function (event, current, previous) {
                if(!(current.indexOf("login?error") > 0)){
                    if(previous != base_url + "/#!/changePassword"){
                         window.localStorage.setItem('preUrl', previous);
                    }
                    if(localStorage.preUrl == base_url + "/#!/reports/ma/cumulative-summary-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/ma/subscriber-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/ma/performance-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/repeat-listener-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/message-matrix-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/call-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/thematic-content-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/listening-matrix-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/message-listenership-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/aggregate-beneficiary-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/usage-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/cumulative-summary-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/subscriber-report" ||
                       localStorage.preUrl == base_url + "/#!/reports/kilkari/beneficiary-completion-report" 
                    ){
                       $scope.preUrl = localStorage.preUrl;
                       console.log($scope.preUrl);
                       console.log($scope.user);
                    }
//                    else if(localStorage.preUrl == base_url + "/#!/changePassword"){
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
                var encrypted = CryptoJS.AES.encrypt($scope.user.password, 'ABCD123');
                var decrypted = $crypto.decrypt(encrypted);
                var data = {
                "username": $scope.user.username,
                "password": encrypted.toString(),
                "cipherTextHex": encrypted.ciphertext.toString(),
                "saltHex": encrypted.salt.toString(),
                "rememberMe": $scope.user.rememberMe
                };
                $http({
                    method: 'POST',
                    url: $scope.loginUrl,
                    data: JSON.stringify(data),
                    headers: {'Content-Type': 'application/json'}
                })
                .then(function(success) {
                  $window.location.replace(success.data);
                  $window.location.reload();
                }, function (error) {
                   $window.location.href = error.data;
                });

                }});


            }

		}])
})()

