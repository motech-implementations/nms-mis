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
                    $state.go('userManual.userManual_Management')
                }
                else if(val== 'pr'){
                    $state.go('userManual.userManual_Profile')
                }
            }
		}])
})();
