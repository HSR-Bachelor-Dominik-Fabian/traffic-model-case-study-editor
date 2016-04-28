(function () {
    var menuModule = angular.module('menuModule', ['mainModule', 'ngMaterial']);

    menuModule.directive('rootmenu', function () {
        return {templateUrl: '/partials/rootmenu'};
    });
    menuModule.directive('loadchangesetmenu', function () {
        return {templateUrl: '/partials/loadchangesetmenu'};
    });

    menuModule.controller('StreetMenuController', ['$scope', '$mdDialog', '$mdToast', 'layerInstance',
            function ($scope, $mdDialog, $mdToast, layerInstance) {
        $scope.menuState = 'rootMenu';
        $scope.changeCount = 0;
        $scope.isUndoDisabled = false;
        $scope.isRedoDisabled = false;
        $scope.isSaveDisabled = false;
        $scope.changesetsToLoad = null;

        $scope.$watch(function () {
            return sessionStorage.getItem('undoStack');
        }, function (newVal, oldVal) {
            $scope.changeCount = JSON.parse(newVal).length;
            if ($scope.changeCount === 0) {
                $("#undoButton").addClass('deactivatedButton');
                $("#saveButton").addClass('deactivatedButton');
                $scope.isUndoDisabled = true;
                $scope.isSaveDisabled = true;
            } else {
                $("#undoButton").removeClass('deactivatedButton');
                $("#saveButton").removeClass('deactivatedButton');
                $scope.isUndoDisabled = false;
                $scope.isSaveDisabled = false;
            }
        }, true);

        $scope.$watch(function () {
            return sessionStorage.getItem('redoStack');
        }, function (newVal, oldVal) {
            var redoStack = JSON.parse(newVal);
            if (redoStack.length === 0) {
                $("#redoButton").addClass('deactivatedButton');
                $scope.isRedoDisabled = true;
            } else {
                $("#redoButton").removeClass('deactivatedButton');
                $scope.isRedoDisabled = false;
            }
        }, true);

        $scope.$watch('menuState', function (newValue) {
            $scope.changesetsToLoad = null;
            if (newValue === 'loadChangeset') {
                var changeSetHandler = new ChangesetHandler();
                $scope.changesetsToLoad = changeSetHandler.getAllChangesets();
            }
        });

        $scope.onChangesetLoadClicked = function (item) {
            var changeSetHandler = new ChangesetHandler();
            changeSetHandler.loadChangesetIntoLocalStorage(item.id);
            layerInstance.instance.redraw();
        };

        $scope.onChangesetSaveClicked = function () {
            var changeSetHandler = new ChangesetHandler();
            var changesetStorageHandler = new ChangesetStorageHandler();
            var changeset = changesetStorageHandler.getLocalChangeset();
            if (changeset.name == null) {
                showNameDialog(changeset, changesetStorageHandler, changeSetHandler);
            } else {
                changeSetHandler.saveChangeSet();
                showMessageDialog('Changeset wurde erfolgreich gespeichert.');
            }
        };

        $scope.onNewChangesetClicked = function () {
            var changesetHandler = new ChangesetHandler();
            changesetHandler._loadEmptyChangesetIntoLocalStorage();
            showMessageDialog('Leeres Changeset wurde erstellt.');
        };

        var showNameDialog = function (changeset, changesetStorageHandler, changeSetHandler) {
            var nameNeededDialog = $mdDialog.prompt()
                .title('Ihr Changeset hat noch keinen Namen.')
                .textContent('Geben Sie hier einen Namen für Ihr Changeset an.')
                .placeholder('Changeset Name')
                .ariaLabel('Changeset Name')
                .openFrom('#saveButton')
                .closeTo('#saveButton')
                .ok('Changeset speichern')
                .cancel('Abbrechen');
            $mdDialog.show(nameNeededDialog).then(function (result) {
                changeset.name = result;
                changesetStorageHandler._setUpdatedLocalChangeset(changeset);
                changeSetHandler.saveChangeSet();
                showMessageDialog('Changeset \"' + result + '\" wurde erfolgreich gespeichert.');
            }, function () {
                $("#saveError").show();
                $("#saveError").fadeOut(5000);
            });
        };

        var showMessageDialog = function (message) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(message)
                    .position('top right')
                    .hideDelay(5000)
            );
        };

        $scope.onUndoClicked = function () {
            var undoRedoHandler = new UndoRedoHandler();
            undoRedoHandler.undo();
            layerInstance.instance.redraw();
        };

        $scope.onRedoClicked = function () {
            var undoRedoHandler = new UndoRedoHandler();
            undoRedoHandler.redo();
            layerInstance.instance.redraw();
        };
    }]);
})();