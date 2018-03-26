(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ContactUsController", ['$scope', '$state', 'UserFormFactory','$http', '$location', function($scope, $state,$location,$http, UserFormFactory){

//			UserFormFactory.isAdminLoggedIn()
//			.then(function(result){
//				if(!result.data){
//					$state.go('login', {});
//				}
//			})

            $scope.contactUsUrl = backend_root + 'nms/mail/sendEmailForContactUs';

//            var url = $location.absUrl();
//            var error = url.split('?')[1]
//            $scope.preUrl = "";
//            $scope.errorMessage = "";
//            if(error == null){
//                $scope.errorMessage = "";
//            }
//            else{
//                $scope.errorMessage = "Invalid data";
//            }
            $scope.contactUs = {};




            $scope.contactUs = function(e){

                     var formElement = angular.element(e.target);
                     formElement.attr("action", $scope.contactUsUrl);
                     formElement.attr("method", "post");
                     formElement[0].submit();

		}






		}])
})();