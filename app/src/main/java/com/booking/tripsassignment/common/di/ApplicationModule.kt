package com.booking.tripsassignment.common.di

import android.content.Context
import com.booking.tripsassignment.common.ResourceManager
import com.booking.tripsassignment.common.ResourceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Module for proving objects for all application
 *
 * @author Dima Balash on 12.06.2022
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideResourceManager(@ApplicationContext appContext: Context): ResourceManager =
        ResourceManagerImpl(appContext)
}