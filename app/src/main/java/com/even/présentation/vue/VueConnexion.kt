package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.even.R
import com.even.domaine.entité.UnCoroutineDispatcher
import com.even.domaine.entité.ValidateurTextuel
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.présenteur.IConnexion
import com.even.présentation.présenteur.PrésentateurConnexion

/**
 *  Une vue qui permet d'interagir avec le fragment connexion
 *
 */
class VueConnexion : Fragment(R.layout.fragment_connexion), IConnexion.IVue {
    lateinit var présentateurConnexion: IConnexion.IPrésentateur

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        présentateurConnexion = PrésentateurConnexion(
            this,
            ModèleAuthentification(),
            ValidateurTextuel(),
            UnCoroutineDispatcher()
        )
        clickListenerBtnConnexion(view)
        clickListenerBtnCreerUnCompte(view)
    }

    /**
     * Instancie le clickListerer pour le bouton creer un compte
     *
     * @param view la reference à la vue
     */
    private fun clickListenerBtnCreerUnCompte(view: View) {
        val boutonEnregistrement = view.findViewById<Button>(R.id.connexion_boutonEnregistrement)
        boutonEnregistrement?.setOnClickListener { naviguerVersFragmentEnregistgrement() }
    }

    /**
     * Instancie le clickListerer pour le bouton connexion
     *
     * @param view la reference à la vue
     */
    private fun clickListenerBtnConnexion(view: View) {
        val boutonConnexion = view.findViewById<Button>(R.id.connexion_boutonConnexion)
        val txtNomUsager = view.findViewById<EditText>(R.id.connexion_textNomUtilisateur).text
        val txtMotDePasse = view.findViewById<EditText>(R.id.connexion_textMotDePasse).text
        boutonConnexion?.setOnClickListener {
            présentateurConnexion.traiterRequêteDemanderProfilPourConnexion(
                txtNomUsager,
                txtMotDePasse
            )
        }
    }

    /**
     * Affiche un toast qui indique que la connexion s'est bien déroulée
     *
     */
    override fun afficherToastSuccesConnexion() {
        Toast.makeText(context, R.string.login_completed, Toast.LENGTH_SHORT).show()
    }

    /**
     * Affiche un toast qui indique une erreur de connexion du à des informations invalides
     *
     */
    override fun afficherToastErreurConnexion() {
        Toast.makeText(context, R.string.login_incompleted, Toast.LENGTH_LONG).show()
    }

    /**
     * Affiche un toast qui indique une erreur de connexion avec le serveur
     *
     */
    override fun afficherToastErreurServeur() {
        Toast.makeText(context, R.string.serveur_error, Toast.LENGTH_LONG).show()
    }

    /**
     * Navigue vers le fragment principal
     *
     */
    override fun naviguerVersFragmentPrincipal() {
        findNavController().navigate(R.id.action_connexion_to_principal)
    }

    /**
     * Navigue vers le fragment enregistrement
     *
     */
    override fun naviguerVersFragmentEnregistgrement() {
        findNavController().navigate(R.id.action_connexion_to_enregistrement)
    }

    /**
     * Change la couleur de la zone contenant le nom utilisateur afin de permettre à l'utilisateur
     * de réaliser qu'il a fait une erreur dans le champ.
     *
     * @param afficherEnRouge si la zone doit etre affichée en rouge
     */
    override fun afficherErreurNomUtilisateur(afficherEnRouge: Boolean) {
        changerCouleurValidation(
            afficherEnRouge, R.id.connexion_textNomUtilisateur, R.id.connexion_hint_nomUtilisateur
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
            afficherEnRouge, R.id.connexion_textMotDePasse, R.id.connexion_hint_motDePasse
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
        textView.setTextColor(ContextCompat.getColor(requireContext(), idCouleur))
    }
}