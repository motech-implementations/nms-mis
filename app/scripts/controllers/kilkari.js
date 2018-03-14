/*
(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("kilkariController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

			$scope.show = true ;
			$scope.show2 = true ;
			$scope.show3 = true ;

			$scope.showAg = function (){
			$scope.show = true;
			$scope.show2 = false;
			$scope.show3 = false;
			}

			$scope.showBn = function (){
            			$scope.show2 = true;
            			$scope.show = false;
            			$scope.show3 = false;
            			}

            			$scope.showBd = function (){
                        			$scope.show3 = true;
                        			$scope.show = false;
                        			$scope.show2 = false;
                        			}

		}])
})();
*/

(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("kilkariController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})
			 $scope.mobileAcademyReports = [];

            			UserFormFactory.getReportsMenu()
            			.then(function(result){
                            if(result.data[1].service == "K"){

                                    $scope.serviceReport = result.data[1].name;
                                    console.log($scope.serviceReport);
                                    $scope.mobileAcademyReports = result.data[1].options;

                                }
                            else {
                                $scope.mobileAcademyReports = result.data[0].options;
                            }
            			})



            		}])
            })();

