package com.even.présentation.modèle

import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntDétailÉvenement
import com.even.sourceDeDonnées.ISourceDeDonnées
import retrofit2.Response

class ModèleDétailÉvenement {

    companion object {
        lateinit var _source : ISourceDeDonnées
        fun setSource(source : ISourceDeDonnées) {
            _source = source;
        }
    }

    suspend fun allerChercherInfoÉvenement(id : Int) : Événement {
        val evenement = IntDétailÉvenement(_source).getInfoÉvenement(id)
        return evenement
    }
}