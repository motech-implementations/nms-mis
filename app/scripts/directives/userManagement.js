(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('userManagement', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/userManagement.html',
			};
		})
		
})()