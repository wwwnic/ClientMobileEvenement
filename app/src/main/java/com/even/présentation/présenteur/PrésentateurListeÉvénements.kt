package com.even.présentation.présenteur


import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleÉvénements
import com.even.testOuvert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

/**
 * Permet de faire les traitements dans la vue de la liste d'événement
 *
 * @property vue La vue VueListeÉvénement
 */
@testOuvert
class PrésentateurListeÉvénements(
    val vue: IListeEvenements.IVue,
    var modèleÉvénements: ModèleÉvénements
) : IListeEvenements.IPrésentateur {

    lateinit var liste: List<Événement>

    /**
     * Méthode qui permet d'aller chercher la liste de tous les événements à afficher et de la passer
     * à la vue pour faire l'affichage.
     *
     */
    override fun traiterRequêteAfficherListeRecents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                liste = modèleÉvénements.getÉvénementsRécents()
                withContext(Dispatchers.Main) {
                    if (!liste.isEmpty()) {
                        vue.afficherListeEvenements(liste,
                            { i -> ModèleÉvénements().getImageÉvénement(i) })

                    } else {
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

    /**
     * Méthode qui permet d'aller chercher la liste d'événement à partir d'une recherche et de la passer
     * à la vue pour faire l'affichage.
     *
     * @param tag Chaine de caractère qui représente la recherche de l'utilisateur.
     */
    override fun traiterRequêteAfficherListeRecherche(tag: String) {
        var motCles = tag.split(":")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                liste = modèleÉvénements.getÉvénementsParRecherche(
                    motCles[0],
                    motCles[1],
                    motCles[2],
                    motCles[3]
                )
                if (!liste.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        vue.afficherListeEvenements(liste,
                            { i -> ModèleÉvénements().getImageÉvénement(i) })
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

    /**
     * Méthode qui permet de mettre l'événement sélectionnéé dans le modèle et de rediriger vers la
     * vue des détails de l'événement sélectionné.
     *
     * @param idÉvénement Clé unique qui représente l'événement sélectionné.
     */
    override fun traiterRequêteAfficherDétailsÉvénement(idÉvénement: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                modèleÉvénements.setÉvénementPrésenté(idÉvénement)
                withContext(Dispatchers.Main) {
                    vue.afficherDétailsÉvénement()
                }
            } catch (e: SocketTimeoutException) {
                withContext(Dispatchers.Main) {
                    vue.afficherErreurConnexion()
                }
            }
        }
    }


}