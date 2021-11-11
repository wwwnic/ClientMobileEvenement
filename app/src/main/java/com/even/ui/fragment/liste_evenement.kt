package com.even.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.even.domaine.entité.Événement
import com.google.android.material.composethemeadapter3.Mdc3Theme
import java.util.*
import com.even.R
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.even.MainActivity
import com.even.ui.composants.CarteÉvénement
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_liste_evenement,container,false).apply {
            val listeEvens : ArrayList<Événement> = ArrayList<Événement>()
            listeEvens.add(évén)
            listeEvens.add(évén)
            listeEvens.add(évén)

            findViewById<ComposeView>(R.id.listeBlocsEven).setContent {
                ListeCarteÉvénements(événements = listeEvens)
            }
        }
    }
}