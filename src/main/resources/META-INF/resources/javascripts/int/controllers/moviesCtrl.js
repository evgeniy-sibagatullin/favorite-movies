angular.module('movie-favorites.site').controller('moviesCtrl', ['$scope', '$location', '$http', '$routeParams', function ($scope, $location, $http, $routeParams) {
    $scope.getMovie = function () {
        $http({
            method: 'GET',
            url: '/movie/' + $routeParams.id
        }).success(function (movie) {
            $scope.movie = movie;
        }).error(function () {
            $location.url('/');
        });
    };

    $scope.getFavoritesLists = function () {
        $http({
            method: 'GET',
            url: '/movie/getFavoritesLists/' + $routeParams.id
        }).success(function (favorites_lists) {
            $scope.favorites_lists = favorites_lists;
        }).error(function () {
            $location.url('/');
        });
    };
}]);

