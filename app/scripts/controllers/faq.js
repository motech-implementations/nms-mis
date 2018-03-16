(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("faqController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){
            $scope.isCollapsed = true;
			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})
            $scope.active1 = 'wi';
            $scope.func = function (val) {
                if(val == 'wi'){
                    $state.go('faq.faqWebsiteInformation');
                }
                else if(val== 'kr'){
                    $state.go('faq.faqKilkari')
                }
                else if(val== 'ma'){
                    $state.go('faq.faqMobileAcademy')
                }
                else if(val== 'um'){
                    $state.go('faq.faqUserManagement')
                }
                else if(val== 'pr'){
                    $state.go('faq.faqProfile')
                }
            }
		}])
})();
