package com.even.présentation.modèle

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.*
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

class ModèleÉvénementsTest : CouroutineTestHelper() {

    lateinit var modèleTruqué : ModèleÉvénements
    lateinit var mockIntGetÉvénement : IntGetÉvénement
    lateinit var mockIntGestionÉvénement: IntGestionÉvénement
    lateinit var mockIntDétailÉvenement: IntDétailÉvenement
    lateinit var mockIntGetCommentaire: IntGetCommentaires
    lateinit var mockIntCreerCommentaire: IntCreerCommentaire
    lateinit var mockIntGetUtilisateur: IntGetUtilisateur

    @Before
    override fun setUp() {
        super.setUp()
        mockIntGetÉvénement = mock()
        mockIntGestionÉvénement = mock()
        mockIntDétailÉvenement = mock()
        mockIntGetCommentaire = mock()
        mockIntCreerCommentaire = mock()
        mockIntGetUtilisateur = mock()
        modèleTruqué =
            ModèleÉvénements(mockIntGetÉvénement,
                mockIntGestionÉvénement,
                mockIntDétailÉvenement,
                mockIntGetCommentaire,
                mockIntCreerCommentaire,
                mockIntGetUtilisateur
            )
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on passe l'événement à présenter à celui-ci, il est ajouté correctement`() {
        val événement = sourceBidon.listeEvens[0]
        val organisateur = sourceBidon.listeUtils[0]
        organisateur.urlImage = "test"
        runBlocking(coroutineProvider.io) {
            doReturn(événement).whenever(mockIntGetÉvénement).getÉvénementParId(any())
            doReturn(organisateur).whenever(mockIntGetUtilisateur).getParId(any())
            doReturn("test").whenever(mockIntGetUtilisateur).getImageUtilisateur(any())
            modèleTruqué.setÉvénementPrésenté(événement.idEvenement)
        }
        assertEquals(événement,ModèleÉvénements.événementPrésenté)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on demande la liste des événement récents, on retourne une liste d'événements`() {
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Événement>
        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockIntGetÉvénement).getÉvénementsRécents()
            listeRetournée = modèleTruqué.getÉvénementsRécents()
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on demande une recherche d'événements, on retourne une liste d'événements`() {
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Événement>
        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockIntGetÉvénement).getÉvénementsParRecherche(any(),any(),any(),any())
            listeRetournée = modèleTruqué.getÉvénementsParRecherche("a","b","c","d")
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on cree un événement, on retourne l'événement via la source`() {
        val événement = sourceBidon.listeEvens[0]
        val événementRetourné : Événement
        runBlocking(coroutineProvider.io) {
            doReturn(événement).whenever(mockIntGestionÉvénement).creerÉvénement(événement)
            événementRetourné = modèleTruqué.créerÉvénement(événement)!!
        }
        assertEquals(événement,événementRetourné)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on modifie un événement, on retourne la réponse de la source`() {
        val événement = sourceBidon.listeEvens[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockIntGestionÉvénement).modifierÉvénement(événement)
            reponseRetournée = modèleTruqué.modifierÉvénement(événement)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on annule un événement, on retourne la réponse de la source`() {
        val événement = sourceBidon.listeEvens[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockIntGestionÉvénement).annulerÉvénement(événement.idEvenement)
            reponseRetournée = modèleTruqué.annulerÉvénement(événement.idEvenement)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on demande la liste des participations d'un utilisateur donné, on retourne une liste d'événements`() {
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Événement>
        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockIntGetÉvénement).demanderMesParticipations(any())
            listeRetournée = modèleTruqué.demanderLesParticipations(1)
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on demande la liste des événements organisés par un utilisateur donné, on retourne une liste d'événements`() {
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Événement>
        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockIntGetÉvénement).demanderMesÉvènements(any())
            listeRetournée = modèleTruqué.demanderSesPropreÉvènement(1)
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on ajoute une participation à un événement, on retourne la réponse de la source`() {
        val participation = sourceBidon.listeUtilEven[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockIntDétailÉvenement).ajouterParticipation(any())
            reponseRetournée = modèleTruqué.ajouterParticipation(participation)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on retire une participation à un événement, on retourne la réponse de la source`() {
        val participation = sourceBidon.listeUtilEven[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockIntDétailÉvenement).retirerParticipation(any())
            reponseRetournée = modèleTruqué.retirerParticipation(participation)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on demande la liste des commentaires d'un événement donné, on retourne une liste de commentaires`() {
        val liste = sourceBidon.listeComm
        val listeRetournée : List<Commentaire>

        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockIntGetCommentaire).getCommentairesParÉvénement(any())
            listeRetournée = modèleTruqué.getCommentairesDansÉvénement(1)
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on crée un commentaire, on retourne la réponse de la source`() {
        val commentaire = sourceBidon.listeComm[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val reponseRetournée : Response<Void>

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockIntCreerCommentaire).creerCommentaire(any())
            reponseRetournée = modèleTruqué.créerCommentaire(commentaire)
        }
        assertEquals(fausseReponse,reponseRetournée)
    }

    @Test
    fun `Étant donné un modèle, lorsqu'on demande une image par id événement,on retourne un string`() {
        val lienImage = sourceBidon.getImageEvenement(1)
        val lienRetourné : String

        doReturn(lienImage).whenever(mockIntGetÉvénement).getImageÉvénement(any())
        lienRetourné = modèleTruqué.getImageÉvénement(1)
        assertEquals(lienImage,lienRetourné)
    }
}