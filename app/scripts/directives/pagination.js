(function() {
    var nmsReportsApp = angular.module('nmsReports').directive('pagination', function() {
        return {
            restrict: 'E',
            scope: {
                totalItems: '=',
                itemsPerPage: '=',
                currentPage: '='
            },
            templateUrl: 'htpagesmis/pagination.html'
        };
    });
})()