(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserManagementController", ['$scope', function($scope){
			$scope.currentPage = "user-table";

			$scope.userTable = function(){
				$scope.currentPage = "user-table";
				delete $scope.currentPageTitle;
			}

			$scope.bulkUpload = function(){
				$scope.currentPage = "bulk-user";
				$scope.currentPageTitle = 'Import bulk users';
			}

			$scope.newUser = function(){
				$scope.currentPage = "create-user";
				$scope.currentPageTitle = 'Create new user';
			}

			$scope.editUser = function(id){
				console.log(id)
				$scope.currentPage = "edit-user";
				$scope.currentPageTitle = 'Edit user';
				$scope.idToEdit = id;
			}

			$scope.display = function(page){
				return $scope.currentPage == page;
			}

			// $scope.editUser(1);

		}])
})()