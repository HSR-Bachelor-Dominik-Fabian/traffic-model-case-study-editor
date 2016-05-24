function ChangesetHandler() {
    this.getAllChangesets = function(showMessageDialog) {
        var getLinkURL = MyProps["rootURL"] + "/api/changesets/user/1";
        var result = [];
        $.ajax({
            type:'GET',
            cache: false,
            url:getLinkURL,
            dataType:'json',
            async:false,
            success:function(data){
                result = data;
            },
            error: function(data){
                showMessageDialog('Fehler: ' + data.responseJSON.message);
            }
        });
        return result;
    };
    this.initializeChangeset = function (showMessageDialog){
        var storageHandler = new ChangesetStorageHandler();
        if (!storageHandler.localChangeSetExists()) {
            var allChangesets = this.getAllChangesets(showMessageDialog);
            if (allChangesets.length !== 0) {
                this.loadChangesetIntoLocalStorage(allChangesets[0].id);
            } else {
                this._loadEmptyChangesetIntoLocalStorage();
            }
        }
    };

    this._loadEmptyChangesetIntoLocalStorage = function() {
        var storageHandler = new ChangesetStorageHandler();
        var fullChangeModel = {"id": null, "name": null, "networkId": 1, "userNr": 1,
            "lastModified": Date.now(), "link_changeModels": [], "node_changeModels": []};
        storageHandler.setLocalChangeset(fullChangeModel);
    };

    this.loadChangesetIntoLocalStorage = function(changesetNr, showMessageDialog){
        var storageHandler = new ChangesetStorageHandler();
        var changeSet = this._loadChangeSet(MyProps["rootURL"] + "/api/changesets/" + changesetNr, showMessageDialog);
        storageHandler.setLocalChangeset(changeSet);
    };

    this._loadChangeSet = function(getLinkURL, showMessageDialog){
        var result;
        $.ajax({
            type:'GET',
            url:getLinkURL,
            dataType:'json',
            async:false,
            cache: false,
            success:function(data){
                result = data;
            },
            error: function(data){
                showMessageDialog('Fehler: ' + data.responseJSON.message);
            }
        });
        return result;
    };

    this.deleteChangeset = function (id, showMessageDialog){
        var storageHandler = new ChangesetStorageHandler();
        var data = storageHandler.getLocalChangeset();
        var getLinkURL = MyProps["rootURL"] + "/api/changesets/" + id;
        var success = false;
        $.ajax({
            type:'DELETE',
            url:getLinkURL,
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            dataType: "json",
            async:false,
            success:function(){
                success = true;
            },
            error: function(){
                success = false;
            }
        });

        if(success && data.id === id){
            storageHandler.clearLocalChangeset();
            this.initializeChangeset(showMessageDialog);
        }
        return success;
    };

    this.saveChangeSet = function(showMessageDialog){
        var storageHandler = new ChangesetStorageHandler();
        var data = storageHandler.getLocalChangeset();
        data.lastModified = Date.now();
        if(data.id != null){
            var getLinkURL = MyProps["rootURL"] + "/api/changesets/" + data.id;
            $.ajax({
                type:'PUT',
                url:getLinkURL,
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(data),
                dataType: "json",
                success:function(){
                    storageHandler._setUpdatedLocalChangeset(data);
                    var undoRedoHandler = new UndoRedoHandler();
                    undoRedoHandler.clearUndoRedoStack();
                    $("#saveSuccess").show();
                    $("#saveSuccess").fadeOut(5000);
                    showMessageDialog('Changeset wurde erfolgreich gespeichert.');
                },
                error: function(data){
                    showMessageDialog('Fehler: ' + data.responseJSON.message);
                    $("#saveError").show();
                    $("#saveError").fadeOut(5000);
                }
            });
        }
        else{
            var getLinkURL = MyProps["rootURL"] + "/api/changesets/user/1"
            $.ajax({
                type:'POST',
                url:getLinkURL,
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(data),
                success:function(data, status, xhr){
                    var location = xhr.getResponseHeader('Location');
                    var storageHandler = new ChangesetStorageHandler();
                    var changeSetHandler = new ChangesetHandler();
                    var changeSet = changeSetHandler._loadChangeSet(location);
                    storageHandler.setLocalChangeset(changeSet);
                    $("#saveSuccess").show();
                    $("#saveSuccess").fadeOut(5000);
                },
                error: function(){
                    $("#saveError").show();
                    $("#saveError").fadeOut(5000);
                }
            });
        }
    };
}