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

            $http.get(backend_root + 'page/userTable')
                .then(function(result){
                        if(result.status===200){
                            $scope.userTablePage= result.data.pagecontent;
                            $scope.userTablePageContent = $sce.trustAsHtml($scope.userTablePage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userManualManagementHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/userManualManagement')
                .then(function(result){
                        if(result.status===200){
                            $scope.userManualManagementPage= result.data.pagecontent;
                            $scope.userManualManagementPageContent = $sce.trustAsHtml($scope.userManualManagementPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("aboutKilkariHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("aboutMAHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("changePasswordHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/changePassword')
                .then(function(result){
                        if(result.status===200){
                            $scope.changePasswordPage= result.data.pagecontent;
                            $scope.changePasswordPageContent = $sce.trustAsHtml($scope.changePasswordPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("downloadsHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/downloads')
                .then(function(result){
                        if(result.status===200){
                            $scope.downloadsPage= result.data.pagecontent;
                            $scope.downloadsPageContent = $sce.trustAsHtml($scope.downloadsPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("faqHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("faqAggregateInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("faqGeneralInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("faqLineListingInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("faqLoginInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("faqReportsInfoHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("feedbackFormHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/feedbackForm')
                .then(function(result){
                        if(result.status===200){
                            $scope.feedbackFormPage= result.data.pagecontent;
                            $scope.feedbackFormPageContent = $sce.trustAsHtml($scope.feedbackFormPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("feedbackResponseHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/feedbackResponse')
                .then(function(result){
                        if(result.status===200){
                            $scope.feedbackResponsePage= result.data.pagecontent;
                            $scope.feedbackResponsePageContent = $sce.trustAsHtml($scope.feedbackResponsePage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("profileHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/profile')
                .then(function(result){
                        if(result.status===200){
                            $scope.profilePage= result.data.pagecontent;
                            $scope.profilePageContent = $sce.trustAsHtml($scope.profilePage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userManualHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/userManual')
                .then(function(result){
                        if(result.status===200){
                            $scope.userManualPage= result.data.pagecontent;
                            $scope.userManualPageContent = $sce.trustAsHtml($scope.userManualPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userManualKilkariHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/userManualKilkari')
                .then(function(result){
                        if(result.status===200){
                            $scope.userManualKilkariPage= result.data.pagecontent+result.data.pagecontent2+result.data.pagecontent3;
                            $scope.userManualKilkariPageContent = $sce.trustAsHtml($scope.userManualKilkariPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userManualKilkariAggHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/userManualKilkariAgg')
                .then(function(result){
                        if(result.status===200){
                            $scope.userManualKilkariAggPage= result.data.pagecontent+result.data.pagecontent2+result.data.pagecontent3+result.data.pagecontent4+
                                result.data.pagecontent5+result.data.pagecontent6+result.data.pagecontent7+result.data.pagecontent8+result.data.pagecontent9;
                            $scope.userManualKilkariAggPageContent = $sce.trustAsHtml($scope.userManualKilkariAggPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userManualMobileAcademyHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/userManualMobileAcademy')
                .then(function(result){
                        if(result.status===200){
                            $scope.userManualMobileAcademyPage= result.data.pagecontent;
                            $scope.userManualMobileAcademyPage2= result.data.pagecontent2;
                            $scope.userManualMobileAcademyPage3= result.data.pagecontent3;
                            $scope.userManualMobileAcademyPage4= result.data.pagecontent4;
                            $scope.userManualMobileAcademyPageContent = $sce.trustAsHtml($scope.userManualMobileAcademyPage+$scope.userManualMobileAcademyPage2+$scope.userManualMobileAcademyPage3+$scope.userManualMobileAcademyPage4);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userManualMobileAcademyAggHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/userManualMobileAcademyAgg')
                .then(function(result){
                        if(result.status===200){
                            $scope.userManualMobileAcademyAggPage= result.data.pagecontent;
                            $scope.userManualMobileAcademyAggPage2= result.data.pagecontent2;
                            $scope.userManualMobileAcademyAggPageContent = $sce.trustAsHtml($scope.userManualMobileAcademyAggPage+$scope.userManualMobileAcademyAggPage2);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userManualProfileHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/userManualProfile')
                .then(function(result){
                        if(result.status===200){
                            $scope.userManualProfilePage= result.data.pagecontent;
                            $scope.userManualProfilePageContent = $sce.trustAsHtml($scope.userManualProfilePage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("userManualWebsiteInformationHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/userManualWebsiteInformation')
                .then(function(result){
                        if(result.status===200){
                            $scope.userManualWebsiteInformationPage= result.data.pagecontent;
                            $scope.userManualWebsiteInformationPageContent = $sce.trustAsHtml($scope.userManualWebsiteInformationPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("forgotPasswordHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/forgotPassword')
                .then(function(result){
                        if(result.status===200){
                            $scope.forgotPasswordPage= result.data.pagecontent;
                            $scope.forgotPasswordPageContent = $sce.trustAsHtml($scope.forgotPasswordPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("sitemapHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("contactUsHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

            $http.get(backend_root + 'page/contactUs')
                .then(function(result){
                        if(result.status===200){
                            $scope.ContactUsPage= result.data.pagecontent;
                            $scope.ContactUsPageContent = $sce.trustAsHtml($scope.ContactUsPage);
                        }
                        else {
                            $state.go('login', {});
                        }
                    }, function(error){
                        $state.go('login', {});
                    }
                )

        }])
        .controller("contactUsResponseHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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

        }])
        .controller("helpPageHtPageController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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