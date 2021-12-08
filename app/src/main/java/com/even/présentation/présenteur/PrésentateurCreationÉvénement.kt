package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleConnexion
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrésentateurCreationÉvénement(
    val vue: ICreationEvenement.IVue
) : ICreationEvenement.IPrésentateur {

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
            ModèleConnexion.utilisateurConnecté!!.idUtilisateur!!,
            description
        )
        var nouvelÉvénement: Événement?
        CoroutineScope(Dispatchers.IO).launch {
            try {
                nouvelÉvénement = ModèleÉvénements().créerÉvénement(événementCrée)
                withContext(Dispatchers.Main) {
                    if (nouvelÉvénement != null) {
                        ModèleÉvénements.setÉvénementPrésenté(nouvelÉvénement!!.idEvenement)
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