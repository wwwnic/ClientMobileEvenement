package com.even.domaine.interacteur

class IConnexion {
    interface IVue {
    }

    interface IPrésentateur {
        fun traiterRequêteDemanderProfilPourConnexion(
            nomUtilisateur: CharSequence,
            motDePasse: CharSequence
        )
    }
}
