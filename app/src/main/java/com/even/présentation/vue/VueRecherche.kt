package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import com.even.R
import com.even.présentation.présenteur.IRecherche
import com.even.présentation.présenteur.PrésentateurRecherche
import com.even.ui.composants.FragmentLoader
import com.even.ui.composants.SelecteurDate


class VueRecherche : Fragment(R.layout.fragment_recherche),IRecherche.IVue {

    val fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

    lateinit var présentateur : IRecherche.IPrésentateur

    lateinit var texteMotCle : EditText
    lateinit var texteMois : TextView
    lateinit var texteLocation : EditText
    lateinit var texteOrganisateur : EditText
    lateinit var boutonRechercher : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val selecteur = SelecteurDate(texteMois = texteMois)
        selecteur.show(requireActivity().supportFragmentManager,"selecteurDate")
    }

    override fun afficherMessageAucunMotCle() {
        Toast.makeText(requireContext(),"Aucun mot-clé entré.",Toast.LENGTH_SHORT).show()
    }

}