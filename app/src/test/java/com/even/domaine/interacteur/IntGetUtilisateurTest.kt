package com.even.domaine.interacteur

import com.even.domaine.entité.Utilisateur
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class IntGetUtilisateurTest : CouroutineTestHelper() {

    lateinit var mockSourceDeDonnées : ISourceDeDonnées
    lateinit var interacteurTruqué : IntGetUtilisateur

    @Before
    override fun setUp() {
        super.setUp()
        mockSourceDeDonnées = mock()
        interacteurTruqué = IntGetUtilisateur(mockSourceDeDonnées)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande un utilisateur par son id, on retourne un utilisateur`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val utilisateurRetourné : Utilisateur

        runBlocking(coroutineProvider.io) {
            doReturn(utilisateur).whenever(mockSourceDeDonnées).getUtilisateurParId(any())
            utilisateurRetourné = interacteurTruqué.getParId(1)!!
        }
        assertEquals(utilisateur,utilisateurRetourné)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande les utilisateurs dans un événement donné, on retourne une liste d'utilisateurs`() {
        val liste = sourceBidon.listeUtils
        val listeRetournée : List<Utilisateur>

        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockSourceDeDonnées).getUtilisateursDansEvenement(any())
            listeRetournée = interacteurTruqué.getUtilisateursDansÉvénement(1)
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné un interacteur, lorsqu'on demande une image par id utilisateur,on retourne un string`() {
        val lienImage = sourceBidon.getImageUtilisateur(1)
        val lienRetourné : String

        doReturn(lienImage).whenever(mockSourceDeDonnées).getImageUtilisateur(any())
        lienRetourné = interacteurTruqué.getImageUtilisateur(1)
        assertEquals(lienImage,lienRetourné)
    }
}