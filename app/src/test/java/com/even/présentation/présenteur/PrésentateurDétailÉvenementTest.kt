package com.even.présentation.présenteur

import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.UtilisateurÉvénement
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import com.even.testHelper.CouroutineTestHelper
import com.google.gson.JsonParseException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Arrays.asList

class PrésentateurDétailÉvenementTest : CouroutineTestHelper() {


    lateinit var présentateurTruqué: PrésentateurDétailÉvenement
    lateinit var mockVue: IDétailÉvenement.IVue
    lateinit var mockModèleÉvénement: ModèleÉvénements
    lateinit var mockModèleUtilisateurs: ModèleUtilisateurs
    lateinit var mockModèleAuthentification: ModèleAuthentification
    lateinit var fauxÉvénement: Événement
    lateinit var fauxÉvénement2: Événement
    lateinit var fauxUtilisateur: Utilisateur
    lateinit var fauxUtilisateurAutre : Utilisateur
    lateinit var fauxCommentaire: Commentaire


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

        fauxÉvénement2 = Événement(
            2,
            "Party",
            "Chez toi",
            SimpleDateFormat("yyyy.MM").format(Date()),
            2,
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

        fauxUtilisateurAutre = Utilisateur(
            2,
            "Bobinette",
            "Jupiter!!",
            "Bobinette@gmail.com",
            "5141234567",
            SimpleDateFormat("yyyy.MM").format(Date())
        )

        fauxCommentaire = Commentaire(
            1,
            1,
            "WOW"
        )

        mockVue = mock()
        mockModèleÉvénement = mock()
        mockModèleUtilisateurs = mock()
        mockModèleAuthentification = mock()
        ModèleÉvénements.événementPrésenté = fauxÉvénement
        ModèleAuthentification.utilisateurConnecté = fauxUtilisateur
        présentateurTruqué = PrésentateurDétailÉvenement(
            mockVue,
            mockModèleAuthentification,
            mockModèleUtilisateurs,
            mockModèleÉvénement,
            coroutineProvider
        )
    }

