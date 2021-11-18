package com.even.présentation.présenteur

import android.util.Log
import com.even.domaine.interacteur.IntEnregistrement
import com.even.domaine.interacteur.IEnregistrement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
}
