package com.even

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.even.présentation.modèle.ModèleUtilisateurs
import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.SourceDeDonnéesAPI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val source = SourceDeDonnéesAPI()
        ModèleUtilisateurs.setSource(source)
        ModèleÉvénements.setSource(source)
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