package com.even.présentation.présenteur

import com.even.domaine.entité.Utilisateur
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleÉvénements
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class PrésentateurCreationEvenementTest : CouroutineTestHelper() {

    lateinit var présentateurTruqué : PrésentateurCreationÉvénement
    lateinit var mockVue : ICreationEvenement.IVue
    lateinit var mockModèleÉvénements: ModèleÉvénements
    lateinit var fauxUtilisateurConnecté : Utilisateur

    @Before
    override fun setUp() {
        fauxUtilisateurConnecté = sourceBidon.listeUtils[0]
        super.setUp()
        mockVue = mock()
        mockModèleÉvénements = mock()
        ModèleAuthentification.utilisateurConnecté = fauxUtilisateurConnecté
        présentateurTruqué = PrésentateurCreationÉvénement(
            mockVue,
            mockModèleÉvénements
        )
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        Assert.assertTrue(true)
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande de créer un événement, on appelle la méthode pour créer l'événement`() {
        ModèleÉvénements.événementPrésenté = sourceBidon.listeEvens[0]
        var dateBidon = sourceBidon.listeEvens[0].date
        runBlocking(coroutineProvider.io) {
            présentateurTruqué.traiterRequêteCréerÉvénement("aaa",dateBidon,"bbb","ccc")
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockModèleÉvénements,
            times(invocationUnique)
        ) {
            créerÉvénement(any())
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande de créer un événement et la création réussit, on set l'événement présenté et on affiche le nouvel événement`() {
        ModèleÉvénements.événementPrésenté = sourceBidon.listeEvens[0]
        var dateBidon = sourceBidon.listeEvens[0].date
        runBlocking(coroutineProvider.io) {
            doReturn(sourceBidon.listeEvens[0]).whenever(mockModèleÉvénements).créerÉvénement(any())
            présentateurTruqué.traiterRequêteCréerÉvénement("aaa",dateBidon,"bbb","ccc")
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
            afficherNouvelÉvénement(sourceBidon.listeEvens[0])
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'on demande de créer un événement et la création échoue, on affiche un message d'erreur`() {
        ModèleÉvénements.événementPrésenté = sourceBidon.listeEvens[0]
        var dateBidon = sourceBidon.listeEvens[0].date
        runBlocking(coroutineProvider.io) {
            doReturn(null).whenever(mockModèleÉvénements).créerÉvénement(any())
            présentateurTruqué.traiterRequêteCréerÉvénement("aaa",dateBidon,"bbb","ccc")
            delay(delaiPourWithContext)
        }

        verifyBlocking(
            mockVue,
            times(invocationUnique)
        ) {
            afficherErreurConnexion()
        }
    }
}