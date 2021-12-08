package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface ICreationEvenement {
    interface IVue {
        fun afficherNouvelÉvénement(evenement : Événement)
        fun afficherErreurConnexion()
    }

    interface IPrésentateur {
        fun traiterRequêteCréerÉvénement(
            nom : String,
            date : String,
            location : String,
            description : String
        )
    }
}