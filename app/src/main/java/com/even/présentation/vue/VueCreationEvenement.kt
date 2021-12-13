package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.even.R
import com.even.domaine.entité.Événement
import com.even.présentation.présenteur.ICreationEvenement
import com.even.présentation.présenteur.PrésentateurCreationÉvénement
import com.even.ui.composants.FragmentLoader
import com.even.ui.composants.SelecteurDate

/**
 * La vue qui s'occupe de gérer la création d'événement
 *
 */
class VueCreationEvenement : Fragment(R.layout.fragment_creation_evenement),
    ICreationEvenement.IVue {

    lateinit var fragmentLoader: FragmentLoader

    lateinit var présentateur: PrésentateurCreationÉvénement

    lateinit var texteNom: EditText
    lateinit var texteDate: TextView
    lateinit var texteLocation: EditText
    lateinit var texteDescription: EditText
    lateinit var boutonCreer: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

        présentateur = PrésentateurCreationÉvénement(this)

        texteNom = view.findViewById(R.id.creer_evenement_nom)
        texteDate = view.findViewById(R.id.creer_evenement_date)
        texteLocation = view.findViewById(R.id.creer_evenement_location)
        texteDescription = view.findViewById(R.id.creer_evenement_description)
        boutonCreer = view.findViewById(R.id.creation_evenement_bouton_creer)

        boutonCreer.setOnClickListener {
            présentateur.traiterRequêteCréerÉvénement(
                texteNom.text.toString(),
                texteDate.text.toString(),
                texteLocation.text.toString(),
                texteDescription.text.toString()
            )
        }

        texteDate.setOnClickListener { afficherSelecteurDate() }
    }

    /**
     * Méthode qui permet d'afficher le sélecteur de date lorsque l'utilisateur doit
     * sélectionner la date et l'heure de l'événement.
     *
     */
    private fun afficherSelecteurDate() {
        val selecteur = SelecteurDate(texteDate = texteDate, "création")
        selecteur.show(requireActivity().supportFragmentManager, null)
    }

    /**
     * Méthode qui permet de rediriger vers le nouvel événement une fois que celui-ci est créé
     *
     * @param evenement L'événement à afficher
     */
    override fun afficherNouvelÉvénement(evenement : Événement) {
        fragmentLoader.loadFragment(VueDetailsEvenement())
    }

    /**
     * Affiche simplement un toast qui indique à l'utilisateur qu'une erreur est survenu.
     *
     */
    override fun afficherErreurConnexion() {
        Toast.makeText(requireContext(), R.string.serveur_error, Toast.LENGTH_SHORT).show()
    }
}