    @Test
    fun `Etant donne un presentateur, quand il traite la requete afficher detail, il va chercher les informations necessaire dans tous les modeles`() {
        var lstEven = asList(fauxÉvénement)
        var lstParticipant = asList(fauxUtilisateur)

        runBlocking(coroutineProvider.io) {
            doReturn("urlFactice.com").whenever(mockModèleÉvénement).getImageÉvénement(any())
            doReturn(lstEven).whenever(mockModèleÉvénement).demanderLesParticipations(any())
            doReturn(lstParticipant).whenever(mockModèleUtilisateurs).getUtilisateursDansÉvénement(any())

            présentateurTruqué.traiterRequêteAfficherDétailÉvenement(fauxÉvénement.idEvenement)
        }

        verifyBlocking(
            mockModèleÉvénement,
            times(invocationUnique)
        ) {
            getImageÉvénement(fauxÉvénement.idEvenement)
        }

        verifyBlocking(
            mockModèleÉvénement,
            times(invocationUnique)
        ) {
            demanderLesParticipations(fauxUtilisateur.idUtilisateur!!)
        }

        verifyBlocking(
            mockModèleUtilisateurs,
            times(invocationUnique)
        ) {
            getUtilisateursDansÉvénement(fauxÉvénement.idEvenement)
        }
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête ajouter une participation, il ajoute la participation de l'utilisateur à l'événement en question`() {
        présentateurTruqué.participation = false
        var utilisateurÉvénement = UtilisateurÉvénement(fauxUtilisateur.idUtilisateur!!,fauxÉvénement.idEvenement)

        runBlocking(coroutineProvider.io) {
            présentateurTruqué.traiterRequêteAjouterRetirerParticipation(fauxÉvénement.idEvenement)
        }

        verifyBlocking(mockModèleÉvénement, times(invocationUnique)) {
            ajouterParticipation(utilisateurÉvénement)
        }
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête retirer une participation, il retire la participation de l'utilisateur à l'événement en question`() {
        présentateurTruqué.participation = true
        var utilisateurÉvénement = UtilisateurÉvénement(fauxUtilisateur.idUtilisateur!!, fauxÉvénement.idEvenement)

        runBlocking(coroutineProvider.io) {
            présentateurTruqué.traiterRequêteAjouterRetirerParticipation(fauxÉvénement.idEvenement)
        }

        verifyBlocking(mockModèleÉvénement, times(invocationUnique)) {
            retirerParticipation(utilisateurÉvénement)
        }
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête d'afficher les participants, il va chercher la liste de participant`() {
        var lstParticipant = asList(fauxUtilisateur)

        runBlocking (coroutineProvider.io) {
            doReturn(lstParticipant).whenever(mockModèleUtilisateurs).getUtilisateursDansÉvénement(any())

            présentateurTruqué.traiterRequêteAfficherParticipants()
        }

        verifyBlocking(mockModèleUtilisateurs, times(invocationUnique)) {
            getUtilisateursDansÉvénement(fauxÉvénement.idEvenement)
        }
    }

    @Test
    fun `Étant donné un présentateur, quand il traite le requête d'afficher les commentaires, il va chercher la liste de commentaire`() {
        var lstCommentaire = asList(fauxCommentaire)

        runBlocking(coroutineProvider.io) {
            doReturn(lstCommentaire).whenever(mockModèleÉvénement).getCommentairesDansÉvénement(any())

            présentateurTruqué.traiterRequêteAfficherCommentaires()
        }

        verifyBlocking(mockModèleÉvénement, times(invocationUnique)) {
            getCommentairesDansÉvénement(fauxÉvénement.idEvenement)
        }
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête d'afficher les informations de l'événement, il les affiche correctement sur la vue`() {

        var lstEven = asList(fauxÉvénement)
        var lstParticipant = asList(fauxUtilisateur)

        runBlocking(coroutineProvider.io) {
            doReturn("urlFactice.com").whenever(mockModèleÉvénement).getImageÉvénement(any())
            doReturn(lstEven).whenever(mockModèleÉvénement).demanderLesParticipations(any())
            doReturn(lstParticipant).whenever(mockModèleUtilisateurs).getUtilisateursDansÉvénement(any())

            présentateurTruqué.traiterRequêteAfficherDétailÉvenement(fauxÉvénement.idEvenement)

            delay(delaiPourWithContext)
        }

        verify(mockVue, times(invocationUnique)).setInfo(fauxÉvénement)
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête d'afficher le nombre de participant, il affiche affiche l'information correctement`(){
        var lstEven = asList(fauxÉvénement)
        var lstParticipant = asList(fauxUtilisateur)

        runBlocking(coroutineProvider.io) {
            doReturn("urlFactice.com").whenever(mockModèleÉvénement).getImageÉvénement(any())
            doReturn(lstEven).whenever(mockModèleÉvénement).demanderLesParticipations(any())
            doReturn(lstParticipant).whenever(mockModèleUtilisateurs).getUtilisateursDansÉvénement(any())

            présentateurTruqué.traiterRequêteAfficherDétailÉvenement(fauxÉvénement.idEvenement)

            delay(delaiPourWithContext)
        }

        verify(mockVue, times(invocationUnique)).setNombreParticipant(présentateurTruqué.nombreParticipant)
    }

    @Test
    fun `Étant donné un présentateur quand il traite la requête d'afficher le bouton de participation, le bouton s'affiche correctement`(){

        var listeDeParticipation = asList(fauxÉvénement2)
        var nombreParticipant = asList(fauxUtilisateur)

        présentateurTruqué.idUtilisateurConnecté = fauxUtilisateurAutre.idUtilisateur!!

        runBlocking(coroutineProvider.io) {
            doReturn("urlFactice.com").whenever(mockModèleÉvénement).getImageÉvénement(any())
            doReturn(listeDeParticipation).whenever(mockModèleÉvénement).demanderLesParticipations(any())
            doReturn(nombreParticipant).whenever(mockModèleUtilisateurs).getUtilisateursDansÉvénement(any())

            présentateurTruqué.traiterRequêteAfficherDétailÉvenement(fauxÉvénement.idEvenement)

            delay(delaiPourWithContext)
        }

        verify(mockVue, times(invocationUnique)).afficherParticipation()
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête d'afficher bouton ne plus participer, le bouton s'affiche correctement`() {
        var listeDeParticipation = asList(fauxÉvénement)
        var nombreParticipant = asList(fauxUtilisateurAutre)

        présentateurTruqué.idUtilisateurConnecté = fauxUtilisateurAutre.idUtilisateur!!

        runBlocking(coroutineProvider.io) {
            doReturn("urlFactice.com").whenever(mockModèleÉvénement).getImageÉvénement(any())
            doReturn(listeDeParticipation).whenever(mockModèleÉvénement).demanderLesParticipations(any())
            doReturn(nombreParticipant).whenever(mockModèleUtilisateurs).getUtilisateursDansÉvénement(any())

            présentateurTruqué.traiterRequêteAfficherDétailÉvenement(fauxÉvénement.idEvenement)

            delay(delaiPourWithContext)
        }

        verify(mockVue, times(invocationUnique)).afficherNePlusParticiper()
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête de cache le bouton de participation pour l'organisateur de l'événement, le bouton est correctement caché`(){
        var listeDeParticipation = asList(fauxÉvénement)
        var nombreParticipant = asList(fauxUtilisateur)

        présentateurTruqué.idUtilisateurConnecté = fauxUtilisateur.idUtilisateur!!

        runBlocking(coroutineProvider.io) {
            doReturn("urlFactice.com").whenever(mockModèleÉvénement).getImageÉvénement(any())
            doReturn(listeDeParticipation).whenever(mockModèleÉvénement).demanderLesParticipations(any())
            doReturn(nombreParticipant).whenever(mockModèleUtilisateurs).getUtilisateursDansÉvénement(any())

            présentateurTruqué.traiterRequêteAfficherDétailÉvenement(fauxÉvénement.idEvenement)

            delay(delaiPourWithContext)
        }

        verify(mockVue, times(invocationUnique)).cacherBoutonParticipation()
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il rencontre une erreur, il affiche un toast erreur serveur`() {
        runBlocking(coroutineProvider.io) {
            doThrow(JsonParseException("Une erreur quelconque")).whenever(mockModèleÉvénement)
                .demanderLesParticipations(fauxUtilisateur.idUtilisateur!!)

            présentateurTruqué.traiterRequêteAfficherDétailÉvenement(fauxÉvénement.idEvenement)

            delay(delaiPourWithContext)
        }
        verify(mockVue, times(invocationUnique)).afficherToastErreurServeur()
    }

    /*
    /**
     * À revoir, je n'arrive pas à le faire fonctionner
     */
    @Test
    fun `Étant donné un présentateur, lorsque la participation d'un utilisateur est ajouté, il affiche de toast pour aviser l'utilisateur que l'action s'est effectué avec succès`(){
        présentateurTruqué.idUtilisateurConnecté = fauxUtilisateur.idUtilisateur!!
        présentateurTruqué.participation = false

        runBlocking(coroutineProvider.io) {
            doNothing().`when`(mockModèleÉvénement).ajouterParticipation(any())

            présentateurTruqué.traiterRequêteAjouterRetirerParticipation(fauxÉvénement.idEvenement)

            delay(delaiPourWithContext)
        }

        verify(mockVue, times(invocationUnique)).afficherToastParticipationAjouté()
    }
     */



}