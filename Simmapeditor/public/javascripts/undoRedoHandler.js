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
            var redoEntry = {value:{}};
            redoEntry.id = undoEntry.id;
            redoEntry.key = undoEntry.key;
            var changesetStorageHandler = new ChangesetStorageHandler();
            var changeset = changesetStorageHandler.getLocalChangeset();
            for (var index in changeset.link_changeModels) {
                var linkChangeModel = changeset.link_changeModels[index];
                if(undoEntry.key === "link_changed" && linkChangeModel.id === undoEntry.id){
                    if(undoEntry.value.from !== undefined){
                        redoEntry.value.from = linkChangeModel.from;
                        linkChangeModel.from = undoEntry.value.from;
                    }
                    if(undoEntry.value.to !== undefined){
                        redoEntry.value.to = linkChangeModel.to;
                        linkChangeModel.to = undoEntry.value.to;
                    }
                    if(undoEntry.value.long1 !== undefined){
                        redoEntry.value.long1 = linkChangeModel.long1;
                        linkChangeModel.long1 = undoEntry.value.long1;
                    }
                    if(undoEntry.value.lat1 !== undefined){
                        redoEntry.value.lat1 = linkChangeModel.lat1;
                        linkChangeModel.lat1 = undoEntry.value.lat1;
                    }
                    if(undoEntry.value.long2 !== undefined){
                        redoEntry.value.long2 = linkChangeModel.long2;
                        linkChangeModel.long2 = undoEntry.value.long2;
                    }
                    if(undoEntry.value.lat2 !== undefined){
                        redoEntry.value.lat2 = linkChangeModel.lat2;
                        linkChangeModel.lat2 = undoEntry.value.lat2;
                    }
                    //TODO: Delete if key of entry not exisiting in undoStack
                }
                else if (linkChangeModel.id === undoEntry.id) {
                    redoEntry.value = linkChangeModel[undoEntry.key];
                    //TODO: Delete if key of entry not exisiting in undoStack
                    linkChangeModel[undoEntry.key] = undoEntry.value;
                }
            }
            for (var index in changeset.geoJson.features) {
                var geoJsonFeature = changeset.geoJson.features[index];
                if (geoJsonFeature.properties.id === undoEntry.id) {
                    if(undoEntry.key === "link_changed" && undoEntry.value.coordinates !== undefined){
                        redoEntry.value.coordinates = geoJsonFeature.geometry;
                        geoJsonFeature.geometry = undoEntry.value.coordinates;
                    }else {
                        geoJsonFeature.properties[undoEntry.key] = undoEntry.value;
                    }
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
            var undoEntry = {value:{}};

            undoEntry.id = redoEntry.id;
            undoEntry.key = redoEntry.key;
            var changesetStorageHandler = new ChangesetStorageHandler();
            var changeset = changesetStorageHandler.getLocalChangeset();
            for (var index in changeset.link_changeModels) {
                var linkChangeModel = changeset.link_changeModels[index];
                if(redoEntry.key === "link_changed" && linkChangeModel.id === redoEntry.id){
                    if(redoEntry.value.from !== undefined){
                        undoEntry.value.from = linkChangeModel.from;
                        linkChangeModel.from = redoEntry.value.from;
                    }
                    if(redoEntry.value.to !== undefined){
                        undoEntry.value.to = linkChangeModel.to;
                        linkChangeModel.to = redoEntry.value.to;
                    }
                    if(redoEntry.value.long1 !== undefined){
                        undoEntry.value.long1 = linkChangeModel.long1;
                        linkChangeModel.long1 = redoEntry.value.long1;
                    }
                    if(redoEntry.value.lat1 !== undefined){
                        undoEntry.value.lat1 = linkChangeModel.lat1;
                        linkChangeModel.lat1 = redoEntry.value.lat1;
                    }
                    if(redoEntry.value.long2 !== undefined){
                        undoEntry.value.long2 = linkChangeModel.long2;
                        linkChangeModel.long2 = redoEntry.value.long2;
                    }
                    if(redoEntry.value.lat2 !== undefined){
                        undoEntry.value.lat2 = linkChangeModel.lat2;
                        linkChangeModel.lat2 = redoEntry.value.lat2;
                    }
                    //TODO: Delete if key of entry not exisiting in undoStack
                }
                else if (linkChangeModel.id === redoEntry.id) {
                    undoEntry.value = linkChangeModel[redoEntry.key];
                    linkChangeModel[redoEntry.key] = redoEntry.value;
                }
            }
            for (var index in changeset.geoJson.features) {
                var geoJsonFeature = changeset.geoJson.features[index];
                if (geoJsonFeature.properties.id === redoEntry.id) {
                    if(redoEntry.key === "link_changed" && redoEntry.value.coordinates !== undefined){
                        undoEntry.value.coordinates = geoJsonFeature.geometry;
                        geoJsonFeature.geometry = redoEntry.value.coordinates;
                    }else {
                        geoJsonFeature.properties[redoEntry.key] = redoEntry.value;
                    }
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