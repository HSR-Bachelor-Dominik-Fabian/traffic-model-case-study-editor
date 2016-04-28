/**
 * Created by fke on 19.04.2016.
 */

function ChangesetStorageHandler() {

    this._isLocalStorageSupported = function() {
        return typeof(Storage) !== "undefined";
    };

    this._convertModelToGeoJsonFeature = function(model) {
        var feature = {};
        var lat1 = model["lat1"], lat2 = model["lat2"], lon1 = model["long1"], lon2 = model["long2"];

        var coordinates = [];
        var point1 = [lon1,lat1], point2 = [lon2,lat2];
        coordinates.push(point1,point2);
        var geometry = { "coordinates": coordinates, "type": "LineString"};
        feature.geometry = geometry;
        feature.type = "Feature";

        var properties = { "modes": model["modes"], "zoomlevel": model["minlevel"], "length": model["length"],
            "freespeed": model["freespeed"], "permlanes": model["permlanes"], "id": model["id"],
            "oneway": model["oneway"], "capacity": model["capacity"]};

        feature.properties = properties;
        return feature;
    };

    this._convertFullModelToGeoJson = function(fullChangeModel) {
        var geoJson = {};
        geoJson.features = [];
        for (var link_changeModelIndex in fullChangeModel.link_changeModels) {
            var link_changeModel = fullChangeModel.link_changeModels[link_changeModelIndex];
            var geoJsonFeature = this._convertModelToGeoJsonFeature(link_changeModel);
            geoJson.features.push(geoJsonFeature);
        }
        geoJson.type = "FeatureCollection";
        return geoJson;
    };

    this.getLocalChangeset = function() {
        if (this._isLocalStorageSupported()) {
            return JSON.parse(sessionStorage.getItem("changeset"));
        } else {
            console.log("local Storage not supported from your browser.");
        }
    };

    this.setLocalChangeset = function(fullChangeModel) {
        if (this._isLocalStorageSupported()) {
            var geoJson = this._convertFullModelToGeoJson(fullChangeModel);
            fullChangeModel.geoJson = geoJson;
            sessionStorage.setItem("changeset", JSON.stringify(fullChangeModel));
            var undoRedoHandler = new UndoRedoHandler();
            undoRedoHandler.clearUndoRedoStack();
        } else {
            console.log("local Storage not supported from your browser.");
        }
    };

    this.clearLocalChangeset = function(){
        if (this._isLocalStorageSupported()) {
            sessionStorage.removeItem("changeset");
            var undoRedoHandler = new UndoRedoHandler();
            undoRedoHandler.clearUndoRedoStack();
        } else {
            console.log("local Storage not supported from your browser.");
        }
    };

    this._setUpdatedLocalChangeset = function(changeSet) {
        if (this._isLocalStorageSupported()) {
            sessionStorage.setItem("changeset", JSON.stringify(changeSet));
        } else {
            console.log("local Storage not supported from your browser.");
        }
    };

    this.localChangeSetExists = function() {
        return this.getLocalChangeset() !== null;
    };

    this.addNewChange = function(model) {
        var localChangeset = this.getLocalChangeset();

        var linkExistsInChangeset = false;
        for (var index in localChangeset.link_changeModels) {
            var link_changeModel = localChangeset.link_changeModels[index];
            if (link_changeModel.id === model.properties.id) {
                this._changeExistingLinkChangeModelInChangeset(model, link_changeModel, localChangeset);
                linkExistsInChangeset = true;
                break;
            }
        }
        if (!linkExistsInChangeset) {
            this._addNewLinkChangeModelToChangeset(model, localChangeset);
        }
    };

    this._changeExistingLinkChangeModelInChangeset = function(model, link_changeModel, localChangeset) {
        var geoJsonFeatureIndex
        for (geoJsonFeatureIndex in localChangeset.geoJson.features) {
            var geoJsonFeature = localChangeset.geoJson.features[geoJsonFeatureIndex];
            if (geoJsonFeature.properties.id === model.properties.id) {
                break;
            }
        }

        $.each(model.properties, function(key, value) {
            if (key.indexOf('Calculated') === -1) {
                link_changeModel[key] = value;
                localChangeset.geoJson.features[geoJsonFeatureIndex].properties[key] = value;
            }
        });

        this._setUpdatedLocalChangeset(localChangeset);
    };

    this._addNewLinkChangeModelToChangeset = function(model, localChangeset) {
        var getLinkURL = MyProps["rootURL"] + "/api/link/" + model.properties.id;
        $.getJSON(getLinkURL, function(data){
            var storageHandler = new ChangesetStorageHandler();

            var link_changeModel = {"changesetNr":localChangeset.id};
            link_changeModel.defaultValues = data;

            $.each(data, function(key, value) {
                link_changeModel[key] = value;
            });

            $.each(model.properties, function(key, value) {
                if (key.indexOf('Calculated') === -1) {
                    link_changeModel[key] = value;
                }
            });

            localChangeset.link_changeModels.push(link_changeModel);
            localChangeset.geoJson.features.push(storageHandler._convertModelToGeoJsonFeature(link_changeModel));
            storageHandler._setUpdatedLocalChangeset(localChangeset);
        });
    };

}