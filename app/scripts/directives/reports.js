(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('reports', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/reports.html',
			};
		})
		
})()