package com.even.présentation.vue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.Navigation
import com.even.R
import com.even.domaine.entité.ApiClient
import com.even.domaine.interacteur.IConnexion
import com.even.présentation.modèle.ModèleConnexion
import com.even.présentation.présenteur.PrésentateurConnexion

class VueConnexion : Fragment(R.layout.fragment_connexion), IConnexion.IVue {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val présentateurConnexion =
            PrésentateurConnexion(this, ModèleConnexion(ApiClient.apiService))

        var boutonConnexion = view.findViewById<Button>(R.id.connexion_boutonConnexion)
        var boutonEnregistrement = view.findViewById<Button>(R.id.connexion_boutonEnregistrement)

        var navController = Navigation.findNavController(view)

        boutonConnexion?.setOnClickListener {
            //présentateurConnexion.traiterRequêteDemanderProfilPourConnexion("urnm", "pw")
            navController.navigate(R.id.action_connexion_to_principal)
        }
        boutonEnregistrement?.setOnClickListener { navController.navigate(R.id.action_connexion_to_enregistrement) }
    }
}