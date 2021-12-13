package com.even.présentation.présenteur

/**
 * Une liste de contact représentant un lien entre la vue enregistrement et le présentateur enregistrement
 *
 */
interface IEnregistrement {
    interface IVue {
        /**
         * Navigue vers l'écran connexion
         *
         */
        fun naviguerVersConnexion()

        /**
         * Affiche un toast qui indique que l'enregistrement s'est bien déroulée
         *
         */
        fun afficherToastSuccesEnregistrement()

        /**
         * Affiche un toast qui indique une erreur d'enregistrement
         *
         */
        fun afficherToastErreurEnregistrement()

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
        fun afficherErreurNomUsager(afficherEnRouge: Boolean)

        /**
         * Change la couleur de la zone contenant le mot de passe afin de permettre à l'utilisateur
         * de réaliser qu'il a fait une erreur dans le champ.
         *
         * @param afficherEnRouge si la zone doit etre affichée en rouge
         */
        fun afficherErreurMotDePasse(afficherEnRouge: Boolean)

        /**
         * Change la couleur de la zone contenant le courriel afin de permettre à l'utilisateur
         * de réaliser qu'il a fait une erreur dans le champ.
         *
         * @param afficherEnRouge si la zone doit etre affichée en rouge
         */
        fun afficherErreurCourriel(afficherEnRouge: Boolean)

        /**
         * Change la couleur de la zone contenant le telephone afin de permettre à l'utilisateur
         * de réaliser qu'il a fait une erreur dans le champ.
         *
         * @param afficherEnRouge si la zone doit etre affichée en rouge
         */
        fun afficherErreurTéléphone(afficherEnRouge: Boolean)
    }

    interface IPrésentateur {
        /**
         * Vérifie les informations entrées, si elles sont valides, envoie une requête contenant les informations au modèle
         *
         * @param nomUtilisateur Un nom qu'un utilisateur a entré
         * @param motDePasse Un mot de passe qu'un utilisateur a entré
         * @param courriel Un courriel qu'un utilisateur a entré
         * @param telephone Un telephone qu'un utilisateur a entré
         */
        fun traiterRequêteReclamerEnregistrement(
            nomUsager: CharSequence,
            motDePasse: CharSequence,
            courriel: CharSequence,
            telephone: CharSequence
        )

        /**
         * Valide le nom et le mot de passe qu'un utilisateur a rentré.
         *
         * @param nomUsager Un nom qu'un utilisateur a entré
         * @param motDePasse Un mot de passe qu'un utilisateur a entré
         * @param courriel Un courriel qu'un utilisateur a entré
         * @param telephone Un telephone qu'un utilisateur a entré
         * @return Les entrées de l'utilisateur sont valides
         */
        fun traiterRequêteValiderTousLesEntrées(
            nomUsager: CharSequence,
            motDePasse: CharSequence,
            courriel: CharSequence,
            telephone: CharSequence
        ): Boolean
    }
}
