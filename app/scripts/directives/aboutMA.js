(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('AboutMA', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/aboutMA.html',

			};
		})
})()