package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.sourceDeDonnées.SourceDeDonnéesBidon

class IntGetÉvénementsRécents(var _source : ISourceDeDonnées) {

    suspend fun getAllÉvénements() : List<Événement> {
        var listeEvenement = _source.getAllEvenements()
        var utilisateurs = IntGetAllUtilisateurs(_source).getAllUtilisateurs()
        listeEvenement.forEach { evenement ->
            utilisateurs.forEach { utilisateur ->
                if (utilisateur.idUtilisateur == evenement.idOrganisateur) evenement.organisateur = utilisateur
            }
            evenement.date = evenement.date.split("T").let { it[0] + " " + it[1] }
        }
        return listeEvenement
    }
}