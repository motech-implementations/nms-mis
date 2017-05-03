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

					// $http.post('http://localhost:8080/NMSReportingSuite/nms/user/create-new',
					// $scope.newUser, 
					// {'Content-Type': 'application/json'})
					// .success(function (data, status, headers, config) {
					// 	console.log(data);
					// })
					// .error(function (data, status, header, config) {
					// 	console.log(data);
					// });

					// $http({
					// 	method  : 'post',
					// 	url     : 'http://localhost:8080/NMSReportingSuite/nms/user/create-new',
					// 	data    : JSON.stringify($scope.newUser), //forms user object
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
				$scope.newUser.createdBy = true;

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

			$scope.$watch('place.state', function(value){
				if(value != null){
					$scope.districts = UserFormFactory.getChildLocations(value.locationId);

					$scope.place.district = null;
					$scope.place.block = null;

					$scope.newUser.state = value.location;
					$scope.newUser.district = null;
					$scope.newUser.block = null;
				}
			});

			$scope.$watch('place.district', function(value){
				if(value != null){
					$scope.blocks = UserFormFactory.getChildLocations(value.locationId);

					$scope.place.block = null;

					$scope.newUser.district = value.location;
					$scope.newUser.block = null;
				}
			});

			$scope.$watch('place.block', function(value){
				if(value != null){
					$scope.newUser.block = value.location;
				}
			});

		}])
})()