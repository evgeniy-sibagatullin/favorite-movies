window.bootstrap = function() {
    jQuery.event.props.push('dataTransfer');
    angular.bootstrap(document, ['movie-favorites']);
};

window.init = function() {
    window.bootstrap();
};

angular.element(document).ready(function() {
    window.init();
});