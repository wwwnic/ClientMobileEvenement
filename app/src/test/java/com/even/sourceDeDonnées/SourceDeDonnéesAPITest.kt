package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.testHelper.CouroutineTestHelper
import com.even.testHelper.SubstitueReponseApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class SourceDeDonnéesAPITest : CouroutineTestHelper() {

    lateinit var sourceDeDonnéesTruqué: ISourceDeDonnées
    lateinit var mockServiceApi: IApiService
    var substitueReponseApi = SubstitueReponseApi()


    @Before
    override fun setUp() {
        super.setUp()
        mockServiceApi = mock()
        sourceDeDonnéesTruqué = SourceDeDonnéesAPI(mockServiceApi)
    }

    @Test
    fun getAllUtilisateurs() {
        assert(true)
    }

    @Test
    fun getAllEvenements() {
        assert(true)
    }

    @Test
    fun getEvenementParId() {
        assert(true)
    }

    @Test
    fun getUtilisateursEvenement() {
        assert(true)
    }

    @Test
    fun creerUtilisateur() {
        assert(true)
    }

    //Test demanderProfil
    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service un profil, les bonnes informations lui sont transmises`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val utilisateurTronqué = Utilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)
        runBlocking(coroutineProvider.io) {
            doReturn(substitueReponseApi.reponseUtilisateur200).whenever(mockServiceApi)
                .demanderProfil(utilisateurTronqué)
            sourceDeDonnéesTruqué.demanderProfil(utilisateurTronqué)
        }
        verifyBlocking(
            mockServiceApi,
            times(invocationUnique)
        ) { demanderProfil(utilisateurTronqué) }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service un profil, la source de données retourne un utilisateur dans le body de la requete http`() {
        val utilisateur = substitueReponseApi.utilisateur200
        val utilisateurTronqué = Utilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)

        val reponseUtilisateur: Utilisateur?
        runBlocking(coroutineProvider.io) {
            doReturn(substitueReponseApi.reponseUtilisateur200).whenever(mockServiceApi)
                .demanderProfil(utilisateurTronqué)
            reponseUtilisateur = sourceDeDonnéesTruqué.demanderProfil(utilisateurTronqué)
        }
        assertEquals(utilisateur, reponseUtilisateur)
    }

    @Test
    fun getEvenementsParRecherche() {
        assert(true)
    }

    @Test
    fun creerEvenement() {
        assert(true)
    }

    @Test
    fun modifierEvenement() {
        assert(true)
    }

    @Test
    fun supprimerEvenement() {
        assert(true)
    }

    @Test
    fun getUtilisateurParId() {
        assert(true)
    }

    @Test
    fun getUtilisateursParNom() {
        assert(true)
    }

    @Test
    fun getEvenementParParticipation() {
        assert(true)
    }

    @Test
    fun getEvenementsParOrganisateur() {
        assert(true)
    }

    @Test
    fun getImageUtilisateur() {
        assert(true)
    }

    @Test
    fun getImageEvenement() {
        assert(true)
    }
}