package com.even.présentation.présenteur

import android.util.Log
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Permet de faire les traitements dans la vue de modification d'événement
 *
 * @property vue La vue VueModifierÉvénement
 */
class PrésentateurModification(
    val vue : IModificationÉvénement.IVue
) : IModificationÉvénement.IPrésentateur {

    val modèleÉvénements = ModèleÉvénements()

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
        var événementModifié = ModèleÉvénements.événementPrésenté
        événementModifié!!.nomEvenement = nom
        événementModifié!!.date = dateÉvénement
        événementModifié!!.location = location
        événementModifié!!.description = description
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reponse = modèleÉvénements.modifierÉvénement(événementModifié)
                withContext(Dispatchers.Main) {
                    if (reponse.isSuccessful) {
                        modèleÉvénements.setÉvénementPrésenté(événementModifié.idEvenement)
                        vue.afficherÉvénementModifiéOuRetourMenu(true)
                    } else {
                        vue.afficherErreurConnexion()
                    }
                }
            } catch (e: Exception) {
                Log.e("Évèn", "La requête a rencontré une erreur", e)
                withContext(Dispatchers.Main) {
                    vue.afficherErreurConnexion()
                }
            }
        }
    }

    /**
     * Méthode qui permet d'annuler un événement.
     *
     */
    override fun traiterRequêteAnnulerÉvénement() {
        var événementAnnulé = ModèleÉvénements.événementPrésenté
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reponse = modèleÉvénements.annulerÉvénement(événementAnnulé!!.idEvenement)
                withContext(Dispatchers.Main) {
                    if (reponse.isSuccessful) {
                        ModèleÉvénements.événementPrésenté = null
                        vue.afficherÉvénementModifiéOuRetourMenu(false)
                    } else {
                        vue.afficherErreurConnexion()
                    }
                }
            } catch (e: Exception) {
                Log.e("Évèn", "La requête a rencontré une erreur", e)
                withContext(Dispatchers.Main) {
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
        vue.remplirChamps(ModèleÉvénements.événementPrésenté!!)
    }

}