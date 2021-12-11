package com.even.domaine.interacteur

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

class IntDétailÉvénementTest : CouroutineTestHelper() {
    lateinit var mockSourceDeDonnées: ISourceDeDonnées
    lateinit var interacteurTruqué: IntDétailÉvenement

    @Before
    override fun setUp() {
        super.setUp()
        mockSourceDeDonnées = mock()
        interacteurTruqué = IntDétailÉvenement(mockSourceDeDonnées)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on ajoute une participation à un événement, on retourne la réponse de la source`() {
        val participation = sourceBidon.listeUtilEven[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockSourceDeDonnées).ajouterParticipation(any())
            reponseRetournée = interacteurTruqué.ajouterParticipation(participation)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on retire une participation à un événement, on retourne la réponse de la source`() {
        val participation = sourceBidon.listeUtilEven[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockSourceDeDonnées).retirerParticipation(any())
            reponseRetournée = interacteurTruqué.retirerParticipation(participation)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }
}