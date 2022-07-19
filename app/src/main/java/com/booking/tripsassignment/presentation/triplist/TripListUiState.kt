package com.booking.tripsassignment.presentation.triplist

import com.booking.tripsassignment.domain.models.ContentItem

/**
 * Ui State
 *
 * @author Dima Balash on 12.06.2022
 */
data class TripListUiState(
    val loading: Boolean = false,
    val error: String = "",
    val data: List<ContentItem> = emptyList()
)
