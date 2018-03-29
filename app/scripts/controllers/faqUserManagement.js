(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.controller("faqUserManagementController", ['$scope', function($scope){


			$scope.active = true;
            $scope.userMangements = [
                {"id": 0,
                    "question" : "User Management 1",
                    "answer" : "red"
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
                    "answer" : "black"
                }
            ];
		}])
})();

