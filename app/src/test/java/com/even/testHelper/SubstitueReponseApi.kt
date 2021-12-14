package com.even.testHelper

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ApiReponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SubstitueReponseApi {

    val utilisateur200 = Utilisateur(
        1,
        "BOBLEBRICOLEUR",
        "laSecuriteAvantTout",
        "bob@lesBricoleurs.brico",
        "5145552343",
        SimpleDateFormat("yyyy.MM").format(Date())
    )

    val reponseUtilisateur200 = Response.success(
        200,
        utilisateur200
    )

    val erreur400 = Response.error<ApiReponse>(
        400,
        "{\"error\":[\"Cette requête n'est pas approuvée par Bob le bricoleur :( ! \"]}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    )

    val événement = Événement(
        1,
        "Party",
        "Chez Bob",
        SimpleDateFormat("yyyy.MM").format(Date()),
        1,
        "Gros Party chez Bob le builder"
    )

}
