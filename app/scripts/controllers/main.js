(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', '$state', '$http', 'UserFormFactory', function($scope, $state, $http, UserFormFactory,){
			
			$scope.breadCrumbDict = {
				'userManagement.userTable': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable',
						'active': false,
					}
				],
				'userManagement.createUser': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable',
						'active': true,
					},
					{
						'name': 'Create User',
						'sref': 'userManagement.createUser',
						'active': false,
					},
				]
			}

			$scope.getBreadCrumb = function(state){
				return $scope.breadCrumbDict[state];
			}

			$scope.activeTab = function(tabName){
				return $state.current.name.includes(tabName);
			}

			$scope.breadCrumb = ['User Management', 'Create User'];
		}
	])}
)()