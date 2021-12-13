package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.even.R
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleÉvénements
import com.even.présentation.présenteur.IListeEvenements
import com.even.présentation.présenteur.PrésentateurListeÉvénements
import com.even.ui.composants.FragmentLoader
import com.even.ui.composants.ListeCarteÉvénements


class VueListeEvenement() : Fragment(R.layout.fragment_liste_evenement), IListeEvenements.IVue {

    lateinit var fragmentLoader: FragmentLoader

    lateinit var présentateur: IListeEvenements.IPrésentateur

    lateinit var composeView: ComposeView
    lateinit var chargement: ProgressBar
    lateinit var textErreur: TextView
    lateinit var imageErreur: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)
        présentateur = PrésentateurListeÉvénements(this, ModèleÉvénements())
        if (this.tag.isNullOrEmpty()) {
            présentateur.traiterRequêteAfficherListeRecents()
        } else {
            présentateur.traiterRequêteAfficherListeRecherche(this.tag!!)
        }

        composeView = view.findViewById(R.id.listeBlocsEven)
        chargement = view.findViewById(R.id.chargementListe)
        textErreur = view.findViewById(R.id.textErreur)
        imageErreur = view.findViewById(R.id.imageErreur)
    }

    override fun afficherListeEvenements(listeEvens: List<Événement>, imageUrl: (Int) -> String) {
        if (!listeEvens.isEmpty()) {
            chargement.visibility = View.INVISIBLE
            composeView.setContent {
                MaterialTheme {
                    ListeCarteÉvénements(événements = listeEvens,
                        clickEvent = {e -> présentateur.traiterRequêteAfficherDétailsÉvénement(e.idEvenement)},
                        imageUrl = { i -> imageUrl(i) }
                    )
                }
            }
        }
    }

    override fun afficherDétailsÉvénement() {
        fragmentLoader.loadFragment(VueDetailsEvenement())
    }

    override fun afficherErreurConnexion() {
        chargement.visibility = View.INVISIBLE
        imageErreur.visibility = View.VISIBLE
        textErreur.visibility = View.VISIBLE
    }

    override fun afficherAucunRésultatRecherche() {
        chargement.visibility = View.INVISIBLE
        imageErreur.visibility = View.VISIBLE
        textErreur.text = "Aucun résultat..."
        textErreur.visibility = View.VISIBLE
    }
}