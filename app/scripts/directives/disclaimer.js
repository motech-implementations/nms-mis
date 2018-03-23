(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('Disclaimer', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/disclaimer.html',
			};
		})
})()