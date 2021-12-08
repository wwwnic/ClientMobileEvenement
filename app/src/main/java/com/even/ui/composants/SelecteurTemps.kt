package com.even.ui.composants


import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class SelecteurTemps(texteDate: TextView) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    var texteDate = texteDate

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        var hour = c.get(Calendar.HOUR_OF_DAY)
        var minute = c.get(Calendar.MINUTE)

        val dialog = TimePickerDialog(
            requireContext(),
            android.R.style.Theme_Holo_Dialog,
            this,
            hour,
            minute,
            true
        )
        return dialog
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        var heureFormatée = if (hour < 10) "0${hour}" else hour
        var minuteFormatée = if (minute < 10) "0${minute}" else minute
        texteDate.append(" ${heureFormatée}:${minuteFormatée}")
    }
}