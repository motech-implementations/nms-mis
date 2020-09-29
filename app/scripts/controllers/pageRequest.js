(function(){
    var nmsReportsApp = angular
        .module('nmsReports')
        .controller("reportsHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/reports')
                .then(function(result){
                        if(result.status===200){
                            $scope.reportsPage= result.data.pagecontent;
                            $scope.reportsPageContent = $sce.trustAsHtml($scope.reportsPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("bulkUserHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/bulkUser')
                .then(function(result){
                        if(result.status===200){
                            $scope.bulkUserPage= result.data.pagecontent;
                            $scope.bulkUserPageContent = $sce.trustAsHtml($scope.bulkUserPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("createUserHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/createUser')
                .then(function(result){
                        if(result.status===200){
                            $scope.createUserPage= result.data.pagecontent;
                            $scope.createUserPageContent = $sce.trustAsHtml($scope.createUserPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("editUserHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/editUser')
                .then(function(result){
                        if(result.status===200){
                            $scope.editUserPage= result.data.pagecontent;
                            $scope.editUserPageContent = $sce.trustAsHtml($scope.editUserPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userTableHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("userManualManagementHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("aboutKilkariHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("aboutMAHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("changePasswordHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("downloadsHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("faqHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("faqAggregateInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("faqGeneralInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("faqLineListingInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("faqLoginInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("faqReportsInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("feedbackFormHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("feedbackResponseHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("profileHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("userManualHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("userManualKilkariHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("userManualKilkariAggHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("userManualMobileAcademyHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("userManualMobileAcademyAggHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("userManualProfileHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("userManualWebsiteInformationHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("forgotPasswordHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("sitemapHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("contactUsHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("contactUsResponseHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])
        .controller("helpPageHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

        }])

        .directive('compileTemplate', function($compile, $parse){
            return {
                link: function(scope, element, attr){
                    var parsed = $parse(attr.ngBindHtml);
                    function getStringValue() {
                        return (parsed(scope) || '').toString();
                    }

                    // Recompile if the template changes
                    scope.$watch(getStringValue, function() {
                        $compile(element, null, -9999)(scope);  // The -9999 makes it skip directives so that we do not recompile ourselves
                    });
                }
            }
        })
})()