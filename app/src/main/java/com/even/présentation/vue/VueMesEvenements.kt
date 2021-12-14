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

/**
 *  Une vue qui permet d'interagir avec le fragment mes evenements
 *
 */
class VueMesEvenements() : Fragment(R.layout.fragment_mes_evenements), IMesÉvènements.IVue {

    lateinit var fragmentLoader: FragmentLoader
    lateinit var présentateur: IMesÉvènements.IPrésentateur
    lateinit var composeView: ComposeView
    lateinit var barreTab: TabLayout
    lateinit var boutonCreer: Button
    lateinit var chargement: ProgressBar
    lateinit var emojiTriste: ImageView
    lateinit var textErreur: TextView

    private var estSurMesÉvènement = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

        chargement = view.findViewById(R.id.mesEvenement_chargement)
        emojiTriste = view.findViewById(R.id.mesEvenement_imageErreur)
        textErreur = view.findViewById(R.id.mesEvenement_textErreur)

        barreTab = view.findViewById(R.id.barreTabMesEvens)
        boutonCreer = view.findViewById(R.id.boutonCreer)
        composeView = view.findViewById(R.id.mesEven_listeBlocsEven)
        présentateur = PrésentateurMesÉvènements(this)
        présentateur.traiterRequêtelancerCoroutine(false)
        boutonCreer.setOnClickListener { fragmentLoader.loadFragment(VueCreationEvenement()) }
        barreTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab?.position == 0) {
                    estSurMesÉvènement = false
                    boutonCreer.visibility = View.INVISIBLE
                    présentateur.traiterRequêtelancerCoroutine(estSurMesÉvènement)
                } else {
                    estSurMesÉvènement = true
                    boutonCreer.visibility = View.VISIBLE
                    présentateur.traiterRequêtelancerCoroutine(estSurMesÉvènement)
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    /**
     * Affiche la liste d'évènements
     *
     * @param listeEvens une liste d'évènement à afficher
     * @param imageUrl le lien de l'image de l'évènement
     */
    override fun afficherListeEvenements(
        lstÉvènenement: List<Événement>,
        imageUrl: (Int) -> String
    ) {
        if (!lstÉvènenement.isEmpty()) {
            emojiTriste.visibility = View.INVISIBLE
            textErreur.visibility = View.INVISIBLE
        }
        val chargement = requireView().findViewById<ProgressBar>(R.id.mesEvenement_chargement)
        chargement.visibility = View.INVISIBLE
        afficherÉvènementCliquable(lstÉvènenement, imageUrl)
    }

    /**
     * Affiche une liste évènement et permets de le sélectionner
     * @param lstÉvènenement une liste d'évènement
     * @param imageUrl le lien des images des évènements
     */
    private fun afficherÉvènementCliquable(
        lstÉvènenement: List<Événement>,
        imageUrl: (Int) -> String
    ) {
        composeView.setContent {
            MaterialTheme {
                ListeCarteÉvénements(lstÉvènenement, clickEvent = { e ->
                    présentateur.traiterRequêteAfficherÉvénement(e.idEvenement)
                }, imageUrl = { img -> imageUrl(img) })
            }
        }
    }

    /**
     * Navigue vers les détails de l'évènement ou la modification
     * de l'évènement selon le filtre actif.
     */
    override fun afficherÉvénementSelectionné() {
        fragmentLoader.loadFragment(
            if (estSurMesÉvènement) VueModifierEvenement() else VueDetailsEvenement()
        )
    }

    /**
     * Affiche un message indiquant à l'utilisateur qu'il y a
     * aucun évènement à afficher selon le filtre selectionné
     *
     * @param listeEvens une liste d'évènement à afficher
     * @param imageUrl le lien de l'image de l'évènement
     */
    override fun afficherAucunRésultatRecherche(estErreurConnexion: Boolean) {
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