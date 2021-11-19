package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IListeEvenements {

    interface IVue {
        fun afficherListeEvenementsRecents(liste : List<Événement>)
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherListeRecents()
    }
}