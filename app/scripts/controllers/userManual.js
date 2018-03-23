(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserManualController", ['$scope', '$state','$location', 'UserFormFactory', function($scope, $state, $location, UserFormFactory){

/*			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})*/

            switch($state.current.name){
                case "userManual.kilkari":$scope.active1 = 'kr';break;
                case "userManual.mobileAcademy":$scope.active1 = 'ma';break;
                case "userManual.userManual_Profile":$scope.active1 = 'pr';break;
                case "userManual.userManual_Management":$scope.active1 = 'um';break;
                default :$scope.active1 = 'wi';break;
            }


            (function () {
                if(localStorage.getItem('role') !== undefined) {
                    $scope.selectRole = localStorage.getItem('role');
                } else {
                    $scope.selectRole ='Anonymous';
                }
            }
            )();
            (function () {
                    if($scope.selectRole !== 'Admin' && $state.current.name ==="userManual.userManual_Management") {
                        $state.go('userManual.websiteInformation');
                    }
                }
            )();
            $scope.roles = [
                {role : "Anonymous"},
                {role : "User"},
                {role : "Admin"}
            ];

           /* $scope.flagx=true;
            $scope.flag1 = false;
            $scope.fuct = function () {
                $scope.flag1 = true
                console.log($scope.flag1);
            }
*/
            $scope.func = function (val) {
                if(val == 'wi'){
                    $state.go('userManual.websiteInformation');
				}
				else if(val== 'kr'){
                	$state.go('userManual.kilkari')
				}
                else if(val== 'ma'){
                    $state.go('userManual.mobileAcademy')
                }
                else if(val== 'um'){
/*                    if($scope.selectRole ==='Admin'){
                        $state.go('userManual.userManual_Management')
                    }else{*/
                        $state.go('userManual.userManual_Management');
                   /* }*/

                }
                else if(val== 'pr'){
                    $state.go('userManual.userManual_Profile')
                }
            };

            $scope.fnk = function () {
                localStorage.setItem('role', $scope.selectRole);
                if($scope.selectRole !== 'Admin' && $state.current.name ==="userManual.userManual_Management") {
                    $scope.active1 = 'wi';
                    $state.go('userManual.websiteInformation');
                }

            }
		}])
})();
