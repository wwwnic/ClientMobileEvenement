package com.even.présentation.présenteur

class IConnexion {
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
        fun traiterRequêteDemanderProfilPourConnexion(
            nomUtilisateur: CharSequence,
            motDePasse: CharSequence
        )
    }
}
