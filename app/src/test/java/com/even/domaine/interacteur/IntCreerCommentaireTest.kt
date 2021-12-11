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

class IntCreerCommentaireTest : CouroutineTestHelper() {
    lateinit var mockSourceDeDonnées: ISourceDeDonnées
    lateinit var interacteurTruqué: IntCreerCommentaire

    @Before
    override fun setUp() {
        super.setUp()
        mockSourceDeDonnées = mock()
        interacteurTruqué = IntCreerCommentaire(mockSourceDeDonnées)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on cree un commentaire, la méthode est appelée dans la source`() {
        val commentaire = sourceBidon.listeComm[0]
        runBlocking(coroutineProvider.io) {
            mockSourceDeDonnées.creerCommentaire(commentaire)
        }
        verifyBlocking(
            mockSourceDeDonnées,
            times(invocationUnique)
        ) { interacteurTruqué.creerCommentaire(commentaire) }
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on cree un commentaire, on retourne la réponse de la source`() {
        val commentaire = sourceBidon.listeComm[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockSourceDeDonnées).creerCommentaire(any())
            reponseRetournée = interacteurTruqué.creerCommentaire(commentaire)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }
}