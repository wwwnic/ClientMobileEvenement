package com.even.présentation.présenteur

import android.util.Log
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrésentateurModification(
    val vue : IModificationÉvénement.IVue
) : IModificationÉvénement.IPrésentateur {
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
                val reponse = ModèleÉvénements().modifierÉvénement(événementModifié)
                withContext(Dispatchers.Main) {
                    if (reponse.isSuccessful) {
                        ModèleÉvénements.setÉvénementPrésenté(événementModifié.idEvenement)
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

    override fun traiterRequêteAnnulerÉvénement() {
        var événementAnnulé = ModèleÉvénements.événementPrésenté
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reponse = ModèleÉvénements().annulerÉvénement(événementAnnulé!!.idEvenement)
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

    override fun traiterRequêteRemplirChamps() {
        vue.remplirChamps(ModèleÉvénements.événementPrésenté!!)
    }

}