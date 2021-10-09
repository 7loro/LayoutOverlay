package com.casper.layoutoverlay.overlay.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.casper.layoutoverlay.overlay.databinding.OverlayItemBinding
import com.casper.layoutoverlay.overlay.domain.model.Overlay
import com.casper.layoutoverlay.shared.delegate.observer
import javax.inject.Inject

class OverlayAdapter @Inject constructor() : RecyclerView.Adapter<OverlayAdapter.ViewHolder>() {

    var overlayItems: List<Overlay> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onClickListener: ((overlay: Overlay) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OverlayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(overlayItems[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = overlayItems.size

    fun setOnClickListener(listener: (overlay: Overlay) -> Unit) {
        this.onClickListener = listener
    }

    inner class ViewHolder(private val binding: OverlayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(overlay: Overlay) {
            binding.id.text = overlay.id.toString()
            binding.width.text = overlay.widthDp.toString()
            itemView.setOnClickListener { onClickListener?.invoke(overlay) }
        }
    }
}
