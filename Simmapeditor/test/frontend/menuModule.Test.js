var MyProps = {};
MyProps["rootURL"] = 'http://localhost:9001';
(function() {
    describe("Test MenuModule", function() {
        beforeEach(module('menuModule'));

        var $controller = null;
        var $scope = null;
        var changesetStorageHandler = null;
        var changesetHandler = null;
        var undoRedoHandler = null;

        beforeEach(angular.mock.inject(function($rootScope, _$controller_) {
            $scope = $rootScope.$new();
            $controller = _$controller_('StreetMenuController', { $scope: $scope });
            changesetStorageHandler = new ChangesetStorageHandler();
            changesetStorageHandler.clearLocalChangeset();
            changesetHandler = new ChangesetHandler();
            undoRedoHandler = new UndoRedoHandler();
        }));

        it('test $scope default state', function() {
            expect($scope.menuState).toEqual('rootMenu');
            expect($scope.changeCount).toEqual(0);
            expect($scope.isUndoDisabled).toEqual(false);
            expect($scope.isRedoDisabled).toEqual(false);
            expect($scope.isSaveDisabled).toEqual(false);
            expect($scope.changesetsToLoad).toEqual(null);
        });

        it('test isActiveChangeset false', function() {
            changesetHandler._loadEmptyChangesetIntoLocalStorage();

            expect($scope.isActiveChangeset(1)).toEqual(false);
        });

        it('test isActiveChangeset true', function() {
            changesetHandler._loadEmptyChangesetIntoLocalStorage();

            var localChangeset = changesetStorageHandler.getLocalChangeset();
            localChangeset.id = 1;
            changesetStorageHandler._setUpdatedLocalChangeset(localChangeset);

            expect($scope.isActiveChangeset(1)).toEqual(true);
        });

        it('test $scope.$watch of undoStack', function() {
            changesetHandler._loadEmptyChangesetIntoLocalStorage();

            var changeModel = {"id":"1","key":"permlanes","value":4};

            undoRedoHandler.initializeUndoRedoStack();
            $scope.$digest();

            expect($scope.changeCount).toEqual(0);
            expect($scope.isUndoDisabled).toEqual(true);
            expect($scope.isRedoDisabled).toEqual(true);
            expect($scope.isSaveDisabled).toEqual(true);

            undoRedoHandler.addChange(changeModel);
            $scope.$digest();

            expect($scope.changeCount).toEqual(1);
            expect($scope.isUndoDisabled).toEqual(false);
            expect($scope.isRedoDisabled).toEqual(true);
            expect($scope.isSaveDisabled).toEqual(false);
        });

        it('test $scope.$watch of redoStack', function() {
            changesetHandler._loadEmptyChangesetIntoLocalStorage();

            var changeModel = {"id":"1","key":"permlanes","value":4};

            undoRedoHandler.initializeUndoRedoStack();
            $scope.$digest();

            expect($scope.changeCount).toEqual(0);
            expect($scope.isUndoDisabled).toEqual(true);
            expect($scope.isRedoDisabled).toEqual(true);
            expect($scope.isSaveDisabled).toEqual(true);

            undoRedoHandler.addChange(changeModel);
            $scope.$digest();

            expect($scope.changeCount).toEqual(1);
            expect($scope.isUndoDisabled).toEqual(false);
            expect($scope.isRedoDisabled).toEqual(true);
            expect($scope.isSaveDisabled).toEqual(false);

            undoRedoHandler.undo();
            $scope.$digest();

            expect($scope.changeCount).toEqual(0);
            expect($scope.isUndoDisabled).toEqual(true);
            expect($scope.isRedoDisabled).toEqual(false);
            expect($scope.isSaveDisabled).toEqual(true);
        });

        it('test $scope.$watch of menustate', function() {
            expect($scope.menuState).toEqual('rootMenu');
            expect($scope.changesetsToLoad).toEqual(null);

            $scope.menuState = 'loadChangeset';
            $scope.$digest();

            expect($scope.menuState).toEqual('loadChangeset');
            expect($scope.changesetsToLoad).toEqual([]);
        });

    });
})();