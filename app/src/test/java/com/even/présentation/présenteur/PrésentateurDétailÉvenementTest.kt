package com.even.présentation.présenteur

import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import com.even.testHelper.CouroutineTestHelper
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
    lateinit var fauxUtilisateur: Utilisateur
    lateinit var lstEven: List<Événement>


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

        lstEven = asList(fauxÉvénement)




        fauxUtilisateur = Utilisateur(
            1,
            "Bobinette",
            "Jupiter!!",
            "Bobinette@gmail.com",
            "5141234567",
            SimpleDateFormat("yyyy.MM").format(Date())
        )

        mockVue = mock()
        mockModèleÉvénement = mock()
        mockModèleUtilisateurs = mock()
        mockModèleAuthentification = mock()
        ModèleAuthentification.utilisateurConnecté = fauxUtilisateur
        ModèleÉvénements.événementPrésenté = fauxÉvénement
        présentateurTruqué = PrésentateurDétailÉvenement(
            mockVue,
            mockModèleAuthentification,
            mockModèleUtilisateurs,
            mockModèleÉvénement,
            coroutineProvider
        )


    }

    @Test
    fun `Etant donne une presentateur, quand il traite la requete afficher detail, il va chercher les informations necessaire dans tous les modeles`() {
        runBlocking(coroutineProvider.io) {
            doReturn("urlFactice.com").whenever(mockModèleÉvénement).getImageÉvénement(any())
            doReturn(lstEven).whenever(mockModèleÉvénement).demanderLesParticipations(any())

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
}