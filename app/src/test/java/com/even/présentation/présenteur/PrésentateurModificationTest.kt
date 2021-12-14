package com.even.présentation.présenteur

import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.ApiReponse
import com.even.testHelper.CouroutineTestHelper
import com.google.gson.JsonParseException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.internal.verification.Times
import org.mockito.kotlin.*
import retrofit2.Response

class PrésentateurModificationTest : CouroutineTestHelper() {

    lateinit var présentateurTruqué: PrésentateurModification
    lateinit var mockVue: IModificationÉvénement.IVue
    lateinit var mockModele: ModèleÉvénements
    lateinit var fauxEvenement: Événement

    @Before
    override fun setUp() {
        super.setUp()
        fauxEvenement = Événement(
            1,
            "Party chez Bob",
            "Maison de Bob",
            "2021-11-24T18:00",
            1,
            "gros party chez Bob let's gooooooo!"
        )
        mockVue = mock()
        mockModele = mock()
        présentateurTruqué =
            PrésentateurModification(mockVue, mockModele, coroutineProvider)
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete modifier evenement, il modifie le modele du modele evenement`() {
        doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
        présentateurTruqué.traiterRequêteModifierÉvénement(
            fauxEvenement.nomEvenement,
            fauxEvenement.date,
            fauxEvenement.location,
            fauxEvenement.description
        )
        verifyBlocking(
            mockModele,
            Times(invocationUnique)
        ) {
            modifierÉvénement(
                fauxEvenement
            )
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete modifier evenement, il modifie l'évènement présenté`() {
        doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
        présentateurTruqué.traiterRequêteModifierÉvénement(
            fauxEvenement.nomEvenement,
            fauxEvenement.date,
            fauxEvenement.location,
            fauxEvenement.description
        )
        verifyBlocking(mockModele, times(invocationUnique)) {
            setÉvénementPrésenté(fauxEvenement.idEvenement)
        }
    }


    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete modifier evenement et que la reponse de la source est positive, la vue affiche un evenement modifié`() {
        runBlocking {
            doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
            doReturn(Response.success(200, fauxEvenement)).whenever(mockModele)
                .modifierÉvénement(any())
            présentateurTruqué.traiterRequêteModifierÉvénement(
                fauxEvenement.nomEvenement,
                fauxEvenement.date,
                fauxEvenement.location,
                fauxEvenement.description
            )
            delay(delaiPourWithContext)
        }

        verifyBlocking(mockVue, times(invocationUnique)) {
            afficherÉvénementModifiéOuRetourMenu(modification = true)
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete modifier evenement et que la reponse de la source est negative, il affiche un toast erreur`() {
        runBlocking {
            doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
            doReturn(
                Response.error<ApiReponse>(
                    400, "".toResponseBody("".toMediaTypeOrNull())
                )
            ).whenever(mockModele)
                .modifierÉvénement(any())
            présentateurTruqué.traiterRequêteModifierÉvénement(
                fauxEvenement.nomEvenement,
                fauxEvenement.date,
                fauxEvenement.location,
                fauxEvenement.description
            )
            delay(delaiPourWithContext)
        }

        verifyBlocking(mockVue, times(invocationUnique)) {
            afficherErreurConnexion()
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete modifier evenement et que la requete echoue, la vue affiche un erreur`() {
        runBlocking {
            doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
            doThrow(JsonParseException("cool")).whenever(mockModele)
                .modifierÉvénement(any())
            présentateurTruqué.traiterRequêteModifierÉvénement(
                fauxEvenement.nomEvenement,
                fauxEvenement.date,
                fauxEvenement.location,
                fauxEvenement.description
            )
            delay(delaiPourWithContext)
        }

        verifyBlocking(mockVue, times(invocationUnique)) {
            afficherErreurConnexion()
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete annuler evenement, il annule l'évènement`() {
        doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
        présentateurTruqué.traiterRequêteAnnulerÉvénement()

        verifyBlocking(mockModele, times(invocationUnique)) {
            annulerÉvénement(fauxEvenement.idEvenement)
        }
    }


    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete annuler evenement et que la requete est reussite il detruit l'evenement dans le modele et affiche l'evenement`() {
        runBlocking {
            doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
            doReturn(Response.success(200, fauxEvenement)).whenever(mockModele)
                .annulerÉvénement(any())
            présentateurTruqué.traiterRequêteAnnulerÉvénement()
            delay(delaiPourWithContext)
        }

        verifyBlocking(mockModele, times(invocationUnique)) {
            ajouterUnÉvénement(evenement = null)
        }
        verifyBlocking(mockVue, times(invocationUnique)) {
            afficherÉvénementModifiéOuRetourMenu(modification = false)
        }
    }


    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete annuler evenement et que la requete est echoue il affiche un message d'erreur`() {
        runBlocking {
            doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
            doThrow(JsonParseException("cool")).whenever(mockModele)
                .annulerÉvénement(any())
            présentateurTruqué.traiterRequêteAnnulerÉvénement()
            delay(delaiPourWithContext)
        }

        verifyBlocking(mockVue, times(invocationUnique)) {
            afficherErreurConnexion()
        }
    }

    @Test
    fun `Etant donne un presentateur lorsqu'il traite la requete remplir champs il appelle la vue`() {
        doReturn(fauxEvenement).whenever(mockModele).obtenirÉvénement()
        présentateurTruqué.traiterRequêteRemplirChamps()
        verify(mockVue).remplirChamps(fauxEvenement)
    }


}