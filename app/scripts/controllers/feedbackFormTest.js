(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("FeedbackFormController", ['$scope', '$state', 'UserFormFactory','$http', '$location','Captcha',function($scope, $state, UserFormFactory,$http,$location,Captcha){

            $scope.feedbackUrl = backend_root + 'nms/mail/sendEmail';

			var url = $location.absUrl();
			var error = url.split('?')[1]
            $scope.preUrl = "";
			$scope.errorMessage = "";
			if(error == null){
				$scope.errorMessage = "";
			}
			else{
				$scope.errorMessage = "Invalid data";
			}
			$scope.feedback = {};


            $scope.feedback = function(e){

                 var formElement = angular.element(e.target);
                 formElement.attr("action", $scope.feedbackUrl);
                 formElement.attr("method", "post");
                 formElement[0].submit();

		    }
		}])
})();