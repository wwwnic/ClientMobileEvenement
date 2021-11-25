package com.even.présentation.modèle

import com.even.sourceDeDonnées.ISourceDeDonnées

class ModèleMesÉvènements {
    companion object {
        lateinit var _source: ISourceDeDonnées
        fun setSource(source: ISourceDeDonnées) {
            _source = source
        }
    }
}