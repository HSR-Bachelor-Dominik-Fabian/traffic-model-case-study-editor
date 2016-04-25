(function(){
    var simmapeditorApp = angular.module("simmapeditorApp", []);
    simmapeditorApp.directive("rootmenu", function() {
        return {templateUrl: "/partials/rootmenu"};
    });
    simmapeditorApp.directive("loadchangesetmenu", function() {
        return {templateUrl: "/partials/loadchangesetmenu"};
    });
    simmapeditorApp.value("layerInstance", {instance: null, mapInstance: null});
    simmapeditorApp.directive("simmap", ['layerInstance', function(layerInstance){
       return {
            link: function($scope, element, attrs){
                var baseMap = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    noWrap:true
                });
                var baseMap_de = L.tileLayer('http://{s}.tile.openstreetmap.de/tiles/osmde/{z}/{x}/{y}.png', {
                    noWrap:true
                })
                var OpenStreetMap_BlackAndWhite = L.tileLayer('http://{s}.tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png', {
                    noWrap:true
                });
                var MapBox = L.tileLayer('http://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                    subdomains: 'abcd',
                    id: 'mapbox.streets-basic',
                    accessToken: 'pk.eyJ1IjoiZjNrZWxsZXIiLCJhIjoiY2lsa3VyNjB3MDA3am5ja3FxNHFld3E2NiJ9.A99UzeLIycU2I8jRCPixgg',
                    noWrap:true
                });
                var MapBoxLight = L.tileLayer('http://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                    subdomains: 'abcd',
                    id: 'mapbox.light',
                    accessToken: 'pk.eyJ1IjoiZjNrZWxsZXIiLCJhIjoiY2lsa3VyNjB3MDA3am5ja3FxNHFld3E2NiJ9.A99UzeLIycU2I8jRCPixgg',
                    noWrap:true
                });
                var SenozonLight = L.tileLayer('http://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                    subdomains: 'abcd',
                    id: 'firehollaender.b2cc84a4',
                    accessToken: 'pk.eyJ1IjoiZmlyZWhvbGxhZW5kZXIiLCJhIjoiY2lsdzlmamExMDA5dXY5bTQ2bWl2MTZoNCJ9.Q8uzzgMdko8ZbcA6Kbwzlw',
                    noWrap:true
                });

                var map = new L.Map("map", {center: [46.83, 8.3], zoom: 8, layers: [SenozonLight],
                    attributionControl: false,minZoom: 3, maxZoom: 18, noWrap: true , zoomControl: false, maxBounds: [[-180, -180],[180,180]]});
                layerInstance.mapInstance = map;
                map.on("click", function(){
                    var streetDetails=$("#streetDetails");
                    var scope = angular.element(streetDetails).scope();
                    scope.updateFeature(null, null, null, map);
                    $(".street-active").removeClass("street-active");
                });
                var svg = d3.select(map.getPanes().overlayPane).append("svg"),
                    g = svg.append("g").attr("class", "leaflet-zoom-hide");

                var addGeoJsonTileLayer = function() {
                    var geojsonURL = MyProps["rootURL"] + '/api/quadtile/1/{z}/{x}/{y}';
                    var geojsonTileLayer = new L.TileLayer.GeoJSON(geojsonURL,{
                        clipTiles: false,
                        identified: function(feature){
                            return feature.properties.id;
                        },
                        unique: function (feature) {
                            return feature.properties.zoomlevel;
                        },
                        modified: function (feature) {
                            var storageHandler = new StorageHandler();
                            var changeset = storageHandler.getLocalChangeset();
                            var geoJson = changeset.geoJson;
                            for (var changedFeature in geoJson.features) {
                                if (geoJson.features[changedFeature].properties.id === feature.properties.id) {
                                    return geoJson.features[changedFeature];
                                }
                            }
                            return feature;
                        }
                    },{
                        onEachFeature: onEachFeature
                    });
                    layerInstance.instance = geojsonTileLayer;
                    addOverLay(geojsonTileLayer, "Test Layer");
                };

                var baseLayers = { "Color": baseMap, "Color_DE": baseMap_de,
                    "Black and White": OpenStreetMap_BlackAndWhite, "MapBox": MapBox, "MapBoxLight": MapBoxLight, "SenozonLight": SenozonLight};

                var basicControl = L.control.layers(baseLayers, {}).addTo(map);
                L.control.attribution(null);
                map.addControl(new L.Control.Zoomslider( {position: "bottomright"} ));
                L.control.scale( {position: "bottomleft", imperial: false} ).addTo(map);


                function onEachFeature(feature, layer){
                    if (feature.properties) {
                        layer.setStyle({color: "#6f98a4",opacity: 0.9 , className: "street street_"+feature.properties.zoomlevel});
                        layer.on("click", function(e){
                            $(".street-active").removeClass("street-active");
                            var path = e.target;
                            var container = path._container;
                            $('> path', container).addClass('street-active');
                            var streetDetails = $("#streetDetails");
                            var scope = angular.element(streetDetails).scope();
                            scope.updateFeature(feature, layer, e.latlng, map);
                        });
                    }
                }

                function addOverLay(layer, name) {
                    map.addLayer(layer);
                    basicControl.addOverlay(layer, name);
                }


                var changesetHandler = new ChangesetHandler();
                changesetHandler.initializeChangeset();
                addGeoJsonTileLayer();
            }
       };
    }]);

    simmapeditorApp.controller("StreetMenuController", ['$scope','layerInstance', function($scope, layerInstance) {
        $scope.menuState="rootMenu";
        $scope.changesetsToLoad = null;
        $scope.$watch('menuState', function(newValue){
            $scope.changesetsToLoad = null;
            if(newValue == "loadChangeset"){
                var changeSetHandler = new ChangesetHandler();
                $scope.changesetsToLoad = changeSetHandler.getAllChangesets();
            }
        });
        $scope.onChangesetLoadClicked = function(item){
            var changeSetHandler = new ChangesetHandler();
            changeSetHandler.loadChangesetIntoLocalStorage(item.id);
            /*layerInstance.mapInstance.removeLayer(layerInstance.instance);
            layerInstance.mapInstance.addLayer(layerInstance.instance);*/
            layerInstance.instance.redraw();
        };
    }]);
    simmapeditorApp.controller("StreetDetailController", ['$scope', function($scope) {
        $scope.streetModel = null;
        $scope.layer = null;
        $scope.marker = null;
        var storageHandler = new StorageHandler();

        $scope.changeModel = function() {
            $scope.streetModel.properties.freespeed = parseFloat($scope.streetModel.properties.freespeedCalculated / 3.6)
            storageHandler.addNewChange($scope.streetModel);
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