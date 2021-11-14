package com.even.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.even.MainActivity
import com.even.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class principal : Fragment(R.layout.fragment_principal) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNav)
        loadFragment(liste_evenement())

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
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}