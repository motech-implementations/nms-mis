(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ContactUsResponseController", ['$scope', '$state', '$timeout', function($scope, $state, $timeout){

            $scope.goBackToContactUsForm = function(){
                $state.go('contactUs')
            }


		}])
})();