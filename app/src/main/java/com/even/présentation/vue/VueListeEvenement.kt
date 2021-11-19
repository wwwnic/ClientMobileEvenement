package com.even.présentation.vue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.compose.material.MaterialTheme
import com.even.domaine.entité.Événement
import com.even.R
import androidx.compose.ui.platform.ComposeView
import com.even.présentation.présenteur.IListeEvenements
import com.even.présentation.présenteur.PrésentateurListeÉvénements
import com.even.ui.composants.ListeCarteÉvénements


class VueListeEvenement() : Fragment(R.layout.fragment_liste_evenement), IListeEvenements.IVue {

    lateinit var présentateur : IListeEvenements.IPrésentateur

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        présentateur = PrésentateurListeÉvénements(this)
        présentateur.traiterRequêteAfficherListeRecents()
    }

    // https://stackoverflow.com/questions/44424985/switch-between-fragments-in-bottomnavigationview
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun afficherListeEvenementsRecents(listeEvens : List<Événement>) {
        val composeView = view?.findViewById<ComposeView>(R.id.listeBlocsEven)

        composeView?.setContent {
            MaterialTheme {
                ListeCarteÉvénements(événements = listeEvens, clickEvent = {e -> loadFragment(
                    VueDetailsEvenement(e)
                ) })
            }
        }
    }
}