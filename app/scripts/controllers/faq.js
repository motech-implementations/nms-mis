(function () {
    var nmsReportsApp = angular
        .module('nmsReports');
    nmsReportsApp.controller("faqController", ['$scope', '$state', '$http', '$sce', function ($scope, $state, $http, $sce) {
        $scope.isCollapsed = true;
        $scope.current = 0;
        $scope.active = true;

        $http.get(backend_root + 'page/faq')
            .then(function(result){
                    if(result.status===200){
                        $scope.faqPage= result.data.pagecontent;
                        $scope.faqPageContent = $sce.trustAsHtml($scope.faqPage);
                    }
                    else {
                        $state.go('login', {});
                    }
                }, function(error){
                    $state.go('login', {});
                }
            )

        switch ($state.current.name) {
            case "faq.faqGeneralInfo":
                $scope.active1 = 'faq-general';
                break;
            case "faq.faqLoginInfo":
                $scope.active1 = 'faq-login';
                break;
            case "faq.faqReportsInfo":
                $scope.active1 = 'faq-reports';
                break;
            case "faq.faqLineListingInfo":
                $scope.active1 = 'faq-line-listing';
                break;
            case "faq.faqAggregateInfo":
                $scope.active1 = 'faq-aggregate';
                break;
            default :
                $scope.active1 = 'faq-general';
                break;
        }
        $scope.fun = function (val) {
            if ($scope.current == val) {
                $scope.current = 0;
            } else {
                $scope.current = val;
            }
            $scope.active = !$scope.active;
        }

        $scope.func = function (val) {
            if (val == 'faq-general') {
                $state.go('faq.faqGeneralInfo');
            } else if (val == 'faq-login') {
                $state.go('faq.faqLoginInfo')
            } else if (val == 'faq-reports') {
                $state.go('faq.faqReportsInfo')
            } else if (val == 'faq-line-listing') {
                $state.go('faq.faqLineListingInfo')
            } else if (val == 'faq-aggregate') {
                $state.go('faq.faqAggregateInfo')
            }
        }
    }]);
    nmsReportsApp.controller("faqAggregateInfoController", ['$scope', '$http', '$sce', function ($scope, $http, $sce) {

        $http.get(backend_root + 'page/faqAggregateInfo')
            .then(function(result){
                    if(result.status===200){
                        $scope.faqAggregateInfoPage= result.data.pagecontent;
                        $scope.faqAggregateInfoPageContent = $sce.trustAsHtml($scope.faqAggregateInfoPage);
                    }
                    else {
                        $state.go('login', {});
                    }
                }, function(error){
                    $state.go('login', {});
                }
            )

        $scope.active = true;

    }]);
    nmsReportsApp.controller("faqLineListingInfoController", ['$scope', '$http', '$sce', function ($scope, $http, $sce) {

        $http.get(backend_root + 'page/faqLineListingInfo')
            .then(function(result){
                    if(result.status===200){
                        $scope.faqLineListingInfoPage= result.data.pagecontent;
                        $scope.faqLineListingInfoPageContent = $sce.trustAsHtml($scope.faqLineListingInfoPage);
                    }
                    else {
                        $state.go('login', {});
                    }
                }, function(error){
                    $state.go('login', {});
                }
            )

        $scope.active = true;
    }]);
    nmsReportsApp.controller("faqLoginInfoController", ['$scope', '$http', '$sce', function ($scope, $http, $sce) {

        $http.get(backend_root + 'page/faqLoginInfo')
            .then(function(result){
                    if(result.status===200){
                        $scope.faqLoginInfoPage= result.data.pagecontent;
                        $scope.faqLoginInfoPageContent = $sce.trustAsHtml($scope.faqLoginInfoPage);
                    }
                    else {
                        $state.go('login', {});
                    }
                }, function(error){
                    $state.go('login', {});
                }
            )

        /*			UserFormFactory.isLoggedIn()
                    .then(function(result){
                        if(!result.data){
                            $state.go('login', {});
                        }
                    })*/

        $scope.active = true;

    }]);
    nmsReportsApp.controller("faqReportsInfoController", ['$scope', '$http', '$sce', function ($scope, $http, $sce) {

        $http.get(backend_root + 'page/faqReportsInfo')
            .then(function(result){
                    if(result.status===200){
                        $scope.faqReportsInfoPage= result.data.pagecontent;
                        $scope.faqReportsInfoPageContent = $sce.trustAsHtml($scope.faqReportsInfoPage);
                    }
                    else {
                        $state.go('login', {});
                    }
                }, function(error){
                    $state.go('login', {});
                }
            )

        $scope.active = true;

    }]);
    nmsReportsApp.controller("faqGeneralInfoController", ['$scope', '$http', '$sce', function ($scope, $http, $sce) {

        $http.get(backend_root + 'page/faqGeneralInfo')
            .then(function(result){
                    if(result.status===200){
                        $scope.faqGeneralInfoPage= result.data.pagecontent;
                        $scope.faqGeneralInfoPageContent = $sce.trustAsHtml($scope.faqGeneralInfoPage);
                    }
                    else {
                        $state.go('login', {});
                    }
                }, function(error){
                    $state.go('login', {});
                }
            )

        $scope.active = true;



    }]);
})();
