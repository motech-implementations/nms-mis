(function(){
    var nmsReportsApp = angular
        .module('nmsReports');

        nmsReportsApp.controller("TandCController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("HelpController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/helpPage')
                .then(function(result){
                        if(result.status===200){
                            $scope.helpPage= result.data.pagecontent;
                            $scope.helpPageContent = $sce.trustAsHtml($scope.helpPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }]);
        nmsReportsApp.controller("HLPolicyController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("SitemapController", ['$scope', '$state','$http','$sce', function($scope, $state,$http,$sce){

            $http.get(backend_root + 'page/sitemap')
                .then(function(result){
                        if(result.status===200){
                            console.log("HU haaa")
                            $scope.sitemapPage= result.data.pagecontent;
                            $scope.sitemappageContent = $sce.trustAsHtml($scope.sitemapPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        $scope.func_aboutus = function(){
            $state.go('AboutUs')
        }

        $scope.func_aboutKilkari = function(){
            $state.go('AboutKilkari')
        }

        $scope.func_aboutMA = function(){
            $state.go('AboutMA')
        }

        $scope.func_UM = function(){
            $state.go('userManual.websiteInformation')
        }

        $scope.func_FAQ = function(){
            $state.go('faq.faqGeneralInfo')
        }

        $scope.func_Feedback = function(){
            $state.go('feedbackForm')
        }

        $scope.func_Contactus = function(){
            $state.go('contactUs')
        }

        $scope.func_downloads = function(){
            $state.go('Downloads')
        }

        $scope.func_tnc = function(){
            $state.go('TandC')
        }

        $scope.func_pp = function(){
            $state.go('PrivacyPolicy')
        }

        $scope.func_cp = function(){
            $state.go('CopyrightPolicy')
        }

        $scope.func_HLP = function(){
            $state.go('HLPolicy')
        }

        $scope.func_desclaimer = function(){
            $state.go('Disclaimer')
        }

        $scope.func_help = function(){
            $state.go('Help')
        }

    }]);
        nmsReportsApp.controller("DisclaimerController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("CopyrightPController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("PrivacyPController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("AboutKilkariController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/aboutKilkari')
                .then(function(result){
                        if(result.status===200){
                            $scope.aboutKilkariPage= result.data.pagecontent;
                            $scope.aboutKilkariPageContent = $sce.trustAsHtml($scope.aboutKilkariPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }]);
        nmsReportsApp.controller("AboutMAController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/aboutMA')
                .then(function(result){
                        if(result.status===200){
                            $scope.aboutMAPage= result.data.pagecontent;
                            $scope.aboutMAPageContent = $sce.trustAsHtml($scope.aboutMAPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )
        }]);
        nmsReportsApp.controller("AboutUsController", ['$scope', '$state', function($scope, $state){
        }]);

})()