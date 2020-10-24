angular.module('movie-favorites.site').controller('searchCtrl', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.searchMovies = function () {
        var search_query = $scope.search_query;
        if (search_query && search_query.length > 2) {
            $scope.movies_to_display = [];
            $http({
                method: 'POST',
                dataType: "json",
                url: '/movie/ajax/getMovieData',
                data: search_query
            }).success(function (input_movies) {
                $scope.prepareMoviesToDisplay(input_movies);
            });
        } else {
            $scope.search_query = "";
        }
    };

    $scope.prepareMoviesToDisplay = function (input_movies) {
        $.each(input_movies, function (i, movie) {
            var poster_full_path = (movie.poster_path != null) ?
                "http://image.tmdb.org/t/p/w92" + movie.poster_path :
                "/img/no_poster_available.jpg";

            $scope.movies_to_display.push({
                "id": movie.id,
                "title": movie.title,
                "poster_path": poster_full_path,
                "overview": movie.overview
            });
        });
    };

    $scope.getFavoritesLists = function () {
        $http({
            method: 'GET',
            url: '/favorites/getAll'
        }).success(function (favorites_lists) {
            $scope.favorites_lists = favorites_lists;
        });
    };

    $scope.createFavoritesList = function () {
        var favorites_list_name = $scope.favorites_list_name ? $scope.favorites_list_name.trim() : null;
        if (favorites_list_name) {
            $http({
                method: 'POST',
                dataType: "json",
                url: '/favorites/create',
                data: favorites_list_name
            }).success(function () {
                $scope.getFavoritesLists();
                $scope.favorites_list_name = '';
            });
        }
    };

    $scope.isListSelected = function (id) {
        return $scope.selected_list_id == id;
    };

    $scope.selectList = function (id) {
        $scope.selected_list_id = id;
    };

    $scope.showFavoritesListDetails = function (favorites_list_id) {
        $location.url('/favorites/' + favorites_list_id);
    };

    $scope.movieDetails = function (id) {
        var movieToProcess = null;

        $.each($scope.movies_to_display, function (i, movie) {
            if (movie.id == id) {
                movieToProcess = movie;
            }
        });

        alert(JSON.stringify(movieToProcess));
    };
}]);