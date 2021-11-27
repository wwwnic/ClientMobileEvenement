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
import com.even.présentation.présenteur.IDétailÉvenement
import com.even.présentation.présenteur.PrésentateurDétailÉvenement
import retrofit2.Response

class VueDetailsEvenement : Fragment(R.layout.fragment_detail_evenement), IDétailÉvenement.IVue{

    lateinit var imageEvent : ImageView
    lateinit var texteNom : TextView
    lateinit var texteLocation : TextView
    lateinit var texteDate : TextView
    lateinit var texteOrganisateur : TextView
    lateinit var texteDescription : TextView
    lateinit var texteParticipant : TextView
    lateinit var btnParticipation : Button
    lateinit var evenement : Response<Événement>

    lateinit var présentateur : IDétailÉvenement.IPrésentateur

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        présentateur = PrésentateurDétailÉvenement(this)
        evenement = présentateur.traiterRequêteAfficherDétailÉvenement(this.tag!!.toInt())!!

        imageEvent = view?.findViewById(R.id.detailEvenement_eventImage)
        texteNom = view?.findViewById(R.id.detailEvenement_nameEvent)
        texteLocation = view?.findViewById(R.id.detailEvenement_location)
        texteDate = view?.findViewById(R.id.detailEvenement_date)
        texteOrganisateur = view?.findViewById(R.id.detailEvenement_organizer)
        texteDescription = view?.findViewById(R.id.detailEvenement_description)
        texteParticipant = view?.findViewById(R.id.detailEvenement_nomber)
        btnParticipation = view?.findViewById(R.id.detailEvenement_participation)

        clickListenerParticipation()
    }

    private fun clickListenerParticipation() {

        btnParticipation.setOnClickListener {

        }

    }

    override fun afficherToastErreurServeur() {
        Toast.makeText(context, R.string.serveur_error, Toast.LENGTH_LONG).show()
    }

    override fun setInfo() {
        texteNom?.text = evenement.body()!!.nomEvenement
        texteLocation?.text = evenement.body()!!.location
        texteDate?.text = evenement.body()!!.date
        texteOrganisateur?.text = evenement.body()!!.organisateur!!.nomUtilisateur
        texteDescription?.text = evenement.body()!!.description
    }


}