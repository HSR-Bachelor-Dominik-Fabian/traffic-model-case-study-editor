(function(){
    var menuModule = angular.module('menuModule', ["mainModule"]);

    menuModule.directive("rootmenu", function() {
        return {templateUrl: "/partials/rootmenu"};
    });
    menuModule.directive("loadchangesetmenu", function() {
        return {templateUrl: "/partials/loadchangesetmenu"};
    });

    menuModule.controller("StreetMenuController", ['$scope', "layerInstance", function($scope, layerInstance) {
        $scope.menuState = "rootMenu";
        $scope.changeCount = 0;
        $scope.changesetsToLoad = null;
        $scope.$watch('menuState', function(newValue){
            $scope.changesetsToLoad = null;
            if(newValue === "loadChangeset"){
                var changeSetHandler = new ChangesetHandler();
                $scope.changesetsToLoad = changeSetHandler.getAllChangesets();
            }
        });
        $scope.onChangesetLoadClicked = function(item){
            var changeSetHandler = new ChangesetHandler();
            changeSetHandler.loadChangesetIntoLocalStorage(item.id);
            layerInstance.instance.redraw();
        };
        $scope.onChangesetSaveClicked = function(){
            var changeSetHandler = new ChangesetHandler();
            changeSetHandler.saveChangeSet();
        };

        $scope.onUndoClicked = function() {
            var undoRedoHandler = new UndoRedoHandler();
            undoRedoHandler.undo();
            $scope.changeCount--;
            layerInstance.instance.redraw();
        };

        $scope.onRedoClicked = function() {
            var undoRedoHandler = new UndoRedoHandler();
            undoRedoHandler.redo();
            $scope.changeCount++;
            layerInstance.instance.redraw();
        };

        $scope.$on('addChange', function(event, args) {
            $scope.changeCount++;
        });

        $scope.$on('removeChange', function(event, args) {
            $scope.changeCount--;
        });
    }]);
})();