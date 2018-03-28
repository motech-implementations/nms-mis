(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserManualController", ['$scope', '$state', function($scope, $state){

            switch($state.current.name){
                case "userManual.kilkari":$scope.active1 = 'kr';break;
                case "userManual.mobileAcademy":$scope.active1 = 'ma';break;
                case "userManual.userManual_Profile":$scope.active1 = 'pr';break;
                case "userManual.userManual_Management":$scope.active1 = 'um';break;
                default :$scope.active1 = 'wi';break;
            }
            // localStorage.getItem('role') = 'Anonymous';
            (function () {
                if(localStorage.getItem('role') === undefined) {
                    $scope.selectRole = 0;
                } else {

                    $scope.selectRole = localStorage.getItem('role');
                }
            }
            )();

            (function () {
                    if(($scope.selectRole < 5 && $state.current.name ==="userManual.userManual_Management") || ($scope.selectRole < 1 && $state.current.name ==="userManual.kilkari") || ($scope.selectRole < 1 && $state.current.name ==="userManual.mobileAcademy")) {
                        $state.go('userManual.websiteInformation');
                        $scope.active1 = 'wi';
                    }
                }
            )();
            $scope.roles = [
                {"id": 0, "role" : "Anonymous"},
                {"id": 1, "role" : "National User"},
                {"id": 2, "role" : "State User"},
                {"id": 3, "role" : "District User"},
                {"id": 4, "role" : "Block User"},
                {"id": 5, "role" : "National Admin"},
                {"id": 6, "role" : "State Admin"},
                {"id": 7, "role" : "District Admin"}

                /*{"id": 0, "role" : "Anonymous"},
                {"id": 1, "role" : "User"},
                {"id": 2, "role" : "Admin"}*/
            ];

            $scope.func = function (val) {
                if(val === 'wi'){
                    $state.go('userManual.websiteInformation');
				}
				else if(val=== 'kr'){
                	$state.go('userManual.kilkari')
				}
                else if(val=== 'ma'){
                    $state.go('userManual.mobileAcademy')
                }
                else if(val=== 'um'){
                        $state.go('userManual.userManual_Management');

                }
                else if(val=== 'pr'){
                    $state.go('userManual.userManual_Profile')
                }
            };

            $scope.fnk = function () {
                localStorage.setItem('role', $scope.selectRole);
                if(($scope.selectRole < 5 && $state.current.name ==="userManual.userManual_Management") || ($scope.selectRole < 1 && $state.current.name ==="userManual.kilkari") || ($scope.selectRole < 1 && $state.current.name ==="userManual.mobileAcademy")) {
                    $scope.active1 = 'wi';
                    $state.go('userManual.websiteInformation');
                }

            }
		}])
})();
