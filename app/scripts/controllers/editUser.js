(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("EditUserController", ['$scope', 'UserFormFactory', '$http', function($scope, UserFormFactory, $http){

			// console.log($scope.$parent.idToEdit);

			// $scope.$watch('$scope.$parent.idToEdit', function(value){
			// 	console.log(value);
			// })
			$scope.accessLevelList = ["National", "State", "District", "Block"];

			$scope.user = {};
			$scope.place = {};

			UserFormFactory.downloadRoles()
			.then(function(result){
				UserFormFactory.setRoles(result.data);
				$scope.accessTypeList = UserFormFactory.getRoles();
			});

			UserFormFactory.downloadLocations()
			.then(function(result){
				UserFormFactory.setLocations(result.data);
				
			})

			UserFormFactory.downloadUsers()
			.then(function(result){
				UserFormFactory.setUsers(result.data);
				$scope.user = UserFormFactory.getUser();
				$scope.getLocations($scope.user.locationId);
			})

			$scope.getLocations = function(loc){
				$scope.location = [];
				while(loc.referenceId != null){
					$scope.location.unshift(loc);
					loc = loc.referenceId;
				}
				$scope.accessLevel = $scope.accessLevelList[($scope.location).length];

				$scope.states = UserFormFactory.getChildLocations(1);
				$scope.place.state = $scope.location[0];
				$scope.districts = UserFormFactory.getChildLocations($scope.location[0].locationId);
				$scope.place.state = $scope.location[1];
				$scope.blocks = UserFormFactory.getChildLocations($scope.location[1].locationId);
				$scope.place.state = $scope.location[2];
				
				console.log($scope.location)
			}



			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.accessLevel;
				return list.indexOf(item) < level;
			}

			$scope.onAccessLevelChanged = function(){

				$scope.place = {};

				$scope.editUserForm.state.$setPristine(false);
				$scope.editUserForm.district.$setPristine(false);
				$scope.editUserForm.block.$setPristine(false);

				$scope.states = UserFormFactory.getChildLocations(1);
				$scope.districts = [];
				$scope.blocks = [];
			}

			$scope.$watch('accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue){
					$scope.onAccessLevelChanged();
				}
			});



			$scope.$watch('place.state', function(value){
				if(value != null){
					$scope.districts = UserFormFactory.getChildLocations(value.locationId);
					$scope.final = value;
					$scope.blocks = [];

					$scope.place.district = {};
				}
			});
			$scope.$watch('place.district', function(value){
				if(value != null){
					$scope.blocks = UserFormFactory.getChildLocations(value.locationId);
					$scope.final = value;

					$scope.place.block = {};
				}
			});
			$scope.$watch('place.block', function(value){
				if(value != null){
					$scope.final = value;
				}
			});
			$scope.$watch('final', function(value){
				console.log(value);
				$scope.locationId = value;
			});



			$scope.editUserSubmit = function() {
				if ($scope.editUserForm.$valid) {
					console.log($scope.user);
					$http({
						method  : 'POST',
						url     : backend_root + 'nms/user/create-user',
						data    : $scope.user, //forms user object
						headers : {'Content-Type': 'application/x-www-form-urlencoded'} 
					})
					.then(function(data) {
						if (data.errors) {
							// Showing errors.
							$scope.errorName = data.errors.name;
							$scope.errorUserName = data.errors.username;
							$scope.errorEmail = data.errors.email;
						} else {
							$scope.message = data.message;
						}
					});
				}
				else{
					angular.forEach($scope.editUserForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}
			};

			
			

		}]);

})()