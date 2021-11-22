package com.even.présentation.présenteur

import com.even.domaine.interacteur.IntEnregistrement
import com.even.domaine.interacteur.IEnregistrement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            enregistrementInteracteur.enregisterNouvelUtilisateur(
                username,
                password,
                email,
                phone
            )
        }
    }
}