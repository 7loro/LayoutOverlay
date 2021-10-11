package com.casper.layoutoverlay.overlay.domain.usecase

import com.casper.layoutoverlay.overlay.domain.model.Overlay
import com.casper.layoutoverlay.overlay.domain.repository.OverlayRepository
import javax.inject.Inject

class GetOverlayUseCase @Inject constructor(
    private val overlayRepository: OverlayRepository
) {
    sealed interface Result {
        data class Success(val data: List<Overlay>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend fun execute(): Result {
        return runCatching {
            Result.Success(overlayRepository.getOverlayList())
        }.getOrElse {
            Result.Error(it)
        }
    }
}
