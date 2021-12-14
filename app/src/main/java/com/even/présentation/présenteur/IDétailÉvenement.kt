package com.even.présentation.présenteur

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement

interface IDétailÉvenement {

    interface IVue {

        /**
         * Affiche simplement un toast disant qu'une erreur niveau serveur est survenu.
         *
         */
        fun afficherToastErreurServeur()

        /**
         * Affiche simplement un toast qui indique qu'il n'y a aucun commentaire
         * pour l'événement sélectionné.
         *
         */
        fun afficherToastAucunCommentaire()

        /**
         * Affiche simplement un toast qui indique à l'utilisateur que sa participation
         * à un événement a bien été enregistré.
         *
         */
        fun afficherToastParticipationAjouté()

        /**
         * Affiche simplement un toast qui indique à l'utilisateur que sa participation
         * à un événement a bien été retiré.
         *
         */
        fun afficherToastParticipationRetiré()

        /**
         * Cette méthode sert à initialiser les informations concernant l'événement
         * à partir de l'objet événement qui lui est passé en paramètre.
         *
         * @param evenement Un objet événenement passé par le présentateur.
         */
        fun setInfo(evenement: Événement)

        /**
         * Cette méthode sert à initialiser le nombre de participant inscrit à l'événement
         * dans la vue des détails de l'événement
         *
         * @param nombreParticipant Le nombre correspondant au nombre total de partitipant
         * inscrit à l'événement en question.
         */
        fun setNombreParticipant(nombreParticipant: Int)

        /**
         * Change simplement le texte du bouton de participation si l'utilisateur sélectionné
         * participe déjà à l'événement.
         *
         */
        fun afficherNePlusParticiper()

        /**
         * Change simplement le texte du bouton de participation si l'utilisateur sélectionné
         * ne participe pas encore à l'événement.
         *
         */
        fun afficherParticipation()

        /**
         * Permet de cacher le bouton de participation lorsque l'utilisateur connecté
         * est aussi l'organisateur de l'événement.
         *
         */
        fun cacherBoutonParticipation()

        /**
         * Cette méthode permet de faire l'affichage de la liste complète des participants
         * inscrit à l'événement sélectionné.
         *
         * @param participants La liste de tous les participants.
         * @param imageUrl L'url de l'image de chaque utilisateur.
         */
        fun afficherListeParticipants(participants : List<Utilisateur>,imageUrl : (Int) -> String)

        /**
         * Cette méthode permet de faire l'affichage de la liste complète des commentaires
         * laissé relié à l'événement sélectionné.
         *
         * @param commentaires La liste de tous les commentaires de l'événement
         */
        fun afficherListeCommentaires(commentaires : List<Commentaire>)

        /**
         * Permet de rediriger vers la vue d'ajout de commentaire.
         *
         */
        fun afficherVueAjoutCommentaire()

        /**
         * Cette méthode permet d'ouvrir le calendrier et remplir automatiquement
         * les champs en fonction des informations de l'événement lorsque
         * l'utilisateur clique sur le bouton participer.
         *
         * @param date représente la date de l'événement qui sera ajouté au calendrier
         */
        fun afficherApplicationCalendrierPourAjouter(date : IntArray)

        /**
         * Cette méthode cache la vue de chargement lorsque les informations de l'événement
         * sont chargé correctement.
         *
         */
        fun montrerLesDetailsEvenement()
    }

    interface IPrésentateur {

        /**
         * Cette méthode permet d'interroger les différents modèles afin d'aller chercher les informations
         * d'un événement. C'est cette méthode qui s'occupera de l'affichage des détails de l'événement
         * lorsqu'un événement est sélectionné.
         *
         * @param idEvenement C'est la clé unique qui identifie l'événement.
         */
        fun traiterRequêteAfficherDétailÉvenement(idEvenement : Int)

        /**
         * Cette méthode s'occupe d'ajouter ou retirer la participation d'un utilisateur en fonction
         * de son status de participation qui est initialisé lorsque la vue est chargé.     *
         *
         * @param idEvenement C'est la clé unique qui identifie l'événement.
         */
        fun traiterRequêteAjouterRetirerParticipation(idEvenement: Int)

        /**
         * Traite la requête pour faire un ajout au calendrier en passant une date configurée à la vue
         * pour que celle-ci puisse ouvrir l'application calendrier de l'utilisateur.
         */
        fun traiterRequêteAjouterAuCalendrier()

        /**
         * Permet d'aller chercher la liste de participant à partir du modèle et de la passer
         * à la vue pour en faire l'affichage.
         *
         */
        fun traiterRequêteAfficherParticipants()

        /**
         * Permet d'aller chercher la liste de commentaire à partir du modèle et de la passer
         * à la vue pour en faire l'affichage.
         *
         */
        fun traiterRequêteAfficherCommentaires()
    }

}