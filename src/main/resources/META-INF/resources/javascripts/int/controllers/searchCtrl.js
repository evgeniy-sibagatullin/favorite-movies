angular.module('movie-favorites.site').controller('searchCtrl', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.searchMovies = function () {
        const search_query = $scope.search_query;
        if (search_query && search_query.length > 2) {
            $scope.movies_to_display = [];
            $http({
                method: 'POST',
                dataType: "json",
                url: '/movie/getData',
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
            const poster_full_path = (movie.poster_path != null) ?
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
        const favorites_list_name = $scope.favorites_list_name ? $scope.favorites_list_name.trim() : null;
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
        return $scope.selected_list_id === id;
    };

    $scope.selectList = function (id) {
        $scope.selected_list_id = id;
    };

    $scope.showFavoritesListDetails = function (favorites_list_id) {
        $location.url('/favorites/' + favorites_list_id);
    };

    $scope.movieDetails = function (movieId) {
        let movieToProcess = $scope.getMovieById(movieId);

        if (movieToProcess) {
            $http({
                method: 'POST',
                dataType: "json",
                url: '/movie/getDetails',
                data: movieToProcess
            }).success(function () {
                $location.url('\/movie\/' + movieToProcess.id);
            });
        }
    };

    $scope.addToFavoritesList = function (movie) {
        let movieId = movie.id;
        let movieToProcess = $scope.getMovieById(movieId);

        const active_favorites_list = $('.favorites_list_title.active');

        if (active_favorites_list.length) {
            $http({
                method: 'POST',
                dataType: "json",
                url: "/favorites/addMovie/" + active_favorites_list.attr("id") + "/" + movieId,
                data: movieToProcess
            });
        }
    };

    $scope.getMovieById = function (movieId) {
        let movieToProcess = null;

        $.each($scope.movies_to_display, function (i, movie) {
            if (movie.id === movieId) {
                movieToProcess = movie;
            }
        });

        return movieToProcess;
    }
}]);