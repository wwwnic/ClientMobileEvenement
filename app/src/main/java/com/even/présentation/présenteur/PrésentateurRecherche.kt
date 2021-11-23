package com.even.présentation.présenteur

class PrésentateurRecherche(
    val vue : IRecherche.IVue
) : IRecherche.IPrésentateur{
    override fun traiterRequêteRechercheÉvénement(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ) {
        var tag = "$nom:$mois:$location:$organisateur"
        if (tag.length == 3){
            vue.afficherMessageAucunMotCle()
        } else {
            vue.afficherRésultatsRecherche(tag)
        }
    }
}