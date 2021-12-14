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
import com.even.présentation.présenteur.IModificationÉvénement
import com.even.présentation.présenteur.PrésentateurModification
import com.even.ui.composants.FragmentLoader
import com.even.ui.composants.SelecteurDate

/**
 * La vue qui permet de faire les modifications à un événement existant.
 *
 */
class VueModifierEvenement : Fragment(R.layout.fragment_modifier_evenement),
IModificationÉvénement.IVue {
    lateinit var fragmentLoader: FragmentLoader

    lateinit var présentateur: PrésentateurModification

    lateinit var texteNom: EditText
    lateinit var texteDate: TextView
    lateinit var texteLocation: EditText
    lateinit var texteDescription: EditText
    lateinit var boutonModifier: Button
    lateinit var boutonAnnuler : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

        présentateur = PrésentateurModification(this)

        texteNom = view.findViewById(R.id.modifier_evenement_nom)
        texteDate = view.findViewById(R.id.modifier_evenement_date)
        texteLocation = view.findViewById(R.id.modifier_evenement_location)
        texteDescription = view.findViewById(R.id.modifier_evenement_description)
        boutonModifier = view.findViewById(R.id.modifier_evenement_boutonModifier)
        boutonAnnuler = view.findViewById(R.id.modifier_evenement_boutonAnnuler)

        présentateur.traiterRequêteRemplirChamps()

        boutonModifier.setOnClickListener {
            présentateur.traiterRequêteModifierÉvénement(
                texteNom.text.toString(),
                texteDate.text.toString(),
                texteLocation.text.toString(),
                texteDescription.text.toString()
            )
        }

        boutonAnnuler.setOnClickListener {
            présentateur.traiterRequêteAnnulerÉvénement()
        }

        texteDate.setOnClickListener { afficherSelecteurDate() }
    }

    /**
     * Retourne la page de l'événement si il a été modifié ou la page Mes événements si il a été annulé
     *
     * @param modification Vrai si c'est une modification ou faux si c'est une annulation
     */
    override fun afficherÉvénementModifiéOuRetourMenu(modification: Boolean) {
        if (modification) {
            Toast.makeText(requireContext(), "Événement modifié.", Toast.LENGTH_SHORT).show()
            fragmentLoader.loadFragment(VueDetailsEvenement())
        } else {
            Toast.makeText(requireContext(), "Événement annulé.", Toast.LENGTH_SHORT).show()
            fragmentLoader.loadFragment(VueMesEvenements())
        }
    }

    /**
     * Méthode qui permet de remplir les champs lorsque la vue est appelé à partir des informations
     * de l'événement en paramètre
     *
     * @param événement L'événement sélectionné.
     */
    override fun remplirChamps(événement : Événement) {
        texteNom.setText(événement.nomEvenement)
        texteDate.text = événement.date
        texteLocation.setText(événement.location)
        texteDescription.setText(événement.description)
    }

    /**
     * Permet d'afficher le sélecteur de date lorsque l'utilisateur veut modifier la date
     *  ou l'heure de l'événement.
     *
     */
    private fun afficherSelecteurDate() {
        val selecteur = SelecteurDate(texteDate = texteDate, "modification")
        selecteur.show(requireActivity().supportFragmentManager, null)
    }

    /**
     * Affiche simplement un toast lorsqu'une erreur survient.
     *
     */
    override fun afficherErreurConnexion() {
        Toast.makeText(requireContext(), R.string.serveur_error, Toast.LENGTH_SHORT).show()
    }
}