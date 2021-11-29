package com.even.présentation.présenteur

import com.even.domaine.entité.UnCoroutineDispatcher
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleConnexion
import com.even.sourceDeDonnées.SourceDeDonnéesBidon
import com.google.gson.JsonParseException
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

// les tests sont basés sur https://github.com/mockito/mockito-kotlin/blob/main/mockito-kotlin/src/test/kotlin/test/CoroutinesTest.kt
// pourquoi delay ?: https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/README.md#virtual-time-support-with-other-dispatchers
class PrésentateurConnexionTest {

    val delaiPourWithContext: Long = 15
    val invocationUnique = 1
    var sourceBidon = SourceDeDonnéesBidon()

    lateinit var présentateurTruqué: PrésentateurConnexion
    lateinit var mockVue: IConnexion.IVue
    lateinit var mockModele: ModèleConnexion
    lateinit var mockValidateur: ValidateurTextuel
    val coroutineProvider = UnCoroutineDispatcher()
    private val SubstitutFilPrincipal = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        mockVue = mock()
        mockModele = mock()
        mockValidateur = mock()
        présentateurTruqué =
            PrésentateurConnexion(mockVue, mockModele, mockValidateur, coroutineProvider)
        Dispatchers.setMain(SubstitutFilPrincipal)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        SubstitutFilPrincipal.close()
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il verifie les identifiants entres dans la vue, la methode de verification dans le modele est appelee 1 fois avec les bons parametres`() {
        val utilisateur = sourceBidon.listeUtils[0]
        présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
            utilisateur.nomUtilisateur,
            utilisateur.motDePasse
        )
        verify(mockValidateur, times(invocationUnique)).validerNomUsager(utilisateur.nomUtilisateur)
        verify(mockValidateur, times(invocationUnique)).validerMotDePasse(utilisateur.motDePasse)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, il envoie le nom et le mot de passe de l'utilisateur correctement car la verificatrion reussi`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
        }
        verifyBlocking(
            mockModele,
            times(invocationUnique)
        ) { demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse) }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, il n'envoie pas la requete car la verification echoue via nom utilisateur`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(false).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
        }
        verifyBlocking(
            mockModele,
            never()
        ) { demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse) }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, il n'envoie pas la requete car la verification echoue via mot de passe`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(false).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
        }
        verifyBlocking(
            mockModele,
            never()
        ) { demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse) }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur, il n'envoie pas la requete car la verification echoue via le nom utilisateur et le mot de passe`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(false).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(false).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
        }
        verifyBlocking(
            mockModele,
            never()
        ) { demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse) }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il valide les entrees et que le nom utilisateur est pas bon, il affiche un toast erreur connexion`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(false).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il valide les entrees et que le mot de passe est pas bon, il affiche un toast erreur connexion`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(false).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurConnexion()
    }


    @Test
    fun `Etant donne un presentateur lorsqu'il valide les entrees et qu'elle ne sont pas bonnes, il affiche un toast erreur connexion`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(false).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(false).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et que ca marche, il navigue vers l'ecran principal`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            doReturn(true).whenever(mockModele)
                .demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)

            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).naviguerVersFragmentPrincipal()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et que ca marche, il affiche un toast qui indique le succes de l'operation`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            doReturn(true).whenever(mockModele)
                .demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherToastSuccesConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et qu'il indique que les informations sont invalides, il affiche un toast qui indique une erreur de connexion`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            doReturn(false).whenever(mockModele)
                .demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurConnexion()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et qu'il indique que les informations sont invalides, il affiche une erreur sur nom utilisateur`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val estUtilisateurExistant = false
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            doReturn(estUtilisateurExistant).whenever(mockModele)
                .demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(
            mockVue,
            times(invocationUnique)
        ).afficherErreurNomUtilisateur(!estUtilisateurExistant)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il demande au modele le profil de l'utilisateur et qu'il indique que les informations sont invalides, il affiche une erreur sur mot de passe`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val estUtilisateurExistant = false
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            doReturn(estUtilisateurExistant).whenever(mockModele)
                .demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)
            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherErreurMotDePasse(!estUtilisateurExistant)
    }


    @Test
    fun `Etant donne un presentateur lorsqu'il rencontre une erreur, il affiche un toast erreur serveur`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val estUtilisateurExistant = false
        runBlocking(coroutineProvider.io) {
            doReturn(true).whenever(mockValidateur).validerNomUsager(utilisateur.nomUtilisateur)
            doReturn(true).whenever(mockValidateur).validerMotDePasse(utilisateur.motDePasse)
            doThrow(JsonParseException("Une erreur quelconque")).whenever(mockModele)
                .demanderProfilUtilisateur(utilisateur.nomUtilisateur, utilisateur.motDePasse)

            présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
                utilisateur.nomUtilisateur,
                utilisateur.motDePasse
            )
            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurServeur()
    }

}