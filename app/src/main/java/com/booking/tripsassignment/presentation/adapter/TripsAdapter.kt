package com.booking.tripsassignment.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booking.tripsassignment.R
import com.booking.tripsassignment.common.utils.ImageLoader
import com.booking.tripsassignment.databinding.TripCardItemLayoutBinding
import com.booking.tripsassignment.databinding.TripsHeaderItemLayoutBinding
import com.booking.tripsassignment.domain.models.BookingContentItem
import com.booking.tripsassignment.domain.models.ContentItem
import com.booking.tripsassignment.domain.models.TitleContentItem
import com.booking.tripsassignment.domain.models.ViewType

/**
 * Adapter for showing bookings
 *
 * @author Dima Balash on 12.06.2022
 */
class TripsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val typeTitle = 0
    private val typeItem = 1

    private var items: List<ContentItem> = emptyList()

    /**
     * Set data to list
     *
     * @param data list of [ContentItem]
     */
    fun setData(data: List<ContentItem>) {
        notifyItemRangeRemoved(0, items.size)
        items = data
        notifyItemRangeInserted(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (typeTitle == viewType) {
            HeaderViewHolder(TripsHeaderItemLayoutBinding.inflate(layoutInflater, parent, false))
        } else {
            ItemViewHolder(TripCardItemLayoutBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            typeTitle -> (holder as HeaderViewHolder).bind(items[position] as TitleContentItem)
            else -> (holder as ItemViewHolder).bind(items[position] as BookingContentItem)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position].type ==
            ViewType.TitleViewType
        ) typeTitle else typeItem
    }

    /**
     * ViewHolder for header
     *
     * @param binding [TripsHeaderItemLayoutBinding]
     */
    class HeaderViewHolder(private val binding: TripsHeaderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds data to view
         *
         * @param item [TitleContentItem]
         */
        fun bind(
            item: TitleContentItem
        ) = with(binding) {
            tripsHeader.text = item.title
        }
    }

    /**
     * ViewHolder for header
     *
     * @param binding [TripCardItemLayoutBinding]
     */
    class ItemViewHolder(private val binding: TripCardItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds data to view
         *
         * @param item [BookingContentItem]
         */
        fun bind(
            item: BookingContentItem
        ) = with(binding) {
            ImageLoader.loadImage(tripImage, item.urlPicture)
            cities.text = getDestinations(item.bookingLocation, cities.context)
            dates.text = item.getDate()
            nights.text = nights.context.resources.getQuantityString(
                R.plurals.trip_list_booking,
                item.numberOfBooking,
                item.numberOfBooking
            )
        }

        private fun getDestinations(destinations: List<String>, context: Context): String {
            val result = StringBuilder()
            for (item in destinations) {
                if (result.isEmpty()) {
                    result.append(item)
                } else if (item == destinations.last()) {
                    result.append(' ')
                    result.append(context.getString(R.string.trip_list_and, item))
                } else {
                    result.append(", ")
                    result.append(item)
                }
            }
            return context.getString(R.string.trip_list_trip_to, result.toString())
        }
    }
}