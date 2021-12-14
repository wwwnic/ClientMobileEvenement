package com.even.présentation.présenteur

import com.even.testHelper.CouroutineTestHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class PrésentateurRechercheTest : CouroutineTestHelper() {

    lateinit var mockVue : IRecherche.IVue
    lateinit var présentateurTruqué : IRecherche.IPrésentateur

    override fun setUp() {
        super.setUp()

        mockVue = mock()
        présentateurTruqué = PrésentateurRecherche(mockVue)
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête d'afficher les résultats avec un bon tag, il affiche les résutats de la recherche correctement`() {

        runBlocking (coroutineProvider.io){
            présentateurTruqué.traiterRequêteRechercheÉvénement("Party","Party","Party","Party")

            delay(delaiPourWithContext)
        }

        verify(mockVue).afficherRésultatsRecherche("Party:Party:Party:Party")
    }

    @Test
    fun `Étant donné un présentateur, quand il traite la requête d'afficher les résultats avec un tag qui n'est pas assez long, il inqdique que le mot-clé n'était pas bon`() {
        runBlocking (coroutineProvider.io){
            présentateurTruqué.traiterRequêteRechercheÉvénement("","","","")

            delay(delaiPourWithContext)
        }

        verify(mockVue).afficherMessageAucunMotCle()
    }
}