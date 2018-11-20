(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('createUser', function() {
			return {
			    require: 'ngMessages',
				restrict: 'AC',
				templateUrl: '../views/createUser.html',
				scope:{
					'newUser':'='
				}
			};
		})
})()