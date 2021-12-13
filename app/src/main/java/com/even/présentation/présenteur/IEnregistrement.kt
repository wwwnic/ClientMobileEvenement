package com.even.présentation.présenteur

interface IEnregistrement {
    interface IVue {
        fun naviguerVersConnexion()
        fun afficherToastSuccesEnregistrement()
        fun afficherToastErreurEnregistrement()
        fun afficherToastErreurServeur()
        fun afficherErreurNomUsager(afficherEnRouge: Boolean)
        fun afficherErreurMotDePasse(afficherEnRouge: Boolean)
        fun afficherErreurCourriel(afficherEnRouge: Boolean)
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
