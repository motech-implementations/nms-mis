(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ContactUsController", ['$scope', '$state', 'UserFormFactory','$http', '$location', function($scope,$state,UserFormFactory, $http, $location ){

            $scope.contactUsUrl = backend_root + 'nms/mail/sendEmailForContactUs';

            $scope.contactUs = {};
            $scope.email = {};

            var emailField = $scope.email.to
            function validateEmail(emailField){
                var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return reg.test(emailField);
            }

            var phoneNoField = $scope.email.phoneNo
            function validatePhoneNo(phoneNoField){
//                var reg =/^\s*(?:\+?(\d{1,3}))?[-. (]*(\d{3})[-. )]*(\d{3})[-. ]*(\d{4})(?: *x(\d+))?\s*$/;
                  var reg = /^(?:\+\d+[- ])?\d{10}$/;
                return reg.test(phoneNoField);
            }


            $scope.contactUs = function(e){

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

                if(!$scope.email.phoneNo){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the PhoneNo")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the PhoneNo")
                        return;
                    }
                }

                if(!validatePhoneNo($scope.email.phoneNo)){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter valid phone No.")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter valid phone No.")
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


                var formElement = angular.element(e.target);
                formElement.attr("action", $scope.contactUsUrl);
                formElement.attr("method", "post");
                formElement[0].submit();
		    }
		}])
})();