package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.interacteur.IConnexion
import com.even.présentation.modèle.ModèleConnexion
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.*

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
                val estUtilisateurExistant =
                    modèleEnregistrment.demanderProfilUtilisateur(nomUtilisateur, motDePasse)
                if (estUtilisateurExistant) {
                    withContext(Dispatchers.Main) {
                        vue.naviguerVersFragmentPrincipal()
                    }
                    Log.e(
                        "api",
                        "Succès 100%"
                    )
                } else {
                    Log.e(
                        "api",
                        "Erreur de connexion au serveur / réponse incompatible"
                    )
                }
            } catch (e: MalformedJsonException) {
                Log.e("json", "Erreur dans la réponse json, format non respecté.")
            } catch (e: Exception) {
                Log.e("api", "La requête a rencontré une erreur", e)
            }
        }
    }
}