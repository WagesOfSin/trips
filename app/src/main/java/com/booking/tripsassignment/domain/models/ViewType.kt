package com.booking.tripsassignment.domain.models

/**
 * Type of views
 *
 * @author Dima Balash on 12.06.2022
 */
sealed class ViewType {
    object TitleViewType : ViewType()
    object ItemViewType : ViewType()
}
