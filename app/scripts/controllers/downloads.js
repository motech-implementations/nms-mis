(function(){
    var nmsReportsApp = angular
        .module('nmsReports')
        .controller("DownloadsController", ['$scope', '$state', '$http', '$sce', function($scope, $state, $http, $sce){

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


            $scope.downloadList = [
                 {"id":1,"name":"downloads1"},
                 {"id":2,"name":"downloads2"},
                 {"id":3,"name":"downloads3"},
                 {"id":4,"name":"downloads4"},
            ];

        }])
})()