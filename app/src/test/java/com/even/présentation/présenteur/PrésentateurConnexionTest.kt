package com.even.présentation.présenteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleConnexion
import com.even.sourceDeDonnées.SourceDeDonnéesBidon
import kotlinx.coroutines.*
import okhttp3.internal.wait
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

// https://github.com/mockito/mockito-kotlin/blob/main/mockito-kotlin/src/test/kotlin/test/CoroutinesTest.kt
class PrésentateurConnexionTest {

    val invocationUnique = 1
    lateinit var présentateurTruqué: PrésentateurConnexion
    var sourceBidon = SourceDeDonnéesBidon()
    val mockVue = mock<IConnexion.IVue>()
    val mockModele = mock<ModèleConnexion>()
    val mockValidateur = mock<ValidateurTextuel>()

    @Before
    fun setUp() {
        présentateurTruqué = PrésentateurConnexion(mockVue, mockModele, mockValidateur)

    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un présentateur lorsqu'il vérifie les identifiants entrés dans la vue, les méthodes sont appelées uniquement 1 fois avec ce qui a été entré`() {
        val utilisateur = sourceBidon.listeUtils[0]
        présentateurTruqué.traiterRequêteDemanderProfilPourConnexion(
            utilisateur.nomUtilisateur,
            utilisateur.motDePasse
        )
        verify(mockValidateur, times(invocationUnique)).validerNomUsager(utilisateur.nomUtilisateur)
        verify(mockValidateur, times(invocationUnique)).validerMotDePasse(utilisateur.motDePasse)
    }

    @Test
    fun `Étant donné un présentateur lorsqu'il demande au modèle le profil de l'utilisateur, il envoie le nom et le mot de passe de l'utilisateur correctement`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking {
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
}