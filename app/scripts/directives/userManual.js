(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('userManual', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/userManual.html',
			};
		})

})()