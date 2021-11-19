package com.even.présentation.présenteur

import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.SourceDeDonnéesAPI

class PrésentateurListeÉvénements(
    val vue : IListeEvenements.IVue,
) : IListeEvenements.IPrésentateur {

    private val modèle : ModèleÉvénements = ModèleÉvénements(SourceDeDonnéesAPI())

    override fun traiterRequêteAfficherListeRecents() {
        vue.afficherListeEvenementsRecents(modèle.Événements)
    }

}