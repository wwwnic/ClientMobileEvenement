package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.even.R
import com.even.domaine.entité.Événement
import com.even.présentation.présenteur.IListeEvenements
import com.even.présentation.présenteur.IMesÉvènements
import com.even.présentation.présenteur.PrésentateurMesÉvènements
import com.even.ui.composants.FragmentLoader
import com.google.android.material.tabs.TabLayout

class VueMesEvenements() : Fragment(R.layout.fragment_mes_evenements), IMesÉvènements.IVue {

    lateinit var fragmentLoader: FragmentLoader
    lateinit var présentateur: IMesÉvènements.IPrésentateur


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

        val barreTab = view.findViewById<TabLayout>(R.id.barreTabMesEvens)
        //var listeEvens : List<Événement> = setListeEvens(0)
        var listeEvens: List<Événement> = ArrayList<Événement>()
        val composeView = view.findViewById<ComposeView>(R.id.listeBlocsEven)
        val boutonCreer = view.findViewById<Button>(R.id.boutonCreer)
        présentateur = PrésentateurMesÉvènements(this)

        boutonCreer.setOnClickListener { fragmentLoader.loadFragment(VueCreationEvenement()) }

        barreTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //listeEvens = setListeEvens(tab!!.position)
                if (tab!!.position == 0) {
                    /*composeView.setContent {
                        MaterialTheme {
                            ListeCarteÉvénements(événements = listeEvens, clickEvent = {e -> loadFragment(
                                VueDetailsEvenement(e)
                            ) })
                        }
                    }*/
                    boutonCreer.visibility = View.INVISIBLE
                } else {
                    /*composeView.setContent {
                        MaterialTheme {
                            ListeCarteÉvénements(événements = listeEvens, clickEvent = {e -> loadFragment(
                                VueModifierEvenement()
                            ) })
                        }
                    }*/
                    boutonCreer.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    /*private fun setListeEvens(tabSélectionée : Int) : List<Événement> {
        var liste : List<Événement>
        if (tabSélectionée == 0) {
            //liste = modèle.getÉvénementsParPrésence(ModèleUtilisateurs(SourceDeDonnéesBidon()).Utilisateurs.get(0))
        } else {
            //liste = modèle.getÉvénementsParCréateur(ModèleUtilisateurs(SourceDeDonnéesBidon()).Utilisateurs.get(0))
        }
        return liste
    }*/

}