(function(){
	var nmsReportsApp = angular
		.module('nmsReports');
		nmsReportsApp.controller("ContactUsController", ['$scope', '$state', 'UserFormFactory','$http', '$location', function($scope,$state,UserFormFactory, $http, $location ){
                        UserFormFactory.isLoggedIn()
                        			.then(function(result){
                                    if(result.data){
            UserFormFactory.downloadCurrentUser()
            					.then(function(result){
            					                        $scope.email.name=result.data.fullName;
            					                        $scope.email.email=result.data.emailId;
            					                        $scope.email.phoneNo=result.data.phoneNumber;
            					})
            					}
                        			})
            $scope.contactUsUrl = backend_root + 'nms/mail/sendEmailForContactUs';

            $scope.contactUs = {};
            $scope.email = {};
            $scope.email.captchaCode = '';

            var emailField = $scope.email.email
            function validateEmail(emailField){
                var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return reg.test(emailField);
            }

            var phoneNoField = $scope.email.phoneNo
            function validatePhoneNo(phoneNoField){
//                var reg =/^\s*(?:\+?(\d{1,3}))?[-. (]*(\d{3})[-. )]*(\d{3})[-. ]*(\d{4})(?: *x(\d+))?\s*$/;
//                var reg = /^(?:\+\d+[- ])?\d{10}$/;
//                var reg =  /^[0-9]{10}$/
                var reg = /^[6-9]\d{9}$/;
                return reg.test(phoneNoField);
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
                                 var code = a + ' ' + b + ' '  + c + ' ' + d + ' ' + e;
                                 $scope.email.mainCaptchaCode = code;


                               }
                        $scope.ValidCaptcha = function(){
                        console.log($scope.user);
                             var string1 = $scope.email.mainCaptchaCode.split(' ').join('');;
                             var string2 = $scope.email.captchaCode.split(' ').join('');;
                             if (string1 == string2.toUpperCase()){
                                    return true;
                             }else{
                                  return false;
                                  }
                        }



            $scope.contactUs = function(e){
              $(':input[type="submit"]').text('Submitting..');
              $(':input[type="submit"]').prop('disabled', true);
              $(':input[type="submit"]').css('background-color', 'red');

                if(!$scope.email.name){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the name");
                        $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the name");
                        $scope.reverse();
                        return;
                    }
                }

                if(!$scope.email.phoneNo){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter a valid Phone Number");
                        $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter a valid Phone Number");
                        $scope.reverse();
                        return;
                    }
                }

                if(!validatePhoneNo($scope.email.phoneNo)){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter valid phone No.");
                        $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter valid phone No.");
                        $scope.reverse();
                        return;
                    }
                }

                if(!$scope.email.email){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the email");
                        $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the email");
                        $scope.reverse();
                        return;
                    }
                }

                if(!validateEmail($scope.email.email)){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter valid email");
                        $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter valid email");
                        $scope.reverse();
                        return;
                    }
                }

                if(!$scope.email.body){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the Message");
                        $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the Message");
                        $scope.reverse();
                        return;
                    }
                }

                if($scope.email.captchaCode ==''){

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


                else {
                    var encrypted = CryptoJS.AES.encrypt($scope.email.captchaCode, 'ABCD123');
                    var data = {
                        "name": $scope.email.name,
                        "captcha" : $scope.email.captchaCode,
                        "cipherTextHex": encrypted.ciphertext.toString(),
                        "saltHex": encrypted.salt.toString(),
                        "email" : $scope.email.email,
                        "body" : $scope.email.body,
                        "subject" : $scope.email.subject,
                        "phoneNo" : $scope.email.phoneNo
                    };
                    $http({
                        method  : 'POST',
                        url     : backend_root + 'nms/mail/sendEmailForContactUs',
                        data    : JSON.stringify(data), //forms user object
                        headers : {'Content-Type': 'application/json'}
                    }).then(function(){
                        if(UserFormFactory.isInternetExplorer()){
                            alert('Contact-us form submitted successfully')
                            $state.go($state.current, {}, {reload: true});
                            return;
                        }
                        else{
                            UserFormFactory.showAlert('Contact-us form submitted successfully')
                            $state.go($state.current, {}, {reload: true});
                            return;
                        }
                    })
                }
		    }

            $scope.reverse = function(){
                   $(':input[type="submit"]').text('Submit');
                   $(':input[type="submit"]').prop('disabled', false);
                   $(':input[type="submit"]').css('background-color', '#2164b2');
                    }
		}])
})();