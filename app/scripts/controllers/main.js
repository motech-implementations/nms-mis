(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("MainController", ['$scope', 'UserFormFactory', function($scope, UserFormFactory){

			$scope.currentPage = "user-management"

			$scope.$watch('currentPage', function(value){
				console.log(value);
			})

			
		}]
	)}
)()