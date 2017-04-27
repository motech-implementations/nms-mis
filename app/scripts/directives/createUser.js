(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('createUser', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/createUser.html',
				scope:{
					'newUser':'='
				}
			};
		})
})()