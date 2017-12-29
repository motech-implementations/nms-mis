 (function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', '$state', '$http', '$localStorage', '$rootScope', 'UserFormFactory', function($scope, $state, $http, $localStorage, $rootScope, UserFormFactory) {
			
			$scope.breadCrumbDict = {
				'userManagement.userTable': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable({pageNum: 1})',
						'active': false
					}
				],
				'userManagement.createUser': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable({pageNum: 1})',
						'active': true
					},
					{
						'name': 'Create User',
						'sref': 'userManagement.createUser',
						'active': false
					}
				], 
				'userManagement.editUser': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable({pageNum: 1})',
						'active': true
					},
					{
						'name': 'Edit User',
						'sref': 'userManagement.editUser({pageNum: 1})',
						'active': false
					}
				], 
				'userManagement.bulkUpload': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable({pageNum: 1})',
						'active': true
					},
					{
						'name': 'Bulk Upload',
						'sref': 'userManagement.bulkUpload',
						'active': false
					}
				],
				'profile': [
					{
						'name': 'Profile',
						'sref': 'profile',
						'active': false
					}
				],
				'reports': [
					{
						'name': 'Reports',
						'sref': 'reports',
						'active': false
					}
				],
				'login': [
					{
						'name': 'Login',
						'sref': 'login',
						'active': false
					}
				],
				'forgotPassword': [
                	{
                		'name': 'ForgotPassword',
                		'sref': 'forgotPassword',
                		'active': false
                	}
                ],
                'changePassword': [
                    {
                        'name': 'ChangePassword',
                        'sref': 'changePassword',
                        'active': false
                    }
                ]
			};

            $scope.child = {};

			$scope.getBreadCrumb = function(state){
				return $scope.breadCrumbDict[state];
			};

			$scope.getTitle = function(state){
				var states = $scope.getBreadCrumb(state);
				if (states !== null) {
					return states[states.length - 1].name;
				} else {
					return "Please Wait...";
				}
			};

			$scope.goToUserTable = function() {
			    delete $localStorage.filter;
                if ($state.current.name !== 'userManagement.userTable') {
                    $state.go('userManagement.userTable', {pageNum: 1});
                }
			};

			$scope.activeTab = function(tabName){
				return ($state.current.name.indexOf(tabName) > -1);
			};

			UserFormFactory.downloadCurrentUser().then(function(result){
				UserFormFactory.setCurrentUser(result.data);
				$scope.currentUser = UserFormFactory.getCurrentUser();
			});

			$scope.logoutUrl = backend_root + "nms/logout";

			$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
			    if (toState.name === 'login') {
			        UserFormFactory.isLoggedIn().then(function(res) {
                        if (res.data) {
                            $state.go('reports');
                        }
                    });
                }
            });
		}
	])}

)();