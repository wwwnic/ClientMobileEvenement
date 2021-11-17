package com.even.présentation.vue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.even.R
import androidx.navigation.Navigation
import com.even.domaine.entité.Utilisateur
import com.even.retrofit.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class enregistrement : Fragment(R.layout.fragment_enregistrement) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var toolbar = view.findViewById<Toolbar>(R.id.enregistrement_toolbar)
        val boutonEnregistrement = view.findViewById<Button>(R.id.enregistrement_creer_un_compte)
        var navController = Navigation.findNavController(view)


        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        boutonEnregistrement?.setOnClickListener {
            var tvUsername = view.findViewById<TextView>(R.id.enregistrement_username)
            var tvPassword = view.findViewById<TextView>(R.id.enregistrement_password)
            var tvEmail = view.findViewById<TextView>(R.id.enregistrement_email)
            var tvPhone = view.findViewById<TextView>(R.id.enregistrement_phone)

            CoroutineScope(Dispatchers.IO).launch {
                var reponseRequete = ApiClient.apiService.creerUtilisateur(
                    Utilisateur(
                        0,
                        tvUsername.text.toString(),
                        tvPassword.text.toString(),
                        tvEmail.text.toString(),
                        tvPhone.text.toString()
                    )
                )
                Log.e("POST", reponseRequete.toString()) //TODO: permet de voir la réponse du serveur dans les logs
            }

            Toast.makeText(context, R.string.sign_up_completed, Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_enregistrement_to_connexion)
        }
    }


}