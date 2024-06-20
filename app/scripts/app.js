
var nmsReportsApp = angular.module('nmsReports', ['vcRecaptcha','ui.bootstrap', 'ui.validate', 'ngMessages', 'ui.router', 'ui.grid', 'ngMaterial', 'ngFileUpload', 'ng.deviceDetector', 'ui.grid.exporter', 'ngStorage', 'ngAnimate', '$idle', 'mdo-angular-cryptography'])
    .run(['$rootScope', '$state', '$stateParams', '$idle', '$http', '$window', '$timeout', function($rootScope, $state, $stateParams, $idle, $http, $window, $timeout) {
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

    .factory('authorization', ['$http', '$state', '$timeout',
        function($http, $state, $timeout) {
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
                                    var lastPasswordChangeDate = new Date(result.data.lastPasswordChangeDate);
                                    var currentDate = new Date();
                                    var diffInMillies = Math.abs(currentDate - lastPasswordChangeDate);
                                    var diff = Math.ceil(diffInMillies / (1000 * 60 * 60 * 24));
                                    var alertShown = false;
                                    console.log("diff: " + diff);
                                    if (result.data.default) {
                                          $state.go('changePassword', {});
                                    }else if (diff > 30 && !alertShown) {
                                                 alertShown = true;
                                                 $timeout(function() {
                                                 if($state.current.url=="/changePassword"){
                                                 alert("You have not changed your password in the last 30 days. Please change it.");}
                                                 $state.go('changePassword', {});
                                                 });
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
            templateUrl: 'views/userManagement.html',
            resolve : {
                user : function (authorizationRole,authorization){
                    if(authorization.authorize()){
                        return authorizationRole.authorize();
                    }
                }
            }
        }).state('userManagement.bulkUpload', {
            url: '/bulkUpload',
            templateUrl: 'views/bulkUser.html',
            resolve : {
                user : function (authorizationRole) {
                    return authorizationRole.authorize();
                }
            }
        }).state('userManagement.createUser', {
            url: '/create',
            templateUrl: 'views/createUser.html',
            resolve : {
                user : function ( authorizationRole) {
                    return authorizationRole.authorize();
                }
            }
        }).state('userManagement.userTable', {
            url: '/:pageNum',
            templateUrl: 'views/userTable.html',
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
            templateUrl: 'views/editUser.html',
            resolve : {
                user : function ( authorizationRole) {
                    return authorizationRole.authorize();
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
            templateUrl: 'views/feedbackForm.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('feedbackResponse', {
            url: '/feedbackResponse',
            templateUrl: 'views/feedbackResponse.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('contactUs', {
            url: '/contactUs',
            templateUrl: 'views/contactUs.html'

        }).state('contactUsResponse', {
            url: '/contactUsResponse',
            templateUrl: 'views/contactUsResponse.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('sitemap', {
            url: '/sitemap',
            templateUrl: 'views/sitemap.html'

        }).state('AboutKilkari', {
            url: '/kilkari',
            templateUrl: 'views/aboutKilkari.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('AboutMA', {
            url: '/aboutMA',
            templateUrl: 'views/aboutMA.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
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
            templateUrl: 'views/helpPage.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('changePassword', {
            url: '/changePassword',
            templateUrl: 'views/changePassword.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual', {
            url: '/userManual',
            templateUrl: 'views/userManual.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.kilkari', {
            url: '/kilkari',
            templateUrl: 'views/userManual_kilkari.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.kilkariAggregate', {
            url: '/kilkariAggregate',
            templateUrl: 'views/userManual_kilkariAgg.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.websiteInformation', {
            url: '/WebsiteInformation',
            templateUrl: 'views/userManual_websiteInformation.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.mobileAcademy', {
            url: '/mobileAcademy',
            templateUrl: 'views/userManual_mobileAcademy.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.mobileAcademyAggregate', {
            url: '/mobileAcademyAggregate',
            templateUrl: 'views/userManual_mobileAcademyAgg.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.userManual_Management', {
            url: '/userManual_Management',
            templateUrl: 'views/userManual_Management.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('userManual.userManual_Profile', {
            url: '/userManual_Profile',
            templateUrl: 'views/userManual_Profile.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq', {
            url: '/faq',
            templateUrl: 'views/faq.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqGeneralInfo', {
            url: '/general-info',
            templateUrl: 'views/faqGeneralInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqLoginInfo', {
            url: '/login-info',
            templateUrl: 'views/faqLoginInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqReportsInfo', {
            url: '/reports-info',
            templateUrl: 'views/faqReportsInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqLineListingInfo', {
            url: '/line-listing-info',
            templateUrl: 'views/faqLineListingInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('faq.faqAggregateInfo', {
            url: '/aggregate-info',
            templateUrl: 'views/faqAggregateInfo.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            }
        }).state('Downloads', {
            url: '/Downloads',
            templateUrl: 'views/downloads.html'
        }).state('certificate', {
            url: '/certificate',
            templateUrl: 'views/certificate.html',
            resolve : {
                user : function ( authorization) {
                    return authorization.authorize();
                }
            },
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
