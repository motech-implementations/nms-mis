(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.directive('editUser', function() {
			return {
				restrict: 'AC',
				templateUrl: '../htpagesmis/editUser.html',
				scope:{
					'user':'='
				}
			};
		})
})()