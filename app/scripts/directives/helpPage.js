(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('Help', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/helpPage.html',
			};
		})
})()