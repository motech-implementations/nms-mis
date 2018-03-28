(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ContactUsController", ['$scope', '$state', 'UserFormFactory','$http', '$location', function($scope, $state,$location,$http, UserFormFactory){

            $scope.contactUsUrl = backend_root + 'nms/mail/sendEmailForContactUs';

            $scope.contactUs = {};

            $scope.contactUs = function(e){





                var formElement = angular.element(e.target);
                formElement.attr("action", $scope.contactUsUrl);
                formElement.attr("method", "post");
                formElement[0].submit();
		    }
		}])
})();