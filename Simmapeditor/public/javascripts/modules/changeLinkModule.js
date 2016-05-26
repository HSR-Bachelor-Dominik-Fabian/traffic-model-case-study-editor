(function(){
    var changeLinkModule = angular.module('changeLinkModule', []);

    changeLinkModule.directive('ngModelOnblur', function() {
        return {
            restrict: 'A',
            require: 'ngModel',
            priority: 1,
            link: function (scope, elm, attr, ngModelCtrl) {
                if (attr.type === 'radio' || attr.type === 'checkbox') return;

                elm.unbind('input').unbind('keydown').unbind('change');
                elm.bind('blur', function () {
                    scope.$apply(function () {
                        ngModelCtrl.$setViewValue(elm.val());
                    });
                });
            }
        };
    });

    changeLinkModule.controller("StreetDetailController", function($scope) {
        $scope.streetModel = null;
        $scope.streetModelDefault = null;
        $scope.layer = null;
        $scope.marker = null;
        var changesetStorageHandler = new ChangesetStorageHandler();
        var undoRedoHandler = new UndoRedoHandler();

        $scope.changeModel = function() {
            $scope.streetModel.properties.freespeed = $scope.streetModel.properties.freespeedCalculated / 3.6;
            changesetStorageHandler.addNewChange($scope.streetModel);
            undoRedoHandler.addChange($scope._getChangeModelForUndoRedoStack());
            $scope.streetModelDefault = JSON.parse(JSON.stringify($scope.streetModel));
            var path = $scope.layer._path;
            $(path).addClass('street-edited');
        };

        $scope._getChangeModelForUndoRedoStack = function() {
            var changeModel = {};
            changeModel.id = $scope.streetModel.properties.id;
            for (var key in $scope.streetModel.properties) {
                if (key.indexOf('Calculated') === -1) {
                    if ($scope.streetModel.properties[key] !== $scope.streetModelDefault.properties[key]) {
                        changeModel.key = key;
                        changeModel.value = $scope.streetModelDefault.properties[key];
                        break;
                    }
                }
            }
            return changeModel;
        };

        $scope.newFeature = function (feature, layer, latlng, map) {
            var streetDetails = $("#streetDetails");
            $scope.streetModel = feature;
            $scope.streetModelDefault = JSON.parse(JSON.stringify(feature));

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
            $scope.streetModelDefault = null;
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