(function(){
    var nmsReportsApp = angular
        .module('nmsReports')
        .controller("DownloadsController", ['$scope', '$state', function($scope, $state){
             $scope.downloadList = [
                 {"id":1,"name":"downloads1"},
                 {"id":2,"name":"downloads2"},
                 {"id":3,"name":"downloads3"},
                 {"id":4,"name":"downloads4"},
            ];

        }])
})()