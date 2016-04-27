function UndoRedoHandler() {

    this._isLocalStorageSupported = function() {
        return typeof(Storage) !== "undefined";
    };

    this.initializeUndoRedoStack = function() {
        if (this._isLocalStorageSupported() && !this.undoRedoStackExists()) {
            var undoStack = [];
            var redoStack = [];
            sessionStorage.setItem('undoStack', JSON.stringify(undoStack));
            sessionStorage.setItem('redoStack', JSON.stringify(redoStack));
        }
    };

    this.clearUndoRedoStack = function() {
        this._setRedoStack([]);
        this._setUndoStack([]);
    };

    this.addChange = function(changeModel) {
        this._setRedoStack([]);
        var undoStack = this._getUndoStack();
        undoStack.push(changeModel);
        this._setUndoStack(undoStack);
    };

    this.undo = function() {
        var undoStack = this._getUndoStack();
        var redoStack = this._getRedoStack();
        var undoEntry = undoStack.pop();
        if (undoEntry !== undefined && undoEntry !== null) {
            var redoEntry = {};
            redoEntry.id = undoEntry.id;
            redoEntry.key = undoEntry.key;
            var changesetStorageHandler = new ChangesetStorageHandler();
            var changeset = changesetStorageHandler.getLocalChangeset();
            for (var index in changeset.link_changeModels) {
                var linkChangeModel = changeset.link_changeModels[index];
                if (linkChangeModel.id === undoEntry.id) {
                    redoEntry.value = linkChangeModel[undoEntry.key];
                    linkChangeModel[undoEntry.key] = undoEntry.value;
                }
            }
            for (var index in changeset.geoJson.features) {
                var geoJsonFeature = changeset.geoJson.features[index];
                if (geoJsonFeature.properties.id === undoEntry.id) {
                    geoJsonFeature.properties[undoEntry.key] = undoEntry.value;
                }
            }
            changesetStorageHandler._setUpdatedLocalChangeset(changeset);
            redoStack.push(redoEntry);
            this._setUndoStack(undoStack);
            this._setRedoStack(redoStack);
        }
    };

    this.redo = function() {
        var undoStack = this._getUndoStack();
        var redoStack = this._getRedoStack();
        var redoEntry = redoStack.pop();
        if (redoEntry !== undefined && redoEntry !== null) {
            var undoEntry = {};

            undoEntry.id = redoEntry.id;
            undoEntry.key = redoEntry.key;
            var changesetStorageHandler = new ChangesetStorageHandler();
            var changeset = changesetStorageHandler.getLocalChangeset();
            for (var index in changeset.link_changeModels) {
                var linkChangeModel = changeset.link_changeModels[index];
                if (linkChangeModel.id === redoEntry.id) {
                    undoEntry.value = linkChangeModel[redoEntry.key];
                    linkChangeModel[redoEntry.key] = redoEntry.value;
                }
            }
            for (var index in changeset.geoJson.features) {
                var geoJsonFeature = changeset.geoJson.features[index];
                if (geoJsonFeature.properties.id === redoEntry.id) {
                    geoJsonFeature.properties[redoEntry.key] = redoEntry.value;
                }
            }
            changesetStorageHandler._setUpdatedLocalChangeset(changeset);
            undoStack.push(undoEntry);
            this._setUndoStack(undoStack);
            this._setRedoStack(redoStack);
        }
    };

    this._getUndoStack = function() {
        if (this._isLocalStorageSupported()) {
            return JSON.parse(sessionStorage.getItem('undoStack'));
        } else {
            console.log("Local storage is not supported.");
        }
    };

    this._setUndoStack = function(undoStack) {
        if (this._isLocalStorageSupported()) {
            sessionStorage.setItem('undoStack', JSON.stringify(undoStack));
        } else {
            console.log("Local storage is not supported.");
        }
    };

    this._getRedoStack = function() {
        if (this._isLocalStorageSupported()) {
            return JSON.parse(sessionStorage.getItem('redoStack'));
        } else {
            console.log("Local storage is not supported.");
        }
    };

    this._setRedoStack = function(redoStack) {
        if (this._isLocalStorageSupported()) {
            sessionStorage.setItem('redoStack', JSON.stringify(redoStack));
        } else {
            console.log("Local storage is not supported.");
        }
    };

    this.undoRedoStackExists = function() {
        return this._getUndoStack() !== null && this._getRedoStack() !== null;
    };
}