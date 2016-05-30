(function () {
    var menuModule = angular.module('menuModule', ['mainModule', 'ngMaterial']);

    menuModule.directive('rootmenu', function () {
        return {templateUrl: '/partials/rootmenu'};
    });
    menuModule.directive('loadchangesetmenu', function () {
        return {templateUrl: '/partials/loadchangesetmenu'};
    });
    menuModule.directive('importmenu', function () {
        return {templateUrl: '/partials/importmenu'};
    });

    menuModule.factory("dataService", function () {
        var streetToEdit = {instance: null};
        getStreetToEdit = function () {
            return streetToEdit.instance;
        };
        setStreetToEdit = function (value) {
            streetToEdit.instance = value;
        };
        setFromStreet = function (from) {
            streetToEdit.instance.from = from;
        };
        setToStreet = function (to) {
            streetToEdit.instance.to = to;
        };
        return {
            editMode: false,
            getStreetToEdit: getStreetToEdit,
            setStreetToEdit: setStreetToEdit,
            setFromStreet: setFromStreet,
            setToStreet: setToStreet
        };
    });

    menuModule.controller('StreetMenuController', ['$scope', '$mdDialog', '$mdToast', 'layerInstance', 'dataService',
        function ($scope, $mdDialog, $mdToast, layerInstance, dataService) {
            $scope.menuState = 'rootMenu';
            $scope.changeCount = 0;
            $scope.isUndoDisabled = false;
            $scope.isRedoDisabled = false;
            $scope.isSaveDisabled = false;
            $scope.changesetsToLoad = null;
            $scope.getEditMode = function () {
                return dataService.editMode;
            };
            $scope.getStreetToEdit = function () {
                return dataService.getStreetToEdit();
            };
            $scope.setStreetToEdit = function (value) {
                dataService.setStreetToEdit(value);
            };
            $scope.setFromStreetToEdit = function (from) {
                dataService.setFromStreet(from);
            };
            $scope.setToStreetToEdit = function (to) {
                dataService.setToStreet(to);
            };
            $scope.$watch('getEditMode()', function (newVal, oldVal) {
                if (newVal) {
                    $('#editButton').addClass('editModeActive');
                    if (layerInstance.editInstance != null && layerInstance.instance != null) {
                        layerInstance.mapInstance.removeLayer(layerInstance.instance);
                        layerInstance.mapInstance.addLayer(layerInstance.editInstance);
                        layerInstance.editInstance.geojsonLayer.eachLayer(function (layer) {
                            if (typeof layer._path != 'undefined') {
                                layer._path.id = layer.feature.properties.id;
                            } else {
                                layer.eachLayer(function (layer2) {
                                    layer2._path.id = layer.feature.properties.id;
                                });
                            }
                        });
                        layerInstance.instance.redraw();
                        layerInstance.editInstance.redraw();
                    }
                } else {
                    $('#editButton').removeClass('editModeActive');
                    if (layerInstance.editInstance != null && layerInstance.instance != null) {
                        layerInstance.mapInstance.removeLayer(layerInstance.editInstance);
                        layerInstance.mapInstance.addLayer(layerInstance.instance);
                        layerInstance.instance.redraw();
                        layerInstance.editInstance.redraw();
                    }
                }
            });
            $scope.$watch('getStreetToEdit().from'
                , function (newVal, oldVal) {
                    if (oldVal != null) {
                        $('.node_' + oldVal.properties.id).removeClass('point-active');
                    }
                    if (newVal != null) {
                        $('.node_' + newVal.properties.id).addClass('point-active');
                    }
                });
            $scope.$watch('getStreetToEdit().to', function (newVal, oldVal) {
                if (oldVal != null) {
                    $('.node_' + oldVal.properties.id).removeClass('point-active');
                }
                if (newVal != null) {
                    $('.node_' + newVal.properties.id).addClass('point-active');
                }
            });

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
                    $scope._fillOpenChangeset();
                }
            });
            $scope.isActiveChangeset = function (id) {
                var changesetStorageHandler = new ChangesetStorageHandler();
                var changeset = changesetStorageHandler.getLocalChangeset();
                return changeset.id === id;
            };
            $scope._fillOpenChangeset = function () {
                var changeSetHandler = new ChangesetHandler();
                $scope.changesetsToLoad = changeSetHandler.getAllChangesets(showMessageDialog);
            };
            $scope.onChangesetLoadClicked = function (item) {
                var changesetHandler = new ChangesetHandler();
                changesetHandler.loadChangesetIntoLocalStorage(item.id, showMessageDialog);
                layerInstance.instance.redraw();
            };
            $scope.onChangesetDeleteClicked = function (item) {
                var deleteChangesetDialog = $mdDialog.confirm()
                    .title('Löschen?')
                    .textContent('Wollen Sie das Changeset "' + item.name + '" löschen?')
                    .openFrom('#deleteButtonChangeset' + item.id)
                    .closeTo('#saveButton')
                    .ok('Löschen')
                    .cancel('Abbrechen');
                $mdDialog.show(deleteChangesetDialog).then(function () {
                    var changesetHandler = new ChangesetHandler();
                    var success = changesetHandler.deleteChangeset(item.id, showMessageDialog);
                    if (success) {
                        showMessageDialog('Changeset "' + item.name + '" wurde erfolgreich gelöscht.');
                        layerInstance.instance.redraw();
                        $scope._fillOpenChangeset();
                    }
                    else {
                        showMessageDialog('Fehler beim löschen von Changeset "' + item.name + '".');
                    }
                }, function () {

                });

            };

            $scope.onChangesetSaveClicked = function () {
                var changeSetHandler = new ChangesetHandler();
                var changesetStorageHandler = new ChangesetStorageHandler();
                var changeset = changesetStorageHandler.getLocalChangeset();
                if (changeset.name == null) {
                    showNameDialog(changeset, changesetStorageHandler, changeSetHandler);
                } else {
                    changeSetHandler.saveChangeSet(showMessageDialog);
                }
            };

            $scope.onSaveNewStreetClicked = function () {
                //TODO: feature direkt an add change übergeben
                var changesetStorageHandler = new ChangesetStorageHandler();
                var undoRedoHandler = new UndoRedoHandler();
                var change = dataService.getStreetToEdit();
                var link = change.link;
                var from = change.from;
                var to = change.to;
                var changed = false;
                var changeModel = {id: link.properties.id, key: "link_changed", value: {}};
                if(link.properties.from !== from.properties.id){
                    changeModel.value.from = link.properties.from;
                    changeModel.value.long1 = link.geometry.coordinates[0][0];
                    changeModel.value.lat1 = link.geometry.coordinates[0][1];
                    link.properties.from = from.properties.id;
                    link.properties.lat1 = from.geometry.coordinates[1];
                    link.properties.long1 = from.geometry.coordinates[0];
                    changed = true;
                }
                if(link.properties.to !== to.properties.id){
                    changeModel.value.to = link.properties.to;
                    changeModel.value.long2 = link.geometry.coordinates[1][0];
                    changeModel.value.lat2 = link.geometry.coordinates[1][1];
                    link.properties.to = to.properties.id;
                    link.properties.lat2 = to.geometry.coordinates[1];
                    link.properties.long2 = to.geometry.coordinates[0];
                    changed = true;
                }
                if(changed){
                    changeModel.value.coordinates = link.geometry.coordinates;
                    link.geometry.coordinates = [from.geometry, to.geometry];
                    changesetStorageHandler.addNewChange(link);
                    undoRedoHandler.addChange(changeModel);
                    layerInstance.editInstance.redraw();
                }
                dataService.setStreetToEdit(null);

                showMessageDialog('Strasse gespeichert.')
            };

            $scope.onNewChangesetClicked = function () {
                var changesetHandler = new ChangesetHandler();
                changesetHandler._loadEmptyChangesetIntoLocalStorage();
                showMessageDialog('Leeres Changeset wurde erstellt.');
            };

            $scope.convertPropsToArray = function (propsString) {
                return propsString.split(',');
            };

            $scope.onEditClicked = function () {
                if (dataService.editMode) {
                    dataService.editMode = false;
                    showMessageDialog('Sie befinden sich wieder im normalen Modus.');
                } else {
                    dataService.editMode = true;
                    showMessageDialog('Sie haben nun in den "Strassen Editieren" Modus gewechselt.');
                }
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

            $scope.onAddStreetClicked = function () {
                dataService.setStreetToEdit({from: null, to: null, link: null});
            };

            $scope.onUndoClicked = function () {
                var undoRedoHandler = new UndoRedoHandler();
                undoRedoHandler.undo();
                layerInstance.instance.redraw();
                layerInstance.editInstance.redraw();
            };

            $scope.onRedoClicked = function () {
                var undoRedoHandler = new UndoRedoHandler();
                undoRedoHandler.redo();
                layerInstance.instance.redraw();
                layerInstance.editInstance.redraw();
            };
        }]);
})();