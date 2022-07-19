package com.booking.tripsassignment.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Coroutine Dispatcher module
 *
 * @author Dima Balash on 12.06.2022
 */
@InstallIn(SingletonComponent::class)
@Module
object CoroutinesDispatcherModule {
    @Provides
    @Singleton
    fun CoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}