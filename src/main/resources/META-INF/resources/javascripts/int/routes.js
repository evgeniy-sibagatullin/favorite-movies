window.app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'partials/search.html',
        controller: 'searchCtrl'});
    $routeProvider.otherwise({redirectTo: '/404'});
}]);

