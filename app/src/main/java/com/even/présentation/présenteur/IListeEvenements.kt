package com.even.présentation.présenteur

import com.even.domaine.entité.Événement

interface IListeEvenements {

    interface IVue {

        /**
         * Méthode qui permet de faire l'affichage des événements sous forme de carte.
         *
         * @param listeEvens Liste d'événement à afficher
         * @param imageUrl Chaine de caractère de l'url de l'image
         */
        fun afficherListeEvenements(liste : List<Événement>, imageUrl : (Int) -> String)

        /**
         * Méthode qui permet d'afficher une image et un texte lorsqu'une erreur survient.
         *
         */
        fun afficherErreurConnexion()

        /**
         * Méthode qui permet d'afficher une image et un texte lorsqu'aucun événement n'est retourné
         * lors d'une recherche.
         *
         */
        fun afficherAucunRésultatRecherche()

        /**
         * Permet de rediriger vers les détails d'un événement
         *
         */
        fun afficherDétailsÉvénement()
    }

    interface IPrésentateur {

        /**
         * Méthode qui permet d'aller chercher la liste de tous les événements à afficher et de la passer
         * à la vue pour faire l'affichage.
         *
         */
        fun traiterRequêteAfficherListeRecents()

        /**
         * Méthode qui permet d'aller chercher la liste d'événement à partir d'une recherche et de la passer
         * à la vue pour faire l'affichage.
         *
         * @param tag Chaine de caractère qui représente la recherche de l'utilisateur.
         */
        fun traiterRequêteAfficherListeRecherche(tag : String)

        /**
         * Méthode qui permet de mettre l'événement sélectionnéé dans le modèle et de rediriger vers la
         * vue des détails de l'événement sélectionné.
         *
         * @param idÉvénement Clé unique qui représente l'événement sélectionné.
         */
        fun traiterRequêteAfficherDétailsÉvénement(idÉvénement : Int)
    }
}