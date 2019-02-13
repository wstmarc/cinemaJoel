// Single state variable for endpoint
var MD_BASE_URL = "https://api.themoviedb.org/3/search/movie?include_adult=false&page=1";



var state = {
    query: ''
};

// State modification functions

function getApiData(searchTerm, callback) {
    var query = {
        query: searchTerm,
        language: 'fr-FR',
        api_key: '1348a42689a984e53728a4b3e41f7e11', // took the key out for privacy
    };
    $.getJSON(MD_BASE_URL, query, callback);
}

// Render functions
function displayMDSearchData(data) {

    var resultElement = '';

    if(data.results.length > 1) {
        resultElement += '<section class="js-search-results clearfix">';
        resultElement += '<h2>' + '<span>' + "Results for " + state.query + '</span>' + '</h2>';

        if(data.results)

            data.results.forEach(function(results){
                resultElement += '<article>';
                resultElement += '<div class="container">';
                resultElement += '<img src="https://image.tmdb.org/t/p/w500' + results.poster_path + '"/>';
                resultElement += '<div class="content">';
                if(results.title.length > 20) {
                    resultElement += '<h3>' + results.title.substr(0,20) +'...</h3>';
                } else {
                    resultElement += '<h3>' + results.title + '</h3>';
                }

                resultElement += '<p>Released: ' + results.release_date + '</p>';
                resultElement += '</div>';
                resultElement += '</div>';
                resultElement += '</article>';
            });

        resultElement += '</section>';
        console.log(data);



    } else {
        resultElement += '<p class="no-results">No results</p>';
    }

    $('.js-search-results').html(resultElement);
}

// Event listeners
function watchSubmit() {
    $('.js-search-form').submit(function(e) {
        e.preventDefault();
        state.query = $(this).find('.js-query').val();
        var query = state.query;
        getApiData(query, displayMDSearchData);
    });
}

$(function(){watchSubmit();});
