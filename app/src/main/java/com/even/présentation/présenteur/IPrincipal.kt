package com.even.présentation.présenteur

/**
 * Contrat entre la vue et le présentateur de la vue principale.
 */
interface IPrincipal {

    interface IVue {
        /**
         * Affiche la page de connexion après la déconnexion.
         */
        fun afficherPageConnexion()
    }

    interface IPrésentateur {
        /**
         * Traite la requête de déconnexion.
         */
        fun traiterRequêteDéconnexion()
    }
}