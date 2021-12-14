package com.even.présentation.vue

import com.even.MainActivity
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.SourceDeDonnéesBidon
import org.junit.Before

open class InstrumentalTestHelper {

    @Before
    fun setup() {
        MainActivity.nePasChargerSourcePourTestInstrumentation = true
        ModèleAuthentification.setSource(SourceDeDonnéesBidon())
        ModèleUtilisateurs.setSource(SourceDeDonnéesBidon())
        ModèleÉvénements.setSource(SourceDeDonnéesBidon())
    }
}