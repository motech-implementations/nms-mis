(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserTableController", ['$scope', 'UserTableFactory', function($scope, UserTableFactory){

			// $scope.v = [];
			// $scope.v = TestFactory.getUsers();

			$scope.numPerPageList = [10, 20];

			$scope.init = function(){
				$scope.headers = UserTableFactory.headers();
				$scope.currentPageNo = 1;
				$scope.numPerPage = $scope.numPerPageList[0];
			}

			UserTableFactory.getUsers()
			.then(function(result){
				UserTableFactory.setUsers(result.data)
				$scope.init();
			});

			$scope.resetPage = function(){
				$scope.currentPageNo = 1;
			}

			$scope.getAllUsers = function(){
				return UserTableFactory.getAllUsers();
			}

            $scope.getUniqueAccessTypes = function(){
                var aT = [];
                var users = UserTableFactory.getAllUsers();
                for(var i=0;i<users.length;i++){
                    if(aT.indexOf(users[i].accessType)==-1) {
                        aT.push(users[i].accessType);
                    }
                }
                return aT;
            }
            $scope.getUniqueAccessLevels = function(){
                var aL = [];
                var users = UserTableFactory.getAllUsers();
                for(var i=0;i<users.length;i++){
                    if(aL.indexOf(users[i].accessLevel)==-1) {
                        aL.push(users[i].accessLevel);
                    }
                }
                return aL;
            }
            $scope.getUniqueStates = function(){
                var states = [];
                var users = UserTableFactory.getAllUsers();
                for(var i=0;i<users.length;i++){
                    if((users[i].state!="")&&(states.indexOf(users[i].state)==-1)) {
                        states.push(users[i].state);
                    }
                }
            return states;
            }
            $scope.getUniqueDistricts = function(){
                var districts = [];
                var users = UserTableFactory.getAllUsers();
                for(var i=0;i<users.length;i++){
                    if((users[i].district!="")&&(districts.indexOf(users[i].district)==-1)) {
                        districts.push(users[i].district);
                    }
                }
            return districts;
            }
            $scope.getUniqueBlocks = function(){
                var blocks = [];
                var users = UserTableFactory.getAllUsers();
                for(var i=0;i<users.length;i++){
                    if((users[i].block!="")&&(blocks.indexOf(users[i].block)==-1)) {
                        blocks.push(users[i].block);
                    }
                }
            return blocks;
            }

			$scope.$watch('numPerPage', $scope.resetPage);

			$scope.search = function (row) {
				return (angular.lowercase(row.name).includes(angular.lowercase($scope.filterText) || '') || 
						angular.lowercase(row.username).includes(angular.lowercase($scope.filterText) || '') || 
						angular.lowercase(row.phoneNumber).includes(angular.lowercase($scope.filterText) || '') ||
						angular.lowercase(row.email).includes(angular.lowercase($scope.filterText) || '') || 
						angular.lowercase(row.accessLevel).includes(angular.lowercase($scope.filterText) || '') ||
						angular.lowercase(row.state).includes(angular.lowercase($scope.filterText) || '') ||
						angular.lowercase(row.district).includes(angular.lowercase($scope.filterText) || '') ||
						angular.lowercase(row.block).includes(angular.lowercase($scope.filterText) || '') ) &&(
						angular.lowercase(row.accessType).includes(angular.lowercase($scope.accType)||'') )&&(
						angular.lowercase(row.accessLevel).includes(angular.lowercase($scope.accLevel)||'') )&&(
						angular.lowercase(row.state).includes(angular.lowercase($scope.stateName)||'') )&&(
						angular.lowercase(row.district).includes(angular.lowercase($scope.districtName)||'') )&&(
						angular.lowercase(row.block).includes(angular.lowercase($scope.blockName)||'')
						);
			};
            $scope.sorter = 'id';
            $scope.sort_by = function(newSortingOrder) {
                    if ($scope.sorter == newSortingOrder)
                        $scope.reverse = !$scope.reverse;

                    $scope.sorter = newSortingOrder;
            }
			

			$scope.editUser = function(user){
				console.log("edit");
				console.log(user);
				$scope.$parent.editUser(user.id);
			}

			$scope.deleteUser = function(user){
				console.log("delete");
				console.log(user);
			}
		}])
})()