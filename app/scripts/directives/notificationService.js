
angular.module('nmsReports')
  .factory('etlNotification', ['$http', function($http) {
    var service = {};

    service.getNotifications = function() {
      return $http.get(backend_root + 'nms/user/etlNotification');
      // Adjust the URL path 'etl/notifications' as per your backend API endpoint
    };

    return service;
  }]);


