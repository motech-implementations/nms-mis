(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('bulkUser', function() {
			return {
				restrict: 'AC',
				templateUrl: '../htpagesmis/bulkUser.html',

			};
		})
})()