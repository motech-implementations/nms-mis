(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserTableController", ['$scope', '$state', '$filter', 'UserTableFactory', '$localStorage', function($scope, $state, $filter, UserTableFactory, $localStorage) {
            var selectedPage = $scope.currentPageNo;
            var lessRecordsFilter = false;
			$scope.numPerPageList = [10, 20];
            $scope.waiting = UserTableFactory.getAllUsers().length === 0;

			function init() {
				$scope.numPerPage = $scope.numPerPageList[0];
                var filter = $localStorage.filter;
				if (filter) {
					for (key in filter) {
						if (filter[key]) {
							$scope[key] = filter[key];
							if (key === 'filterText') {
								console.log(filter[key]);
								$scope.filterText = filter[key];
							}
						}
					}
				}
                delete $localStorage.filter;
			}

			UserTableFactory.clearAllUsers();
			UserTableFactory.getUsers().then(function(result) {
				UserTableFactory.setUsers(result.data);
				$scope.waiting = false;
                $scope.currentPageNo = selectedPage;
                init();
            });

			function resetPage() {
				$scope.currentPageNo = 1;
			}

			$scope.resetDistrict = function(){
				$scope.districtName = "";
			};

			$scope.resetBlock = function(){
				$scope.blockName = "";
			};

			$scope.getAllUsers = function() {
				var users = UserTableFactory.getAllUsers();
                if ($filter('filter')(users, $scope.search).length <= $scope.numPerPage) {
                    lessRecordsFilter = true;
                    $scope.currentPageNo = 1;
                }
				return users;
			};

			$scope.crop = function(name){
				if(name.length > 13){
					return name.substring(0, 10) + "...";
				}
				return name;
			};

			$scope.getUniqueAccessTypes = function(){
				var aT = [];
				var users = $scope.getAllUsers();
				for(var i=0;i<users.length;i++){
					if(aT.indexOf(users[i].accessType) === -1) {
						aT.push(users[i].accessType);
					}
				}
				return aT;
			};

			$scope.setUniqueAccessTypes = function(accessType) {
                $scope.accType = accessType;
            };

			$scope.getUniqueAccessLevels = function() {
				var aL = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if(aL.indexOf(users[i].accessLevel) === -1) {
						aL.push(users[i].accessLevel);
					}
				}
				return aL;
			};

			$scope.setUniqueAccessLevels = function(accessLevel){
				$scope.accLevel = accessLevel;
			};

			$scope.getUniqueStates = function(){
				var states = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if((users[i].state !== "") && (states.indexOf(users[i].state) === -1)) {
						states.push(users[i].state);
					}
				}
				return states;
			};
			$scope.setUniqueStates = function(state){
				$scope.stateName = state;
			};
			$scope.$watch('stateName', $scope.resetDistrict);
			$scope.$watch('stateName', $scope.resetBlock);
			$scope.$watch('currentPageNo', function() {
				if (($scope.filterData.length !== 0) || lessRecordsFilter) {
                    $state.transitionTo('userManagement.userTable', {pageNum: $scope.currentPageNo}, {notify: false});
                    lessRecordsFilter = false;
				} else {

				}
            });
			$scope.getUniqueDistricts = function(){
				var districts = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if((angular.lowercase(users[i].state).indexOf(angular.lowercase($scope.stateName) > -1 ||''))&&
					(angular.lowercase(users[i].block).indexOf(angular.lowercase($scope.blockName) > -1 ||''))&&
					((users[i].district !== "")&&(districts.indexOf(users[i].district) === -1))) {
						districts.push(users[i].district);
					}
				}
				return districts;
			};
			$scope.setUniqueDistricts = function(district){
				$scope.districtName = district;
			};
			$scope.$watch('districtName', $scope.resetBlock);
			$scope.getUniqueBlocks = function(){
				var blocks = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if((angular.lowercase(users[i].state).indexOf(angular.lowercase($scope.stateName) > -1 ||'')) &&
					(angular.lowercase(users[i].district).indexOf(angular.lowercase($scope.districtName) > -1 ||''))&&
					((users[i].block !== "") && (blocks.indexOf(users[i].block) === -1))) {
						blocks.push(users[i].block);
					}
				}
				return blocks;
			};
			$scope.setUniqueBlocks = function(block){
				$scope.blockName = block;
			};

			$scope.exists = function(value){
				return value !== '';
			};

			$scope.resetFilters = function(){
				$scope.stateName = '';
				$scope.districtName = '';
				$scope.blockName = '';
				$scope.accType = '';
				$scope.accLevel = '';
				$scope.filterText = '';
				$scope.sorter = 'id';
				$scope.reverse = false;
			};

			$scope.resetFilters();

			$scope.$watch('numPerPage', resetPage());

			$scope.dropdownOpen =function(){
				return (
					$scope.exists($scope.accType) ||
					$scope.exists($scope.accLevel) ||
					$scope.exists($scope.stateName) ||
					$scope.exists($scope.districtName) ||
					$scope.exists($scope.blockName)
				);
			};

			$scope.search = function (row) {
				return (angular.lowercase(row.name).indexOf(angular.lowercase($scope.filterText) || '') > -1 ||
						angular.lowercase(row.username).indexOf(angular.lowercase($scope.filterText) || '') > -1 ||
						angular.lowercase(row.phoneNumber).indexOf(angular.lowercase($scope.filterText)  || '')> -1 ||
						angular.lowercase(row.email).indexOf(angular.lowercase($scope.filterText) || '')> -1  ||
						angular.lowercase(row.accessLevel).indexOf(angular.lowercase($scope.filterText)  || '')> -1 ||
						angular.lowercase(row.state).indexOf(angular.lowercase($scope.filterText)  || '')> -1 ||
						angular.lowercase(row.district).indexOf(angular.lowercase($scope.filterText)  || '')> -1 ||
						angular.lowercase(row.block).indexOf(angular.lowercase($scope.filterText) || '') > -1 ) &&(
						angular.lowercase(row.accessType).indexOf(angular.lowercase($scope.accType)  ||'')> -1 )&&(
						angular.lowercase(row.accessLevel).indexOf(angular.lowercase($scope.accLevel) ||'') > -1 )&&(
						angular.lowercase(row.state).indexOf(angular.lowercase($scope.stateName)  ||'') > -1)&&(
						angular.lowercase(row.district).indexOf(angular.lowercase($scope.districtName)  ||'')> -1 )&&(
						angular.lowercase(row.block).indexOf(angular.lowercase($scope.blockName)  ||'')> -1
						);
			};

			$scope.sorter = 'id';
			$scope.sort_by = function(newSortingOrder) {
				if ($scope.sorter === newSortingOrder) {
                    $scope.reverse = !$scope.reverse;
                }
				$scope.sorter = newSortingOrder;
			};

			$scope.editUser = function(userId) {
				$localStorage.filter = {
                    filterText: $scope.filterText ? $scope.filterText : null,
					reverse: $scope.reverse ? $scope.reverse : null,
					sorter: $scope.sorter ? $scope.sorter : null,
					accLevel: $scope.accLevel ? $scope.accLevel : null,
					accType: $scope.accType ? $scope.accType : null,
                    stateName: $scope.stateName ? $scope.stateName : null,
                    districtName: $scope.districtName ? $scope.districtName : null,
                    blockName: $scope.blockName ? $scope.blockName : null
				};
				$state.go("userManagement.editUser", { pageNum: $scope.currentPageNo, id: userId});
			};

			$scope.deleteUser = function(user) {

			};

			$scope.createUser = function() {

			}

		}])
})();