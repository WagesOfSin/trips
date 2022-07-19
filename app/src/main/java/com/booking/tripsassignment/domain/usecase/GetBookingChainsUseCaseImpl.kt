package com.booking.tripsassignment.domain.usecase

import com.booking.tripsassignment.R
import com.booking.tripsassignment.common.ResourceManager
import com.booking.tripsassignment.common.utils.Result
import com.booking.tripsassignment.data.models.Booking
import com.booking.tripsassignment.data.repositories.BookingRepository
import com.booking.tripsassignment.domain.models.BookingContentItem
import com.booking.tripsassignment.domain.models.ContentItem
import com.booking.tripsassignment.domain.models.TitleContentItem
import org.joda.time.LocalDate
import javax.inject.Inject

/**
 * Implementation of [GetBookingChainsUseCase]
 *
 * @property bookingRepository [BookingRepository]
 * @property resourceManager [ResourceManager]
 *
 * @author Dima Balash on 12.06.2022
 */
class GetBookingChainsUseCaseImpl @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val resourceManager: ResourceManager
) :
    GetBookingChainsUseCase {

    override suspend fun getBookingChains(userId: Int): Result<List<ContentItem>> {
        return when (val result = bookingRepository.fetchBookings(userId)) {
            is Result.Error -> result
            else -> Result.Success(getBookingChain(result.data()))
        }
    }

    private fun getBookingChain(bookingList: List<Booking>?): List<ContentItem> {
        val resultList = ArrayList<ContentItem>()
        bookingList?.let {
            val resultUpcomingBookingList = ArrayList<BookingContentItem>()
            val resultPastBookingList = ArrayList<BookingContentItem>()
            val sortedList = bookingList.sortedByDescending { booking -> booking.checkin.toDate() }
            var bookingCount = 1
            var checkOutDate: LocalDate? = null
            var checkInDate: LocalDate?
            var picture: String
            var listOfLocations = LinkedHashSet<String>()
            val now = LocalDate.now()
            for (i in sortedList.indices) {
                val currentItem = sortedList[i]
                if (checkOutDate == null) {
                    checkOutDate = currentItem.checkout
                }
                checkInDate = currentItem.checkin
                picture = currentItem.hotel.mainPhoto
                listOfLocations.add(currentItem.hotel.cityName)

                if (i + 1 <= sortedList.lastIndex) {
                    val nextItem = sortedList[i + 1]
                    if (checkInDate.isEqual(nextItem.checkout) || checkInDate.isBefore(nextItem.checkout)) {
                        bookingCount++
                    } else {
                        val item = BookingContentItem(
                            listOfLocations.reversed(),
                            checkInDate,
                            checkOutDate,
                            picture,
                            bookingCount
                        )
                        if(now.isBefore(checkInDate))
                            resultUpcomingBookingList.add(item)
                        else
                            resultPastBookingList.add(item)
                        checkOutDate = null
                        bookingCount = 1
                        listOfLocations = LinkedHashSet()
                    }
                } else {
                    listOfLocations.add(currentItem.hotel.cityName)
                    val item = BookingContentItem(
                        listOfLocations.reversed(),
                        checkInDate,
                        checkOutDate,
                        picture,
                        bookingCount
                    )
                    if(now.isBefore(checkInDate))
                        resultUpcomingBookingList.add(item)
                    else
                        resultPastBookingList.add(item)
                }

            }
            
            val upcomingTitle = TitleContentItem(resourceManager.getString(R.string.upcoming_trips))
            val pastTitle = TitleContentItem(resourceManager.getString(R.string.past_trips))
            
            if (resultUpcomingBookingList.isNotEmpty()){
                resultList.add(upcomingTitle)
                resultUpcomingBookingList.reverse()
                resultList.addAll(resultUpcomingBookingList)
            }

            if (resultPastBookingList.isNotEmpty()){
                resultList.add(pastTitle)
                resultList.addAll(resultPastBookingList)
            }
        }
        return resultList
    }
}