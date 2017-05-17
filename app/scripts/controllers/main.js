(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', 'UserFormFactory', '$state', function($scope, UserFormFactory, $state){
			
			$scope.breadcrumbDict = {
				'userManagement.userTable': ['User Management'],
				'userManagement.createUser': ['User Management', 'Create User'],
				
			}
			// $scope.onUserManagementLoad = function() {
			// 	$state.go('user-management.user-table')
			// };

			// $scope.onUserManagementLoad();
		}]
	)}
)()