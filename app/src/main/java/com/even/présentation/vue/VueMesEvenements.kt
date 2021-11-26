package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.even.R
import com.even.domaine.entité.Événement
import com.even.présentation.présenteur.IMesÉvènements
import com.even.présentation.présenteur.PrésentateurMesÉvènements
import com.even.ui.composants.FragmentLoader
import com.even.ui.composants.ListeCarteÉvénements
import com.google.android.material.tabs.TabLayout

class VueMesEvenements() : Fragment(R.layout.fragment_mes_evenements), IMesÉvènements.IVue {

    lateinit var fragmentLoader: FragmentLoader
    lateinit var présentateur: IMesÉvènements.IPrésentateur
    lateinit var composeView: ComposeView
    private var estSurMesÉvènement = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)
        val barreTab = view.findViewById<TabLayout>(R.id.barreTabMesEvens)
        val boutonCreer = view.findViewById<Button>(R.id.boutonCreer)
        composeView = view.findViewById(R.id.mesEven_listeBlocsEven)
        présentateur = PrésentateurMesÉvènements(this)
        présentateur.traiterRequêteAfficherLesParticipations()
        boutonCreer.setOnClickListener { fragmentLoader.loadFragment(VueCreationEvenement()) }
        barreTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab?.position == 0) {
                    estSurMesÉvènement = false
                    boutonCreer.visibility = View.INVISIBLE
                    présentateur.traiterRequêteAfficherLesParticipations()
                } else {
                    estSurMesÉvènement = true
                    boutonCreer.visibility = View.VISIBLE
                    présentateur.traiterRequêteAfficherSesPropreÉvènements()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    override fun afficherListeEvenements(
        lstÉvènenement: List<Événement>,
        imageUrl: (Int) -> String
    ) {
        if (!lstÉvènenement.isEmpty()) {
            val chargement = requireView().findViewById<ProgressBar>(R.id.mesEvenement_chargement)
            chargement.visibility = View.INVISIBLE
            afficherÉvènementCliquable(lstÉvènenement, imageUrl)
        }
    }

    private fun afficherÉvènementCliquable(
        lstÉvènenement: List<Événement>,
        imageUrl: (Int) -> String
    ) {
        composeView.setContent {
            MaterialTheme {
                ListeCarteÉvénements(lstÉvènenement, clickEvent = {
                    fragmentLoader.loadFragment(
                        if (estSurMesÉvènement) VueDetailsEvenement() else VueModifierEvenement(),
                        it.idEvenement.toString()
                    )
                }, imageUrl = { img -> imageUrl(img) })
            }
        }
    }


    override fun afficherAucunRésultatRecherche(estErreurConnexion: Boolean) {
        val vue = requireView()
        val chargement = vue.findViewById<ProgressBar>(R.id.mesEvenement_chargement)
        val emojiTriste = vue.findViewById<ImageView>(R.id.mesEvenement_imageErreur)
        val textErreur = vue.findViewById<TextView>(R.id.mesEvenement_textErreur)
        chargement.visibility = View.INVISIBLE
        emojiTriste.visibility = View.VISIBLE
        textErreur.text =
            getString(
                if (estErreurConnexion) {
                    R.string.serveur_error
                } else R.string.not_going_to_any_event
            )
        textErreur.visibility = View.VISIBLE
    }
}