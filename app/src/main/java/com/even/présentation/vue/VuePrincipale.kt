package com.even.présentation.vue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.even.R
import com.even.présentation.présenteur.IPrincipal
import com.even.présentation.présenteur.PrésentateurPrincipal
import com.even.sourceDeDonnées.SourceDeDonnéesBidon
import com.even.ui.composants.FragmentLoader
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


/**
 * Vue principale qui contient la barre de navigation et les fragments contenant les pages
 * de l'application.
 *
 */
class VuePrincipale : Fragment(R.layout.fragment_principal),IPrincipal.IVue {

    lateinit var fragmentLoader: FragmentLoader

    lateinit var présentateur : IPrincipal.IPrésentateur

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView : NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_principal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoader = FragmentLoader(requireActivity().supportFragmentManager)

        présentateur = PrésentateurPrincipal(this)

        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_nav)
        navView = view.findViewById(R.id.nav_view)

        toolbar = view.findViewById(R.id.toolbar)
        drawerLayout = view.findViewById(R.id.drawer_layout)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_round_side_menu_24)
        toolbar.title = resources.getString(R.string.recent_event)

        setupDrawerToggle()
        fragmentLoader.loadFragment(VueListeEvenement())
        bottomNavOnClick(bottomNav)
        navViewOnClick(navView)
    }

    /**
     * Setup du menu de la barre du haut.
     *
     */
    private fun navViewOnClick(navView: NavigationView) {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.account_management -> {
                    fragmentLoader.loadFragment(modification_compte())
                    toolbar.title = resources.getString(R.string.account_management)
                    drawerLayout.close()
                    true
                }
                R.id.log_off -> {
                    présentateur.traiterRequêteDéconnexion()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    /**
     * Setup de la barre de navigation du bas.
     *
     */
    private fun bottomNavOnClick(bottomNav: BottomNavigationView) {
        val source = SourceDeDonnéesBidon()
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuRecent -> {
                    fragmentLoader.loadFragment(VueListeEvenement())
                    toolbar.title = resources.getString(R.string.recent_event)
                    true
                }
                R.id.menuRecherche -> {
                    fragmentLoader.loadFragment(VueRecherche())
                    toolbar.title = resources.getString(R.string.search)
                    true
                }
                R.id.menuMesEvens -> {
                    fragmentLoader.loadFragment(VueMesEvenements())
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

    /**
     * Affiche la page de connexion après la déconnexion.
     */
    override fun afficherPageConnexion() {
        requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        navView.findNavController().navigate(R.id.action_principal_to_connexion)
        drawerLayout.close()
        Toast.makeText(context, R.string.log_off_msg, Toast.LENGTH_SHORT).show()
    }
}
