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

            $scope.reverse = function(){
                   $(':input[type="submit"]').text('Submit');
                   $(':input[type="submit"]').prop('disabled', false);
                   $(':input[type="submit"]').css('background-color', '#2164b2');
                    }
		}])
})();