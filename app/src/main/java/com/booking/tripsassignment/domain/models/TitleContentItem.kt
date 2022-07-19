package com.booking.tripsassignment.domain.models

/**
 * Title content item
 *
 * @property title title
 * @property type  [ViewType]
 *
 * @author Dima Balash on 12.06.2022
 */
data class TitleContentItem(
    val title: String,
    override val type: ViewType = ViewType.TitleViewType
) : ContentItem()
