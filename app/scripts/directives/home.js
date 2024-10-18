(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('home', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/home.html',
			};
		})

})()