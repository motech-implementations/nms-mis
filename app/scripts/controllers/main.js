 (function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', '$state', '$http', '$localStorage', '$rootScope', 'UserFormFactory', function($scope, $state, $http, $localStorage, $rootScope, UserFormFactory) {
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
                $state.go('AboutUs', {pageNum: 1});
            }
            $scope.goToKilkari = function () {
                $state.go('AboutKilkari', {pageNum: 1});
            }
            $scope.goToMobileA = function () {
                $state.go('AboutMA', {pageNum: 1});
            }
            $scope.goToUserManual = function () {
                $state.go('userManual.websiteInformation', {pageNum: 1});
                if ((($state.current.name)===("userManual")) || (($state.current.name)===("userManual.kilkari"))||(($state.current.name)===("userManual.mobileAcademy"))||
                    (($state.current.name)===("userManual.websiteInformation"))||(($state.current.name)===("userManual.userManual_Management"))||(($state.current.name)===("userManual.userManual_Profile"))){
                    $scope.userManualBool = false;
                }

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
            $scope.goToPrivacyPolicy = function () {
                $state.go('PrivacyPolicy', {pageNum: 1});
            }
            $scope.goToCopyrightPolicy = function () {
                $state.go('CopyrightPolicy', {pageNum: 1});
            }
            $scope.goToTandC = function () {
                $state.go('TandC', {pageNum: 1});
            }
            $scope.goToHLPolicy = function () {
                $state.go('HLPolicy', {pageNum: 1});
            }
            $scope.goToDisclaimer = function () {
                $state.go('Disclaimer', {pageNum: 1});
            }
            $scope.goToHelp = function () {
                $state.go('Help', {pageNum: 1});
            }

            $scope.hovered = function () {
                $scope.show = true;
            }
            $scope.removed = function () {
                $scope.show = false;
            }
            $scope.goToReports = function() {
            delete $localStorage.filter;
            if((($state.current.name)===("MA Cumulative Summary"))||(($state.current.name)===("MA Subscriber"))||
            (($state.current.name)===("MA Performance"))||(($state.current.name)===("Kilkari Cumulative Summary"))||
            (($state.current.name)===("Kilkari Beneficiary Completion"))||(($state.current.name)===("Kilkari Usage"))||
            (($state.current.name)===("Kilkari Call"))||(($state.current.name)===("Kilkari Message Matrix"))||
            (($state.current.name)===("Kilkari Listening Matrix"))||(($state.current.name)===("Kilkari Thematic Content"))||
            (($state.current.name)===("Kilkari Repeat Listener"))||(($state.current.name)===("Kilkari Subscriber"))||
            (($state.current.name)===("Kilkari Message Listenership"))||(($state.current.name)===("Kilkari Aggregate Beneficiary"))){
                            return false;}
                            if ($state.current.name !== 'reports') {
                                                $state.go('reports', {pageNum: 1});
                                            }
            };

			$scope.goToUserTable = function() {
			    delete $localStorage.filter;
			    if((($state.current.name)===("MA Cumulative Summary"))||(($state.current.name)===("MA Subscriber"))||
                               (($state.current.name)===("MA Performance"))||(($state.current.name)===("Kilkari Cumulative Summary"))||
                               (($state.current.name)===("Kilkari Beneficiary Completion"))||(($state.current.name)===("Kilkari Usage"))||
                               (($state.current.name)===("Kilkari Call"))||(($state.current.name)===("Kilkari Message Matrix"))||
                               (($state.current.name)===("Kilkari Listening Matrix"))||(($state.current.name)===("Kilkari Thematic Content"))||
                               (($state.current.name)===("Kilkari Repeat Listener"))||(($state.current.name)===("Kilkari Subscriber"))||
                               (($state.current.name)===("Kilkari Message Listenership"))||(($state.current.name)===("Kilkari Aggregate Beneficiary"))){
			    return false;}
                if ($state.current.name !== 'userManagement.userTable') {
                    $state.go('userManagement.userTable', {pageNum: 1});
                }
			};


			$scope.goToProfile = function() {
            			    delete $localStorage.filter;
            			    if((($state.current.name)===("MA Cumulative Summary"))||(($state.current.name)===("MA Subscriber"))||
                                           (($state.current.name)===("MA Performance"))||(($state.current.name)===("Kilkari Cumulative Summary"))||
                                           (($state.current.name)===("Kilkari Beneficiary Completion"))||(($state.current.name)===("Kilkari Usage"))||
                                           (($state.current.name)===("Kilkari Call"))||(($state.current.name)===("Kilkari Message Matrix"))||
                                           (($state.current.name)===("Kilkari Listening Matrix"))||(($state.current.name)===("Kilkari Thematic Content"))||
                                           (($state.current.name)===("Kilkari Repeat Listener"))||(($state.current.name)===("Kilkari Subscriber"))||
                                           (($state.current.name)===("Kilkari Message Listenership"))||(($state.current.name)===("Kilkari Aggregate Beneficiary"))){
            			    return false;}
                            if ($state.current.name !== 'profile') {
                                $state.go('profile', {pageNum: 1});
                            }
            			};

            $scope.goToChangePassword = function() {
                    delete $localStorage.filter;
                    if((($state.current.name)===("MA Cumulative Summary"))||(($state.current.name)===("MA Subscriber"))||
                                   (($state.current.name)===("MA Performance"))||(($state.current.name)===("Kilkari Cumulative Summary"))||
                                   (($state.current.name)===("Kilkari Beneficiary Completion"))||(($state.current.name)===("Kilkari Usage"))||
                                   (($state.current.name)===("Kilkari Call"))||(($state.current.name)===("Kilkari Message Matrix"))||
                                   (($state.current.name)===("Kilkari Listening Matrix"))||(($state.current.name)===("Kilkari Thematic Content"))||
                                   (($state.current.name)===("Kilkari Repeat Listener"))||(($state.current.name)===("Kilkari Subscriber"))||
                                   (($state.current.name)===("Kilkari Message Listenership"))||(($state.current.name)===("Kilkari Aggregate Beneficiary"))){
                    return false;}
                    if ($state.current.name !== 'changePassword') {
                        $state.go('changePassword', {pageNum: 1});
                    }
                       };

			$scope.activeTab = function(tabName){
				return ($state.current.name.indexOf(tabName) > -1);
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