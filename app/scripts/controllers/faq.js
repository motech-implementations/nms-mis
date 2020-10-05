(function () {
    var nmsReportsApp = angular
        .module('nmsReports');
    nmsReportsApp.controller("faqController", ['$scope', '$state', function ($scope, $state) {
        $scope.isCollapsed = true;
        $scope.current = 0;
        $scope.active = true;
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
    nmsReportsApp.controller("faqAggregateInfoController", ['$scope', function ($scope) {

        $scope.active = true;

    }]);
    nmsReportsApp.controller("faqLineListingInfoController", ['$scope', function ($scope) {

        $scope.active = true;
    }]);
    nmsReportsApp.controller("faqLoginInfoController", ['$scope', function ($scope) {

        /*			UserFormFactory.isLoggedIn()
                    .then(function(result){
                        if(!result.data){
                            $state.go('login', {});
                        }
                    })*/

        $scope.active = true;

    }]);
    nmsReportsApp.controller("faqReportsInfoController", ['$scope', function ($scope) {
        $scope.active = true;

    }]);
    nmsReportsApp.controller("faqGeneralInfoController", ['$scope', function ($scope) {

        $scope.active = true;



    }]);
})();
