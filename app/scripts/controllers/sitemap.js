(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("SitemapController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){


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
                $state.go('userManual')
            }

            $scope.func_FAQ = function(){
                $state.go('faq')
            }

            $scope.func_Feedback = function(){
                $state.go('feedbackForm')
            }

            $scope.func_Contactus = function(){
                $state.go('contactUs')
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

		}])
})();