package com.even.présentation.vue

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import com.even.R
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.even.domaine.interacteur.IntEnregistrement
import com.even.domaine.interacteur.IEnregistrement
import com.even.présentation.présenteur.PrésentateurEnregistrement


class VueEnregistrement : Fragment(R.layout.fragment_enregistrement), IEnregistrement.IVue {

    lateinit var présentateurEnregistrement: PrésentateurEnregistrement

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        présentateurEnregistrement = PrésentateurEnregistrement(this,IntEnregistrement())
        var toolbar = view.findViewById<Toolbar>(R.id.enregistrement_toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        clickListenerBtnEnregistrement(view)
    }

    private fun clickListenerBtnEnregistrement(
        view: View,
    ) {
        val boutonEnregistrement = view.findViewById<Button>(R.id.enregistrement_creer_un_compte)
        boutonEnregistrement?.setOnClickListener {
            var tvUsername = view.findViewById<TextView>(R.id.enregistrement_username)
            var tvPassword = view.findViewById<TextView>(R.id.enregistrement_password)
            var tvEmail = view.findViewById<TextView>(R.id.enregistrement_email)
            var tvPhone = view.findViewById<TextView>(R.id.enregistrement_phone)

            présentateurEnregistrement.traiterRequêteReclamerEnregistrement(
                tvUsername.text.toString(),
                tvPassword.text.toString(),
                tvEmail.text.toString(),
                tvPhone.text.toString()
            )
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


}