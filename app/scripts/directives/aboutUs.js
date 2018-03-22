(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('AboutUs', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/aboutUs.html',

			};
		})
})()