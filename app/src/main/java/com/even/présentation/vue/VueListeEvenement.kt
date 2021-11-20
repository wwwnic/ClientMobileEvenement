package com.even.présentation.vue

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import com.even.domaine.entité.Événement
import com.even.R
import androidx.compose.ui.platform.ComposeView
import com.even.présentation.présenteur.IListeEvenements
import com.even.présentation.présenteur.PrésentateurListeÉvénements
import com.even.ui.composants.ListeCarteÉvénements
import org.w3c.dom.Text


class VueListeEvenement() : Fragment(R.layout.fragment_liste_evenement), IListeEvenements.IVue {

    lateinit var présentateur : IListeEvenements.IPrésentateur

    lateinit var composeView: ComposeView
    lateinit var chargement : ProgressBar
    lateinit var textErreur: TextView
    lateinit var imageErreur : ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        présentateur = PrésentateurListeÉvénements(this)
        présentateur.traiterRequêteAfficherListeRecents()

        composeView = view.findViewById(R.id.listeBlocsEven)
        chargement = view.findViewById(R.id.chargementListe)
        textErreur = view.findViewById(R.id.textErreur)
        imageErreur = view.findViewById(R.id.imageErreur)
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
        if (!listeEvens.isEmpty()) {
            chargement.visibility = View.INVISIBLE
            composeView.setContent {
                MaterialTheme {
                    ListeCarteÉvénements(événements = listeEvens, clickEvent = {e -> loadFragment(
                        VueDetailsEvenement(e)
                    ) })
                }
            }
        }
    }

    override fun afficherErreurConnexion() {
        chargement.visibility = View.INVISIBLE
        imageErreur.visibility = View.VISIBLE
        textErreur.visibility = View.VISIBLE
    }
}