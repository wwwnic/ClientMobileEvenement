package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class PrésentateurCreation(
    val vue : ICreationEvenement.IVue
) : ICreationEvenement.IPrésentateur {

    override fun traiterRequêteCréerÉvénement(
        nom : String,
        date : String,
        location : String,
        description : String
    ) {
        var dateÉvénement = date.substring(0,10)+"T"+date.substring(11)
        var événementCrée : Événement = Événement(
            0,
            nom,
            location,
            dateÉvénement,
            2,
            description
        )
        var nouvelÉvénement : Événement?
        CoroutineScope(Dispatchers.IO).launch {
            try {
                nouvelÉvénement = ModèleÉvénements().créerÉvénement(événementCrée)
                withContext(Dispatchers.Main) {
                    if (nouvelÉvénement != null) {
                        ModèleÉvénements.setNouvelÉvénement(nouvelÉvénement!!)
                        vue.afficherNouvelÉvénement()
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