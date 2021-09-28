package com.casper.layoutoverlay.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.casper.layoutoverlay.home.R
import com.casper.layoutoverlay.home.databinding.FragmentHomeBinding
import com.casper.layoutoverlay.shared.delegate.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.drawButton.setOnClickListener {
            Toast.makeText(context, "Start service to draw overlay layout", Toast.LENGTH_SHORT).show()
            // Run Service to draw debug overlay layout
        }
    }
}