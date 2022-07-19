package com.booking.tripsassignment.domain.models

import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.*

/**
 * Booking item
 *
 * @property bookingLocation location of booking
 * @property startDate       start date of booking
 * @property endDate         end date of booking
 * @property urlPicture      picture
 * @property numberOfBooking count of booking
 * @property type            type of element
 *
 * @author Dima Balash on 12.06.2022
 */
data class BookingContentItem(
    val bookingLocation: List<String>,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val urlPicture: String,
    val numberOfBooking: Int,
    override val type: ViewType = ViewType.ItemViewType
) : ContentItem() {

    private val dayMonthYearFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val dayMonthFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())
    private val dayFormatter = SimpleDateFormat("dd", Locale.getDefault())

    fun getDate(): String {
        val date = StringBuilder()
        if (startDate.year != endDate.year) {
            date.append(dayMonthYearFormatter.format(startDate.toDate()))
        } else if (startDate.monthOfYear != endDate.monthOfYear) {
            date.append(dayMonthFormatter.format(startDate.toDate()))
        } else {
            date.append(dayFormatter.format(startDate.toDate()))
        }
        date.append('-')
        date.append(dayMonthYearFormatter.format(endDate.toDate()))
        return date.toString()
    }
}
