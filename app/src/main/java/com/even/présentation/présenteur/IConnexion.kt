package com.even.présentation.présenteur

import com.even.testOuvert

@testOuvert
interface IConnexion {
    interface IVue {
        fun naviguerVersFragmentPrincipal()
        fun naviguerVersFragmentEnregistgrement()
        fun afficherToastSuccesConnexion()
        fun afficherToastErreurConnexion()
        fun afficherToastErreurServeur()
        fun afficherErreurNomUtilisateur(afficherEnRouge: Boolean)
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
