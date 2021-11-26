package com.even

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.even.présentation.modèle.*
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.sourceDeDonnées.SourceDeDonnéesAPI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val source = SourceDeDonnéesAPI()
        instancierLesModèles(source)
    }

    private fun instancierLesModèles(source: ISourceDeDonnées) {
        ModèleUtilisateurs.setSource(source)
        ModèleÉvénements.setSource(source)
        ModèleEnregistrement.setSource(source)
        ModèleConnexion.setSource(source)
        ModèleMesÉvènements.setSource(source)
    }

    //permet de fermer le drawer quand il est ouvert en appuyant sur back
    override fun onBackPressed() {
        var drawer : DrawerLayout?= findViewById(R.id.drawer_layout)
        if (drawer?.isDrawerOpen(GravityCompat.START) == true) {
            drawer?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}