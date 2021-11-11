package com.even.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.even.R
import com.even.domaine.entité.Événement

class details_evenement(evenement : Événement) : Fragment(R.layout.fragment_details) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    init {
        var texte = view?.findViewById<TextView>(R.id.textEven)
        texte?.text = evenement.nom
    }
}