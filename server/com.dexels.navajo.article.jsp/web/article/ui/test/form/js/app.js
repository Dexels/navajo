angular.module('myApp.service',['ngResource']).
    factory('Metadata', function($resource){
        return $resource('/MetadataOutput/data/metadata.json', {},{
            get: { method: 'GET' }
        });
    });

angular.module('myApp',['myApp.service']);

var MetadataController = function($scope,Metadata) {
    $scope.metaData = Metadata.get();
};