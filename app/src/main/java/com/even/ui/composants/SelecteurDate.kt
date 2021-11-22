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

class SelecteurDate(texteMois : TextView) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    var texteMois = texteMois
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // https://stackoverflow.com/a/41348240/17406108
        val dialog = DatePickerDialog(requireContext(),android.R.style.Theme_Holo_Dialog,this,year,month,day)
        dialog.datePicker.findViewById<View>(resources.getIdentifier("day", "id", "android"))
            .visibility = View.GONE

        return dialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        var moisFormaté : String = if(month<10) "0${month+1}" else "${month+1}"
        texteMois.setText("${year}-${moisFormaté}")
    }
}