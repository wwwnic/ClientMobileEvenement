package com.even.présentation.présenteur

import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.SourceDeDonnéesAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrésentateurListeÉvénements(
    val vue : IListeEvenements.IVue,
) : IListeEvenements.IPrésentateur {


    override fun traiterRequêteAfficherListeRecents() {
        CoroutineScope(Dispatchers.IO).launch {
            var liste = ModèleÉvénements().getÉvénementsRécents()
            withContext(Dispatchers.Main) {
                vue.afficherListeEvenementsRecents(liste)
            }
        }
    }

}