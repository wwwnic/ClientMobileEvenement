package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

class IMesÉvènements {
    interface IVue {
        fun afficherListeEvenements(listeEvens: List<Événement>, imageUrl: (Int) -> String)
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherLesParticipations()
    }
}