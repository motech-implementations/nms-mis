(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("EditUserController", ['$scope', 'UserFormFactory', '$http', '$stateParams', function($scope, UserFormFactory, $http, $stateParams){

			UserFormFactory.downloadCurrentUser()
			.then(function(result){
				UserFormFactory.setCurrentUser(result.data);
			})

			$scope.editUser = {};
			$scope.place = {};

			UserFormFactory.getUser($stateParams.id)
			.then(function(result){
				$scope.editUser = result.data;

				$scope.place.state = $scope.editUser.stateId
				$scope.place.district = $scope.editUser.districtId
				$scope.place.block = $scope.editUser.blockId
			});
			

			$scope.accessLevelList = ["NATIONAL", "STATE", "DISTRICT", "BLOCK"];

			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.editUser.accessLevel
				var off = 4 - list.length;
				return list.indexOf(item) + off < level;
			}
			
			UserFormFactory.getRoles()
			.then(function(result){
				$scope.accessTypeList = result.data;
			});

			$scope.getStates = function(){
				return UserFormFactory.getStates()
				.then(function(result){
					$scope.states = result.data;
					$scope.districts = [];
					$scope.blocks = [];

					$scope.editUser.stateId = $scope.place.state;
					$scope.place.state = null;
				});
			}
			
			$scope.getDistricts = function(stateId){
				return UserFormFactory.getDistricts(stateId)
				.then(function(result){
					$scope.districts = result.data;
					$scope.blocks = [];

					$scope.editUser.districtId = $scope.place.district;
					$scope.place.district = null;
				});
			}

			$scope.getBlocks =function(districtId){
				return UserFormFactory.getBlocks(districtId)
				.then(function(result){
					$scope.blocks = result.data;

					$scope.editUser.blockId = $scope.place.block;
					$scope.place.block = null;
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

			$scope.stateRes = false;
			$scope.districtRes = false;

			$scope.$watch('editUser.stateId', function(value){
				if(value != null){
					$scope.getDistricts(value)
					$scope.stateRes = true;
				}
			});
			$scope.$watch('editUser.districtId', function(value){
				if(value != null && $scope.stateRes == true){
					if($scope.editUser.stateId != null){
						$scope.getBlocks(value);
						$scope.districtRes = true;
					}
				}
			});
			$scope.$watch('editUser.blockId', function(value){
				if(value != null && $scope.stateRes == true && $scope.districtRes == true){

				}
			});

			$scope.editUserSubmit = function() {
				if ($scope.editUserForm.$valid) {
					delete $scope.editUser.$$hashKey;
					$http({
						method  : 'POST',
						url     : backend_root + 'nms/user/updateUser',
						data    : $scope.editUser, //forms user object
						headers : {'Content-Type': 'application/json'} 
					}).then(function(result){
						alert(result.data['0']);
						// $scope.open()
					})
				}
				else{
					angular.forEach($scope.editUserForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}
			};

			// $scope.open = function () {
			// 	$modal.open({
			// 		templateUrl: 'myModalContent.html', // loads the template
			// 		backdrop: true, // setting backdrop allows us to close the modal window on clicking outside the modal window
			// 		windowClass: 'modal', // windowClass - additional CSS class(es) to be added to a modal window template
			// 		controller: function ($scope, $modalInstance) {
			// 			$scope.submit = function () {
			// 				$modalInstance.dismiss('cancel'); // dismiss(reason) - a method that can be used to dismiss a modal, passing a reason
			// 			}
			// 			$scope.cancel = function () {
			// 				$modalInstance.dismiss('cancel'); 
			// 			};
			// 		},
			// 	});//end of modal.open
			// }; // end of scope.open function
		
		}]);
})()