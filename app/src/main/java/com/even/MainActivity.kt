package com.even

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.even.R
import com.even.ui.fragment.liste_evenement
import com.even.ui.fragment.mesEvenements
import com.even.ui.fragment.recherche
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_principal)

        loadFragment(liste_evenement())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuRecent -> {
                    loadFragment(liste_evenement())
                    true
                }
                R.id.menuRecherche -> {
                    loadFragment(recherche())
                    true
                }
                R.id.menuMesEvens -> {
                    loadFragment(mesEvenements())
                    true
                }
                else -> false
            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}