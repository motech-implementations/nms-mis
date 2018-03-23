(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('HLPolicy', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/hyperLinkingPolicy.html',
			};
		})
})()