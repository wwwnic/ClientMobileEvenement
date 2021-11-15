package com.even.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.even.domaine.entité.Événement
import java.util.*
import com.even.R
import androidx.compose.ui.platform.ComposeView
import com.even.présentation.modèle.ModèleÉvénements
import com.even.ui.composants.ListeCarteÉvénements
import kotlin.collections.ArrayList


class liste_evenement(val modèle : ModèleÉvénements) : Fragment(R.layout.fragment_liste_evenement) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listeEvens : List<Événement> = modèle.Événements
        val composeView = view.findViewById<ComposeView>(R.id.listeBlocsEven)

        composeView.setContent {
            ListeCarteÉvénements(événements = listeEvens, clickEvent = {e -> loadFragment(details_evenement(e)) })
        }
    }

    // https://stackoverflow.com/questions/44424985/switch-between-fragments-in-bottomnavigationview
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}