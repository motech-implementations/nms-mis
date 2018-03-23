(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('PrivacyPolicy', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/privacyPolicy.html',
			};
		})
})()