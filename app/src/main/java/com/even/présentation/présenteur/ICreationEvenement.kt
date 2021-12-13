package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface ICreationEvenement {
    interface IVue {

        /**
         * Méthode qui permet de rediriger vers le nouvel événement une fois que celui-ci est créé
         *
         * @param evenement L'événement à afficher
         */
        fun afficherNouvelÉvénement(evenement : Événement)

        /**
         * Affiche simplement un toast qui indique à l'utilisateur qu'une erreur est survenu.
         *
         */
        fun afficherErreurConnexion()
    }

    interface IPrésentateur {

        /**
         * Méthode qui permet d'ajouter un nouvel événement à la source de donnée.
         *
         * @param nom Nom de l'événement
         * @param date Date de l'événement
         * @param location Endroit de l'événement
         * @param description Description de l'événement
         */
        fun traiterRequêteCréerÉvénement(
            nom : String,
            date : String,
            location : String,
            description : String
        )
    }
}