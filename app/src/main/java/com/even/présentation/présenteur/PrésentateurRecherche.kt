package com.even.présentation.présenteur

import com.even.présentation.modèle.ModèleÉvénements
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class PrésentateurRecherche(
    val vue : IRecherche.IVue
) : IRecherche.IPrésentateur{
    override fun traiterRequêteRechercheÉvénement(
        nom: String,
        mois: String,
        location: String,
        organisateur: String
    ) {
        var tag = "$nom:$mois:$location:$organisateur"
        if (tag.length == 3){
            vue.afficherMessageAucunMotCle()
        } else {
            vue.afficherRésultatsRecherche(tag)
        }
    }
}