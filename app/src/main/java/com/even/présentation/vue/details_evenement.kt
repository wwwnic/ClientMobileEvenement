package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.even.R
import com.even.domaine.entité.Événement

class details_evenement(var evenement : Événement) : Fragment(R.layout.fragment_detail_evenement) {

    lateinit var imageEvent : ImageView
    lateinit var texteNom : TextView
    lateinit var texteLocation : TextView
    lateinit var texteDate : TextView
    lateinit var texteOrganisateur : TextView
    lateinit var texteDescription : TextView
    lateinit var texteParticipant : TextView
    lateinit var btnParticipation : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageEvent = view?.findViewById<ImageView>(R.id.detailEvenement_eventImage)
        texteNom = view?.findViewById<TextView>(R.id.detailEvenement_nameEvent)
        texteLocation = view?.findViewById<TextView>(R.id.detailEvenement_location)
        texteDate = view?.findViewById<TextView>(R.id.detailEvenement_date)
        texteOrganisateur = view?.findViewById<TextView>(R.id.detailEvenement_organizer)
        texteDescription = view?.findViewById<TextView>(R.id.detailEvenement_description)
        texteParticipant = view?.findViewById<TextView>(R.id.detailEvenement_nomber)
        btnParticipation = view?.findViewById<Button>(R.id.detailEvenement_participation)

        imageEvent.setImageResource(R.drawable.wowimg)
        texteNom?.text = evenement.nom
        texteLocation?.text = evenement.location
        texteDate?.text = evenement.date.toString()
        texteOrganisateur?.text = evenement.organisateur?.nomUtilisateur
        texteDescription?.text = evenement.description
        texteParticipant?.text = evenement.nbParticipant.toString()

        clickListenerParticipation(view)
    }

    private fun clickListenerParticipation(view: View) {

        btnParticipation.setOnClickListener {

        }

    }
}