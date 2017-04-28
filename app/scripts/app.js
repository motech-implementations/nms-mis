'use strict';

/**
 * @ngdoc overview
 * @name alacritiReports
 * @description
 * # alacrityReportsUiApp
 *
 * Main module of the application.
 */
var nmsReportsApp = angular
	.module('nmsReports', ['ui.bootstrap', 'ui.validate', 'ngMessages', 'ngRoute'])
	
	.config(['$routeProvider',function ($routeProvider) {
		$routeProvider
		// .when('/', {
		// 	templateUrl: 'login.html',
		// })
		.when('/login', {
			templateUrl: 'login.html',
		})
		.when('/index',{
			templateUrl: 'main.html',
		})
/*		.otherwise({
			redirectTo: 'login.html',
		});*/
	}])
		// $logProvider.debugEnabled(false);
