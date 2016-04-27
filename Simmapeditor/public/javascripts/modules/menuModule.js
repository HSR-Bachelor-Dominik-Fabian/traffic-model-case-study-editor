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
        $scope.isUndoDisabled = false;
        $scope.isRedoDisabled = false;
        $scope.changesetsToLoad = null;

        $scope.$watch(function () {
            return sessionStorage.getItem("undoStack");
        }, function (newVal, oldVal) {
            $scope.changeCount = JSON.parse(newVal).length;
            if ($scope.changeCount === 0) {
                $("#undoButton").addClass('deactivatedButton');
                $scope.isUndoDisabled = true;
            } else {
                $("#undoButton").removeClass('deactivatedButton');
                $scope.isUndoDisabled = false;
            }
        }, true);

        $scope.$watch(function () {
            return sessionStorage.getItem("redoStack");
        }, function (newVal, oldVal) {
            var redoStack = JSON.parse(newVal);
            if (redoStack.length === 0) {
                $("#redoButton").addClass('deactivatedButton');
                $scope.isRedoDisabled = true;
            } else {
                $("#redoButton").removeClass('deactivatedButton');
                $scope.isRedoDisabled = false;
            }
        }, true);

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
            layerInstance.instance.redraw();
        };

        $scope.onRedoClicked = function() {
            var undoRedoHandler = new UndoRedoHandler();
            undoRedoHandler.redo();
            layerInstance.instance.redraw();
        };
    }]);
})();