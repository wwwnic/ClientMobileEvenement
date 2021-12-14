package com.even.sourceDeDonnées

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.testHelper.CouroutineTestHelper
import com.even.testHelper.SubstitueReponseApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import java.util.Arrays.asList

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
    fun `Etant donne une source de donnée lorsqu'on demande au service une liste de tous les utilisateurs, les bonnes informations lui sont transmises`() {
        var liste = substitueReponseApi.reponseListeUtilisateur200

        runBlocking (coroutineProvider.io) {
            doReturn(liste).whenever(mockServiceApi).getAllUtilisateurs()

            sourceDeDonnéesTruqué.getAllUtilisateurs()
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            getAllUtilisateurs()
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service une liste de tous les événements, les bonnes informations lui sont transmises`() {
        var liste = substitueReponseApi.reponseListeÉvénement200

        runBlocking (coroutineProvider.io){
            doReturn(liste).whenever(mockServiceApi).getAllEvenements()

            sourceDeDonnéesTruqué.getAllEvenements()
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)){
            getAllEvenements()
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service un événement avec un id spécifique, les bonnes informations lui sont transmises`() {
        var événement = substitueReponseApi.reponseÉvénement200

        runBlocking (coroutineProvider.io) {
            doReturn(événement).whenever(mockServiceApi).getEvenementParId(any())

            sourceDeDonnéesTruqué.getÉvénementParId(1)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            getEvenementParId(1)
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service un les événements auxquelles un utilisateur participe, les bonnes informations lui sont transmises`() {
        var liste = substitueReponseApi.reponseListeUtilisateur200

        runBlocking (coroutineProvider.io) {
            doReturn(liste).whenever(mockServiceApi).getUtilisateursDansEvenement(any())

            sourceDeDonnéesTruqué.getUtilisateursDansEvenement(1)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            getUtilisateursDansEvenement(1)
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service de créer un utilisateur, les bonnes informations lui sont transmises`() {

        runBlocking (coroutineProvider.io) {
            sourceDeDonnéesTruqué.creerUtilisateur(substitueReponseApi.utilisateur200)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            creerUtilisateur(substitueReponseApi.utilisateur200)
        }
    }

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
    fun `Etant donne une source de donnée lorsqu'on demande au service un événement à partir d'une recherche, les bonnes informations lui sont transmises`() {
        val liste = substitueReponseApi.reponseListeÉvénement200

        runBlocking (coroutineProvider.io) {
            doReturn(liste).whenever(mockServiceApi).getEvenementsParRecherche(any(), any(), any(), any())

            sourceDeDonnéesTruqué.getÉvénementsParRecherche("Party","","","")
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            getEvenementsParRecherche("Party","","","")
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service de créer un événement, les bonnes informations lui sont transmises`() {
        val événement = substitueReponseApi.reponseÉvénement200

        runBlocking (coroutineProvider.io){
            doReturn(événement).whenever(mockServiceApi).creerEvenement(any())

            sourceDeDonnéesTruqué.creerEvenement(substitueReponseApi.événement200)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            creerEvenement(substitueReponseApi.événement200)
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service de modifier un événement, les bonnes informations lui sont transmises`() {
        runBlocking (coroutineProvider.io) {
            sourceDeDonnéesTruqué.modifierEvenement(substitueReponseApi.événement200)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            updateEvenement(substitueReponseApi.événement200)
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service de supprimer un événement, les bonnes informations lui sont transmises`() {
        runBlocking (coroutineProvider.io) {
            sourceDeDonnéesTruqué.supprimerEvenement(1)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            deleteEvenement(1)
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service un utilisateur avec un id spécifique, les bonnes informations lui sont transmises`() {
        val utilisateur = substitueReponseApi.reponseUtilisateur200

        runBlocking (coroutineProvider.io) {
            doReturn(utilisateur).whenever(mockServiceApi).getUtilisateurParId(any())

            sourceDeDonnéesTruqué.getUtilisateurParId(1)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            getUtilisateurParId(1)
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service une liste d'utilisateur avec un nom spécifique, les bonnes informations lui sont transmises`() {
        val liste = substitueReponseApi.reponseListeUtilisateur200

        runBlocking (coroutineProvider.io) {
            doReturn(liste).whenever(mockServiceApi).getUtilisateursParNom(any())

            sourceDeDonnéesTruqué.getUtilisateursParNom("Bob")
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            getUtilisateursParNom("Bob")
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service une liste d'événement par participation, les bonnes informations lui sont transmises`() {
        val liste = substitueReponseApi.reponseListeÉvénement200

        runBlocking (coroutineProvider.io) {
            doReturn(liste).whenever(mockServiceApi).getEvenementsParParticipation(any())

            sourceDeDonnéesTruqué.getÉvénementsParParticipation(1)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            getEvenementsParParticipation(1)
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service une liste d'événement relié à un organisateur, les bonnes informations lui sont transmises`() {
        val liste = substitueReponseApi.reponseListeÉvénement200

        runBlocking (coroutineProvider.io) {
            doReturn(liste).whenever(mockServiceApi).getEvenementsParOrganisateur(any())

            sourceDeDonnéesTruqué.getÉvénementsParOrganisateur(1)
        }

        verifyBlocking(mockServiceApi, times(invocationUnique)) {
            getEvenementsParOrganisateur(1)
        }
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service une image d'un utilisateur, les bonnes informations lui sont transmises`() {
        var résultatAttendu = "http://140.82.8.101/images/utilisateurs/1.jpg"
        var résultatObservé = ""

        résultatObservé = sourceDeDonnéesTruqué.getImageUtilisateur(1)

        assertEquals(résultatAttendu,résultatObservé)
    }

    @Test
    fun `Etant donne une source de donnée lorsqu'on demande au service une image d'un événement, les bonnes informations lui sont transmises`() {
        var résultatAttendu = "http://140.82.8.101/images/evenements/1.jpg"
        var résultatObservé = ""

        résultatObservé = sourceDeDonnéesTruqué.getImageEvenement(1)

        assertEquals(résultatAttendu,résultatObservé)
    }
}