package com.even.présentation.vue


import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.even.R
import com.even.domaine.entité.Commentaire
import com.even.domaine.entité.Utilisateur
import com.even.domaine.entité.Événement
import com.even.présentation.modèle.ModèleÉvénements
import com.even.présentation.présenteur.IDétailÉvenement
import com.even.présentation.présenteur.PrésentateurDétailÉvenement
import com.even.ui.composants.FragmentLoader
import com.even.ui.composants.ListeCarteCommentaires
import com.even.ui.composants.ListeCarteUtilisateurs
import com.google.android.material.tabs.TabLayout
import java.util.*


class VueDetailsEvenement : Fragment(R.layout.fragment_detail_evenement), IDétailÉvenement.IVue {

    lateinit var fragmentLoader: FragmentLoader

    lateinit var imageEvent: ImageView
    lateinit var imageOrganisateur: ImageView
    lateinit var texteNom: TextView
    lateinit var texteLocation: TextView
    lateinit var texteDate: TextView
    lateinit var texteOrganisateur: TextView
    lateinit var texteDescription: TextView
    lateinit var texteParticipant: TextView
    lateinit var btnParticipation: Button
    lateinit var btnAddCommentaire: Button
    lateinit var barreTab: TabLayout
    lateinit var groupeDetails: ConstraintLayout
    lateinit var groupeParticipation: ConstraintLayout
    lateinit var listeComposables: ComposeView

    lateinit var chargement: View

    lateinit var présentateur: IDétailÉvenement.IPrésentateur

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

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
        btnAddCommentaire = view.findViewById(R.id.detailEvenement_btnAddCommentaire)
        chargement = view.findViewById(R.id.detailEvenement_progressBar)

        groupeDetails = view.findViewById(R.id.groupeDetails)
        groupeParticipation = view.findViewById(R.id.groupeParticipations)
        listeComposables = view.findViewById(R.id.listeComposablesDetails)

        barreTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    setVisibilitéVues("details")
                    présentateur.traiterRequêteAfficherDétailÉvenement(ModèleÉvénements.événementPrésenté!!.idEvenement)
                } else if (tab.position == 1) {
                    setVisibilitéVues("participants")
                    présentateur.traiterRequêteAfficherParticipants()
                } else {
                    setVisibilitéVues("commentaires")
                    présentateur.traiterRequêteAfficherCommentaires()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        présentateur.traiterRequêteAfficherDétailÉvenement(ModèleÉvénements.événementPrésenté!!.idEvenement)

        clickListenerParticipation()

        btnAddCommentaire.setOnClickListener { afficherVueAjoutCommentaire() }
    }

    /**
     * Cette méthode sert à afficher ou cacher les composants des différentes vue
     * qui ne sont pas nécéssaire à la vue en cours. Par exemple, lorsque la vue
     * détailÉvénement est en cours, les composants de la liste de participant
     * et de commentaire sont caché.
     *
     * @param pageÀgénérer le nom de la vue à afficher
     */
    private fun setVisibilitéVues(pageÀgénérer: String) {
        if (pageÀgénérer == "details") {
            groupeDetails.visibility = View.VISIBLE
            groupeParticipation.visibility = View.VISIBLE
            btnParticipation.visibility = View.VISIBLE
            imageEvent.visibility = View.VISIBLE

            listeComposables.visibility = View.INVISIBLE
            btnAddCommentaire.visibility = View.INVISIBLE
        } else {
            groupeDetails.visibility = View.INVISIBLE
            groupeParticipation.visibility = View.INVISIBLE
            btnParticipation.visibility = View.INVISIBLE
            imageEvent.visibility = View.INVISIBLE

            listeComposables.visibility = View.VISIBLE
            if (pageÀgénérer == "commentaires") btnAddCommentaire.visibility =
                View.VISIBLE else btnAddCommentaire.visibility = View.INVISIBLE
        }
    }

    /**
     * Ajoute l'écouteur sur le bouton de prticipation.
     *
     */
    private fun clickListenerParticipation() {
        btnParticipation.setOnClickListener {
            présentateur.traiterRequêteAjouterParticipation(ModèleÉvénements.événementPrésenté!!.idEvenement)
        }

    }

    /**
     * Cette méthode permet d'ouvrir le calendrier et remplir automatiquement
     * les champs en fonction des informations de l'événement lorsque
     * l'utilisateur clique sur le bouton participer.
     *
     * @param date représente la date de l'événement qui sera ajouté au calendrier
     */
    // https://developer.android.com/guide/topics/providers/calendar-provider#intents
    override fun afficherApplicationCalendrierPourAjouter(date: IntArray) {
        val startMillis: Long = Calendar.getInstance().run {
            set(date[0], date[1] - 1, date[2], date[3], date[4])
            timeInMillis
        }
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.Events.TITLE, texteNom.text)
            .putExtra(CalendarContract.Events.DESCRIPTION, texteDescription.text)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, texteLocation.text)
            .putExtra(
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.AVAILABILITY_BUSY
            )
        startActivity(intent)
    }

    /**
     * Cette méthode permet d'ouvrir le calendrier et de retirer l'événement
     * ajouté préalablement lorsque l'utilisateur clique sur le bouton
     * pour retirer sa participation
     *
     * @param date représente la date de l'événement qui sera retirer du calendrier
     */
    // https://developer.android.com/guide/topics/providers/calendar-provider#intents
    override fun afficherApplicationCalendrierPourEffacer(date: IntArray) {
        val startMillis: Long = Calendar.getInstance().run {
            set(date[0], date[1] - 1, date[2], date[3], date[4])
            timeInMillis
        }
        val builder: Uri.Builder = CalendarContract.CONTENT_URI.buildUpon()
            .appendPath("time")
        ContentUris.appendId(builder, startMillis)
        val intent = Intent(Intent.ACTION_VIEW)
            .setData(builder.build())
        startActivity(intent)
    }

    /**
     * Affiche simplement un toast disant qu'une erreur niveau serveur est survenu.
     *
     */
    override fun afficherToastErreurServeur() {
        Toast.makeText(context, R.string.serveur_error, Toast.LENGTH_LONG).show()
    }

    /**
     * Affiche simplement un toast qui indique qu'il n'y a aucun commentaire
     * pour l'événement sélectionné.
     *
     */
    override fun afficherToastAucunCommentaire() {
        Toast.makeText(context, R.string.no_comments, Toast.LENGTH_LONG).show()
    }

    /**
     * Affiche simplement un toast qui indique à l'utilisateur que sa participation
     * à un événement a bien été enregistré.
     *
     */
    override fun afficherToastParticipationAjouté() {
        Toast.makeText(context, "Votre participation a été ajouté.", Toast.LENGTH_SHORT).show()
    }

    /**
     * Affiche simplement un toast qui indique à l'utilisateur que sa participation
     * à un événement a bien été retiré.
     *
     */
    override fun afficherToastParticipationRetiré() {
        Toast.makeText(context, "Votre participation a été retiré.", Toast.LENGTH_SHORT).show()
    }

    /**
     * Cette méthode sert à initialiser les informations concernant l'événement
     * à partir de l'objet événement qui lui est passé en paramètre.
     *
     * @param evenement Un objet événenement passé par le présentateur.
     */
    override fun setInfo(evenement: Événement) {
        texteNom.text = evenement.nomEvenement
        texteLocation.text = evenement.location
        texteDate.text = evenement.date
        texteOrganisateur.text = evenement.organisateur?.nomUtilisateur
        texteDescription.text = evenement.description
        imageEvent.load(evenement.urlImage)
        imageOrganisateur.load(evenement.organisateur!!.urlImage)
    }

    /**
     * Cette méthode sert à initialiser le nombre de participant inscrit à l'événement
     * dans la vue des détails de l'événement
     *
     * @param nombreParticipant Le nombre correspondant au nombre total de partitipant
     * inscrit à l'événement en question.
     */
    override fun setNombreParticipant(nombreParticipant: Int) {
        texteParticipant.text = nombreParticipant.toString()
    }

    /**
     * Change simplement le texte du bouton de participation si l'utilisateur sélectionné
     * participe déjà à l'événement.
     *
     */
    override fun afficherNePlusParticiper() {
        btnParticipation.text = "Je ne participe pas"
    }

    /**
     * Change simplement le texte du bouton de participation si l'utilisateur sélectionné
     * ne participe pas encore à l'événement.
     *
     */
    override fun afficherParticipation() {
        btnParticipation.text = "Je participe"
    }

    /**
     * Permet de cacher le bouton de participation lorsque l'utilisateur connecté
     * est aussi l'organisateur de l'événement.
     *
     */
    override fun cacherBoutonParticipation() {
        btnParticipation.isVisible = false
    }

    /**
     * Cette méthode permet de faire l'affichage de la liste complète des participants
     * inscrit à l'événement sélectionné.
     *
     * @param participants La liste de tous les participants.
     * @param imageUrl L'url de l'image de chaque utilisateur.
     */
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

    /**
     * Cette méthode cache la vue de chargement lorsque les informations de l'événement
     * sont chargé correctement.
     *
     */
    override fun montrerLesDetailsEvenement() {
        chargement.isVisible = false
        groupeDetails.isVisible = true
    }

    /**
     * Cette méthode permet de faire l'affichage de la liste complète des commentaires
     * laissé relié à l'événement sélectionné.
     *
     * @param commentaires La liste de tous les commentaires de l'événement
     */
    override fun afficherListeCommentaires(commentaires: List<Commentaire>) {
        listeComposables.setContent {
            MaterialTheme {
                ListeCarteCommentaires(commentaires = commentaires)
            }
        }
        if (commentaires.isEmpty()) afficherToastAucunCommentaire()
    }

    /**
     * Permet de rediriger vers la vue d'ajout de commentaire.
     *
     */
    override fun afficherVueAjoutCommentaire() {
        fragmentLoader.loadFragment(VueCreationCommentaire())
    }
}