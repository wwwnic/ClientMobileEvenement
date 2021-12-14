package com.even.présentation.présenteur

import com.even.présentation.modèle.ModèleAuthentification

/**
 * Présentateur de la vue principale pour traiter la déconnexion.
 */
class PrésentateurPrincipal(
    val vue : IPrincipal.IVue
) : IPrincipal.IPrésentateur {

    /**
     * Retire l'utilisateur du modèle et affiche la page de connexion.
     */
    override fun traiterRequêteDéconnexion() {
        ModèleAuthentification.utilisateurConnecté = null
        vue.afficherPageConnexion()
    }
}