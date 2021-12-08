package com.even.ui.composants

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.even.R

class FragmentLoader(val fragmentManager: FragmentManager) {
    // https://stackoverflow.com/questions/44424985/switch-between-fragments-in-bottomnavigationview

    fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun loadFragment(fragment: Fragment, motcles: String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment, motcles)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}