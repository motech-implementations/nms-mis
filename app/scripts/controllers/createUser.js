(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("CreateUserController", ['$scope', 'UserFormFactory', '$http', function($scope, UserFormFactory, $http){
			
			UserFormFactory.downloadRoles()
			.then(function(result){
				UserFormFactory.setRoles(result.data);
				$scope.accessTypeList = UserFormFactory.getRoles();
			});

			UserFormFactory.downloadLocations()
			.then(function(result){
				UserFormFactory.setLocations(result.data);
				$scope.states = UserFormFactory.getChildLocations(1);
			})

			$scope.accessLevelList = ["National", "State", "District", "Block"];

			$scope.newUser = {};

			

			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.accessLevel
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
					$scope.newUser.createdByUser = null;
					$scope.newUser.accountStatus = 'active';
					console.log(JSON.stringify($scope.newUser));
					$http({
						method  : 'POST',
						url     : 'http://localhost:8080/NMSReportingSuite/nms/user/create-user',
						data    : JSON.stringify($scope.newUser), //forms user object
						headers : {'Content-Type': 'application/json'} 
					});
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
				$scope.place = {};
				$scope.newUser.locationId = 
				$scope.createUserForm.state.$setPristine(false);
				$scope.createUserForm.district.$setPristine(false);
				$scope.createUserForm.block.$setPristine(false);
			}

			$scope.$watch('accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue){
					$scope.onAccessLevelChanged();
				}
			});
			$scope.$watch('newUser.phoneNumber', function(value){
				$scope.newUser.password = value;
			});

			$scope.$watch('place.state', function(value){
				if(value != null){
					$scope.districts = UserFormFactory.getChildLocations(value.locationId);
					$scope.place.district = null;
					$scope.place.block = null;
					$scope.newUser.locationId = value;
				}
			});

			$scope.$watch('place.district', function(value){
				if(value != null){
					$scope.blocks = UserFormFactory.getChildLocations(value.locationId);
					$scope.place.block = null;
					$scope.newUser.locationId = value;
				}
			});

			$scope.$watch('place.state', function(value){
				if(value != null){
					$scope.newUser.locationId = value;
				}
			});

		}])
})()