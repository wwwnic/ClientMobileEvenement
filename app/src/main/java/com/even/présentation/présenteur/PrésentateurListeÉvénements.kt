package com.even.présentation.présenteur

import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.*

class PrésentateurListeÉvénements(
    val vue : IListeEvenements.IVue,
) : IListeEvenements.IPrésentateur {

    override fun traiterRequêteAfficherListeRecents() {
        val handler = CoroutineExceptionHandler {
            _, exception -> vue.afficherErreurConnexion()
        }
        CoroutineScope(Dispatchers.Main).launch(handler) {
            var liste = ModèleÉvénements().getÉvénementsRécents()
            withContext(Dispatchers.Main) {
                vue.afficherListeEvenementsRecents(liste)
            }
        }
    }
}