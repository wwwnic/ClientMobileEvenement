package com.even.présentation.vue

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.even.MainActivity
import com.even.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


private const val MESSAGE = "This is a test"
private const val PACKAGE_NAME = "com.example.even"

//https://developer.android.com/training/testing/ui-testing/espresso-testing#espresso-atr
@RunWith(AndroidJUnit4::class)
class VueConnexionTest : InstrumentalTestHelper() {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()


    @Test
    fun Appuyer_sur_bouton_creer_un_compte_dans_ecran_connexion_redirige_vers_ecran_enregistrement() {
        onView(withId(R.id.connexion_boutonEnregistrement)).perform(click())
        onView(withId(R.id.enregistrement_creer_un_compte)).check(matches(withText(R.string.create_account)))
    }

    @Test
    fun Appuyer_sur_bouton_connexion_sans_text_affiche_les_indices_en_rouge() {
        onView(withId(R.id.connexion_boutonConnexion)).perform(click())
        onView(withId(R.id.connexion_hint_nomUtilisateur)).check(matches(hasTextColor(R.color.rouge)))
        onView(withId(R.id.connexion_hint_motDePasse)).check(matches(hasTextColor(R.color.rouge)))
    }

    @Test
    fun Appuyer_sur_bouton_connexion_avec_text_valide_dans_nom_utilisateur_restore_son_indices_en_mauve() {
        val infoFactice = "Nicolas123"
        onView(withId(R.id.connexion_boutonConnexion)).perform(click())
        onView(withId(R.id.connexion_textNomUtilisateur)).perform(typeText(infoFactice))
        onView(withId(R.id.connexion_textNomUtilisateur)).perform(closeSoftKeyboard())
        onView(withId(R.id.connexion_boutonConnexion)).perform(click())
        onView(withId(R.id.connexion_hint_nomUtilisateur)).check(matches(hasTextColor(R.color.figmaMauve)))
    }


    @Test
    fun Appuyer_sur_bouton_connexion_avec_text_valide_dans_mot_de_passe_restore_son_indices_en_mauve() {
        val infoFactice = "Nicolas1234"
        onView(withId(R.id.connexion_boutonConnexion)).perform(click())
        onView(withId(R.id.connexion_textMotDePasse)).perform(typeText(infoFactice))
        onView(withId(R.id.connexion_textMotDePasse)).perform(closeSoftKeyboard())
        onView(withId(R.id.connexion_boutonConnexion)).perform(click())
        onView(withId(R.id.connexion_hint_motDePasse)).check(matches(hasTextColor(R.color.figmaMauve)))
    }

}


