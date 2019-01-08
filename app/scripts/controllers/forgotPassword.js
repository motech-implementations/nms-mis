(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ForgotPasswordController", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){

            UserFormFactory.getCaptcha()
                .then(function(result){
                    var encryptedCaptcha = result.data;
                    if(result.data){
                        encryptedCaptcha = encryptedCaptcha + '=';
                        var decryptedCaptcha = window.atob(encryptedCaptcha);
                        $scope.forgotPassword.mainCaptchaCode = decryptedCaptcha;
                    }
                })
            $scope.forgotPassword = {};
            $scope.forgotPassword.captchaCode = '';




            // $scope.Captcha = function(){
            //      var alpha = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z',
            //             '1','2','3','4','5','6','7','8','9');
            //      var i;
            //      for (i=0;i<6;i++){
            //          var a = alpha[Math.floor(Math.random() * alpha.length)];
            //          var b = alpha[Math.floor(Math.random() * alpha.length)];
            //          var c = alpha[Math.floor(Math.random() * alpha.length)];
            //          var d = alpha[Math.floor(Math.random() * alpha.length)];
            //          var e = alpha[Math.floor(Math.random() * alpha.length)];
            //                       }
            //          var code = a + ' ' + b + ' '  + c + ' ' + d + ' ' + e;
            //          $scope.forgotPassword.mainCaptchaCode = code;
            //
            //
            //  }
            $scope.refreshCaptcha = function(){
                UserFormFactory.getCaptcha()
                    .then(function(result){
                        var encryptedCaptcha = result.data;
                        if(result.data){
                            encryptedCaptcha = encryptedCaptcha + '=';
                            var decryptedCaptcha = window.atob(encryptedCaptcha);
                            $scope.forgotPassword.mainCaptchaCode = decryptedCaptcha;
                        }

                    })

            }

            $scope.ValidCaptcha = function(){
            console.log($scope.user);
                 var string1 = $scope.forgotPassword.mainCaptchaCode.split(' ').join('');;
                 var string2 = $scope.forgotPassword.captchaCode.split(' ').join('');;
                 if (string1 == string2.toUpperCase()){
                        return true;
                 }else{
                     $scope.refreshCaptcha();
                      return false;

                 }
            }




			$scope.forgotPasswordSubmit = function(){


			// var encryptedNew = CryptoJS.AES.encrypt($scope.forgotPassword.newPassword, 'ABCD123');
			// $scope.forgotPassword.cipherTextHexNew = encryptedNew.ciphertext.toString();
			// $scope.forgotPassword.saltHexNew = encryptedNew.salt.toString();



			if($scope.forgotPassword.captchaCode ==''){

                if(UserFormFactory.isInternetExplorer()){
                    alert("Please fill the captcha");
                    $scope.reverse();
                    return;
                }
                else{
                    UserFormFactory.showAlert("Please fill the captcha");
                    $scope.reverse();
                    return;
                }

             }

            if($scope.ValidCaptcha()==false){
               if(UserFormFactory.isInternetExplorer()){
                   alert("Incorrect Captcha");
                   $scope.reverse();
                   return;
               }
               else{
                   UserFormFactory.showAlert("Incorrect Captcha");
                   $scope.reverse();
                   return;
               }
            }

                var encryptedCaptcha = CryptoJS.AES.encrypt($scope.forgotPassword.mainCaptchaCode, 'ABCD123');
                var cipherTextHex = encryptedCaptcha.ciphertext.toString();
                var saltHex = encryptedCaptcha.salt.toString();
                var captchaToken = cipherTextHex + "||" +saltHex;
                captchaToken = (window.btoa(captchaToken)).slice(0,-1);

			var data = {
                    "username": $scope.forgotPassword.username,
                    "captcha" : captchaToken,
                    };

				if ($scope.forgotPasswordForm.$valid) {
					$http({
						method  : 'POST',
						url     : backend_root + 'nms/user/forgotPassword',
						data: JSON.stringify(data),
						headers : {'Content-Type': 'application/json'}
					}).then(function (result){
                            if(UserFormFactory.isInternetExplorer()){
                                if(result.data=="success") {
                                    alert("Password changed successfully. Please check your e-mail for further instructions.");
                                    $state.go('login', {});                                }
                                else if(result.data=="invalid user"){
                                    alert("No user with provided username");
                                    $scope.refreshCaptcha();

                                }
                                else if(result.data=="invalid captcha"){
                                    alert("Invalid captcha. Please refresh or try again");
                                    $scope.refreshCaptcha();
                                }
                            }
                            else{
                                if(result.data=="success") {
                                    UserFormFactory.showAlert("Password changed successfully. Please check your e-mail for further instructions.");
                                    $state.go('login', {});                                }
                                else if(result.data=="invalid user"){
                                    UserFormFactory.showAlert("No user with provided username");
                                    $scope.refreshCaptcha();

                                }
                                else if(result.data=="invalid captcha"){
                                    UserFormFactory.showAlert("Invalid captcha. Please refresh or try again");
                                    $scope.refreshCaptcha();
                                }

                            }
                        $scope.forgotPassword = {};
                        $scope.forgotPasswordForm.$setPristine();
                        $scope.forgotPasswordForm.$setUntouched();
                        // $state.go('login', {});

                     },function (result){
                        if(UserFormFactory.isInternetExplorer()){
                            alert(result.data['0'])
                             return;
                        }
                        else{
                            UserFormFactory.showAlert(result.data['0'])
                            return;
                        }

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
})();