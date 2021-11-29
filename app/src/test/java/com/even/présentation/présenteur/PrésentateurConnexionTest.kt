package com.even.présentation.présenteur

import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleConnexion
import com.even.sourceDeDonnées.SourceDeDonnéesBidon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

// les tests sont basés sur https://github.com/mockito/mockito-kotlin/blob/main/mockito-kotlin/src/test/kotlin/test/CoroutinesTest.kt
class PrésentateurConnexionTest {

    val invocationUnique = 1
    var sourceBidon = SourceDeDonnéesBidon()

    lateinit var présentateurTruqué: PrésentateurConnexion
    lateinit var mockVue: IConnexion.IVue
    lateinit var mockModele: ModèleConnexion
    lateinit var mockValidateur: ValidateurTextuel

    @Before
    fun setUp() {
        mockVue = mock()
        mockModele = mock()
        mockValidateur = mock()
        présentateurTruqué = PrésentateurConnexion(mockVue, mockModele, mockValidateur)
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
        runBlocking(Dispatchers.IO) {
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
        runBlocking(Dispatchers.IO) {
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
        runBlocking(Dispatchers.IO) {
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
        runBlocking(Dispatchers.IO) {
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
}