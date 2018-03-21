 (function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', '$state', '$http', '$localStorage', '$rootScope', 'UserFormFactory', function($scope, $state, $http, $localStorage, $rootScope, UserFormFactory) {
			$scope.isCollapsed = true;
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
				if (states != null) {
					return states[states.length - 1].name;
				} else {
					return "Please Wait...";
				}
			};
			$scope.goToLogin = function () {
                $state.go('login', {pageNum: 1});
            }
            $scope.goToAboutus = function () {
                $state.go('login', {pageNum: 1});
            }
            $scope.goToKilkari = function () {
                $state.go('login', {pageNum: 1});
            }
            $scope.goToUserManual = function () {
                $state.go('userManual.websiteInformation', {pageNum: 1});
            }
            $scope.goToFaq = function () {
                $state.go('faq.faqWebsiteInformation', {pageNum: 1});
            }
            $scope.goToContactUs = function () {
                $state.go('contactUs', {pageNum: 1});
            }
            $scope.goToFeedback = function () {
                $state.go('feedbackForm', {pageNum: 1});
            }
$scope.goToReports = function() {
delete $localStorage.filter;
if(!(($state.current.name)===("reports"))&&!(($state.current.name)===("profile"))&&!
                (($state.current.name)===("forgotPassword"))&&!(($state.current.name)===("changePassword"))&&!
                (($state.current.name)===("userManagement.bulkUpload"))&&!(($state.current.name)===("userManagement"))&&!
                (($state.current.name)===("userManagement.createUser"))&&!(($state.current.name)===("userManagement.userTable"))&&!
                (($state.current.name)===("userManagement.editUser"))){
			    return false;}
			    if ($state.current.name !== 'reports') {
                                    $state.go('reports', {pageNum: 1});
                                }
};

			$scope.goToUserTable = function() {
			    delete $localStorage.filter;
			    if(!(($state.current.name)===("reports"))&&!(($state.current.name)===("profile"))&&!
                (($state.current.name)===("forgotPassword"))&&!(($state.current.name)===("changePassword"))&&!
                (($state.current.name)===("userManagement.bulkUpload"))&&!(($state.current.name)===("userManagement"))&&!
                (($state.current.name)===("userManagement.createUser"))&&!(($state.current.name)===("userManagement.userTable"))&&!
                (($state.current.name)===("userManagement.editUser"))){
			    return false;}
                if ($state.current.name !== 'userManagement.userTable') {
                    $state.go('userManagement.userTable', {pageNum: 1});
                }
			};


			$scope.goToProfile = function() {
            			    delete $localStorage.filter;
            			    if(!(($state.current.name)===("reports"))&&!(($state.current.name)===("profile"))&&!
                            (($state.current.name)===("forgotPassword"))&&!(($state.current.name)===("changePassword"))&&!
                            (($state.current.name)===("userManagement.bulkUpload"))&&!(($state.current.name)===("userManagement"))&&!
                            (($state.current.name)===("userManagement.createUser"))&&!(($state.current.name)===("userManagement.userTable"))&&!
                            (($state.current.name)===("userManagement.editUser"))){
            			    return false;}
                            if ($state.current.name !== 'profile') {
                                $state.go('profile', {pageNum: 1});
                            }
            			};

            			$scope.goToChangePassword = function() {
                                    			    delete $localStorage.filter;
                                    			    if(!(($state.current.name)===("reports"))&&!(($state.current.name)===("profile"))&&!
                                                    (($state.current.name)===("forgotPassword"))&&!(($state.current.name)===("changePassword"))&&!
                                                    (($state.current.name)===("userManagement.bulkUpload"))&&!(($state.current.name)===("userManagement"))&&!
                                                    (($state.current.name)===("userManagement.createUser"))&&!(($state.current.name)===("userManagement.userTable"))&&!
                                                    (($state.current.name)===("userManagement.editUser"))){
                                    			    return false;}
                                                    if ($state.current.name !== 'changePassword') {
                                                        $state.go('changePassword', {pageNum: 1});
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
            $scope.date = new Date();

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
	]);

	 }

)();