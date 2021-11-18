package com.even.domaine.interacteur

interface IEnregistrement {

    interface IVue {
        fun naviguerVersConnexion()
        fun afficherToastSuccesEnregistrement()
        fun afficherToastErreurEnregistrement()
    }

    interface IPrésentateur {
        fun traiterRequêteReclamerEnregistrement(
            username: String,
            password: String,
            email: String,
            phone: String
        )
    }
}