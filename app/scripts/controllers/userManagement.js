(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserManagementController", ['$scope', 'UserFormFactory', function($scope, UserFormFactory){
			$scope.currentPage = "user-table";

			// user management

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

			$scope.editUser = function(user){
				console.log(user)
				UserFormFactory.setUserToEdit(user);
				$scope.currentPage = "edit-user";
				$scope.currentPageTitle = 'Edit user';
			}

			$scope.display = function(page){
				return $scope.currentPage == page;
			}

			// edit user




		}])
})()