package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.even.R
import com.even.domaine.entité.ValidateurTextuel
import com.even.domaine.interacteur.IntConnexion
import com.even.domaine.interacteur.IntEnregistrement
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.présenteur.IEnregistrement
import com.even.présentation.présenteur.PrésentateurEnregistrement
import com.even.sourceDeDonnées.SourceDeDonnéesAPI

/**
 *  Une vue qui permet d'interagir avec le fragment enregistrement
 *
 */
class VueEnregistrement : Fragment(R.layout.fragment_enregistrement), IEnregistrement.IVue {

    lateinit var présentateurEnregistrement: IEnregistrement.IPrésentateur

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        présentateurEnregistrement =
            PrésentateurEnregistrement(
                this,
                ModèleAuthentification(),
                ValidateurTextuel()
            )
        val toolbar = view.findViewById<Toolbar>(R.id.enregistrement_toolbar)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        clickListenerBtnEnregistrement(view)
    }

    /**
     * Instancie le clickListerer pour le bouton creer un compte
     *
     * @param view la reference à la vue
     */
    private fun clickListenerBtnEnregistrement(view: View) {
        val boutonEnregistrement = view.findViewById<Button>(R.id.enregistrement_creer_un_compte)
        boutonEnregistrement?.setOnClickListener {
            val txtNomUsager = view.findViewById<TextView>(R.id.enregistrement_nomUsager).text
            val txtMotDePasse = view.findViewById<TextView>(R.id.enregistrement_motDePasse).text
            val txtCourriel = view.findViewById<TextView>(R.id.enregistrement_courriel).text
            val txtTelephone = view.findViewById<TextView>(R.id.enregistrement_telephone).text
            val entréesValide = présentateurEnregistrement.traiterRequêteValiderTousLesEntrées(
                txtNomUsager,
                txtMotDePasse,
                txtCourriel,
                txtTelephone
            )
            if (entréesValide) {
                présentateurEnregistrement.traiterRequêteReclamerEnregistrement(
                    txtNomUsager,
                    txtMotDePasse,
                    txtCourriel,
                    txtTelephone
                )
            }
        }
    }

    /**
     * Navigue vers l'écran connexion
     *
     */
    override fun naviguerVersConnexion() {
        findNavController().navigate(R.id.action_enregistrement_to_connexion)
    }

    /**
     * Affiche un toast qui indique que l'enregistrement s'est bien déroulée
     *
     */
    override fun afficherToastSuccesEnregistrement() {
        Toast.makeText(context, R.string.sign_up_completed, Toast.LENGTH_SHORT).show()
    }
    /**
     * Affiche un toast qui indique une erreur d'enregistrement
     *
     */
    override fun afficherToastErreurEnregistrement() {
        Toast.makeText(context, R.string.sign_up_incompleted, Toast.LENGTH_LONG).show()
    }

    /**
     * Affiche un toast qui indique une erreur de connexion avec le serveur
     *
     */
    override fun afficherToastErreurServeur() {
        Toast.makeText(context, R.string.serveur_error, Toast.LENGTH_LONG).show()
    }

    /**
     * Change la couleur de la zone contenant le nom utilisateur afin de permettre à l'utilisateur
     * de réaliser qu'il a fait une erreur dans le champ.
     *
     * @param afficherEnRouge si la zone doit etre affichée en rouge
     */
    override fun afficherErreurNomUsager(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.enregistrement_nomUsager, R.id.enregistrement_hint_nomUsager
        )
    }

    /**
     * Change la couleur de la zone contenant le mot de passe afin de permettre à l'utilisateur
     * de réaliser qu'il a fait une erreur dans le champ.
     *
     * @param afficherEnRouge si la zone doit etre affichée en rouge
     */
    override fun afficherErreurMotDePasse(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.enregistrement_motDePasse, R.id.enregistrement_hint_motDePasse
        )
    }

    /**
     * Change la couleur de la zone contenant le courriel afin de permettre à l'utilisateur
     * de réaliser qu'il a fait une erreur dans le champ.
     *
     * @param afficherEnRouge si la zone doit etre affichée en rouge
     */
    override fun afficherErreurCourriel(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.enregistrement_courriel, R.id.enregistrement_hint_courriel
        )
    }

    /**
     * Change la couleur de la zone contenant le telephone afin de permettre à l'utilisateur
     * de réaliser qu'il a fait une erreur dans le champ.
     *
     * @param afficherEnRouge si la zone doit etre affichée en rouge
     */
    override fun afficherErreurTéléphone(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.enregistrement_telephone, R.id.enregistrement_hint_telephone
        )
    }

    /**
     * Permets de changer la couleur des zones envoyées
     *
     * @param afficherEnRouge si la zone doit être affichée en rouge
     * @param editTextId le text à être changé en rouge
     * @param textViewId la textView dont l'arrière-plan doit être changé
     */
    private fun changerCouleurValidation(
        afficherEnRouge: Boolean,
        editTextId: Int,
        textViewId: Int
    ) {
        val vue = requireView()
        val editText = vue.findViewById<EditText>(editTextId)
        val textView = vue.findViewById<TextView>(textViewId)
        val idArrièrePlan =
            if (afficherEnRouge) R.drawable.login_plaintext_in_red else R.drawable.login_plaintext
        val idCouleur = if (afficherEnRouge) R.color.rouge else R.color.figmaMauve
        editText.setBackgroundResource(idArrièrePlan)
        textView.setTextColor(getColor(requireContext(), idCouleur))
    }
}
