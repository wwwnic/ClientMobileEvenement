package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.even.R
import com.even.présentation.présenteur.IRecherche
import com.even.présentation.présenteur.PrésentateurRecherche
import com.even.ui.composants.FragmentLoader
import com.even.ui.composants.SelecteurDate


class VueRecherche : Fragment(R.layout.fragment_recherche),IRecherche.IVue {

    lateinit var fragmentLoader : FragmentLoader

    lateinit var présentateur : IRecherche.IPrésentateur

    lateinit var texteMotCle : EditText
    lateinit var texteMois : TextView
    lateinit var texteLocation : EditText
    lateinit var texteOrganisateur : EditText
    lateinit var boutonRechercher : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

        présentateur = PrésentateurRecherche(this)

        texteMotCle = view.findViewById(R.id.rechercher_par_mot_cle)
        texteMois = view.findViewById(R.id.rechercher_par_mois)
        texteLocation = view.findViewById(R.id.rechercher_par_location)
        texteOrganisateur = view.findViewById(R.id.rechercher_par_organisateur)

        boutonRechercher = view.findViewById(R.id.rechercher_bouton_rechercher)
        boutonRechercher.setOnClickListener { présentateur.traiterRequêteRechercheÉvénement(
            texteMotCle.text.toString(),
            texteMois.text.toString(),
            texteLocation.text.toString(),
            texteOrganisateur.text.toString()
        ) }

        texteMois.setOnClickListener { afficherSelecteurDate() }
    }

    override fun afficherRésultatsRecherche(tag : String) {
        fragmentLoader.loadFragment(VueListeEvenement(),tag)
    }

    private fun afficherSelecteurDate() {
        val selecteur = SelecteurDate(texteDate = texteMois,"recherche")
        selecteur.show(requireActivity().supportFragmentManager,null)
    }

    override fun afficherMessageAucunMotCle() {
        Toast.makeText(requireContext(),"Aucun mot-clé entré.",Toast.LENGTH_SHORT).show()
    }

}