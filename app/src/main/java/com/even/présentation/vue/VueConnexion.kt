package com.even.présentation.vue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.even.R
import com.even.domaine.interacteur.IConnexion

class VueConnexion : Fragment(R.layout.fragment_connexion), IConnexion.IVue {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connexion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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