var app = angular.module('nmsReports');

app.controller('MainCtrl', function($scope) {
  $scope.name = 'World';
  $scope.test = function(id) {
    console.log('DELETED ' + id);
  }
});

app.service('ConfirmService', function($modal) {
  var service = {};
  service.open = function (text, onOk) {
    var modalInstance = $modal.open({
      templateUrl: 'myModalContent.html',
      controller: 'ModalConfirmCtrl',
      resolve: {
        text: function () {
          return text;
        }
      }
    });

    modalInstance.result.then(function (selectedItem) {
      onOk();
    }, function () {
    });
  };

  return service;
})


app.directive('confirm', function(ConfirmService) {
    return {
        restrict: 'A',
        scope: {
            eventHandler: '&ngClick'
        },
        link: function(scope, element, attrs){
          element.unbind("click");
          element.bind("click", function(e) {
            ConfirmService.open(attrs.confirm, scope.eventHandler);
          });
        }
    }
});


app.controller('ModalConfirmCtrl', function ($scope, $modalInstance, text) {

  $scope.text = text;

  $scope.ok = function () {
    $modalInstance.close(true);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});