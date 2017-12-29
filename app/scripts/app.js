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
	.module('nmsReports', ['ui.bootstrap', 'ui.validate', 'ngMessages', 'ui.router', 'ui.grid', 'ngMaterial', 'BotDetectCaptcha','ng.deviceDetector','ui.grid.exporter', 'ngStorage'])
	.run( ['$rootScope', '$state', '$stateParams',
		function ($rootScope, $state, $stateParams) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
		}
	]).config(function ($stateProvider, $urlRouterProvider, $httpProvider, captchaSettingsProvider) {
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

			.state('reports', {
				url: '/reports',
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

            .state('changePassword', {
                url: '/changePassword',
                templateUrl: 'views/changePassword.html'
            });
		$urlRouterProvider
			.otherwise('/login');

        captchaSettingsProvider.setSettings({
            captchaEndpoint: backend_root + 'botdetectcaptcha'
        });

			
		$httpProvider.defaults.headers.common = {};
		$httpProvider.defaults.headers.post = {};
		$httpProvider.defaults.headers.put = {};
		$httpProvider.defaults.headers.patch = {};
	});