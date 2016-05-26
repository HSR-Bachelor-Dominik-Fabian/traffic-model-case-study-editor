(function () {
    var mainModule = angular.module('mainModule', ['changeLinkModule', 'menuModule', 'ngMaterial']);
    mainModule.value("layerInstance", {instance: null, mapInstance: null, editInstance: null});
    mainModule.directive("simmap", ["$rootScope", "layerInstance", "$mdToast", "dataService", function ($rootScope, layerInstance, $mdToast, dataService) {
        return {
            scope: true,
            link: function ($scope, element, attrs) {

                var SenozonLight = L.tileLayer('http://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                    subdomains: 'abcd',
                    id: 'firehollaender.b2cc84a4',
                    accessToken: 'pk.eyJ1IjoiZmlyZWhvbGxhZW5kZXIiLCJhIjoiY2lsdzlmamExMDA5dXY5bTQ2bWl2MTZoNCJ9.Q8uzzgMdko8ZbcA6Kbwzlw',
                    noWrap: true
                });

                var map = new L.Map("map", {
                    center: [46.83, 8.3],
                    zoom: 8,
                    layers: [SenozonLight],
                    attributionControl: false,
                    minZoom: 3,
                    maxZoom: 18,
                    noWrap: true,
                    zoomControl: false,
                    maxBounds: [[-180, -180], [180, 180]]
                });
                layerInstance.mapInstance = map;

                $('#streetDetails').on('hide.bs.offcanvas', function (e) {
                    $rootScope.$broadcast('updateFeature', {feature: null, layer: null, latlng: null, map: map});
                    $(".street-active").removeClass("street-active");
                });
                /*
                 map.on('zoomend', function(e) {
                 var currentZoom = map.getZoom();
                 if (currentZoom > 14) {
                 $('.point-hidden').removeClass('point-hidden');
                 } else {
                 $('.point').addClass('point-hidden');
                 }
                 });
                 */
                var svg = d3.select(map.getPanes().overlayPane).append("svg"),
                    g = svg.append("g").attr("class", "leaflet-zoom-hide");

                var addGeoJsonTileLayer = function () {
                    var geojsonURL = MyProps["rootURL"] + '/api/quadtile/1/{z}/{x}/{y}';
                    var geojsonTileLayer = new L.TileLayer.GeoJSON(geojsonURL, {
                        clipTiles: false,
                        identified: function (feature) {
                            return feature.properties.id;
                        },
                        unique: function (feature) {
                            return feature.properties.zoomlevel;
                        },
                        modified: function (feature) {
                            var storageHandler = new ChangesetStorageHandler();
                            var changeset = storageHandler.getLocalChangeset();
                            var geoJson = changeset.geoJson;
                            for (var changedFeature in geoJson.features) {
                                if (geoJson.features[changedFeature].properties.id === feature.properties.id) {
                                    var result = geoJson.features[changedFeature];
                                    result.isModified = true;
                                    return result;
                                }
                            }
                            feature.isModified = false;
                            return feature;
                        }
                    }, {
                        onEachFeature: onEachFeature
                    });
                    layerInstance.instance = geojsonTileLayer;
                    map.addLayer(geojsonTileLayer);
                };

                var addEditGeoJsonTileLayer = function () {
                    var geojsonURLEdit = MyProps["rootURL"] + '/api/quadtile/edit/1/{z}/{x}/{y}';
                    var geojsonTileLayerEdit = new L.TileLayer.GeoJSON(geojsonURLEdit, {
                        clipTiles: false,
                        identified: function (feature) {
                            return feature.properties.id;
                        },
                        unique: function (feature) {
                            return feature.properties.zoomlevel;
                        },
                        modified: function (feature) {
                            var storageHandler = new ChangesetStorageHandler();
                            var changeset = storageHandler.getLocalChangeset();
                            var geoJson = changeset.geoJson;
                            for (var changedFeature in geoJson.features) {
                                if (geoJson.features[changedFeature].properties.id === feature.properties.id) {
                                    var result = geoJson.features[changedFeature];
                                    result.isModified = true;
                                    return result;
                                }
                            }
                            return feature;
                        }
                    }, {
                        onEachFeature: onEachFeatureEdit,
                        pointToLayer: function (feature, latlng){
                            return L.circleMarker(latlng, { className: 'point', radius: 3, fillOpacity: 0});
                        }
                    });
                    layerInstance.editInstance = geojsonTileLayerEdit;
                };

                L.control.attribution(null);
                map.addControl(new L.Control.Zoomslider({position: 'bottomright'}));
                L.control.scale({position: 'bottomleft', imperial: false}).addTo(map);

                function onEachFeature(feature, layer) {
                    if (feature.properties && feature.geometry && feature.geometry.type !== 'Point') {
                        if (feature.isModified) {
                            layer.setStyle({className: 'street street-edited street_'+feature.properties.zoomlevel});
                        } else {
                            layer.setStyle({className: 'street street_'+feature.properties.zoomlevel});
                        }
                        layer.on('click', function(e){
                            $('.street-active').removeClass('street-active');
                            var path = e.target;
                            var container = path._container;
                            $('> path', container).addClass('street-active');
                            $rootScope.$broadcast('updateFeature', {
                                feature: feature,
                                layer: layer,
                                latlng: e.latlng,
                                map: map
                            });
                        });
                    }
                }

                function onEachFeatureEdit(feature, layer) {
                    if (feature.properties) {
                        if (feature.geometry.type !== 'Point') {
                            if (feature.isModified) {
                                layer.setStyle({className: 'street-edit street-edited street_'+feature.properties.zoomlevel, clickable: false});
                            } else {
                                layer.setStyle({className: 'street-edit street_'+feature.properties.zoomlevel, clickable: false});
                            }
                        } else {
                            layer.setStyle({className: 'node_' + feature.properties.id});
                            layer.on('click', function (e) {
                                if (dataService.editMode && dataService.getStreetToEdit() != null) {
                                    var path = e.target;
                                    var container = path._container;
                                    var id = path.feature.properties.id;
                                    if (dataService.getStreetToEdit().from == null) {
                                        $rootScope.$apply(function () {
                                            dataService.setFromStreet(id);
                                        });
                                    }
                                    else {
                                        $rootScope.$apply(function () {
                                            dataService.setToStreet(id);
                                        });
                                    }

                                }
                            });
                        }
                    }
                }

                var showMessageDialog = function (message) {
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent(message)
                            .position('top right')
                            .hideDelay(5000)
                    );
                };

                var undoRedoHandler = new UndoRedoHandler();
                undoRedoHandler.initializeUndoRedoStack();

                var changesetHandler = new ChangesetHandler();
                changesetHandler.initializeChangeset(showMessageDialog);
                addGeoJsonTileLayer();
                addEditGeoJsonTileLayer();
            }
        };
    }]);
})();