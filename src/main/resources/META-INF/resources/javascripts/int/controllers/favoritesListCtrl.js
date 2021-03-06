angular.module('movie-favorites.site').controller('favoritesListCtrl', ['$scope', '$location', '$http', '$routeParams', function ($scope, $location, $http, $routeParams) {
    $scope.getFavoritesList = function () {
        $http({
            method: 'GET',
            url: '/favorites/' + $routeParams.id
        }).success(function (favorites_list) {
            $scope.favorites_list = favorites_list;
        }).error(function () {
            $location.url('/');
        });
    };

    $scope.getMovies = function () {
        $http({
            method: 'GET',
            url: 'movie/getMovies/' + $routeParams.id
        }).success(function (movies_list) {
            $scope.movies = movies_list;
        }).error(function () {
            $location.url('/');
        });
    }

    $scope.deleteFavoritesList = function () {
        if (confirm('Are you sure?')) {
            $http({
                method: 'DELETE',
                url: '/favorites/delete/' + $routeParams.id
            }).success(function () {
                $location.url('/');
            }).error(function () {
                $location.url('/');
            });
        }
    };

    $scope.removeMovie = function (movieId) {
        $http({
            method: 'GET',
            url: 'favorites/removeMovie/' + $routeParams.id + "/" + movieId
        }).success(function () {
            $scope.getMovies();
        }).error(function () {
            $location.url('/');
        });
    }
}]);