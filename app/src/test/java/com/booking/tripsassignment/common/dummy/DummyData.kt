package com.booking.tripsassignment.common.dummy

import com.booking.tripsassignment.data.models.Booker
import com.booking.tripsassignment.data.models.Booking
import com.booking.tripsassignment.data.models.Hotel
import com.booking.tripsassignment.data.models.Price
import com.booking.tripsassignment.domain.models.BookingContentItem
import com.booking.tripsassignment.domain.models.TitleContentItem
import org.joda.time.LocalDate

/**
 * File with dummy data
 *
 * @author Dima Balash on 13.06.2022
 */

val today = LocalDate.now()

val titleUpcoming = TitleContentItem("Upcoming")

val bookingUpcoming = BookingContentItem(
    listOf("Amsterdam"),
    today.plusDays(10),
    today.plusDays(20),
    "",
    1
)

val titlePast = TitleContentItem("Past")

val bookingPast = BookingContentItem(
    listOf("Amsterdam"),
    today.minusDays(20),
    today.minusDays(10),
    "",
    1
)

val bookingList = listOf(titleUpcoming, bookingUpcoming, titlePast, bookingPast)

fun buildBooking(
    destinationName: List<String>,
    urlPicture: String,
    days: Int,
    duration: Int,
    bookingCount: Int
): BookingContentItem {
    val today = LocalDate.now()

    val checkin = today.plusDays(days)
    val checkout = checkin.plusDays(duration)

    return BookingContentItem(
        destinationName,
        checkin,
        checkout,
        urlPicture,
        bookingCount
    )
}
