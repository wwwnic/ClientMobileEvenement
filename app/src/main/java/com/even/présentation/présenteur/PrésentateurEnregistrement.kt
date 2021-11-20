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
        return ValidateurEntréesTextuel.validerNomUsager(nomUsager)
    }

    override fun traiterRequêteValiderMotDePasse(motDePasse: CharSequence): Boolean {
        return  ValidateurEntréesTextuel.validerMotDePasse(motDePasse)
    }

    override fun traiterRequêteValiderCourriel(courriel: CharSequence): Boolean {
        return ValidateurEntréesTextuel.validerCourriel(courriel)
    }

    override fun traiterRequêteValiderTelephone(telephone: CharSequence): Boolean {
        return ValidateurEntréesTextuel.validerTelephone(telephone)
    }
}