(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('TandC', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/tAndC.html',
			};
		})
})()