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
				if (lessRecordsFilter) {
                    $state.transitionTo('userManagement.userTable', {pageNum: $scope.currentPageNo}, {notify: false});
                    lessRecordsFilter = false;
				} else {

				}
            });
			$scope.getUniqueDistricts = function(){
				var districts = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if((users[i].state.toLowerCase().indexOf($scope.stateName.toLowerCase() > -1 ||''))&&
					(users[i].block.toLowerCase().indexOf($scope.blockName.toLowerCase() > -1 ||''))&&
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
					if((users[i].state.toLowerCase().indexOf($scope.stateName.toLowerCase()) > -1 ||'') &&
					(users[i].district.toLowerCase().indexOf($scope.districtName.toLowerCase()) > -1 ||'')&&
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
				return (row.name.toLowerCase().indexOf($scope.filterText.toLowerCase() || '') > -1 ||
						row.username.toLowerCase().indexOf($scope.filterText.toLowerCase() || '') > -1 ||
						row.phoneNumber.toLowerCase().indexOf($scope.filterText.toLowerCase()  || '')> -1 ||
						row.email.toLowerCase().indexOf($scope.filterText.toLowerCase() || '')> -1  ||
						row.accessLevel.toLowerCase().indexOf($scope.filterText.toLowerCase()  || '')> -1 ||
						row.state.toLowerCase().indexOf($scope.filterText.toLowerCase()  || '')> -1 ||
						row.district.toLowerCase().indexOf($scope.filterText.toLowerCase()  || '')> -1 ||
						row.block.toLowerCase().indexOf($scope.filterText.toLowerCase() || '') > -1 ) &&(
						row.accessType.toLowerCase().indexOf($scope.accType.toLowerCase()  ||'')> -1 )&&(
						row.accessLevel.toLowerCase().indexOf($scope.accLevel.toLowerCase() ||'') > -1 )&&(
						row.state.toLowerCase().indexOf($scope.stateName.toLowerCase()  ||'') > -1)&&(
						row.district.toLowerCase().indexOf($scope.districtName.toLowerCase()  ||'')> -1 )&&(
						row.block.toLowerCase().indexOf($scope.blockName.toLowerCase()  ||'')> -1
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
			    setFilterInLocalStorage();
				$state.go("userManagement.editUser", { pageNum: $scope.currentPageNo, id: userId});
			};

			$scope.deleteUser = function(user) {

			};

			$scope.createUser = function() {
                setFilterInLocalStorage();
                $state.go('userManagement.createUser');
			};

			function setFilterInLocalStorage() {
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
            }

		}])
})();