package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IListeEvenements {

    interface IVue {
        fun afficherListeEvenementsRecents(liste : List<Événement>)
        fun afficherErreurConnexion()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherListeRecents()
    }
}