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
                alert("emails non identiques !!!");
                $("#email1, #email2").addClass('bg-warning');
            } /* if(!concordance(mdp1, mdp2)){
					//mots de passe non concordants.
					//on colore en orange les champs correspondants à mdp1 et mdp2.
					$("#mdp1, #mdp2").addClass('bg-warning');
					alert("mots de passe non identiques !!!");
				} */ else {
                //OBJECTIF les champs concordants sont colorés en vert (adresse, mdp).
                //mots de passe concordants et mails concordants.
                //on colore en vert tous les champs.
                $(".saisie").addClass('bg-success');
                $(this).removeAttr('class').attr('class','');
                $(this).attr('class').addClass('btn-success');
            }
        }
        //BONUS : test changement de couleur : BONUS /////

        if($(this).hasClass('btn-info')){				//
            $(this).addClass('btn-danger');			//
            $(this).removeClass('btn-info','btn-success');
        } else {										//
            $(this).addClass('btn-info');			//
            $(this).removeClass('btn-danger','btn-success');
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
    alert("le formulaire est complété");
    return resultat;
}

//concordance de deux champs
function concordance(champ1, champ2)
{
    /* 	console.log("champ1: ", champ1);
        console.log("champ2: ", champ2);
        console.log("concordance: ", champ1==champ2); */
    return champ1 == champ2;
}

/* ######## SEMANTIC ADAPTED ######## */
// $("#valider").click
// (
// function()
// {
alert("bonjour");
// var resultat = estComplet();
//récupération des valeurs
// var nom = $("#nom").val();
// var email1 = $("#email1").val();
// var email2 = $("#email2").val();
// var mdp1 = $("#mdp1").val();
// var mdp2 = $("#mdp2").val();
// if(resultat)
// {
// if(!concordance(email1, email2)){
//mails non concordants.
//on colore en orange les champs correspondants à email1 et email2.
// alert("emails non identiques !!!");
// $("#email1, #email2").addClass('orange');
// } /* if(!concordance(mdp1, mdp2)){
//mots de passe non concordants.
//on colore en orange les champs correspondants à mdp1 et mdp2.
// $("#mdp1, #mdp2").addClass('bg-warning');
// alert("mots de passe non identiques !!!");
// } */ else {
//OBJECTIF les champs concordants sont colorés en vert (adresse, mdp).
//mots de passe concordants et mails concordants.
//on colore en vert tous les champs.
// $(".saisie").addClass('green');
// }
// }
//BONUS : test changement de couleur : BONUS /////
// if($(this).hasClass('grey')){				//
// $(this).toggleClass('red');			//
// } else {										//
// $(this).toggleClass('grey');			//
// }/////////////////////////////////////////////////

// }

// );

//click sur des éléments de saisie
// $(".saisie").focus
// (
// function()
// {
// $(this).removeClass('orange');
// }
// );

//fonctions définies
// function estComplet()
// {
// resultat = true;
//parcours de tous les éléments de la classe 'saisie'.
// $(".saisie").each
// (
// function()
// {
//on recupère la valuer de l'objet courant
// let valeur = $(this).val();
// if(!valeur){
// resultat=false;
// $(this).addClass('orange');
// }
// }
// );
// alert("le formulaire est complété");
// return resultat;
// }

//concordance de deux champs
// function concordance(champ1, champ2)
// {
// /* 	console.log("champ1: ", champ1);
// console.log("champ2: ", champ2);
// console.log("concordance: ", champ1==champ2); */
// return champ1 == champ2;
// }

