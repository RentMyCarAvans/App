package com.avans.rentmycar.ui.mycars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avans.rentmycar.databinding.FragmentMycarsBinding

class MyCarsFragment : Fragment() {

    private var _binding: FragmentMycarsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myCarsViewModel =
            ViewModelProvider(this).get(MyCarsViewModel::class.java)

        _binding = FragmentMycarsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textviewMycars
        myCarsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}