 (function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', '$state', '$http', 'UserFormFactory', function($scope, $state, $http, UserFormFactory){
			
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
				], 
				'userManagement.editUser': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable',
						'active': true,
					},
					{
						'name': 'Edit User',
						'sref': 'userManagement.editUser',
						'active': false,
					},
				], 
				'userManagement.bulkUpload': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable',
						'active': true,
					},
					{
						'name': 'Bulk Upload',
						'sref': 'userManagement.bulkUpload',
						'active': false,
					},
				],
				'profile': [
					{
						'name': 'Profile',
						'sref': 'profile',
						'active': false,
					},
				],
				'reports': [
					{
						'name': 'Reports',
						'sref': 'reports',
						'active': false,
					},
				],
				'login': [
					{
						'name': 'Login',
						'sref': 'login',
						'active': false,
					},
				]
			}


			$scope.getBreadCrumb = function(state){
				return $scope.breadCrumbDict[state];
			}

			$scope.title = function(state){
				var states = $scope.getBreadCrumb(state)
				return states[states.length - 1].name
			}

			$scope.activeTab = function(tabName){
				return $state.current.name.includes(tabName);
			}

			// $scope.breadCrumb = ['User Management', 'Create User'];
			UserFormFactory.downloadCurrentUser()
			.then(function(result){
				UserFormFactory.setCurrentUser(result.data);
				$scope.currentUser = UserFormFactory.getCurrentUser();
			})

			$scope.logoutUrl = backend_root + "nms/logout";

		}
	])}
)()