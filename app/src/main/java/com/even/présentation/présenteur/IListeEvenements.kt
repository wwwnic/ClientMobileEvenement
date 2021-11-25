package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IListeEvenements {

    interface IVue {
        fun afficherListeEvenements(liste : List<Événement>, imageUrl : (Int) -> String)
        fun afficherErreurConnexion()
        fun afficherAucunRésultatRecherche()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherListeRecents()
        fun traiterRequêteAfficherListeRecherche(tag : String)
    }
}