(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("EditUserController", ['$scope', 'UserFormFactory', '$http', '$stateParams', function($scope, UserFormFactory, $http, $stateParams){

			UserFormFactory.downloadCurrentUser()
			.then(function(result){
				console.log(result.data);
				UserFormFactory.setCurrentUser(result.data);
			})


			UserFormFactory.getUser($stateParams.id)
			.then(function(result){
				console.log(result.data);
				console.log(JSON.stringify(result.data));
				$scope.editUser = result.data;

				$scope.states = [$scope.editUser.stateId];
				$scope.districts = [$scope.editUser.districtId];
				$scope.blocks = [$scope.editUser.blockId];
			});
			
			// UserFormFactory.getAccessLevels()
			// .then(function(result){
			// 	$scope.accessLevelList = result.data;

			
			// })

			$scope.accessLevelList = ["NATIONAL", "STATE", "DISTRICT", "BLOCK"];

			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.editUser.accessLevel
				var off = 4 - list.length;
				return list.indexOf(item) + off < level;
			}
			
			$scope.editUser = {};

			UserFormFactory.getRoles()
			.then(function(result){
				$scope.accessTypeList = result.data;
			});

			$scope.getStates = function(){
				return UserFormFactory.getStates()
				.then(function(result){
					if(UserFormFactory.getCurrentUser.stateId != null){
						$scope.states = [];
						$scope.states.push(UserFormFactory.getCurrentUser.stateId);
					}else{
						$scope.states = result.data;
					}
					$scope.districts = [];
					$scope.blocks = [];
				});
			}
			
			$scope.getDistricts = function(stateId){
				return UserFormFactory.getDistricts(stateId)
				.then(function(result){
					$scope.districts = result.data;
					$scope.blocks = [];
				});
			}

			$scope.getBlocks =function(districtId){
				return UserFormFactory.getBlocks(districtId)
				.then(function(result){
					$scope.blocks = result.data;
				});
			}

			$scope.onAccessLevelChanged = function(){
				$scope.getStates()

				$scope.editUserForm.state.$setPristine(false);
				$scope.editUserForm.district.$setPristine(false);
				$scope.editUserForm.block.$setPristine(false);
			}

			$scope.$watch('editUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue && oldValue != null){
					$scope.onAccessLevelChanged();
				}
			});

			$scope.$watch('editUser.stateId', function(value){
				if(value != null){
					$scope.editUser.districtId = null;
					$scope.editUser.blockId = null;

					$scope.getDistricts(value.stateId)
				}
			});
			$scope.$watch('editUser.districtId', function(value){
				if(value != null){
					$scope.editUser.blockId = null;

					$scope.getBlocks(value.districtId);
				}
			});
			$scope.$watch('editUser.blockId', function(value){
				if(value != null){

				}
			});

			$scope.editUserSubmit = function() {
				if ($scope.editUserForm.$valid) {

					delete $scope.editUser.$$hashKey;

					console.log($scope.editUser);
					$http({
						method  : 'POST',
						url     : backend_root + 'nms/user/updateUser',
						data    : $scope.editUser, //forms user object
						headers : {'Content-Type': 'application/json'} 
					})
				}
				else{
					console.log("error")
					angular.forEach($scope.editUserForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}
			};
		}]);

})()