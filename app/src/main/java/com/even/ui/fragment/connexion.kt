package com.even.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.even.R

class connexion : Fragment(R.layout.fragment_connexion) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connexion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bouton = view.findViewById<Button>(R.id.connexion_boutonConnexion)
        var navController = Navigation.findNavController(view)
        bouton?.setOnClickListener { navController.navigate(R.id.action_connexion_to_principal) }
    }
}