package com.booking.tripsassignment.common

import android.content.Context
import com.booking.tripsassignment.R
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Test on [ResourceManagerImpl]
 *
 * @author Dima Balash on 12.06.2022
 */
class ResourceManagerImplTest{
    private val context = mockk<Context>()
    private lateinit var resourceManager: ResourceManager

    @Before
    fun setUp() {
        resourceManager = ResourceManagerImpl(context)
    }

    @Test
    fun `receive string`() {
        val string = "Upcoming"
        every { context.getString(R.string.upcoming_trips) } returns string
        assertEquals(string, resourceManager.getString(R.string.upcoming_trips))
        verify { context.getString(R.string.upcoming_trips) }
    }

    @Test
    fun `receive empty string`() {
        every { context.getString(R.string.upcoming_trips) } returns ""
        assertTrue(resourceManager.getString(R.string.upcoming_trips).isEmpty())
        verify { context.getString(R.string.upcoming_trips) }
    }
}