package com.even.présentation.vue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getColor
import com.even.R
import androidx.navigation.fragment.findNavController
import com.even.domaine.entité.ApiClient
import com.even.domaine.interacteur.IEnregistrement
import com.even.présentation.modèle.ModèleEnregistrement
import com.even.présentation.présenteur.PrésentateurEnregistrement


class VueEnregistrement : Fragment(R.layout.fragment_enregistrement), IEnregistrement.IVue {

    lateinit var présentateurEnregistrement: PrésentateurEnregistrement

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        présentateurEnregistrement =
            PrésentateurEnregistrement(this, ModèleEnregistrement(ApiClient.apiService))

        val toolbar = view.findViewById<Toolbar>(R.id.enregistrement_toolbar)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        clickListenerBtnEnregistrement(view)
    }

    private fun clickListenerBtnEnregistrement(view: View) {
        val boutonEnregistrement = view.findViewById<Button>(R.id.enregistrement_creer_un_compte)
        boutonEnregistrement?.setOnClickListener {
            val txtNomUsager = view.findViewById<TextView>(R.id.enregistrement_nomUsager).text
            val txtMotDePasse = view.findViewById<TextView>(R.id.enregistrement_motDePasse).text
            val txtCourriel = view.findViewById<TextView>(R.id.enregistrement_courriel).text
            val txtTelephone = view.findViewById<TextView>(R.id.enregistrement_telephone).text
            if (validerLesEntrées(txtNomUsager, txtMotDePasse, txtCourriel, txtTelephone)) {
                présentateurEnregistrement.traiterRequêteReclamerEnregistrement(
                    txtNomUsager.toString(),
                    txtMotDePasse.toString(),
                    txtCourriel.toString(),
                    txtTelephone.toString()
                )
            } else {
                afficherToastErreurEnregistrement()
            }
        }
    }

    private fun validerLesEntrées(
        txtNomUsager: CharSequence,
        txtMotDePasse: CharSequence,
        txtCourriel: CharSequence,
        txtTelephone: CharSequence
    ): Boolean {
        val estNomUsagerValide =
            présentateurEnregistrement.traiterRequêteValiderNomUsager(txtNomUsager)
        val estMotPasseValide =
            présentateurEnregistrement.traiterRequêteValiderMotDePasse(txtMotDePasse)
        val estCourrielValide =
            présentateurEnregistrement.traiterRequêteValiderCourriel(txtCourriel)
        val estTelephoneValide =
            présentateurEnregistrement.traiterRequêteValiderTelephone(txtTelephone)

        return estCourrielValide && estTelephoneValide && estNomUsagerValide && estMotPasseValide
    }

    override fun naviguerVersConnexion() {
        findNavController().navigate(R.id.action_enregistrement_to_connexion)
        Log.i("Navigation", "Navigation vers la vue connexion") //TODO:log
    }

    override fun afficherToastSuccesEnregistrement() {
        Toast.makeText(context, R.string.sign_up_completed, Toast.LENGTH_SHORT).show()
    }

    override fun afficherToastErreurEnregistrement() {
        Toast.makeText(context, R.string.sign_up_incompleted, Toast.LENGTH_LONG).show()
    }


    override fun afficherErreurNomUsager(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.enregistrement_nomUsager, R.id.enregistrement_hint_nomUsager
        )
    }

    override fun afficherErreurMotDePasse(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.enregistrement_motDePasse, R.id.enregistrement_hint_motDePasse
        )
    }

    override fun afficherErreurCourriel(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.enregistrement_courriel, R.id.enregistrement_hint_courriel
        )
    }

    override fun afficherErreurTéléphone(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.enregistrement_telephone, R.id.enregistrement_hint_telephone
        )
    }

    private fun changerCouleurValidation(
        afficherEnRouge: Boolean,
        editTextId: Int,
        textViewId: Int
    ) {
        val vue = requireView()
        val editText = vue.findViewById<EditText>(editTextId)
        val textView = vue.findViewById<TextView>(textViewId)
        val idArrièrePlan =
            if (afficherEnRouge) R.drawable.login_plaintext_in_red else R.drawable.login_plaintext
        val idCouleur = if (afficherEnRouge) R.color.rouge else R.color.figmaMauve
        editText.setBackgroundResource(idArrièrePlan)
        textView.setTextColor(getColor(requireContext(), idCouleur))
    }
}