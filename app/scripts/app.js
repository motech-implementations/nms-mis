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
	.module('nmsReports', ['ui.bootstrap', 'ui.validate', 'ngMessages', 'ngRoute', 'ui.router'])
	
// 	.config(['$routeProvider',function ($routeProvider) {
// 		$routeProvider
// 		// .when('/', {
// 		// 	templateUrl: 'login.html',
// 		// })
// 		.when('/login', {
// 			templateUrl: 'login.html',
// 		})
// 		.when('/index',{
// 			templateUrl: 'main.html',
// 		})
// /*		.otherwise({
// 			redirectTo: 'login.html',
// 		});*/
// 	}])

	.config(function ($stateProvider, $urlRouterProvider) {

		$urlRouterProvider.otherwise('/user-management');

		$stateProvider
			// HOME STATES AND NESTED VIEWS ========================================
			.state('user-management', {
				url: '/user-management',
				templateUrl: '../views/userManagement.html'
			})

			// ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
			.state('reports', {
				url: '/reports',
				templateUrl: '../views/reports.html'     
			});

		  // $stateProvider.state('login', {
		  //   url:'/login',
		  //   templateUrl: 'login.html'
		  // })
		  // .state('index',{
		  //   url:'/index',
		  //   templateUrl: 'index.html'
		  // });
	  });