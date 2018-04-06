(function(){
	var nmsReportsApp = angular
		.module('nmsReports');

	nmsReportsApp.controller("FeedbackFormController", ['$scope', '$state', 'UserFormFactory','$http', '$location','Captcha',function($scope, $state, UserFormFactory,$http,$location,Captcha){

            $scope.feedbackUrl = backend_root + 'nms/mail/sendFeedback1';

			$scope.feedback = {};
			$scope.email = {};

            var emailField = $scope.email.to
            function validateEmail(emailField){
                    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                        return reg.test(emailField);
            }

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

                if(!validateEmail($scope.email.to)){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter valid email")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter valid email")
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

                if(!$scope.captchaResponse){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Check captcha")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Check captcha")
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

		}]);
	nmsReportsApp.controller("FeedbackResponseController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){

        $scope.goBackToFeedbackForm = function(){
            $state.go('feedbackForm')
        }

    }]);
})();