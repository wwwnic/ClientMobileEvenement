package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IMesÉvènements {
    interface IVue {
        fun afficherListeEvenements(listeEvens: List<Événement>, imageUrl: (Int) -> String)
        fun afficherAucunRésultatRecherche(estErreurConnexion: Boolean)
    }

    interface IPrésentateur {
        fun traiterRequêtelancerCoroutine(estSurOngletMesÉvènement: Boolean)
    }
}