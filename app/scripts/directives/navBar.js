(function(){
    var nmsReportsApp = angular
        .module('nmsReports')
		.directive('navBar', function() {
	        return {
	            restrict: 'AC',
	            templateUrl: '../views/navBar.html',
	        };
	    })
})()