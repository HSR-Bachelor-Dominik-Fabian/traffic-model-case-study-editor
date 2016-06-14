var MyProps = {};
MyProps["rootURL"] = 'http://localhost:9001';
(function () {
    describe("Test ChangesetHandler", function () {

        var changesetStorageHandler = null;
        var changesetHandler = null;

        beforeEach(angular.mock.inject(function () {
            changesetStorageHandler = new ChangesetStorageHandler();
            changesetStorageHandler.clearLocalChangeset();
            changesetHandler = new ChangesetHandler();
        }));

        it('test getAllChangesets', function () {
            spyOn($, 'ajax');
            changesetHandler.getAllChangesets();

            expect($.ajax.calls.mostRecent().args[0]['url']).toEqual('http://localhost:9001/api/changesets/user/1');
            expect($.ajax.calls.mostRecent().args[0]['type']).toEqual('GET');
        });

        it('test initialization of changeset', function () {
            expect(changesetStorageHandler.getLocalChangeset()).toBeNull();
            spyOn($, 'ajax');
            changesetHandler.initializeChangeset();

            var changeset = changesetStorageHandler.getLocalChangeset();

            expect(changeset.id).toBeNull();
            expect(changeset.name).toBeNull();
            expect(changeset.networkId).toEqual(1);
            expect(changeset.userNr).toEqual(1);
            expect(changeset.link_changeModels).toEqual([]);
            expect(changeset.node_changeModels).toEqual([]);
            expect(changeset.geoJson).toEqual({features: [], type: 'FeatureCollection'});

            expect($.ajax.calls.mostRecent().args[0]['url']).toEqual('http://localhost:9001/api/changesets/user/1');
            expect($.ajax.calls.mostRecent().args[0]['type']).toEqual('GET');
        });

        it('test empty changeset', function () {
            changesetHandler._loadEmptyChangesetIntoLocalStorage();

            var changeset = changesetStorageHandler.getLocalChangeset();

            expect(changeset.id).toBeNull();
            expect(changeset.name).toBeNull();
            expect(changeset.networkId).toEqual(1);
            expect(changeset.userNr).toEqual(1);
            expect(changeset.link_changeModels).toEqual([]);
            expect(changeset.node_changeModels).toEqual([]);
            expect(changeset.geoJson).toEqual({features: [], type: 'FeatureCollection'});
        });

        it('test loadChangeset URL', function () {
            spyOn($, 'ajax');

            var url = 'http://localhost:9001/api/changesets/1';

            changesetHandler._loadChangeSet(url);

            expect($.ajax.calls.mostRecent().args[0]['url']).toEqual(url);
            expect($.ajax.calls.mostRecent().args[0]['type']).toEqual('GET');
        });

        it('test deleteChangeset URL', function () {
            spyOn($, 'ajax');

            changesetHandler.deleteChangeset(1);

            expect($.ajax.calls.mostRecent().args[0]['url']).toEqual('http://localhost:9001/api/changesets/1');
            expect($.ajax.calls.mostRecent().args[0]['type']).toEqual('DELETE');
        });

        it('test save new changeset URL', function () {
            spyOn($, 'ajax');
            changesetHandler._loadEmptyChangesetIntoLocalStorage();

            changesetHandler.saveChangeSet();

            expect($.ajax.calls.mostRecent().args[0]['url']).toEqual('http://localhost:9001/api/changesets/user/1');
            expect($.ajax.calls.mostRecent().args[0]['type']).toEqual('POST');
        });

        it('test save existing changeset URL', function () {
            spyOn($, 'ajax');
            changesetHandler._loadEmptyChangesetIntoLocalStorage();
            var changeset = changesetStorageHandler.getLocalChangeset();
            changeset.id = 1;
            changeset.name = 'Test';
            changesetStorageHandler._setUpdatedLocalChangeset(changeset);

            changesetHandler.saveChangeSet();

            expect($.ajax.calls.mostRecent().args[0]['url']).toEqual('http://localhost:9001/api/changesets/1');
            expect($.ajax.calls.mostRecent().args[0]['type']).toEqual('PUT');
        });
    });
})();