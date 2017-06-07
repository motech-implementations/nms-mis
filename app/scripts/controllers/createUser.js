(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("CreateUserController", ['$scope', '$state' 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){
			
			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

			UserFormFactory.downloadCurrentUser()
			.then(function(result){
				UserFormFactory.setCurrentUser(result.data);
			})


			UserFormFactory.getRoles()
			.then(function(result){
				$scope.accessTypeList = result.data;
			});

			$scope.accessLevelList = ["NATIONAL", "STATE", "DISTRICT", "BLOCK"]

			$scope.getAccessLevel = function(level){
			
				var list = $scope.accessLevelList;
				var item = $scope.newUser.accessLevel
				var off = 4 - list.length;
				return list.indexOf(item) + off < level;
			}

			$scope.getStates = function(){
				return UserFormFactory.getStates()
				.then(function(result){
					$scope.states = result.data;
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

			$scope.newUser = {};
			
			// $scope.clearForm = function(){
			// 	$scope.newUser = {};
			// 	$scope.createUserForm.$setPristine();

			// 	$scope.$parent.currentPage = "user-table";
			// 	delete $scope.$parent.currentPageTitle;
			// }

			$scope.createUserSubmit = function() {
				if ($scope.createUserForm.$valid) {
					delete $scope.newUser.$$hashKey;
					$http({
						method  : 'post',
						url     : backend_root + 'nms/user/createUser',
						data    : $scope.newUser, //forms user object
						headers : {'Content-Type': 'application/json'} 
					}).then(function(result){
						alert(result.data['0']);
						if(result.data['0'] == 'User Created'){
							$state.go('userManagement.userTable', {});
						}
					})
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

				$scope.getStates();
				$scope.place = {};

				$scope.newUser.stateId = null;
				$scope.newUser.districtId = null;
				$scope.newUser.blockId = null;

				$scope.createUserForm.state.$setPristine(false);
				$scope.createUserForm.district.$setPristine(false);
				$scope.createUserForm.block.$setPristine(false);
			}

			$scope.$watch('newUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue){
					$scope.onAccessLevelChanged();
				}
			});

			$scope.$watch('newUser.stateId', function(value){
				if(value != null){
					$scope.newUser.district = null;
					$scope.newUser.block = null;

					$scope.getDistricts(value)
				}
			});

			$scope.$watch('newUser.districtId', function(value){
				if(value != null){
					$scope.newUser.block = null;

					$scope.getBlocks(value);
				}
			});

			$scope.$watch('newUser.blockId', function(value){
				if(value != null){

				}
			});

		}])
})()