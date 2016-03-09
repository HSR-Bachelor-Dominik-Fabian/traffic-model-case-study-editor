require(["angular"], function(){
    var simmapeditorApp = angular.module("simmapeditorApp",[]);

    simmapeditorApp.controller("StreetDetailController", function($scope){
        $scope.streetModel=null
        $scope.clicked=function(evt){
            alert(evt);
        }
    });
});