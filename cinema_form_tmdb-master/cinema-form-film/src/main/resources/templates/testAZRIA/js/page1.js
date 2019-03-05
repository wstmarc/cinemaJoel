$("#valider").click
(
    function()
    {
        //alert("bonjour");
        var resultat = estComplet();
        //récupération des valeurs
        var nom = $("#nom").val();
        var email1 = $("#email1").val();
        var email2 = $("#email2").val();
        var mdp1 = $("#mdp1").val();
        var mdp2 = $("#mdp2").val();
        if(resultat)
        {
            if(!concordance(email1, email2)){
                //mails non concordants.
                //on colore en orange les champs correspondants à email1 et email2.
                $("#email1, #email2").addClass('bg-warning');
            } if(!concordance(mdp1, mdp2)){
            //mots de passe non concordants.
            //on colore en orange les champs correspondants à mdp1 et mdp2.
            $("#mdp1, #mdp2").addClass('bg-warning');
        } else {
            //mots de passe concordants et mails concordants.
            //on colore en vert tous les champs.
            $(".saisie").addClass('bg-success');
        }
        }
        //BONUS : test changement de couleur : BONUS /////
        if($(this).hasClass('btn-info')){				//
            $(this).toggleClass('btn-danger');			//
        } else {										//
            $(this).toggleClass('btn-info');			//
        }/////////////////////////////////////////////////

    }

);

//click sur des éléments de saisie
$(".saisie").focus
(
    function()
    {
        $(this).removeClass('bg-warning');
    }
);

//fonctions définies
function estComplet()
{
    resultat = true;
    //parcours de tous les éléments de la classe 'saisie'.
    $(".saisie").each
    (
        function()
        {
            //on recupère la valuer de l'objet courant
            let valeur = $(this).val();
            if(!valeur){
                resultat=false;
                $(this).addClass('bg-warning');
            }
        }
    );
    return resultat;
}

//OBJECTIF les champs concordants sont colorés en vert (adresse, mdp).

//concordance de deux champs
function concordance(champ1, champ2)
{
    return champ1 == champ2;
}
