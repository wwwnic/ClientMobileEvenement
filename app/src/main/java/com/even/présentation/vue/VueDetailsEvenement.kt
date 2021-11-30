package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import coil.load
import com.even.R
import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.domaine.interacteur.IntGetÉvènementParParticipant
import com.even.présentation.modèle.ModèleÉvénements
import com.even.présentation.présenteur.IDétailÉvenement
import com.even.présentation.présenteur.PrésentateurDétailÉvenement
import com.even.ui.composants.ListeCarteCommentaires
import com.even.ui.composants.ListeCarteUtilisateurs
import com.even.ui.composants.ListeCarteÉvénements
import com.google.android.material.tabs.TabLayout

class VueDetailsEvenement : Fragment(R.layout.fragment_detail_evenement), IDétailÉvenement.IVue{

    lateinit var imageEvent : ImageView
    lateinit var imageOrganisateur : ImageView
    lateinit var texteNom : TextView
    lateinit var texteLocation : TextView
    lateinit var texteDate : TextView
    lateinit var texteOrganisateur : TextView
    lateinit var texteDescription : TextView
    lateinit var texteParticipant : TextView
    lateinit var btnParticipation : Button
    lateinit var barreTab : TabLayout
    lateinit var groupeDetails : ConstraintLayout
    lateinit var groupeParticipation : ConstraintLayout
    lateinit var listeComposables : ComposeView

    lateinit var présentateur : IDétailÉvenement.IPrésentateur

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        présentateur = PrésentateurDétailÉvenement(this)

        barreTab = view.findViewById(R.id.barreTabDetailsEven)
        imageEvent = view.findViewById(R.id.detailEvenement_eventImage)
        imageOrganisateur = view.findViewById(R.id.detailEvenement_organizerAvatar)
        texteNom = view.findViewById(R.id.detailEvenement_nameEvent)
        texteLocation = view.findViewById(R.id.detailEvenement_location)
        texteDate = view.findViewById(R.id.detailEvenement_date)
        texteOrganisateur = view.findViewById(R.id.detailEvenement_organizer)
        texteDescription = view.findViewById(R.id.detailEvenement_description)
        texteParticipant = view.findViewById(R.id.detailEvenement_nomber)
        btnParticipation = view.findViewById(R.id.detailEvenement_participation)

        groupeDetails = view.findViewById(R.id.groupeDetails)
        groupeParticipation = view.findViewById(R.id.groupeParticipations)
        listeComposables = view.findViewById(R.id.listeComposablesDetails)

        barreTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab?.position == 0) {
                    setVisibilitéVues(false)
                    présentateur.traiterRequêteAfficherDétailÉvenement(ModèleÉvénements.événementPrésenté!!.idEvenement)
                } else if (tab?.position == 1) {
                    setVisibilitéVues(true)
                    présentateur.traiterRequêteAfficherParticipants()
                } else {
                    setVisibilitéVues(true)
                    présentateur.traiterRequêteAfficherCommentaires()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        présentateur.traiterRequêteAfficherDétailÉvenement(ModèleÉvénements.événementPrésenté!!.idEvenement)

        clickListenerParticipation()
    }

    private fun setVisibilitéVues(showComposables : Boolean) {
        if (showComposables) {
            groupeDetails.visibility = View.INVISIBLE
            groupeParticipation.visibility = View.INVISIBLE
            btnParticipation.visibility = View.INVISIBLE
            imageEvent.visibility = View.INVISIBLE

            listeComposables.visibility = View.VISIBLE
        } else {
            groupeDetails.visibility = View.VISIBLE
            groupeParticipation.visibility = View.VISIBLE
            btnParticipation.visibility = View.VISIBLE
            imageEvent.visibility = View.VISIBLE

            listeComposables.visibility = View.INVISIBLE
        }
    }

    private fun clickListenerParticipation() {
        btnParticipation.setOnClickListener {
            présentateur.traiterRequêteAjouterParticipation(ModèleÉvénements.événementPrésenté!!.idEvenement)
        }

    }

    override fun afficherToastErreurServeur() {
        Toast.makeText(context, R.string.serveur_error, Toast.LENGTH_LONG).show()
    }

    override fun afficherToastAucunCommentaire() {
        Toast.makeText(context, R.string.no_comments, Toast.LENGTH_LONG).show()
    }

    override fun afficherToastParticipationAjouté() {
        Toast.makeText(context, "Votre participation a été ajouté.", Toast.LENGTH_SHORT).show()
    }


    override fun afficherToastParticipationRetiré() {
        Toast.makeText(context, "Votre participation a été retiré.", Toast.LENGTH_SHORT).show()
    }

    override fun setInfo(evenement : Événement) {
        texteNom.text = evenement.nomEvenement
        texteLocation.text = evenement.location
        texteDate.text = evenement.date
        texteOrganisateur.text = evenement.organisateur?.nomUtilisateur
        texteDescription.text = evenement.description
        imageEvent.load(evenement.urlImage)
        imageOrganisateur.load(evenement.organisateur!!.urlImage)
    }

    override fun afficherNePlusParticiper() {
        btnParticipation.text = "Je ne participe pas"
    }

    override fun afficherParticipation() {
        btnParticipation.text = "Je participe"
    }

    override fun afficherListeParticipants(
        participants: List<Utilisateur>,
        imageUrl: (Int) -> String
    ) {
        if (participants.isNotEmpty()) {
            listeComposables.setContent {
                MaterialTheme {
                    ListeCarteUtilisateurs(participants,
                        imageUrl = { i -> imageUrl(i) }
                    )
                }
            }
        }
    }

    override fun afficherListeCommentaires(commentaires: List<Commentaire>) {
        if (commentaires.isNotEmpty()) {
            listeComposables.setContent {
                MaterialTheme {
                    ListeCarteCommentaires(commentaires = commentaires)
                }
            }
        }
    }


}