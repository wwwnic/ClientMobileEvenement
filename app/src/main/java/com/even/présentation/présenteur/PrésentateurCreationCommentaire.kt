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

/**
 * Permet de faire les traitements dans la vue de création de commentaire
 *
 * @property vue La vue VueCréationCommentaire
 */
class PrésentateurCreationCommentaire(
    val vue : ICreationCommentaire.IVue
    ) : ICreationCommentaire.IPrésentateur {

    val modèleÉvénements = ModèleÉvénements()

    /**
     * Méthode qui permet de faire l'affichage dans la vue du nom de l'événement sélectionné.
     *
     */
    override fun traiterRequêteAfficherNomÉvénement() {
        vue.afficherNomÉvénement(ModèleÉvénements.événementPrésenté!!.nomEvenement)
    }

    /**
     * Méthode qui permet de faire l'ajout d'un commentaire dans l'événement sélectionné.
     * Lorsque le commentaire est ajouté, l'utilisateur est rediriger sur la vue détail de l'événement.
     *
     * @param texte Texte du commentaire en question
     */
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
                var reponse = modèleÉvénements.créerCommentaire(commentaireCrée)
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