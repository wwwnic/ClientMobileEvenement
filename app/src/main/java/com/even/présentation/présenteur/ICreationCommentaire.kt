package com.even.présentation.présenteur

interface ICreationCommentaire {

    interface IVue {

        /**
         * Méthode qui permet de rediriger vers la vue des détails de l'événement lorsque
         * l'utilisateur a ajouté un commentaire.
         *
         */
        fun afficherVueDétailsÉvénement()

        /**
         * Méthode qui permet de modifier le nom de l'événement
         *
         * @param nom Nouveau nom à remplacer
         */
        fun afficherNomÉvénement(nom : String)

        /**
         * Affiche simplement un toast disant qu'une erreur niveau serveur est survenu.
         *
         */
        fun afficherToastErreurServeur()
    }

    interface IPrésentateur {

        /**
         * Méthode qui permet de faire l'affichage dans la vue du nom de l'événement sélectionné.
         *
         */
        fun traiterRequêteAfficherNomÉvénement()

        /**
         * Méthode qui permet de faire l'ajout d'un commentaire dans l'événement sélectionné.
         * Lorsque le commentaire est ajouté, l'utilisateur est rediriger sur la vue détail de l'événement.
         *
         * @param texte Texte du commentaire en question
         */
        fun traiterRequêteAjouterCommentaire(texte : String)
    }
}