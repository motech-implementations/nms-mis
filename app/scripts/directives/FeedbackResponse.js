(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('feedbackResponse', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/feedbackResponse.html',
			};
		})

})()