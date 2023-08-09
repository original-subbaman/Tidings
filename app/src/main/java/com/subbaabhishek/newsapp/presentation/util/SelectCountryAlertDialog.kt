package com.subbaabhishek.newsapp.presentation.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.subbaabhishek.newsapp.R

class SelectCountryAlertDialog : DialogFragment() {

    private var index : Int = 0;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.select_country)
                .setSingleChoiceItems(R.array.country_array, 0) { _, i ->
                    index = i;
                }
                .setPositiveButton(R.string.select) { _, _ -> setFragmentResult("index", bundleOf("index_country" to index))}
                .setNegativeButton(R.string.cancel){ dialog, _ -> dialog.dismiss()}

            builder.create()
        } ?: throw IllegalStateException("Activity is null")
    }

}