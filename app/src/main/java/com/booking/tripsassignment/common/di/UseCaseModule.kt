package com.booking.tripsassignment.common.di

import com.booking.tripsassignment.common.ResourceManager
import com.booking.tripsassignment.data.repositories.BookingRepository
import com.booking.tripsassignment.domain.usecase.GetBookingChainsUseCase
import com.booking.tripsassignment.domain.usecase.GetBookingChainsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * UseCase Module
 *
 * @author Dima Balash on 12.06.2022
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideGetBookingChainsUseCase(
        bookingRepository: BookingRepository,
        resourceManager: ResourceManager
    ): GetBookingChainsUseCase {
        return GetBookingChainsUseCaseImpl(bookingRepository, resourceManager)
    }
}