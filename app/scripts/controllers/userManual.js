(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("UserManualController", ['$scope', '$state','$location', 'UserFormFactory', function($scope, $state, $location, UserFormFactory){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})
            $scope.active1 = 'wi';

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
