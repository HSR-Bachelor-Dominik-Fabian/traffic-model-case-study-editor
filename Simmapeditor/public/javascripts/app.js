(function(){
    var simmapeditorApp = angular.module("simmapeditorApp", []);

    simmapeditorApp.controller("StreetDetailController", ['$scope', function($scope) {
        $scope.streetModel = null;
        $scope.layer = null;
        $scope.marker = null;
        var storageHandler = new StorageHandler();

        $scope.changeModel = function() {
            $scope.streetModel.properties.freespeed = parseFloat($scope.streetModel.properties.freespeedCalculated / 3.6)
            storageHandler.addNewChange($scope.streetModel);
        }

        $scope.newFeature = function (feature, layer, latlng, map) {
            var streetDetails = $("#streetDetails");
            $scope.streetModel = feature;

            $scope.streetModel.properties.freespeedCalculated = parseInt($scope.streetModel.properties.freespeed * 3.6);

            $scope.layer = layer;
            streetDetails.offcanvas("show");
            if ($scope.marker != null) {
                L.removeLayer(marker);
            }

            $scope.marker = L.marker(latlng).addTo(map);
            $scope.$apply();
        };
        $scope.removeFeature = function (map, beforeNew) {
            var streetDetails = $("#streetDetails");
            if (!beforeNew) streetDetails.offcanvas("hide");
            $scope.streetModel = null;
            $scope.layer = null;
            var marker = $scope.marker;
            if (marker != null) {
                map.removeLayer(marker);
            }
            $scope.marker = null;
            $scope.$apply();
        };
        $scope.updateFeature = function (feature, layer, latlng, map) {
            if ($scope.streetModel != null) {
                if (feature == null) {
                    $scope.removeFeature(map, false)
                } else {
                    $scope.removeFeature(map, true);
                    $scope.newFeature(feature, layer, latlng, map);
                }
            } else {
                if (feature != null) {
                    $scope.newFeature(feature, layer, latlng, map);
                }
            }
        }
    }]);
})();