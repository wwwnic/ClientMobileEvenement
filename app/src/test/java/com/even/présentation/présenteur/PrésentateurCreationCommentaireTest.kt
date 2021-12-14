package com.even.présentation.présenteur

import com.even.domaine.entité.Utilisateur
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.ApiReponse
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

class PrésentateurCreationCommentaireTest : CouroutineTestHelper() {

    lateinit var présentateurTruqué : PrésentateurCreationCommentaire
    lateinit var mockVue : ICreationCommentaire.IVue
    lateinit var mockModèleÉvénements: ModèleÉvénements
    lateinit var fauxUtilisateurConnecté : Utilisateur

    @Before
    override fun setUp() {
        fauxUtilisateurConnecté = sourceBidon.listeUtils[0]
        super.setUp()
        mockVue = mock()
        mockModèleÉvénements = mock()
        ModèleAuthentification.utilisateurConnecté = fauxUtilisateurConnecté
        présentateurTruqué = PrésentateurCreationCommentaire(
            mockVue,
            mockModèleÉvénements
        )
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        Assert.assertTrue(true)
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande d'afficher le nom de l'événement, on appelle la méthode pour afficher le nom dans la vue`() {
        ModèleÉvénements.événementPrésenté = sourceBidon.listeEvens[0]
        runBlocking(coroutineProvider.io) {
            présentateurTruqué.traiterRequêteAfficherNomÉvénement()
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockVue,
            times(invocationUnique)
        ) {
            afficherNomÉvénement(any())
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande d'ajouter un commentaire, on appelle la méthode pour créer le commentaire`() {
        ModèleÉvénements.événementPrésenté = sourceBidon.listeEvens[0]
        runBlocking(coroutineProvider.io) {
            présentateurTruqué.traiterRequêteAjouterCommentaire("Test")
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockModèleÉvénements,
            times(invocationUnique)
        ) {
            créerCommentaire(any())
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande d'ajouter un commentaire et que l'ajout a fonctionné, on appelle la méthode pour rafraichir la vue`() {
        ModèleÉvénements.événementPrésenté = sourceBidon.listeEvens[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockModèleÉvénements).créerCommentaire(any())
            présentateurTruqué.traiterRequêteAjouterCommentaire("Test")
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockVue,
            times(invocationUnique)
        ) {
            afficherVueDétailsÉvénement()
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande d'ajouter un commentaire et que l'ajout a échoué, on appelle la méthode pour afficher un message d'erreur`() {
        ModèleÉvénements.événementPrésenté = sourceBidon.listeEvens[0]
        val fausseReponse = Response.error<ApiReponse>(
            400,
            "{\"error\":[\"Cette requête n'est pas approuvée par Bob le bricoleur :( ! \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockModèleÉvénements).créerCommentaire(any())
            présentateurTruqué.traiterRequêteAjouterCommentaire("Test")
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockVue,
            times(invocationUnique)
        ) {
            afficherToastErreurServeur()
        }
    }
}