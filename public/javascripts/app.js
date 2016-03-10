require(["angular", "bootstrap", "leaflet", "angular-route"], function(){
    var simmapeditorApp = angular.module("simmapeditorApp",['ngRoute']);

    simmapeditorApp.controller("StreetDetailController", function($scope){
        $scope.streetModel = null
        $scope.layer = null;
        $scope.marker = null;
        $scope.newFeature = function(feature, layer, latlng, map) {
            var streetDetails = $("#streetDetails")
            $scope.streetModel = feature;
            $scope.layer = layer;
            streetDetails.offcanvas("show");
            layer.setStyle( {color: "yellow"} );
            if ($scope.marker != null) {L.removeLayer(marker);};
            $scope.marker = L.marker(latlng).addTo(map);
            $scope.$apply();
        };
        $scope.removeFeature = function(map, beforeNew) {
            var streetDetails = $("#streetDetails")
            if (!beforeNew) streetDetails.offcanvas("hide");
            $scope.streetModel = null;
            var layer = $scope.layer;
            if (layer != null) { layer.setStyle( {color: "red"} ); };
            $scope.layer = null;
            var marker = $scope.marker;
            if (marker != null) {map.removeLayer(marker);};
            $scope.marker = null;
            $scope.$apply();
        };
        $scope.updateFeature = function(feature, layer, latlng, map) {
            if ($scope.streetModel == null) {
                $scope.newFeature(feature, layer, latlng, map);
            } else if (feature == null) {
                $scope.removeFeature(map, false);
            } else {
                $scope.removeFeature(map, true);
                $scope.newFeature(feature, layer, latlng, map);
            }
        }
    });
});