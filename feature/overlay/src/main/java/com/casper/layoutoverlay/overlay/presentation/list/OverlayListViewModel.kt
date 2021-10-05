package com.casper.layoutoverlay.overlay.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.casper.layoutoverlay.overlay.domain.model.Overlay
import com.casper.layoutoverlay.overlay.domain.usecase.GetOverlayUseCase
import com.casper.layoutoverlay.service.domain.OverlayItem
import com.casper.layoutoverlay.shared.presentation.viewmodel.BaseAction
import com.casper.layoutoverlay.shared.presentation.viewmodel.BaseViewModel
import com.casper.layoutoverlay.shared.presentation.viewmodel.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverlayListViewModel @Inject constructor(
    private val getOverlayUseCase: GetOverlayUseCase
) : BaseViewModel<OverlayListViewModel.ViewState, OverlayListViewModel.Action>(ViewState()){

    private val _overlayItems = MutableLiveData<List<OverlayItem>>()
    private val overlayItems: LiveData<List<OverlayItem>> = _overlayItems

    override fun onLoadData() {
        getOverlayList()
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.OverlayListLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            overlayList = viewAction.overlayList
        )
        is Action.OverlayListLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            overlayList = listOf()
        )
    }

    private fun getOverlayList() {
        viewModelScope.launch {
            getOverlayUseCase.execute().also { result ->
                val action = when (result) {
                    is GetOverlayUseCase.Result.Success ->
                        if (result.data.isEmpty()) {
                            Action.OverlayListLoadingFailure
                        } else {
                            Action.OverlayListLoadingSuccess(result.data)
                        }

                    is GetOverlayUseCase.Result.Error ->
                        Action.OverlayListLoadingFailure
                }
                sendAction(action)
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val overlayList: List<Overlay> = listOf()
    ) : BaseViewState

    sealed interface Action : BaseAction {
        class OverlayListLoadingSuccess(val overlayList: List<Overlay>) : Action
        object OverlayListLoadingFailure : Action
    }
}