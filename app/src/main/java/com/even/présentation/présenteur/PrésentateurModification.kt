package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.UnCoroutineDispatcher
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Permet de faire les traitements dans la vue de modification d'événement
 *
 * @property vue La vue VueModifierÉvénement
 */
class PrésentateurModification(
    val vue: IModificationÉvénement.IVue,
    val modèleÉvénements: ModèleÉvénements,
    val dispatcher: UnCoroutineDispatcher

) : IModificationÉvénement.IPrésentateur {


    /**
     * Méthode qui permet de faire la modification d'un événement
     *
     * @param nom Nouveau nom de l'événement
     * @param date Nouvelle date de l'événement
     * @param location Nouvelle location de l'événement
     * @param description Nouvelle description de l'événement
     */
    override fun traiterRequêteModifierÉvénement(
        nom: String,
        date: String,
        location: String,
        description: String
    ) {
        var dateÉvénement = date.substring(0, 10) + "T" + date.substring(11)
        var événementModifié =
            modifierEvenement(nom, dateÉvénement, location, description)
        CoroutineScope(dispatcher.io).launch {
            try {
                val reponse = modèleÉvénements.modifierÉvénement(événementModifié)
                modèleÉvénements.setÉvénementPrésenté(événementModifié.idEvenement)
                withContext(dispatcher.main) {
                    if (reponse.isSuccessful) {
                        vue.afficherÉvénementModifiéOuRetourMenu(true)
                    } else {
                        vue.afficherErreurConnexion()
                    }
                }
            } catch (e: Exception) {
                Log.e("Évèn", "La requête a rencontré une erreur", e)
                withContext(dispatcher.main) {
                    vue.afficherErreurConnexion()
                }
            }
        }
    }

    /**
     * Modifie l'événement dans le modele puis le retourne
     *
     * @param nom le nouveau nom
     * @param dateÉvénement la nouvelle date
     * @param location la nouvelle location
     * @param description la nouvelle description
     * @return l'évènement modifié
     */
    private fun modifierEvenement(
        nom: String,
        dateÉvénement: String,
        location: String,
        description: String
    ): Événement {
        var événementModifié = modèleÉvénements.obtenirÉvénement()
        événementModifié!!.nomEvenement = nom
        événementModifié.date = dateÉvénement
        événementModifié.location = location
        événementModifié.description = description
        return événementModifié
    }

    /**
     * Méthode qui permet d'annuler un événement.
     *
     */
    override fun traiterRequêteAnnulerÉvénement() {
        var événementAnnulé = modèleÉvénements.obtenirÉvénement()
        CoroutineScope(dispatcher.io).launch {
            try {
                val reponse = modèleÉvénements.annulerÉvénement(événementAnnulé!!.idEvenement)
                withContext(dispatcher.main) {
                    if (reponse.isSuccessful) {
                        modèleÉvénements.ajouterUnÉvénement(null)
                        vue.afficherÉvénementModifiéOuRetourMenu(false)
                    } else {
                        vue.afficherErreurConnexion()
                    }
                }
            } catch (e: Exception) {
                Log.e("Évèn", "La requête a rencontré une erreur", e)
                withContext(dispatcher.main) {
                    vue.afficherErreurConnexion()
                }
            }
        }
    }

    /**
     * Méthode qui permet de faire l'affichage des informations actuelles de l'événement
     * sur la vue.
     *
     */
    override fun traiterRequêteRemplirChamps() {
        vue.remplirChamps(modèleÉvénements.obtenirÉvénement()!!)
    }

}