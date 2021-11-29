package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.even.R
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntGetÉvènementParParticipant
import com.even.présentation.modèle.ModèleÉvénements
import com.even.présentation.présenteur.IDétailÉvenement
import com.even.présentation.présenteur.PrésentateurDétailÉvenement

class VueDetailsEvenement : Fragment(R.layout.fragment_detail_evenement), IDétailÉvenement.IVue{

    lateinit var imageEvent : ImageView
    lateinit var texteNom : TextView
    lateinit var texteLocation : TextView
    lateinit var texteDate : TextView
    lateinit var texteOrganisateur : TextView
    lateinit var texteDescription : TextView
    lateinit var texteParticipant : TextView
    lateinit var btnParticipation : Button

    lateinit var présentateur : IDétailÉvenement.IPrésentateur

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        présentateur = PrésentateurDétailÉvenement(this)
        imageEvent = view.findViewById(R.id.detailEvenement_eventImage)
        texteNom = view.findViewById(R.id.detailEvenement_nameEvent)
        texteLocation = view.findViewById(R.id.detailEvenement_location)
        texteDate = view.findViewById(R.id.detailEvenement_date)
        texteOrganisateur = view.findViewById(R.id.detailEvenement_organizer)
        texteDescription = view.findViewById(R.id.detailEvenement_description)
        texteParticipant = view.findViewById(R.id.detailEvenement_nomber)
        btnParticipation = view.findViewById(R.id.detailEvenement_participation)

        présentateur.traiterRequêteAfficherDétailÉvenement(ModèleÉvénements.événementPrésenté!!.idEvenement)

        clickListenerParticipation()
    }

    private fun clickListenerParticipation() {

        btnParticipation.setOnClickListener {

        }

    }

    override fun afficherToastErreurServeur() {
        Toast.makeText(context, R.string.serveur_error, Toast.LENGTH_LONG).show()
    }


    override fun setInfo(evenement : Événement) {
        texteNom?.text = evenement.nomEvenement
        texteLocation?.text = evenement.location
        texteDate?.text = evenement.date
        texteOrganisateur?.text = evenement.organisateur?.nomUtilisateur
        texteDescription?.text = evenement.description
    }


}