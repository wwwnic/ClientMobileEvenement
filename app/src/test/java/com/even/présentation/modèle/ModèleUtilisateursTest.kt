package com.even.présentation.modèle

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntGetUtilisateur
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class ModèleUtilisateursTest : CouroutineTestHelper(){
    lateinit var modèleTruqué : ModèleUtilisateurs
    lateinit var mockIntGetUtilisateur: IntGetUtilisateur

    @Before
    override fun setUp() {
        super.setUp()
        mockIntGetUtilisateur = mock()
        modèleTruqué = ModèleUtilisateurs(mockIntGetUtilisateur)
    }

    @Test
    fun `Vérifier que les tests fonctionnent`() {
        assertTrue(true)
    }

    @Test
    fun `Étant donné le modèle utilisateurs, lorsqu'on demande la liste d'utilisateurs dans un événement donné, la requete est envoyée correctement`() {
        val idÉvénement = sourceBidon.listeEvens[0].idEvenement

        runBlocking(coroutineProvider.io) {
            modèleTruqué.getUtilisateursDansÉvénement(idÉvénement)
        }
        verifyBlocking(
            mockIntGetUtilisateur,
            times(invocationUnique)
        ) { getUtilisateursDansÉvénement(idÉvénement) }
    }

    @Test
    fun `Étant donné le modèle utilisateurs, lorsqu'on demande la liste d'utilisateurs dans un événement donné, on retourne une liste d'utilisateurs`() {
        val idÉvénement = sourceBidon.listeEvens[0].idEvenement
        val liste = sourceBidon.listeEvens
        val listeRetournée : List<Utilisateur>

        runBlocking(coroutineProvider.io) {
            doReturn(liste).whenever(mockIntGetUtilisateur).getUtilisateursDansÉvénement(any())
            listeRetournée = modèleTruqué.getUtilisateursDansÉvénement(idÉvénement)
        }
        assertEquals(liste,listeRetournée)
    }

    @Test
    fun `Étant donné le modèle utilisateurs, lorsqu'on demande l'image utilisateur, on retourne un string contenant le lien vers l'image`() {
        val id = sourceBidon.listeUtils[0].idUtilisateur
        val lienImage = "http://140.82.8.101/images/utilisateurs/1.jpg"

        doReturn(lienImage).whenever(mockIntGetUtilisateur).getImageUtilisateur(any())
        val lienRetourné = modèleTruqué.getImageUtilisateur(id!!)
        assertEquals(lienImage,lienRetourné)
    }
}