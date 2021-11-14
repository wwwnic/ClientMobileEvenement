package com.even.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.even.R
import com.even.domaine.entité.Événement

class details_evenement(var evenement : Événement) : Fragment(R.layout.fragment_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var imageEven = view?.findViewById<ImageView>(R.id.imageEven)
        var texteNom = view?.findViewById<TextView>(R.id.textNomEven)
        var texteLocation = view?.findViewById<TextView>(R.id.textLocationEven)
        var texteDate = view?.findViewById<TextView>(R.id.textDateEven)
        var texteOrg = view?.findViewById<TextView>(R.id.textOrgEven)
        var texteDesc = view?.findViewById<TextView>(R.id.textDescEven)

        imageEven.setImageResource(R.drawable.wowimg)
        texteNom?.text = evenement.nom
        texteLocation?.text = evenement.location
        texteDate?.text = evenement.date.toString()
        texteOrg?.text = evenement.organisateur
        texteDesc?.text = evenement.description
    }
}