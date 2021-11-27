package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IDétailÉvenement {

    interface IVue {
        fun afficherToastErreurServeur()
        fun afficherToastParticipationAjouté()
        fun setInfo(evenement: Événement, participant : Boolean)
        fun afficherNePlusParticiper()
        fun afficherParticipation()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherDétailÉvenement(id : Int)
        fun traiterRequêteAjouterParticipation(idEvenement: Int)
    }

}