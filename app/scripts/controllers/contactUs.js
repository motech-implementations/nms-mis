(function(){
	var nmsReportsApp = angular
		.module('nmsReports');
		nmsReportsApp.controller("ContactUsController", ['$scope', '$state', 'UserFormFactory','$http', '$location', function($scope,$state,UserFormFactory, $http, $location ){

            $scope.contactUsUrl = backend_root + 'nms/mail/sendEmailForContactUs';

            $scope.contactUs = {};
            $scope.email = {};

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
                        alert("Please enter a valid Phone Number")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter a valid Phone Number")
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

                if(!$scope.email.email){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the email")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the email")
                        return;
                    }
                }

                if(!validateEmail($scope.email.email)){
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
                        alert("Please enter the Message")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the Message")
                        return;
                    }
                }

                else {
                    $http({
                        method  : 'POST',
                        url     : backend_root + 'nms/mail/sendEmailForContactUs',
                        data    : $scope.email, //forms user object
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
		}])
})();