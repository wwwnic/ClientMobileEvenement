package com.even.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.even.R
import androidx.navigation.Navigation


class enregistrement : Fragment(R.layout.fragment_enregistrement) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var toolbar = view.findViewById<Toolbar>(R.id.enregistrement_toolbar)
        val boutonEnregistrement = view.findViewById<Button>(R.id.enregistrement_creer_un_compte)
        var navController = Navigation.findNavController(view)

        boutonEnregistrement?.setOnClickListener {
            navController.navigate(R.id.action_enregistrement_to_connexion)
            Toast.makeText(context, R.string.sign_up_completed, Toast.LENGTH_SHORT).show()
        }
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }
}