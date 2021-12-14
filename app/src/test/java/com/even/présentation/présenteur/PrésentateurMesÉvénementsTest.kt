package com.even.présentation.présenteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleÉvénements
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class PrésentateurMesÉvénementsTest : CouroutineTestHelper() {

    lateinit var présentateurTruqué : PrésentateurMesÉvènements
    lateinit var mockVue : IMesÉvènements.IVue
    lateinit var mockModèleÉvénements: ModèleÉvénements
    lateinit var fauxUtilisateurConnecté : Utilisateur

    @Before
    override fun setUp() {
        fauxUtilisateurConnecté = sourceBidon.listeUtils[0]
        super.setUp()
        mockVue = mock()
        mockModèleÉvénements = mock()
        ModèleAuthentification.utilisateurConnecté = fauxUtilisateurConnecté
        présentateurTruqué = PrésentateurMesÉvènements(
            mockVue,
            mockModèleÉvénements
        )
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        Assert.assertTrue(true)
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande la liste des présences de l'utilisateur, on appelle la méthode pour afficher la liste`() {
        var listeÉvénements : List<Événement>
        runBlocking(coroutineProvider.io) {
            listeÉvénements = sourceBidon.getÉvénementsParParticipation(1)
            doReturn("urlFactice.com").whenever(mockModèleÉvénements).getImageÉvénement(any())
            doReturn(listeÉvénements).whenever(mockModèleÉvénements).demanderLesParticipations(any())

            présentateurTruqué.traiterRequêtelancerCoroutine(false)
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockModèleÉvénements,
            times(invocationUnique)
        ) {
            demanderLesParticipations(1)
        }

       verifyBlocking(
            mockVue,
            times(invocationUnique)
        ) {
            afficherListeEvenements(eq(listeÉvénements),any())
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande la liste des événements crées par l'utilisateur, on appelle la méthode pour afficher la liste`() {
        var listeÉvénements : List<Événement>
        runBlocking(coroutineProvider.io) {
            listeÉvénements = sourceBidon.getÉvénementsParOrganisateur(1)
            doReturn("urlFactice.com").whenever(mockModèleÉvénements).getImageÉvénement(any())
            doReturn(listeÉvénements).whenever(mockModèleÉvénements).demanderSesPropreÉvènement(any())

            présentateurTruqué.traiterRequêtelancerCoroutine(true)
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockModèleÉvénements,
            times(invocationUnique)
        ) {
            demanderSesPropreÉvènement(1)
        }

        verifyBlocking(
            mockVue,
            times(invocationUnique)
        ) {
            afficherListeEvenements(eq(listeÉvénements),any())
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande l'affichage d'un événement sélectioné, on appelle la méthode pour afficher l'événement`() {
        ModèleÉvénements.événementPrésenté = sourceBidon.listeEvens[0]
        runBlocking(coroutineProvider.io) {
            présentateurTruqué.traiterRequêteAfficherÉvénement(1)
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockModèleÉvénements,
            times(invocationUnique)
        ) {
            setÉvénementPrésenté(any())
        }

        verifyBlocking(
            mockVue,
            times(invocationUnique)
        ) {
            afficherÉvénementSelectionné()
        }
    }
}