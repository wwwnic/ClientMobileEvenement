package com.even.domaine.interacteur

interface IListeEvenements {

    interface IVue {
        fun afficherListeEvenementsRecents()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherListeRecents()
    }
}