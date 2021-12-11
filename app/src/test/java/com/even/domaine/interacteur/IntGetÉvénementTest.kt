package com.even.domaine.interacteur

import com.even.domaine.entité.Événement
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class IntGetÉvénementTest : CouroutineTestHelper() {

    lateinit var mockSourceDeDonnées: ISourceDeDonnées
    lateinit var interacteurTruqué : IntGetÉvénement

    @Before
    override fun setUp() {
        super.setUp()
        mockSourceDeDonnées = mock()
        interacteurTruqué = IntGetÉvénement(mockSourceDeDonnées)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande la liste d'événements récents, la méthode est appelée dans la source de données`() {
        runBlocking(coroutineProvider.io) {
            mockSourceDeDonnées.getAllEvenements()
        }
        verifyBlocking(
            mockSourceDeDonnées,
            times(invocationUnique)
        ) { interacteurTruqué.getÉvénementsRécents() }
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande la liste d'événements récents, on nous retourne une liste` () {
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Événement>
        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockSourceDeDonnées).getAllEvenements()
            listeRetournée = interacteurTruqué.getÉvénementsRécents()
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande un événement par son id,on retourne un événement`() {
        val événement = sourceBidon.listeEvens[0]
        val événementRetourné : Événement

        runBlocking(coroutineProvider.io) {
            doReturn(événement).whenever(mockSourceDeDonnées).getÉvénementParId(any())
            événementRetourné = interacteurTruqué.getÉvénementParId(1)!!
        }
        assertEquals(événement,événementRetourné)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande la liste d'événements crées par le même organisateur, on nous retourne une liste`() {
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Événement>
        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockSourceDeDonnées).getÉvénementsParOrganisateur(any())
            listeRetournée = interacteurTruqué.demanderMesÉvènements(1)
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande la liste d'événements auxquels participe un utilisateur donné, on nous retourne une liste`() {
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Événement>
        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockSourceDeDonnées).getÉvénementsParParticipation(any())
            listeRetournée = interacteurTruqué.demanderMesParticipations(1)
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande la liste d'événements recherchés par l'utilisateur, on nous retourne une liste`() {
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Événement>
        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockSourceDeDonnées).getÉvénementsParRecherche(any(),any(),any(),any())
            listeRetournée = interacteurTruqué.getÉvénementsParRecherche("a","b","c","d")
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande une image par id événement,on retourne un string`() {
        val lienImage = sourceBidon.getImageEvenement(1)
        val lienRetourné : String

        doReturn(lienImage).whenever(mockSourceDeDonnées).getImageEvenement(any())
        lienRetourné = interacteurTruqué.getImageÉvénement(1)
        assertEquals(lienImage,lienRetourné)
    }
}