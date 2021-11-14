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
import com.even.ui.composants.ListeCarteÉvénements
import kotlin.collections.ArrayList


class liste_evenement : Fragment(R.layout.fragment_liste_evenement) {

    private var évén : Événement = Événement(
        "Party chez Bob",
        "Maison de Bob",
        Calendar.getInstance().time,
        "bob", R.drawable.wowimg,
        "gros party chez Bob let's gooooooo!"
    )

    private var évén2 : Événement = Événement(
        "Autre Party chez Bob",
        "bbbbbb",
        Calendar.getInstance().time,
        "bob encore", R.drawable.wowimg,
        "hey salut"
    )

    private var évén3 : Événement = Événement(
        "Réunion des bricoleurs",
        "Bricoville",
        Calendar.getInstance().time,
        "Les bricoleurs", R.drawable.wowimg,
        "ayoyeeeeeeeee"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liste_evenement,container,false).apply {
            val listeEvens : ArrayList<Événement> = ArrayList<Événement>()
            listeEvens.add(évén)
            listeEvens.add(évén2)
            listeEvens.add(évén3)
            val composeView = findViewById<ComposeView>(R.id.listeBlocsEven)

            composeView.setContent {
                ListeCarteÉvénements(événements = listeEvens, clickEvent = {e -> loadFragment(details_evenement(e)) })
            }

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