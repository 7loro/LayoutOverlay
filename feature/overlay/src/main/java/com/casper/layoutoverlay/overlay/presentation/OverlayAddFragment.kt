package com.casper.layoutoverlay.overlay.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.casper.layoutoverlay.overlay.R
import com.casper.layoutoverlay.overlay.databinding.FragmentOverlayAddBinding
import com.casper.layoutoverlay.service.domain.OverlayItem
import com.casper.layoutoverlay.service.presentation.IOverlayService
import com.casper.layoutoverlay.service.presentation.OverlayService
import com.casper.layoutoverlay.service.presentation.Shape
import com.casper.layoutoverlay.shared.delegate.viewBinding

class OverlayAddFragment : Fragment(R.layout.fragment_overlay_add) {
    private val binding: FragmentOverlayAddBinding by viewBinding()
    private var overlayService: IOverlayService? = null
    private var bound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as OverlayService.OverlayServiceBinder
            overlayService = binder.getService()
            bound = true
            drawLayout()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            bound = false
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkOverlayPermission();

        binding.drawButton.setOnClickListener {
            if (bound) {
                drawLayout()
                return@setOnClickListener
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // check if the user has already granted
                // the Draw over other apps permission
                if (Settings.canDrawOverlays(context)) {
                    // bind the service based on the android version
                    bindService()
                }
            } else {
                bindService()
            }
        }
    }

    // method to ask user to grant the Overlay permission
    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(myIntent)
            }
        }
    }

    private fun bindService() {
        Intent(requireContext(), OverlayService::class.java).also { intent ->
            requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun drawLayout() {
        val width = binding.widthInput.text
        val height = binding.heightInput.text
        if (width.isNullOrEmpty() || height.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please check width or height",
                Toast.LENGTH_SHORT
            ).show()
        }
        runCatching {
            overlayService?.drawLayout(
                OverlayItem(
                    // TODO support multiple id, various type
                    id = 0,
                    Shape.Rect,
                    dpToPx(width.toString().toFloat()),
                    dpToPx(height.toString().toFloat())
                )
            )
        }.onFailure {
            Toast.makeText(
                requireContext(),
                "Please check width or height",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            requireContext().resources.displayMetrics
        ).toInt()
    }
}