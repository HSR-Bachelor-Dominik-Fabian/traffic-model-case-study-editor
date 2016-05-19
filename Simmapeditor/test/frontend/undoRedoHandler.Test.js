var MyProps = {};
MyProps["rootURL"] = 'http://localhost:9001';
(function() {
    describe("Test ChangesetStorageHandler", function() {
        beforeEach(module('changeLinkModule'));

        var changesetStorageHandler = null;
        var changesetHandler = null;
        var undoRedoHandler = null;
        var $scope = null;
        var testFullModel = null;
        var testFeature = null;

        var expectedUndo = { id: '1', key: 'oneway', value: false };
        var expectedRedo = { id: '1', key: 'oneway', value: true };

        beforeEach(angular.mock.inject(function($rootScope, _$controller_) {
            changesetStorageHandler = new ChangesetStorageHandler();
            changesetStorageHandler.clearLocalChangeset();
            changesetHandler = new ChangesetHandler();
            undoRedoHandler = new UndoRedoHandler();
            undoRedoHandler.clearUndoRedoStack();
            $scope = $rootScope.$new();
            _$controller_('StreetDetailController', { $scope: $scope });

            testFullModel = {"id":9,"name":"ChangesetTest","networkId":1,"userNr":1,"lastModified":1463649230713,
                "link_changeModels":[{"defaultValues":{"id":"1","networkId":1,
                    "quadKey":"1202213002","length":9110.444119989446,"freespeed":33.333333333333336,
                    "capacity":4000.0,"permlanes":2.0,"oneway":false,"modes":"car,ride",
                    "from":"3089852391","to":"24975040","minlevel":10,"lastModified":"2016-04-11",
                    "long1":8.6017313369933390276855789124965667724609375,
                    "lat1":46.7054662357313787879320443607866764068603515625,
                    "long2":8.668947135098921563667317968793213367462158203125,
                    "lat2":46.7681431350288931980685447342693805694580078125},
                    "id":"1","changesetNr":9,"networkId":1,"deleted":false,
                    "quadKey":"1202213002","length":9110.444119989446,"freespeed":33.333333333333336,
                    "capacity":4000.0,"permlanes":4,"oneway":false,"modes":"car,ride",
                    "from":"3089852391","to":"24975040","minlevel":10,"lastModified":"2016-04-11",
                    "long1":8.6017313369933390276855789124965667724609375,
                    "lat1":46.7054662357313787879320443607866764068603515625,
                    "long2":8.668947135098921563667317968793213367462158203125,
                    "lat2":46.7681431350288931980685447342693805694580078125}],
                "node_changeModels":[]};

            testFeature = { geometry: Object({ coordinates: [ [ 8.601731336993339, 46.70546623573138 ],
                [ 8.668947135098922, 46.76814313502889 ] ], type: 'LineString' }), type: 'Feature',
                properties: Object({ modes: 'car,ride', zoomlevel: 10, length: 9110.444119989446,
                    freespeed: 33.333333333333336, permlanes: 4, id: '1', oneway: false,
                    capacity: 4000 }) };
        }));

        it('test is local storage supported', function() {
            expect(undoRedoHandler._isLocalStorageSupported()).toBeTruthy();
        });

        it('test initialization of undoRedo stack', function() {
            undoRedoHandler.initializeUndoRedoStack();

            expect(undoRedoHandler.undoRedoStackExists()).toBeTruthy();

            expect(undoRedoHandler._getUndoStack()).toEqual([]);
            expect(undoRedoHandler._getRedoStack()).toEqual([]);
        });

        it('test addChange', function() {
            var change = { id: '1', key: 'oneway', value: false };

            undoRedoHandler.addChange(change);

            expect(undoRedoHandler._getUndoStack()).toEqual([change]);
            expect(undoRedoHandler._getRedoStack()).toEqual([]);
        });

        it('test undo negative', function() {
            var change = { id: '1', key: 'oneway', value: false };

            undoRedoHandler.addChange(change);

            expect(undoRedoHandler.undo).toThrow(new TypeError('this._getUndoStack is not a function'));
        });

        it('test undo positive', function() {
            changesetStorageHandler.setLocalChangeset(testFullModel);

            $scope.streetModel = testFeature;
            $scope.streetModelDefault = JSON.parse(JSON.stringify(testFeature));
            testFeature.properties.oneway = true;

            changesetStorageHandler.addNewChange(testFeature);

            undoRedoHandler.addChange($scope._getChangeModelForUndoRedoStack());

            expect(undoRedoHandler._getUndoStack()).toEqual([expectedUndo]);
            expect(undoRedoHandler._getRedoStack()).toEqual([]);

            undoRedoHandler.undo();

            expect(undoRedoHandler._getUndoStack()).toEqual([]);
            expect(undoRedoHandler._getRedoStack()).toEqual([expectedRedo]);
        });

        it('test redo negative', function() {
            var change = { id: '1', key: 'oneway', value: false };

            undoRedoHandler.addChange(change);

            expect( undoRedoHandler.redo).toThrow(new TypeError('this._getUndoStack is not a function'));
        });

        it('test redo positive', function() {
            changesetStorageHandler.setLocalChangeset(testFullModel);

            $scope.streetModel = testFeature;
            $scope.streetModelDefault = JSON.parse(JSON.stringify(testFeature));
            testFeature.properties.oneway = true;

            changesetStorageHandler.addNewChange(testFeature);

            undoRedoHandler.addChange($scope._getChangeModelForUndoRedoStack());

            expect(undoRedoHandler._getUndoStack()).toEqual([expectedUndo]);
            expect(undoRedoHandler._getRedoStack()).toEqual([]);

            undoRedoHandler.undo();

            expect(undoRedoHandler._getUndoStack()).toEqual([]);
            expect(undoRedoHandler._getRedoStack()).toEqual([expectedRedo]);

            undoRedoHandler.redo();

            expect(undoRedoHandler._getUndoStack()).toEqual([expectedUndo]);
            expect(undoRedoHandler._getRedoStack()).toEqual([]);
        });
    });
})();