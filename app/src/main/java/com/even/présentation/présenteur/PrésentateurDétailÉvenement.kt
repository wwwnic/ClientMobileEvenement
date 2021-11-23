package com.even.présentation.présenteur

import com.even.domaine.interacteur.IntDétailÉvenement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import okhttp3.Dispatcher

class PrésentateurDétailÉvenement(
    var vue : IDétailÉvenement.IVue,
    var interacteurDétailÉvenement : IntDétailÉvenement
    ) : IDétailÉvenement.IPrésentateur {

    override fun traiterRequêteAfficherDétailÉvenement() {
        CoroutineScope(Dispatchers.IO).launch {

        }
    }
}