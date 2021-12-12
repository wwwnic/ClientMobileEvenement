package com.even

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.even.présentation.modèle.ModèleAuthentification
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.ISourceDeDonnées
import com.even.sourceDeDonnées.SourceDeDonnéesAPI

class MainActivity : AppCompatActivity() {

    companion object {
        var nePasChargerSourcePourTestInstrumentation: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val source = SourceDeDonnéesAPI()
        if (!nePasChargerSourcePourTestInstrumentation) {
            instancierLesModèles(source)
        }
        setContentView(R.layout.activity_main)
    }

    private fun instancierLesModèles(source: ISourceDeDonnées) {
        ModèleUtilisateurs.setSource(source)
        ModèleÉvénements.setSource(source)
        ModèleAuthentification.setSource(source)
    }

    //permet de fermer le drawer quand il est ouvert en appuyant sur back
    override fun onBackPressed() {
        var drawer: DrawerLayout? = findViewById(R.id.drawer_layout)
        if (drawer?.isDrawerOpen(GravityCompat.START) == true) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}