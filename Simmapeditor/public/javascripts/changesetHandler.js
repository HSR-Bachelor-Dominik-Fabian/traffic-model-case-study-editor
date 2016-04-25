/**
 * Created by fke on 19.04.2016.
 */

function ChangesetHandler() {
    var rootUrl = "http://localhost:9001/";
    //var rootUrl = "http://152.96.56.47:40005/";
    this.getAllChangesets = function() {
        var getLinkURL = rootUrl + "api/changesets/user/1";
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
        var storageHandler = new StorageHandler();
        if (!storageHandler.localChangeSetExists()) {
            this.loadChangesetIntoLocalStorage(this.getAllChangesets()[0].id);
        }
    };
    this.loadChangesetIntoLocalStorage = function(changesetNr){
        var storageHandler = new StorageHandler();
        var changeSet = this._loadChangeSet(changesetNr);
        storageHandler.setLocalChangeset(changeSet);
    };

    this._loadChangeSet = function(changesetNr){
        var getLinkURL = rootUrl + "api/changesets/" + changesetNr;
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
    }
}