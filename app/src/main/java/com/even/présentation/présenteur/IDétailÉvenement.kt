package com.even.présentation.présenteur

import com.even.domaine.entité.Événement
import retrofit2.Response

interface IDétailÉvenement {

    interface IVue {
        fun afficherToastErreurServeur()
        fun setInfo()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherDétailÉvenement(id : Int) : Response<Événement>?
    }

}