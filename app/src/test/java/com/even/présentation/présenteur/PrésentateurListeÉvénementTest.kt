package com.even.présentation.présenteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleÉvénements
import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import org.mockito.kotlin.*
import java.util.Arrays.asList

class PrésentateurListeÉvénementTest : CouroutineTestHelper() {

    lateinit var fauxUtilisateur: Utilisateur
    lateinit var fauxÉvénement : Événement

    lateinit var présentateurTruqué : PrésentateurListeÉvénements
    lateinit var mockModèle : ModèleÉvénements
    lateinit var mockVue : IListeEvenements.IVue

    @Before
    override fun setUp() {
        super.setUp()

        fauxÉvénement = Événement(
            1,
            "Party",
            "Chez moi",
            SimpleDateFormat("yyyy.MM").format(Date()),
            1,
            "Gros party chez moi"
        )

        fauxUtilisateur = Utilisateur(
            1,
            "Bobinette",
            "Jupiter!!",
            "Bobinette@gmail.com",
            "5141234567",
            SimpleDateFormat("yyyy.MM").format(Date())
        )

        mockModèle = mock()
        mockVue = mock()
        présentateurTruqué = PrésentateurListeÉvénements(mockVue, mockModèle)

    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête d'afficher la liste d'événement récent sans paramètre, il va chercher les informations de la liste dans le modèle`(){
        var lstÉvénement = asList(fauxÉvénement)

        runBlocking (coroutineProvider.io){
            doReturn(lstÉvénement).whenever(mockModèle).getÉvénementsRécents()

            présentateurTruqué.traiterRequêteAfficherListeRecents()
        }

        verifyBlocking(mockModèle, times(invocationUnique)) {
            getÉvénementsRécents()
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'il traite la requête d'afficher la liste d'événement récent avec une recherche, il va chercher la liste dans le modèle`() {
        var lstRecherche = asList(fauxÉvénement)

        runBlocking (coroutineProvider.io){
            doReturn(lstRecherche).whenever(mockModèle).getÉvénementsParRecherche(any(), any(), any(), any())

            présentateurTruqué.traiterRequêteAfficherListeRecherche("Party:10:Chez moi:1")
        }

        verifyBlocking(mockModèle, times(invocationUnique)) {
            getÉvénementsParRecherche("Party", "10", "Chez moi", "1")
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'il traire la requête d'afficher les détails de l'événement, l'événement est placé dans le modèle`() {

        runBlocking(coroutineProvider.io) {
            présentateurTruqué.traiterRequêteAfficherDétailsÉvénement(fauxÉvénement.idEvenement)
        }

        verifyBlocking(mockModèle, times(invocationUnique)) {
            setÉvénementPrésenté(fauxÉvénement.idEvenement)
        }
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'il reçoit la liste d'événement récent, il l'affaiche dans la vue`(){
        var lstÉvénement = asList(fauxÉvénement)

        runBlocking(coroutineProvider.io) {
            doReturn(lstÉvénement).whenever(mockModèle).getÉvénementsRécents()

            présentateurTruqué.traiterRequêteAfficherListeRecents()

            delay(delaiPourWithContext)
        }

        verify(mockVue).afficherListeEvenements(eq(lstÉvénement), any())
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'il reçoit la liste de recherche d'événement qui n'est pas vide, il l'affiche dans la vue`() {
        var lstRecherche = asList(fauxÉvénement)

        runBlocking (coroutineProvider.io){
            doReturn(lstRecherche).whenever(mockModèle).getÉvénementsParRecherche(any(), any(), any(), any())

            présentateurTruqué.traiterRequêteAfficherListeRecherche("Party:10:Chez moi:1")

            delay(delaiPourWithContext)
        }

        verify(mockVue).afficherListeEvenements(eq(lstRecherche), any())
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'il reçoit une liste d'événement vide, il affiche une erreur de connexion`(){
        var lstÉvénement = emptyList<Événement>()

        runBlocking(coroutineProvider.io) {
            doReturn(lstÉvénement).whenever(mockModèle).getÉvénementsRécents()

            présentateurTruqué.traiterRequêteAfficherListeRecents()

            delay(delaiPourWithContext)
        }

        verify(mockVue).afficherErreurConnexion()
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'il reçoit une liste vide après une recherche, il affiche un message indiquant qu'il n'y a aucun résultat`() {
        var lstRecherche = emptyList<Événement>()

        runBlocking (coroutineProvider.io){
            doReturn(lstRecherche).whenever(mockModèle).getÉvénementsParRecherche(any(), any(), any(), any())

            présentateurTruqué.traiterRequêteAfficherListeRecherche("Party:10:Chez moi:1")

            delay(delaiPourWithContext)
        }

        verify(mockVue).afficherAucunRésultatRecherche()
    }

    @Test
    fun `Étant donné un présentateur, lorsqu'il traite la requête d'afficher les détails de l'événement, il affiche la page de détail correctement`(){

        runBlocking (coroutineProvider.io){
            présentateurTruqué.traiterRequêteAfficherDétailsÉvénement(fauxÉvénement.idEvenement)

            delay(delaiPourWithContext)
        }

        verify(mockVue).afficherDétailsÉvénement()
    }
}