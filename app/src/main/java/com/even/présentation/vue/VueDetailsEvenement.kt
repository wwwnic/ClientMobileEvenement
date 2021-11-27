package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.even.R
import com.even.présentation.modèle.ModèleÉvénements

class VueDetailsEvenement() : Fragment(R.layout.fragment_detail_evenement) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var imageEven = view?.findViewById<ImageView>(R.id.detailEvenement_eventImage)
        var texteNom = view?.findViewById<TextView>(R.id.detailEvenement_nameEvent)
        var texteLocation = view?.findViewById<TextView>(R.id.detailEvenement_location)
        var texteDate = view?.findViewById<TextView>(R.id.detailEvenement_date)
        var texteOrg = view?.findViewById<TextView>(R.id.detailEvenement_organizer)
        var texteDesc = view?.findViewById<TextView>(R.id.detailEvenement_description)

        imageEven.setImageResource(R.drawable.imageevenementbidon)
        var evenement = ModèleÉvénements.événementPrésenté!! //TODO: passer par le présentateur
        texteNom?.text = evenement.nomEvenement
        texteLocation?.text = evenement.location
        texteDate?.text = evenement.date
        texteOrg?.text = evenement.organisateur?.nomUtilisateur
        texteDesc?.text = evenement.description
    }
}