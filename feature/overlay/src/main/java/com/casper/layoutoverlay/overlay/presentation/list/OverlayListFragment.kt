package com.casper.layoutoverlay.overlay.presentation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.casper.layoutoverlay.overlay.R
import com.casper.layoutoverlay.overlay.databinding.FragmentOverlayListBinding
import com.casper.layoutoverlay.shared.delegate.viewBinding
import com.casper.layoutoverlay.shared.presentation.extension.observe
import com.casper.layoutoverlay.shared.presentation.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OverlayListFragment : Fragment(R.layout.fragment_overlay_list) {

    private val binding: FragmentOverlayListBinding by viewBinding()
    private val viewModel: OverlayListViewModel by viewModels()
    @Inject lateinit var overlayAdapter: OverlayAdapter
    private val stateObserver = Observer<OverlayListViewModel.ViewState> {
        overlayAdapter.overlayItems = it.overlayList

        binding.progressBar.visible = it.isLoading
        binding.errorAnimation.visible = it.isError
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        overlayAdapter.setOnClickListener {
            // TODO navigation util 만들기
            // TODO AddFramgnet 로 이동해서 edit 모드
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            /*addItemDecoration(
                VerticalSpaceItemDecoration(resources.getDimensionPixelSize(
                    R.dimen.todo_list_vertical_space
                ))
            )*/
            adapter = overlayAdapter
        }

        binding.addBtn.setOnClickListener {
            val action =
                OverlayListFragmentDirections.actionOverlayListFragmentToOverlayAddFragment()
            findNavController().navigate(action)
        }

        observe(viewModel.stateLiveData, stateObserver)

        viewModel.loadData()
    }
}
