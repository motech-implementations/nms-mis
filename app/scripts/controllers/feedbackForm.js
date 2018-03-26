(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("FeedbackFormController", ['$scope', '$state', 'UserFormFactory','$http', '$location','Captcha',function($scope, $state, UserFormFactory,$http,$location,Captcha){

            $scope.feedbackUrl = backend_root + 'nms/mail/sendFeedback';

			$scope.feedback = {};
			$scope.email = {};

            $scope.feedback = function(e){

                if(!$scope.email.name){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the name")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the name")
                        return;
                    }
                }

                if(!$scope.email.subject){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the Subject")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the Subject")
                        return;
                    }
                }

                if(!$scope.email.to){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the email")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the email")
                        return;
                    }
                }

                if(!$scope.email.body){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the Feedback")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the Feedback")
                        return;
                    }
                }

                if(!$scope.feedback.captchaCode){
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

                // captcha id for validating captcha at server-side
                var captchaId = captcha.captchaId;

                // captcha code input value for validating captcha at server-side
                var captchaCode = angular.uppercase($scope.feedback.captchaCode);

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
                             formElement.attr("action", $scope.feedbackUrl);
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

                     })


		    }

		}])
})();