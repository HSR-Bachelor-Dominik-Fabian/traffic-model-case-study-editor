function ChangesetHandler() {
    this.getAllChangesets = function() {
        var getLinkURL = MyProps["rootURL"] + "/api/changesets/user/1";
        var result;
        $.ajax({
            type:'GET',
            url:getLinkURL,
            dataType:'json',
            async:false,
            success:function(data){
                result = data;
            }
        });
        return result;
    };
    this.initializeChangeset = function (){
        var storageHandler = new ChangesetStorageHandler();
        if (!storageHandler.localChangeSetExists()) {
            this.loadChangesetIntoLocalStorage(this.getAllChangesets()[0].id);
        }
    };
    this.loadChangesetIntoLocalStorage = function(changesetNr){
        var storageHandler = new ChangesetStorageHandler();
        var changeSet = this._loadChangeSet(changesetNr);
        storageHandler.setLocalChangeset(changeSet);
    };

    this._loadChangeSet = function(changesetNr){
        var getLinkURL = MyProps["rootURL"] + "/api/changesets/" + changesetNr;
        var result;
        $.ajax({
            type:'GET',
            url:getLinkURL,
            dataType:'json',
            async:false,
            success:function(data){
                result = data;
            }
        });
        return result;
    };

    this.saveChangeSet = function(){
        var storageHandler = new ChangesetStorageHandler();
        var data = storageHandler.getLocalChangeset();
        var getLinkURL = MyProps["rootURL"] + "/api/changesets/" + data.id;
        $.ajax({
            type:'PUT',
            url:getLinkURL,
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            dataType: "json",
            success:function(){

            }
        });
    };
}