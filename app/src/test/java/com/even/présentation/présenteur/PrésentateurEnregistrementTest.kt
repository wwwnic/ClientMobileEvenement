package com.even.présentation.présenteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleAuthentification
import com.even.testHelper.CouroutineTestHelper
import com.even.testHelper.SubstitueReponseApi
import com.google.gson.JsonParseException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PrésentateurEnregistrementTest : CouroutineTestHelper() {


    lateinit var présentateurTruqué: PrésentateurEnregistrement
    lateinit var mockVue: IEnregistrement.IVue
    lateinit var mockModele: ModèleAuthentification
    lateinit var mockValidateur: ValidateurTextuel
    lateinit var fauxUtilisateur: Utilisateur

    val fauxNomUtilisateur = "Bobinette"
    val fauxMotDePasse = "Jupiter!!"
    val fauxCourriel = "Bobinette@gmail.com"
    val fauxTelephone = "5141234567"

    @Before
    override fun setUp() {
        super.setUp()
        fauxUtilisateur = Utilisateur(
            1,
            "Bobinette",
            "Jupiter!!",
            "Bobinette@gmail.com",
            "5141234567",
            SimpleDateFormat("yyyy.MM").format(Date())
        )
        mockVue = mock()
        mockModele = mock()
        mockValidateur = mock()
        présentateurTruqué =
            PrésentateurEnregistrement(mockVue, mockModele, mockValidateur, coroutineProvider)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il verifie les identifiant, les methodes de verification sont appelees une fois avec les bons parametres`() {
        présentateurTruqué.traiterRequêteEnregistrerUtilisateur(
            fauxNomUtilisateur, fauxMotDePasse, fauxCourriel, fauxTelephone
        )
        verify(mockValidateur, times(invocationUnique)).validerNomUsager(fauxNomUtilisateur)
        verify(mockValidateur, times(invocationUnique)).validerMotDePasse(fauxMotDePasse)
        verify(mockValidateur, times(invocationUnique)).validerCourriel(fauxCourriel)
        verify(mockValidateur, times(invocationUnique)).validerTelephone(fauxTelephone)
    }


    @Test
    fun `Etant donne un presentateur lorsqu'il verifie les identifiants et que les verifications echouent, elles affichent une erreur dans la vue en plus d'un toast`() {
        val informationInvalide = "!@ldsfk;ajgfl"
        présentateurTruqué.traiterRequêteEnregistrerUtilisateur(
            informationInvalide, informationInvalide, informationInvalide, informationInvalide
        )
        val vérificationsÉchoué = true
        verify(mockVue, times(invocationUnique)).afficherErreurNomUsager(vérificationsÉchoué)
        verify(mockVue, times(invocationUnique)).afficherErreurMotDePasse(vérificationsÉchoué)
        verify(mockVue, times(invocationUnique)).afficherErreurCourriel(vérificationsÉchoué)
        verify(mockVue, times(invocationUnique)).afficherErreurTéléphone(vérificationsÉchoué)
        verify(mockVue, times(invocationUnique)).afficherToastErreurEnregistrement()
    }


    private fun bouchonValidationReussi() {
        doReturn(true).whenever(mockValidateur).validerNomUsager(any())
        doReturn(true).whenever(mockValidateur).validerMotDePasse(any())
        doReturn(true).whenever(mockValidateur).validerCourriel(any())
        doReturn(true).whenever(mockValidateur).validerTelephone(any())
    }

    @Test
    fun `Etant donne un presentateur, lorsque la verification reussi une requete enregistrement est lancee dans le model avec les bonnes informations`() {
        runBlocking(coroutineProvider.io) {
            bouchonValidationReussi()
            présentateurTruqué.traiterRequêteEnregistrerUtilisateur(
                fauxNomUtilisateur,
                fauxMotDePasse,
                fauxCourriel,
                fauxTelephone
            )
        }
        verifyBlocking(
            mockModele,
            times(invocationUnique)
        ) {
            effectuerEnregistrement(
                fauxNomUtilisateur,
                fauxMotDePasse,
                fauxCourriel,
                fauxTelephone
            )
        }
    }

    @Test
    fun `Etant donne un presentateur, lorsque le modele retourne une reponse apres l'enregistrement et que c'est réussi, la vue navigue veres connexion et affiche un toast succes `() {
        runBlocking(coroutineProvider.io) {
            bouchonValidationReussi()
            doReturn(Response.success(200, "")).whenever(mockModele)
                .effectuerEnregistrement(any(), any(), any(), any())

            présentateurTruqué.traiterRequêteEnregistrerUtilisateur(
                fauxNomUtilisateur,
                fauxMotDePasse,
                fauxCourriel,
                fauxTelephone
            )
            delay(delaiPourWithContext)
        }
        verifyBlocking(mockVue, times(invocationUnique)) { naviguerVersConnexion() }
        verifyBlocking(mockVue, times(invocationUnique)) { afficherToastSuccesEnregistrement() }
    }


    @Test
    fun `Etant donne un presentateur, lorsque le modele retourne une reponse apres l'enregistrement et que c'est echouee, la vue affiche un toast erreur `() {
        runBlocking(coroutineProvider.io) {
            bouchonValidationReussi()
            doReturn(SubstitueReponseApi().erreur400).whenever(mockModele)
                .effectuerEnregistrement(any(), any(), any(), any())

            présentateurTruqué.traiterRequêteEnregistrerUtilisateur(
                fauxNomUtilisateur,
                fauxMotDePasse,
                fauxCourriel,
                fauxTelephone
            )
            delay(delaiPourWithContext)
        }
        verifyBlocking(mockVue, times(invocationUnique)) { afficherToastErreurEnregistrement() }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il rencontre une erreur, il affiche un toast erreur serveur`() {
        runBlocking(coroutineProvider.io) {
            bouchonValidationReussi()
            doThrow(JsonParseException("Une execption pas très concrete")).whenever(mockModele)
                .effectuerEnregistrement(any(), any(), any(), any())

            présentateurTruqué.traiterRequêteEnregistrerUtilisateur(
                fauxNomUtilisateur,
                fauxMotDePasse,
                fauxCourriel,
                fauxTelephone
            )
            delay(delaiPourWithContext)
        }
        verifyBlocking(mockVue, times(invocationUnique)) { afficherToastErreurServeur() }
    }
}