(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserManagementController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){
		// 	UserFormFactory.downloadCurrentUser()
		// 	.then(function(result){
		// 		UserFormFactory.setCurrentUser(result.data);
		// 		$scope.currentUser = UserFormFactory.getCurrentUser;

		// 		if($scope.currentUser == null || $scope.currentUser == undefined){
		// 			$state.go('login', {});
		// 		}
		// 	})

		}])
})()