package com.casper.layoutoverlay.overlay.domain.usecase

import com.casper.layoutoverlay.overlay.domain.model.Overlay
import com.casper.layoutoverlay.overlay.domain.repository.OverlayRepository
import javax.inject.Inject

class AddOverlayUseCase @Inject constructor(
    private val overlayRepository: OverlayRepository
) {
    sealed interface Result {
        data class Success(val rowId: Long) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend fun execute(overlay: Overlay): Result {
        return runCatching {
            Result.Success(
                overlayRepository.addOverlayItem(
                    Overlay(
                        id = overlay.id,
                        shape = overlay.shape,
                        widthDp = overlay.widthDp,
                        heightDp = overlay.heightDp
                    )
                )
            )
        }.getOrElse {
            Result.Error(it)
        }
    }
}
