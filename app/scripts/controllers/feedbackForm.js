(function(){
	var nmsReportsApp = angular
		.module('nmsReports');

	nmsReportsApp.controller("FeedbackFormController", ['$scope', '$state', 'UserFormFactory','$http', '$location','Captcha',function($scope, $state, UserFormFactory,$http,$location,Captcha){

            $scope.feedbackUrl = backend_root + 'nms/mail/sendFeedback1';

			$scope.feedback = {};
			$scope.email = {};

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

                if(!$scope.captchaResponse){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Check captcha");
                         $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Check captcha");
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