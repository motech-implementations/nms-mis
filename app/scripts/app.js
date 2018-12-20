var nmsReportsApp = angular.module('nmsReports', ['ui.bootstrap', 'ui.validate', 'ngMessages', 'ui.router', 'ui.grid', 'ngMaterial', 'ngFileUpload', 'ng.deviceDetector', 'ui.grid.exporter', 'ngStorage', 'ngAnimate', '$idle', 'mdo-angular-cryptography'])
    .run(['$rootScope', '$state', '$stateParams', '$idle', '$http', '$window', function($rootScope, $state, $stateParams, $idle, $http, $window) {
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
    }])

    .factory('authorization', ['$http', '$state',
        function($http, $state) {
            return {
                authorize: function() {
                    return $http.post(backend_root + 'nms/user/isLoggedIn')
                        .then(function(result){
                            console.log("You are here")
                            if(!result.data){
                                $state.go('login', {});
                            }
                        });
                }
            };
        }
    ])


    .config(['$stateProvider', '$urlRouterProvider', '$httpProvider', '$idleProvider', function($stateProvider, $urlRouterProvider, $httpProvider, $idleProvider) {
        $stateProvider.state('userManagement', {
            url: '/userManagement',
            abstract: true,
            templateUrl: 'views/userManagement.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManagement.bulkUpload', {
            url: '/bulkUpload',
            templateUrl: 'views/bulkUser.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManagement.createUser', {
            url: '/create',
            templateUrl: 'views/createUser.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManagement.userTable', {
            url: '/:pageNum',
            templateUrl: 'views/userTable.html',
            reloadOnSearch: false,
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            },
            controller: function($scope, $stateParams) {
                $scope.currentPageNo = $stateParams.pageNum;
            }
        }).state('userManagement.editUser', {
            url: '/:pageNum/edit/:id',
            templateUrl: 'views/editUser.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('login', {
            url: '/login',
            templateUrl: 'views/login.html'
        }).state('logout', {
            url: '/logout',
            templateUrl: 'views/login.html'
        }).state('reports', {
            url: '/reports',
            templateUrl: 'views/reports.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('profile', {
            url: '/profile',
            templateUrl: 'views/profile.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('forgotPassword', {
            url: '/forgotPassword',
            templateUrl: 'views/forgotPassword.html'
        }).state('feedbackForm', {
            url: '/feedbackForm',
            templateUrl: 'views/feedbackForm.html'
        }).state('feedbackResponse', {
            url: '/feedbackResponse',
            templateUrl: 'views/feedbackResponse.html'
        }).state('contactUs', {
            url: '/contactUs',
            templateUrl: 'views/contactUs.html'
        }).state('contactUsResponse', {
            url: '/contactUsResponse',
            templateUrl: 'views/contactUsResponse.html'
        }).state('sitemap', {
            url: '/sitemap',
            templateUrl: 'views/sitemap.html'
        }).state('AboutKilkari', {
            url: '/kilkari',
            templateUrl: 'views/aboutKilkari.html'
        }).state('AboutMA', {
            url: '/aboutMA',
            templateUrl: 'views/aboutMA.html'
        }).state('PrivacyPolicy', {
            url: '/privacyPolicy',
            templateUrl: 'views/privacyPolicy.html'
        }).state('CopyrightPolicy', {
            url: '/copyrightPolicy',
            templateUrl: 'views/copyrightPolicy.html'
        }).state('TandC', {
            url: '/termsAndConditions',
            templateUrl: 'views/tAndC.html'
        }).state('HLPolicy', {
            url: '/hyperLinkingPolicy',
            templateUrl: 'views/hyperLinkingPolicy.html'
        }).state('Disclaimer', {
            url: '/disclaimer',
            templateUrl: 'views/disclaimer.html'
        }).state('Help', {
            url: '/help',
            templateUrl: 'views/helpPage.html'
        }).state('changePassword', {
            url: '/changePassword',
            templateUrl: 'views/changePassword.html'
        }).state('userManual', {
            url: '/userManual',
            templateUrl: 'views/userManual.html'
        }).state('userManual.kilkari', {
            url: '/kilkari',
            templateUrl: 'views/userManual_kilkari.html'
        }).state('userManual.kilkariAggregate', {
            url: '/kilkariAggregate',
            templateUrl: 'views/userManual_kilkariAgg.html'
        }).state('userManual.websiteInformation', {
            url: '/WebsiteInformation',
            templateUrl: 'views/userManual_websiteInformation.html'
        }).state('userManual.mobileAcademy', {
            url: '/mobileAcademy',
            templateUrl: 'views/userManual_mobileAcademy.html'
        }).state('userManual.mobileAcademyAggregate', {
            url: '/mobileAcademyAggregate',
            templateUrl: 'views/userManual_mobileAcademyAgg.html'
        }).state('userManual.userManual_Management', {
            url: '/userManual_Management',
            templateUrl: 'views/userManual_Management.html'
        }).state('userManual.userManual_Profile', {
            url: '/userManual_Profile',
            templateUrl: 'views/userManual_Profile.html'
        }).state('faq', {
            url: '/faq',
            templateUrl: 'views/faq.html'
        }).state('faq.faqGeneralInfo', {
            url: '/general-info',
            templateUrl: 'views/faqGeneralInfo.html'
        }).state('faq.faqLoginInfo', {
            url: '/login-info',
            templateUrl: 'views/faqLoginInfo.html'
        }).state('faq.faqReportsInfo', {
            url: '/reports-info',
            templateUrl: 'views/faqReportsInfo.html'
        }).state('faq.faqLineListingInfo', {
            url: '/line-listing-info',
            templateUrl: 'views/faqLineListingInfo.html'
        }).state('faq.faqAggregateInfo', {
            url: '/aggregate-info',
            templateUrl: 'views/faqAggregateInfo.html'
        }).state('Downloads', {
            url: '/Downloads',
            templateUrl: 'views/downloads.html'
        });
        $urlRouterProvider.otherwise('/login');
        $httpProvider.defaults.headers.common = {};
        $httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache, no-store, must-revalidate';
        $httpProvider.defaults.headers.common['Expires'] = '0';
        $httpProvider.defaults.cache = false;
        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }
        $httpProvider.defaults.headers.get = {
            'Pragma': 'no-cache'
        };
        $httpProvider.defaults.headers.get = {
            'X-Frame-Options': 'DENY'
        };
        $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
        $httpProvider.defaults.headers.post = {};
        $httpProvider.defaults.headers.put = {};
        $httpProvider.defaults.headers.patch = {};
        $idleProvider.interrupt('keydown mousedown touchstart touchmove');
        $idleProvider.setIdleTime(1800);
    }])
    .config(['$cryptoProvider', function($cryptoProvider) {
        $cryptoProvider.setCryptographyKey('ABCD123');
    }]);