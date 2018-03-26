(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("faqProfileController", ['$scope', function($scope){

/*			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})*/

			$scope.active = true;
            $scope.active1 = true;
            $scope.active2 = true;

		}])
})();


