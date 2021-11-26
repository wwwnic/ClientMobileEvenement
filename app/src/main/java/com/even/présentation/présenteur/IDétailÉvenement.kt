package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IDétailÉvenement {

    interface IVue {
        fun afficherToastErreurServeur()
        fun setInfo()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherDétailÉvenement(id : Int)
    }

}