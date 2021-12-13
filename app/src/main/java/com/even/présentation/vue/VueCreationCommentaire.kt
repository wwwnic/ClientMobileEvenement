package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.even.R
import com.even.présentation.présenteur.ICreationCommentaire
import com.even.présentation.présenteur.PrésentateurCreationCommentaire
import com.even.présentation.présenteur.PrésentateurDétailÉvenement
import com.even.ui.composants.FragmentLoader

/**
 * La vue qui s'occupe de gérer la création de commentaire.
 *
 */
class VueCreationCommentaire : Fragment(R.layout.fragment_creation_commentaire),
ICreationCommentaire.IVue {

    lateinit var fragmentLoader : FragmentLoader

    lateinit var présentateur : PrésentateurCreationCommentaire

    lateinit var texteNomÉvénement : TextView
    lateinit var texteCommentaire : TextView
    lateinit var boutonAjouter : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

        présentateur = PrésentateurCreationCommentaire(this)

        texteNomÉvénement = view.findViewById(R.id.textAddCommentaireEvent)
        texteCommentaire = view.findViewById(R.id.add_commentaire_texte)
        boutonAjouter = view.findViewById(R.id.add_commentaire_bouton_creer)

        présentateur.traiterRequêteAfficherNomÉvénement()

        boutonAjouter.setOnClickListener {
            présentateur.traiterRequêteAjouterCommentaire(texteCommentaire.text.toString())
        }
    }

    /**
     * Méthode qui permet de rediriger vers la vue des détails de l'événement lorsque
     * l'utilisateur a ajouté un commentaire.
     *
     */
    override fun afficherVueDétailsÉvénement() {
        Toast.makeText(context, R.string.commentCreated, Toast.LENGTH_LONG).show()
        fragmentLoader.loadFragment(VueDetailsEvenement())
    }

    /**
     * Méthode qui permet de modifier le nom de l'événement
     *
     * @param nom Nouveau nom à remplacer
     */
    override fun afficherNomÉvénement(nom: String) {
        texteNomÉvénement.text = nom
    }

    /**
     * Affiche simplement un toast disant qu'une erreur niveau serveur est survenu.
     *
     */
    override fun afficherToastErreurServeur() {
        Toast.makeText(context, R.string.serveur_error, Toast.LENGTH_LONG).show()
    }
}