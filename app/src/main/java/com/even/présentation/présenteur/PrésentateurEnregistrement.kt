package com.even.présentation.présenteur

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.even.domaine.interacteur.IntEnregistrement
import com.even.domaine.interacteur.IEnregistrement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class PrésentateurEnregistrement(
    var vue: IEnregistrement.IVue,
    var enregistrementInteracteur: IntEnregistrement
) : IEnregistrement.IPrésentateur {

    override fun traiterRequêteReclamerEnregistrement(
        username: String,
        password: String,
        email: String,
        phone: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            var reponseApi = enregistrementInteracteur.enregisterNouvelUtilisateur(
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

    override fun traiterRequêteValiderNomUsager(username: String): Boolean {
        val usernamePattern = Pattern.compile(
            "^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"
        )
        return !TextUtils.isEmpty(username) && usernamePattern.matcher(username).matches()
    }

    override fun traiterRequêteValiderMotDePasse(password: String): Boolean {
        val passwordPattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{4,24}")
        return !TextUtils.isEmpty(password) && passwordPattern.matcher(password).matches()
    }

    override fun traiterRequêteValiderCourriel(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun traiterRequêteValiderTelephone(phone: String): Boolean {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches()
    }
}