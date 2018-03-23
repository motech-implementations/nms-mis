(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('CopyrightPolicy', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/copyrightPolicy.html',
			};
		})
})()