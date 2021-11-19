package com.even.présentation.vue

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getColor
import com.even.R
import androidx.navigation.fragment.findNavController
import com.even.domaine.interacteur.IntEnregistrement
import com.even.domaine.interacteur.IEnregistrement
import com.even.présentation.présenteur.PrésentateurEnregistrement


class VueEnregistrement : Fragment(R.layout.fragment_enregistrement), IEnregistrement.IVue {

    lateinit var présentateurEnregistrement: PrésentateurEnregistrement

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        présentateurEnregistrement = PrésentateurEnregistrement(this, IntEnregistrement())
        var toolbar = view.findViewById<Toolbar>(R.id.enregistrement_toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        clickListenerBtnEnregistrement(view)
    }

    private fun clickListenerBtnEnregistrement(
        view: View,
    ) {
        val boutonEnregistrement = view.findViewById<Button>(R.id.enregistrement_creer_un_compte)
        boutonEnregistrement?.setOnClickListener {
            val tvUsername = view.findViewById<TextView>(R.id.enregistrement_username)
            val tvPassword = view.findViewById<TextView>(R.id.enregistrement_password)
            val tvEmail = view.findViewById<TextView>(R.id.enregistrement_email)
            val tvPhone = view.findViewById<TextView>(R.id.enregistrement_phone)
            Log.i("input", "Les entrées sont " + validerLesEntrées().toString())
            if (validerLesEntrées()) {
                présentateurEnregistrement.traiterRequêteReclamerEnregistrement(
                    tvUsername.text.toString(),
                    tvPassword.text.toString(),
                    tvEmail.text.toString(),
                    tvPhone.text.toString()
                )
            }
        }
    }

    private fun validerLesEntrées(): Boolean {
        val vue = requireView()
        val username =
            vue.findViewById<TextView>(R.id.enregistrement_username).text.toString()
        val password =
            vue.findViewById<TextView>(R.id.enregistrement_password).text.toString()
        val email =
            vue.findViewById<TextView>(R.id.enregistrement_email).text.toString()
        val phone =
            vue.findViewById<TextView>(R.id.enregistrement_phone).text.toString()
        val estNomUsagerValide = présentateurEnregistrement.traiterRequêteValiderNomUsager(username)
        val estMotPasseValide = présentateurEnregistrement.traiterRequêteValiderMotDePasse(password)
        val estCourrielValide = présentateurEnregistrement.traiterRequêteValiderCourriel(email)
        val estTelephoneValide = présentateurEnregistrement.traiterRequêteValiderTelephone(phone)

        afficherLesEntréesInvalides(estNomUsagerValide, estMotPasseValide, estCourrielValide, estTelephoneValide)
        return estNomUsagerValide && estMotPasseValide && estCourrielValide && estTelephoneValide
    }

    private fun afficherLesEntréesInvalides(
        estNomUtilValide: Boolean,
        estMotPassValide: Boolean,
        estCourrielValide: Boolean,
        estTelphoneValide: Boolean
    ) {
        if (!estNomUtilValide) {
            afficherContourErreurNomUsager()
        }
        if (!estMotPassValide) {
            afficherContourErreurMotDePasse()
        }
        if (!estCourrielValide) {
            afficherContourErreurCourriel()
        }
        if (!estTelphoneValide) {
            afficherContourErreurTelephone()
        }
    }

    override fun naviguerVersConnexion() {
        findNavController().navigate(R.id.action_enregistrement_to_connexion)
        Log.i("Navigation", "Navigation vers la vue connexion") //TODO:log
    }

    override fun afficherToastSuccesEnregistrement() {
        Toast.makeText(context, R.string.sign_up_completed, Toast.LENGTH_SHORT).show()
    }

    override fun afficherToastErreurEnregistrement() {
        Toast.makeText(context, R.string.sign_up_incompleted, Toast.LENGTH_SHORT).show()
    }

    override fun afficherContourErreurNomUsager() {
        var tvUsername =
            requireView().findViewById<TextView>(R.id.enregistrement_username)
        var tvHintUsername =
            requireView().findViewById<TextView>(R.id.enregistrement_hint_nomUtilisateur)
        tvHintUsername.setTextColor(getColor(requireContext(), R.color.rouge))
        tvUsername.setBackgroundResource(R.drawable.login_plaintext_with_error)
    }

    override fun afficherContourErreurMotDePasse() {
        var tvPassword =
            requireView().findViewById<TextView>(R.id.enregistrement_password)
        var tvHintPassword =
            requireView().findViewById<TextView>(R.id.enregistrement_hint_password)
        tvHintPassword.setTextColor(getColor(requireContext(), R.color.rouge))
        tvPassword.setBackgroundResource(R.drawable.login_plaintext_with_error)
    }

    override fun afficherContourErreurCourriel() {
        var tvEmail =
            requireView().findViewById<TextView>(R.id.enregistrement_email)
        var tvHintEmail =
            requireView().findViewById<TextView>(R.id.enregistrement_hint_email)
        tvHintEmail.setTextColor(getColor(requireContext(), R.color.rouge))
        tvEmail.setBackgroundResource(R.drawable.login_plaintext_with_error)
    }

    override fun afficherContourErreurTelephone() {
        var tvPhone =
            requireView().findViewById<TextView>(R.id.enregistrement_phone)
        var tvHintPhone =
            requireView().findViewById<TextView>(R.id.enregistrement_hint_phone)
        tvHintPhone.setTextColor(getColor(requireContext(), R.color.rouge))
        tvPhone.setBackgroundResource(R.drawable.login_plaintext_with_error)
    }
}