var MyProps = {};
MyProps["rootURL"] = 'http://localhost:9001';
(function () {
    describe("Test ChangesetStorageHandler", function () {

        var changesetStorageHandler = null;
        var changesetHandler = null;

        beforeEach(angular.mock.inject(function () {
            changesetStorageHandler = new ChangesetStorageHandler();
            changesetStorageHandler.clearLocalChangeset();
            changesetHandler = new ChangesetHandler();
        }));

        it('test if local storage is supported', function () {
            expect(changesetStorageHandler._isLocalStorageSupported()).toEqual(true);
        });

        it('test clear changeset', function () {
            changesetHandler._loadEmptyChangesetIntoLocalStorage();
            var changeset = changesetStorageHandler.getLocalChangeset();

            expect(changeset).not.toBeNull();

            changesetStorageHandler.clearLocalChangeset();
            changeset = changesetStorageHandler.getLocalChangeset();

            expect(changeset).toBeNull();
        });

        it('test if local changeset exists', function () {
            expect(changesetStorageHandler.localChangeSetExists()).toEqual(false);

            changesetHandler._loadEmptyChangesetIntoLocalStorage();

            expect(changesetStorageHandler.localChangeSetExists()).toEqual(true);
        });

        it('test convert model to geoJson feature', function () {
            var model = {
                "id": "1", "networkId": 1, "quadKey": "120221323002221",
                "length": 285.35880418880106, "freespeed": 16.666666666666668, "capacity": 1000.0,
                "permlanes": 1.0, "oneway": false, "modes": "car,ride", "from": "21493748",
                "to": "21225847", "minlevel": 13, "lastModified": "2016-04-11",
                "long1": 9.1554249332249053594523502397350966930389404296875,
                "lat1": 45.4731550542789051405634381808340549468994140625,
                "long2": 9.155258933244912356030908995307981967926025390625,
                "lat2": 45.4705969543135921639986918307840824127197265625
            };

            var expcetedGeoJsonFeature = {
                geometry: Object({
                    coordinates: [[9.155424933224905, 45.473155054278905],
                        [9.155258933244912, 45.47059695431359]], type: 'LineString'
                }), type: 'Feature',
                properties: Object({
                    modes: 'car,ride', zoomlevel: 13, length: 285.35880418880106,
                    freespeed: 16.666666666666668, permlanes: 1, id: '1', oneway: false,
                    capacity: 1000
                })
            };

            var actualGeoJsonFeature = changesetStorageHandler._convertModelToGeoJsonFeature(model);

            expect(actualGeoJsonFeature).toEqual(expcetedGeoJsonFeature);
        });

        it('test convert full model to GeoJson', function () {
            var fullModel = {
                "id": 9, "name": "ChangesetTest", "networkId": 1, "userNr": 1, "lastModified": 1463649230713,
                "link_changeModels": [{
                    "defaultValues": {
                        "id": "31078720", "networkId": 1,
                        "quadKey": "1202213002", "length": 9110.444119989446, "freespeed": 33.333333333333336,
                        "capacity": 4000.0, "permlanes": 2.0, "oneway": false, "modes": "car,ride",
                        "from": "3089852391", "to": "24975040", "minlevel": 10, "lastModified": "2016-04-11",
                        "long1": 8.6017313369933390276855789124965667724609375,
                        "lat1": 46.7054662357313787879320443607866764068603515625,
                        "long2": 8.668947135098921563667317968793213367462158203125,
                        "lat2": 46.7681431350288931980685447342693805694580078125
                    },
                    "id": "31078720", "changesetNr": 9, "networkId": 1, "deleted": false,
                    "quadKey": "1202213002", "length": 9110.444119989446, "freespeed": 33.333333333333336,
                    "capacity": 4000.0, "permlanes": 4, "oneway": false, "modes": "car,ride",
                    "from": "3089852391", "to": "24975040", "minlevel": 10, "lastModified": "2016-04-11",
                    "long1": 8.6017313369933390276855789124965667724609375,
                    "lat1": 46.7054662357313787879320443607866764068603515625,
                    "long2": 8.668947135098921563667317968793213367462158203125,
                    "lat2": 46.7681431350288931980685447342693805694580078125
                }],
                "node_changeModels": []
            }

            var expectedGeoJson = {
                features: [Object({
                    geometry: Object({
                        coordinates: [[8.601731336993339, 46.70546623573138],
                            [8.668947135098922, 46.76814313502889]], type: 'LineString'
                    }), type: 'Feature',
                    properties: Object({
                        modes: 'car,ride', zoomlevel: 10, length: 9110.444119989446,
                        freespeed: 33.333333333333336, permlanes: 4, id: '31078720', oneway: false,
                        capacity: 4000
                    })
                })], type: 'FeatureCollection'
            };

            var actualGeoJson = changesetStorageHandler._convertFullModelToGeoJson(fullModel);

            expect(actualGeoJson).toEqual(expectedGeoJson);
        });
    });
})();