package com.even.présentation.présenteur

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