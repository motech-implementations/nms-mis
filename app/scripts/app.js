
var nmsReportsApp = angular.module('nmsReports', ['vcRecaptcha','ui.bootstrap', 'ui.validate', 'ngMessages', 'ui.router', 'ui.grid', 'ngMaterial', 'ngFileUpload', 'ng.deviceDetector', 'ui.grid.exporter', 'ngStorage', 'ngAnimate', '$idle', 'mdo-angular-cryptography'])
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
                            else {
                                $http.post(backend_root + 'nms/user/currentUser')
                                    .then(function(result){
                                        if(result.data.default){
                                            $state.go('changePassword', {});
                                        }
                                    });
                            }
                        });
                }
            };
        }
    ])

    .factory('httpRequestInterceptor',
        function () {
            return {
                request: function (config) {
                    config.headers['SameSite'] = 'Lax';
                    return config;
                }
            };
        }
    )

    .factory('authorizationRole', ['$http', '$state',
        function($http, $state) {
            return {
                authorize: function() {
                    return $http.post(backend_root + 'nms/user/isLoggedIn')
                        .then(function(result){
                            console.log("You are here")
                            if(!result.data){
                                $state.go('login', {});
                            }
                            else {
                                return $http.post(backend_root + 'nms/user/currentUser')
                                    .then(function(result1){
                                        if(result1.data.roleName == 'USER'){
                                            $state.go('reports', {});
                                        }
                                    });

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
            templateUrl: 'htpagesmis/userManagement.html',
            resolve : {
                user : function (authorizationRole,authorization){
                    if(authorization.authorize()){
                        return authorizationRole.authorize();
                    }
                }
            }
        }).state('userManagement.bulkUpload', {
            url: '/bulkUpload',
            templateUrl: 'htpagesmis/bulkUser.html',
            resolve : {
                user : function (authorizationRole) {
                    return authorizationRole.authorize();
                }
            }
        }).state('userManagement.createUser', {
            url: '/create',
            templateUrl: 'htpagesmis/createUser.html',
            resolve : {
                user : function ( authorizationRole) {
                    return authorizationRole.authorize();
                }
            }
        }).state('userManagement.userTable', {
            url: '/:pageNum',
            templateUrl: 'htpagesmis/userTable.html',
            reloadOnSearch: false,
            resolve : {
                user : function ( authorizationRole) {
                    return authorizationRole.authorize();
                }
            },
            controller: function($scope, $stateParams) {
                $scope.currentPageNo = $stateParams.pageNum;
            }
        }).state('userManagement.editUser', {
            url: '/:pageNum/edit/:id',
            templateUrl: 'htpagesmis/editUser.html',
            resolve : {
                user : function ( authorizationRole) {
                    return authorizationRole.authorize();
                }
            }
        }).state('login', {
            url: '/login',
            templateUrl: 'htpagesmis/login.html'
        }).state('logout', {
            url: '/logout',
            templateUrl: 'htpagesmis/login.html'
        }).state('reports', {
            url: '/reports',
            templateUrl: 'htpagesmis/reports.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('profile', {
            url: '/profile',
            templateUrl: 'htpagesmis/profile.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('forgotPassword', {
            url: '/forgotPassword',
            templateUrl: 'htpagesmis/forgotPassword.html'
        }).state('feedbackForm', {
            url: '/feedbackForm',
            templateUrl: 'htpagesmis/feedbackForm.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('feedbackResponse', {
            url: '/feedbackResponse',
            templateUrl: 'htpagesmis/feedbackResponse.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('contactUs', {
            url: '/contactUs',
            templateUrl: 'htpagesmis/contactUs.html'

        }).state('contactUsResponse', {
            url: '/contactUsResponse',
            templateUrl: 'htpagesmis/contactUsResponse.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('sitemap', {
            url: '/sitemap',
            templateUrl: 'htpagesmis/sitemap.html'

        }).state('AboutKilkari', {
            url: '/kilkari',
            templateUrl: 'htpagesmis/aboutKilkari.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('AboutMA', {
            url: '/aboutMA',
            templateUrl: 'htpagesmis/aboutMA.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('PrivacyPolicy', {
            url: '/privacyPolicy',
            templateUrl: 'htpagesmis/privacyPolicy.html'

        }).state('CopyrightPolicy', {
            url: '/copyrightPolicy',
            templateUrl: 'htpagesmis/copyrightPolicy.html'

        }).state('TandC', {
            url: '/termsAndConditions',
            templateUrl: 'htpagesmis/tAndC.html'

        }).state('HLPolicy', {
            url: '/hyperLinkingPolicy',
            templateUrl: 'htpagesmis/hyperLinkingPolicy.html'

        }).state('Disclaimer', {
            url: '/disclaimer',
            templateUrl: 'htpagesmis/disclaimer.html'

        }).state('Help', {
            url: '/help',
            templateUrl: 'htpagesmis/helpPage.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('changePassword', {
            url: '/changePassword',
            templateUrl: 'htpagesmis/changePassword.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual', {
            url: '/userManual',
            templateUrl: 'htpagesmis/userManual.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.kilkari', {
            url: '/kilkari',
            templateUrl: 'htpagesmis/userManual_kilkari.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.kilkariAggregate', {
            url: '/kilkariAggregate',
            templateUrl: 'htpagesmis/userManual_kilkariAgg.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.websiteInformation', {
            url: '/WebsiteInformation',
            templateUrl: 'htpagesmis/userManual_websiteInformation.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.mobileAcademy', {
            url: '/mobileAcademy',
            templateUrl: 'htpagesmis/userManual_mobileAcademy.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.mobileAcademyAggregate', {
            url: '/mobileAcademyAggregate',
            templateUrl: 'htpagesmis/userManual_mobileAcademyAgg.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.userManual_Management', {
            url: '/userManual_Management',
            templateUrl: 'htpagesmis/userManual_Management.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.userManual_Profile', {
            url: '/userManual_Profile',
            templateUrl: 'htpagesmis/userManual_Profile.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq', {
            url: '/faq',
            templateUrl: 'htpagesmis/faq.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqGeneralInfo', {
            url: '/general-info',
            templateUrl: 'htpagesmis/faqGeneralInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqLoginInfo', {
            url: '/login-info',
            templateUrl: 'htpagesmis/faqLoginInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqReportsInfo', {
            url: '/reports-info',
            templateUrl: 'htpagesmis/faqReportsInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqLineListingInfo', {
            url: '/line-listing-info',
            templateUrl: 'htpagesmis/faqLineListingInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqAggregateInfo', {
            url: '/aggregate-info',
            templateUrl: 'htpagesmis/faqAggregateInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('Downloads', {
            url: '/Downloads',
            templateUrl: 'htpagesmis/downloads.html'
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

    .config(function ($httpProvider) {
        $httpProvider.interceptors.push('httpRequestInterceptor');
    })

    .config(['$cryptoProvider', function($cryptoProvider) {
        $cryptoProvider.setCryptographyKey('ABCD123');
    }]);