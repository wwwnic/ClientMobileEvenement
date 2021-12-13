package com.even.présentation.présenteur

interface IRecherche {
    interface IVue {

        /**
         * Méthode qui permet de faire l'affichage de la liste d'événement retourné après la recherche
         *
         * @param tag Chaine de caratère de la recherche
         */
        fun afficherRésultatsRecherche(tag : String)

        /**
         * Méthode qui affiche simplement un toast lorsqu'aucun mot-clé n'est entré.
         *
         */
        fun afficherMessageAucunMotCle()
    }

    interface IPrésentateur {

        /**
         * Méthode qui permet de faire une recherche d'événement à partir des critères établies
         *
         * @param nom Nom de l'événement
         * @param mois Mois durant lequel l'événement aura lieu
         * @param location Endroit dans lequel l'événement aura lieu
         * @param organisateur Organisateur de l'événement
         */
        fun traiterRequêteRechercheÉvénement(nom : String,mois : String,location : String,organisateur : String)
    }
}