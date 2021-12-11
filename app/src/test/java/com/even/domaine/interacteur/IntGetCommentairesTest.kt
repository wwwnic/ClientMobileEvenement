package com.even.domaine.interacteur

import com.even.domaine.entité.Commentaire
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class IntGetCommentairesTest : CouroutineTestHelper() {
    lateinit var mockSourceDeDonnées: ISourceDeDonnées
    lateinit var interacteurTruqué: IntGetCommentaires

    @Before
    override fun setUp() {
        super.setUp()
        mockSourceDeDonnées = mock()
        interacteurTruqué = IntGetCommentaires(mockSourceDeDonnées)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande la liste de commentaires d'un événement donné, la méthode est appelée`() {
        runBlocking(coroutineProvider.io) {
            mockSourceDeDonnées.getCommentairesParEvenement(1)
        }
        verifyBlocking(
            mockSourceDeDonnées,
            times(invocationUnique)
        ) { interacteurTruqué.getCommentairesParÉvénement(1) }
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande la liste de commentaires d'un événement donné, on retourne une liste de commentaires`() {
        val liste = sourceBidon.listeComm
        val listeRetournée : List<Commentaire>

        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockSourceDeDonnées).getCommentairesParEvenement(any())
            listeRetournée = interacteurTruqué.getCommentairesParÉvénement(1)
        }
        assertEquals(liste,listeRetournée)
    }
}