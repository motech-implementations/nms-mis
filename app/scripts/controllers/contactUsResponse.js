(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("ContactUsResponseController", ['$scope', '$state', '$timeout','$sce', function($scope, $state, $timeout,$sce){

			$http.get(backend_root + 'page/contactUsResponse')
				.then(function(result){
						if(result.status===200){
							$scope.contactUsResponsePage= result.data.pagecontent;
							$scope.contactUsResponsepageContent = $sce.trustAsHtml($scope.contactUsResponsePage);
						}
						else {
							$state.go('login', {});
						}
					}, function(error){
						$state.go('login', {});
					}
				)

            $scope.goBackToContactUsForm = function(){
                $state.go('contactUs')
            }

		}])
})();