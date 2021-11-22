package com.even.présentation.vue

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.even.présentation.modèle.ModèleÉvénements
import com.even.sourceDeDonnées.SourceDeDonnéesBidon
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.even.R
import com.even.sourceDeDonnées.SourceDeDonnéesAPI
import com.google.android.material.navigation.NavigationView


class VuePrincipale : Fragment(R.layout.fragment_principal) {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navView = view.findViewById<NavigationView>(R.id.nav_view)
        
        toolbar = view.findViewById(R.id.toolbar)
        drawerLayout = view.findViewById(R.id.drawer_layout)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_round_side_menu_24)
        toolbar.title = resources.getString(R.string.recent_event)

        setupDrawerToggle();
        loadFragment(VueListeEvenement())
        bottomNavOnClick(bottomNav)
        navViewOnClick(navView)
    }

    private fun navViewOnClick(navView: NavigationView) {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.account_management -> {
                    loadFragment(modification_compte())
                    toolbar.title = resources.getString(R.string.account_management)
                    drawerLayout.close()
                    true
                }
                R.id.log_off -> {
                    navView.findNavController().navigate(R.id.connexion)
                    drawerLayout.close()
                    Toast.makeText(context, R.string.log_off_msg, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun bottomNavOnClick(bottomNav: BottomNavigationView) {
        val source = SourceDeDonnéesBidon()
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuRecent -> {
                    loadFragment(VueListeEvenement())
                    toolbar.title = resources.getString(R.string.recent_event)
                    true
                }
                R.id.menuRecherche -> {
                    loadFragment(VueRecherche())
                    toolbar.title = resources.getString(R.string.search)
                    true
                }
                R.id.menuMesEvens -> {
                    loadFragment(VueMesEvenements())
                    toolbar.title = resources.getString(R.string.my_event)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle? {
        return ActionBarDrawerToggle(
            this.activity,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
    }

    // https://stackoverflow.com/questions/44424985/switch-between-fragments-in-bottomnavigationview
    private fun loadFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
