package com.booking.tripsassignment.common.di

import com.booking.tripsassignment.data.repositories.BookingRepository
import com.booking.tripsassignment.data.repositories.MockNetworkBookingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * Repository Module
 *
 * @author Dima Balash on 12.06.2022
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideBookingRepository(): BookingRepository {
        return MockNetworkBookingRepository()
    }
}