package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.even.R
import com.even.présentation.présenteur.ICreationEvenement
import com.even.présentation.présenteur.IRecherche
import com.even.présentation.présenteur.PrésentateurCreation
import com.even.ui.composants.FragmentLoader
import com.even.ui.composants.SelecteurDate


class VueCreationEvenement : Fragment(R.layout.fragment_creation_evenement),ICreationEvenement.IVue {

    lateinit var fragmentLoader : FragmentLoader

    lateinit var présentateur : PrésentateurCreation

    lateinit var texteNom : EditText
    lateinit var texteDate : TextView
    lateinit var texteLocation : EditText
    lateinit var texteDescription : EditText
    lateinit var boutonCreer : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

        présentateur = PrésentateurCreation(this)

        texteNom = view.findViewById(R.id.creer_evenement_nom)
        texteDate = view.findViewById(R.id.creer_evenement_date)
        texteLocation = view.findViewById(R.id.creer_evenement_location)
        texteDescription = view.findViewById(R.id.creer_evenement_description)
        boutonCreer = view.findViewById(R.id.creation_evenement_bouton_creer)

        boutonCreer.setOnClickListener { présentateur.traiterRequêteCréerÉvénement(
            texteNom.text.toString(),
            texteDate.text.toString(),
            texteLocation.text.toString(),
            texteDescription.text.toString()
        ) }

        texteDate.setOnClickListener { afficherSelecteurDate() }
    }

    private fun afficherSelecteurDate() {
        val selecteur = SelecteurDate(texteDate = texteDate,"création")
        selecteur.show(requireActivity().supportFragmentManager,null)
    }

    override fun afficherNouvelÉvénement() {
        fragmentLoader.loadFragment(VueDetailsEvenement(),"new")
    }

    override fun afficherErreurConnexion() {
        Toast.makeText(requireContext(),"Erreur de connexion.", Toast.LENGTH_SHORT).show()
    }

}