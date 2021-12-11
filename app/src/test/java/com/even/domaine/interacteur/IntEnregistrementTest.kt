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

class IntEnregistrementTest : CouroutineTestHelper() {
    lateinit var mockSourceDeDonnées: ISourceDeDonnées
    lateinit var interacteurTruqué: IntEnregistrement

    @Before
    override fun setUp() {
        super.setUp()
        mockSourceDeDonnées = mock()
        interacteurTruqué = IntEnregistrement(mockSourceDeDonnées)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on enregistre un nouvel utilisateur, la méthode est appelée dans la source`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            mockSourceDeDonnées.creerUtilisateur(utilisateur)
        }
        verifyBlocking(
            mockSourceDeDonnées,
            times(invocationUnique)
        ) { interacteurTruqué.enregisterNouvelUtilisateur(utilisateur) }
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on cree un commentaire, on retourne la réponse de la source`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockSourceDeDonnées).creerUtilisateur(any())
            reponseRetournée = interacteurTruqué.enregisterNouvelUtilisateur(utilisateur)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }
}