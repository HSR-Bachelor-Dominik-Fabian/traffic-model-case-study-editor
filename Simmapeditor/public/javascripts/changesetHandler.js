/**
 * Created by fke on 19.04.2016.
 */

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
        var getLinkURL = MyProps["rootURL"] + "/api/changesets/" + changesetNr;
        var result;
        $.ajax({
            type:'GET',
            url:getLinkURL,
            dataType:'json',
            async:false,
            cache: false,
            success:function(data){
                result = data;
            }
        });
        return result;
    };

    this.saveChangeSet = function(){
        var storageHandler = new StorageHandler();
        var data = storageHandler.getLocalChangeset();
        if(data.id != null){
            var getLinkURL = MyProps["rootURL"] + "/api/changesets/" + data.id;
            $.ajax({
                type:'PUT',
                url:getLinkURL,
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(data),
                dataType: "json",
                success:function(){
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