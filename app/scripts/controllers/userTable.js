(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserTableController", ['$scope', 'UserTableFactory', function($scope, UserTableFactory){

			// $scope.v = [];
			// $scope.v = TestFactory.getUsers();

			$scope.numPerPageList = [10, 20];

			$scope.init = function(){
				$scope.headers = UserTableFactory.headers();
				$scope.currentPageNo = 1;
				$scope.numPerPage = $scope.numPerPageList[0];
			}

			UserTableFactory.getUsers()
			.then(function(result){
				UserTableFactory.setUsers(result.data)
				$scope.init();
			});

			$scope.resetPage = function(){
				$scope.currentPageNo = 1;
			}

			$scope.getAllUsers = function(){
				return UserTableFactory.getAllUsers();
			}

			$scope.$watch('numPerPage', $scope.resetPage);

			$scope.search = function (row) {
				return (angular.lowercase(row.name).includes(angular.lowercase($scope.filterText) || '') || 
						angular.lowercase(row.username).includes(angular.lowercase($scope.filterText) || '') || 
						angular.lowercase(row.phone).includes(angular.lowercase($scope.filterText) || '') || 
						angular.lowercase(row.email).includes(angular.lowercase($scope.filterText) || '') || 
						angular.lowercase(row.accessLevel).includes(angular.lowercase($scope.filterText) || '') ||
						angular.lowercase(row.state).includes(angular.lowercase($scope.filterText) || '') ||
						angular.lowercase(row.district).includes(angular.lowercase($scope.filterText) || '') ||
						angular.lowercase(row.block).includes(angular.lowercase($scope.filterText) || ''));
			};

			

			$scope.editUser = function(user){
				console.log("edit");
				console.log(user);
				$scope.$parent.editUser(user.id);
			}

			$scope.deleteUser = function(user){
				console.log("delete");
				console.log(user);
			}
		}])
})()