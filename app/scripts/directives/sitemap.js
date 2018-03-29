(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('sitemap', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/sitemap.html',
			};
		})

})()