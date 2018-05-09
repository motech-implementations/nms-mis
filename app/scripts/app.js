'use strict';

/**
 * @ngdoc overview
 * @name NMS Reporting
 * @description
 * # NMS Reporting UI app
 *
 * Main module of the application.
 */
var nmsReportsApp = angular
	.module('nmsReports', ['ui.bootstrap', 'ui.validate', 'ngMessages', 'ui.router', 'ui.grid', 'ngMaterial','ng.deviceDetector','ui.grid.exporter', 'ngStorage','ngAnimate','$idle'])
	.run( ['$rootScope', '$state', '$stateParams','$idle','$http','$window',
		function ($rootScope, $state, $stateParams,$idle,$http,$window) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
            $idle.watch();

            $rootScope.online = navigator.onLine;
                  $window.addEventListener("offline", function() {
                    $rootScope.$apply(function() {
                      $rootScope.online = false;
                    });
                  }, false);

                  $window.addEventListener("online", function() {
                    $rootScope.$apply(function() {
                      $rootScope.online = true;
                    });
                  }, false);

		}
	]).config(function ($stateProvider, $urlRouterProvider, $httpProvider ,$idleProvider) {
		$stateProvider
		
			.state('userManagement', {
				url: '/userManagement',
				abstract: true,
				templateUrl: 'views/userManagement.html'
			})
			.state('userManagement.bulkUpload', {
				url: '/bulkUpload',
				templateUrl: 'views/bulkUser.html'
			})
			.state('userManagement.createUser', {
				url: '/create',
				templateUrl: 'views/createUser.html'
			})
            .state('userManagement.userTable', {
                url: '/:pageNum',
                templateUrl: 'views/userTable.html',
                reloadOnSearch: false,
                controller: function($scope, $stateParams) {
                    $scope.currentPageNo = $stateParams.pageNum;
                }
            })
            .state('userManagement.editUser', {
                url: '/:pageNum/edit/:id',
                templateUrl: 'views/editUser.html'
            })

			.state('login', {
				url: '/login',
				templateUrl: 'views/login.html'
			})

            .state('logout', {
                url: '/logout',
                templateUrl: 'views/login.html'
            })

			.state('reports', {
				url: '/reports',
				templateUrl: 'views/reports.html'
			})

			.state('MA Cumulative Summary', {
            	url: '/reports/ma/cumulative-summary-report',
            	templateUrl: 'views/reports.html'
            })

			.state('MA Subscriber', {
            	url: '/reports/ma/subscriber-report',
            	templateUrl: 'views/reports.html'
            })

			.state('MA Performance', {
            	url: '/reports/ma/performance-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Cumulative Summary', {
            	url: '/reports/kilkari/cumulative-summary-report',
            	templateUrl: 'views/reports.html'
            })


			.state('Kilkari Beneficiary Completion', {
            	url: '/reports/kilkari/beneficiary-completion-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Usage', {
            	url: '/reports/kilkari/usage-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Call', {
            	url: '/reports/kilkari/call-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Message Matrix', {
            	url: '/reports/kilkari/message-matrix-report',
            	templateUrl: 'views/reports.html'
            })


			.state('Kilkari Listening Matrix', {
            	url: '/reports/kilkari/listening-matrix-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Thematic Content', {
            	url: '/reports/kilkari/thematic-content-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Repeat Listener', {
            	url: '/reports/kilkari/repeat-listener-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Subscriber', {
            	url: '/reports/kilkari/subscriber-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Message Listenership', {
            	url: '/reports/kilkari/message-listenership-report',
            	templateUrl: 'views/reports.html'
            })

			.state('Kilkari Aggregate Beneficiaries', {
            	url: '/reports/kilkari/aggregate-beneficiary-report',
            	templateUrl: 'views/reports.html'
            })

			.state('profile', {
				url: '/profile',
				templateUrl: 'views/profile.html'
			})

			.state('forgotPassword', {
            	url: '/forgotPassword',
            	templateUrl: 'views/forgotPassword.html'
            })

            .state('feedbackForm', {
            	url: '/feedbackForm',
            	templateUrl: 'views/feedbackForm.html'
            })

            .state('feedbackResponse',{
                url: '/feedbackResponse',
                templateUrl: 'views/feedbackResponse.html'
            })

            .state('contactUs', {
                 url: '/contactUs',
                 templateUrl: 'views/contactUs.html'
            })

            .state('contactUsResponse',{
                url: '/contactUsResponse',
                templateUrl: 'views/contactUsResponse.html'
            })

            .state('sitemap', {
                 url: '/sitemap',
                 templateUrl: 'views/sitemap.html'
            })

            .state('AboutUs', {
            	url: '/aboutUs',
                templateUrl: 'views/aboutUs.html'
            })
            .state('AboutKilkari', {
                url: '/kilkari',
                templateUrl: 'views/aboutKilkari.html'
            })
            .state('AboutMA', {
                url: '/aboutMA',
                templateUrl: 'views/aboutMA.html'
            })
            .state('PrivacyPolicy', {
                url: '/privacyPolicy',
                templateUrl: 'views/privacyPolicy.html'
            })
            .state('CopyrightPolicy', {
                url: '/copyrightPolicy',
                templateUrl: 'views/copyrightPolicy.html'
            })
            .state('TandC', {
                url: '/termsAndConditions',
                templateUrl: 'views/tAndC.html'
            })
            .state('HLPolicy', {
                url: '/hyperLinkingPolicy',
                templateUrl: 'views/hyperLinkingPolicy.html'
            })
            .state('Disclaimer', {
                url: '/disclaimer',
                templateUrl: 'views/disclaimer.html'
            })
            .state('Help', {
                url: '/help',
                templateUrl: 'views/helpPage.html'
            })

            .state('changePassword', {
                url: '/changePassword',
                templateUrl: 'views/changePassword.html'
            })
            .state('userManual', {
                url: '/userManual',
                templateUrl: 'views/userManual.html'
            })

            .state('userManual.kilkari', {
                url: '/kilkari',
                templateUrl: 'views/userManual_kilkari.html'
            })
            .state('userManual.websiteInformation', {
                url: '/WebsiteInformation',
                templateUrl: 'views/userManual_websiteInformation.html'
            })
            .state('userManual.mobileAcademy', {
                url: '/mobileAcademy',
                templateUrl: 'views/userManual_mobileAcademy.html'
            })
            .state('userManual.userManual_Management', {
                url: '/userManual_Management',
                templateUrl: 'views/userManual_Management.html'
            })
            .state('userManual.userManual_Profile', {
                url: '/userManual_Profile',
                templateUrl: 'views/userManual_Profile.html'
            })

            .state('faq', {
                url: '/faq',
                templateUrl: 'views/faq.html'
            })
            .state('faq.faqKilkari', {
                url: '/Kilkari',
                templateUrl: 'views/faqKilkari.html'
            })
            .state('faq.faqMobileAcademy', {
                url: '/MobileAcademy',
                templateUrl: 'views/faqMobileAcademy.html'
            })
            .state('faq.faqWebsiteInformation', {
                url: '/WebsiteInformation',
                templateUrl: 'views/faqWebsiteInformation.html'
            })

            .state('faq.faqProfile', {
                url: '/Profile',
                templateUrl: 'views/faqProfile.html'
            })
             .state('faq.faqUserManagement', {
                 url: '/UserManagement',
                 templateUrl: 'views/faqUserManagement.html'
             })
            .state('Downloads', {
                url: '/Downloads',
                templateUrl: 'views/downloads.html'
            });

		$urlRouterProvider
			.otherwise('/login');

//        captchaSettingsProvider.setSettings({
//            captchaEndpoint: backend_root + 'botdetectcaptcha/'
//        });

		$httpProvider.defaults.headers.common = {};

		$httpProvider.defaults.headers.post = {};
		$httpProvider.defaults.headers.put = {};
		$httpProvider.defaults.headers.patch = {};
		$idleProvider.interrupt('keydown mousedown touchstart touchmove');
		$idleProvider.setIdleTime(1800);

		$httpProvider.interceptors.push(['$q',function($q) {
                        return {
            request: function(config) {
            // console.log(config);
             if(config.url != backend_root+'nms/mail/sendEmailForContactUs' || config.url!= backend_root + 'nms/mail/sendFeedback'){
             $httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache';
                     $httpProvider.defaults.cache = false;

                     if (!$httpProvider.defaults.headers.get) {
                     $httpProvider.defaults.headers.get = {};
                     }
                     $httpProvider.defaults.headers.get['If-Modified-Since'] = '0';

             }
            return config;
        }

                        };

                      }
                    ]);
	});