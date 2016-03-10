requirejs.config({
    baseUrl: 'assets/javascripts/lib',
    paths: {
        "jquery":["https://code.jquery.com/jquery-1.12.1.min", "jquery-1.9.0.min"],
        "d3": ["//d3js.org/d3.v3.min", "d3.v3.min"],
        "angular": ["https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular.min", "angular"],
        "angular-route": ["https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-route", "angular-route"],
        "bootstrap":["https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min"],
        "jasny": ["https://cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/js/jasny-bootstrap.min"],
        "zoomslider": ["L.Control.Zoomslider"]
    },
    shim:{
        "bootstrap":{deps:["jquery"]},
        "jasny":{deps:["bootstrap"]},
        "angular-route":{deps:["angular"]}
    }
});