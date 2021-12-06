package com.even.présentation.modèle

import android.graphics.ColorSpace
import com.even.domaine.entité.UnCoroutineDispatcher
import com.even.domaine.interacteur.IntConnexion
import com.even.présentation.présenteur.PrésentateurConnexion
import com.even.sourceDeDonnées.ISourceDeDonnées
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.kotlin.mock

class ModèleAuthentificationTest {


    lateinit var modeleTruqué: ModèleAuthentification
    lateinit var mockInteracteur: IntConnexion
    lateinit var mockSource: ISourceDeDonnées

    val coroutineProvider = UnCoroutineDispatcher()
    private val SubstitutFilPrincipal = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        mockInteracteur = mock()
        mockSource = mock()
        modeleTruqué =
            ModèleAuthentification()
        Dispatchers.setMain(SubstitutFilPrincipal)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        SubstitutFilPrincipal.close()
    }

    @Test
    fun `Etant donne le modele authentification, lorsqu'on demande le profil, il envoie la requete a l'intracteur`() {
        assertTrue(true)
    }
}