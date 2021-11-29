package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IDétailÉvenement {

    interface IVue {
        fun afficherToastErreurServeur()
        fun setInfo(evenement: Événement)
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherDétailÉvenement(id : Int)
    }

}