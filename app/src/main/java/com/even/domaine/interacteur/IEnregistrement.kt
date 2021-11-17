package com.even.domaine.interacteur

interface IEnregistrement {

    interface IVue {
        fun naviguerVersConnexion()
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