package com.booking.tripsassignment.presentation.triplist

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booking.tripsassignment.R
import com.booking.tripsassignment.common.ResourceManager
import com.booking.tripsassignment.common.utils.Result
import com.booking.tripsassignment.domain.usecase.GetBookingChainsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for showing trip list
 *
 * @author Dima Balash on 12.06.2022
 */
@HiltViewModel
class TripListViewModel @Inject constructor(
    private var dispatcher: CoroutineDispatcher,
    private val getBookingChainsUseCase: GetBookingChainsUseCase,
    private val resourceManager: ResourceManager
) : ViewModel() {

    //put user id here
    private val userId = 7984567

    private val _uiState = MutableStateFlow(TripListUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        getBookings(userId)
    }

    @VisibleForTesting
    fun getBookings(userId: Int) {
        _uiState.update { currentUiState -> currentUiState.copy(loading = true, error = "") }
        viewModelScope.launch {
            withContext(dispatcher) {
                when (val result = getBookingChainsUseCase.getBookingChains(userId)) {
                    is Result.Error -> _uiState.update { currentUiState ->
                        currentUiState.copy(
                            loading = false,
                            error = result.exception.message ?: resourceManager.getString(
                                R.string.default_error
                            )
                        )
                    }
                    else -> _uiState.update { currentUiState ->
                        currentUiState.copy(
                            loading = false,
                            data = result.data() ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    /**
     * Retry receive data
     */
    fun retryClick() {
        getBookings(userId)
    }
}