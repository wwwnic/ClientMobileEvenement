package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntEnregistrement
import com.even.sourceDeDonnées.IApiService
import retrofit2.Response

class ModèleEnregistrement(val api: IApiService) {

    suspend fun effectuerEnregistrement(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
        courriel: CharSequence,
        telephone: CharSequence
    ): Response<Void> {
        val reponseBodyRequête = IntEnregistrement(api).enregisterNouvelUtilisateur(
            Utilisateur(
                nomUsager.toString(),
                motDePasse.toString(),
                courriel.toString(),
                telephone.toString()
            )
        )
        return reponseBodyRequête
    }
}