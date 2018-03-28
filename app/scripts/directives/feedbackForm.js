(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('feedbackForm', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/feedbackForm.html',
			};
		})

})()