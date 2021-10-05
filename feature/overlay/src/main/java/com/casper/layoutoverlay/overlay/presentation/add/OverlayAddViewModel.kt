package com.casper.layoutoverlay.overlay.presentation.add

import androidx.lifecycle.viewModelScope
import com.casper.layoutoverlay.overlay.domain.model.Overlay
import com.casper.layoutoverlay.overlay.domain.usecase.AddOverlayUseCase
import com.casper.layoutoverlay.shared.presentation.viewmodel.BaseAction
import com.casper.layoutoverlay.shared.presentation.viewmodel.BaseViewModel
import com.casper.layoutoverlay.shared.presentation.viewmodel.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverlayAddViewModel @Inject constructor(
    private val addOverlayUseCase: AddOverlayUseCase
) : BaseViewModel<OverlayAddViewModel.ViewState, OverlayAddViewModel.Action>(ViewState()) {

    fun addOverlay(overlay: Overlay) {
        viewModelScope.launch {
            addOverlayUseCase.execute(overlay).also { result ->
                val action = when (result) {
                    is AddOverlayUseCase.Result.Success -> Action.TodoAddSuccess
                    is AddOverlayUseCase.Result.Error -> Action.TodoAddFailure
                }
                sendAction(action)
            }
        }
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.TodoAddSuccess -> state.copy(
            isLoading = false,
            isError = false,
            isSaved = true
        )
        is Action.TodoAddFailure -> state.copy(
            isLoading = false,
            isError = true,
            isSaved = false
        )
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val isSaved: Boolean = false
    ) : BaseViewState

    sealed interface Action : BaseAction {
        object TodoAddSuccess : Action
        object TodoAddFailure : Action
    }
}
