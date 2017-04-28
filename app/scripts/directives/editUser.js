(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('editUser', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/editUser.html',
				scope:{
					'user':'='
				}
			};
		})
})()