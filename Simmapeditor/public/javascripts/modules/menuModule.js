(function(){
    var menuModule = angular.module('menuModule', []);

    menuModule.directive("rootmenu", function() {
        return {templateUrl: "/partials/rootmenu"};
    });
    menuModule.directive("loadchangesetmenu", function() {
        return {templateUrl: "/partials/loadchangesetmenu"};
    });

    menuModule.controller("StreetMenuController", ['$scope', function($scope) {
        $scope.menuState="rootMenu";
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
        };
    }]);
})();