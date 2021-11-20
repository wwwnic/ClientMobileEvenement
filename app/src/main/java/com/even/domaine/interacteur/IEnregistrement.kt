package com.even.domaine.interacteur

interface IEnregistrement {

    interface IVue {
        fun naviguerVersConnexion()
        fun afficherToastSuccesEnregistrement()
        fun afficherToastErreurEnregistrement()
        fun afficherErreurNomUsager()
        fun afficherErreurMotDePasse()
        fun afficherErreurCourriel()
        fun afficherErreurTelephone()
    }

    interface IPrésentateur {
        fun traiterRequêteReclamerEnregistrement(
            username: String,
            password: String,
            email: String,
            phone: String
        )

        fun traiterRequêteValiderNomUsager(nomUsager: CharSequence): Boolean

        fun traiterRequêteValiderMotDePasse(motDePasse: CharSequence): Boolean

        fun traiterRequêteValiderCourriel(courriel: CharSequence): Boolean

        fun traiterRequêteValiderTelephone(telephone: CharSequence): Boolean
    }
}