package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.even.R
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.présenteur.IModificationCompte
import com.even.présentation.présenteur.PrésentateurModificationCompte

class VueModificationCompte : Fragment(R.layout.fragment_modification_compte), IModificationCompte.IVue{

    lateinit var username : EditText
    lateinit var email : EditText
    lateinit var phone :EditText

    var utilisateur = ModèleAuthentification.utilisateurConnecté

    lateinit var présentateur : PrésentateurModificationCompte

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = view.findViewById(R.id.modificationCompte_username)
        email = view.findViewById(R.id.modificationComte_email)
        phone = view.findViewById(R.id.modificationCompte_phone)

        présentateur = PrésentateurModificationCompte()

        username.setText(utilisateur!!.nomUtilisateur)
        email.setText(utilisateur!!.courriel)
        phone.setText(utilisateur!!.telephone)
    }

}