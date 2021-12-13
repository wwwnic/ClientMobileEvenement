package com.even.présentation.présenteur

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement

interface IDétailÉvenement {

    interface IVue {
        fun afficherToastErreurServeur()
        fun afficherToastAucunCommentaire()
        fun afficherToastParticipationAjouté()
        fun afficherToastParticipationRetiré()
        fun setInfo(evenement: Événement)
        fun setNombreParticipant(nombreParticipant: Int)
        fun afficherNePlusParticiper()
        fun afficherParticipation()
        fun cacherBoutonParticipation()
        fun afficherListeParticipants(participants : List<Utilisateur>,imageUrl : (Int) -> String)
        fun afficherListeCommentaires(commentaires : List<Commentaire>)
        fun afficherVueAjoutCommentaire()
        fun afficherApplicationCalendrierPourAjouter(date : IntArray)
        fun afficherApplicationCalendrierPourEffacer(date : IntArray)
        fun montrerLesDetailsEvenement()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherDétailÉvenement(id : Int)
        fun traiterRequêteAjouterRetirerParticipation(idEvenement: Int)
        fun traiterRequêteAfficherParticipants()
        fun traiterRequêteAfficherCommentaires()
    }

}