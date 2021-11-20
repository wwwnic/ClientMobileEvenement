package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.entité.ValidateurEntréesTextuel
import com.even.domaine.interacteur.IntEnregistrement
import com.even.domaine.interacteur.IEnregistrement
import com.even.présentation.modèle.ModèleEnregistrement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrésentateurEnregistrement(
    var vue: IEnregistrement.IVue,
    var modèleEnregistrment: ModèleEnregistrement
) : IEnregistrement.IPrésentateur {

    override fun traiterRequêteReclamerEnregistrement(
        username: String,
        password: String,
        email: String,
        phone: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            var reponseApi = modèleEnregistrment.effectuerEnregistrement(
                username,
                password,
                email,
                phone
            )
            if (reponseApi.isSuccessful) {
                withContext(Dispatchers.Main) {
                    vue.naviguerVersConnexion()
                    vue.afficherToastSuccesEnregistrement()
                    Log.e("api", "Succès !") //TODO: log
                }
            } else {
                vue.afficherToastErreurEnregistrement()
                Log.e("api", "Échec") //TODO: log
            }
        }
    }

    override fun traiterRequêteValiderNomUsager(nomUsager: CharSequence): Boolean {
        val estValide = ValidateurEntréesTextuel.validerNomUsager(nomUsager)
        vue.afficherErreurNomUsager(!estValide)
        return estValide
    }

    override fun traiterRequêteValiderMotDePasse(motDePasse: CharSequence): Boolean {
        val estValide = ValidateurEntréesTextuel.validerMotDePasse(motDePasse)
        vue.afficherErreurMotDePasse(!estValide)
        return estValide
    }

    override fun traiterRequêteValiderCourriel(courriel: CharSequence): Boolean {
        val estValide = ValidateurEntréesTextuel.validerCourriel(courriel)
        vue.afficherErreurCourriel(!estValide)
        return estValide
    }

    override fun traiterRequêteValiderTelephone(telephone: CharSequence): Boolean {
        val estValide = ValidateurEntréesTextuel.validerTelephone(telephone)
        vue.afficherErreurTéléphone(!estValide)
        return estValide
    }
}