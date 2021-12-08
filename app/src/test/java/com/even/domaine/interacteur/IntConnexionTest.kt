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

class IntConnexionTest : CouroutineTestHelper() {

    lateinit var mockSourceDeDonnées: ISourceDeDonnées
    lateinit var interacteurTruqué: IntConnexion

    @Before
    override fun setUp() {
        super.setUp()
        mockSourceDeDonnées = mock()
        interacteurTruqué = IntConnexion(mockSourceDeDonnées)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Etant donne un interacteur, lorsqu'on demande un profil, la methode est appelée dans la source de donnees avec les informations exacte`() {
        val utilisateur = sourceBidon.listeUtils[0]
        runBlocking(coroutineProvider.io) {
            mockSourceDeDonnées.demanderProfil(utilisateur)
        }
        verifyBlocking(
            mockSourceDeDonnées,
            times(invocationUnique)
        ) { demanderProfil(utilisateur) }
    }

    @Test
    fun `Etant donne un interacteur, lorsqu'on appelle la méthode pour demander un profil, celle-ci retourne l'utilisateur retourné par la source de donnée`() {
        val utilisateur = sourceBidon.listeUtils[0]
        val utilisateurRetourné: Utilisateur
        runBlocking(coroutineProvider.io) {
            doReturn(utilisateur).whenever(mockSourceDeDonnées).demanderProfil(any())
            utilisateurRetourné = interacteurTruqué.connexionDemanderProfil(utilisateur)!!
        }
        assertEquals(utilisateur, utilisateurRetourné)
    }
}