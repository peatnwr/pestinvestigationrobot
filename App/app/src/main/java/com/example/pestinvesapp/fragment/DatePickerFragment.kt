package com.example.pestinvesapp.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.example.pestinvesapp.R
import java.text.SimpleDateFormat
import java.util.Calendar

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate : AppCompatButton = activity?.findViewById(R.id.btnSelectDate) as AppCompatButton
        selectedDate.text = formatDate(year, month, dayOfMonth)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        var calendar : Calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val chosenDate = calendar.time
        val formatt = SimpleDateFormat("yyyy-MM-dd")

        return formatt.format(chosenDate)
    }

}