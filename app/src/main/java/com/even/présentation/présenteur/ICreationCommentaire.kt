package com.even.présentation.présenteur

interface ICreationCommentaire {

    interface IVue {
        fun afficherVueDétailsÉvénement()
        fun afficherNomÉvénement(nom : String)
        fun afficherToastErreurServeur()
    }

    interface IPrésentateur {
        fun traiterRequêteAfficherNomÉvénement()
        fun traiterRequêteAjouterCommentaire(texte : String)
    }
}