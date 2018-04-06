 (function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', '$state', '$http', '$localStorage', '$rootScope', 'UserFormFactory','$idle', function($scope, $state, $http, $localStorage, $rootScope, UserFormFactory,$idle) {

		    var logoutUrl = backend_root + "nms/logout";
		    $scope.isCollapsed = true;
            $scope.aboutUsBool = true;
            $scope.kilkariBool = true;
            $scope.maBool = true;
            $scope.userManualBool = true;
            $scope.faqBool = true;
            $scope.repBool = true;
            $scope.usermBool = true;
            $scope.feedbackBool = true;
            $scope.contusBool = true;
            $scope.profileBool = true;
            $scope.loginBool = true;
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
            $scope.checkLogin = function(){
                return (($state.current.name=="login" || $state.current.name=="forgotPassword" || !$scope.currentUser));
            };
            $scope.checkProfile = function(){
                return (($state.current.name=="profile" || $state.current.name=="changePassword" ));
            };

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
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                $state.go('login', {pageNum: 1});
            }
            $scope.goToAboutus = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('AboutUs', {pageNum: 1});
                }

            }
            $scope.goToKilkari = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('AboutKilkari', {pageNum: 1});
                }
            }
            $scope.goToMobileA = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('AboutMA', {pageNum: 1});
                }
            }
            $scope.goToUserManual = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
               if (!($scope.disableCursor())){
                   $state.go('userManual.websiteInformation', {pageNum: 1});
               }

            }
            $scope.goToFaq = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('faq.faqWebsiteInformation', {pageNum: 1});
                }
            }
            $scope.goToContactUs = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('contactUs', {pageNum: 1});
                }
            }
            $scope.goToFeedback = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                   if (!($scope.disableCursor())){
                       $state.go('feedbackForm', {pageNum: 1});
                   }


            }
            $scope.goToPrivacyPolicy = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('PrivacyPolicy', {pageNum: 1});
                }
            }
            $scope.goToCopyrightPolicy = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('CopyrightPolicy', {pageNum: 1});
                }
            }
            $scope.goToTandC = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
			    if (!($scope.disableCursor())){
                    $state.go('TandC', {pageNum: 1});
                }

            }
            $scope.goToHLPolicy = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('HLPolicy', {pageNum: 1});
                }
            }
            $scope.goToDisclaimer = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('Disclaimer', {pageNum: 1});
                }
            }
            $scope.goToHelp = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('Help', {pageNum: 1});
                }
            }
            $scope.goToSitemap = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('sitemap', {pageNum: 1});
                }
            }

            $scope.hovered = function () {
                $scope.show = !$scope.show;
            }
            $scope.removed = function () {
                $scope.show = false;
            }
            $scope.goToReports = function() {
            delete $localStorage.filter;
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if($scope.disableCursor()){
                    return false;
                };
                if ($state.current.name !== 'reports') {
                    $state.go('reports', {pageNum: 1});
                };
            };

			$scope.goToUserTable = function() {
			    delete $localStorage.filter;
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if($scope.disableCursor()){
                    return false;};
                if ($state.current.name !== 'userManagement.userTable') {
                    $state.go('userManagement.userTable', {pageNum: 1});
                }
			};


			$scope.goToProfile = function() {
			    delete $localStorage.filter;
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if($scope.disableCursor()){
                    return false;};
                if ($state.current.name !== 'profile') {
                    $state.go('profile', {pageNum: 1});
                    $scope.show = false;
                 };
            			};
            $scope.goToLogout = function () {

                UserFormFactory.logoutUser().then(function(result){
                    if(result.data){

                        UserFormFactory.downloadCurrentUser().then(function(result){
                            UserFormFactory.setCurrentUser(result.data);
                            $scope.currentUser = UserFormFactory.getCurrentUser();
                        });
                        $scope.show = false;
                        $state.go('login');
                    }
                });


            };
            $scope.goToChangePassword = function() {
                    delete $localStorage.filter;
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if($scope.disableCursor()){
                    return false;}
                    if ($state.current.name !== 'changePassword') {
                        $state.go('changePassword', {pageNum: 1});
                        $scope.show = false;
                    }
            };

			$scope.activeTab = function(tabName){
				return ($state.current.name.indexOf(tabName) > -1);
			};
			$scope.activeTabProfile = function (tabName) {
                return (($state.current.name) === (tabName));
            }
            $scope.reportsActive = function(){
                return (($state.current.name.indexOf('reports') > -1)||($scope.disableCursor()));
            };

			$scope.disableCursor = function(){

            				return (($state.current.name)===("MA Cumulative Summary"))||(($state.current.name)===("MA Subscriber"))||
                                                                      (($state.current.name)===("MA Performance"))||(($state.current.name)===("Kilkari Cumulative Summary"))||
                                                                      (($state.current.name)===("Kilkari Beneficiary Completion"))||(($state.current.name)===("Kilkari Usage"))||
                                                                      (($state.current.name)===("Kilkari Call"))||(($state.current.name)===("Kilkari Message Matrix"))||
                                                                      (($state.current.name)===("Kilkari Listening Matrix"))||(($state.current.name)===("Kilkari Thematic Content"))||
                                                                      (($state.current.name)===("Kilkari Repeat Listener"))||(($state.current.name)===("Kilkari Subscriber"))||
                                                                      (($state.current.name)===("Kilkari Message Listenership"))||(($state.current.name)===("Kilkari Aggregate Beneficiary"));
            			};

			UserFormFactory.downloadCurrentUser().then(function(result){
				UserFormFactory.setCurrentUser(result.data);
				$scope.currentUser = UserFormFactory.getCurrentUser();
			});


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
            $scope.$on('$userIdle', function () {
                if (!($scope.checkLogin())){

                    if(UserFormFactory.isInternetExplorer()){
                        alert("session timed out login again");
                        $scope.goToLogout();
                        return;
                    }
                    else{
                      var a= UserFormFactory.showAlert2("session timed out login again");
                      a.then(function () {
                          $scope.goToLogout();
                          return;
                      });

                    }
                }
                else {

                }

            });
		}
	]);

	 }

)();