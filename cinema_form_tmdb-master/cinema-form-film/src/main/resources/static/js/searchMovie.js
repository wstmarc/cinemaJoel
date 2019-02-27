$(document).ready(function () {
    $("#reset").click(function (e) {
        location.reload();
    });

    $("#submit").click(function (e) {
        var validate = Validate();
        $("#message").html(validate);
        if (validate.length == 0) {

            CallAPI(1);}
    });

    $("#message").on("click", ".result", function () {
        var resourceId = $(this).attr("resourceId");
        $.ajax({
            url: "https://api.themoviedb.org/3/movie/" + resourceId + "?language=fr-FR",
            data: {
                api_key: "1348a42689a984e53728a4b3e41f7e11"
            },
            dataType: 'json',
            success: modalDialog,
            error: modalErreur
        });
    });

    function modalErreur(xhr, status, error) {
        $("#message").html("Result: " + status + " " + error + " " + xhr.status + " " + xhr.statusText)
    }

    function onSuccessAddFilm(result) {

        $("#myModal").modal("hide");
        $("#monModal").modal("show");
        $("#boutonSucces").attr("href", "/film/detail/" + result.id);
    }

    function modalDialog(result) {
        $("#modalTitleH4").html(result["title"]);

        var image = result["poster_path"] == null ? "images/no-image.png" : "https://image.tmdb.org/t/p/w500/" + result["poster_path"];
        var overview = result["overview"] == null ? "No information available" : result["overview"];
        var genres = result["genres"] == null ? "No information available" : result["genres"];
        var compagni = result["production_companies"] == null ? "No information available" : result["production_companies"];
        var idt = result["id"] == null ? "No information available" : result["id"];
        $("#ajout-film").on("click", function () {
            var $this = $(this);
            $this.button('loading');
            setTimeout(function() {
                $this.button('reset');
            }, 500000);

            $.ajax({
                url: "/api/film/tmdb/" + idt,
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                success: onSuccessAddFilm
            });
        })

        let genresNom = "";
        for (var i = 0; i < genres.length; i++) {
            genresNom += genres[i].name + " ";
        }
        let compagni_name = "";
        for (var i = 0; i < compagni.length; i++) {
            compagni_name += compagni[i].name + " ";
        }
        console.log(compagni_name);
        var resultHtml = "<p class=\"text-center\"><img src=\"" + image + "\"/></p><p>Genres de film: " + genresNom + "</p><p>Maison de production: "+compagni_name+"</p><p>Résumé : " + overview + "</p>";
        resultHtml += "<p>Date de sortie: " + result["release_date"] + "</p><p>Popularité: " + result["popularity"] + "";

        $("#modalBodyDiv").html(resultHtml)

        $("#myModal").modal("show");
    }

    $(document).ajaxStart(function () {
        $(".imageDiv img").show();
    });

    $(document).ajaxStop(function () {
        $(".imageDiv img").hide();
    });

    function CallAPI(page) {

        $.ajax({
            url: "https://api.themoviedb.org/3/search/movie?language=fr-FR&query=" + $("#searchInput").val() + "&page=" + page + "&include_adult=false",
            data: {"api_key": "1348a42689a984e53728a4b3e41f7e11"},
            dataType: "json",
            success: onSuccesSearch,
            error: onErrorSearch
        });
    }

    function onSuccesSearch(result) {
        var resultHtml = $("<div class=\"resultDiv\"><p>Names</p>");
        for (i = 0; i < result["results"].length; i++) {

            var image = result["results"][i]["poster_path"] == null ? "images/no-image.png" : "https://image.tmdb.org/t/p/w500/" + result["results"][i]["poster_path"];

            resultHtml.append("<div class=\"result\" resourceId=\"" + result["results"][i]["id"] + "\">" + "<img src=\"" + image + "\" />" + "<p><a>" + result["results"][i]["title"] + "</a></p></div>")
        }

        resultHtml.append("</div>");
        $("#message").html(resultHtml);


        Paging(result["total_pages"]);
    }

    function onErrorSearch(xhr, status, error) {
        $("#message").html("Result: " + status + " " + error + " " + xhr.status + " " + xhr.statusText)
    }

    // si le champs est vide
    function Validate() {
        var errorMessage = "";
        if ($("#searchInput").val() == "") {
            errorMessage += "► Enter Search Text";
        }
        return errorMessage;
    }
    //aucun film pour une recherche


    //la pagination
    function Paging(totalPage) {
        var obj = $("#pagination").twbsPagination({
            totalPages: totalPage,
            visiblePages: 5,
            onPageClick: function (event, page) {
                CallAPI(page);
            }
        });
    }

});