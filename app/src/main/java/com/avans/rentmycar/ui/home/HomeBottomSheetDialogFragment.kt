package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.avans.rentmycar.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_bottom_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.findViewById<View>(R.id.firstButton)?.setOnClickListener {
            Toast.makeText(context, "First Button clicked", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        view?.findViewById<View>(R.id.secondButton)?.setOnClickListener {
            Toast.makeText(context, "Second Button clicked", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        view?.findViewById<View>(R.id.thirdButton)?.setOnClickListener {
            Toast.makeText(context, "Third Button clicked", Toast.LENGTH_SHORT).show()
            dismiss()
        }


    }
}