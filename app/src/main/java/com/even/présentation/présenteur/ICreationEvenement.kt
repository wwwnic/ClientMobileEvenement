package com.even.présentation.présenteur

interface ICreationEvenement {
    interface IVue {
        fun afficherNouvelÉvénement()
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