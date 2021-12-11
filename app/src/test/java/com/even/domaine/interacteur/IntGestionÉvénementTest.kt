package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

class IntGestionÉvénementTest : CouroutineTestHelper() {
    lateinit var mockSourceDeDonnées: ISourceDeDonnées
    lateinit var interacteurTruqué: IntGestionÉvénement

    @Before
    override fun setUp() {
        super.setUp()
        mockSourceDeDonnées = mock()
        interacteurTruqué = IntGestionÉvénement(mockSourceDeDonnées)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on crée un événement, on retourne cet événment à partir de la source`() {
        val événement = sourceBidon.listeEvens[0]
        val événementRetourné : Événement

        runBlocking(coroutineProvider.io) {
            doReturn(événement).whenever(mockSourceDeDonnées).creerEvenement(any())
            événementRetourné = interacteurTruqué.creerÉvénement(événement)!!
        }
        assertEquals(événement,événementRetourné)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on annule un événement par son id, on retourne la réponse de la source`() {
        val événement = sourceBidon.listeEvens[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockSourceDeDonnées).supprimerEvenement(événement.idEvenement)
            reponseRetournée = interacteurTruqué.annulerÉvénement(événement.idEvenement)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on modifie un événement, on retourne la réponse de la source`() {
        val événement = sourceBidon.listeEvens[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockSourceDeDonnées).modifierEvenement(événement)
            reponseRetournée = interacteurTruqué.modifierÉvénement(événement)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }
}