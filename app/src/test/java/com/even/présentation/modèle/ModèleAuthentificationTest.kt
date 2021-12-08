package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IntConnexion
import com.even.domaine.interacteur.IntEnregistrement
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


class ModèleAuthentificationTest : CouroutineTestHelper() {

    lateinit var modeleTruqué: ModèleAuthentification
    lateinit var mockIntConnexion: IntConnexion
    lateinit var mockIntEnregistrement: IntEnregistrement

    @Before
    override fun setUp() {
        super.setUp()
        mockIntConnexion = mock()
        mockIntEnregistrement = mock()
        modeleTruqué =
            ModèleAuthentification(mockIntConnexion, mockIntEnregistrement)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Etant donne le modele authentification, lorsqu'on ajoute un utilisateur, il est ajouté correctement`() {
        val utilisateur = sourceBidon.listeUtils[0]
        modeleTruqué.ajouterUnUtilisateur(utilisateur)
        assertEquals(utilisateur, ModèleAuthentification.utilisateurConnecté)
    }

    @Test
    fun `Etant donne le modele authentification, lorsqu'on demande le profil, il envoie la requete pour le recuperer`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val utiTronqué = Utilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)

        runBlocking(coroutineProvider.io) {
            modeleTruqué.demanderProfilUtilisateur(
                utilisateur.nomUtilisateur as CharSequence,
                utilisateur.motDePasse as CharSequence
            )
        }
        verifyBlocking(
            mockIntConnexion,
            times(invocationUnique)
        ) { connexionDemanderProfil(utiTronqué) }
    }

    @Test
    fun `Etant donne le modele authentification, lorsqu'on demande le profil, il traite la demande et retourne un utilisateur`() {
        val utilisateur = sourceBidon.listeUtils[0]

        runBlocking(coroutineProvider.io) {
            doReturn(utilisateur).whenever(mockIntConnexion).connexionDemanderProfil(any())
            val utilRetourné = modeleTruqué.demanderProfilUtilisateur(
                utilisateur.nomUtilisateur as CharSequence,
                utilisateur.motDePasse as CharSequence
            )
            assertEquals(utilisateur, utilRetourné)
        }
    }

    @Test
    fun `Etant donne le modele authentification, lorsqu'on effectue un enregistrement, il envoie demande a enregistrer un nouvel utilisateur`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val utilTronqué = Utilisateur(
            utilisateur.nomUtilisateur,
            utilisateur.motDePasse,
            utilisateur.courriel,
            utilisateur.telephone
        )
        runBlocking(coroutineProvider.io) {
            modeleTruqué.effectuerEnregistrement(
                utilisateur.nomUtilisateur as CharSequence,
                utilisateur.motDePasse as CharSequence,
                utilisateur.courriel as CharSequence,
                utilisateur.telephone as CharSequence
            )
        }
        verifyBlocking(
            mockIntEnregistrement,
            times(invocationUnique)
        ) { enregisterNouvelUtilisateur(utilTronqué) }
    }


    @Test
    fun `Etant donne le modele authentification, lorsqu'on effectue un enregistrement , il traite la demande et retourne la reponse de l'api`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val fausseReponse = Response.success(
            200,
            "{\"key\":[\"Cette requête est approuvée par Bob le bricoleur :) \"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        runBlocking(coroutineProvider.io) {
            doReturn(fausseReponse).whenever(mockIntEnregistrement)
                .enregisterNouvelUtilisateur(any())
            val reponseRetourné = modeleTruqué.effectuerEnregistrement(
                utilisateur.nomUtilisateur as CharSequence,
                utilisateur.motDePasse as CharSequence,
                utilisateur.courriel as CharSequence,
                utilisateur.telephone as CharSequence
            )
            assertEquals(fausseReponse, reponseRetourné)
        }
    }
}