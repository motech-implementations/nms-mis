(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("kilkariController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){

			UserFormFactory.isAdminLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

		}])
})();

