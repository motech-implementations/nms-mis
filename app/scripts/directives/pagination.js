(function() {
    var nmsReportsApp = angular.module('nmsReports').directive('pagination', function() {
        return {
            restrict: 'E',
            scope: {
                totalItems: '=',
                itemsPerPage: '=',
                currentPage: '='
            },
            templateUrl: 'views/pagination.html'
        };
    });
})()