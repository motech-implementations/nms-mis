 (function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', '$state', '$http', '$localStorage', '$rootScope', 'UserFormFactory','$idle','$window','$interval', function($scope, $state, $http, $localStorage, $rootScope, UserFormFactory,$idle,$window,$interval) {

		    var logoutUrl = backend_root + "nms/logout";
		    var timestamp = new Date().getTime();
		    $scope.ondropdown = false;
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

//			$(window).focus(function() {
//                //do something
//                if (document.hasFocus()) {
//                			location.reload();
//                			}
//
//            });

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
                $scope.removed();

            }
            $scope.goToKilkari = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('AboutKilkari', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToMobileA = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('AboutMA', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToUserManual = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
               if (!($scope.disableCursor())){
                   $state.go('userManual.websiteInformation', {pageNum: 1});
               }
               $scope.removed();

            }
            $scope.goToFaq = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('faq.faqWebsiteInformation', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToContactUs = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())){
                    $state.go('contactUs', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToFeedback = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                   if (!($scope.disableCursor())){
                       $state.go('feedbackForm', {pageNum: 1});
                   }
                $scope.removed();

            }
            $scope.goToPrivacyPolicy = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('PrivacyPolicy', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToCopyrightPolicy = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('CopyrightPolicy', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToTandC = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
			    if (!($scope.disableCursor())){
                    $state.go('TandC', {pageNum: 1});
                }
            $scope.removed();
            }
            $scope.goToHLPolicy = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('HLPolicy', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToDisclaimer = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('Disclaimer', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToHelp = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('Help', {pageNum: 1});
                }
                $scope.removed();
            }
            $scope.goToSitemap = function () {
                UserFormFactory.downloadCurrentUser().then(function(result){
                    UserFormFactory.setCurrentUser(result.data);
                    $scope.currentUser = UserFormFactory.getCurrentUser();
                });
                if (!($scope.disableCursor())) {
                    $state.go('sitemap', {pageNum: 1});
                }
                $scope.removed();
            }

            $scope.hovered = function () {
                $scope.ondropdown = true;
                $scope.show = !$scope.show;
            }

            $scope.ondropdownfn = function () {
               $scope.ondropdown = false;
            }
            $scope.removed = function () {
                $scope.show = false;
            }

            $window.addEventListener('click', function() {
                localStorage.setItem('lastEventTime', new Date().getTime());
                if($scope.show&&!$scope.ondropdown){
                     $scope.removed();
                }
            });

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
                $scope.removed();
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
                $scope.removed();
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
                 $scope.removed();
            			};
            $scope.goToLogout = function () {

                UserFormFactory.logoutUser().then(function(result){
                    if(result.data){
                        window.localStorage.setItem('logged_in', false);
                        UserFormFactory.downloadCurrentUser().then(function(result){
                            UserFormFactory.setCurrentUser(result.data);
                            $scope.currentUser = UserFormFactory.getCurrentUser();
                        });
                        $scope.show = false;
                        $state.go('login');
                    }
                });


            };


            function storageChange (event) {
            if(UserFormFactory.isInternetExplorer()){
                if(event.key === 'logged_in') {
                    //alert('Logged in: ' + event.newValue);
                    $state.go('login');
                }}
                window.localStorage.setItem('logged_in', true);
            }
            window.addEventListener('storage', storageChange, false)

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
                    $scope.removed();
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
                                                                      (($state.current.name)===("Kilkari Message Listenership"))||(($state.current.name)===("Kilkari Aggregate Beneficiaries"));
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
            if(new Date().getTime()-localStorage.lastEventTime>1800000){
                if (!($scope.checkLogin())){

                    if(UserFormFactory.isInternetExplorer()){
                        alert("Sorry, Your Session timed out after a long time of inactivity. Please, login again");
                        $scope.goToLogout();
                        return;
                    }
                    else{
                      var a= UserFormFactory.showAlert2("Sorry, Your Session timed out after a long time of inactivity. Please, login in again");
                      a.then(function () {
                          $scope.goToLogout();
                          return;
                      });

                    }
                }}

            });
            $scope.$watch('online', function(newStatus) {
            if($rootScope.online){
            }
            else{
                    if (UserFormFactory.isInternetExplorer()) {
                            alert("You are offline");
                             return;
                        } else {
                            UserFormFactory.showAlert("You are offline");
                            return;
                        }
            }
            });

            $interval(function() {
                if (localStorage.lastEventTime > timestamp) {
                     $idle.watch();
                     timestamp = localStorage.lastEventTime;
                }
            }, 2000);

		}
	]);

	 }

)();