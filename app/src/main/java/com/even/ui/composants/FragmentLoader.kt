package com.even.ui.composants

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.even.R

/**
 * Classe qui donne accès au gestionnaire de fragments pour pouvoir naviguer dans l'application à
 * partir de la vue principale.
 */
class FragmentLoader(val fragmentManager: FragmentManager) {
    // https://stackoverflow.com/questions/44424985/switch-between-fragments-in-bottomnavigationview

    /**
     * Charge le fragment donné en paramètre.
     */
    fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Charge le fragment donné en paramètre avec un tag.
     */
    fun loadFragment(fragment: Fragment, motcles: String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment, motcles)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}