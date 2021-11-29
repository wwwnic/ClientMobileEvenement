package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IDétailÉvenement {

    interface IVue {
        fun afficherToastErreurServeur()
        fun afficherToastParticipationAjouté()
        fun afficherToastParticipationRetiré()
        fun setInfo(evenement: Événement)
        fun afficherNePlusParticiper()
        fun afficherParticipation()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherDétailÉvenement(id : Int)
    }

}