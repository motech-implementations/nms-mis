(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("faqController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){
            $scope.isCollapsed = true;

/*			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})*/


            switch($state.current.name){
                case "faq.faqKilkari":$scope.active1 = 'kr';break;
                case "faq.faqMobileAcademy":$scope.active1 = 'ma';break;
                case "faq.faqProfile":$scope.active1 = 'pr';break;
                case "faq.faqUserManagement":$scope.active1 = 'um';break;
                default :$scope.active1 = 'wi';break;
            }

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
