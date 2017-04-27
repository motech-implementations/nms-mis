(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("NavBarController", ['$scope', 'NavBarFactory', function($scope, NavBarFactory){
			

			$scope.loadMenu =function(){
				// return $scope.menu;
				return NavBarFactory.getMenu();
			}

			$scope.loadPage = function(page){
				$scope.$parent.currentPage = page;
			}

			$scope.status = {
				isopen: false
			};

			$scope.toggled = function(open) {

			};

			// $scope.toggleDropdown = function($event) {
			// 	$event.preventDefault();
			// 	$event.stopPropagation();
			// 	$scope.status.isopen = !$scope.status.isopen;
			// };


		}]
	)}
)()