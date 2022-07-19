package com.booking.tripsassignment.common

import android.content.Context
import androidx.annotation.StringRes

/**
 * Manager to retrieve resources
 *
 * @author Dima Balash on 12.06.2022
 */
interface ResourceManager {
    /**
     * Gets string by resource id
     *
     * @param resId string resource id
     * @return string for resId
     */
    fun getString(@StringRes resId: Int): String
}

/**
 * Realization [ResourceManager]
 *
 * @property context android context
 */
internal class ResourceManagerImpl constructor(private val context: Context) :
    ResourceManager {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}