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
            $scope.active1 = true;
            $scope.active2 = true;
            $scope.active3 = true;
            $scope.active4 = true;
            $scope.active5 = true;
		}])
})();
