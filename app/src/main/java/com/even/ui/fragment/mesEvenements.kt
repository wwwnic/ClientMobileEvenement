package com.even.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.ui.platform.ComposeView
import com.even.R
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.sourceDeDonnées.SourceDeDonnéesBidon
import com.even.ui.composants.ListeCarteÉvénements
import com.google.android.material.tabs.TabLayout


class mesEvenements(val modèle : ModèleÉvénements) : Fragment(R.layout.fragment_mes_evenements) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val barreTab = view.findViewById<TabLayout>(R.id.barreTabMesEvens)
        var listeEvens : List<Événement> = setListeEvens(0)
        val composeView = view.findViewById<ComposeView>(R.id.listeBlocsEven)
        val boutonCreer = view.findViewById<Button>(R.id.boutonCreer)

        composeView.setContent {
            ListeCarteÉvénements(événements = listeEvens, clickEvent = {e -> loadFragment(details_evenement(e)) })
        }

        boutonCreer.setOnClickListener { loadFragment(creation_evenement()) }

        barreTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                listeEvens = setListeEvens(tab!!.position)
                if (tab!!.position == 0) {
                    composeView.setContent {
                        ListeCarteÉvénements(
                            événements = listeEvens,
                            clickEvent = { e -> loadFragment(details_evenement(e)) })
                    }
                    boutonCreer.visibility = View.INVISIBLE
                } else {
                    composeView.setContent {
                        ListeCarteÉvénements(
                            événements = listeEvens,
                            clickEvent = { e -> loadFragment(modifier_evenement()) })
                    }
                    boutonCreer.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setListeEvens(tabSélectionée : Int) : List<Événement> {
        var liste : List<Événement>
        if (tabSélectionée == 0) {
            liste = modèle.getÉvénementsParPrésence(ModèleUtilisateurs(SourceDeDonnéesBidon()).Utilisateurs.get(0))
        } else {
            liste = modèle.getÉvénementsParCréateur(ModèleUtilisateurs(SourceDeDonnéesBidon()).Utilisateurs.get(0))
        }
        return liste
    }

    // https://stackoverflow.com/questions/44424985/switch-between-fragments-in-bottomnavigationview
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}