package com.even.présentation.présenteur

import com.even.testHelper.CouroutineTestHelper
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleAuthentification
import com.google.gson.JsonParseException
import kotlinx.coroutines.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import java.text.SimpleDateFormat
import java.util.*

// les tests sont basés sur -> https://github.com/mockito/mockito-kotlin/blob/main/mockito-kotlin/src/test/kotlin/test/CoroutinesTest.kt
// Delay pour permettre le withContext -> https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/README.md#virtual-time-support-with-other-dispatchers
class PrésentateurConnexionTest : CouroutineTestHelper() {

    lateinit var présentateurTruqué: PrésentateurConnexion
    lateinit var mockVue: IConnexion.IVue
    lateinit var mockModele: ModèleAuthentification
    lateinit var mockValidateur: ValidateurTextuel
    lateinit var fauxUtilisateur: Utilisateur

    val fauxNomUtilisateur = "Bobinette"
    val fauxMotDePasse = "Jupiter!!"

    @Before
    override fun setUp() {
        super.setUp()
        fauxUtilisateur = Utilisateur(
            1,
            "Bobinette",
            "Jupiter!!",
            "Bobinette@gmail.com",
            "5141234567",
            SimpleDateFormat("yyyy.MM").format(Date())
        )
        mockVue = mock()
        mockModele = mock()
        mockValidateur = mock()
        présentateurTruqué =
            PrésentateurConnexion(mockVue, mockModele, mockValidateur, coroutineProvider)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il verifie les identifiants entres dans la vue, la methode de verification dans le modele est appelee 1 fois avec les bons parametres`() {
        présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
            fauxNomUtilisateur,
            fauxMotDePasse
        )
        verify(
            mockValidateur,
            times(invocationUnique)
        ).validerNomUsager(fauxNomUtilisateur)
        verify(
            mockValidateur,
            times(invocationUnique)
        ).validerMotDePasse(fauxMotDePasse)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, il envoie le nom et le mot de passe de l'utilisateur correctement car la verificatrion reussi`() {
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
        verifyBlocking(
            mockModele,
            times(invocationUnique)
        ) {
            demanderProfilUtilisateur(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, il n'envoie pas la requete car la verification echoue via nom utilisateur`() {
        runBlocking(coroutineProvider.io) {
            doReturn(false).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
        verifyBlocking(
            mockModele,
            never()
        ) {
            demanderProfilUtilisateur(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, celui-ci l'ajoute au modele authentification`() {
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            doReturn(fauxUtilisateur).whenever(mockModele)
                .demanderProfilUtilisateur(
                    fauxNomUtilisateur,
                    fauxMotDePasse
                )
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
        verifyBlocking(
            mockModele,
            times(invocationUnique)
        ) { ajouterUnUtilisateur(fauxUtilisateur) }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, il n'envoie pas la requete car la verification echoue via mot de passe`() {
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(false).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
        verifyBlocking(
            mockModele,
            never()
        ) {
            demanderProfilUtilisateur(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, il n'envoie pas la requete car la verification echoue via le nom utilisateur et le mot de passe`() {
        runBlocking(coroutineProvider.io) {
            doReturn(false).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(false).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
        verifyBlocking(
            mockModele,
            never()
        ) {
            demanderProfilUtilisateur(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il valide les entrees et que le nom utilisateur est pas bon, il affiche un toast erreur connexion`() {
        runBlocking(coroutineProvider.io) {
            doReturn(false).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il valide les entrees et que le mot de passe est pas bon, il affiche un toast erreur connexion`() {
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(false).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il valide les entrees et qu'elle ne sont pas bonnes, il affiche un toast erreur connexion`() {
        runBlocking(coroutineProvider.io) {
            doReturn(false).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(false).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et que ca marche, il navigue vers l'ecran principal`() {
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            doReturn(fauxUtilisateur).whenever(mockModele)
                .demanderProfilUtilisateur(
                    fauxNomUtilisateur,
                    fauxMotDePasse
                )

            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).naviguerVersFragmentPrincipal()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et que ca marche, il affiche un toast qui indique le succes de l'operation`() {
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            doReturn(fauxUtilisateur).whenever(mockModele)
                .demanderProfilUtilisateur(
                    fauxNomUtilisateur,
                    fauxMotDePasse
                )
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherToastSuccesConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et qu'il indique que les informations sont invalides, il affiche un toast qui indique une erreur de connexion`() {
        val utilisateurVide = Utilisateur(null, null)
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            doReturn(utilisateurVide).whenever(mockModele)
                .demanderProfilUtilisateur(
                    fauxNomUtilisateur,
                    fauxMotDePasse
                )
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et qu'il indique que les informations sont invalides, il affiche une erreur sur nom utilisateur`() {
        val utilisateurVide = Utilisateur(null, null)
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            doReturn(utilisateurVide).whenever(mockModele)
                .demanderProfilUtilisateur(
                    fauxNomUtilisateur,
                    fauxMotDePasse
                )
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(
            mockVue,
            times(invocationUnique)
        ).afficherErreurNomUtilisateur(afficherEnRouge = true)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et qu'il indique que les informations sont invalides, il affiche une erreur sur mot de passe`() {
        val estUtilisateurNull = Utilisateur(null, null)
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxNomUtilisateur)
            doReturn(estUtilisateurNull).whenever(mockModele)
                .demanderProfilUtilisateur(
                    fauxNomUtilisateur,
                    fauxMotDePasse
                )
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherErreurMotDePasse(afficherEnRouge = true)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il rencontre une erreur, il affiche un toast erreur serveur`() {
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur)
                .validerNomUsager(fauxNomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(fauxMotDePasse)
            doThrow(JsonParseException("Une erreur quelconque")).whenever(mockModele)
                .demanderProfilUtilisateur(
                    fauxNomUtilisateur,
                    fauxMotDePasse
                )

            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                fauxNomUtilisateur,
                fauxMotDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurServeur()
    }

}