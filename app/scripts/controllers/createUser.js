(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("CreateUserController", ['$scope', function($scope){
			
			$scope.accessTypeList = ["User", "Administrator"];
			$scope.accessLevelList = ["National", "State", "District", "Block"];

			$scope.states=["asdf", "sadfs", "sadf", "asdfsadf"];
			$scope.districts=["asdf", "sadfs", "sadf", "asdfsadf"];

			$scope.newUser = {};

			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.newUser.accessLevel
				return list.indexOf(item) < level;
			}

			$scope.clearForm = function(){
				$scope.newUser = {};
				$scope.confirmPassword = null;
				$scope.createUserForm.$setPristine();

				$scope.$parent.currentPage = "user-table";
				delete $scope.$parent.currentPageTitle;
			}

			$scope.createUserSubmit = function() {
				if ($scope.createUserForm.$valid) {
					console.log($scope.newUser)
				}
				else{
					angular.forEach($scope.createUserForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}
			};

			$scope.onAccessLevelChanged = function(){
				$scope.newUser.place = {};
				$scope.createUserForm.state.$setPristine(false);
				$scope.createUserForm.district.$setPristine(false);
				$scope.createUserForm.block.$setPristine(false);
			}

			$scope.$watch('newUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue){
					$scope.onAccessLevelChanged();
				}
			})

		}])
})()