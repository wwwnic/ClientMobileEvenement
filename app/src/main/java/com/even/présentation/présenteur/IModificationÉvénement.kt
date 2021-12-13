package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IModificationÉvénement {
    interface IVue {

        /**
         * TODO
         *
         * @param modification
         */
        fun afficherÉvénementModifiéOuRetourMenu(modification : Boolean)

        /**
         * Méthode qui permet de remplir les champs lorsque la vue est appelé à partir des informations
         * de l'événement en paramètre
         *
         * @param événement L'événement sélectionné.
         */
        fun remplirChamps(événement : Événement)

        /**
         * Affiche simplement un toast lorsqu'une erreur survient.
         *
         */
        fun afficherErreurConnexion()
    }

    interface IPrésentateur {

        /**
         * Méthode qui permet de faire la modification d'un événement
         *
         * @param nom Nouveau nom de l'événement
         * @param date Nouvelle date de l'événement
         * @param location Nouvelle location de l'événement
         * @param description Nouvelle description de l'événement
         */
        fun traiterRequêteModifierÉvénement(
            nom : String,
            date : String,
            location : String,
            description : String
        )

        /**
         * Méthode qui permet d'annuler un événement.
         *
         */
        fun traiterRequêteAnnulerÉvénement()

        /**
         * Méthode qui permet de faire l'affichage des informations actuelles de l'événement
         * sur la vue.
         *
         */
        fun traiterRequêteRemplirChamps()
    }
}