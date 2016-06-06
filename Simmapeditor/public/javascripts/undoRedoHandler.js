function UndoRedoHandler() {

    this._isLocalStorageSupported = function () {
        return typeof(Storage) !== "undefined";
    };

    this.initializeUndoRedoStack = function () {
        if (this._isLocalStorageSupported() && !this.undoRedoStackExists()) {
            var undoStack = [];
            var redoStack = [];
            sessionStorage.setItem('undoStack', JSON.stringify(undoStack));
            sessionStorage.setItem('redoStack', JSON.stringify(redoStack));
        }
    };

    this.clearUndoRedoStack = function () {
        this._setRedoStack([]);
        this._setUndoStack([]);
    };

    this.addChange = function (changeModel) {
        this._setRedoStack([]);
        var undoStack = this._getUndoStack();
        undoStack.push(changeModel);
        this._setUndoStack(undoStack);
    };

    this.cleanUpChangeset = function(changeset) {
        var undoStack = this._getUndoStack();
        for (var index in changeset.link_changeModels) {
            var linkChangeModel = changeset.link_changeModels[index];
            var linkIdInUndoStack = false;
            for (var undoIndex in undoStack) {
                var undoEntry = undoStack[undoIndex];
                if (linkChangeModel.id === undoEntry.id) {
                    linkIdInUndoStack = true;
                }
            }
            if (!linkIdInUndoStack) {
                for (var featureIndex in changeset.geoJson.features) {
                    var feature = changeset.geoJson.features[featureIndex];
                    if (feature.properties.id === linkChangeModel.id) {
                        changeset.geoJson.features.splice(featureIndex, 1);
                    }
                }
                changeset.link_changeModels.splice(index, 1);
            }
        }
        return changeset;
    };

    this.undo = function () {
        var undoStack = this._getUndoStack();
        var redoStack = this._getRedoStack();
        var undoEntry = undoStack.pop();
        if (!(undoEntry !== undefined && undoEntry !== null)) {
        } else {
            var redoEntry = {value: {}};
            redoEntry.id = undoEntry.id;
            redoEntry.key = undoEntry.key;
            var changesetStorageHandler = new ChangesetStorageHandler();
            var changeset = changesetStorageHandler.getLocalChangeset();
            for (var index in changeset.link_changeModels) {
                var linkChangeModel = changeset.link_changeModels[index];
                if (undoEntry.key === "link_changed" && linkChangeModel.id === undoEntry.id) {
                    if (undoEntry.value.from !== undefined) {
                        redoEntry.value.from = linkChangeModel.from;
                        linkChangeModel.from = undoEntry.value.from;
                    }
                    if (undoEntry.value.to !== undefined) {
                        redoEntry.value.to = linkChangeModel.to;
                        linkChangeModel.to = undoEntry.value.to;
                    }
                    if (undoEntry.value.long1 !== undefined) {
                        redoEntry.value.long1 = linkChangeModel.long1;
                        linkChangeModel.long1 = undoEntry.value.long1;
                    }
                    if (undoEntry.value.lat1 !== undefined) {
                        redoEntry.value.lat1 = linkChangeModel.lat1;
                        linkChangeModel.lat1 = undoEntry.value.lat1;
                    }
                    if (undoEntry.value.long2 !== undefined) {
                        redoEntry.value.long2 = linkChangeModel.long2;
                        linkChangeModel.long2 = undoEntry.value.long2;
                    }
                    if (undoEntry.value.lat2 !== undefined) {
                        redoEntry.value.lat2 = linkChangeModel.lat2;
                        linkChangeModel.lat2 = undoEntry.value.lat2;
                    }
                }
                else if (linkChangeModel.id === undoEntry.id) {
                    redoEntry.value = linkChangeModel[undoEntry.key];
                    linkChangeModel[undoEntry.key] = undoEntry.value;
                }
            }
            for (var index in changeset.geoJson.features) {
                var geoJsonFeature = changeset.geoJson.features[index];
                if (geoJsonFeature.properties.id === undoEntry.id) {
                    if(undoEntry.key === "link_changed" && undoEntry.value.coordinates !== undefined){
                        redoEntry.value.coordinates = geoJsonFeature.geometry.coordinates;
                        geoJsonFeature.geometry.coordinates = undoEntry.value.coordinates;
                        if (undoEntry.value.from !== undefined) {
                            geoJsonFeature.properties.from = undoEntry.value.from;
                        }
                        if (undoEntry.value.to !== undefined) {
                            geoJsonFeature.properties.to = undoEntry.value.to;
                        }
                    }else {
                        geoJsonFeature.properties[undoEntry.key] = undoEntry.value;
                    }
                }
            }
            redoStack.push(redoEntry);
            this._setUndoStack(undoStack);
            this._setRedoStack(redoStack);
            changeset = this.cleanUpChangeset(changeset);
            changesetStorageHandler._setUpdatedLocalChangeset(changeset);

        }
    };

    this.redo = function () {
        var undoStack = this._getUndoStack();
        var redoStack = this._getRedoStack();
        var redoEntry = redoStack.pop();
        if (redoEntry !== undefined && redoEntry !== null) {
            var changesetStorageHandler = new ChangesetStorageHandler();
            var changeset = changesetStorageHandler.getLocalChangeset();

            if (!this.linkIdExistsInChangeModels(redoEntry.id)) {
                var getLinkURL = MyProps["rootURL"] + "/api/link/" + redoEntry.id;
                $.ajax({
                    dataType: "json",
                    async: false,
                    url: getLinkURL,
                    success: function(data) {
                    var storageHandler = new ChangesetStorageHandler();

                    var link_changeModel = {"changesetNr":changeset.id};
                    link_changeModel.defaultValues = data;

                    $.each(data, function(key, value) {
                        link_changeModel[key] = value;
                    });

                    changeset.link_changeModels.push(link_changeModel);
                    changeset.geoJson.features.push(storageHandler._convertModelToGeoJsonFeature(link_changeModel));

                    var undoRedoHandler = new UndoRedoHandler();
                    undoRedoHandler.updateChangesetAfterRedo(changeset, redoEntry, undoStack);
                }});
            } else {
                this.updateChangesetAfterRedo(changeset, redoEntry, undoStack);
            }
        }
        this._setRedoStack(redoStack);
    };

    this.updateChangesetAfterRedo = function(changeset, redoEntry, undoStack) {
        var undoEntry = {value: {}};

        undoEntry.id = redoEntry.id;
        undoEntry.key = redoEntry.key;

        for (var index in changeset.link_changeModels) {
            var linkChangeModel = changeset.link_changeModels[index];
            if (redoEntry.key === "link_changed" && linkChangeModel.id === redoEntry.id) {
                if (redoEntry.value.from !== undefined) {
                    undoEntry.value.from = linkChangeModel.from;
                    linkChangeModel.from = redoEntry.value.from;
                }
                if (redoEntry.value.to !== undefined) {
                    undoEntry.value.to = linkChangeModel.to;
                    linkChangeModel.to = redoEntry.value.to;
                }
                if (redoEntry.value.long1 !== undefined) {
                    undoEntry.value.long1 = linkChangeModel.long1;
                    linkChangeModel.long1 = redoEntry.value.long1;
                }
                if (redoEntry.value.lat1 !== undefined) {
                    undoEntry.value.lat1 = linkChangeModel.lat1;
                    linkChangeModel.lat1 = redoEntry.value.lat1;
                }
                if (redoEntry.value.long2 !== undefined) {
                    undoEntry.value.long2 = linkChangeModel.long2;
                    linkChangeModel.long2 = redoEntry.value.long2;
                }
                if (redoEntry.value.lat2 !== undefined) {
                    undoEntry.value.lat2 = linkChangeModel.lat2;
                    linkChangeModel.lat2 = redoEntry.value.lat2;
                }
            } else if (linkChangeModel.id === redoEntry.id) {
                undoEntry.value = linkChangeModel[redoEntry.key];
                linkChangeModel[redoEntry.key] = redoEntry.value;
            }
        }
        for (var index in changeset.geoJson.features) {
            var geoJsonFeature = changeset.geoJson.features[index];
            if (geoJsonFeature.properties.id === redoEntry.id) {
                if(redoEntry.key === "link_changed" && redoEntry.value.coordinates !== undefined){
                    undoEntry.value.coordinates = geoJsonFeature.geometry.coordinates;
                    geoJsonFeature.geometry.coordinates = redoEntry.value.coordinates;
                    if (redoEntry.value.from !== undefined) {
                        geoJsonFeature.properties.from = redoEntry.value.from;
                    }
                    if (redoEntry.value.to !== undefined) {
                        geoJsonFeature.properties.to = redoEntry.value.to;
                    }
                }else {
                    geoJsonFeature.properties[redoEntry.key] = redoEntry.value;
                }
            }
        }
        var changesetStorageHandler = new ChangesetStorageHandler();
        changesetStorageHandler._setUpdatedLocalChangeset(changeset);
        undoStack.push(undoEntry);
        this._setUndoStack(undoStack);
    };

    this._getUndoStack = function () {
        if (this._isLocalStorageSupported()) {
            return JSON.parse(sessionStorage.getItem('undoStack'));
        } else {
            console.log("Local storage is not supported.");
        }
    };

    this._setUndoStack = function (undoStack) {
        if (this._isLocalStorageSupported()) {
            sessionStorage.setItem('undoStack', JSON.stringify(undoStack));
        } else {
            console.log("Local storage is not supported.");
        }
    };

    this._getRedoStack = function () {
        if (this._isLocalStorageSupported()) {
            return JSON.parse(sessionStorage.getItem('redoStack'));
        } else {
            console.log("Local storage is not supported.");
        }
    };

    this._setRedoStack = function (redoStack) {
        if (this._isLocalStorageSupported()) {
            sessionStorage.setItem('redoStack', JSON.stringify(redoStack));
        } else {
            console.log("Local storage is not supported.");
        }
    };

    this.undoRedoStackExists = function () {
        return this._getUndoStack() !== null && this._getRedoStack() !== null;
    };

    this.linkIdExistsInChangeModels = function(id) {
        var changesetStorageHandler = new ChangesetStorageHandler();
        var changeset = changesetStorageHandler.getLocalChangeset();

        for (var index in changeset.link_changeModels) {
            var linkChangeModel = changeset.link_changeModels[index];
            if (linkChangeModel.id === id) {
                return true;
            }
        }
        return false;
    }
}