(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("EditUserController", ['$scope', 'UserFormFactory', '$http', '$state', '$stateParams', function($scope, UserFormFactory, $http, $state, $stateParams){

			UserFormFactory.isLoggedIn().then(function(result) {
				if (!result.data) {
					$state.go('login', {});
				}
			});

			UserFormFactory.downloadCurrentUser().then(function(result){
				UserFormFactory.setCurrentUser(result.data);
				UserFormFactory.getUser($stateParams.id).then(function(result) {
					//added null check, redirecting to usermangement table if null
					if(result.data)
					$scope.editUser = result.data;
					else {
						alert("Not authorized");
						$state.go('userManagement.userTable', {});
					}
				});
			});
			var token = 'dhty'+UserFormFactory.getCurrentUser().userId+'alkihkf';
			$scope.editUser = {};
			$scope.place = {};
			$scope.accessLevelList = ["NATIONAL", "STATE", "DISTRICT", "BLOCK"];

			$scope.showAccess = function(level){
				var levelIndex = $scope.accessLevelList.indexOf(UserFormFactory.getCurrentUser().accessLevel);
				if ($scope.accessLevelList.indexOf(level) >= levelIndex) {
					return true;
				} else {
					return false;
				}
			};

			$scope.filterAccessType = function(accessType){
				if ($scope.editUser.accessLevel === 'BLOCK' ||
					UserFormFactory.getCurrentUser().accessLevel === 'DISTRICT' ||
					(UserFormFactory.getCurrentUser().accessLevel === $scope.editUser.accessLevel && UserFormFactory.getCurrentUser().roleId !== 1)) {
					return accessType.roleId !== 2;
				} else {
					return true;
				}
			};

			$scope.filterAccessLevel = function(accessLevel){
				var temp = false;
				if ($scope.editUser.roleId === 2 && UserFormFactory.getCurrentUser().roleId !== 1) { // admin
					temp = accessLevel !== "BLOCK" && accessLevel !== UserFormFactory.getCurrentUser().accessLevel;
				} else {
					temp = true;
				}

				var levelIndex = $scope.accessLevelList.indexOf(UserFormFactory.getCurrentUser().accessLevel);
				return ($scope.accessLevelList.indexOf(accessLevel) >= levelIndex) && temp;
			};


			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.editUser.accessLevel;
				var off = 4 - list.length;
				return list.indexOf(item) + off < level;
			};
			
			$scope.roleLoading = true;
			UserFormFactory.getRoles().then(function(result){
				$scope.accessTypeList = result.data;
				$scope.roleLoading = false;
			});

			$scope.getStates = function(){
				$scope.stateLoading = true;
				return UserFormFactory.getStates().then(function(result) {
					$scope.stateLoading = false;
					$scope.states = result.data;
					$scope.districts = [];
					$scope.blocks = [];
					if($scope.editUser.stateId !== null){
						$scope.place.stateId = $scope.editUser.stateId;
						$scope.editUser.stateId = null;
					}
				});
			};
			
			$scope.getDistricts = function(stateId) {
				$scope.districtLoading = true;
				return UserFormFactory.getDistricts(stateId).then(function(result) {
					$scope.districtLoading = false;
					$scope.districts = result.data;
					$scope.blocks = [];
					if($scope.editUser.districtId !== null){
						$scope.place.districtId = $scope.editUser.districtId;
						$scope.editUser.districtId = null;
					}
				});
			};

			$scope.getBlocks =function(districtId){
				$scope.blockLoading = true;
				return UserFormFactory.getBlocks(districtId).then(function(result) {
					$scope.blockLoading = false;
					$scope.blocks = result.data;
					if($scope.editUser.blockId !== null){
						$scope.place.blockId = $scope.editUser.blockId;
						$scope.editUser.blockId = null;
					}
				});
			};

			$scope.onAccessLevelChanged = function(){
				$scope.getStates();
				$scope.editUserForm.state.$setPristine(false);
				$scope.editUserForm.district.$setPristine(false);
				$scope.editUserForm.block.$setPristine(false);
			};

			$scope.$watch('editUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue && oldValue !== null){
					$scope.onAccessLevelChanged();
				}
			});

			$scope.$watch('place.stateId', function(value){
				if(value){
					$scope.getDistricts(value)
				}
			});
			$scope.$watch('place.districtId', function(value) {
				if(value){
					$scope.getBlocks(value);
				}
			});

			$scope.editUserSubmit = function() {
				if ($scope.editUserForm.$valid) {
					$scope.editUser.stateId = $scope.place.stateId;
					$scope.editUser.districtId = $scope.place.districtId;
					$scope.editUser.blockId = $scope.place.blockId;
					delete $scope.editUser.$$hashKey;
					$http({
						method  : 'POST',
						url     : backend_root + 'nms/user/updateUser',
						data    : $scope.editUser, //forms user object
						headers : {'Content-Type': 'application/json', 'csrfToken': token}
					}).then(function(result){
                        if (UserFormFactory.isInternetExplorer()) {
                            alert(result.data['0']);

                        } else {
                            UserFormFactory.showAlert(result.data['0']);

                        }

						if (result.data['0'] === 'User Updated') {
							$state.go('userManagement.userTable', {});
						}
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

			$scope.resetPasswordSubmit = function() {
			    var password = {};
			    password.userId = $scope.editUser.userId;
			    password.oldPassword = "";
			    password.newPassword = "";
                $http({
                    method  : 'POST',
                    url     : backend_root + 'nms/admin/changePassword',
                    data    : password, //forms user object
                    headers : {'Content-Type': 'application/json', 'csrfToken': token}
                }).then(function(result){
                    if(UserFormFactory.isInternetExplorer()){
                        alert(result.data['0']);
                    }
                    else{
                        UserFormFactory.showAlert(result.data['0']);
                    }

                    if (result.data['0'] === 'Password changed successfully') {
                        $state.go('userManagement.userTable', {});
                    }

                })

            };
	//changed delete user to post, added a token for verification
            $scope.deactivateUserSubmit = function() {
				$http({
					method  : 'POST',
					url     : backend_root + 'nms/user/deleteUser',
					data    : $scope.editUser.userId,
					headers : {'Content-Type': 'application/json', 'csrfToken': token}
				})
                .then(function(result){
                    if (UserFormFactory.isInternetExplorer()) {
                        alert(result.data['0']);
                    } else {
                        UserFormFactory.showAlert(result.data['0']);
                    }

                    if (result.data['0'] === 'User deleted') {
                        $state.go('userManagement.userTable', {});
                    }
                });
            };
		
		}]);
})();