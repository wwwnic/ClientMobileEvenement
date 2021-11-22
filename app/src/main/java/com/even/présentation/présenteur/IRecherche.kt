package com.even.présentation.présenteur

interface IRecherche {
    interface IVue {
        fun afficherRésultatsRecherche(tag : String)
        fun afficherMessageAucunMotCle()
    }

    interface IPrésentateur {
        fun traiterRequêteRechercheÉvénement(nom : String,mois : String,location : String,organisateur : String)
    }
}