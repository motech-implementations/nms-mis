(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("EditUserController", ['$scope', 'UserFormFactory', '$http', function($scope, UserFormFactory, $http){

			UserFormFactory.getUser( UserFormFactory.getUserToEdit().id )
			.then(function(result){
				console.log(result.data);
				$scope.user = result.data;
				$scope.editUser = UserFormFactory.getUserToEdit()
				$scope.editUser.accessType = $scope.user.roleId;

				
			});
			

			$scope.accessLevelList = ["National", "State", "District", "Block"];

			$scope.editUser = {};

			UserFormFactory.downloadRoles()
			.then(function(result){
				UserFormFactory.setRoles(result.data);
				$scope.accessTypeList = UserFormFactory.getRoles();
			});

			$scope.getStates = function(){
				return UserFormFactory.getStates()
				.then(function(result){
					$scope.states = result.data;
					$scope.districts = [];
					$scope.blocks = [];

					$scope.editUser.state = $scope.user.stateId;
				});
			}
			
			$scope.getDistricts = function(stateId){
				return UserFormFactory.getDistricts(stateId.stateId)
				.then(function(result){
					$scope.districts = result.data;
					$scope.blocks = [];

					$scope.editUser.district = $scope.user.districtId;
				});
			}

			$scope.getBlocks =function(districtId){
				return UserFormFactory.getBlocks(districtId.districtId)
				.then(function(result){
					$scope.blocks = result.data;

					$scope.editUser.block = $scope.user.blockId;
				});
			}

			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.editUser.accessLevel;
				return list.indexOf(item) < level;
			}

			$scope.onAccessLevelChanged = function(){
				$scope.getStates()

				$scope.editUserForm.state.$setPristine(false);
				$scope.editUserForm.district.$setPristine(false);
				$scope.editUserForm.block.$setPristine(false);
			}

			$scope.$watch('editUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue){
					$scope.onAccessLevelChanged();
				}
			});

			$scope.$watch('editUser.state', function(value){
				if(value != null){
					$scope.editUser.district = null;
					$scope.editUser.block = null;

					$scope.getDistricts(value)
				}
			});
			$scope.$watch('editUser.district', function(value){
				if(value != null){
					$scope.editUser.block = null;

					$scope.getBlocks(value);
				}
			});
			$scope.$watch('editUser.block', function(value){
				if(value != null){

				}
			});

			$scope.editUserSubmit = function() {
				if ($scope.editUserForm.$valid) {

					// $scope.editUser.accessType = $scope.editUser.accessType.roleId + '';
					// if($scope.editUser.state != null){
					// 	$scope.editUser.state = $scope.editUser.state.stateId + ''
					// }else{
					// 	delete $scope.editUser.state;
					// }
					// if($scope.editUser.district != null){
					// 	$scope.editUser.district = $scope.editUser.district.districtId + ''
					// }else{
					// 	delete $scope.editUser.district;
					// }
					// if($scope.editUser.block != null){
					// 	$scope.editUser.block = $scope.editUser.block.blockId + ''
					// }else{
					// 	delete $scope.editUser.block;
					// }
					delete $scope.editUser.$$hashKey;

					console.log($scope.editUser);
					// $http({
					// 	method  : 'POST',
					// 	url     : backend_root + 'nms/user/update-user-2',
					// 	data    : $scope.editUser, //forms user object
					// 	headers : {'Content-Type': 'application/json'} 
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