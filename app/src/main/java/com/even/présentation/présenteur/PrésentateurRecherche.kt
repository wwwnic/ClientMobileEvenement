package com.even.présentation.présenteur

class PrésentateurRecherche(
    val vue : IRecherche.IVue
) : IRecherche.IPrésentateur{

    /**
     * Méthode qui permet de faire une recherche d'événement à partir des critères établies
     *
     * @param nom Nom de l'événement
     * @param mois Mois durant lequel l'événement aura lieu
     * @param location Endroit dans lequel l'événement aura lieu
     * @param organisateur Organisateur de l'événement
     */
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