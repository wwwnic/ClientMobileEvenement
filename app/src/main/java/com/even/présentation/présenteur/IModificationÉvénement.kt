package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IModificationÉvénement {
    interface IVue {
        fun afficherÉvénementModifiéOuRetourMenu(modification : Boolean)
        fun remplirChamps(événement : Événement)
        fun afficherErreurConnexion()
    }

    interface IPrésentateur {
        fun traiterRequêteModifierÉvénement(
            nom : String,
            date : String,
            location : String,
            description : String
        )
        fun traiterRequêteAnnulerÉvénement()
        fun traiterRequêteRemplirChamps()
    }
}