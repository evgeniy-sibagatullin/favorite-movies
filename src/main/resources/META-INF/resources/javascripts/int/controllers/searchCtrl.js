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
            }).
                success(function (input_movies) {
                    $scope.prepareMoviesToDisplay(input_movies);
                });
        }
        else {
            $scope.search_query = "";
        }
    };

    $scope.prepareMoviesToDisplay = function (input_movies) {
        $.each(input_movies, function (i, movie) {
            var poster_full_path = (movie.poster_path != null) ?
            "http://image.tmdb.org/t/p/w92" + movie.poster_path :
                "/img/no_poster_available.jpg";

            $scope.movies_to_display.push({
                "movie_id": movie.id,
                "title": movie.title,
                "poster_path": poster_full_path,
                "overview": movie.overview
            });
        });
    };
}]);