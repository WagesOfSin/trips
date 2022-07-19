package com.booking.tripsassignment.presentation.triplist

import app.cash.turbine.test
import com.booking.tripsassignment.common.ResourceManager
import com.booking.tripsassignment.common.dummy.bookingList
import com.booking.tripsassignment.common.utils.NetworkError
import com.booking.tripsassignment.common.utils.Result
import com.booking.tripsassignment.domain.usecase.GetBookingChainsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test on [TripListViewModel]
 *
 * @author Dima Balash on 13.06.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TripListViewModelTest {

    private lateinit var viewModel: TripListViewModel
    private val useCase = mockk<GetBookingChainsUseCase>()
    private val resourceManager = mockk<ResourceManager>()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

    }

    @Test
    fun `receive booking by userId`() = runBlocking {
        coEvery { useCase.getBookingChains(7984567) } returns Result.Success(bookingList)
        viewModel = TripListViewModel(testDispatcher, useCase, resourceManager)
        viewModel.uiState.test {
            val item = awaitItem()
            assertFalse(item.loading)
            assertTrue(item.error.isEmpty())
            assertEquals(item.data, bookingList)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { useCase.getBookingChains(7984567) }
    }

    @Test
    fun `receive empty booking by userId`() = runBlocking {
        coEvery { useCase.getBookingChains(7984567) } returns Result.Success(emptyList())
        viewModel = TripListViewModel(testDispatcher, useCase, resourceManager)
        viewModel.uiState.test {
            val item = awaitItem()
            assertFalse(item.loading)
            assertTrue(item.error.isEmpty())
            assertTrue(item.data.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { useCase.getBookingChains(7984567) }
    }

    @Test
    fun `receive error`() = runBlocking {
        val error = "API call error"
        coEvery { useCase.getBookingChains(7984567) } returns Result.Error(NetworkError(error))
        viewModel = TripListViewModel(testDispatcher, useCase, resourceManager)
        viewModel.uiState.test {
            val item = awaitItem()
            assertFalse(item.loading)
            assertEquals(item.error, error)
            assertTrue(item.data.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { useCase.getBookingChains(7984567) }
    }

    @Test
    fun `check retry click`() = runBlocking {
        val error = "API call error"
        coEvery { useCase.getBookingChains(7984567) } returns Result.Error(NetworkError(error)) andThen Result.Success(
            bookingList
        )
        viewModel = TripListViewModel(testDispatcher, useCase, resourceManager)
        viewModel.uiState.test {
            val item = awaitItem()
            assertFalse(item.loading)
            assertEquals(item.error, error)
            assertTrue(item.data.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
        viewModel.retryClick()
        viewModel.uiState.test {
            val item = awaitItem()
            assertFalse(item.loading)
            assertTrue(item.error.isEmpty())
            assertEquals(item.data, bookingList)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { useCase.getBookingChains(7984567) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}