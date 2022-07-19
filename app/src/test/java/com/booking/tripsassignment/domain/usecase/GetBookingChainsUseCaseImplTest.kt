package com.booking.tripsassignment.domain.usecase

import com.booking.tripsassignment.R
import com.booking.tripsassignment.common.ResourceManager
import com.booking.tripsassignment.common.dummy.buildBooking
import com.booking.tripsassignment.common.utils.Result
import com.booking.tripsassignment.data.repositories.BookingRepository
import com.booking.tripsassignment.data.repositories.MockDataGenerator
import com.booking.tripsassignment.data.repositories.TestCase
import com.booking.tripsassignment.domain.models.TitleContentItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test

/**
 * Test on [GetBookingChainsUseCaseImpl]
 *
 * @author Dima Balash on 12.06.2022
 */
class GetBookingChainsUseCaseImplTest {

    private val repository = mockk<BookingRepository>()
    private val resourceManager = mockk<ResourceManager>()
    private lateinit var getBookingChainsUseCase: GetBookingChainsUseCase
    private lateinit var titleUpcomingItem: TitleContentItem
    private lateinit var titlePastItem: TitleContentItem

    @Before
    fun setUp() {
        getBookingChainsUseCase = GetBookingChainsUseCaseImpl(repository, resourceManager)
        every { resourceManager.getString(R.string.upcoming_trips) } returns "Upcoming"
        every { resourceManager.getString(R.string.past_trips) } returns "Past"
        titleUpcomingItem = TitleContentItem(resourceManager.getString(R.string.upcoming_trips))
        titlePastItem = TitleContentItem(resourceManager.getString(R.string.past_trips))
    }

