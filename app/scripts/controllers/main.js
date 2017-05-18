(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', '$state', '$http', 'UserFormFactory', function($scope, $state, $http, UserFormFactory,){
			
			$scope.breadcrumbDict = {
				'userManagement.userTable': ['User Management'],
				'userManagement.createUser': ['User Management', 'Create User'],
				
			}

			$scope.activeTab = function(tabName){
				return $state.current.name.includes(tabName);
			}

			$scope.breadCrumb = ['User Management', 'Create User'];
		}
	])}
)()