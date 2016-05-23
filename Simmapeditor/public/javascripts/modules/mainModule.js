(function(){
    var mainModule = angular.module('mainModule', ['changeLinkModule', 'menuModule']);
    mainModule.value("layerInstance", {instance: null, mapInstance: null});
    mainModule.directive("simmap", ["$rootScope","layerInstance", function($rootScope, layerInstance){
       return {
            scope: true,
            link: function($scope, element, attrs){

                var SenozonLight = L.tileLayer('http://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
                    subdomains: 'abcd',
                    id: 'firehollaender.b2cc84a4',
                    accessToken: 'pk.eyJ1IjoiZmlyZWhvbGxhZW5kZXIiLCJhIjoiY2lsdzlmamExMDA5dXY5bTQ2bWl2MTZoNCJ9.Q8uzzgMdko8ZbcA6Kbwzlw',
                    noWrap:true
                });

                var map = new L.Map("map", {center: [46.83, 8.3], zoom: 8, layers: [SenozonLight],
                    attributionControl: false,minZoom: 3, maxZoom: 18, noWrap: true , zoomControl: false, maxBounds: [[-180, -180],[180,180]]});
                layerInstance.mapInstance = map;

                $('#streetDetails').on('hide.bs.offcanvas', function (e) {
                    $rootScope.$broadcast('updateFeature', {feature: null, layer: null, latlng: null, map: map});
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
                            var storageHandler = new ChangesetStorageHandler();
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
                    addOverLay(geojsonTileLayer, "Link Layer");
                };

                L.control.attribution(null);
                map.addControl(new L.Control.Zoomslider( {position: 'bottomright'} ));
                L.control.scale( {position: 'bottomleft', imperial: false} ).addTo(map);

                function onEachFeature(feature, layer){
                    if (feature.properties) {
                        if (feature.geometry.type !== 'Point') {
                            layer.setStyle({className: 'street street_'+feature.properties.zoomlevel});
                            layer.on('click', function(e){
                                $('.street-active').removeClass('street-active');
                                var path = e.target;
                                var container = path._container;
                                $('> path', container).addClass('street-active');
                                $rootScope.$broadcast('updateFeature', {feature: feature, layer: layer, latlng: e.latlng, map: map});
                            });
                        } else {
                            layer.style = {className: 'point'};
                        }
                    }
                }

                function addOverLay(layer) {
                    map.addLayer(layer);
                }

                var undoRedoHandler = new UndoRedoHandler();
                undoRedoHandler.initializeUndoRedoStack();

                var changesetHandler = new ChangesetHandler();
                changesetHandler.initializeChangeset();
                addGeoJsonTileLayer();
            }
       };
    }]);
})();