    @Test
    fun `check empty state booking chain`() {
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(TestCase.NO_BOOKING.bookerId)!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertTrue(res.data()!!.isEmpty())
        }
    }

    @Test
    fun `1 past booking`() {
        val expected = listOf(
            titlePastItem,
            buildBooking(
                listOf(
                    "Goa"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/59511743.jpg?k=b2a1143941d2de67941e55e685e5385c665792019fa43c972043769cc2091c5c&o=&hp=1",
                -5,
                4,
                1
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.PAST_BOOKING.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data()!!)
        }
    }

    @Test
    fun `1 future booking`() {
        val expected = listOf(
            titleUpcomingItem,
            buildBooking(
                listOf(
                    "Goa"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/145814506.jpg?k=18fb49c6cdaf938a5376cbfc1501ab020a52b1c6f542e481cb34d36045463eaa&o=&hp=1",
                28,
                10,
                1
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.FUTURE_BOOKING.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data()!!)
        }
    }

    @Test
    fun `past bookings`() {
        val expected = listOf(
            titlePastItem,
            buildBooking(
                listOf(
                    "Goa"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/34261038.jpg?k=51496304e6713360c3692a063336e555902a4afe01811b257a5b2d4cd42c14cc&o=&hp=1",
                -5,
                4,
                1
            ),
            buildBooking(
                listOf(
                    "Stockholm"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/220059420.jpg?k=8d35789d99875f87ff72acd03fb67d166a38e3ede565ed517ca1520121f01874&o=&hp=11",
                -180,
                1,
                1
            ),
            buildBooking(
                listOf(
                    "Stockholm"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                -365,
                15,
                1
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.PAST_BOOKINGS.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data()!!)
        }
    }

    @Test
    fun `future bookings`() {
        val expected = listOf(
            titleUpcomingItem,
            buildBooking(
                listOf(
                    "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/267009146.jpg?k=660368ae042748f93c7ccf44cb7b25dfbce8cb86bc259de4fda0813283c27019&o=&hp=1",
                5,
                14,
                1
            ),
            buildBooking(
                listOf(
                    "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/52262839.jpg?k=063666f1d8734967fe65d6185a0750cc30b3212d10499c8c2651941c4a78ea47&o=&hp=1",
                28,
                1,
                1
            ),
            buildBooking(
                listOf(
                    "Florence"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/208620626.jpg?k=e93b419894128698097727139e16d96b2222026c9edbdb06abda72e60d3031ab&o=&hp=1",
                30,
                15,
                1
            ),
            buildBooking(
                listOf(
                    "Paris"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/95095762.jpg?k=92793eac1c10698135069793f23e53e0742f10e53f5ba3561cbf66ac07d1e2fd&o=&hp=1",
                180,
                15,
                1
            ),
            buildBooking(
                listOf(
                    "Kovalam"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/210918969.jpg?k=fef6d0af1a5c153a72801a14eaa8eb5b43eacc76dc6135f9892b45ae31a24f27&o=&hp=1",
                200,
                30,
                1
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.FUTURE_BOOKINGS.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data()!!)
        }
    }

    @Test
    fun `future and past bookings`() {
        val expected = listOf(
            titleUpcomingItem,
            buildBooking(
                listOf(
                    "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/267009146.jpg?k=660368ae042748f93c7ccf44cb7b25dfbce8cb86bc259de4fda0813283c27019&o=&hp=1",
                5,
                14,
                1
            ),
            buildBooking(
                listOf(
                    "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/52262839.jpg?k=063666f1d8734967fe65d6185a0750cc30b3212d10499c8c2651941c4a78ea47&o=&hp=1",
                28,
                1,
                1
            ),
            buildBooking(
                listOf(
                    "Florence"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/208620626.jpg?k=e93b419894128698097727139e16d96b2222026c9edbdb06abda72e60d3031ab&o=&hp=1",
                30,
                15,
                1
            ),
            buildBooking(
                listOf(
                    "Paris"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/95095762.jpg?k=92793eac1c10698135069793f23e53e0742f10e53f5ba3561cbf66ac07d1e2fd&o=&hp=1",
                180,
                15,
                1
            ),
            buildBooking(
                listOf(
                    "Kovalam"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/210918969.jpg?k=fef6d0af1a5c153a72801a14eaa8eb5b43eacc76dc6135f9892b45ae31a24f27&o=&hp=1",
                200,
                30,
                1
            ),
            titlePastItem,
            buildBooking(
                listOf(
                    "Goa"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/34261038.jpg?k=51496304e6713360c3692a063336e555902a4afe01811b257a5b2d4cd42c14cc&o=&hp=1",
                -5,
                4,
                1
            ),
            buildBooking(
                listOf(
                    "Stockholm"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/220059420.jpg?k=8d35789d99875f87ff72acd03fb67d166a38e3ede565ed517ca1520121f01874&o=&hp=11",
                -180,
                1,
                1
            ),
            buildBooking(
                listOf(
                    "Stockholm"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                -365,
                15,
                1
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.FUTURE_AND_PAST_BOOKINGS.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data()!!)
        }
    }

    @Test
    fun `1 past booking chain`() {
        val expected = listOf(
            titlePastItem,
            buildBooking(
                listOf(
                    "Milan", "Florence", "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                -30,
                29,
                7
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.PAST_CHAIN.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data())
        }
    }

    @Test
    fun `1 future booking chain`() {
        val expected = listOf(
            titleUpcomingItem,
            buildBooking(
                listOf(
                    "Milan", "Florence", "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                30,
                43,
                7
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.FUTURE_CHAIN.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data())
        }
    }

    @Test
    fun `1 future and 1 past booking chain`() {
        val expected = listOf(
            titleUpcomingItem,
            buildBooking(
                listOf(
                    "Agra", "Goa", "Pondicherry", "Kovalam", "Cochin", "Pune", "Delhi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/295686542.jpg?k=88737ca54ca3d4c7da9aa31a9bf881c4878f96fa8b392bd3fc65dfa637679ff5&o=&hp=1",
                83,
                63,
                11
            ),
            titlePastItem,
            buildBooking(
                listOf(
                    "Milan","Florence", "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                -30,
                29,
                7
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.PAST_AND_FUTURE_CHAIN.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data())
        }
    }

    @Test
    fun `pasts booking chain`() {
        val expected = listOf(
            titlePastItem,
            buildBooking(
                listOf(
                    "Milan", "Florence", "Amalfi",
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                -30,
                29,
                7
            ),
            buildBooking(
                listOf(
                    "Agra", "Goa", "Pondicherry", "Kovalam", "Cochin", "Pune", "Delhi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/295686542.jpg?k=88737ca54ca3d4c7da9aa31a9bf881c4878f96fa8b392bd3fc65dfa637679ff5&o=&hp=1",
                -100,
                62,
                11
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.PAST_CHAINS.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data())
        }
    }

    @Test
    fun `future booking chain`() {
        val expected = listOf(
            titleUpcomingItem,
            buildBooking(
                listOf(
                    "Milan", "Florence", "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                30,
                43,
                7
            ),
            buildBooking(
                listOf(
                    "Agra", "Goa", "Pondicherry", "Kovalam", "Cochin", "Pune", "Delhi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/295686542.jpg?k=88737ca54ca3d4c7da9aa31a9bf881c4878f96fa8b392bd3fc65dfa637679ff5&o=&hp=1",
                83,
                63,
                11
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.FUTURE_CHAINS.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data()!!)
        }
    }

    @Test
    fun `multiple chains in the past and future`() {
        val expected = listOf(
            titleUpcomingItem,
            buildBooking(
                listOf(
                    "Milan", "Florence", "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                30,
                43,
                7
            ),
            buildBooking(
                listOf(
                    "Agra", "Goa", "Pondicherry", "Kovalam", "Cochin", "Pune", "Delhi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/295686542.jpg?k=88737ca54ca3d4c7da9aa31a9bf881c4878f96fa8b392bd3fc65dfa637679ff5&o=&hp=1",
                83,
                63,
                11
            ),
            titlePastItem,
            buildBooking(
                listOf(
                    "Milan", "Florence", "Amalfi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/264158980.jpg?k=9f4c03eb0c62962497b7b6d0137fda5b4cf52147a560f4849a01a724ab052ffb&o=&hp=1",
                -30,
                29,
                7
            ),
            buildBooking(
                listOf(
                    "Agra", "Goa", "Pondicherry", "Kovalam", "Cochin", "Pune", "Delhi"
                ),
                "https://cf.bstatic.com/xdata/images/hotel/max1024x768/295686542.jpg?k=88737ca54ca3d4c7da9aa31a9bf881c4878f96fa8b392bd3fc65dfa637679ff5&o=&hp=1",
                -100,
                62,
                11
            )
        )
        every { repository.fetchBookings(1) } returns Result.Success(
            MockDataGenerator.bookingsForUser(
                TestCase.PAST_AND_FUTURE_CHAINS.bookerId
            )!!
        )
        runBlocking {
            val res = getBookingChainsUseCase.getBookingChains(1)
            verify { repository.fetchBookings(1) }
            assertEquals(expected, res.data()!!)
        }
    }

}