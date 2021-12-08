package com.even.ui.composants

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class SelecteurDate(texteDate: TextView, type: String) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    val texteDate = texteDate
    val type = type

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        if (!texteDate.text.isNullOrBlank()) {
            year = texteDate.text.substring(0, 4).toInt()
            month = texteDate.text.substring(5, 7).toInt()
            if (type != "recherche") {
                day = texteDate.text.substring(8, 10).toInt()
            }
        }

        // https://stackoverflow.com/a/41348240/17406108
        val dialog = DatePickerDialog(
            requireContext(), android.R.style.Theme_Holo_Dialog, this, year,
            if (!texteDate.text.isNullOrBlank()) month - 1 else month,
            day
        )
        if (type == "recherche") {
            dialog.datePicker.findViewById<View>(resources.getIdentifier("day", "id", "android"))
                .visibility = View.GONE
        }

        return dialog
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        var moisFormaté: String = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
        if (type != "recherche") {
            var jourFormaté: String = if (day < 10) "0${day}" else "${day}"
            texteDate.text = "${year}-${moisFormaté}-${jourFormaté}"
            SelecteurTemps(texteDate).show(
                requireActivity().supportFragmentManager,
                "selecteurTemps"
            )
        } else {
            texteDate.text = "${year}-${moisFormaté}"
        }
    }
}