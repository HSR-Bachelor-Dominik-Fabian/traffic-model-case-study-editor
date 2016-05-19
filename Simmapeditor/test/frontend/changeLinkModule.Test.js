var MyProps = {};
MyProps["rootURL"] = 'http://localhost:9001';
(function() {
    describe("Test changeLinkModule", function() {
        beforeEach(module('changeLinkModule'));

        var $controller = null;
        var $scope = null;
        var testModel = null;

        beforeEach(angular.mock.inject(function($rootScope, _$controller_) {
            $scope = $rootScope.$new();
            $controller = _$controller_('StreetDetailController', { $scope: $scope });

            testModel = {
                "geometry":{
                    "coordinates":[
                        [
                            8.601731336993339,
                            46.70546623573138
                        ],
                        [
                            8.668947135098922,
                            46.76814313502889
                        ]
                    ],
                    "type":"LineString"
                },
                "type":"Feature",
                "properties":{
                    "modes":"car,ride",
                    "zoomlevel":10,
                    "length":9110.444119989446,
                    "freespeed":33.333333333333336,
                    "permlanes":2,
                    "id":"1",
                    "oneway":false,
                    "capacity":4000
                }
            };
        }));

        it('test $scope default state', function() {
            expect($scope.streetModel).toBeNull();
            expect($scope.streetModelDefault).toBeNull();
            expect($scope.layer).toBeNull();
            expect($scope.marker).toBeNull();
        });

        it('test getChangeModelForUndoRedoStack', function() {
            var expectedChangeModel = { id: '1', key: 'oneway', value: false };

            var defaultModel = JSON.parse(JSON.stringify(testModel));

            testModel.properties.oneway = true;

            $scope.streetModel = testModel;
            $scope.streetModelDefault = defaultModel;

            var changeModel = $scope._getChangeModelForUndoRedoStack();

            expect(changeModel).toEqual(expectedChangeModel);
        });
    });
})();