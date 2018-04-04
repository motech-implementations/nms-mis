(function(){
	var nmsReportsApp = angular
		.module('nmsReports');
		nmsReportsApp.controller("faqController", ['$scope', '$state', function($scope, $state){
            $scope.isCollapsed = true;

            switch($state.current.name){
                case "faq.faqKilkari":$scope.active1 = 'kr';break;
                case "faq.faqMobileAcademy":$scope.active1 = 'ma';break;
                case "faq.faqProfile":$scope.active1 = 'pr';break;
                case "faq.faqUserManagement":$scope.active1 = 'um';break;
                default :$scope.active1 = 'wi';break;
            }

            $scope.func = function (val) {
                if(val == 'wi'){
                    $state.go('faq.faqWebsiteInformation');
                }
                else if(val== 'kr'){
                    $state.go('faq.faqKilkari')
                }
                else if(val== 'ma'){
                    $state.go('faq.faqMobileAcademy')
                }
                else if(val== 'um'){
                    $state.go('faq.faqUserManagement')
                }
                else if(val== 'pr'){
                    $state.go('faq.faqProfile')
                }
            }
		}]);
		nmsReportsApp.controller("faqKilkariController", ['$scope', function($scope){

            $scope.active = true;


            $scope.kilkariFaqs = [
                {"id": 0,
                    "question" : "Kilkari 1",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id": 1,
                    "question" : "Kilkari 2",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id" :2,
                    "question" : "Kilkari 3",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                }
            ];
        }]);
		nmsReportsApp.controller("faqMobileAcademyController", ['$scope', function($scope){

            $scope.active = true;
            $scope.mobileAcademies = [
                {"id": 0,
                    "question" : "Mobile Academy 1",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id": 1,
                    "question" : "Mobile Academy 2",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id" :2,
                    "question" : "Mobile Academy 3",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                }
            ];
        }]);
		nmsReportsApp.controller("faqProfileController", ['$scope', function($scope){

            /*			UserFormFactory.isLoggedIn()
                        .then(function(result){
                            if(!result.data){
                                $state.go('login', {});
                            }
                        })*/

            $scope.active = true;
            $scope.profiles = [
                {"id": 0,
                    "question" : "Profile 1",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id": 1,
                    "question" : "Profile 2",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id" :2,
                    "question" : "Profile 3",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                }
            ];

        }]);
		nmsReportsApp.controller("faqUserManagementController", ['$scope', function($scope){


            $scope.active = true;
            $scope.userMangements = [
                {"id": 0,
                    "question" : "User Management 1",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id": 1,
                    "question" : "User Management 2",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id" :2,
                    "question" : "User Management 3",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                }
            ];
        }]);
		nmsReportsApp.controller("faqWebsiteInformationController",['$scope', function($scope){

            $scope.active = true;
            $scope.websiteInfos = [
                {"id": 0,
                    "question" : "Website Information 1",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id": 1,
                    "question" : "Website Information 2",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                },
                {"id" :2,
                    "question" : "Website Information 3",
                    "answer" : "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. " +
                    "3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. " +
                    "Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch " +
                    "et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. " +
                    "Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth " +
                    "nesciunt you probably haven't heard of them accusamus labore sustainable VHS."
                }
            ];

        }]);
})();
