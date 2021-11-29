package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleConnexion
import kotlinx.coroutines.*

class PrésentateurConnexion(
    val vue: IConnexion.IVue, val modele: ModèleConnexion, val validateur: ValidateurTextuel
) : IConnexion.IPrésentateur {
    private var coroutileLogin: Job? = null

    override fun traiterRequêteDemanderProfilPourConnexion(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ) {
        //val entréesValide = validerLesEntréesConnexion(nomUtilisateur, motDePasse)
        if (true) { //entréesValide
            lancerRequeteConnexionApi(nomUtilisateur, motDePasse)
        } else {
            vue.afficherToastErreurConnexion()
        }
    }

    private fun lancerRequeteConnexionApi(
        nomUtilisateur: CharSequence,
        motDePasse: CharSequence
    ) {
        coroutileLogin = CoroutineScope(Dispatchers.IO).launch {
            try {
                val estUtilisateurExistant =
                    modele.demanderProfilUtilisateur(nomUtilisateur, motDePasse)
                withContext(Dispatchers.Main) {
                    if (estUtilisateurExistant) {
                        vue.naviguerVersFragmentPrincipal()
                        vue.afficherToastSuccesConnexion()
                    } else {
                        vue.afficherToastErreurConnexion()
                        vue.afficherErreurNomUtilisateur(!estUtilisateurExistant)
                        vue.afficherErreurMotDePasse(!estUtilisateurExistant)
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

    private fun validerLesEntréesConnexion(
        nomUsager: CharSequence,
        motDePasse: CharSequence,
    ): Boolean {
        val estNomUsagerValide = this.traiterRequêteValiderNomUsager(nomUsager)
        val estMotDePasseValide = this.traiterRequêteValiderMotDePasse(motDePasse)
        val entréesValide = estNomUsagerValide && estMotDePasseValide
        return entréesValide
    }

    private fun traiterRequêteValiderNomUsager(nomUsager: CharSequence): Boolean {
        val estValide = validateur.validerNomUsager(nomUsager)
        vue.afficherErreurNomUtilisateur(!estValide)
        return estValide
    }

    private fun traiterRequêteValiderMotDePasse(motDePasse: CharSequence): Boolean {
        val estValide = validateur.validerMotDePasse(motDePasse)
        vue.afficherErreurMotDePasse(!estValide)
        return estValide
    }
}