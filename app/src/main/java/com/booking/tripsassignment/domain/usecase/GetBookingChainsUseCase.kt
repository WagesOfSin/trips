package com.booking.tripsassignment.domain.usecase

import com.booking.tripsassignment.common.utils.Result
import com.booking.tripsassignment.domain.models.ContentItem

/**
 * UseCase to receive booking chains
 *
 * @author Dima Balash on 12.06.2022
 */
interface GetBookingChainsUseCase {

    /**
     * Gets booking chain
     *
     * @param userId user id
     *
     * @return [Result]
     */
    suspend fun getBookingChains(userId: Int): Result<List<ContentItem>>
}