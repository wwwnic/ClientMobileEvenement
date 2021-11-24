package com.even.présentation.vue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.even.R
import com.even.domaine.interacteur.IConnexion
import com.even.présentation.modèle.ModèleConnexion
import com.even.présentation.présenteur.PrésentateurConnexion
import com.even.sourceDeDonnées.SourceDeDonnéesAPI

class VueConnexion : Fragment(R.layout.fragment_connexion), IConnexion.IVue {
    lateinit var présentateurConnexion: IConnexion.IPrésentateur

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        présentateurConnexion = PrésentateurConnexion(this, ModèleConnexion(SourceDeDonnéesAPI()))
        return inflater.inflate(R.layout.fragment_connexion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var boutonConnexion = view.findViewById<Button>(R.id.connexion_boutonConnexion)
        var boutonEnregistrement = view.findViewById<Button>(R.id.connexion_boutonEnregistrement)

        boutonConnexion?.setOnClickListener {
            présentateurConnexion.traiterRequêteDemanderProfilPourConnexion("nicolas", "all2o")
        }
        boutonEnregistrement?.setOnClickListener { findNavController().navigate(R.id.action_connexion_to_enregistrement) }

    }

    override fun naviguerVersFragmentPrincipal() {
        findNavController().navigate(R.id.action_connexion_to_principal)
    }


}