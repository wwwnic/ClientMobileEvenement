package com.even.présentation.présenteur

/**
 * Une liste de contact représentant un lien entre la vue enregistrement et le présentateur connexion
 *
 */
interface IConnexion {
    interface IVue {
        /**
         * Navigue vers le fragment principal
         *
         */
        fun naviguerVersFragmentPrincipal()

        /**
         * Navigue vers le fragment enregistrement
         *
         */
        fun naviguerVersFragmentEnregistgrement()

        /**
         * Affiche un toast qui indique que la connexion s'est bien déroulée
         *
         */
        fun afficherToastSuccesConnexion()

        /**
         * Affiche un toast qui indique une erreur de connexion du à des informations invalides
         *
         */
        fun afficherToastErreurConnexion()

        /**
         * Affiche un toast qui indique une erreur de connexion avec le serveur
         *
         */
        fun afficherToastErreurServeur()

        /**
         * Change la couleur de la zone contenant le nom utilisateur afin de permettre à l'utilisateur
         * de réaliser qu'il a fait une erreur dans le champ.
         *
         * @param afficherEnRouge si la zone doit etre affichée en rouge
         */
        fun afficherErreurNomUtilisateur(afficherEnRouge: Boolean)

        /**
         * Change la couleur de la zone contenant le mot de passe afin de permettre à l'utilisateur
         * de réaliser qu'il a fait une erreur dans le champ.
         *
         * @param afficherEnRouge si la zone doit etre affichée en rouge
         */
        fun afficherErreurMotDePasse(afficherEnRouge: Boolean)
    }

    interface IPrésentateur {
        /**
         * Vérifie les informations entrées, si elles sont valides, envoie une requête contenant les informations au modèle
         *
         * @param nomUtilisateur Un nom qu'un utilisateur a entré
         * @param motDePasse Un mot de passe qu'un utilisateur a entré
         */
        fun traiterRequêteDemanderProfilPourConnexion(
            nomUtilisateur: CharSequence,
            motDePasse: CharSequence
        )
    }
}
