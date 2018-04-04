(function(){
    var nmsReportsApp = angular
        .module('nmsReports');

        nmsReportsApp.controller("TandCController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("HelpController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("HLPolicyController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("SitemapController", ['$scope', '$state', function($scope, $state){


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
            $state.go('faq.faqWebsiteInformation')
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
        nmsReportsApp.controller("AboutKilkariController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("AboutMAController", ['$scope', '$state', function($scope, $state){
        }]);
        nmsReportsApp.controller("AboutUsController", ['$scope', '$state', function($scope, $state){
        }]);

})()