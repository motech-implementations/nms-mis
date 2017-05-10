(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("CreateUserController", ['$scope', 'UserFormFactory', '$http', function($scope, UserFormFactory, $http){
			
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

			$scope.accessLevelList = ["National", "State", "District", "Block"];

			$scope.newUser = {};
			

			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.newUser.accessLevel
				return list.indexOf(item) < level;
			}

			$scope.clearForm = function(){
				$scope.newUser = {};
				$scope.createUserForm.$setPristine();

				$scope.$parent.currentPage = "user-table";
				delete $scope.$parent.currentPageTitle;
			}

			$scope.createUserSubmit = function() {
				if ($scope.createUserForm.$valid && !$scope.createUserForm.$pristine) {
					console.log(JSON.stringify($scope.newUser));

					// UserFormFactory.createUserSubmitDto($scope.newUser);
					// $http.post('http://localhost:8080/NMSReportingSuite/nms/user/create-new', $scope.newUser);

					// $http.post('http://localhost:8080/NMSReportingSuite/nms/user/createFromDto',
					// JSON.stringify($scope.newUser), 
					// {'Content-Type': 'application/text'})
					// .success(function (data, status, headers, config) {
					// 	console.log(data);
					// })
					// .error(function (data, status, header, config) {
					// 	console.log(data);
					// });

					// $http({
					// 	method  : 'post',
					// 	url     : 'http://localhost:8080/NMSReportingSuite/nms/user/createFromDto',
					// 	data    : $scope.newUser, //forms user object
					// 	headers : {'Content-Type': 'application/json'} 
					// });

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

				$scope.newUser.state = null;
				$scope.newUser.district = null;
				$scope.newUser.block = null;

				$scope.createUserForm.state.$setPristine(false);
				$scope.createUserForm.district.$setPristine(false);
				$scope.createUserForm.block.$setPristine(false);
			}

			$scope.$watch('newUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue){
					$scope.onAccessLevelChanged();
				}
			});

			$scope.$watch('newUser.state', function(value){
				if(value != null){
					$scope.getDistricts(value)
					.then(function(){
						// $scope.place.district = null;
						// $scope.place.block = null;

						// $scope.newUser.state = value.stateName;
						$scope.newUser.district = null;
						$scope.newUser.block = null;

						// console.log($scope.place);
					})
				}
			});

			$scope.$watch('newUser.district', function(value){
				if(value != null){
					$scope.getBlocks(value)
					.then(function(){
						// $scope.place.block = null;

						// $scope.newUser.district = value.districtName;
						$scope.newUser.block = null;

						// console.log($scope.place);
					})
				}
			});

			$scope.$watch('newUser.block', function(value){
				if(value != null){
					// $scope.newUser.block = value.blockName;

					// console.log($scope.place);
				}
			});

		}])
})()