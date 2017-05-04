(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("EditUserController", ['$scope', 'UserFormFactory', '$http', function($scope, UserFormFactory, $http){

			// console.log($scope.$parent.idToEdit);

			$scope.$watch('$parent.idToEdit', function(value){
				console.log(value);
			})
			$scope.accessLevelList = ["National", "State", "District", "Block"];

			$scope.editUser = {};
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



			UserFormFactory.getUser($scope.$parent.idToEdit)
			.then(function(result){
				$scope.editUser = result.data;

				// $scope.place.state = UserFormFactory.getLocationByName($scope.editUser.state);
				// $scope.place.district = UserFormFactory.getLocationByName($scope.editUser.district);
				// $scope.place.block = UserFormFactory.getLocationByName($scope.editUser.block);



				console.log($scope.editUser);

				$scope.states = UserFormFactory.getChildLocations(1);
				$scope.place.state = UserFormFactory.getLocationByName($scope.editUser.state);

				$scope.districts = UserFormFactory.getChildLocations($scope.place.state.locationId);
				$scope.place.district = UserFormFactory.getLocationByName($scope.editUser.district);

				$scope.states = UserFormFactory.getChildLocations($scope.place.district.locationId);
				$scope.place.block = UserFormFactory.getLocationByName($scope.editUser.block);
			})

			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.editUser.accessLevel;
				return list.indexOf(item) < level;
			}

			$scope.onAccessLevelChanged = function(){
				$scope.place = {};

				$scope.editUser.state = null;
				$scope.editUser.district = null;
				$scope.editUser.block = null;

				$scope.editUserForm.state.$setPristine(false);
				$scope.editUserForm.district.$setPristine(false);
				$scope.editUserForm.block.$setPristine(false);
			}

			$scope.$watch('editUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue){
					$scope.onAccessLevelChanged();
				}
			});

			$scope.$watch('place.state', function(value){
				if(value != null){
					$scope.editUser.state = value.location;
					$scope.editUser.district = null;
					$scope.editUser.block = null;

					$scope.districts = UserFormFactory.getChildLocations(value.locationId);
					$scope.blocks = [];

					$scope.place.district = null;
					$scope.place.block = null;
				}
			});
			$scope.$watch('place.district', function(value){
				if(value != null){
					$scope.editUser.district = value.location;
					$scope.editUser.block = null;

					$scope.blocks = UserFormFactory.getChildLocations(value.locationId);

					$scope.place.block = null;
				}
			});
			$scope.$watch('place.block', function(value){
				if(value != null){
					$scope.editUser.block = value.location;
				}
			});


			$scope.editUserSubmit = function() {
				if ($scope.editUserForm.$valid) {
					console.log($scope.user);
					// $http({
					// 	method  : 'POST',
					// 	url     : backend_root + 'nms/user/update-user',
					// 	data    : $scope.editUser, //forms user object
					// 	headers : {'Content-Type': 'application/x-www-form-urlencoded'} 
					// })

					
					// .then(function(data) {
					// 	if (data.errors) {
					// 		// Showing errors.
					// 		$scope.errorName = data.errors.name;
					// 		$scope.errorUserName = data.errors.username;
					// 		$scope.errorEmail = data.errors.email;
					// 	} else {
					// 		$scope.message = data.message;
					// 	}
					// });
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