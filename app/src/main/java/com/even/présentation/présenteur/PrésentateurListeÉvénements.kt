package com.even.présentation.présenteur


import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class PrésentateurListeÉvénements(
    val vue : IListeEvenements.IVue,
) : IListeEvenements.IPrésentateur {

    lateinit var liste : List<Événement>

    override fun traiterRequêteAfficherListeRecents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                liste = ModèleÉvénements().getÉvénementsRécents()
                if (!liste.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        vue.afficherListeEvenements(liste,{ i -> ModèleÉvénements().getImageÉvénement(i)})
                    }
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    vue.afficherErreurConnexion()
                }
            }
        }
    }

    override fun traiterRequêteAfficherListeRecherche(tag : String) {
        var motCles = tag.split(":")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                liste = ModèleÉvénements().getÉvénementsParRecherche(
                    motCles[0],
                    motCles[1],
                    motCles[2],
                    motCles[3]
                )
                if (!liste.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        vue.afficherListeEvenements(liste,{ i -> ModèleÉvénements().getImageÉvénement(i)})
                    }
                } else {
                    withContext((Dispatchers.Main)) {
                        vue.afficherAucunRésultatRecherche()
                    }
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    vue.afficherErreurConnexion()
                }
            }
        }
    }


}