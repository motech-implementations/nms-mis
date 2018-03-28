(function(){
    var nmsReportsApp = angular
        .module('nmsReports')
        .directive('Downloads', function() {
            return {
                restrict: 'AC',
                templateUrl: '../views/downloads.html',
            };
        })
})()