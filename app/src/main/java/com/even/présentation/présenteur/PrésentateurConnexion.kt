package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.interacteur.IConnexion
import com.even.présentation.modèle.ModèleConnexion
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PrésentateurConnexion(
    val vue: IConnexion.IVue,
    val modèleEnregistrment: ModèleConnexion
) : IConnexion.IPrésentateur {
    private var coroutileLogin: Job? = null

    override fun traiterRequêteDemanderProfilPourConnexion(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ) {
        coroutileLogin = CoroutineScope(Dispatchers.IO).launch {
            try {
                val requeteComplete =
                    modèleEnregistrment.demanderProfilUtilisateur(nomUtilisateur, motDePasse)
                if (requeteComplete) {
                    Log.e(
                        "api",
                        "Succès 100%"
                    )
                }
                Log.e(
                    "api",
                    "Erreur de connexion au serveur / réponse incompatible"
                )
            } catch (e: MalformedJsonException) {
                Log.e("json", "Erreur dans la réponse json, format non respecté.")
            } catch (e: Exception) {
                Log.e("api", "La requête a rencontré une erreur")
            }
        }
    }
}