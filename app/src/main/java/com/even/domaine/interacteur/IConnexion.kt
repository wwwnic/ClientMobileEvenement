package com.even.domaine.interacteur

class IConnexion {
    interface IVue {
        fun naviguerVersFragmentPrincipal()
    }

    interface IPrésentateur {
        fun traiterRequêteDemanderProfilPourConnexion(
            nomUtilisateur: CharSequence,
            motDePasse: CharSequence
        )
    }
}
