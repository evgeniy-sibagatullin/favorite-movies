window.app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'partials/search.html',
        controller: 'searchCtrl'});
    $routeProvider.otherwise({
        templateUrl: 'partials/404.html',
        controller: 'page404Ctrl'});
}]);

