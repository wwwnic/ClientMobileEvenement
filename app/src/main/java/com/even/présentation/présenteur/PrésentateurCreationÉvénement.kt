package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Permet de faire les traitements dans la vue de création d'événement
 *
 * @property vue La vue VueCréationÉvénement
 */
class PrésentateurCreationÉvénement(
    val vue: ICreationEvenement.IVue
) : ICreationEvenement.IPrésentateur {

    val modèleÉvénements = ModèleÉvénements()

    /**
     * Méthode qui permet d'ajouter un nouvel événement à la source de donnée.
     *
     * @param nom Nom de l'événement
     * @param date Date de l'événement
     * @param location Endroit de l'événement
     * @param description Description de l'événement
     */
    override fun traiterRequêteCréerÉvénement(
        nom: String,
        date: String,
        location: String,
        description: String
    ) {
        var dateÉvénement = date.substring(0, 10) + "T" + date.substring(11)
        var événementCrée: Événement = Événement(
            0,
            nom,
            location,
            dateÉvénement,
            ModèleAuthentification.utilisateurConnecté!!.idUtilisateur!!,
            description
        )
        var nouvelÉvénement: Événement?
        CoroutineScope(Dispatchers.IO).launch {
            try {
                nouvelÉvénement = modèleÉvénements.créerÉvénement(événementCrée)
                withContext(Dispatchers.Main) {
                    if (nouvelÉvénement != null) {
                        modèleÉvénements.setÉvénementPrésenté(nouvelÉvénement!!.idEvenement)
                        vue.afficherNouvelÉvénement(nouvelÉvénement!!)
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
}