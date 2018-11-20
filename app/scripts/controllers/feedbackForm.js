(function(){
	var nmsReportsApp = angular
		.module('nmsReports');

	nmsReportsApp.controller("FeedbackFormController", ['$scope', '$state', 'UserFormFactory','$http', '$location',function($scope, $state, UserFormFactory,$http,$location){
            UserFormFactory.isLoggedIn()
            			.then(function(result){
                        if(result.data){
UserFormFactory.downloadCurrentUser()
					.then(function(result){
					                        $scope.email.name=result.data.fullName;
					                        $scope.email.email=result.data.emailId;
					})
					}
            			})

            $scope.feedbackUrl = backend_root + 'nms/mail/sendFeedback1';

			$scope.feedback = {};
			$scope.email = {};
			$scope.email.captchaCode = '';


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

            var emailField = $scope.email.email;

                        function validateEmail(emailField){
                                var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                                    return reg.test(emailField);
                        }


            $scope.feedback = function(e){
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

                if(!$scope.email.subject){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the Subject");
                         $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the Subject");
                         $scope.reverse();
                        return;
                    }
                }

//                if(!$scope.email.email){
//                    if(UserFormFactory.isInternetExplorer()){
//                        alert("Please enter the email");
//                         $scope.reverse();
//                        return;
//                    }
//                    else{
//                        UserFormFactory.showAlert("Please enter the email");
//                         $scope.reverse();
//                        return;
//                    }
//                }
//
//                if(!validateEmail($scope.email.email)){
//                    if(UserFormFactory.isInternetExplorer()){
//                        alert("Please enter valid email");
//                         $scope.reverse();
//                        return;
//                    }
//                    else{
//                        UserFormFactory.showAlert("Please enter valid email");
//                         $scope.reverse();
//                        return;
//                    }
//                }

                if(!$scope.email.body){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the Feedback");
                         $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the Feedback");
                         $scope.reverse();
                        return;
                    }
                }

//                if(!$scope.captchaResponse){
//                    if(UserFormFactory.isInternetExplorer()){
//                        alert("Check captcha");
//                         $scope.reverse();
//                        return;
//                    }
//                    else{
//                        UserFormFactory.showAlert("Check captcha");
//                         $scope.reverse();
//                        return;
//                    }
//                }

               if($scope.email.captchaCode ==''){
                console.log($scope.user);
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
                    $http({
                        method  : 'POST',
                        url     : backend_root + 'nms/mail/sendFeedback',
                        data    : $scope.email, //forms user object
                        headers : {'Content-Type': 'application/json'}
                    }).then(function(){
                        if(UserFormFactory.isInternetExplorer()){
                            alert('feedback form submitted successfully')
                            $state.go($state.current, {}, {reload: true});
                            return;
                        }
                        else{
                            UserFormFactory.showAlert('feedback form submitted successfully')
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



		}]);

	nmsReportsApp.controller("FeedbackResponseController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){

        $scope.goBackToFeedbackForm = function(){
            $state.go('feedbackForm')
        }

    }]);
})();