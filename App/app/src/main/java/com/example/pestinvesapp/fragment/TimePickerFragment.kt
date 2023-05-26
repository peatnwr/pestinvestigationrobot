package com.example.pestinvesapp.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.example.pestinvesapp.R
import java.util.Calendar

class TimePickerFragment:DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        var hour = c.get(Calendar.HOUR_OF_DAY)
        var minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity, 2, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val selectedTime: AppCompatButton = activity?.findViewById(R.id.btnSelectTime) as AppCompatButton
        val minuteNew: String = if(minute < 10) "0${minute.toString()}" else minute.toString()
        selectedTime.text = "$hourOfDay:$minuteNew:00"
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }
}