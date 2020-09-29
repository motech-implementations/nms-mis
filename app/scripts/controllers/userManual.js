(function(){
	var nmsReportsApp = angular
		.module('nmsReports');

    nmsReportsApp.controller("UserManualController", ['$scope', '$state','$http', '$sce', function($scope, $state, $http, $sce){

            switch($state.current.name){
                case "userManual.kilkari":$scope.active1 = 'kr';break;
                case "userManual.kilkariAggregate":$scope.active1 = 'kr-agg';break;
                case "userManual.mobileAcademy":$scope.active1 = 'ma';break;
                case "userManual.mobileAcademyAggregate":$scope.active1 = 'ma-agg';break;
                case "userManual.userManual_Profile":$scope.active1 = 'pr';break;
                case "userManual.userManual_Management":$scope.active1 = 'um';break;
                default :$scope.active1 = 'wi';break;
            }
            // localStorage.getItem('role') = 'Anonymous';
      var acc =  localStorage.getItem("accessLevel");
      var rn = localStorage.getItem("roleName");

            //
            // if(localStorage.getItem('role') == null ||localStorage.getItem('role') == ''||localStorage.getItem('role') === undefined){
            //     $scope.selectRole = 0;
            //     $scope.selectRoleValue = 'Anonymous';
            //     localStorage.setItem('role', $scope.selectRole);
            // }
            (function () {
            $scope.roles = [
                            {"id": 1, "role" : "National User"},
                            {"id": 2, "role" : "State User"},
                            {"id": 3, "role" : "District User"},
                            {"id": 4, "role" : "Block User"},
                            {"id": 5, "role" : "National Admin"},
                            {"id": 6, "role" : "State Admin"},
                            {"id": 7, "role" : "District Admin"}

                        ];
                // if(localStorage.getItem('role') === undefined) {
                //     $scope.selectRole = 0;
                //     $scope.selectRoleValue = 'Anonymous';
                // } else {
                //     $scope.selectRole = localStorage.getItem('role');
                //     $scope.selectRoleValue = $scope.roles[$scope.selectRole].role;
                // }

                    if(acc==="NATIONAL" && rn ==="ADMIN"){
                        $scope.selectRole = 5;
                        $scope.selectRoleValue = "National Admin";

                    }
                    if(acc==="NATIONAL" && rn ==="USER"){
                        $scope.selectRole = 1;
                        $scope.selectRoleValue = "National User";
                        $scope.roles.splice(4,3);
                    }
                    if(acc==="STATE" && rn ==="ADMIN"){
                        $scope.selectRole = 6;
                        $scope.selectRoleValue = "State Admin";
                        $scope.roles.splice(0,1);
                        $scope.roles.splice(3,1);

                    }
                    if(acc==="STATE" && rn ==="USER"){
                        $scope.selectRole = 2;
                        $scope.selectRoleValue = "State User";
                        $scope.roles.splice(0,1);
                        $scope.roles.splice(3,3);

                    }
                    if(acc==="DISTRICT" && rn ==="ADMIN"){
                        $scope.selectRole = 7;
                        $scope.selectRoleValue = "District Admin";
                        $scope.roles.splice(0,2);
                        $scope.roles.splice(2,2);

                    }
                    if(acc==="DISTRICT" && rn ==="USER"){
                        $scope.selectRole = 3;
                        $scope.selectRoleValue = "District User";
                        $scope.roles.splice(0,2);
                        $scope.roles.splice(2,3);

                    }
                    if(acc==="BLOCK" && rn ==="USER"){
                        $scope.selectRole = 7;
                        $scope.selectRoleValue = "Block User";
                        $scope.roles.splice(0,3);
                        $scope.roles.splice(1,3);

                    }
            }
            )();

            (function () {
                    if(($scope.selectRole < 5 && $state.current.name ==="userManual.userManual_Management") ||
                    ($scope.selectRole < 1 && $state.current.name ==="userManual.kilkari") ||
                    ($scope.selectRole < 1 && $state.current.name ==="userManual.mobileAcademy") ||
                    ($scope.selectRole < 1 && $state.current.name ==="userManual.kilkariAggregate") ||
                    ($scope.selectRole < 1 && $state.current.name ==="userManual.mobileAcademyAggregate")) {
                        $state.go('userManual.websiteInformation');
                        $scope.active1 = 'wi';
                    }
                }
            )();


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

            $scope.func = function (val) {
                if(val === 'wi'){
                    $state.go('userManual.websiteInformation');
				}
				else if(val=== 'kr'){
                	$state.go('userManual.kilkari')
				}
				else if(val=== 'kr-agg'){
                	$state.go('userManual.kilkariAggregate')
				}
                else if(val=== 'ma'){
                    $state.go('userManual.mobileAcademy')
                }
                else if(val=== 'ma-agg'){
                    $state.go('userManual.mobileAcademyAggregate')
                }
                else if(val=== 'um'){
                        $state.go('userManual.userManual_Management');
                }
                else if(val=== 'pr'){
                    $state.go('userManual.userManual_Profile')
                }
            };

            $scope.fnk = function (x) {
                $scope.selectRole = x.id;
                $scope.selectRoleValue = x.role;
                localStorage.setItem('role', $scope.selectRole);
                if(($scope.selectRole < 5 && $state.current.name ==="userManual.userManual_Management") ||
                ($scope.selectRole < 1 && $state.current.name ==="userManual.kilkari") ||
                ($scope.selectRole < 1 && $state.current.name ==="userManual.mobileAcademy") ||
                ($scope.selectRole < 1 && $state.current.name ==="userManual.kilkariAggregate") ||
                ($scope.selectRole < 1 && $state.current.name ==="userManual.mobileAcademyAggregate")) {
                    $scope.active1 = 'wi';
                    $state.go('userManual.websiteInformation');
                }

            }
		}]);
    nmsReportsApp.controller("kilkariController", ['$scope', '$state', 'UserFormFactory','$http', '$sce', function($scope, $state, UserFormFactory,$http,$sce){

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
            /*			UserFormFactory.isLoggedIn()
                        .then(function(result){
                            if(!result.data){
                                $state.go('login', {});
                            }
                        });*/

            $scope.param = '';
            $scope.flag = false;
            $scope.func = function (val) {
                $scope.flag = true;
                $scope.param = val;
            }
        }]);
    nmsReportsApp.controller("kilkariAggregateController", ['$scope', '$state', 'UserFormFactory','$http','$sce', function($scope, $state, UserFormFactory,$http,$sce){

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
            /*			UserFormFactory.isLoggedIn()
                        .then(function(result){
                            if(!result.data){
                                $state.go('login', {});
                            }
                        });*/

            $scope.param = '';
            $scope.flag = false;
            $scope.func = function (val) {
                $scope.flag = true;
                $scope.param = val;
            }
        }]);
    nmsReportsApp.controller("userManual_ManagementController", ['$scope', '$state', 'UserFormFactory', '$http', '$sce', function($scope, $state, UserFormFactory, $http, $sce){

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


    }]);
    nmsReportsApp.controller("mobileAcademyController", ['$scope', '$state', 'UserFormFactory','$http','$sce', function($scope, $state, UserFormFactory,$http,$sce){

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

        /*
                    UserFormFactory.isLoggedIn()
                    .then(function(result){
                        if(!result.data){
                            $state.go('login', {});
                        }
                    })
        */

        /*
                    $scope.mobileAcademyReports = [];

                    UserFormFactory.getReportsMenu()
                    .then(function(result){
                        if(result.data[0].service == "M"){

                                $scope.serviceReport = result.data[0].name;
                                console.log($scope.serviceReport);
                                $scope.mobileAcademyReports = result.data[0].options;

                            }
                        else {
                            $scope.mobileAcademyReports = result.data[1].options;
                        }
                    })

        */

        $scope.param = '';
        $scope.flag = false;
        $scope.func = function (val) {
            $scope.flag = true;
            $scope.param = val;
        }

    }]);
    nmsReportsApp.controller("mobileAcademyAggregateController", ['$scope', '$state', 'UserFormFactory','$http','$sce', function($scope, $state, UserFormFactory, $http, $sce){

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

        $scope.param = '';
        $scope.flag = false;
        $scope.func = function (val) {
            $scope.flag = true;
            $scope.param = val;
        }

    }]);
    nmsReportsApp.controller("userManual_ProfileController", ['$scope', '$state', 'UserFormFactory','$http','$sce', function($scope, $state, UserFormFactory,$http,$sce){

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

    }]);
    nmsReportsApp.controller("websiteInformationController", ['$scope', '$state', 'UserFormFactory','$http','$sce', function($scope, $state, UserFormFactory,$http,$sce){

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

        /*			UserFormFactory.isLoggedIn()
                    .then(function(result){
                        if(!result.data){
                            $state.go('login', {});
                        }
                    })*/

    }]);
})();
