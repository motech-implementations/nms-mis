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
	.module('nmsReports', ['ui.bootstrap', 'ui.validate', 'ngMessages', 'ui.router', 'ui.grid', 'ngMaterial'])
	.run( ['$rootScope', '$state', '$stateParams',
		function ($rootScope, $state, $stateParams) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
		}
	])
	.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
		$stateProvider
		
			.state('userManagement', {
				url: '/userManagement',
				abstract: true,
				templateUrl: 'views/userManagement.html',
			})
			.state('userManagement.userTable', {
				url: '',
				templateUrl: 'views/userTable.html'
			})
			.state('userManagement.bulkUpload', {
				url: '/bulkUpload',
				templateUrl: 'views/bulkUser.html'
			})
			.state('userManagement.createUser', {
				url: '/create',
				templateUrl: 'views/createUser.html'
			})
			.state('userManagement.editUser', {
				url: '/edit/{id}',
				templateUrl: 'views/editUser.html'
			})

			.state('login', {
				url: '/login',
				templateUrl: 'views/login.html',
			})

			.state('reports', {
				url: '/reports',
				templateUrl: 'views/reports.html',
			})
			
			.state('profile', {
				url: '/profile',
				templateUrl: 'views/profile.html',
			})

			.state('forgotPassword', {
            	url: '/forgotPassword',
            	templateUrl: 'views/forgotPassword.html',
            })

            .state('changePassword', {
                url: '/changePassword',
                templateUrl: 'views/changePassword.html',
            })
		$urlRouterProvider
			.otherwise('/login')


			
		$httpProvider.defaults.headers.common = {};
		$httpProvider.defaults.headers.post = {};
		$httpProvider.defaults.headers.put = {};
		$httpProvider.defaults.headers.patch = {};
	});