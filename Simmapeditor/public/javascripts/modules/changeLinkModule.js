(function(){
    var changeLinkModule = angular.module('changeLinkModule', []);

    changeLinkModule.controller("StreetDetailController", function($scope, $rootScope) {
        $scope.streetModel = null;
        $scope.layer = null;
        $scope.marker = null;
        var storageHandler = new ChangesetStorageHandler();

        $scope.changeModel = function() {
            $scope.streetModel.properties.freespeed = $scope.streetModel.properties.freespeedCalculated / 3.6;
            storageHandler.addNewChange($scope.streetModel);
            $rootScope.$broadcast('addChange');
        };

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
            $scope.streetModel = null;
            $scope.layer = null;
            if (!beforeNew) streetDetails.offcanvas("hide");
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
        };

        $scope.$on('updateFeature', function(event, args) {
            $scope.updateFeature(args.feature, args.layer, args.latlng, args.map);
        });
    });
})();