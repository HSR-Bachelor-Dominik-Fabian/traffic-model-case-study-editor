(function(){
    var mainModule = angular.module('mainModule', ['changeLinkModule', 'menuModule']);
    mainModule.value("layerInstance", {instance: null, mapInstance: null});
    mainModule.directive("simmap", ["$rootScope","layerInstance", function($rootScope, layerInstance){
       return {
            scope: true,
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
                            $rootScope.$broadcast('updateFeature', {feature: feature, layer: layer, latlng: e.latlng, map: map});
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
})();