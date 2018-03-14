(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("mobileAcademyController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

            $scope.mobileAcademyReports = [];

			UserFormFactory.getReportsMenu()
			.then(function(result){
                if(result.data[0].service == "M"){

                        $scope.serviceReport = result.data[0].name;
                        console.log($scope.serviceReport);
                        $scope.mobileAcademyReports = result.data[0].options;

                    }
                else {
                    $scope.mobileAcademyReports = result.data[1].options;
                }
			})



		}])
})();

