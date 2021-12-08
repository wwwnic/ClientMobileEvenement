package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrésentateurCreationCommentaire(
    val vue : ICreationCommentaire.IVue
    ) : ICreationCommentaire.IPrésentateur {

    override fun traiterRequêteAfficherNomÉvénement() {
        vue.afficherNomÉvénement(ModèleÉvénements.événementPrésenté!!.nomEvenement)
    }

    override fun traiterRequêteAjouterCommentaire(texte: String) {
        val événementCommenté = ModèleÉvénements.événementPrésenté
        val utilisateur = ModèleAuthentification.utilisateurConnecté
        var commentaireCrée: Commentaire = Commentaire(
            événementCommenté!!.idEvenement,
            utilisateur!!.idUtilisateur!!,
            texte
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var reponse = ModèleÉvénements().créerCommentaire(commentaireCrée)
                withContext(Dispatchers.Main) {
                    if (reponse.isSuccessful) {
                        vue.afficherVueDétailsÉvénement()
                    } else {
                        vue.afficherToastErreurServeur()
                    }
                }
            } catch (e: Exception) {
                Log.e("Évèn", "La requête a rencontré une erreur", e)
                withContext(Dispatchers.Main) {
                    vue.afficherToastErreurServeur()
                }
            }
        }
    }
}