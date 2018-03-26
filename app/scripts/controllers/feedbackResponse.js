(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("FeedbackResponseController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){

            $scope.goBackToFeedbackForm = function(){
                $state.go('feedbackForm')
            }

		}])
})();