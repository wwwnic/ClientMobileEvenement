package com.even.présentation.vue

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.even.R
import com.even.domaine.entité.Utilisateur
import com.even.domaine.interacteur.IModificationCompte
import com.even.domaine.interacteur.IntModificationCompte
import com.even.présentation.présenteur.PrésentateurModificationCompte

class modification_compte(var utilisateur : Utilisateur) : Fragment(R.layout.fragment_modification_compte) {

    lateinit var présentateurModificationCompte : PrésentateurModificationCompte

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        présentateurModificationCompte = PrésentateurModificationCompte(IntModificationCompte())
        
        var nomUtilisateur = view?.findViewById<EditText>(R.id.modificationCompte_username)
        var password = view?.findViewById<EditText>(R.id.modificationCompte_password)
        var email = view?.findViewById<EditText>(R.id.modificationComte_email)
        var phone = view?.findViewById<EditText>(R.id.modificationCompte_phone)
        var avatar = view?.findViewById<ImageView>(R.id.modificationCompte_avatar)        
        var btnEnregistrement = view?.findViewById<Button>(R.id.modificationCompte_btnSave)
        
        nomUtilisateur.setText(utilisateur.nomUtilisateur)
        password.setText(utilisateur.motDePasse)
        email.setText(utilisateur.courriel)
        phone.setText(utilisateur.telephone)
        //avatar.setImageResource(utilisateur.avatar)

    }
    
    


}